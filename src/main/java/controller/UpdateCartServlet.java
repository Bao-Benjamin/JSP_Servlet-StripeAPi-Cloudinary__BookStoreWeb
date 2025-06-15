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

@WebServlet("/update-cart")
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lấy giỏ hàng từ session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            response.sendRedirect("view-cart");
            return;
        }
        
        try {
            // Lấy thông tin từ form
            int productId = Integer.parseInt(request.getParameter("productId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Kiểm tra số lượng hợp lệ
            if (quantity <= 0) {
                response.sendRedirect("view-cart?error=invalid_quantity");
                return;
            }
            
            // Lấy thông tin sản phẩm
            SanPhamDAO spd = new SanPhamDAO();
            SanPham sanPham = spd.getSanPhamById(productId);
            
            if (sanPham == null) {
                response.sendRedirect("view-cart?error=product_not_found");
                return;
            }
            
            // Kiểm tra số lượng tồn kho
            if (sanPham.getSoLuong() < quantity) {
                session.setAttribute("cartMessage", "Số lượng yêu cầu vượt quá số lượng tồn kho của sản phẩm \"" + sanPham.getTenSanPham() + "\".");
                response.sendRedirect("view-cart");
                return;
            }
            
            // Cập nhật số lượng trong giỏ hàng
            cart.updateItem(productId, quantity);
            
            // Thêm thông báo
            session.setAttribute("cartMessage", "Đã cập nhật số lượng sản phẩm \"" + sanPham.getTenSanPham() + "\" thành " + quantity + ".");
            
            // Chuyển hướng về trang giỏ hàng
            response.sendRedirect("view-cart");
            
        } catch (NumberFormatException e) {
            response.sendRedirect("view-cart?error=invalid_format");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("view-cart?error=system_error");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}