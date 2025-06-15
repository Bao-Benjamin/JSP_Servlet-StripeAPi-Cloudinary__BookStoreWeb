package database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.GioHang;
import model.KhachHang;

public class GioHangDAO {
    
    // Lấy giỏ hàng của khách hàng
    public GioHang getGioHangByKhachHang(String maKhachHang) {
        GioHang gioHang = null;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM GioHang WHERE MaKhachHang = ? ORDER BY NgayTao DESC LIMIT 1";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, maKhachHang);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int maGioHang = rs.getInt("MaGioHang");
                Timestamp ngayTao = rs.getTimestamp("NgayTao");
                
                KhachHangDAO khachHangDAO = new KhachHangDAO();
                KhachHang kh = new KhachHang();
                kh.setMaKhachHang(maKhachHang);
                KhachHang khachHang = khachHangDAO.selectById(kh);
                
                gioHang = new GioHang(maGioHang, khachHang, ngayTao,null);
                
                // Lấy danh sách chi tiết giỏ hàng
                ChiTietGioHangDAO chiTietDAO = new ChiTietGioHangDAO();
                gioHang.setDanhSachChiTiet(chiTietDAO.getChiTietByGioHang(maGioHang));
            }
            
            rs.close();
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return gioHang;
    }
    
    // Tạo giỏ hàng mới
    public int taoGioHang(String maKhachHang) {
        int maGioHang = -1;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO GioHang (MaKhachHang) VALUES (?)";
            PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, maKhachHang);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    maGioHang = rs.getInt(1);
                }
                rs.close();
            }
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return maGioHang;
    }
    
    // Xóa giỏ hàng
    public boolean xoaGioHang(int maGioHang) {
        boolean result = false;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            // Xóa chi tiết giỏ hàng trước
            String sqlChiTiet = "DELETE FROM ChiTietGioHang WHERE MaGioHang = ?";
            System.out.println("sql: "+sqlChiTiet);
            PreparedStatement pstChiTiet = conn.prepareStatement(sqlChiTiet);
            pstChiTiet.setInt(1, maGioHang);
            pstChiTiet.executeUpdate();
            pstChiTiet.close();
            
            // Sau đó xóa giỏ hàng
            String sqlGioHang = "DELETE FROM GioHang WHERE MaGioHang = ?";
            PreparedStatement pstGioHang = conn.prepareStatement(sqlGioHang);
            pstGioHang.setInt(1, maGioHang);
            
            int rowsAffected = pstGioHang.executeUpdate();
            System.out.println("Bạn đã thực thi: " + sqlChiTiet);
			
            result = rowsAffected > 0;
            System.out.println("đã " + result + " dòng bị thay đổi!");
            pstGioHang.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
}