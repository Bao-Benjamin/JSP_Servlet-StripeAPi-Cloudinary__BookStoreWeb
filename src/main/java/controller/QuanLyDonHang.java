package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.DonHangDAO;
import model.DonHang;

@WebServlet("/quan-ly-don-hang")
public class QuanLyDonHang extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final int ITEMS_PER_PAGE = 10; // Số đơn hàng trên mỗi trang
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        DonHangDAO donHangDAO = new DonHangDAO();
//        ArrayList<DonHang> danhSachDonHang = donHangDAO.selectAll();
//
//        request.setAttribute("danhSachDonHang", danhSachDonHang);
//        request.getRequestDispatcher("donhang/quanlydonhang.jsp").forward(request, response);
    	String msg = (String)request.getAttribute("message");
//    	request.setAttribute("msg", msg);
    	DonHangDAO donHangDAO = new DonHangDAO();
        ArrayList<DonHang> danhSachDonHang = donHangDAO.selectAll();
        
        // Lấy các tham số tìm kiếm và lọc
        String search = request.getParameter("search");
        String status = request.getParameter("status");
        String payment = request.getParameter("payment");
        
        // Lọc danh sách đơn hàng
        List<DonHang> filteredList = new ArrayList<>();
        for (DonHang dh : danhSachDonHang) {
            boolean match = true;
            
            // Lọc theo tìm kiếm
            if (search != null && !search.isEmpty()) {
                if (!dh.getMaDonHang().contains(search) && 
                    !dh.getTenKhachHang().toLowerCase().contains(search.toLowerCase())) {
                    match = false;
                }
            }
            
            // Lọc theo trạng thái
            if (status != null && !status.isEmpty()) {
                if (!dh.getTrangThai().equals(status)) {
                    match = false;
                }
            }
            
            // Lọc theo phương thức thanh toán
            if (payment != null && !payment.isEmpty()) {
                if (payment.equals("Ship COD") && !dh.getHinhThucThanhToan().equals("Ship COD")) {
                    match = false;
                } else if (payment.equals("Credit Card") && !dh.getHinhThucThanhToan().equals("Credit Card")) {
                    match = false;
                }
            }
            
            if (match) {
                filteredList.add(dh);
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
        
        // Lấy danh sách đơn hàng cho trang hiện tại
        List<DonHang> currentPageItems = new ArrayList<>();
        if (startIndex < totalItems) {
            currentPageItems = filteredList.subList(startIndex, endIndex);
        }
        
        // Đặt các thuộc tính vào request
        request.setAttribute("danhSachDonHang", currentPageItems);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("search", search);
        request.setAttribute("status", status);
        request.setAttribute("payment", payment);
        
        request.getRequestDispatcher("donhang/quanlydonhang.jsp").forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request,response);
    }

}
