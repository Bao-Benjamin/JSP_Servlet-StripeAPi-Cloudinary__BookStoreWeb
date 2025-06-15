package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DonHangDAO;
import model.DonHang;

/**
 * Servlet implementation class xoaDonHang
 */
@WebServlet("/xoa-don-hang")
public class xoaDonHang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public xoaDonHang() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String maDonHang = request.getParameter("maDonHang");
	        if (maDonHang != null) {
	            try {
	              
	                DonHang donHang = new DonHang();
	                donHang.setMaDonHang(maDonHang);
	                DonHangDAO dhd = new DonHangDAO();
	                int ketQua = dhd.delete(donHang);
	               
	                if (ketQua > 0) {
	                    response.sendRedirect("index.jsp");
	                } else {
	                    response.sendRedirect("donhang/quanlydonhang.jsp");
	                }
	            } catch (NumberFormatException e) {
	                e.printStackTrace();
	                response.sendRedirect("QuanLyDonHang.jsp?message=invalid");
	            }
	        } else {
	            response.sendRedirect("QuanLyDonHang.jsp?message=missing");
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
