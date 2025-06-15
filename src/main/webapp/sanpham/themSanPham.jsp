<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm Sản Phẩm</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
     <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
     <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .preview-container {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 10px;
        }
        .image-preview {
            width: 150px;
            height: 150px;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 5px;
            object-fit: cover;
        }
          nav.navbar {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        .nav-item{
        	width:auto;
        }
    </style>
</head>
<body>
<%@ include file="../header.jsp" %>
    <div class="container mt-4">
        <h2>Thêm Sản Phẩm Mới</h2>
        
        <c:if test="${not empty thongBao}">
            <div class="alert alert-danger">${thongBao}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/them-san-pham" method="post" enctype="multipart/form-data">
            <div class="row">
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="tenSanPham">Tên Sản Phẩm:</label>
                        <input type="text" class="form-control" id="tenSanPham" name="tenSanPham" value="${sanPham.tenSanPham}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="tacGia">Tác Giả:</label>
                        <input type="text" class="form-control" id="tacGia" name="tacGia" value="${sanPham.tacGia}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="namXuatBan">Năm Xuất Bản:</label>
                        <input type="number" class="form-control" id="namXuatBan" name="namXuatBan" value="${sanPham.namXuatBan}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="giaNhap">Giá Nhập:</label>
                        <input type="number" step="0.01" class="form-control" id="giaNhap" name="giaNhap" value="${sanPham.giaNhap}" >
                    </div>
                    
                    <div class="form-group">
                        <label for="giaGoc">Giá Gốc:</label>
                        <input type="number" step="0.01" class="form-control" id="giaGoc" name="giaGoc" value="${sanPham.giaGoc}" >
                    </div>
                    
                    <div class="form-group">
                        <label for="giaBan">Giá Bán:</label>
                        <input type="number" step="0.01" class="form-control" id="giaBan" name="giaBan" value="${sanPham.giaBan}" required>
                    </div>
                </div>
                
                <div class="col-md-6">
                    <div class="form-group">
                        <label for="giaGiam">Giá Giảm:</label>
                        <input type="number" step="0.01" class="form-control" id="giaGiam" name="giaGiam" value="${sanPham.giaGiam}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="soLuong">Số Lượng:</label>
                        <input type="number" class="form-control" id="soLuong" name="soLuong" value="${sanPham.soLuong}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="maTheLoai">Thể Loại:</label>
                        <select class="form-control" id="maTheLoai" name="maTheLoai" >
                            <option value="">-- Chọn Thể Loại --</option>
                            <c:forEach var="theLoai" items="${danhSachTheLoai}">
                                <option value="${theLoai.maTheLoai}" >${theLoai.tenTheLoai}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="ngonNgu">Ngôn Ngữ:</label>
                        <input type="text" class="form-control" id="ngonNgu" name="ngonNgu" value="${sanPham.ngonNgu}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="hinhAnh">Hình Ảnh:</label>
                        <input type="file" class="form-control-file" id="hinhAnh" name="hinhAnh" multiple accept="image/*" onchange="previewImages(this)" required>
                        <small class="form-text text-muted">Bạn có thể chọn nhiều hình ảnh.</small>
                        <div id="imagePreviewContainer" class="preview-container"></div>
                    </div>
                </div>
            </div>
            
            <div class="form-group">
                <label for="moTa">Mô Tả:</label>
                <textarea class="form-control" id="moTa" name="moTa" rows="5">${sanPham.moTa}</textarea>
            </div>
            
            <button type="submit" class="btn btn-primary">Thêm Sản Phẩm</button>
            <a href="${pageContext.request.contextPath}/admin/danh-sach-san-pham" class="btn btn-secondary">Hủy</a>
        </form>
    </div>
     <jsp:include page="../footer.jsp"/>
    <script>
        function previewImages(input) {
            var previewContainer = document.getElementById('imagePreviewContainer');
            previewContainer.innerHTML = '';
            
            if (input.files) {
                for (var i = 0; i < input.files.length; i++) {
                    var reader = new FileReader();
                    
                    reader.onload = function(e) {
                        var img = document.createElement('img');
                        img.src = e.target.result;
                        img.className = 'image-preview';
                        previewContainer.appendChild(img);
                    }
                    
                    reader.readAsDataURL(input.files[i]);
                }
            }
        }
    </script>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>