package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import model.SanPham;
import model.TacGia;
import model.TheLoai;

public class SanPhamDAO implements DAOInterface<SanPham> {

    private Gson gson = new Gson();

    // Chuyển List<String> thành JSON
    private String convertListToJson(List<String> list) {
        return gson.toJson(list);
    }

    // Chuyển JSON thành List<String>
    private List<String> convertJsonToList(String json) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, listType);
    }

    @Override
    public ArrayList<SanPham> selectAll() {
        ArrayList<SanPham> ketQua = new ArrayList<>();
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "select * from SanPham";
            PreparedStatement st = con.prepareStatement(sql);
            
            System.out.println(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                Integer maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                // Lấy chuỗi JSON từ DB rồi chuyển thành List<String>
//                String jsonDuongDanAnh = rs.getString("duongdananh");
                Double giaGiam = rs.getDouble("giaGiam");
//                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                
                // Lấy thông tin TheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Giả sử SanPham có constructor: (int, String, String, int, double, double, double, int, TheLoai, String, String, List<String>)
                SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
                ketQua.add(sp);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    
    public ArrayList<SanPham> selectSanPhamSale() {
        ArrayList<SanPham> ketQua = new ArrayList<>();
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "select * from SanPham where giagiam > 0";
            PreparedStatement st = con.prepareStatement(sql);
            
            System.out.println(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                Integer maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                // Lấy chuỗi JSON từ DB rồi chuyển thành List<String>
//                String jsonDuongDanAnh = rs.getString("duongdananh");
                Double giaGiam = rs.getDouble("giaGiam");
//                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                
                // Lấy thông tin TheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Giả sử SanPham có constructor: (int, String, String, int, double, double, double, int, TheLoai, String, String, List<String>)
                SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
                ketQua.add(sp);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    
    public int countSanPhamSale() {
        int count = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM sanpham WHERE giagiam > 0";
            PreparedStatement st = con.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total");
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return count;
    }
    public int countSanPhamSGK() {
        int count = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS total_sach_giao_khoa\r\n"
            		+ "FROM sanpham\r\n"
            		+ "JOIN theloai ON sanpham.matheloai = theloai.maTheLoai\r\n"
            		+ "WHERE theloai.tenTheLoai = 'Sach giao khoa'";
            PreparedStatement st = con.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total_sach_giao_khoa");
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return count;
    }
    public int countSanPhamVPP() {
        int count = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS total_van_phong_pham\r\n"
            		+ "FROM sanpham\r\n"
            		+ "JOIN theloai ON sanpham.matheloai = theloai.maTheLoai\r\n"
            		+ "WHERE theloai.tenTheLoai = 'Van phong pham'";
            PreparedStatement st = con.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total_van_phong_pham");
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return count;
    }
    public int countSanPhamToys() {
        int count = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(*) AS total_sach_giao_khoa\r\n"
            		+ "FROM sanpham\r\n"
            		+ "JOIN theloai ON sanpham.matheloai = theloai.maTheLoai\r\n"
            		+ "WHERE theloai.tenTheLoai = 'Do choi'";
            PreparedStatement st = con.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                count = rs.getInt("total_sach_giao_khoa");
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return count;
    }
    public ArrayList<SanPham> selectSanPhamSaleWithPagination(int start, int recordsPerPage) {
        ArrayList<SanPham> ketQua = new ArrayList<SanPham>();
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE giagiam IS NOT NULL AND giagiam > 0 LIMIT ? OFFSET ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, recordsPerPage);
            st.setInt(2, start);
            
            ResultSet rs = st.executeQuery();
            
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                Double giaGiam = rs.getDouble("giagiam");
                
                // Lấy thông tin thể loại
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Tạo đối tượng SanPham và thêm vào danh sách kết quả
                SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa, giaGiam);
                ketQua.add(sp);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    
    public ArrayList<SanPham> selectSanPhamSGKWithPagination(int start, int recordsPerPage) {
        ArrayList<SanPham> ketQua = new ArrayList<SanPham>();
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT sanpham.*\r\n"
            		+ "FROM sanpham\r\n"
            		+ "JOIN theloai ON sanpham.matheloai = theloai.maTheLoai\r\n"
            		+ "WHERE theloai.tenTheLoai = 'Sach giao khoa'\r\n"
            		+ "LIMIT ? OFFSET ?;\r\n";
            		
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, recordsPerPage);
            st.setInt(2, start);
            
            ResultSet rs = st.executeQuery();
            System.out.println("sql: "+sql);
            System.out.println("da chạy vào selectSGK trong sanpham dao");
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                Double giaGiam = rs.getDouble("giagiam");
                
                // Lấy thông tin thể loại
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Tạo đối tượng SanPham và thêm vào danh sách kết quả
                SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa, giaGiam);
                System.out.println("++++++++++ san pham SGK: "+ sp);
                ketQua.add(sp);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    public ArrayList<SanPham> selectSanPhamVPPWithPagination(int start, int recordsPerPage) {
        ArrayList<SanPham> ketQua = new ArrayList<SanPham>();
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT sanpham.*\r\n"
            		+ "FROM sanpham\r\n"
            		+ "JOIN theloai ON sanpham.matheloai = theloai.maTheLoai\r\n"
            		+ "WHERE theloai.tenTheLoai = 'Van phong pham'\r\n"
            		+ "LIMIT ? OFFSET ?;\r\n";
            		
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, recordsPerPage);
            st.setInt(2, start);
            
            ResultSet rs = st.executeQuery();
            System.out.println("sql: "+sql);
            System.out.println("da chạy vào selectVPP trong sanpham dao");
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                Double giaGiam = rs.getDouble("giagiam");
                
                // Lấy thông tin thể loại
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Tạo đối tượng SanPham và thêm vào danh sách kết quả
                SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa, giaGiam);
                System.out.println("++++++++++ san pham VPP: "+ sp);
                ketQua.add(sp);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    public ArrayList<SanPham> selectSanPhamToysWithPagination(int start, int recordsPerPage) {
        ArrayList<SanPham> ketQua = new ArrayList<SanPham>();
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT sanpham.*\r\n"
            		+ "FROM sanpham\r\n"
            		+ "JOIN theloai ON sanpham.matheloai = theloai.maTheLoai\r\n"
            		+ "WHERE theloai.tenTheLoai = 'Do choi'\r\n"
            		+ "LIMIT ? OFFSET ?;\r\n";
            		
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, recordsPerPage);
            st.setInt(2, start);
            
            ResultSet rs = st.executeQuery();
            System.out.println("sql: "+sql);
            System.out.println("da chạy vào selectToys trong sanpham dao");
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                Double giaGiam = rs.getDouble("giagiam");
                
                // Lấy thông tin thể loại
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Tạo đối tượng SanPham và thêm vào danh sách kết quả
                SanPham sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa, giaGiam);
                System.out.println("++++++++++ san pham toys: "+ sp);
                ketQua.add(sp);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }

    @Override
    public SanPham selectById(SanPham t) {
        SanPham ketQua = null;        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "select * from SanPham where masanpham = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, t.getMaSanPham());
            
            System.out.println(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tenTacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                String jsonDuongDanAnh = rs.getString("duongdananh");
                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                Double giaGiam = rs.getDouble("giaGiam");
             
                // Lấy đối tượng TheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                System.out.println("theloai in DAO: "+tl);
                // Giả sử SanPham có constructor phù hợp
                ketQua = new SanPham(maSanPham, tenSanPham, tenTacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    
    public SanPham selectById(int t) {
        SanPham ketQua = null;        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "select * from SanPham where masanpham = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, t);
            
            System.out.println(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tenTacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                String jsonDuongDanAnh = rs.getString("duongdananh");
                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                Double giaGiam = rs.getDouble("giaGiam");
             
                // Lấy đối tượng TheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                System.out.println("theloai in DAO: "+tl);
                // Giả sử SanPham có constructor phù hợp
                ketQua = new SanPham(maSanPham, tenSanPham, tenTacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa, giaGiam);
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    
    public SanPham getSanPhamById(int id) {
        SanPham sp = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM SanPham WHERE masanpham = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, id);
            System.out.println(sql + " with id = " + id);
            ResultSet rs = st.executeQuery();
            
            if (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                String jsonDuongDanAnh = rs.getString("duongdananh");
                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                Double giaGiam = rs.getDouble("giaGiam");
                // Lấy thông tin thể loại dựa trên maTheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));
                
                // Tạo đối tượng SanPham với constructor phù hợp
                sp = new SanPham(maSanPham, tenSanPham, tacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return sp;
    }


    @Override
    public int insert(SanPham t) {
        int ketQua = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "insert into SanPham(tensanpham, tacgia, namxuatban, gianhap, giagoc, giaban, soluong, matheloai, ngonngu, mota, duongdananh,giagiam) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            
            st.setString(1, t.getTenSanPham());
            st.setString(2, t.getTacGia());
            st.setInt(3, t.getNamXuatBan());
            st.setDouble(4, t.getGiaNhap());
            st.setDouble(5, t.getGiaGoc());
            st.setDouble(6, t.getGiaBan());
            st.setInt(7, t.getSoLuong());
            st.setInt(8, t.getTheLoai().getMaTheLoai());
            st.setString(9, t.getNgonNgu());
            st.setString(10, t.getMoTa());
            // Chuyển List<String> thành JSON để lưu vào DB
//            st.setString(11, convertListToJson(t.getDuongDanAnh()));
            st.setDouble(12, t.getGiaGiam());
            System.out.println(sql);
            ketQua = st.executeUpdate();
            
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }

    public int themSanPham(SanPham sanPham) {
    	Connection con = JDBCUtil.getConnection();
        String sql = "INSERT INTO sanpham (tensanpham, tacgia, namxuatban, gianhap, giagoc, giaban, giagiam, soluong, matheloai, ngonngu, mota) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
//        	PreparedStatement ps = con.prepareStatement(sql);
        	PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        	
            ps.setString(1, sanPham.getTenSanPham());
            ps.setString(2, sanPham.getTacGia());
            ps.setInt(3, sanPham.getNamXuatBan());
            ps.setDouble(4, sanPham.getGiaNhap());
            ps.setDouble(5, sanPham.getGiaGoc());
            ps.setDouble(6, sanPham.getGiaBan());
            ps.setDouble(7, sanPham.getGiaGiam());
            ps.setInt(8, sanPham.getSoLuong());
            ps.setInt(9, sanPham.getTheLoai().getMaTheLoai());
            ps.setString(10, sanPham.getNgonNgu());
            ps.setString(11, sanPham.getMoTa());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    @Override
    public int insertAll(ArrayList<SanPham> arr) {
        int dem = 0;
        for (SanPham sanPham : arr) {
            dem += this.insert(sanPham);
        }
        return dem;
    }

    @Override
    public int delete(SanPham t) {
        int ketQua = 0;
        System.out.println("da vào hàm delete trong sanpham dao với maSP là: "+t.getMaSanPham());
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "delete from sanpham where masanpham = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, t.getMaSanPham());
            
            System.out.println(sql);
            ketQua = st.executeUpdate();
            
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }

    @Override
    public int deleteAll(ArrayList<SanPham> arr) {
        int dem = 0;
        for (SanPham sanPham : arr) {
            dem += this.delete(sanPham);
        }
        return dem;
    }

    @Override
    public int update(SanPham t) {
        int ketQua = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "update sanpham set " +
                    "tensanpham = ?, " +
                    "tacgia = ?, " +
                    "namxuatban = ?, " +
                    "gianhap = ?, " +
                    "giagoc = ?, " +
                    "giaban = ?, " +
                    "soluong = ?, " +
                    "matheloai = ?, " +
                    "ngonngu = ?, " +
                    "mota = ?, " +
//                    "duongdananh = ?, " +
                    "giagiam = ? " +
                    "where masanpham = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getTenSanPham());
            st.setString(2, t.getTacGia());
            st.setInt(3, t.getNamXuatBan());
            st.setDouble(4, t.getGiaNhap());
            st.setDouble(5, t.getGiaGoc());
            st.setDouble(6, t.getGiaBan());
            st.setInt(7, t.getSoLuong());
            st.setInt(8, t.getTheLoai().getMaTheLoai());
            st.setString(9, t.getNgonNgu());
            st.setString(10, t.getMoTa());
//            st.setString(11, convertListToJson(t.getDuongDanAnh()));
            st.setDouble(11, t.getGiaGiam());
            st.setInt(12, t.getMaSanPham());
            
            System.out.println(sql);
            ketQua = st.executeUpdate();
            
            System.out.println("Bạn đã thực thi: " + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    public List<SanPham> selectByCategory(String tenTheLoai) {
        List<SanPham> ketQua = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham sp JOIN theloai tl ON sp.matheloai = tl.maTheLoai WHERE tl.tenTheLoai = ?";
            st = con.prepareStatement(sql);
            st.setString(1, tenTheLoai);

            rs = st.executeQuery();

            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tenTacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                String jsonDuongDanAnh = rs.getString("duongdananh");
                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                Double giaGiam = rs.getDouble("giaGiam");
                // Lấy đối tượng TheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));

                // Tạo đối tượng SanPham và thêm vào danh sách kết quả
                SanPham sanPham = new SanPham(maSanPham, tenSanPham, tenTacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
                ketQua.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) JDBCUtil.closeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ketQua;
    }
 // Thêm phương thức này vào SanPhamDAO.java
    public List<SanPham> selectByTheLoai(int maTheLoai) {
        List<SanPham> ketQua = new ArrayList<>();
        try {
            // Bước 1: tạo kết nối đến CSDL
            Connection con = JDBCUtil.getConnection();
            
            // Bước 2: tạo ra đối tượng statement
            String sql = "SELECT * FROM sanpham WHERE matheloai = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, maTheLoai);
            
            // Bước 3: thực thi câu lệnh SQL
            System.out.println(sql);
            ResultSet rs = st.executeQuery();
            
            // Bước 4:
            while (rs.next()) {
                SanPham sp = new SanPham();
                sp.setMaSanPham(rs.getInt("masanpham"));
                sp.setTenSanPham(rs.getString("tensanpham"));
                sp.setGiaBan(rs.getDouble("giaban"));
                sp.setGiaGiam(rs.getDouble("giagiam"));
                sp.setMoTa(rs.getString("mota"));
                sp.setTacGia(rs.getString("tacgia"));
                sp.setSoLuong(rs.getInt("soluong"));
                
                // Lấy thông tin thể loại
                TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
                TheLoai temp = new TheLoai();
                temp.setMaTheLoai(rs.getInt("matheloai"));
                TheLoai theLoai = theLoaiDAO.selectById(temp);
                sp.setTheLoai(theLoai);
                
                ketQua.add(sp);
            }
            
            // Bước 5:
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return ketQua;
    }
    
    public List<SanPham> selectByStringCategory(String tenTheLoai) {
        List<SanPham> ketQua = new ArrayList<>();
        Connection con = null;
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham sp JOIN theloai tl ON sp.matheloai = tl.maTheLoai WHERE tl.tenTheLoai = ?";
            st = con.prepareStatement(sql);
            st.setString(1, tenTheLoai);

            rs = st.executeQuery();

            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tenTacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
                String jsonDuongDanAnh = rs.getString("duongdananh");
                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                Double giaGiam = rs.getDouble("giaGiam");
                // Lấy đối tượng TheLoai
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));

                // Tạo đối tượng SanPham và thêm vào danh sách kết quả
                SanPham sanPham = new SanPham(maSanPham, tenSanPham, tenTacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
                ketQua.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (con != null) JDBCUtil.closeConnection(con);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return ketQua;
    }
    
    public List<SanPham> searchSanPham(String query) {
        List<SanPham> ketQua = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM SanPham WHERE tensanpham LIKE ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, "%" + query + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                String tenTacGia = rs.getString("tacgia");
                int namXuatBan = rs.getInt("namxuatban");
                double giaNhap = rs.getDouble("gianhap");
                double giaGoc = rs.getDouble("giagoc");
                double giaBan = rs.getDouble("giaban");
                int soLuong = rs.getInt("soluong");
                int maTheLoai = rs.getInt("matheloai");
                String ngonNgu = rs.getString("ngonngu");
                String moTa = rs.getString("mota");
//                String jsonDuongDanAnh = rs.getString("duongdananh");
//                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
                Double giaGiam = rs.getDouble("giaGiam");
                TheLoaiDAO tld = new TheLoaiDAO();
                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));

                SanPham sanPham = new SanPham(maSanPham, tenSanPham, tenTacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
                ketQua.add(sanPham);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    public List<SanPham> getBooksByPage(int start, int recordsPerPage){
    	System.out.println("da chay vao getBooksByPage trong sanpham DAO");
    	List<SanPham> books = new ArrayList<SanPham>();
    	 try {
			Connection con = JDBCUtil.getConnection();
			 String sql = "SELECT * FROM sanpham ORDER BY masanpham DESC LIMIT ? Offset ?";
			 PreparedStatement st = con.prepareStatement(sql);
			 st.setInt(2, start);
			 st.setInt(1, recordsPerPage);
			 ResultSet rs = st.executeQuery();
			 System.out.println(sql);
			 while (rs.next()) {
				 int maSanPham = rs.getInt("masanpham");
	                String tenSanPham = rs.getString("tensanpham");
	                String tenTacGia = rs.getString("tacgia");
	                int namXuatBan = rs.getInt("namxuatban");
	                double giaNhap = rs.getDouble("gianhap");
	                double giaGoc = rs.getDouble("giagoc");
	                double giaBan = rs.getDouble("giaban");
	                int soLuong = rs.getInt("soluong");
	                int maTheLoai = rs.getInt("matheloai");
	                String ngonNgu = rs.getString("ngonngu");
	                String moTa = rs.getString("mota");
//	                String jsonDuongDanAnh = rs.getString("duongdananh");
//	                List<String> duongDanAnh = convertJsonToList(jsonDuongDanAnh);
	                Double giaGiam = rs.getDouble("giaGiam");
	                TheLoaiDAO tld = new TheLoaiDAO();
	                TheLoai tl = tld.selectById(new TheLoai(maTheLoai, null));

	                SanPham sanPham = new SanPham(maSanPham, tenSanPham, tenTacGia, namXuatBan, giaNhap, giaGoc, giaBan, soLuong, tl, ngonNgu, moTa,giaGiam);
//	                System.out.println(sanPham);
	                books.add(sanPham);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 return books;
    }
    public int getTotalBooks() {
    	 int totalBooks = 0;
    	 Connection con = JDBCUtil.getConnection();
		 String sql = "SELECT COUNT(*) FROM sanpham";
		 System.out.println(sql);
		 try {
			PreparedStatement st = con.prepareStatement(sql);
			 ResultSet rs = st.executeQuery() ;
			        if (rs.next()) {
			            totalBooks = rs.getInt(1);
			        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return totalBooks;
		 
    }
    public int tangSoLuong(SanPham t, int sluong) {
    	 int ketQua = 0;
         
         try {
             Connection con = JDBCUtil.getConnection();
             String sql = "update sanpham set " +
                     "tensanpham = ?, " +
                     "tacgia = ?, " +
                     "namxuatban = ?, " +
                     "gianhap = ?, " +
                     "giagoc = ?, " +
                     "giaban = ?, " +
                     "soluong = ?, " +
                     "matheloai = ?, " +
                     "ngonngu = ?, " +
                     "mota = ?, " +
//                     "duongdananh = ?, " +
                     "giagiam = ? " +
                     "where masanpham = ?";
             PreparedStatement st = con.prepareStatement(sql);
             st.setString(1, t.getTenSanPham());
             st.setString(2, t.getTacGia());
             st.setInt(3, t.getNamXuatBan());
             st.setDouble(4, t.getGiaNhap());
             st.setDouble(5, t.getGiaGoc());
             st.setDouble(6, t.getGiaBan());
             st.setInt(7, t.getSoLuong()+sluong);
             st.setInt(8, t.getTheLoai().getMaTheLoai());
             st.setString(9, t.getNgonNgu());
             st.setString(10, t.getMoTa());
//             st.setString(11, convertListToJson(t.getDuongDanAnh()));
             st.setDouble(11, t.getGiaGiam());
             st.setInt(12, t.getMaSanPham());
             
             System.out.println(sql);
             ketQua = st.executeUpdate();
             
             System.out.println("Bạn đã thực thi: tăng số lượng " + sql);
             System.out.println("Có " + ketQua + " dòng bị thay đổi!");
             
             JDBCUtil.closeConnection(con);
         } catch (Exception e) {
             e.printStackTrace();
         }
         
         return ketQua;
    }
    
    public int truSoLuong(SanPham t, int sluong) {
   	 int ketQua = 0;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "update sanpham set " +
                    "tensanpham = ?, " +
                    "tacgia = ?, " +
                    "namxuatban = ?, " +
                    "gianhap = ?, " +
                    "giagoc = ?, " +
                    "giaban = ?, " +
                    "soluong = ?, " +
                    "matheloai = ?, " +
                    "ngonngu = ?, " +
                    "mota = ?, " +
//                    "duongdananh = ?, " +
                    "giagiam = ? " +
                    "where masanpham = ?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, t.getTenSanPham());
            st.setString(2, t.getTacGia());
            st.setInt(3, t.getNamXuatBan());
            st.setDouble(4, t.getGiaNhap());
            st.setDouble(5, t.getGiaGoc());
            st.setDouble(6, t.getGiaBan());
            st.setInt(7, t.getSoLuong() - sluong);
            st.setInt(8, t.getTheLoai().getMaTheLoai());
            st.setString(9, t.getNgonNgu());
            st.setString(10, t.getMoTa());
//            st.setString(11, convertListToJson(t.getDuongDanAnh()));
            st.setDouble(11, t.getGiaGiam());
            st.setInt(12, t.getMaSanPham());
            
            System.out.println(sql);
            ketQua = st.executeUpdate();
            
            System.out.println("Bạn đã thực thi: trừ số lượng" + sql);
            System.out.println("Có " + ketQua + " dòng bị thay đổi!");
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ketQua;
   }
 // Thêm các phương thức này vào SanPhamDAO.java

 // Lấy sản phẩm theo thể loại với phân trang
 public List<SanPham> getBooksByCategory(int maTheLoai, int start, int recordsPerPage) {
     List<SanPham> ketQua = new ArrayList<>();
     try {
         // Bước 1: tạo kết nối đến CSDL
         Connection con = JDBCUtil.getConnection();
         
         // Bước 2: tạo ra đối tượng statement
         String sql = "SELECT * FROM sanpham WHERE matheloai = ? LIMIT ?, ?";
         PreparedStatement st = con.prepareStatement(sql);
         st.setInt(1, maTheLoai);
         st.setInt(2, start);
         st.setInt(3, recordsPerPage);
         
         // Bước 3: thực thi câu lệnh SQL
         System.out.println(sql);
         ResultSet rs = st.executeQuery();
         
         // Bước 4:
         while (rs.next()) {
             SanPham sp = new SanPham();
             sp.setMaSanPham(rs.getInt("masanpham"));
             sp.setTenSanPham(rs.getString("tensanpham"));
             sp.setGiaBan(rs.getDouble("giaban"));
             sp.setGiaGiam(rs.getDouble("giagiam"));
             sp.setMoTa(rs.getString("mota"));
             sp.setTacGia(rs.getString("tacgia"));
             sp.setSoLuong(rs.getInt("soluong"));
             
             // Lấy thông tin thể loại
             TheLoaiDAO theLoaiDAO = new TheLoaiDAO();
             TheLoai temp = new TheLoai();
             temp.setMaTheLoai(rs.getInt("matheloai"));
             TheLoai theLoai = theLoaiDAO.selectById(temp);
             sp.setTheLoai(theLoai);
             
             ketQua.add(sp);
         }
         
         // Bước 5:
         JDBCUtil.closeConnection(con);
     } catch (SQLException e) {
         e.printStackTrace();
     }
     
     return ketQua;
 }

 // Đếm tổng số sản phẩm theo thể loại
 public int getTotalBooksByCategory(int maTheLoai) {
     int count = 0;
     try {
         // Bước 1: tạo kết nối đến CSDL
         Connection con = JDBCUtil.getConnection();
         
         // Bước 2: tạo ra đối tượng statement
         String sql = "SELECT COUNT(*) AS total FROM sanpham WHERE matheloai = ?";
         PreparedStatement st = con.prepareStatement(sql);
         st.setInt(1, maTheLoai);
         
         // Bước 3: thực thi câu lệnh SQL
         System.out.println(sql);
         ResultSet rs = st.executeQuery();
         
         // Bước 4:
         if (rs.next()) {
             count = rs.getInt("total");
         }
         
         // Bước 5:
         JDBCUtil.closeConnection(con);
     } catch (SQLException e) {
         e.printStackTrace();
     }
     
     return count;
 }
}
