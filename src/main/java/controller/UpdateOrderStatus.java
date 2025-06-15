package controller;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DonHangDAO;
import model.DonHang;

/**
 * Servlet implementation class UpdateOrderStatus
 */
@WebServlet("/updateOrderStatus")
public class UpdateOrderStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateOrderStatus() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String orderId = request.getParameter("orderId");
        String status = request.getParameter("status");
        
        if (orderId != null && status != null) {
            DonHangDAO donHangDAO = new DonHangDAO();
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(orderId);
            donHang = donHangDAO.selectById(donHang);
            
            if (donHang != null) {
                donHang.setTrangThai(status);
                donHangDAO.update(donHang);
                
                // Redirect with success message
//                response.sendRedirect("quan-ly-don-hang?message=Cập nhật trạng thái thành công");
                String message = URLEncoder.encode("Cập nhật trạng thái thành công", "UTF-8");
                response.sendRedirect("quan-ly-don-hang?message=" + message);
            } else {
            	String message = URLEncoder.encode("Không tìm thấy đơn hàng", "UTF-8");
            	response.sendRedirect("quan-ly-don-hang?message=" + message);
            }
        } else {
//            response.sendRedirect("quan-ly-don-hang?message=Dữ liệu không hợp lệ");
        	String message = URLEncoder.encode("Dữ liệu không hợp lệ", "UTF-8");
        	response.sendRedirect("quan-ly-don-hang?message=" + message);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
