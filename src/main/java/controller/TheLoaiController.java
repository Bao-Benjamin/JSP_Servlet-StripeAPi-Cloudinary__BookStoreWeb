package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.TheLoaiDAO;
import model.TheLoai;

/**
 * Servlet implementation class TheLoaiController
 */
@WebServlet("/the-loai")
public class TheLoaiController extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    public TheLoaiController() {
        super();
    }

    // Phân loại các hành động dựa vào tham số action truyền lên từ request
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) {
            action = "hienThi";
        }
        
        switch (action) {
            case "them":
                themTheLoai(request, response);
                break;
            case "sua":
                suaTheLoai(request, response);
                break;
            case "xoa":
                xoaTheLoai(request, response);
                break;
            case "hienThi":
            default:
                hienThiTheLoai(request, response);
                break;
        }
    }

    // Cho phép xử lý POST bằng cách gọi lại doGet
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    
    // Phương thức thêm mới thể loại
    private void themTheLoai(HttpServletRequest request, HttpServletResponse response) {
		 try {
			String tenTheLoai = request.getParameter("tentheloai");
			 TheLoai newTheLoai = new TheLoai(0, tenTheLoai);
			 TheLoaiDAO tld = new TheLoaiDAO();
			    tld.insert(newTheLoai);
			    response.sendRedirect("the-loai");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void suaTheLoai(HttpServletRequest request, HttpServletResponse response) {
		try {
			String maTheLoai = request.getParameter("matheloai");
			String tenTheLoai = request.getParameter("tentheloai");
			 int id = Integer.parseInt(maTheLoai);
			 TheLoai tl = new TheLoai();
			 tl.setMaTheLoai(id);
			 tl.setTenTheLoai(tenTheLoai);
			 TheLoaiDAO tld = new TheLoaiDAO();
			 tld.update(tl);
//			 request.getRequestDispatcher("/the-loai").forward(request, response);
			 response.sendRedirect("the-loai");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void xoaTheLoai(HttpServletRequest request, HttpServletResponse response) {
		try {
			String maTheLoai = request.getParameter("matheloai");
			int id = Integer.parseInt(maTheLoai);
			TheLoai tl = new TheLoai();
			tl.setMaTheLoai(id);
			TheLoaiDAO tld = new TheLoaiDAO();
			tld.delete(tl);
//			request.getRequestDispatcher("/the-loai").forward(request, response);
			response.sendRedirect("the-loai");
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void hienThiTheLoai(HttpServletRequest request, HttpServletResponse response) {
		try {
			TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
			List<TheLoai> listTheLoai = theLoaiDAO.selectAll();
			request.setAttribute("listTheLoai", listTheLoai);
			request.getRequestDispatcher("/theloai/theloai.jsp").forward(request, response);
//			response.sendRedirect("the-loai");
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
