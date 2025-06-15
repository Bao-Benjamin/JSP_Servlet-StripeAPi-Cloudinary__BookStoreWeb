package model;

public class HinhAnhSanPham {
    private int maHinhAnh;
    private SanPham sanPham;
    private String url;
    private String publicId;
    
    public HinhAnhSanPham() {
    }
    
   
	public HinhAnhSanPham(int maHinhAnh, SanPham sanPham, String url, String publicId) {
		super();
		this.maHinhAnh = maHinhAnh;
		this.sanPham = sanPham;
		this.url = url;
		this.publicId = publicId;
	}
	

	public int getMaHinhAnh() {
		return maHinhAnh;
	}


	public void setMaHinhAnh(int maHinhAnh) {
		this.maHinhAnh = maHinhAnh;
	}


	public SanPham getSanPham() {
		return sanPham;
	}


	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getPublicId() {
		return publicId;
	}


	public void setPublicId(String publicId) {
		this.publicId = publicId;
	}


	@Override
	public String toString() {
		return "HinhAnhSanPham [maHinhAnh=" + maHinhAnh + ", maSanPham=" + sanPham + ", url=" + url + ", publicId="
				+ publicId + "]";
	}
    
}