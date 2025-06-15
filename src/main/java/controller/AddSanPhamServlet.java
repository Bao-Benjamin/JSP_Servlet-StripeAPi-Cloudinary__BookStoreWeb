package controller;

import database.SanPhamDAO;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.SanPham;
import model.TheLoai;

@WebServlet("/AddSanPhamServlet")
public class AddSanPhamServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
    	System.out.println("da vao AddSanPhamServlet");
        // Lấy dữ liệu từ form
        String tenSanPham = request.getParameter("tenSanPham");
        String matacgia = request.getParameter("matacgia");
        String namxuatbanStr = request.getParameter("namxuatban");
        String gianhapStr = request.getParameter("gianhap");
        String giagocStr = request.getParameter("giagoc");
        String giabanStr = request.getParameter("giaban");
        String soluongStr = request.getParameter("soluong");
        String matheloai = request.getParameter("matheloai");
        String ngonngu = request.getParameter("ngonngu");
        String mota = request.getParameter("mota");
        String duongdananhStr = request.getParameter("duongdananh");
        String giagiamStr = request.getParameter("giagiam");
        System.out.println("dang o AddSanPhamServlet");
        // Chuyển đổi dữ liệu số
        int namxuatban = Integer.parseInt(namxuatbanStr);
        double gianhap = Double.parseDouble(gianhapStr);
        double giagoc = Double.parseDouble(giagocStr);
        double giaban = Double.parseDouble(giabanStr);
        int soluong = Integer.parseInt(soluongStr);
        int theloai = Integer.parseInt(matheloai);
        double giagiam = 0;
        if(giagiamStr != null && !giagiamStr.isEmpty()) {    	
        	 giagiam = Double.parseDouble(giagiamStr);
        }

        // Chuyển chuỗi đường dẫn ảnh thành List<String>
        // Giả sử các đường dẫn được nhập cách nhau bằng dấu phẩy, có thể có khoảng trắng
        List<String> duongDanAnh = Arrays.asList(duongdananhStr.split("\\s*,\\s*"));

        // Tạo đối tượng TheLoai
        TheLoai tl = new TheLoai(theloai, null);

        // Tạo đối tượng SanPham mới
        // Lưu ý: Constructor của SanPham cần phù hợp với thứ tự các tham số.
        SanPham sp = new SanPham(0, tenSanPham, matacgia, namxuatban, gianhap, giagoc, giaban, soluong, tl, ngonngu, mota,giagiam);
        System.out.println(sp);
        // Thêm sản phẩm vào DB
        SanPhamDAO dao = new SanPhamDAO();
        int result = dao.insert(sp);

        // Chuyển hướng hoặc thông báo kết quả
        if (result > 0) {
            // Nếu thêm thành công, chuyển sang trang hiển thị sản phẩm
            // Đưa đối tượng sản phẩm và danh sách ảnh vào request attribute
            request.setAttribute("product", sp);
//            request.setAttribute("duongDanAnh", sp.getDuongDanAnh());
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("sanpham/viewSanPham.jsp");
            dispatcher.forward(request, response);
        } else {
            response.getWriter().println("Thêm sản phẩm thất bại!");
        }
    }
}
