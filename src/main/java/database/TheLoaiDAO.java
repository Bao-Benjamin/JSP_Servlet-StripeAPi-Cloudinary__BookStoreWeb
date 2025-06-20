package database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.TacGia;
import model.TheLoai;

public class TheLoaiDAO implements DAOInterface<TheLoai> {
	private ArrayList<TheLoai> data = new ArrayList<>();

	@Override
	public ArrayList<TheLoai> selectAll() {
		ArrayList<TheLoai> ketQua = new ArrayList<TheLoai>();
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "SELECT * FROM bookstore.theloai";
			PreparedStatement st = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ResultSet rs = st.executeQuery();

			// Bước 4:
			while (rs.next()) {
				int maTheLoai = rs.getInt("maTheLoai");
				String tenTheLoai = rs.getString("tenTheLoai");

				TheLoai tl = new TheLoai(maTheLoai, tenTheLoai);
				ketQua.add(tl);
			}

			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}

	@Override
	public TheLoai selectById(TheLoai t) {
		TheLoai ketQua = null;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "SELECT * FROM theloai WHERE matheloai=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, t.getMaTheLoai());

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ResultSet rs = st.executeQuery();

			// Bước 4:
			while (rs.next()) {
				int maTheLoai = rs.getInt("matheloai");
				String tenTheLoai = rs.getString("tentheloai");

				ketQua = new TheLoai(maTheLoai, tenTheLoai);
				break;
			}
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}
	
	public TheLoai selectByName(TheLoai t) {
		TheLoai ketQua = null;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();

			// Bước 2: tạo ra đối tượng statement
			String sql = "SELECT * FROM theloai WHERE tentheloai=?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getTenTheLoai());

			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ResultSet rs = st.executeQuery();

			// Bước 4:
			while (rs.next()) {
				int maTheLoai = rs.getInt("matheloai");
				String tenTheLoai = rs.getString("tentheloai");

				ketQua = new TheLoai(maTheLoai, tenTheLoai);
				break;
			}
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ketQua;
	}


	@Override
	public int insert(TheLoai t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();
			
			// Bước 2: tạo ra đối tượng statement
			String sql = "INSERT INTO theloai ( tentheloai) "+
					" VALUES (?)";
			
			PreparedStatement st = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			
			st.setString(1, t.getTenTheLoai());
			
			// Bước 3: thực thi câu lệnh SQL
			ketQua = st.executeUpdate();
			
			
			  // Lấy ID tự động tăng
	        ResultSet generatedKeys = st.getGeneratedKeys();
	        if (generatedKeys.next()) {
	            t.setMaTheLoai(generatedKeys.getInt(1)); // Gán ID vào đối tượng
	        }
			// Bước 4:
			System.out.println("Bạn đã thực thi: "+ sql);
			System.out.println("Có "+ ketQua+" dòng bị thay đổi!");
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ketQua;
	}

	@Override
	public int insertAll(ArrayList<TheLoai> arr) {
		int dem = 0;
		for (TheLoai theLoai : arr) {
			dem+=this.insert(theLoai);
		}
		return dem;
	}

	@Override
	public int delete(TheLoai t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();
			
			// Bước 2: tạo ra đối tượng statement
			String sql = "DELETE from theloai "+
					 " WHERE matheloai=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, t.getMaTheLoai());
			
			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ketQua = st.executeUpdate();
			
			// Bước 4:
			System.out.println("Bạn đã thực thi: "+ sql);
			System.out.println("Có "+ ketQua+" dòng bị thay đổi!");
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ketQua;
	}

	@Override
	public int deleteAll(ArrayList<TheLoai> arr) {
		int dem = 0;
		for (TheLoai TheLoai : arr) {
			dem += this.delete(TheLoai);
		}
		return dem;
	}

	@Override
	public int update(TheLoai t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();
			
			// Bước 2: tạo ra đối tượng statement
			String sql = "UPDATE theloai "+
					 " SET " +
					 " tenTheLoai=?"+
					 " WHERE matheloai=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getTenTheLoai());
			st.setInt(2, t.getMaTheLoai());
			// Bước 3: thực thi câu lệnh SQL

			System.out.println(sql);
			ketQua = st.executeUpdate();
			
			// Bước 4:
			System.out.println("Bạn đã thực thi: "+ sql);
			System.out.println("Có "+ ketQua+" dòng bị thay đổi!");
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ketQua;
	}
}