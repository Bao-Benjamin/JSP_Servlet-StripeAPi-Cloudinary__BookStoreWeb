<%@page import="java.util.List"%>
<%@page import="model.SanPham"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chỉnh Sửa Sản Phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            margin: 20px;
        }
        .container {
            max-width: 800px;
            margin: auto;
            background-color: #fff;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
        }
        h2 {
            color: #343a40;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #e9ecef;
        }
        label {
            font-weight: 500;
            margin-top: 10px;
            display: block;
        }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            margin-top: 5px;
            border: 1px solid #ced4da;
            border-radius: 4px;
        }
        button {
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #0d6efd;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        button:hover {
            background-color: #0b5ed7;
        }
        .image-preview {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 10px;
        }
        .image-container {
            position: relative;
            display: inline-block;
            margin-right: 10px;
            margin-bottom: 10px;
        }
        .product-image {
            width: 100px;
            height: 100px;
            object-fit: contain;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            background-color: #f8f9fa;
        }
        .image-url {
            font-size: 12px;
            word-break: break-all;
            max-width: 100px;
        }
        .preview-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 10px;
        }
        .preview-image {
            width: 100px;
            height: 100px;
            object-fit: contain;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            background-color: #f8f9fa;
        }
        .form-check {
            margin-top: 10px;
        }
        .btn-container {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Chỉnh Sửa Sản Phẩm</h2>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </c:if>
        
        <form action="chinh-sua-san-pham" method="post" enctype="multipart/form-data">
            <input type="hidden" name="maSanPham" value="${not empty updateProduct.maSanPham ? updateProduct.maSanPham : sanpham.maSanPham}">
            
            <div class="row">
                <div class="col-md-6">
                    <label for="tenSanPham">Tên Sản Phẩm:</label>
                    <input type="text" id="tenSanPham" name="tenSanPham" value="${not empty updateProduct.tenSanPham ? updateProduct.tenSanPham : sanpham.tenSanPham}" required>
                    
                    <label for="tacGia">Tác Giả:</label>
                    <input type="text" id="tacGia" name="tacGia" value="${not empty updateProduct.tacGia ? updateProduct.tacGia : sanpham.tacGia}" required>
                    
                    <label for="namXuatBan">Năm Xuất Bản:</label>
                    <input type="number" id="namXuatBan" name="namXuatBan" value="${not empty updateProduct.namXuatBan ? updateProduct.namXuatBan : sanpham.namXuatBan}" required>
                    
                    <label for="ngonNgu">Ngôn Ngữ:</label>
                    <input type="text" id="ngonNgu" name="ngonNgu" value="${not empty updateProduct.ngonNgu ? updateProduct.ngonNgu : sanpham.ngonNgu}" required>
                    
                    <label for="theLoai">Thể Loại:</label>
                    <select name="matheloai" id="theLoai" class="form-control" required>
                        <c:forEach var="theLoai" items="${danhSachTheLoai}">
                            <option value="${theLoai.maTheLoai}" 
                                ${theLoai.maTheLoai == sanpham.theLoai.maTheLoai ? 'selected' : ''}>
                                ${theLoai.tenTheLoai}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-md-6">
                    <label for="giaNhap">Giá Nhập:</label>
                    <input type="number" step="1000" id="giaNhap" name="giaNhap" value="${not empty updateProduct.giaNhap ? updateProduct.giaNhap : sanpham.giaNhap}" required>
                    
                    <label for="giaGoc">Giá Gốc:</label>
                    <input type="number" step="1000" id="giaGoc" name="giaGoc" value="${not empty updateProduct.giaGoc ? updateProduct.giaGoc : sanpham.giaGoc}" required>
                    
                    <label for="giaBan">Giá Bán:</label>
                    <input type="number" step="1000" id="giaBan" name="giaBan" value="${not empty updateProduct.giaBan ? updateProduct.giaBan : sanpham.giaBan}" required>
                    
                    <label for="giaGiam">Giá Giảm:</label>
                    <input type="number" step="1000" id="giaGiam" name="giaGiam" value="${not empty updateProduct.giaGiam ? updateProduct.giaGiam : sanpham.giaGiam}">
                    
                    <label for="soLuong">Số Lượng:</label>
                    <input type="number" id="soLuong" name="soLuong" value="${not empty updateProduct.soLuong ? updateProduct.soLuong : sanpham.soLuong}" required>
                </div>
            </div>
            
            <label for="moTa">Mô Tả:</label>
            <textarea id="moTa" name="moTa" rows="4">${not empty updateProduct.moTa ? updateProduct.moTa : sanpham.moTa}</textarea>
            
            <!-- Hiển thị hình ảnh hiện tại -->
            <label>Hình ảnh hiện tại:</label>
            <div class="image-preview">
                <c:if test="${not empty danhSachHinhAnh}">
                    <c:forEach var="hinhAnh" items="${danhSachHinhAnh}" varStatus="status">
                        <div class="image-container">
                            <img src="${hinhAnh.url}" alt="Hình ảnh sản phẩm" class="product-image">
                            <div class="image-url">${hinhAnh.url}</div>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty danhSachHinhAnh}">
                    <p>Không có hình ảnh</p>
                </c:if>
            </div>
            
            <!-- Thêm hình ảnh mới -->
            <label for="hinhAnh">Thêm hình ảnh mới:</label>
            <input type="file" id="hinhAnh" name="hinhAnh" multiple accept="image/*" onchange="previewImages(this)">
            <div class="form-text">Có thể chọn nhiều hình ảnh (tối đa 5 hình)</div>
            
            <div class="preview-container" id="imagePreview"></div>
            
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="xoaHinhAnhCu" name="xoaHinhAnhCu">
                <label class="form-check-label" for="xoaHinhAnhCu">
                    Xóa tất cả hình ảnh cũ khi thêm hình ảnh mới
                </label>
            </div>
            
            <div class="btn-container">
                <a href="xu-ly-phan-trang" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save"></i> Lưu Thay Đổi
                </button>
            </div>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function previewImages(input) {
            var preview = document.getElementById('imagePreview');
            preview.innerHTML = '';
            
            if (input.files) {
                var filesAmount = input.files.length;
                
                if (filesAmount > 5) {
                    alert('Chỉ được chọn tối đa 5 hình ảnh!');
                    input.value = '';
                    return;
                }
                
                for (var i = 0; i < filesAmount; i++) {
                    var reader = new FileReader();
                    
                    reader.onload = function(event) {
                        var img = document.createElement('img');
                        img.src = event.target.result;
                        img.className = 'preview-image';
                        preview.appendChild(img);
                    }
                    
                    reader.readAsDataURL(input.files[i]);
                }
            }
        }
    </script>
</body>
</html>