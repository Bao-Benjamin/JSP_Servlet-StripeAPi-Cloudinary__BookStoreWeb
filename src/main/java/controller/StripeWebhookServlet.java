package controller;

import java.io.IOException;
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;

import database.ChiTietGioHangDAO;
import database.GioHangDAO;
import model.GioHang;

@WebServlet("/stripe-webhook")
public class StripeWebhookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String STRIPE_WEBHOOK_SECRET = "whsec_your_webhook_secret"; // Thay bằng secret của bạn
    
    public StripeWebhookServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Stripe.apiKey = "your_api_Stripe";
        
        // Đọc payload từ request
        StringBuilder buffer = new StringBuilder();
        BufferedReader reader = request.getReader();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        String payload = buffer.toString();
        
        // Lấy signature từ header
        String sigHeader = request.getHeader("Stripe-Signature");
        
        Event event = null;
        
        try {
            // Xác thực webhook
            event = Webhook.constructEvent(payload, sigHeader, STRIPE_WEBHOOK_SECRET);
        } catch (SignatureVerificationException e) {
            // Xác thực thất bại
            response.setStatus(400);
            return;
        }
        
        // Xử lý sự kiện
        if ("checkout.session.completed".equals(event.getType())) {
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            
            if (dataObjectDeserializer.getObject().isPresent()) {
                Session session = (Session) dataObjectDeserializer.getObject().get();
                
                // Lấy mã đơn hàng từ metadata
                String maDonHang = session.getMetadata().get("order_id");
                
                if (maDonHang != null) {
                    // Lấy thông tin khách hàng từ session
                    HttpSession httpSession = request.getSession();
                    String maKhachHang = (String) httpSession.getAttribute("pendingOrderMaKhachHang");
                    
                    if (maKhachHang != null) {
                        // Xóa giỏ hàng sau khi thanh toán thành công
                        GioHangDAO gioHangDAO = new GioHangDAO();
                        GioHang gioHang = gioHangDAO.getGioHangByKhachHang(maKhachHang);
                        
                        if (gioHang != null) {
                            ChiTietGioHangDAO chiTietGioHangDAO = new ChiTietGioHangDAO();
                            chiTietGioHangDAO.xoaChiTietTheoGioHang(gioHang.getMaGioHang());
                        }
                        
                        // Xóa thông tin đơn hàng đang chờ từ session
                        httpSession.removeAttribute("pendingOrderId");
                        httpSession.removeAttribute("pendingOrderMaKhachHang");
                        
                        // Đặt thông báo thành công
                        httpSession.setAttribute("orderSuccess", "true");
                        httpSession.setAttribute("orderCode", maDonHang);
                    }
                }
            }
        }
        
        // Trả về 200 OK
        response.setStatus(200);
    }
}