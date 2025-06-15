<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        .carousel-inner img {
            width: 100%;
            height: auto;
        }
        .product-info {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <!-- Include header nếu có -->
    <jsp:include page="../header.jsp" />

    <div class="container mt-4">
        <c:choose>
            <c:when test="${not empty product}">
                <div class="row">
                    <!-- Phần carousel hiển thị danh sách ảnh sản phẩm -->
                    <div class="col-md-6">
                        <div id="productCarousel" class="carousel slide" data-bs-ride="carousel">
                            <div class="carousel-inner">
                                <c:forEach var="img" items="${product.duongDanAnh}" varStatus="status">
                                    <div class="carousel-item ${status.first ? 'active' : ''}">
                                        <img src="${img}" class="d-block w-100" alt="${product.tenSanPham}">
                                    </div>
                                </c:forEach>
                            </div>
                            <button class="carousel-control-prev" type="button" data-bs-target="#productCarousel" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" type="button" data-bs-target="#productCarousel" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>
                    </div>
                    
                    <!-- Phần hiển thị thông tin chi tiết sản phẩm -->
                    <div class="col-md-6 product-info">
                        <h1>${product.tenSanPham}</h1>
                        <h3>Giá bán: ${product.giaBan} VND</h3>
                        <p><strong>Mô tả: </strong>${product.moTa}</p>
                        <ul class="list-group">
                            <li class="list-group-item"><strong>Tác giả:</strong> ${product.tacGia}</li>
                            <li class="list-group-item"><strong>Năm xuất bản:</strong> ${product.namXuatBan}</li>
                            <li class="list-group-item"><strong>Số lượng:</strong> ${product.soLuong}</li>
                            <li class="list-group-item"><strong>Ngôn ngữ:</strong> ${product.ngonNgu}</li>
                            <!-- Nếu cần hiển thị thông tin của TheLoai, ví dụ: -->
                            <li class="list-group-item">
                                <strong>Thể loại:</strong> 
                                <c:if test="${not empty product.theLoai}">
                                    ${product.theLoai.tenTheLoai}
                                </c:if>
                            </li>
                        </ul>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <p>Sản phẩm không tồn tại hoặc đã bị xóa.</p>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Include footer nếu có -->
    <jsp:include page="../footer.jsp" />
</body>
</html>
