package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.SanPhamDAO;
import database.TheLoaiDAO;
import model.SanPham;
import model.TheLoai;

@WebServlet("/quan-ly-san-pham")
public class QuanLySanPham extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        // Lấy tham số tìm kiếm
        String tenSanPham = request.getParameter("tenSanPham");
        String maTheLoaiStr = request.getParameter("maTheLoai");
        String pageStr = request.getParameter("page");
        
        // Xử lý giá trị mặc định
        if (tenSanPham == null) tenSanPham = "";
        
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
        int recordsPerPage = 10;
        int start = (page - 1) * recordsPerPage;
        
        // Lấy danh sách sản phẩm từ DAO
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        ArrayList<SanPham> danhSachSanPham;
        
        if (tenSanPham != null && !tenSanPham.isEmpty()) {
            // Tìm kiếm theo tên
            danhSachSanPham = (ArrayList<SanPham>) sanPhamDAO.searchSanPham(tenSanPham);
        } else if (maTheLoaiStr != null && !maTheLoaiStr.isEmpty()) {
            // Lọc theo thể loại
            try {
                int maTheLoai = Integer.parseInt(maTheLoaiStr);
                TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
                TheLoai theLoai = theLoaiDAO.selectById(new TheLoai(maTheLoai, null));
                if (theLoai != null) {
                    danhSachSanPham = (ArrayList<SanPham>) sanPhamDAO.selectByStringCategory(theLoai.getTenTheLoai());
                } else {
                    danhSachSanPham = sanPhamDAO.selectAll();
                }
            } catch (NumberFormatException e) {
                danhSachSanPham = sanPhamDAO.selectAll();
            }
        } else {
            // Lấy tất cả sản phẩm với phân trang
            danhSachSanPham = (ArrayList<SanPham>) sanPhamDAO.getBooksByPage(start, recordsPerPage);
        }
        
        // Lấy tổng số sản phẩm để tính số trang
        int totalRecords = sanPhamDAO.getTotalBooks();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        // Lấy danh sách thể loại
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
        ArrayList<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
        
        // Đặt các thuộc tính vào request
        request.setAttribute("danhSachSanPham", danhSachSanPham);
        request.setAttribute("danhSachTheLoai", danhSachTheLoai);
        request.setAttribute("tenSanPham", tenSanPham);
        request.setAttribute("maTheLoai", maTheLoaiStr);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        
        // Forward đến trang JSP
        request.getRequestDispatcher("sanpham/quanlysanpham.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}