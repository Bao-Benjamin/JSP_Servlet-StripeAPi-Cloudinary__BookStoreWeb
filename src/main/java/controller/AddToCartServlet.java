package controller;

import model.Cart;
import model.SanPham;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.SanPhamDAO;

import java.io.IOException;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Lấy giỏ hàng từ session (nếu chưa có thì tạo mới)
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        try {
            // Lấy thông tin sản phẩm từ request parameters
            String productIdParam = request.getParameter("productId");
            String quantityParam = request.getParameter("quantity");
            
            // Kiểm tra xem các tham số có tồn tại không
            if (productIdParam == null || quantityParam == null) {
                response.sendRedirect("xu-ly-phan-trang?error=missing_parameters");
                return;
            }
            
            int productId = Integer.parseInt(productIdParam);
            int quantity = Integer.parseInt(quantityParam);
            
            // Kiểm tra số lượng hợp lệ
            if (quantity <= 0) {
                response.sendRedirect("xu-ly-phan-trang?error=invalid_quantity");
                return;
            }
            
            // Lấy thông tin sản phẩm từ cơ sở dữ liệu
            SanPhamDAO spd = new SanPhamDAO();
            SanPham sanPham = spd.getSanPhamById(productId);
            
            // Kiểm tra xem sản phẩm có tồn tại không
            if (sanPham == null) {
                response.sendRedirect("xu-ly-phan-trang?error=product_not_found");
                return;
            }
            
            // Kiểm tra số lượng tồn kho
            if (sanPham.getSoLuong() < quantity) {
                response.sendRedirect("chiTietSanPham?id=" + productId + "&error=insufficient_stock");
                return;
            }
            
            // Thêm vào giỏ hàng
            cart.addItem(sanPham, quantity);
            
            // Lưu thông báo thành công vào session
            session.setAttribute("cartMessage", "Đã thêm " + quantity + " sản phẩm \"" + sanPham.getTenSanPham() + "\" vào giỏ hàng.");
            
            // Xác định trang để chuyển hướng
            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty() && referer.contains("chiTietSanPham")) {
                // Nếu đang ở trang chi tiết sản phẩm, quay lại trang đó với thông báo thành công
                response.sendRedirect("chiTietSanPham?id=" + productId + "&success=added_to_cart");
            } else {
                // Nếu không, chuyển hướng đến trang giỏ hàng
                response.sendRedirect("view-cart");
            }
            
        } catch (NumberFormatException e) {
            // Xử lý lỗi định dạng số
            response.sendRedirect("xu-ly-phan-trang?error=invalid_format");
        } catch (Exception e) {
            // Xử lý các lỗi khác
            e.printStackTrace();
            response.sendRedirect("xu-ly-phan-trang?error=system_error");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}