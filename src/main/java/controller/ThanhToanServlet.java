package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import database.ChiTietDonHangDAO;
import database.ChiTietGioHangDAO;
import database.DonHangDAO;
import database.GioHangDAO;
import database.KhachHangDAO;
import database.SanPhamDAO;
import model.ChiTietDonHang;
import model.ChiTietGioHang;
import model.DonHang;
import model.GioHang;
import model.KhachHang;
import model.SanPham;

@WebServlet("/thanh-toan")
public class ThanhToanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public ThanhToanServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("----------da chạy vào ThanhToanServlet");
        HttpSession session = request.getSession();
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        
        // Kiểm tra đăng nhập
        if (khachHang == null) {
            response.sendRedirect(request.getContextPath() + "/dang-nhap?redirect=thanh-toan");
            return;
        }
        
        // Lấy giỏ hàng từ database
        GioHangDAO gioHangDAO = new GioHangDAO();
        GioHang gioHang = gioHangDAO.getGioHangByKhachHang(khachHang.getMaKhachHang());
        
        if (gioHang == null || gioHang.getDanhSachChiTiet() == null || gioHang.getDanhSachChiTiet().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/gio-hang?error=empty_cart");
            return;
        }
        
        // Chuyển hướng đến trang thông tin đơn hàng
        request.getRequestDispatcher("/donhang/thongtindonhang.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        
        // Kiểm tra đăng nhập
        if (khachHang == null) {
            response.sendRedirect(request.getContextPath() + "/dang-nhap?redirect=thanh-toan");
            return;
        }
        
        // Lấy giỏ hàng từ database
        GioHangDAO gioHangDAO = new GioHangDAO();
        GioHang gioHang = gioHangDAO.getGioHangByKhachHang(khachHang.getMaKhachHang());
        
        // Kiểm tra giỏ hàng
        if (gioHang == null || gioHang.getDanhSachChiTiet() == null || gioHang.getDanhSachChiTiet().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/donhang/thongtindonhang.jsp?error=empty_cart");
            return;
        }
        
        try {
            // Lấy thông tin từ form
            String paymentMethod = request.getParameter("hinhThucThanhToan");
            String hoVaTen = request.getParameter("hoVaTen");
            String soDienThoai = request.getParameter("soDienThoai");
            String diaChiNhanHang = request.getParameter("diaChiNhanHang");
            String ghiChu = request.getParameter("ghiChu");
            String ngayDatHangStr = request.getParameter("ngayDatHang");
            
            System.out.println("-----------hình thức thanh toán: "+ paymentMethod);
            System.out.println("-----------hovate: "+ hoVaTen);
            System.out.println("-----------sdt "+ soDienThoai);
            System.out.println("-----------dia chi "+diaChiNhanHang);
            System.out.println("-----------ghi chu: "+ghiChu);
            System.out.println("-----------ngay dat: "+ ngayDatHangStr);
            // Kiểm tra các trường bắt buộc
            if (soDienThoai == null || diaChiNhanHang == null || 
                soDienThoai.trim().isEmpty() || diaChiNhanHang.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/donhang/thongtindonhang.jsp?error=missing_fields");
                return;
            }
            
            // Tạo mã đơn hàng
            String maDonHang = "DH" + System.currentTimeMillis();
            
            // Chuyển đổi ngày đặt hàng
            LocalDate ngayDatHang = LocalDate.parse(ngayDatHangStr, DateTimeFormatter.ISO_DATE);
            String trangThai = "cho xu ly";
            // Tạo đơn hàng
            DonHang donHang = new DonHang(maDonHang, khachHang.getTenDangNhap(), soDienThoai, diaChiNhanHang, paymentMethod, Date.valueOf(ngayDatHang), ghiChu,trangThai);
            System.out.println("don hang trong thanh toán servlet: "+donHang);
            DonHangDAO donHangDAO = new DonHangDAO();
            donHangDAO.insert(donHang);
            
            // Tạo chi tiết đơn hàng cho mỗi sản phẩm trong giỏ hàng
            ChiTietDonHangDAO ctdhDAO = new ChiTietDonHangDAO();
            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            
            double tongTien = 0;
            
            for (ChiTietGioHang chiTiet : gioHang.getDanhSachChiTiet()) {
                SanPham sanPham = chiTiet.getSanPham();
                int soLuong = chiTiet.getSoLuong();
                double giaGoc = sanPham.getGiaBan();
                double giaGiam = sanPham.getGiaGiam() > 0 && sanPham.getGiaGiam() < sanPham.getGiaBan() ? sanPham.getGiaGiam() : giaGoc;
                double thanhTien = giaGiam * soLuong;
                tongTien += thanhTien;
                
                ChiTietDonHang ctdh = new ChiTietDonHang(0, donHang, sanPham, soLuong, giaGoc, giaGiam, giaGoc, 0, thanhTien);
                ctdhDAO.insert(ctdh);
                
                // Cập nhật số lượng sản phẩm trong kho
                SanPham sp = sanPhamDAO.selectById(sanPham.getMaSanPham());
                if (sp != null) {
                    sp.setSoLuong(sp.getSoLuong() - soLuong);
                    sanPhamDAO.update(sp);
                }
            }
            
            // Xử lý thanh toán
            if ("Ship COD".equals(paymentMethod)) {
                // Xóa giỏ hàng sau khi đặt hàng thành công
                ChiTietGioHangDAO chiTietGioHangDAO = new ChiTietGioHangDAO();
                chiTietGioHangDAO.xoaChiTietTheoGioHang(gioHang.getMaGioHang());
                GioHangDAO ghd= new GioHangDAO();
                System.out.println("gọi hàm xoaGioHang tronh thanh toán servlet");
                ghd.xoaGioHang(gioHang.getMaGioHang());
                System.out.println("ma gio hang: "+gioHang.getMaGioHang());
                session.setAttribute("orderSuccess", "true");
                session.setAttribute("orderCode", maDonHang);
//                DonHang dh = new DonHang(maDonHang, hoVaTen, soDienThoai, diaChiNhanHang, ngayDatHangStr, null, ghiChu, maDonHang);
                
                response.sendRedirect(request.getContextPath() + "/success.jsp");
                return;
            } else if ("Credit Card".equals(paymentMethod)) {
                // Kiểm tra số tiền tối thiểu cho thanh toán qua thẻ
                if (tongTien < 12000) {
                    response.sendRedirect(request.getContextPath() + "/error.jsp?error=Amount too small");
                    return;
                }
                GioHangDAO ghd= new GioHangDAO();
                System.out.println("gọi hàm xoaGioHang tronh thanh toán servlet case credit cart");
                ghd.xoaGioHang(gioHang.getMaGioHang());
                // Xử lý thanh toán qua Stripe
                Stripe.apiKey = "your_api_Stripe";
                
                try {
                    // Tạo URL tuyệt đối cho success và cancel
                    String baseUrl = getBaseUrl(request);
                    String successUrl = baseUrl + "/success.jsp?order=" + maDonHang;
                    String cancelUrl = baseUrl + "/donhang/thongtindonhang.jsp?error=payment_cancelled";
                    
                    System.out.println("Success URL: " + successUrl);
                    System.out.println("Cancel URL: " + cancelUrl);
                    
                    SessionCreateParams.Builder paramsBuilder = SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(successUrl)
                        .setCancelUrl(cancelUrl);
                    
                    // Thêm metadata để lưu thông tin đơn hàng
                    paramsBuilder.putMetadata("order_id", maDonHang);
                    
                    // Thêm sản phẩm vào session
                    paramsBuilder.addLineItem(
                        SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("vnd")
                                    .setUnitAmount((long)tongTien)
                                    .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                            .setName("Thanh toán đơn hàng #" + maDonHang)
                                            .build()
                                    )
                                    .build()
                            )
                            .build()
                    );
                    
                    SessionCreateParams params = paramsBuilder.build();
                    
                    // Lưu mã đơn hàng và mã khách hàng vào session để xử lý sau khi thanh toán
                    session.setAttribute("pendingOrderId", maDonHang);
                    session.setAttribute("pendingOrderMaKhachHang", khachHang.getMaKhachHang());
                    
                    Session stripeSession = Session.create(params);
                    System.out.println("Stripe Session URL: " + stripeSession.getUrl());
                    response.sendRedirect(stripeSession.getUrl());
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Lỗi Stripe: " + e.getMessage());
                    System.out.println("Stack trace: ");
                    for (StackTraceElement element : e.getStackTrace()) {
                        System.out.println(element.toString());
                    }
                    response.sendRedirect(request.getContextPath() + "/error.jsp?error=payment_failed&message=" + e.getMessage());
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/donhang/thongtindonhang.jsp?error=invalid_payment_method");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/error.jsp?error=order_failed");
        }
    }
    
    // Phương thức để lấy URL cơ sở (base URL)
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        
        // Xây dựng URL cơ sở
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        
        // Thêm port nếu không phải port mặc định
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        
        url.append(contextPath);
        
        return url.toString();
    }
}

