package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.SanPhamDAO;
import model.SanPham;

/**
 * Servlet implementation class SearchSuggestions
 */
@WebServlet(name = "DeXuatTimKiem", urlPatterns = { "/DeXuatTimKiem" })
public class SearchSuggestions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchSuggestions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String query = request.getParameter("query");
		 SanPhamDAO spd = new SanPhamDAO();
	        List<SanPham> suggestions = spd.searchSanPham(query);
	        StringBuilder html = new StringBuilder();
	        for (SanPham sp : suggestions) {
	            html.append("<div>").append(sp.getTenSanPham()).append("</div>");
	        }
	        response.setContentType("text/html");
	        response.getWriter().write(html.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
