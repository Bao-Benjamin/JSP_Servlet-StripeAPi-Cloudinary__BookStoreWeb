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
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;

import database.ChiTietDonHangDAO;
import database.DonHangDAO;
import model.ChiTietDonHang;
import model.DonHang;
import model.KhachHang;

/**
 * Servlet implementation class hienThiDonHangDaMua
 */
@WebServlet("/hien-thi-don-hang-da-mua")
public class hienThiDonHangDaMua extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hienThiDonHangDaMua() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String baoLoi = "";
        ArrayList<DonHang> donHangList = new ArrayList<>();
        Map<String, List<ChiTietDonHang>> chiTietDonHangMap = new HashMap();
        ChiTietDonHangDAO ctdhDAO = new ChiTietDonHangDAO();
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("khachHang");
        KhachHang khachHang = null;

        if (obj != null)
            khachHang = (KhachHang) obj;

        if (khachHang == null) {
            baoLoi = "Bạn chưa đăng nhập vào hệ thống!";
        } else {
            String tenKH = khachHang.getTenDangNhap();
            DonHangDAO dhd = new DonHangDAO();
            donHangList = dhd.selectByUserName(tenKH);

            for (DonHang donHang : donHangList) {
            	String madh = donHang.getMaDonHang();
                List<ChiTietDonHang> chiTietList = ctdhDAO.selectByMaDonHang(madh);
                chiTietDonHangMap.put(madh, chiTietList);  // ✅ Thêm vào danh sách thay vì ghi đè
            }
        }

        request.setAttribute("baoLoi", baoLoi);
        request.setAttribute("donhang", donHangList);
        request.setAttribute("chiTietDonHangMap", chiTietDonHangMap);

        RequestDispatcher rd = getServletContext().getRequestDispatcher("/donhang/danhSachDonHang.jsp");
        rd.forward(request, response);
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
