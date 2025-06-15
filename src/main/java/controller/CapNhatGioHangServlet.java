package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.ChiTietGioHangDAO;
import database.SanPhamDAO;
import model.ChiTietGioHang;
import model.GioHang;
import model.KhachHang;
import model.SanPham;
import service.GioHangService;

@WebServlet("/cap-nhat-gio-hang")
public class CapNhatGioHangServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private GioHangService gioHangService;
    private SanPhamDAO sanPhamDAO;
    private ChiTietGioHangDAO chiTietGioHangDAO;
    
    public CapNhatGioHangServlet() {
        super();
        gioHangService = new GioHangService();
        sanPhamDAO = new SanPhamDAO();
        chiTietGioHangDAO = new ChiTietGioHangDAO(); // Thêm DAO để truy vấn chi tiết giỏ hàng
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/gio-hang");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy thông tin khách hàng từ session
        HttpSession session = request.getSession();
        KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
        
        if (khachHang == null) {
            // Nếu chưa đăng nhập, chuyển đến trang đăng nhập
            response.sendRedirect(request.getContextPath() + "/dang-nhap?redirect=gio-hang");
            return;
        }
        
        // Lấy thông tin từ request
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/gio-hang");
            return;
        }
        
        // Lấy giỏ hàng của khách hàng
        GioHang gioHang = gioHangService.getGioHangByKhachHang(khachHang);
        
        if (gioHang == null) {
            response.sendRedirect(request.getContextPath() + "/gio-hang");
            return;
        }
        
        boolean ketQua = false;
        
        switch (action) {
            case "update":
                // Cập nhật số lượng
                String maChiTietStr = request.getParameter("maChiTiet");
                String soLuongStr = request.getParameter("soLuong");
                
                if (maChiTietStr != null && soLuongStr != null) {
                    try {
                        int maChiTiet = Integer.parseInt(maChiTietStr);
                        int soLuongMoi = Integer.parseInt(soLuongStr);
                        
                        // Lấy chi tiết giỏ hàng hiện tại để biết số lượng cũ
                        
                        ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
                        chiTietGioHang=chiTietGioHangDAO.selectById(maChiTiet);
                        
                        if (chiTietGioHang != null) {
                            int soLuongCu = chiTietGioHang.getSoLuong();
                            SanPham sanPham = chiTietGioHang.getSanPham();
                            System.out.println("-----sanpham trong case update của cap nhat gio hang servlet: " +sanPham);
                            if (soLuongMoi > 0) {
                                // Tính toán sự chênh lệch số lượng
                                if (soLuongMoi > soLuongCu) {
                                    // Nếu tăng số lượng, cần trừ thêm số lượng sản phẩm trong kho
                                    int soLuongTang = soLuongMoi - soLuongCu;
                                    sanPhamDAO.truSoLuong(sanPham, soLuongTang);
                                    System.out.println("Đã trừ " + soLuongTang + " sản phẩm từ kho");
                                } else if (soLuongMoi < soLuongCu) {
                                    // Nếu giảm số lượng, cần tăng lại số lượng sản phẩm trong kho
                                    int soLuongGiam = soLuongCu - soLuongMoi;
                                    sanPhamDAO.tangSoLuong(sanPham, soLuongGiam);
                                    System.out.println("Đã tăng " + soLuongGiam + " sản phẩm vào kho");
                                }
                                
                                // Cập nhật số lượng trong giỏ hàng
                                ketQua = gioHangService.capNhatSoLuong(maChiTiet, soLuongMoi);
                            } else {
                                // Nếu số lượng <= 0, xóa sản phẩm khỏi giỏ hàng và tăng lại số lượng trong kho
                                sanPhamDAO.tangSoLuong(sanPham, soLuongCu);
                                System.out.println("Đã tăng " + soLuongCu + " sản phẩm vào kho (xóa sản phẩm)");
                                ketQua = gioHangService.xoaSanPhamKhoiGioHang(maChiTiet);
                            }
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                break;
                
            case "remove":
                // Xóa sản phẩm khỏi giỏ hàng
                String maChiTietRemoveStr = request.getParameter("maChiTiet");
                String soLuongRemoveStr = request.getParameter("soLuong");
                
                if (maChiTietRemoveStr != null && soLuongRemoveStr != null) {
                    try {
                        int maChiTiet = Integer.parseInt(maChiTietRemoveStr);
                        int soLuong = Integer.parseInt(soLuongRemoveStr);
                        
                        // Lấy chi tiết giỏ hàng để biết sản phẩm
                        ChiTietGioHang chiTietGioHang = chiTietGioHangDAO.selectById(maChiTiet);
                        
                        if (chiTietGioHang != null) {
                            SanPham sanPham = chiTietGioHang.getSanPham();
                            
                            // Tăng lại số lượng sản phẩm trong kho
                            sanPhamDAO.tangSoLuong(sanPham, soLuong);
                            System.out.println("Đã tăng " + soLuong + " sản phẩm vào kho (xóa sản phẩm)");
                            
                            // Xóa sản phẩm khỏi giỏ hàng
                            ketQua = gioHangService.xoaSanPhamKhoiGioHang(maChiTiet);
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                break;
                
            case "clear":
                // Xóa tất cả sản phẩm trong giỏ hàng
                // Trước khi xóa, cần tăng lại số lượng sản phẩm trong kho
                for (ChiTietGioHang chiTiet : gioHang.getDanhSachChiTiet()) {
                    SanPham sanPham = chiTiet.getSanPham();
                    sanPhamDAO.tangSoLuong(sanPham, chiTiet.getSoLuong());
                    System.out.println("Đã tăng " + chiTiet.getSoLuong() + " sản phẩm vào kho (xóa tất cả)");
                }
                
                ketQua = gioHangService.xoaTatCaSanPham(gioHang.getMaGioHang());
                break;
        }
        
        // Chuyển về trang giỏ hàng
        if (ketQua) {
            response.sendRedirect(request.getContextPath() + "/gio-hang?success=" + action);
        } else {
            response.sendRedirect(request.getContextPath() + "/gio-hang?error=" + action);
        }
    }
}