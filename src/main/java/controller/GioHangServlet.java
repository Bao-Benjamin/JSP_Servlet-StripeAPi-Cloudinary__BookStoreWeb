package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.HinhAnhSanPhamDAO;
import model.GioHang;
import model.HinhAnhSanPham;
import model.KhachHang;
import service.GioHangService;

@WebServlet("/gio-hang")
public class GioHangServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GioHangService gioHangService;
    private HinhAnhSanPhamDAO hinhAnhSanPhamDAO;
    
    public GioHangServlet() {
        super();
        gioHangService = new GioHangService();
        hinhAnhSanPhamDAO = new HinhAnhSanPhamDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin khách hàng từ session
        HttpSession session = request.getSession();
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        
        if (khachHang == null) {
            // Nếu chưa đăng nhập, chuyển đến trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/dang-nhap?redirect=gio-hang");
            return;
        }
        
        // Lấy giỏ hàng của khách hàng
        GioHang gioHang = gioHangService.getGioHangByKhachHang(khachHang);
        
        // Tạo map để lưu trữ hình ảnh cho mỗi sản phẩm
        Map<Integer, List<HinhAnhSanPham>> hinhAnhSanPhamMap = new HashMap<>();
        
        // Lấy hình ảnh cho mỗi sản phẩm trong giỏ hàng
        if (gioHang != null && gioHang.getDanhSachChiTiet() != null) {
            gioHang.getDanhSachChiTiet().forEach(chiTiet -> {
                int maSanPham = chiTiet.getSanPham().getMaSanPham();
                List<HinhAnhSanPham> danhSachHinhAnh = hinhAnhSanPhamDAO.getSanPhamsByMaSanPham(maSanPham);
                hinhAnhSanPhamMap.put(maSanPham, danhSachHinhAnh);
            });
        }
        
        // Đặt giỏ hàng và map hình ảnh vào request
        request.setAttribute("gioHang", gioHang);
        request.setAttribute("hinhAnhSanPhamMap", hinhAnhSanPhamMap);
        
        // Chuyển đến trang giỏ hàng
        request.getRequestDispatcher("/GioHang/giohang.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}