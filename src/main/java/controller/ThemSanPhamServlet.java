package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import database.TheLoaiDAO;
import model.SanPham;
import model.TheLoai;
import service.SanPhamService;

@WebServlet("/them-san-pham")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class ThemSanPhamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SanPhamService sanPhamService;
    private TheLoaiDAO theLoaiDAO;
    
    public ThemSanPhamServlet() {
        super();
        sanPhamService = new SanPhamService();
        theLoaiDAO = new TheLoaiDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy danh sách thể loại để hiển thị trong form
        List<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
        request.setAttribute("danhSachTheLoai", danhSachTheLoai);
        
        // Chuyển đến trang thêm sản phẩm
        request.getRequestDispatcher("/admin/them-san-pham.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            // Lấy thông tin sản phẩm từ form
            String tenSanPham = request.getParameter("tenSanPham");
            String tacGia = request.getParameter("tacGia");
            int namXuatBan = Integer.parseInt(request.getParameter("namXuatBan"));
            double giaNhap = Double.parseDouble(request.getParameter("giaNhap"));
            double giaGoc = Double.parseDouble(request.getParameter("giaGoc"));
            double giaBan = Double.parseDouble(request.getParameter("giaBan"));
            double giaGiam = 0;
            if (request.getParameter("giaGiam") != null && !request.getParameter("giaGiam").isEmpty()) {
                giaGiam = Double.parseDouble(request.getParameter("giaGiam"));
            }
            int soLuong = Integer.parseInt(request.getParameter("soLuong"));
            int maTheLoai = Integer.parseInt(request.getParameter("maTheLoai"));
            String ngonNgu = request.getParameter("ngonNgu");
            String moTa = request.getParameter("moTa");
            
            TheLoai tl = new TheLoai();
            tl.setMaTheLoai(maTheLoai);
            // Lấy thể loại
            TheLoai theLoai = theLoaiDAO.selectById(tl);
            if (theLoai == null) {
                theLoai = new TheLoai();
                theLoai.setMaTheLoai(maTheLoai);
            }
            
            // Tạo đối tượng sản phẩm
            SanPham sanPham = new SanPham();
            sanPham.setTenSanPham(tenSanPham);
            sanPham.setTacGia(tacGia);
            sanPham.setNamXuatBan(namXuatBan);
            sanPham.setGiaNhap(giaNhap);
            sanPham.setGiaGoc(giaGoc);
            sanPham.setGiaBan(giaBan);
            sanPham.setGiaGiam(giaGiam);
            sanPham.setSoLuong(soLuong);
            sanPham.setTheLoai(theLoai);
            sanPham.setNgonNgu(ngonNgu);
            sanPham.setMoTa(moTa);
            
            // Lấy danh sách hình ảnh từ form
            Collection<Part> parts = request.getParts();
            List<Part> hinhAnhParts = new ArrayList<>();
            
            for (Part part : parts) {
                if (part.getName().equals("hinhAnh") && part.getSize() > 0) {
                    hinhAnhParts.add(part);
                }
            }
            
            // Thêm sản phẩm và hình ảnh
            boolean ketQua = sanPhamService.themSanPhamVaHinhAnh(sanPham, hinhAnhParts);
            
            if (ketQua) {
                // Thêm thành công, chuyển về trang danh sách sản phẩm
                request.getSession().setAttribute("successMessage", "Thêm sản phẩm thành công!");
                response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
            } else {
                // Thêm thất bại, quay lại trang thêm sản phẩm
                request.setAttribute("errorMessage", "Thêm sản phẩm thất bại!");
                request.setAttribute("sanPham", sanPham);
                request.setAttribute("danhSachTheLoai", theLoaiDAO.selectAll());
                request.getRequestDispatcher("/admin/them-san-pham.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            request.setAttribute("danhSachTheLoai", theLoaiDAO.selectAll());
            request.getRequestDispatcher("/admin/them-san-pham.jsp").forward(request, response);
        }
    }
}