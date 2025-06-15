package controller;

import model.Cart;
import model.CartItem;
import model.HinhAnhSanPham;
import database.HinhAnhSanPhamDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/view-cart")
public class ViewCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        // Lấy hình ảnh cho mỗi sản phẩm trong giỏ hàng
        HinhAnhSanPhamDAO hinhAnhDAO = new HinhAnhSanPhamDAO();
        Map<Integer, List<HinhAnhSanPham>> productImagesMap = new HashMap<>();
        
        for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
            int productId = entry.getKey();
            List<HinhAnhSanPham> images = hinhAnhDAO.getSanPhamsByMaSanPham(productId);
            productImagesMap.put(productId, images);
        }
        
        request.setAttribute("cart", cart);
        request.setAttribute("productImagesMap", productImagesMap);
        request.getRequestDispatcher("/sanpham/cart.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
