package model;

import java.sql.Date;
import java.util.Objects;

public class DonHang {
	
	private String maDonHang;
	private String tenKhachHang;
	private String soDienThoai;
	private String diaChiNhanHang;
	private String hinhThucThanhToan; 
	private Date ngayDatHang;
	private String ghiChu;
	private String trangThai;
	
	public DonHang() {
		super();
	}
	
	public DonHang(String maDonHang, String tenKhachHang, String soDienThoai, String diaChiNhanHang,
			String hinhThucThanhToan, Date ngayDatHang, String ghiChu, String trangThai) {
		super();
		this.maDonHang = maDonHang;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.diaChiNhanHang = diaChiNhanHang;
		this.hinhThucThanhToan = hinhThucThanhToan;
		this.ngayDatHang = ngayDatHang;
		this.ghiChu = ghiChu;
		this.trangThai = trangThai;
	}

	public DonHang(String maDonHang, String tenKhachHang, String soDienThoai, String diaChiNhanHang,
			String hinhThucThanhToan, Date ngayDatHang, String ghiChu) {
		super();
		this.maDonHang = maDonHang;
		this.tenKhachHang = tenKhachHang;
		this.soDienThoai = soDienThoai;
		this.diaChiNhanHang = diaChiNhanHang;
		this.hinhThucThanhToan = hinhThucThanhToan;
		this.ngayDatHang = ngayDatHang;
		this.ghiChu = ghiChu;
	}

	public String getMaDonHang() {
		return maDonHang;
	}
	public void setMaDonHang(String maDonHang) {
		this.maDonHang = maDonHang;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public String getSoDienThoai() {
		return soDienThoai;
	}
	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	public String getDiaChiNhanHang() {
		return diaChiNhanHang;
	}
	public void setDiaChiNhanHang(String diaChiNhanHang) {
		this.diaChiNhanHang = diaChiNhanHang;
	}
	public String getHinhThucThanhToan() {
		return hinhThucThanhToan;
	}
	public void setHinhThucThanhToan(String hinhThucThanhToan) {
		this.hinhThucThanhToan = hinhThucThanhToan;
	}
	public Date getNgayDatHang() {
		return ngayDatHang;
	}
	public void setNgayDatHang(Date ngayDatHang) {
		this.ngayDatHang = ngayDatHang;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	@Override
	public String toString() {
		return "DonHang [maDonHang=" + maDonHang + ", tenKhachHang=" + tenKhachHang + ", soDienThoai=" + soDienThoai
				+ ", diaChiNhanHang=" + diaChiNhanHang + ", hinhThucThanhToan=" + hinhThucThanhToan + ", ngayDatHang="
				+ ngayDatHang + ", ghiChu=" + ghiChu + "]";
	}
	
	

	
	
}