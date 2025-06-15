package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ChiTietGioHang;
import model.GioHang;
import model.SanPham;

public class ChiTietGioHangDAO {
    
	public ChiTietGioHang selectById(int maChiTietGioHang) {
	    ChiTietGioHang chiTietGioHang = null;
	    
	    try {
	        Connection con = JDBCUtil.getConnection();
	        String sql = "SELECT * FROM chitietgiohang WHERE machitietgiohang = ?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setInt(1, maChiTietGioHang);
	        
	        ResultSet rs = st.executeQuery();
	        
	        if (rs.next()) {
	            chiTietGioHang = new ChiTietGioHang();
	            chiTietGioHang.setMaChiTietGioHang(rs.getInt("machitietgiohang"));
	            
	            // Lấy thông tin giỏ hàng
	            GioHang gioHang = new GioHang();
	            gioHang.setMaGioHang(rs.getInt("magiohang"));
	            chiTietGioHang.setGioHang(gioHang);
	            
	            // Lấy thông tin sản phẩm
	            SanPhamDAO sanPhamDAO = new SanPhamDAO();
	            SanPham sanPham = sanPhamDAO.selectById(rs.getInt("masanpham"));
	            chiTietGioHang.setSanPham(sanPham);
	            
	            chiTietGioHang.setSoLuong(rs.getInt("soluong"));
	        }
	        
	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return chiTietGioHang;
	}
    // Lấy danh sách chi tiết giỏ hàng
    public List<ChiTietGioHang> getChiTietByGioHang(int maGioHang) {
        List<ChiTietGioHang> danhSachChiTiet = new ArrayList<>();
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM ChiTietGioHang WHERE MaGioHang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, maGioHang);
            
            ResultSet rs = pst.executeQuery();
            
            SanPhamDAO sanPhamDAO = new SanPhamDAO();
            
            while (rs.next()) {
                int maChiTietGioHang = rs.getInt("MaChiTietGioHang");
                int maSanPham = rs.getInt("MaSanPham");
                int soLuong = rs.getInt("SoLuong");
                
                // Lấy thông tin sản phẩm
                SanPham sanPham = sanPhamDAO.selectById(maSanPham);
                
                // Tạo đối tượng giỏ hàng tạm thời (chỉ có ID)
                GioHang gioHang = new GioHang();
                gioHang.setMaGioHang(maGioHang);
                
                ChiTietGioHang chiTiet = new ChiTietGioHang(maChiTietGioHang, gioHang, sanPham, soLuong);
                danhSachChiTiet.add(chiTiet);
            }
            
            rs.close();
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachChiTiet;
    }
    
    // Thêm chi tiết giỏ hàng
    public boolean themChiTiet(ChiTietGioHang chiTiet) {
        boolean result = false;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
            String sqlCheck = "SELECT * FROM ChiTietGioHang WHERE MaGioHang = ? AND MaSanPham = ?";
            PreparedStatement pstCheck = conn.prepareStatement(sqlCheck);
            pstCheck.setInt(1, chiTiet.getGioHang().getMaGioHang());
            pstCheck.setInt(2, chiTiet.getSanPham().getMaSanPham());
            
            ResultSet rs = pstCheck.executeQuery();
            
            if (rs.next()) {
                // Nếu đã có, cập nhật số lượng
                int maChiTietGioHang = rs.getInt("MaChiTietGioHang");
                int soLuongHienTai = rs.getInt("SoLuong");
                int soLuongMoi = soLuongHienTai + chiTiet.getSoLuong();
                
                String sqlUpdate = "UPDATE ChiTietGioHang SET SoLuong = ? WHERE MaChiTietGioHang = ?";
                PreparedStatement pstUpdate = conn.prepareStatement(sqlUpdate);
                pstUpdate.setInt(1, soLuongMoi);
                pstUpdate.setInt(2, maChiTietGioHang);
                
                result = pstUpdate.executeUpdate() > 0;
                pstUpdate.close();
            } else {
                // Nếu chưa có, thêm mới
                String sqlInsert = "INSERT INTO ChiTietGioHang (MaGioHang, MaSanPham, SoLuong) VALUES (?, ?, ?)";
                PreparedStatement pstInsert = conn.prepareStatement(sqlInsert);
                pstInsert.setInt(1, chiTiet.getGioHang().getMaGioHang());
                pstInsert.setInt(2, chiTiet.getSanPham().getMaSanPham());
                pstInsert.setInt(3, chiTiet.getSoLuong());
                
                result = pstInsert.executeUpdate() > 0;
                pstInsert.close();
            }
            
            rs.close();
            pstCheck.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Cập nhật số lượng
    public boolean capNhatSoLuong(int maChiTietGioHang, int soLuongMoi) {
        boolean result = false;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "UPDATE ChiTietGioHang SET SoLuong = ? WHERE MaChiTietGioHang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, soLuongMoi);
            pst.setInt(2, maChiTietGioHang);
            
            result = pst.executeUpdate() > 0;
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Xóa chi tiết giỏ hàng
    public boolean xoaChiTiet(int maChiTietGioHang) {
        boolean result = false;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM ChiTietGioHang WHERE MaChiTietGioHang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, maChiTietGioHang);
            
            result = pst.executeUpdate() > 0;
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    // Xóa tất cả chi tiết của một giỏ hàng
    public boolean xoaTatCaChiTiet(int maGioHang) {
        boolean result = false;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM ChiTietGioHang WHERE MaGioHang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, maGioHang);
            
            result = pst.executeUpdate() > 0;
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    public boolean xoaChiTietTheoGioHang(int maGioHang) {
        boolean result = false;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM ChiTietGioHang WHERE MaGioHang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, maGioHang);
            
            int rowsAffected = pst.executeUpdate();
            result = rowsAffected > 0;
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}