package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.SanPhamDAO;
import model.SanPham;

/**
 * Servlet implementation class LocSanPham
 */
@WebServlet("/loc-san-pham")
public class LocSanPham extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LocSanPham() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String tenTheLoai = request.getParameter("tenTheLoai");
		 
		     SanPhamDAO sanPhamDAO = new SanPhamDAO();
	        // Kiểm tra nếu tham số rỗng
	        if (tenTheLoai == null || tenTheLoai.trim().isEmpty()) {
	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tên thể loại không hợp lệ");
	            return;
	        }
	        
	        // Lấy danh sách sản phẩm theo thể loại
	        List<SanPham> sanpham = sanPhamDAO.selectByStringCategory(tenTheLoai);
	        
	        // Chuyển đổi danh sách sản phẩm thành JSON
	        Gson gson = new Gson();
	        String json = gson.toJson(sanpham);
	        
	        // Thiết lập kiểu nội dung JSON và gửi phản hồi
	        response.setContentType("application/json;charset=UTF-8");
	        response.getWriter().write(json);
	        request.setAttribute("sanPhams", sanpham);
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
