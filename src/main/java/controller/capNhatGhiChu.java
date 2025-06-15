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
 * Servlet implementation class capNhatGhiChu
 */
@WebServlet("/cap-nhat-ghi-chu")
public class capNhatGhiChu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public capNhatGhiChu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String maDonHang = request.getParameter("maDonHang");
        String ghiChuMoi = request.getParameter("ghiChuMoi");
        
        System.out.println("Cập nhật ghi chú - Mã đơn hàng: " + maDonHang);
        System.out.println("Ghi chú mới: " + ghiChuMoi);
        
        DonHang dh = new DonHang();
        dh.setGhiChu(ghiChuMoi);
        dh.setMaDonHang(maDonHang);
        
        DonHangDAO donHangDAO = new DonHangDAO();
        int kq = donHangDAO.updateGhiChu(dh);
        
        response.getWriter().write("{\"status\": \"" + (kq > 0 ? "success" : "error") + "\"}");
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
