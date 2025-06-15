package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.SanPhamDAO;
import database.TheLoaiDAO;
import model.SanPham;
import model.TheLoai;

/**
 * Servlet implementation class layDanhSachTheLoai
 */
@WebServlet("/lay-danh-sach-the-loai")
public class layDanhSachTheLoai extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public layDanhSachTheLoai() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		
		 TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
	        ArrayList<TheLoai> danhSachTheLoai = theLoaiDAO.selectAll();
	        if (danhSachTheLoai == null) {
	            danhSachTheLoai = new ArrayList<>(); // Tạo danh sách rỗng tránh lỗi NullPointerException
	            System.out.println("danhSachTheLoai dang null");
	        }
	        System.out.println("danhSachTheLoai da select xong");
	        // Đặt danh sách thể loại vào request attribute
	        request.setAttribute("danhSachTheLoai", danhSachTheLoai);
	        
	        // Chuyển tiếp (forward) đến trang JSP form
	        String page = request.getParameter("page");
//	        String page = "filter";
	        if ("add".equals(page)) {
	        	 System.out.println("danhSachTheLoai dang ADD");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/sanpham/themSanPham.jsp");
	            dispatcher.forward(request, response);
	        } else if ("edit".equals(page)) {
	        	String idStr = request.getParameter("idSanPham");
	        	int id =0;
	        	if (idStr != null && idStr.matches("\\d+")) {
	        	    id = Integer.parseInt(idStr);
	        	} else {
	        		System.out.println("idStr dang null");
	        	    response.sendRedirect("index.jsp");
	        	    return;
	        	}
	        	 SanPham sp = new SanPham();
	        	 sp.setMaSanPham(id);
	        	 SanPhamDAO spd = new SanPhamDAO();
	        	 SanPham spByID= spd.selectById(sp);
	        	 System.out.println("sanphambyid trong laydanhsachtheloai.java: "+spByID);
	        	 request.setAttribute("sanpham",spByID );
	            RequestDispatcher dispatcher = request.getRequestDispatcher("/sanpham/chinhsuasanpham.jsp");
	            dispatcher.forward(request, response);
	        } else if("filter".equals(page)){
	        	 RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		            dispatcher.forward(request, response);
	        }else{
	            response.sendRedirect("index.jsp"); 
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
