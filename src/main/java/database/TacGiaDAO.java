package database;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.TacGia;

public class TacGiaDAO implements DAOInterface<TacGia>{
	
	@Override
	public ArrayList<TacGia> selectAll() {
		ArrayList<TacGia> ketQua= new ArrayList<>();
		
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "select * from tacgia";
			PreparedStatement st = con.prepareStatement(sql);
			
			System.out.println(sql);
			ResultSet rs = st.executeQuery();
			
			// Bước 4:
			while(rs.next()) {
				String maTacGia = rs.getString("matacgia");
				String hoVaTen = rs.getString("hovaten");
				Date ngaySinh = rs.getDate("ngaysinh");
				String tieuSu = rs.getString("tieusu");
				
				TacGia tg = new TacGia(maTacGia, hoVaTen, ngaySinh, tieuSu);
				ketQua.add(tg);
			}
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return ketQua;
	}

	@Override
	public TacGia selectById(TacGia t) {
		TacGia ketQua = null;
		
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "select * from tacgia where = ?";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,t.getMaTacGia());
			System.out.println(sql);
			ResultSet rs = st.executeQuery();
			
			// Bước 4:
			while(rs.next()) {
				String maTacGia = rs.getString("matacgia");
				String hoVaTen = rs.getString("hovaten");
				Date ngaySinh = rs.getDate("ngaysinh");
				String tieuSu = rs.getString("tieusu");
				
				ketQua = new TacGia(maTacGia, hoVaTen, ngaySinh, tieuSu);
				break;
				
			}
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return ketQua;
	}





	@Override
	public int insert(TacGia t) {
		int ketQua = 0;
		try {
			Connection con = JDBCUtil.getConnection();
			String sql = "INSERT INTO tacgia(matacgia, hovaten, ngaysinh, tieusu) VALUES (?, ?, ?, null);";
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1,t.getMaTacGia());
			st.setString(2,t.getHoVaTen());
			st.setDate(3,t.getNgaySinh());
			System.out.println(sql);
			ketQua = st.executeUpdate();
			
		
			JDBCUtil.closeConnection(con);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ketQua;
		
	}

	@Override
	public int insertAll(ArrayList<TacGia> arr) {
		int ketqua=0;
		for (TacGia t : arr) {	
			ketqua+=this.insert(t);
		}
		return ketqua;
		
	}

	@Override
	public int delete(TacGia t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();
			
			// Bước 2: tạo ra đối tượng statement
			String sql = "DELETE from tacgia "+
					 " WHERE matacgia=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getMaTacGia());
			
			// Bước 3: thực thi câu lệnh SQL
			System.out.println(sql);
			ketQua = st.executeUpdate();
			
			// Bước 4:
			System.out.println("Bạn đã thực thi: "+ sql);
			System.out.println("Có "+ ketQua+" dòng bị thay đổi!");
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ketQua;
	}

	@Override
	public int deleteAll(ArrayList<TacGia> arr) {
		int dem = 0;
		for (TacGia tacGia : arr) {
			dem+=this.delete(tacGia);
		}
		return dem;
	}

	@Override
	public int update(TacGia t) {
		int ketQua = 0;
		try {
			// Bước 1: tạo kết nối đến CSDL
			Connection con = JDBCUtil.getConnection();
			
			// Bước 2: tạo ra đối tượng statement
			String sql = "UPDATE tacgia "+
					 " SET " +
					 " hovaten=?"+
					 ", ngaysinh=?"+
					 ", tieusu=?"+
					 " WHERE matacgia=?";
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, t.getHoVaTen());
			st.setDate(2, t.getNgaySinh());
			st.setString(3, t.getTieuSu());
			st.setString(4, t.getMaTacGia());
			// Bước 3: thực thi câu lệnh SQL

			System.out.println(sql);
			ketQua = st.executeUpdate();
			
			// Bước 4:
			System.out.println("Bạn đã thực thi: "+ sql);
			System.out.println("Có "+ ketQua+" dòng bị thay đổi!");
			
			// Bước 5:
			JDBCUtil.closeConnection(con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ketQua;
	}
	
	public static void main(String[] args) {
		TacGiaDAO tg = new TacGiaDAO();
//		ArrayList<TacGia> list = new ArrayList<>();
//		list = tg.selectAll();
//		for (TacGia tacGia : list) {
//			System.out.println(" "+tacGia);
//		}
//		tg.insert(new TacGia("TG6","Nguyen Van E",null,null));
		tg.delete(new TacGia("TG6","Nguyen Van E",null,null));
		
		
	}
	
}
