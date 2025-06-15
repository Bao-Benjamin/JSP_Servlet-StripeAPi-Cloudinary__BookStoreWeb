package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import database.TheLoaiDAO;
import model.HinhAnhSanPham;
import model.SanPham;
import model.TheLoai;

@WebServlet("/loc-san-pham-theo-the-loai")
public class LocSanPhamTheoTheLoaiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Lấy tham số từ request
        String maTheLoaiStr = request.getParameter("maTheLoai");
        String pageStr = request.getParameter("page");
        
        // Xử lý tham số mã thể loại
        int maTheLoai = 0;
        if (maTheLoaiStr != null && !maTheLoaiStr.isEmpty()) {
            try {
                maTheLoai = Integer.parseInt(maTheLoaiStr);
            } catch (NumberFormatException e) {
                maTheLoai = 0;
            }
        }
        
        // Xử lý tham số trang
        int page = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        // Số sản phẩm trên mỗi trang
        int recordsPerPage = 9; // Hiển thị 9 sản phẩm mỗi trang (3x3 grid)
        
        // Lấy thông tin thể loại
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
        TheLoai theLoai = null;
        if (maTheLoai > 0) {
            TheLoai temp = new TheLoai();
            temp.setMaTheLoai(maTheLoai);
            theLoai = theLoaiDAO.selectById(temp);
        }
        
        // Lấy danh sách sản phẩm theo thể loại
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        List<SanPham> allSanPhams = new ArrayList<>();
        int totalRecords = 0;
        
        if (maTheLoai > 0 && theLoai != null) {
            // Tính toán phân trang cho sản phẩm theo thể loại
            int start = (page - 1) * recordsPerPage;
            allSanPhams = sanPhamDAO.getBooksByCategory(maTheLoai, start, recordsPerPage);
            totalRecords = sanPhamDAO.getTotalBooksByCategory(maTheLoai);
        } else {
            // Nếu không có mã thể loại hoặc thể loại không tồn tại, lấy tất cả sản phẩm
            int start = (page - 1) * recordsPerPage;
            allSanPhams = sanPhamDAO.getBooksByPage(start, recordsPerPage);
            totalRecords = sanPhamDAO.getTotalBooks();
        }
        
        // Tính toán tổng số trang
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        // Lấy hình ảnh cho mỗi sản phẩm
        Map<Integer, List<HinhAnhSanPham>> hinhAnhSanPhamMap = new HashMap<>();
        HinhAnhSanPhamDAO hinhAnhDAO = new HinhAnhSanPhamDAO();
        
        for (SanPham sanPham : allSanPhams) {
            List<HinhAnhSanPham> hinhAnhList = hinhAnhDAO.getSanPhamsByMaSanPham(sanPham.getMaSanPham());
            hinhAnhSanPhamMap.put(sanPham.getMaSanPham(), hinhAnhList);
        }
        
        // Lấy danh sách tất cả thể loại để hiển thị trong bộ lọc
        ArrayList<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
        
        // Đặt các thuộc tính vào request
        request.setAttribute("sanPhams", allSanPhams);
        request.setAttribute("hinhAnhSanPhamMap", hinhAnhSanPhamMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("maTheLoai", maTheLoai);
        request.setAttribute("theLoai", theLoai);
        request.setAttribute("danhSachTheLoai", danhSachTheLoai);
        request.setAttribute("productType", "category"); // Đánh dấu loại sản phẩm là category
        
        // Thiết lập tiêu đề trang
        if (theLoai != null) {
            request.setAttribute("pageTitle", "Sản phẩm thể loại: " + theLoai.getTenTheLoai());
        } else {
            request.setAttribute("pageTitle", "Tất cả sản phẩm");
        }
        
        // Hiển thị bộ lọc thể loại
        request.setAttribute("showCategoryFilter", true);
        
        // Forward đến trang JSP
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}