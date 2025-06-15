package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Part;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import database.HinhAnhSanPhamDAO;
import database.SanPhamDAO;
import model.HinhAnhSanPham;
import model.SanPham;
import util.CloudinaryUtil;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
public class SanPhamService {
    private SanPhamDAO sanPhamDAO;
    private HinhAnhSanPhamDAO hinhAnhDAO;
    private Cloudinary cloudinary;
    
    public SanPhamService() {
        sanPhamDAO = new SanPhamDAO();
        hinhAnhDAO = new HinhAnhSanPhamDAO();
        cloudinary = CloudinaryUtil.getCloudinary();
    }
    
    public byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }
    public boolean themSanPhamVaHinhAnh(SanPham sanPham, List<Part> hinhAnhParts) {
        try {
            // Thêm sản phẩm vào database
        	System.out.println("Bắt đầu thêm sản phẩm: " + sanPham);
            int maSanPham = sanPhamDAO.themSanPham(sanPham);
            
            if (maSanPham == -1) {
            	System.out.println("Thêm sản phẩm thất bại");
                return false;
            }
            if (hinhAnhParts == null || hinhAnhParts.isEmpty()) {
                System.out.println("Không có hình ảnh để upload");
                return true; // Vẫn thành công nếu không có hình ảnh
            }
            
            System.out.println("Số lượng hình ảnh cần upload: " + hinhAnhParts.size());
            // Upload hình ảnh lên Cloudinary và lưu thông tin vào database
            List<HinhAnhSanPham> danhSachHinhAnh = new ArrayList<>();
            
            for (Part part : hinhAnhParts) {
                if (part != null && part.getSize() > 0) {
                	System.out.println("Đang upload hình ảnh: " + part.getSubmittedFileName() + ", kích thước: " + part.getSize());
                    // Upload lên Cloudinary
                	 InputStream inputStream = part.getInputStream();
                     byte[] fileBytes = convertInputStreamToByteArray(inputStream);
                    Map uploadResult = cloudinary.uploader().upload(
                    		 fileBytes, 
                        ObjectUtils.asMap("resource_type", "auto")
                    );
                    
                    // Lấy thông tin từ kết quả upload
                    String url = (String) uploadResult.get("secure_url");
                    String publicId = (String) uploadResult.get("public_id");
                    SanPham sp = new SanPham();
                    sp.setMaSanPham(maSanPham);
                    // Tạo đối tượng HinhAnhSanPham
                    HinhAnhSanPham hinhAnh = new HinhAnhSanPham(0,sp, url, publicId);
                    danhSachHinhAnh.add(hinhAnh);
                }
            }
            
            // Lưu danh sách hình ảnh vào database
            if (!danhSachHinhAnh.isEmpty()) {
            	boolean ketQua = hinhAnhDAO.themNhieuHinhAnh(danhSachHinhAnh);
                System.out.println("Kết quả thêm hình ảnh: " + ketQua);
                if (!ketQua) {
                    System.out.println("Thêm hình ảnh thất bại");
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi trong quá trình thêm sản phẩm và hình ảnh: " + e.getMessage());
            
            return false;
        }
    }
    
    public boolean themHinhAnhChoSanPham(SanPham sanPham, List<Part> hinhAnhParts) {
        try {
            if (hinhAnhParts == null || hinhAnhParts.isEmpty()) {
                System.out.println("Không có hình ảnh để upload");
                return true; // Vẫn thành công nếu không có hình ảnh
            }
            
            System.out.println("Số lượng hình ảnh cần upload: " + hinhAnhParts.size());
            // Upload hình ảnh lên Cloudinary và lưu thông tin vào database
            List<HinhAnhSanPham> danhSachHinhAnh = new ArrayList<>();
            
            for (Part part : hinhAnhParts) {
                if (part != null && part.getSize() > 0) {
                    System.out.println("Đang upload hình ảnh: " + part.getSubmittedFileName() + ", kích thước: " + part.getSize());
                    // Upload lên Cloudinary
                    InputStream inputStream = part.getInputStream();
                    byte[] fileBytes = convertInputStreamToByteArray(inputStream);
                    Map uploadResult = cloudinary.uploader().upload(
                        fileBytes, 
                        ObjectUtils.asMap("resource_type", "auto")
                    );
                    
                    // Lấy thông tin từ kết quả upload
                    String url = (String) uploadResult.get("secure_url");
                    String publicId = (String) uploadResult.get("public_id");
                    
                    // Tạo đối tượng HinhAnhSanPham
                    HinhAnhSanPham hinhAnh = new HinhAnhSanPham(0, sanPham, url, publicId);
                    danhSachHinhAnh.add(hinhAnh);
                }
            }
            
            // Lưu danh sách hình ảnh vào database
            if (!danhSachHinhAnh.isEmpty()) {
                boolean ketQua = hinhAnhDAO.themNhieuHinhAnh(danhSachHinhAnh);
                System.out.println("Kết quả thêm hình ảnh: " + ketQua);
                if (!ketQua) {
                    System.out.println("Thêm hình ảnh thất bại");
                    return false;
                }
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lỗi trong quá trình thêm hình ảnh: " + e.getMessage());
            return false;
        }
    }
    
    public boolean xoaHinhAnhTheoSanPham(int maSanPham) {
        try {
            // Lấy danh sách hình ảnh của sản phẩm
            List<HinhAnhSanPham> danhSachHinhAnh = hinhAnhDAO.getSanPhamsByMaSanPham(maSanPham);
            
            // Xóa từng hình ảnh trên Cloudinary
            for (HinhAnhSanPham hinhAnh : danhSachHinhAnh) {
                if (hinhAnh.getPublicId() != null && !hinhAnh.getPublicId().isEmpty()) {
                    cloudinary.uploader().destroy(
                        hinhAnh.getPublicId(),
                        ObjectUtils.emptyMap()
                    );
                }
            }
            
            // Xóa thông tin hình ảnh trong database
            return hinhAnhDAO.xoaHinhAnhTheoSanPham(maSanPham);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean xoaHinhAnh(HinhAnhSanPham hinhAnh) {
        try {
            // Xóa hình ảnh trên Cloudinary
            if (hinhAnh.getPublicId() != null && !hinhAnh.getPublicId().isEmpty()) {
                cloudinary.uploader().destroy(
                    hinhAnh.getPublicId(),
                    ObjectUtils.emptyMap()
                );
            }
            
            // Xóa thông tin hình ảnh trong database
            return hinhAnhDAO.xoaHinhAnh(hinhAnh.getMaHinhAnh());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}