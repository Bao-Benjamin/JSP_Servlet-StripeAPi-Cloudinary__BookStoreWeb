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

import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import database.TheLoaiDAO;
import model.HinhAnhSanPham;
import model.SanPham;
import model.TheLoai;
import service.SanPhamService;

@WebServlet("/chinh-sua-san-pham")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024, // 1 MB
    maxFileSize = 1024 * 1024 * 10,  // 10 MB
    maxRequestSize = 1024 * 1024 * 50 // 50 MB
)
public class chinhSuaSanPham extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SanPhamService sanPhamService;
    private TheLoaiDAO theLoaiDAO;
    private SanPhamDAO sanPhamDAO;
    private HinhAnhSanPhamDAO hinhAnhDAO;
       
    public chinhSuaSanPham() {
        super();
        sanPhamService = new SanPhamService();
        theLoaiDAO = new TheLoaiDAO();
        sanPhamDAO = new SanPhamDAO();
        hinhAnhDAO = new HinhAnhSanPhamDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String maSanPhamStr = request.getParameter("maSanPham");
            if (maSanPhamStr == null || maSanPhamStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
                return;
            }
            
            int maSanPham = Integer.parseInt(maSanPhamStr);
            SanPham sanPham = sanPhamDAO.selectById(maSanPham);
            
            if (sanPham == null) {
                request.getSession().setAttribute("errorMessage", "Không tìm thấy sản phẩm!");
                response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
                return;
            }
            
            // Lấy danh sách hình ảnh của sản phẩm
            List<HinhAnhSanPham> danhSachHinhAnh = hinhAnhDAO.getSanPhamsByMaSanPham(maSanPham);
            
            // Lấy danh sách thể loại
            List<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
            
            request.setAttribute("sanpham", sanPham);
            request.setAttribute("danhSachHinhAnh", danhSachHinhAnh);
            request.setAttribute("danhSachTheLoai", danhSachTheLoai);
            
            request.getRequestDispatcher("/sanpham/chinhsuasanpham.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Mã sản phẩm không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        try {
            System.out.println("Bắt đầu xử lý cập nhật sản phẩm");
            
            // Lấy thông tin sản phẩm từ form
            String maSanPhamStr = request.getParameter("maSanPham");
            if (maSanPhamStr == null || maSanPhamStr.trim().isEmpty()) {
                System.out.println("Mã sản phẩm không hợp lệ");
                response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
                return;
            }
            
            int maSanPham = Integer.parseInt(maSanPhamStr);
            String tenSanPham = request.getParameter("tenSanPham");
            String tacGia = request.getParameter("tacGia");
            
            System.out.println("Mã sản phẩm: " + maSanPham);
            System.out.println("Tên sản phẩm: " + tenSanPham);
            System.out.println("Tác giả: " + tacGia);
            
            int namXuatBan = 0;
            try {
                namXuatBan = Integer.parseInt(request.getParameter("namXuatBan"));
            } catch (NumberFormatException e) {
                System.out.println("Lỗi chuyển đổi năm xuất bản: " + e.getMessage());
                request.setAttribute("errorMessage", "Năm xuất bản không hợp lệ!");
                doGet(request, response);
                return;
            }
            
            double giaNhap = 0, giaGoc = 0, giaBan = 0, giaGiam = 0;
            try {
                giaNhap = Double.parseDouble(request.getParameter("giaNhap"));
                giaGoc = Double.parseDouble(request.getParameter("giaGoc"));
                giaBan = Double.parseDouble(request.getParameter("giaBan"));
                
                if (request.getParameter("giaGiam") != null && !request.getParameter("giaGiam").isEmpty()) {
                    giaGiam = Double.parseDouble(request.getParameter("giaGiam"));
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi chuyển đổi giá: " + e.getMessage());
                request.setAttribute("errorMessage", "Thông tin giá không hợp lệ!");
                doGet(request, response);
                return;
            }
            
            int soLuong = 0;
            try {
                soLuong = Integer.parseInt(request.getParameter("soLuong"));
            } catch (NumberFormatException e) {
                System.out.println("Lỗi chuyển đổi số lượng: " + e.getMessage());
                request.setAttribute("errorMessage", "Số lượng không hợp lệ!");
                doGet(request, response);
                return;
            }
            
            int maTheLoai = 0;
            try {
                maTheLoai = Integer.parseInt(request.getParameter("matheloai"));
            } catch (NumberFormatException e) {
                System.out.println("Lỗi chuyển đổi mã thể loại: " + e.getMessage());
                request.setAttribute("errorMessage", "Thể loại không hợp lệ!");
                doGet(request, response);
                return;
            }
            
            String ngonNgu = request.getParameter("ngonNgu");
            String moTa = request.getParameter("moTa");
            
            TheLoai tl = new TheLoai() ;
            tl.setMaTheLoai(maTheLoai);
            // Lấy thể loại
            TheLoai theLoai = theLoaiDAO.selectById(tl);
            if (theLoai == null) {
                System.out.println("Không tìm thấy thể loại với mã: " + maTheLoai);
                theLoai = new TheLoai();
                theLoai.setMaTheLoai(maTheLoai);
            }
            
            // Tạo đối tượng sản phẩm
            SanPham sanPham = new SanPham();
            sanPham.setMaSanPham(maSanPham);
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
            
            System.out.println("Thông tin sản phẩm cần cập nhật: " + sanPham);
            
            // Cập nhật thông tin sản phẩm
            int result = sanPhamDAO.update(sanPham);
            System.out.println("Kết quả cập nhật sản phẩm: " + result);
            
            // Kiểm tra xem có hình ảnh mới không
            String xoaHinhAnhCu = request.getParameter("xoaHinhAnhCu");
            boolean isDeleteOldImages = "on".equals(xoaHinhAnhCu);
            System.out.println("Xóa hình ảnh cũ: " + isDeleteOldImages);
            
            Collection<Part> parts = request.getParts();
            List<Part> hinhAnhParts = new ArrayList<>();
            
            for (Part part : parts) {
                if (part.getName().equals("hinhAnh") && part.getSize() > 0) {
                    System.out.println("Tìm thấy hình ảnh: " + part.getSubmittedFileName() + ", kích thước: " + part.getSize());
                    hinhAnhParts.add(part);
                }
            }
            
            // Nếu có hình ảnh mới và người dùng chọn xóa hình ảnh cũ
            if (!hinhAnhParts.isEmpty() && isDeleteOldImages) {
                System.out.println("Xóa hình ảnh cũ và thêm hình ảnh mới");
                // Xóa hình ảnh cũ
                sanPhamService.xoaHinhAnhTheoSanPham(maSanPham);
                
                // Thêm hình ảnh mới
                sanPhamService.themHinhAnhChoSanPham(sanPham, hinhAnhParts);
            } 
            // Nếu có hình ảnh mới nhưng không xóa hình ảnh cũ
            else if (!hinhAnhParts.isEmpty()) {
                System.out.println("Thêm hình ảnh mới mà không xóa hình ảnh cũ");
                // Thêm hình ảnh mới
                sanPhamService.themHinhAnhChoSanPham(sanPham, hinhAnhParts);
            }
            
            if (result > 0) {
                System.out.println("Cập nhật sản phẩm thành công");
                request.getSession().setAttribute("successMessage", "Cập nhật sản phẩm thành công!");
                response.sendRedirect(request.getContextPath() + "/xu-ly-phan-trang");
            } else {
                System.out.println("Cập nhật sản phẩm thất bại");
                request.setAttribute("errorMessage", "Cập nhật sản phẩm thất bại!");
                
                // Lấy lại danh sách hình ảnh và thể loại
                List<HinhAnhSanPham> danhSachHinhAnh = hinhAnhDAO.getSanPhamsByMaSanPham(maSanPham);
                List<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
                
                request.setAttribute("sanpham", sanPham);
                request.setAttribute("danhSachHinhAnh", danhSachHinhAnh);
                request.setAttribute("danhSachTheLoai", danhSachTheLoai);
                
                request.getRequestDispatcher("/sanpham/chinhsuasanpham.jsp").forward(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi trong quá trình cập nhật sản phẩm: " + e.getMessage());
            request.setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            doGet(request, response);
        }
    }
}