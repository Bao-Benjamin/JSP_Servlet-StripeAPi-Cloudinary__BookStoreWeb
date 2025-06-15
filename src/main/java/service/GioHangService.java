package service;

import database.ChiTietGioHangDAO;
import database.GioHangDAO;
import model.ChiTietGioHang;
import model.GioHang;
import model.KhachHang;
import model.SanPham;

public class GioHangService {
    private GioHangDAO gioHangDAO;
    private ChiTietGioHangDAO chiTietGioHangDAO;
    
    public GioHangService() {
        gioHangDAO = new GioHangDAO();
        chiTietGioHangDAO = new ChiTietGioHangDAO();
    }
    
    // Lấy giỏ hàng của khách hàng, nếu chưa có thì tạo mới
    public GioHang getGioHangByKhachHang(KhachHang khachHang) {
        GioHang gioHang = gioHangDAO.getGioHangByKhachHang(khachHang.getMaKhachHang());
        
        if (gioHang == null) {
            // Tạo giỏ hàng mới
            int maGioHang = gioHangDAO.taoGioHang(khachHang.getMaKhachHang());
            if (maGioHang > 0) {
                gioHang = new GioHang();
                gioHang.setMaGioHang(maGioHang);
                gioHang.setKhachHang(khachHang);
            }
        }
        
        return gioHang;
    }
    
    // Thêm sản phẩm vào giỏ hàng
    public boolean themSanPhamVaoGioHang(GioHang gioHang, SanPham sanPham, int soLuong) {
        ChiTietGioHang chiTiet = new ChiTietGioHang();
        chiTiet.setGioHang(gioHang);
        chiTiet.setSanPham(sanPham);
        chiTiet.setSoLuong(soLuong);
        
        return chiTietGioHangDAO.themChiTiet(chiTiet);
    }
    
    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public boolean capNhatSoLuong(int maChiTietGioHang, int soLuongMoi) {
        return chiTietGioHangDAO.capNhatSoLuong(maChiTietGioHang, soLuongMoi);
    }
    
    // Xóa sản phẩm khỏi giỏ hàng
    public boolean xoaSanPhamKhoiGioHang(int maChiTietGioHang) {
        return chiTietGioHangDAO.xoaChiTiet(maChiTietGioHang);
    }
    
    // Xóa tất cả sản phẩm trong giỏ hàng
    public boolean xoaTatCaSanPham(int maGioHang) {
        return chiTietGioHangDAO.xoaTatCaChiTiet(maGioHang);
    }
}