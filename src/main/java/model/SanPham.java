package model;

import java.util.List;

public class SanPham {
	private int maSanPham;
	private String tenSanPham;
	private String tacGia;
	private int namXuatBan;
	private double giaNhap;
	private double giaGoc;
	private double giaBan;
	private int soLuong;
	private TheLoai theLoai;
	private String ngonNgu;
	private String moTa;
	private double giaGiam;

	public SanPham() {
	}

	
	public SanPham(int maSanPham, String tenSanPham, String tacGia, int namXuatBan, double giaNhap, double giaGoc,
			double giaBan, int soLuong, TheLoai theLoai, String ngonNgu, String moTa, 
			double giaGiam) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.tacGia = tacGia;
		this.namXuatBan = namXuatBan;
		this.giaNhap = giaNhap;
		this.giaGoc = giaGoc;
		this.giaBan = giaBan;
		this.soLuong = soLuong;
		this.theLoai = theLoai;
		this.ngonNgu = ngonNgu;
		this.moTa = moTa;
//		this.duongDanAnh = duongDanAnh;
		this.giaGiam = giaGiam;
	}



	public int getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(int maSanPham) {
		this.maSanPham = maSanPham;
	}

	public String getTenSanPham() {
		return tenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}

	public String getTacGia() {
		return tacGia;
	}

	public void setTacGia(String tacGia) {
		this.tacGia = tacGia;
	}

	public int getNamXuatBan() {
		return namXuatBan;
	}

	public void setNamXuatBan(int namXuatBan) {
		this.namXuatBan = namXuatBan;
	}

	public double getGiaNhap() {
		return giaNhap;
	}

	public void setGiaNhap(double giaNhap) {
		this.giaNhap = giaNhap;
	}

	public double getGiaGoc() {
		return giaGoc;
	}

	public void setGiaGoc(double giaGoc) {
		this.giaGoc = giaGoc;
	}

	public double getGiaBan() {
		return giaBan;
	}

	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public TheLoai getTheLoai() {
		return theLoai;
	}

	public void setTheLoai(TheLoai theLoai) {
		this.theLoai = theLoai;
	}

	public String getNgonNgu() {
		return ngonNgu;
	}

	public void setNgonNgu(String ngonNgu) {
		this.ngonNgu = ngonNgu;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}



//	public List<String> getDuongDanAnh() {
//		return duongDanAnh;
//	}
//
//
//
//	public void setDuongDanAnh(List<String> duongDanAnh) {
//		this.duongDanAnh = duongDanAnh;
//	}
//
//	public String getFirstImage() {
//	    if (duongDanAnh == null || duongDanAnh.isEmpty()) {
//	        // Ảnh mặc định nếu không có ảnh
//	        return "img/default.png";
//	    }
//	    // Trả về phần tử đầu tiên trong List
//	    return duongDanAnh.get(0);
//	}
	

	public double getGiaGiam() {
		return giaGiam;
	}


	public void setGiaGiam(double giaGiam) {
		this.giaGiam = giaGiam;
	}


	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", tacGia=" + tacGia + ", namXuatBan="
				+ namXuatBan + ", giaNhap=" + giaNhap + ", giaGoc=" + giaGoc + ", giaBan=" + giaBan + ", soLuong="
				+ soLuong + ", theLoai=" + theLoai + ", ngonNgu=" + ngonNgu + ", moTa=" + moTa + ", duongDanAnh="
				+ ", giaGiam=" + giaGiam + "]";
	}


	
	
	
	
}