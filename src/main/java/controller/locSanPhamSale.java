package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import model.HinhAnhSanPham;
import model.SanPham;

/**
 * Servlet implementation class locSanPhamSale
 */
@WebServlet("/loc-san-pham-sale")
public class locSanPhamSale extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public locSanPhamSale() {
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
        
        // Lấy tham số trang từ request
        String pageStr = request.getParameter("page");
        int page = 1; // Mặc định là trang 1
        
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        // Số sản phẩm trên mỗi trang
        int recordsPerPage = 8; // Bạn có thể điều chỉnh số này
        
        // Tính vị trí bắt đầu
        int start = (page - 1) * recordsPerPage;
        
        // Lấy danh sách sản phẩm sale với phân trang
        SanPhamDAO spd = new SanPhamDAO();
        
        // Lấy tổng số sản phẩm sale để tính số trang
        int totalRecords = spd.countSanPhamSale();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        // Lấy danh sách sản phẩm sale cho trang hiện tại
        ArrayList<SanPham> spList = spd.selectSanPhamSaleWithPagination(start, recordsPerPage);
        HinhAnhSanPhamDAO haspDAO = new HinhAnhSanPhamDAO();
        Map<Integer, List<HinhAnhSanPham>> hinhAnhSanPhamMap = new HashMap<>();
        for (SanPham sanPham : spList) {
			int maSanPham = sanPham.getMaSanPham();
			
			List<HinhAnhSanPham> haspList = haspDAO.getSanPhamsByMaSanPham(maSanPham);
			hinhAnhSanPhamMap.put(maSanPham, haspList);
		}
        // Đặt các thuộc tính vào request
        request.setAttribute("sanPhams", spList);
        request.setAttribute("currentPage", page);
        request.setAttribute("hinhAnhSanPhamMap", hinhAnhSanPhamMap);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("productType", "sale");
        
        // Forward đến trang JSP
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
