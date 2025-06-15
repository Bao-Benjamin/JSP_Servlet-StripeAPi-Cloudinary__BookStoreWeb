package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.ChiTietDonHangDAO;
import database.DonHangDAO;
import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import model.ChiTietDonHang;
import model.DonHang;
import model.HinhAnhSanPham;
import model.SanPham;

@WebServlet("/san-pham-ban-chay")
public class SanPhamBanChayServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Thiết lập encoding
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        // Lấy tham số từ request
        String pageStr = request.getParameter("page");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        // Xử lý tham số trang
        int page = 1;
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                page = Integer.parseInt(pageStr);
                if (page < 1) page = 1;
            } catch (NumberFormatException e) {
                page = 1;
            }
        }
        
        // Xử lý tham số ngày
        Date startDate = null;
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
            if (startDateStr != null && !startDateStr.isEmpty()) {
                java.util.Date parsedDate = sdf.parse(startDateStr);
                startDate = new Date(parsedDate.getTime());
            } else {
                // Mặc định là 30 ngày trước
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DAY_OF_MONTH, -30);
                startDate = new Date(cal.getTimeInMillis());
                startDateStr = sdf.format(startDate);
            }
            
            if (endDateStr != null && !endDateStr.isEmpty()) {
                java.util.Date parsedDate = sdf.parse(endDateStr);
                endDate = new Date(parsedDate.getTime());
            } else {
                // Mặc định là ngày hiện tại
                endDate = new Date(System.currentTimeMillis());
                endDateStr = sdf.format(endDate);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            // Nếu có lỗi, sử dụng giá trị mặc định
            Calendar cal = Calendar.getInstance();
            endDate = new Date(cal.getTimeInMillis());
            cal.add(Calendar.DAY_OF_MONTH, -30);
            startDate = new Date(cal.getTimeInMillis());
            
            startDateStr = sdf.format(startDate);
            endDateStr = sdf.format(endDate);
        }
        
        // Số sản phẩm trên mỗi trang
        int recordsPerPage = 9; // Hiển thị 9 sản phẩm mỗi trang (3x3 grid)
        
        // Lấy danh sách sản phẩm bán chạy
        List<Map<String, Object>> topSanPhamInfo = getTopSanPhamBanChay(startDate, endDate);
        
        // Tính toán phân trang
        int totalRecords = topSanPhamInfo.size();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        // Lấy danh sách sản phẩm cho trang hiện tại
        int startIndex = (page - 1) * recordsPerPage;
        int endIndex = Math.min(startIndex + recordsPerPage, totalRecords);
        
        List<Map<String, Object>> currentPageItems = new ArrayList<>();
        if (startIndex < totalRecords) {
            currentPageItems = topSanPhamInfo.subList(startIndex, endIndex);
        }
        
        // Lấy thông tin chi tiết của sản phẩm
        List<SanPham> danhSachSanPham = new ArrayList<>();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        
        for (Map<String, Object> item : currentPageItems) {
            int maSanPham = (int) item.get("maSanPham");
            SanPham sanPham = sanPhamDAO.selectById(maSanPham);
            if (sanPham != null) {
                // Thêm thông tin số lượng bán vào đối tượng sản phẩm (nếu cần)
                danhSachSanPham.add(sanPham);
            }
        }
        
        // Lấy hình ảnh cho mỗi sản phẩm
        Map<Integer, List<HinhAnhSanPham>> hinhAnhSanPhamMap = new HashMap<>();
        HinhAnhSanPhamDAO hinhAnhDAO = new HinhAnhSanPhamDAO();
        
        for (SanPham sanPham : danhSachSanPham) {
            List<HinhAnhSanPham> hinhAnhList = hinhAnhDAO.getSanPhamsByMaSanPham(sanPham.getMaSanPham());
            hinhAnhSanPhamMap.put(sanPham.getMaSanPham(), hinhAnhList);
        }
        
        // Đặt các thuộc tính vào request
        request.setAttribute("sanPhams", danhSachSanPham);
        request.setAttribute("hinhAnhSanPhamMap", hinhAnhSanPhamMap);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);
        request.setAttribute("productType", "bestseller"); // Đánh dấu loại sản phẩm là bestseller
        request.setAttribute("pageTitle", "Sản phẩm bán chạy"); // Tiêu đề trang
        
        // Thêm thông tin lọc ngày
        request.setAttribute("showDateFilter", true);
        
        // Forward đến trang JSP
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
    
    // Phương thức lấy top sản phẩm bán chạy (tận dụng logic từ ThongKeServlet)
    private List<Map<String, Object>> getTopSanPhamBanChay(Date startDate, Date endDate) {
        ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
        DonHangDAO donHangDAO = new DonHangDAO();
        
        // Lấy tất cả đơn hàng trong khoảng thời gian
        List<DonHang> danhSachDonHang = donHangDAO.selectByDateRange(startDate, endDate);
        
        // Tạo map để lưu số lượng bán của từng sản phẩm
        Map<Integer, Integer> sanPhamCountMap = new HashMap<>();
        Map<Integer, String> sanPhamNameMap = new HashMap<>();
        Map<Integer, Double> sanPhamRevenueMap = new HashMap<>();
        
        // Đếm số lượng bán và doanh thu của từng sản phẩm
        for (DonHang donHang : danhSachDonHang) {
            // Chỉ tính các đơn hàng đã hoàn thành
            if ("hoan thanh".equals(donHang.getTrangThai())) {
                List<ChiTietDonHang> chiTietList = chiTietDonHangDAO.selectByMaDonHang(donHang.getMaDonHang());
                for (ChiTietDonHang chiTiet : chiTietList) {
                    int maSanPham = chiTiet.getSanPham().getMaSanPham();
                    String tenSanPham = chiTiet.getSanPham().getTenSanPham();
                    int soLuong = (int) chiTiet.getSoLuong();
                    double tongTien = chiTiet.getTongTien();
                    
                    // Cập nhật số lượng và doanh thu
                    if (sanPhamCountMap.containsKey(maSanPham)) {
                        sanPhamCountMap.put(maSanPham, sanPhamCountMap.get(maSanPham) + soLuong);
                        sanPhamRevenueMap.put(maSanPham, sanPhamRevenueMap.get(maSanPham) + tongTien);
                    } else {
                        sanPhamCountMap.put(maSanPham, soLuong);
                        sanPhamNameMap.put(maSanPham, tenSanPham);
                        sanPhamRevenueMap.put(maSanPham, tongTien);
                    }
                }
            }
        }
        
        // Sắp xếp và lấy top sản phẩm
        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(sanPhamCountMap.entrySet());
        sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        // Lấy tất cả sản phẩm đã bán
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : sortedList) {
            Map<String, Object> item = new HashMap<>();
            item.put("maSanPham", entry.getKey());
            item.put("tenSanPham", sanPhamNameMap.get(entry.getKey()));
            item.put("soLuongBan", entry.getValue());
            item.put("doanhThu", sanPhamRevenueMap.get(entry.getKey()));
            
            result.add(item);
        }
        
        return result;
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}