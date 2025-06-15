package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.KhachHangDAO;
import model.KhachHang;

/**
 * Servlet implementation class QuanLyKhachHang
 */
@WebServlet("/quan-ly-khach-hang")
public class QuanLyKhachHang extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int ITEMS_PER_PAGE = 10; // Số khách hàng trên mỗi trang
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuanLyKhachHang() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		KhachHangDAO khachHangDAO = new KhachHangDAO();
        ArrayList<KhachHang> danhSachKhachHang = khachHangDAO.selectAll();
        
        // Lấy các tham số tìm kiếm và lọc
        String search = request.getParameter("search");
        String role = request.getParameter("role");
        
        // Lọc danh sách khách hàng
        List<KhachHang> filteredList = new ArrayList();
        for (KhachHang kh : danhSachKhachHang) {
            boolean match = true;
            
            // Lọc theo tìm kiếm
            if (search != null && !search.isEmpty()) {
                if (!kh.getHoVaTen().toLowerCase().contains(search.toLowerCase()) && 
                    !kh.getEmail().toLowerCase().contains(search.toLowerCase()) &&
                    !kh.getSoDienThoai().contains(search)) {
                    match = false;
                }
            }
            
            // Lọc theo trạng thái (nếu có)
            if (role != null && !role.isEmpty()) {
                if (!kh.getVaiTro().equals(role)) {
                    match = false;
                }
            }
            
            if (match) {
                filteredList.add(kh);
            }
        }
        
        // Xử lý phân trang
        int totalItems = filteredList.size();
        int totalPages = (int) Math.ceil((double) totalItems / ITEMS_PER_PAGE);
        
        // Lấy trang hiện tại
        int currentPage = 1;
        String pageParam = request.getParameter("page");
        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) {
                    currentPage = 1;
                } else if (currentPage > totalPages && totalPages > 0) {
                    currentPage = totalPages;
                }
            } catch (NumberFormatException e) {
                // Giữ nguyên trang mặc định là 1
            }
        }
        
        // Tính chỉ số bắt đầu và kết thúc cho trang hiện tại
        int startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, totalItems);
        
        // Lấy danh sách khách hàng cho trang hiện tại
        List<KhachHang> currentPageItems = new ArrayList<>();
        if (startIndex < totalItems) {
            currentPageItems = filteredList.subList(startIndex, endIndex);
        }
        
        // Đặt các thuộc tính vào request
        request.setAttribute("danhSachKhachHang", currentPageItems);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("search", search);
        request.setAttribute("role", role);
        
        request.getRequestDispatcher("khachhang/quanlykhachhang.jsp").forward(request, response);
    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
