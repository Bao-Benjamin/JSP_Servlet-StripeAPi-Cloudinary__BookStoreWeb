package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ChiTietDonHangDAO;
import database.DonHangDAO;
import model.ChiTietDonHang;
import model.DonHang;

/**
 * Servlet implementation class DeleteOrder
 */
@WebServlet("/deleteOrder")
public class DeleteOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String orderId = request.getParameter("id");
        
        if (orderId != null) {
            // Xóa chi tiết đơn hàng trước
            ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
            ArrayList<ChiTietDonHang> chiTietList = chiTietDonHangDAO.selectByMaDonHang(orderId);
            
            for (ChiTietDonHang chiTiet : chiTietList) {
                chiTietDonHangDAO.delete(chiTiet);
            }
            
            // Sau đó xóa đơn hàng
            DonHangDAO donHangDAO = new DonHangDAO();
            DonHang donHang = new DonHang();
            donHang.setMaDonHang(orderId);
            donHang = donHangDAO.selectById(donHang);
            
            if (donHang != null) {
                donHangDAO.delete(donHang);
//                response.sendRedirect("quan-ly-don-hang?message=Xóa đơn hàng thành công");
                String message = URLEncoder.encode("Xóa đơn hàng thành công", "UTF-8");
                response.sendRedirect("quan-ly-don-hang?message=" + message);
            } else {
//                response.sendRedirect("quan-ly-don-hang?message=Không tìm thấy đơn hàng");
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
