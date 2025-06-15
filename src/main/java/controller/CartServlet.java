package controller;

import model.Cart;
import model.CartItem;
import database.SanPhamDAO;
import model.SanPham;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private SanPhamDAO sanPhamDAO = new SanPhamDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("productId"));
        SanPham sp = new SanPham();
        sp.setMaSanPham(productId);

        if ("add".equals(action)) {
            SanPham sanPham = sanPhamDAO.selectById(sp);
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cart.addItem(sanPham, quantity);
        } else if ("update".equals(action)) {
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cart.updateItem(productId, quantity);
        } else if ("remove".equals(action)) {
            cart.removeItem(productId);
        }

        response.sendRedirect("view-cart");
    }
}
