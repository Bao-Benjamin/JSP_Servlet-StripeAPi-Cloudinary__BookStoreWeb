package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
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

@WebServlet("/xu-ly-phan-trang")
public class XuLyPhanTrang extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public XuLyPhanTrang() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = 1;
        int recordsPerPage = 9; // Số sách hiển thị mỗi trang

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        int start = (page - 1) * recordsPerPage;
        SanPhamDAO spd = new SanPhamDAO();
        HinhAnhSanPhamDAO haspDAO = new HinhAnhSanPhamDAO();
        Map<Integer, List<HinhAnhSanPham>> hinhAnhSanPhamMap = new HashMap<>();
        List<SanPham> books = spd.getBooksByPage(start, recordsPerPage);
        for (SanPham sanPham : books) {
            int maSanPham = sanPham.getMaSanPham();
            
            List<HinhAnhSanPham> haspList = haspDAO.getSanPhamsByMaSanPham(maSanPham);
            hinhAnhSanPhamMap.put(maSanPham, haspList);
        }
        int totalBooks = spd.getTotalBooks();
        int totalPages = (int) Math.ceil((double) totalBooks / recordsPerPage);
        
        // Lấy danh sách thể loại để hiển thị trong bộ lọc
        TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
        ArrayList<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
        
        request.setAttribute("sanPhams", books);
        request.setAttribute("hinhAnhSanPhamMap", hinhAnhSanPhamMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("productType", "home");
        
        // Thêm thuộc tính cho bộ lọc thể loại
        request.setAttribute("danhSachTheLoai", danhSachTheLoai);
//        request.setAttribute("showCategoryFilter", true); vì chúng ta sẽ sử dụng JavaScript để hiển thị/ẩn
        request.setAttribute("maTheLoai", 0); // Mặc định là "Tất cả"

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}