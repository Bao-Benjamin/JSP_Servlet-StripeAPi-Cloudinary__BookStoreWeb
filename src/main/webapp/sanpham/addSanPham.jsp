<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta charset="UTF-8">
    <title>Thêm Sản Phẩm Mới</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" >
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
        integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
        crossorigin="anonymous"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .container {
            width: 50%;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin-top: 20px;
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 5px;
        }
        input, textarea, select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
        }
        textarea {
            resize: none;
        }
        .btn-submit {
            width: 100%;
            padding: 10px;
            background: #28a745;
            border: none;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            transition: 0.3s;
        }
        .btn-submit:hover {
            background: #218838;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp"/>
    <div class="container">
        <h2>Thêm Sản Phẩm Mới</h2>
        <form action="../Bookstore/AddSanPhamServlet" method="post" accept-charset="UTF-8">
            <div class="form-group">
                <label>Tên sản phẩm</label>
                <input type="text" name="tenSanPham" value="" required />
            </div>
            <div class="form-group">
                <label>Mã tác giả</label>
                <input type="text" name="matacgia" required />
            </div>
            <div class="form-group">
                <label>Năm xuất bản</label>
                <input type="number" name="namxuatban" value="${sanpham.getNamXuatBan}"required />
            </div>
            <div class="form-group">
                <label>Giá nhập</label>
                <input type="text" name="gianhap" value="${sanpham.getGiaNhap}" required />
            </div>
            <div class="form-group">
                <label>Giá gốc</label>
                <input type="text" name="giagoc" required />
            </div>
            <div class="form-group">
                <label>Giá bán</label>
                <input type="text" name="giaban" required />
            </div>
            <div class="form-group">
                <label>Số lượng</label>
                <input type="number" name="soluong" required />
            </div>
            <!-- Thay đổi ở đây: dùng dropdown để chọn thể loại -->
            <div class="form-group">
                <label>Thể loại</label>
                <select name="matheloai" required class="form-control">
                    <c:forEach var="theLoai" items="${danhSachTheLoai}">
                        <option value="${theLoai.maTheLoai}">${theLoai.tenTheLoai}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label>Ngôn ngữ</label>
                <input type="text" name="ngonngu" required />
            </div>
            <div class="form-group">
                <label>Mô tả</label>
                <textarea name="mota" rows="4" required></textarea>
            </div>
            <div class="form-group">
                <label>Đường dẫn ảnh (cách nhau bởi dấu phẩy)</label>
                <input type="text" name="duongdananh" required />
            </div>
            <div class="form-group">
                <label>Giá giảm</label>
                <input type="text" name="giagiam" value="${sanpham.getGiaGiam}" />
            </div>
            <button type="submit" class="btn-submit">Thêm sản phẩm</button>
        </form>
    </div>
    <jsp:include page="../header.jsp"/>
</body>
</html>
