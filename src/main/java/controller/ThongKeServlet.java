package controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import database.DonHangDAO;
import database.KhachHangDAO;
import database.ChiTietDonHangDAO;
import model.DonHang;
import model.ChiTietDonHang;

@WebServlet("/thong-ke")
public class ThongKeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy tham số ngày bắt đầu và kết thúc từ request
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String period = request.getParameter("period"); // day, month, year
        
        if (period == null) {
            period = "day"; // Mặc định là thống kê theo ngày
        }
        
        // Khởi tạo ngày bắt đầu và kết thúc mặc định (30 ngày gần nhất)
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
        
        // Lấy dữ liệu thống kê
        DonHangDAO donHangDAO = new DonHangDAO();
        ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
        KhachHangDAO khachHangDAO = new KhachHangDAO();
        
        // 1. Thống kê doanh thu theo thời gian
        Map<String, Double> doanhThuTheoThoiGian = getDoanhThuTheoThoiGian(donHangDAO, chiTietDonHangDAO, startDate, endDate, period);
        
        // 2. Thống kê số lượng đơn hàng theo trạng thái
        Map<String, Integer> donHangTheoTrangThai = getDonHangTheoTrangThai(donHangDAO, startDate, endDate);
        
        // 3. Thống kê top 5 sản phẩm bán chạy
        List<Map<String, Object>> topSanPhamBanChay = getTopSanPhamBanChay(chiTietDonHangDAO, donHangDAO, startDate, endDate, 5);
        
        // 4. Thống kê số lượng khách hàng mới
        int soKhachHangMoi = getSoKhachHangMoi(khachHangDAO, startDate, endDate);
        
        // 5. Tổng doanh thu trong khoảng thời gian
        double tongDoanhThu = getTongDoanhThu(chiTietDonHangDAO, donHangDAO, startDate, endDate);
        
        // 6. Tổng số đơn hàng trong khoảng thời gian
        int tongDonHang = getTongDonHang(donHangDAO, startDate, endDate);
        
        // Chuyển dữ liệu sang JSON để sử dụng trong JavaScript
        Gson gson = new Gson();
        String doanhThuJson = gson.toJson(doanhThuTheoThoiGian);
        String donHangTheoTrangThaiJson = gson.toJson(donHangTheoTrangThai);
        String topSanPhamJson = gson.toJson(topSanPhamBanChay);
        
        // Đặt các thuộc tính vào request
        request.setAttribute("startDate", startDateStr);
        request.setAttribute("endDate", endDateStr);
        request.setAttribute("period", period);
        request.setAttribute("doanhThuJson", doanhThuJson);
        request.setAttribute("donHangTheoTrangThaiJson", donHangTheoTrangThaiJson);
        request.setAttribute("topSanPhamJson", topSanPhamJson);
        request.setAttribute("topSanPhamBanChay", topSanPhamBanChay);
        request.setAttribute("soKhachHangMoi", soKhachHangMoi);
        request.setAttribute("tongDoanhThu", tongDoanhThu);
        request.setAttribute("tongDonHang", tongDonHang);
        
        // Forward đến trang JSP
        request.getRequestDispatcher("thongke/thongke.jsp").forward(request, response);
    }
    
    // Phương thức lấy doanh thu theo thời gian (ngày, tháng, năm)
    private Map<String, Double> getDoanhThuTheoThoiGian(DonHangDAO donHangDAO, ChiTietDonHangDAO chiTietDonHangDAO, Date startDate, Date endDate, String period) {
        // Lấy tất cả đơn hàng trong khoảng thời gian
        List<DonHang> danhSachDonHang = donHangDAO.selectByDateRange(startDate, endDate);
        
        // Tạo map để lưu doanh thu theo thời gian
        Map<String, Double> doanhThuMap = new LinkedHashMap<>();
        
        // Format cho các loại thời gian
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM/yyyy");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        
        // Khởi tạo map với tất cả các ngày/tháng/năm trong khoảng
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startDate.getTime());
        
        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endDate.getTime());
        
        while (!cal.after(endCal)) {
            String key = "";
            if ("day".equals(period)) {
                key = dayFormat.format(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 1);
            } else if ("month".equals(period)) {
                key = monthFormat.format(cal.getTime());
                cal.add(Calendar.MONTH, 1);
            } else if ("year".equals(period)) {
                key = yearFormat.format(cal.getTime());
                cal.add(Calendar.YEAR, 1);
            }
            doanhThuMap.put(key, 0.0);
        }
        
        // Tính doanh thu cho từng đơn hàng và cộng vào map
        for (DonHang donHang : danhSachDonHang) {
            // Chỉ tính các đơn hàng đã hoàn thành
            if ("hoan thanh".equals(donHang.getTrangThai())) {
                String key = "";
                if ("day".equals(period)) {
                    key = dayFormat.format(donHang.getNgayDatHang());
                } else if ("month".equals(period)) {
                    key = monthFormat.format(donHang.getNgayDatHang());
                } else if ("year".equals(period)) {
                    key = yearFormat.format(donHang.getNgayDatHang());
                }
                
                // Lấy chi tiết đơn hàng và tính tổng tiền
                List<ChiTietDonHang> chiTietList = chiTietDonHangDAO.selectByMaDonHang(donHang.getMaDonHang());
                double tongTien = 0;
                for (ChiTietDonHang chiTiet : chiTietList) {
                    tongTien += chiTiet.getTongTien();
                }
                
                // Cộng vào map
                if (doanhThuMap.containsKey(key)) {
                    doanhThuMap.put(key, doanhThuMap.get(key) + tongTien);
                } else {
                    doanhThuMap.put(key, tongTien);
                }
            }
        }
        
        return doanhThuMap;
    }
    
    // Phương thức lấy số lượng đơn hàng theo trạng thái
    private Map<String, Integer> getDonHangTheoTrangThai(DonHangDAO donHangDAO, Date startDate, Date endDate) {
        // Lấy tất cả đơn hàng trong khoảng thời gian
        List<DonHang> danhSachDonHang = donHangDAO.selectByDateRange(startDate, endDate);
        
        // Tạo map để lưu số lượng đơn hàng theo trạng thái
        Map<String, Integer> donHangMap = new HashMap<>();
        donHangMap.put("Chờ xử lý", 0);
        donHangMap.put("Đang xử lý", 0);
        donHangMap.put("Hoàn thành", 0);
        donHangMap.put("Đã hủy", 0);
        
        // Đếm số lượng đơn hàng theo trạng thái
        for (DonHang donHang : danhSachDonHang) {
            String trangThai = donHang.getTrangThai();
            if ("cho xu ly".equals(trangThai)) {
                donHangMap.put("Chờ xử lý", donHangMap.get("Chờ xử lý") + 1);
            } else if ("dang xu ly".equals(trangThai)) {
                donHangMap.put("Đang xử lý", donHangMap.get("Đang xử lý") + 1);
            } else if ("hoan thanh".equals(trangThai)) {
                donHangMap.put("Hoàn thành", donHangMap.get("Hoàn thành") + 1);
            } else if ("da huy".equals(trangThai)) {
                donHangMap.put("Đã hủy", donHangMap.get("Đã hủy") + 1);
            }
        }
        
        return donHangMap;
    }
    
    // Phương thức lấy top sản phẩm bán chạy
    private List<Map<String, Object>> getTopSanPhamBanChay(ChiTietDonHangDAO chiTietDonHangDAO, DonHangDAO donHangDAO, Date startDate, Date endDate, int limit) {
        // Lấy tất cả đơn hàng trong khoảng thời gian
        List<DonHang> danhSachDonHang = donHangDAO.selectByDateRange(startDate, endDate);
        
        // Tạo map để lưu số lượng bán của từng sản phẩm
        Map<Integer, Integer> sanPhamCountMap = new HashMap<>();
        Map<Integer, String> sanPhamNameMap = new HashMap<>();
        
        // Đếm số lượng bán của từng sản phẩm
        for (DonHang donHang : danhSachDonHang) {
            // Chỉ tính các đơn hàng đã hoàn thành
            if ("hoan thanh".equals(donHang.getTrangThai())) {
                List<ChiTietDonHang> chiTietList = chiTietDonHangDAO.selectByMaDonHang(donHang.getMaDonHang());
                for (ChiTietDonHang chiTiet : chiTietList) {
                    int maSanPham = chiTiet.getSanPham().getMaSanPham();
                    String tenSanPham = chiTiet.getSanPham().getTenSanPham();
                    int soLuong = (int) chiTiet.getSoLuong();
                    
                    // Cập nhật số lượng
                    if (sanPhamCountMap.containsKey(maSanPham)) {
                        sanPhamCountMap.put(maSanPham, sanPhamCountMap.get(maSanPham) + soLuong);
                    } else {
                        sanPhamCountMap.put(maSanPham, soLuong);
                        sanPhamNameMap.put(maSanPham, tenSanPham);
                    }
                }
            }
        }
        
        // Sắp xếp và lấy top sản phẩm
        List<Map.Entry<Integer, Integer>> sortedList = new ArrayList<>(sanPhamCountMap.entrySet());
        sortedList.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        
        // Lấy top sản phẩm theo limit
        List<Map<String, Object>> result = new ArrayList<>();
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : sortedList) {
            if (count >= limit) break;
            
            Map<String, Object> item = new HashMap<>();
            item.put("maSanPham", entry.getKey());
            item.put("tenSanPham", sanPhamNameMap.get(entry.getKey()));
            item.put("soLuongBan", entry.getValue());
            
            result.add(item);
            count++;
        }
        
        return result;
    }
    
    // Phương thức lấy số lượng khách hàng mới
    private int getSoKhachHangMoi(KhachHangDAO khachHangDAO, Date startDate, Date endDate) {
        return khachHangDAO.countNewCustomers(startDate, endDate);
    }
    
    // Phương thức lấy tổng doanh thu
    private double getTongDoanhThu(ChiTietDonHangDAO chiTietDonHangDAO, DonHangDAO donHangDAO, Date startDate, Date endDate) {
        // Lấy tất cả đơn hàng trong khoảng thời gian
        List<DonHang> danhSachDonHang = donHangDAO.selectByDateRange(startDate, endDate);
        
        double tongDoanhThu = 0;
        
        // Tính tổng doanh thu
        for (DonHang donHang : danhSachDonHang) {
            // Chỉ tính các đơn hàng đã hoàn thành
            if ("hoan thanh".equals(donHang.getTrangThai())) {
                List<ChiTietDonHang> chiTietList = chiTietDonHangDAO.selectByMaDonHang(donHang.getMaDonHang());
                for (ChiTietDonHang chiTiet : chiTietList) {
                    tongDoanhThu += chiTiet.getTongTien();
                }
            }
        }
        
        return tongDoanhThu;
    }
    
    // Phương thức lấy tổng số đơn hàng
    private int getTongDonHang(DonHangDAO donHangDAO, Date startDate, Date endDate) {
        return donHangDAO.countOrdersByDateRange(startDate, endDate);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}