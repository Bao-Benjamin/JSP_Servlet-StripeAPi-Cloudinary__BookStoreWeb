
package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ChiTietDonHang;
import model.DonHang;
import model.KhachHang;
import model.TacGia;

public class DonHangDAO implements DAOInterface<DonHang> {

	@Override
		public ArrayList<DonHang> selectAll() {
			ArrayList<DonHang> ketQua = new ArrayList<DonHang>();
			Connection con = JDBCUtil.getConnection();
			String sql = "SELECT * FROM donhang";
			try {
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
				while (rs.next()) {
					String maDonHang = rs.getString(1);
					String tenKhachHang = rs.getString(2);
					String soDienThoai = rs.getString(3);
					String diaChiNhanHang = rs.getString(4);
					String hinhThucThanhToan = rs.getString(5);
					Date ngayDatHang = rs.getDate(6);
					String ghiChu = rs.getString(7);
					String trangThai = rs.getString(8);
					DonHang dh = new DonHang(maDonHang, tenKhachHang,soDienThoai,  diaChiNhanHang,  hinhThucThanhToan,  ngayDatHang,ghiChu,trangThai);
	
					ketQua.add(dh);
					
				}
				System.out.println("Bạn đã thực thi: " + sql);
				System.out.println("Có " + ketQua + " dòng bị thay đổi!");
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return ketQua;
		}

	@Override
	public DonHang selectById(DonHang t) {
		DonHang ketQua = null;
		Connection con = JDBCUtil.getConnection();
		String sql = "SELECT * FROM donhang WHERE madonhang = ?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaDonHang());
//			st.setString(1, t.getMaDonHangAsString());
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String maDonHang = rs.getString(1);
				String tenKhachHang = rs.getString(2);
				String soDienThoai = rs.getString(3);
				String diaChiNhanHang = rs.getString(4);
				String hinhThucThanhToan = rs.getString(5);
				Date ngayDatHang = rs.getDate(6);
				String ghiChu = rs.getString(7);
				String trangThai = rs.getString(8);
				 DonHang dh = new DonHang(maDonHang, tenKhachHang,soDienThoai,  diaChiNhanHang,  hinhThucThanhToan,  ngayDatHang,ghiChu,trangThai);

				ketQua = dh;
			}
			System.out.println("Bạn đã thực thi: " + sql);
			System.out.println("Có " + ketQua + " dòng bị thay đổi!");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ketQua;
	}

	public ArrayList<DonHang> selectByUserName(String tenKH) {
		ArrayList<DonHang> ketQua = new ArrayList<DonHang>();
		Connection con = JDBCUtil.getConnection();
		String sql = "SELECT * FROM donhang WHERE tenkhachhang = ?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, tenKH);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				String maDonHang = rs.getString(1);
				String tenKhachHang = rs.getString(2);
				String soDienThoai = rs.getString(3);
				String diaChiNhanHang = rs.getString(4);
				String hinhThucThanhToan = rs.getString(5);
				Date ngayDatHang = rs.getDate(6);
				String ghiChu = rs.getString(7);
				String trangThai = rs.getString(8);
				 DonHang dh = new DonHang(maDonHang, tenKhachHang,soDienThoai,  diaChiNhanHang,  hinhThucThanhToan,  ngayDatHang,ghiChu,trangThai);

				ketQua.add(dh);
			}
			System.out.println("Bạn đã thực thi: " + sql);
			System.out.println("Có " + ketQua + " dòng bị thay đổi!");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ketQua;
	}
	@Override
	public int insert(DonHang t) {
		int kq = 0;
		System.out.println("----------da chạy vào insert trong donhang sao");
		Connection con = JDBCUtil.getConnection();
		String sql = "INSERT INTO donhang( maDonHang,tenkhachhang,sodienthoai, diachinhanhang ,hinhthucthanhtoan, ngaydathang,ghichu,trangthai)"
				+ "VALUES (?,?, ?, ?, ?,?,?,?)";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaDonHang());
//			st.setString(1, t.getMaDonHangAsString());
			st.setString(2, t.getTenKhachHang());
			st.setString(3,t.getSoDienThoai());
			st.setString(4, t.getDiaChiNhanHang());
			
			st.setString(5, t.getHinhThucThanhToan());
		
			st.setDate(6, t.getNgayDatHang());
			st.setString(7,t.getGhiChu());
			st.setString(8,t.getTrangThai());

			kq = st.executeUpdate();
			
			System.out.println("Bạn đã thực thi: " + sql);
			System.out.println("Có " + kq + " dòng bị thay đổi!");
			
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("chưa lệnh insert trong DonHang DAO");
			e.printStackTrace();
		}
		return kq;
	}

	@Override
	public int insertAll(ArrayList<DonHang> arr) {
		int kq = 0;
		for (DonHang donHang : arr) {
			kq += this.insert(donHang);
		}
//		System.out.println("Bạn đã thực thi: " + sql);
		System.out.println("Có " + kq + " dòng bị thay đổi!");
		return kq;
	}

	@Override
	public int delete(DonHang t) {
		int kq = 0;
		Connection con = JDBCUtil.getConnection();
		String sql = "DELETE FROM donhang WHERE madonhang = ?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaDonHang());
//			st.setString(1, t.getMaDonHangAsString());
			kq = st.executeUpdate();
			System.out.println("Bạn đã thực thi: " + sql);
			System.out.println("Có " + kq + " dòng bị thay đổi!");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kq;
	}

	@Override
	public int deleteAll(ArrayList<DonHang> arr) {
		int kq = 0;
		for (DonHang t : arr) {
			kq += this.delete(t);
		}
//		System.out.println("Bạn đã thực thi: " + sql);
		System.out.println("Có " + kq + " dòng bị thay đổi!");
		return kq;
	}

	@Override
	public int update(DonHang t) {
		System.out.println("da chạy vào update trong donhang dao");
		int kq = 0;
		Connection con = JDBCUtil.getConnection();

		String sql = "UPDATE donhang" + " SET " + "tenkhachhang=?" + ",sodienthoai=?"
				+ ",diachinhanhang=?" + ",hinhthucthanhtoan=?"+ ",ngaydathang=?" + ",ghichu=?" + ",trangthai=?" 
				+ " WHERE madonhang=?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			
			st.setString(1, t.getTenKhachHang());
			st.setString(2, t.getSoDienThoai());
			st.setString(3, t.getDiaChiNhanHang());
			
			st.setString(4, t.getHinhThucThanhToan());
			
		
			st.setDate(5, t.getNgayDatHang());
			st.setString(6,t.getGhiChu());
			st.setString(7,t.getTrangThai());
			st.setString(8, t.getMaDonHang());

			kq = st.executeUpdate();
			System.out.println("sql:"+sql);
			System.out.println("da chạy xong update trong donhang dao");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kq;
	}
	public int updateGhiChu(DonHang t) {
		int kq = 0;
		System.out.println("Ghi chú mới: " + t.getGhiChu());
		System.out.println("Mã đơn hàng: " + t.getMaDonHang());

		Connection con = JDBCUtil.getConnection();

		String sql = "UPDATE donhang SET ghichu=? WHERE madonhang=?";
		try {
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(2, t.getMaDonHang());
//			st.setString(2, t.getMaDonHangAsString());
			
			st.setString(1,t.getGhiChu());
		

			kq = st.executeUpdate();
			System.out.println(sql);
			System.out.println("da cap nhat ghi chu trong DonHang dao");
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return kq;
	}
	// Thêm vào class DonHangDAO
//	public List<DonHang> selectByDateRange(Date startDate, Date endDate) {
//	    ArrayList<DonHang> ketQua = new ArrayList<DonHang>();
//	    
//	    try {
//	        // Bước 1: tạo kết nối đến CSDL
//	        Connection con = JDBCUtil.getConnection();
//	        
//	        // Bước 2: tạo ra đối tượng statement
//	        String sql = "SELECT * FROM donhang WHERE ngaydathang BETWEEN ? AND ?";
//	        PreparedStatement st = con.prepareStatement(sql);
//	        st.setDate(1, new java.sql.Date(startDate.getTime()));
//	        st.setDate(2, new java.sql.Date(endDate.getTime()));
//	        
//	        // Bước 3: thực thi câu lệnh SQL
//	        System.out.println(sql);
//	        ResultSet rs = st.executeQuery();
//	        
//	        // Bước 4: xử lý kết quả
//	        while (rs.next()) {
//	            String maDonHang = rs.getString("madonhang");
//	            String tenKhachHang = rs.getString("tenkhachhang");
//	            String soDienThoai = rs.getString("sodienthoai");
//	            String diaChiNhanHang = rs.getString("diachinhanhang");
//	            Date ngayDatHang = rs.getDate("ngaydathang");
//	            String hinhThucThanhToan = rs.getString("hinhthucthanhtoan");
//	            String ghiChu = rs.getString("ghichu");
//	            String trangThai = rs.getString("trangthai");
//	            
//	            DonHang dh = new DonHang();
//	            dh.setMaDonHang(maDonHang);
//	            dh.setTenKhachHang(tenKhachHang);
//	            dh.setSoDienThoai(soDienThoai);
//	            dh.setDiaChiNhanHang(diaChiNhanHang);
//	            dh.setNgayDatHang(ngayDatHang);
//	            dh.setHinhThucThanhToan(hinhThucThanhToan);
//	            dh.setGhiChu(ghiChu);
//	            dh.setTrangThai(trangThai);
//	            
//	            // Lấy chi tiết đơn hàng
//	            ChiTietDonHangDAO chiTietDAO = new ChiTietDonHangDAO();
//	            List<ChiTietDonHang> chiTietList = chiTietDAO.selectByMaDonHang(maDonHang);
//	            dh.setDanhSachChiTiet(chiTietList);
//	            
//	            ketQua.add(dh);
//	        }
//	        
//	        // Bước 5: đóng kết nối
//	        JDBCUtil.closeConnection(con);
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	    }
//	    
//	    return ketQua;
//	}

	// Điều chỉnh phương thức trong class DonHangDAO
	// Điều chỉnh phương thức trong class DonHangDAO
	public List<DonHang> selectByDateRange(Date startDate, Date endDate) {
	    ArrayList<DonHang> ketQua = new ArrayList<DonHang>();
	    
	    try {
	        // Bước 1: tạo kết nối đến CSDL
	        Connection con = JDBCUtil.getConnection();
	        
	        // Bước 2: tạo ra đối tượng statement
	        String sql = "SELECT * FROM donhang WHERE ngaydathang BETWEEN ? AND ?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setDate(1, startDate);
	        st.setDate(2, endDate);
	        
	        // Bước 3: thực thi câu lệnh SQL
	        System.out.println(sql);
	        ResultSet rs = st.executeQuery();
	        
	        // Bước 4: xử lý kết quả
	        while (rs.next()) {
	            String maDonHang = rs.getString("madonhang");
	            String tenKhachHang = rs.getString("tenkhachhang");
	            String soDienThoai = rs.getString("sodienthoai");
	            String diaChiNhanHang = rs.getString("diachinhanhang");
	            Date ngayDatHang = rs.getDate("ngaydathang");
	            String hinhThucThanhToan = rs.getString("hinhthucthanhtoan");
	            String ghiChu = rs.getString("ghichu");
	            String trangThai = rs.getString("trangthai");
	            
	            DonHang dh = new DonHang();
	            dh.setMaDonHang(maDonHang);
	            dh.setTenKhachHang(tenKhachHang);
	            dh.setSoDienThoai(soDienThoai);
	            dh.setDiaChiNhanHang(diaChiNhanHang);
	            dh.setNgayDatHang(ngayDatHang);
	            dh.setHinhThucThanhToan(hinhThucThanhToan);
	            dh.setGhiChu(ghiChu);
	            dh.setTrangThai(trangThai);
	            
	            ketQua.add(dh);
	        }
	        
	        // Bước 5: đóng kết nối
	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return ketQua;
	}

	public int countOrdersByDateRange(Date startDate, Date endDate) {
	    int count = 0;
	    
	    try {
	        // Bước 1: tạo kết nối đến CSDL
	        Connection con = JDBCUtil.getConnection();
	        
	        // Bước 2: tạo ra đối tượng statement
	        String sql = "SELECT COUNT(*) AS total FROM donhang WHERE ngaydathang BETWEEN ? AND ?";
	        PreparedStatement st = con.prepareStatement(sql);
	        st.setDate(1, startDate);
	        st.setDate(2, endDate);
	        
	        // Bước 3: thực thi câu lệnh SQL
	        ResultSet rs = st.executeQuery();
	        
	        // Bước 4: xử lý kết quả
	        if (rs.next()) {
	            count = rs.getInt("total");
	        }
	        
	        // Bước 5: đóng kết nối
	        JDBCUtil.closeConnection(con);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return count;
	}

}