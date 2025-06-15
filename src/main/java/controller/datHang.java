package controller;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DonHangDAO;
import model.DonHang;

/**
 * Servlet implementation class datHang
 */
@WebServlet("/dat-hang")
public class datHang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public datHang() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tenKhachHang = request.getParameter("tenKhachHang");
		String soDienThoai = request.getParameter("soDienThoai");
		String diaChiNhanHang = request.getParameter("diaChiNhanHang");
		String hinhThucThanhToan = request.getParameter("hinhThucThanhToan");
		String ghiChu = request.getParameter("ghiChu");
		
		request.setAttribute("tenKhachHang", tenKhachHang);
		request.setAttribute("soDienThoai", soDienThoai);
		request.setAttribute("diaChiNhanHang", diaChiNhanHang);
		request.setAttribute("hinhThucThanhToan", hinhThucThanhToan);
		request.setAttribute(ghiChu, hinhThucThanhToan);
		
		Random rd = new Random();
		 String maKhachHang = System.currentTimeMillis() + rd.nextInt(1000)+"";
		
		
		DonHang dh = new DonHang(maKhachHang, tenKhachHang, soDienThoai, diaChiNhanHang, hinhThucThanhToan,  Date.valueOf(LocalDate.now()),ghiChu);
		
		DonHangDAO dhd = new DonHangDAO();
		dhd.insert(dh);
		System.out.println("nagy dat hang: "+ dh.getNgayDatHang());
		System.out.println(dh);
		System.out.println("đã chay hàm insert");
		
		 RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
	     dispatcher.forward(request, response);
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
