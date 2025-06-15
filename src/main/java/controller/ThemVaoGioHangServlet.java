package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.SanPhamDAO;
import model.GioHang;
import model.KhachHang;
import model.SanPham;
import service.GioHangService;

@WebServlet("/them-vao-gio-hang")
public class ThemVaoGioHangServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GioHangService gioHangService;
    private SanPhamDAO sanPhamDAO;
    
    public ThemVaoGioHangServlet() {
        super();
        gioHangService = new GioHangService();
        sanPhamDAO = new SanPhamDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin từ request
        String maSanPhamStr = request.getParameter("maSanPham");
        String soLuongStr = request.getParameter("soLuong");
        
        if (maSanPhamStr == null || soLuongStr == null) {
            response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
            return;
        }
        
        try {
            int maSanPham = Integer.parseInt(maSanPhamStr);
            int soLuong = Integer.parseInt(soLuongStr);
            
            // Kiểm tra số lượng
            if (soLuong <= 0) {
                soLuong = 1;
            }
           
            // Lấy thông tin sản phẩm
            SanPham sanPham = sanPhamDAO.selectById(maSanPham);
            //Thay đổi số lượng trước
            int kq = sanPhamDAO.truSoLuong(sanPham, soLuong);
            System.out.println("da thay doi so luong");
            
            if (sanPham == null) {
                response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
                return;
            }
            
            // Lấy thông tin khách hàng từ session
            HttpSession session = request.getSession();
            KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
            
            if (khachHang == null) {
                // Nếu chưa đăng nhập, chuyển đến trang đăng nhập
                response.sendRedirect(request.getContextPath() + "/khachhang/dangnhap.jsp?needsLogin=true");
                return;
            }
            
            // Lấy giỏ hàng của khách hàng
            GioHang gioHang = gioHangService.getGioHangByKhachHang(khachHang);
            
            // Thêm sản phẩm vào giỏ hàng
            boolean ketQua = gioHangService.themSanPhamVaoGioHang(gioHang, sanPham, soLuong);
            
            if (ketQua) {
                // Thêm thành công, chuyển đến trang giỏ hàng
                response.sendRedirect(request.getContextPath() + "/gio-hang?success=add");
            } else {
                // Thêm thất bại, quay lại trang sản phẩm
                response.sendRedirect(request.getContextPath() + "/chi-tiet-san-pham?maSanPham=" + maSanPham + "&error=add");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/trang-chu");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}