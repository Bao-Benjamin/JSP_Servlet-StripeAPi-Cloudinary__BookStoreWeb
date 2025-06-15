package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import model.SanPham;
import service.SanPhamService;

@WebServlet("/xoa-san-pham")
public class xoaSanPham extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SanPhamService sanPhamService;
       
    public xoaSanPham() {
        super();
        sanPhamService = new SanPhamService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("---chạy vào xoaSanPham servlet");
        try {
            String maSanPhamStr = request.getParameter("maSanPham");
            System.out.println("msp String trong  xoaSanPham servlet "+maSanPhamStr);
            if (maSanPhamStr == null || maSanPhamStr.trim().isEmpty()) {
                request.getSession().setAttribute("errorMessage", "Mã sản phẩm không hợp lệ!");
                System.out.println("mã sp ko hợp lệ");
                response.sendRedirect(request.getContextPath() + "xu-ly-phan-trang");
                return;
            }
            
            int maSanPham = Integer.parseInt(maSanPhamStr);
            System.out.println("msp trong  xoaSanPham servlet "+maSanPham);
            // Xóa hình ảnh sản phẩm trước
            HinhAnhSanPhamDAO hinhAnhDAO = new HinhAnhSanPhamDAO();
            hinhAnhDAO.deleteByProductId(maSanPham);
            
            // Xóa sản phẩm
            SanPhamDAO spDAO = new SanPhamDAO();
            SanPham sp = spDAO.selectById(maSanPham);
            
            if (sp == null) {
                request.getSession().setAttribute("errorMessage", "Không tìm thấy sản phẩm!");
                response.sendRedirect(request.getContextPath() + "/admin/quan-ly-san-pham");
                return;
            }
            
            int result = spDAO.delete(sp);
            
            if (result > 0) {
                request.getSession().setAttribute("successMessage", "Xóa sản phẩm thành công!");
            } else {
                request.getSession().setAttribute("errorMessage", "Xóa sản phẩm thất bại!");
            }
            
            response.sendRedirect(request.getContextPath() + "/quan-ly-san-pham");
            
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("errorMessage", "Mã sản phẩm không hợp lệ!");
            response.sendRedirect(request.getContextPath() + "/admin/quan-ly-san-pham");
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/quan-ly-san-pham");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}