package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import model.HinhAnhSanPham;
import model.SanPham;

@WebServlet("/chiTietSanPham")
public class ChiTietSanPhamServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public ChiTietSanPhamServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        
        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("index.jsp?error=missingid");
            return;
        }
        
        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("index.jsp?error=invalidid");
            return;
        }
        
        // Lấy thông tin sản phẩm
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        SanPham sanPham = sanPhamDAO.getSanPhamById(id);
        
        if (sanPham == null) {
            response.sendRedirect("index.jsp?error=notfound");
            return;
        }
        
        // Lấy danh sách hình ảnh của sản phẩm
        HinhAnhSanPhamDAO hinhAnhDAO = new HinhAnhSanPhamDAO();
        List<HinhAnhSanPham> hinhAnhList = hinhAnhDAO.getSanPhamsByMaSanPham(id);
        
        // Đặt thuộc tính cho request
        request.setAttribute("product", sanPham);
        request.setAttribute("productImages", hinhAnhList);
        
        // Chuyển hướng đến trang chi tiết sản phẩm
        request.getRequestDispatcher("sanpham/viewSanPham.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}