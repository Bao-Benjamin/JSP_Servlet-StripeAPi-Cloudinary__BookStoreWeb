package model;

import java.sql.Timestamp;
import java.util.List;

public class GioHang {
	private int maGioHang;
    private KhachHang khachHang;
    private Timestamp ngayTao;
    private List<ChiTietGioHang> danhSachChiTiet;
	public GioHang() {
		super();
	}
	public GioHang(int maGioHang, KhachHang khachHang, Timestamp ngayTao, List<ChiTietGioHang> danhSachChiTiet) {
		super();
		this.maGioHang = maGioHang;
		this.khachHang = khachHang;
		this.ngayTao = ngayTao;
		this.danhSachChiTiet = danhSachChiTiet;
	}
	public int getMaGioHang() {
		return maGioHang;
	}
	public void setMaGioHang(int maGioHang) {
		this.maGioHang = maGioHang;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public Timestamp getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(Timestamp ngayTao) {
		this.ngayTao = ngayTao;
	}
	public List<ChiTietGioHang> getDanhSachChiTiet() {
		return danhSachChiTiet;
	}
	public void setDanhSachChiTiet(List<ChiTietGioHang> danhSachChiTiet) {
		this.danhSachChiTiet = danhSachChiTiet;
	}
	@Override
	public String toString() {
		return "GioHang [maGioHang=" + maGioHang + ", khachHang=" + khachHang + ", ngayTao=" + ngayTao
				+ ", danhSachChiTiet=" + danhSachChiTiet + "]";
	}
    
    
    
    

}
