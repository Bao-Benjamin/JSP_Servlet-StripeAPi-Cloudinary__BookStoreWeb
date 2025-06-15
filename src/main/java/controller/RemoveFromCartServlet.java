package controller;

import model.Cart;
import model.SanPham;
import database.SanPhamDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            response.sendRedirect("view-cart");
            return;
        }
        
        try {
            // Lấy ID sản phẩm cần xóa
            int productId = Integer.parseInt(request.getParameter("productId"));
            
            // Lấy thông tin sản phẩm
            SanPhamDAO spd = new SanPhamDAO();
            SanPham sanPham = spd.getSanPhamById(productId);
            
            if (sanPham == null) {
                response.sendRedirect("view-cart?error=product_not_found");
                return;
            }
            
            // Xóa sản phẩm khỏi giỏ hàng
            cart.removeItem(productId);
            
            // Thêm thông báo
            session.setAttribute("cartMessage", "Đã xóa sản phẩm \"" + sanPham.getTenSanPham() + "\" khỏi giỏ hàng.");
            
            // Chuyển hướng về trang giỏ hàng
            response.sendRedirect("view-cart");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("view-cart?error=invalid_format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("view-cart?error=system_error");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}