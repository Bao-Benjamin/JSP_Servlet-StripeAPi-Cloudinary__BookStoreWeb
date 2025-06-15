package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import database.ChiTietDonHangDAO;
import database.DonHangDAO;
import database.KhachHangDAO;
import database.SanPhamDAO;

import javax.servlet.http.HttpSession;
import model.Cart;
import model.CartItem;
import model.ChiTietDonHang;
import model.DonHang;
import model.KhachHang;
import model.SanPham;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public OrderServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        // Kiểm tra giỏ hàng
        if (cart == null || cart.getItems().isEmpty()) {
            response.sendRedirect("donhang/thongtindonhang.jsp?error=empty_cart");
            return;
        }
        
        try {
            // Lấy thông tin từ form
            String paymentMethod = request.getParameter("hinhThucThanhToan");
            String tenDangNhap = request.getParameter("tenDangNhap");
            String hoVaTen = request.getParameter("hoVaTen");
            String soDienThoai = request.getParameter("soDienThoai");
            String diaChiNhanHang = request.getParameter("diaChiNhanHang");
            String ghiChu = request.getParameter("ghiChu");
            String ngayDatHangStr = request.getParameter("ngayDatHang");
            
            // Kiểm tra các trường bắt buộc
            if (tenDangNhap == null || soDienThoai == null || diaChiNhanHang == null || 
                tenDangNhap.trim().isEmpty() || soDienThoai.trim().isEmpty() || diaChiNhanHang.trim().isEmpty()) {
                response.sendRedirect("donhang/thongtindonhang.jsp?error=missing_fields");
                return;
            }
            
            // Tạo mã khách hàng
            String maKhachHang = "DH" + System.currentTimeMillis();
            
            // Chuyển đổi ngày đặt hàng
            LocalDate ngayDatHang = LocalDate.parse(ngayDatHangStr, DateTimeFormatter.ISO_DATE);
            
            // Tạo đơn hàng
            DonHang dh = new DonHang(maKhachHang, tenDangNhap, soDienThoai, diaChiNhanHang, paymentMethod, Date.valueOf(ngayDatHang), ghiChu);
            DonHangDAO dhd = new DonHangDAO();
            dhd.insert(dh);
            
            // Lấy thông tin khách hàng
            KhachHangDAO khd = new KhachHangDAO();
            KhachHang kh = khd.selectByUsername(tenDangNhap);
            
            // Tạo chi tiết đơn hàng cho mỗi sản phẩm trong giỏ hàng
            ChiTietDonHangDAO ctdhDAO = new ChiTietDonHangDAO();
            
            for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                CartItem item = entry.getValue();
                SanPham spham = item.getSanPham();
                int soLuong = item.getQuantity();
                double giaGoc = spham.getGiaBan();
                double giaGiam = spham.getGiaGiam() > 0 && spham.getGiaGiam() < spham.getGiaBan() ? spham.getGiaGiam() : giaGoc;
                double tongTien = item.getTotalPrice();
                
                ChiTietDonHang ctdh = new ChiTietDonHang(0, dh, spham, soLuong, giaGoc, giaGiam, giaGoc, 0, tongTien);
                ctdhDAO.insert(ctdh);
                
                // Cập nhật số lượng sản phẩm trong kho
                SanPhamDAO spd = new SanPhamDAO();
                SanPham sp = spd.selectById(spham.getMaSanPham());
                if (sp != null) {
                    sp.setSoLuong(sp.getSoLuong() - soLuong);
                    spd.update(sp);
                }
            }
            
            // Xử lý thanh toán
            double totalPrice = cart.getTotalPrice();
            
            if ("Thanh toán khi nhận hàng".equals(paymentMethod)) {
                // Xóa giỏ hàng sau khi đặt hàng thành công
                session.removeAttribute("cart");
                session.setAttribute("orderSuccess", "true");
                session.setAttribute("orderCode", maKhachHang);
                
                response.sendRedirect("success.jsp");
                return;
            } else if ("Thanh toán qua thẻ".equals(paymentMethod)) {
                // Kiểm tra số tiền tối thiểu cho thanh toán qua thẻ
                if (totalPrice < 12000) {
                    response.sendRedirect("error.jsp?error=Amount too small");
                    return;
                }
                
                // Xử lý thanh toán qua Stripe
                Stripe.apiKey = "add_your_Stripe_key";
                
                try {
                    SessionCreateParams params = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:8080/Bookstore/success.jsp?order=" + maKhachHang)
                        .setCancelUrl("http://localhost:8080/Bookstore/donhang/thongtindonhang.jsp?error=payment_cancelled")
                        .addLineItem(
                            SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                    SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("vnd")
                                        .setUnitAmount((long)totalPrice)
                                        .setProductData(
                                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Thanh toán đơn hàng #" + maKhachHang)
                                                .build()
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .build();
                    
                    // Lưu mã đơn hàng vào session để xử lý sau khi thanh toán
                    session.setAttribute("pendingOrderId", maKhachHang);
                    
                    Session stripeSession = Session.create(params);
                    response.sendRedirect(stripeSession.getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("error.jsp?error=payment_failed");
                }
            } else {
                response.sendRedirect("donhang/thongtindonhang.jsp?error=invalid_payment_method");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp?error=order_failed");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}