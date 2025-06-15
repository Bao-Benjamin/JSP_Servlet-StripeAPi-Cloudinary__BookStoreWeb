package model;

public class ChiTietGioHang {
	private int maChiTietGioHang;
    private GioHang gioHang;
    private SanPham sanPham;
    private int soLuong;
    
    
	public ChiTietGioHang() {
		super();
	}
	public ChiTietGioHang(int maChiTietGioHang, GioHang gioHang, SanPham sanPham, int soLuong) {
		super();
		this.maChiTietGioHang = maChiTietGioHang;
		this.gioHang = gioHang;
		this.sanPham = sanPham;
		this.soLuong = soLuong;
	}
	public double tinhThanhTien() {
        double giaBan = 0;
        if (sanPham != null) {
            if (sanPham.getGiaGiam() > 0 && sanPham.getGiaGiam() < sanPham.getGiaBan()) {
                giaBan = sanPham.getGiaGiam();
            } else {
                giaBan = sanPham.getGiaBan();
            }
        }
        return giaBan * soLuong;
    }
	
	public int getMaChiTietGioHang() {
		return maChiTietGioHang;
	}
	public void setMaChiTietGioHang(int maChiTietGioHang) {
		this.maChiTietGioHang = maChiTietGioHang;
	}
	public GioHang getGioHang() {
		return gioHang;
	}
	public void setGioHang(GioHang gioHang) {
		this.gioHang = gioHang;
	}
	public SanPham getSanPham() {
		return sanPham;
	}
	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	@Override
	public String toString() {
		return "ChiTietGioHang [maChiTietGioHang=" + maChiTietGioHang + ", gioHang=" + gioHang + ", sanPham=" + sanPham
				+ ", soLuong=" + soLuong + "]";
	}
    
    
}
