package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.HinhAnhSanPham;
import model.SanPham;

public class HinhAnhSanPhamDAO {
    
    public List<HinhAnhSanPham> selectByProductId(int maSanPham) {
        List<HinhAnhSanPham> danhSachHinhAnh = new ArrayList<>();
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "SELECT * FROM hinhanh_sanpham WHERE MaSanPham = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, maSanPham);
            
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                HinhAnhSanPham hinhAnh = new HinhAnhSanPham();
                hinhAnh.setMaHinhAnh(rs.getInt("MaHinhAnh"));
                
                SanPham sanPham = new SanPham();
                sanPham.setMaSanPham(maSanPham);
                hinhAnh.setSanPham(sanPham);
                
                hinhAnh.setUrl(rs.getString("DuongDan"));
                
                danhSachHinhAnh.add(hinhAnh);
            }
            
            rs.close();
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachHinhAnh;
    }
    public List<HinhAnhSanPham> getSanPhamsByMaSanPham(int masanpham) {
        List<HinhAnhSanPham> HinhAnhSanPhamList = new ArrayList<>();
        // Sửa lỗi thiếu khoảng trắng trước WHERE
        String sql = "SELECT h.mahinhanh, h.masanpham, h.url, h.public_id FROM hinhanh_sanpham h " + 
                     "WHERE h.masanpham = ?";
        try (Connection conn = JDBCUtil.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, masanpham); // Thiết lập tham số
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int mahinhanh = rs.getInt("mahinhanh");
                    int maSanPham = rs.getInt("masanpham");
                    String url = rs.getString("url");
                    String public_id = rs.getString("public_id");
                    
                    SanPhamDAO spd = new SanPhamDAO();
                    SanPham sp = spd.selectById(maSanPham);
                    
                    HinhAnhSanPham hasp = new HinhAnhSanPham(mahinhanh, sp, url, public_id);
                    HinhAnhSanPhamList.add(hasp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return HinhAnhSanPhamList;
    }
    
    public boolean themHinhAnh(HinhAnhSanPham hinhAnh) {
        String sql = "INSERT INTO hinhanh_sanpham (masanpham, url, public_id) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection()){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, hinhAnh.getSanPham().getMaSanPham());
            ps.setString(2, hinhAnh.getUrl());
            ps.setString(3, hinhAnh.getPublicId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean themNhieuHinhAnh(List<HinhAnhSanPham> danhSachHinhAnh) {
        String sql = "INSERT INTO hinhanh_sanpham (masanpham, url, public_id) VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection()) {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            for (HinhAnhSanPham hinhAnh : danhSachHinhAnh) {
                ps.setInt(1, hinhAnh.getSanPham().getMaSanPham());
                ps.setString(2, hinhAnh.getUrl());
                ps.setString(3, hinhAnh.getPublicId());
                ps.addBatch();
            }
            
            int[] results = ps.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);
            
            for (int result : results) {
                if (result <= 0) {
                    return false;
                }
            }
            return true;
        } catch (SQLException e) {
            try (Connection conn = JDBCUtil.getConnection()){
                conn.rollback();
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
        return false;
    }


    public int insert(HinhAnhSanPham hinhAnh) {
        int result = 0;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "INSERT INTO hinhanh_sanpham (MaSanPham, DuongDan) VALUES (?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setInt(1, hinhAnh.getSanPham().getMaSanPham());
            pst.setString(2, hinhAnh.getUrl());
            
            result = pst.executeUpdate();
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int update(HinhAnhSanPham hinhAnh) {
        int result = 0;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "UPDATE hinhanh_sanpham SET masanpham = ?, DuongDan = ? WHERE mahinhanh = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setInt(1, hinhAnh.getSanPham().getMaSanPham());
            pst.setString(2, hinhAnh.getUrl());
            pst.setInt(3, hinhAnh.getMaHinhAnh());
            
            result = pst.executeUpdate();
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int delete(HinhAnhSanPham hinhAnh) {
    	System.out.println("chạy vào delete trong hasp dao:");
        int result = 0;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM hinhanh_sanpham WHERE mahinhanh = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setInt(1, hinhAnh.getMaHinhAnh());
            
            result = pst.executeUpdate();
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int deleteByProductId(int maSanPham) {
    	System.out.println("chạy vào deleteByProductId trong hasp dao");
        int result = 0;
        
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM hinhanh_sanpham WHERE masanpham = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            
            pst.setInt(1, maSanPham);
            
            result = pst.executeUpdate();
            
            pst.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public boolean xoaHinhAnh(int maHinhAnh) {
    	System.out.println("chạy vào xoaHinhAnh trong hasp dao");
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM hinhanh_sanpham WHERE mahinhanh = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maHinhAnh);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean xoaHinhAnhTheoSanPham(int maSanPham) {
    	System.out.println("chạy vào xoaHinhAnhTheoSanPham trong HASP dao");
        try (Connection conn = JDBCUtil.getConnection()) {
            String sql = "DELETE FROM hinhanh_sanpham WHERE masanpham = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, maSanPham);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}