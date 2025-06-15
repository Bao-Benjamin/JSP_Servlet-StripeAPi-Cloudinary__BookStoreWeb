<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết sản phẩm</title>
    <!-- Google Fonts & Font Awesome -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 900px;
            margin: auto;
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .product-title {
            font-size: 28px;
            font-weight: 600;
            color: #333;
            margin-bottom: 20px;
        }
        .product-info {
            display: flex;
            flex-wrap: wrap;
            gap: 30px;
        }
        .slider-container {
            position: relative;
            width: 350px;
            flex: 1 1 350px;
            overflow: visible;
        }
        .image-slider {
            position: relative;
            overflow: hidden;
            border-radius: 10px;
            overflow: visible;
            height: 350px; /* Đảm bảo chiều cao cố định */
        }
        .image-slider img {
            width: 100%;
            height: 100%;
            object-fit: contain; /* Giữ tỷ lệ ảnh */
            display: none;
            background-color: #f8f9fa;
        }
        .image-slider img.active {
            display: block;
        }
        .slider-btn {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background: rgba(0, 0, 0, 0.5);
            border: none;
            padding: 10px;
            color: #fff;
            cursor: pointer;
            border-radius: 50%;
            z-index: 5;
        }
        .slider-btn:hover {
            background: rgba(0, 0, 0, 0.7);
        }
        .prev {
            left: 10px;
        }
        .next {
            right: 10px;
        }
        .dots {
            text-align: center;
            margin-top: 10px;
        }
        .dot {
            height: 12px;
            width: 12px;
            margin: 0 5px;
            background-color: #bbb;
            border-radius: 50%;
            display: inline-block;
            cursor: pointer;
        }
        .dot.active {
            background-color: #ff6f61;
        }
        .details {
            flex: 1 1 400px;
        }
        .details p {
            margin: 8px 0;
            font-size: 16px;
        }
        .details p strong {
            color: #333;
        }
        .price {
            color: red;
            font-size: 20px;
            font-weight: 600;
        }
        .btn-mua {
            margin-top: 15px;
            background-color: #ff6f61;
            border: none;
            color: #fff;
            padding: 10px 20px;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-mua:hover {
            background-color: #ff4c3b;
        }
        .sale-badge {
            position: absolute;
            top: -5px;
            left: -5px;
            background: red;
            color: white;
            font-size: 18px;
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 5px;
            z-index: 10;
        }
        .quantity-control {
            display: flex;
            align-items: center;
            margin: 15px 0;
        }
        .quantity-btn {
            width: 30px;
            height: 30px;
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            font-size: 16px;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
        }
        .quantity-input {
            width: 50px;
            height: 30px;
            text-align: center;
            border: 1px solid #dee2e6;
            border-left: none;
            border-right: none;
        }
        .action-buttons {
            display: flex;
            gap: 10px;
            margin-top: 20px;
        }
        .no-image {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100%;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <!-- Hiển thị thông báo thành công hoặc lỗi -->
    <c:if test="${param.success eq 'added_to_cart'}">
        <div class="alert alert-success alert-dismissible fade show container mt-3" role="alert">
            <strong>Thành công!</strong> Sản phẩm đã được thêm vào giỏ hàng.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <c:if test="${param.error eq 'insufficient_stock'}">
        <div class="alert alert-danger alert-dismissible fade show container mt-3" role="alert">
            <strong>Lỗi!</strong> Số lượng yêu cầu vượt quá số lượng tồn kho.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    
    <div class="container">
        <h2 class="product-title">${product.tenSanPham}</h2>
        <div class="product-info">
            <!-- Slider hiển thị danh sách ảnh -->
            <div class="slider-container">
                <div class="image-slider">
                    <c:if test="${product.giaGiam > 0 && product.giaGiam < product.giaBan}">
                        <span class="sale-badge">🔥 SALE <fmt:formatNumber value="${100 - (product.giaGiam / product.giaBan * 100)}" pattern="#" />%</span>
                    </c:if>
                    
                    <c:choose>
                        <c:when test="${not empty productImages}">
                            <c:forEach var="image" items="${productImages}" varStatus="status">
                                <img src="${image.url}" alt="${product.tenSanPham}" class="${status.first ? 'active' : ''}">
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="no-image">Không có hình ảnh</div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <c:if test="${not empty productImages && productImages.size() > 1}">
                    <button class="slider-btn prev" onclick="prevImage()"><i class="fa-solid fa-angle-left"></i></button>
                    <button class="slider-btn next" onclick="nextImage()"><i class="fa-solid fa-angle-right"></i></button>
                    <div class="dots">
                        <c:forEach var="image" items="${productImages}" varStatus="status">
                            <span class="dot ${status.first ? 'active' : ''}" onclick="setImage(${status.index})"></span>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
            
            <!-- Chi tiết sản phẩm -->
            <div class="details">
                <p><strong>Tên sản phẩm:</strong> ${product.tenSanPham}</p>
                <p><strong>Tác giả:</strong> ${product.tacGia}</p>
                <p><strong>Năm xuất bản:</strong> ${product.namXuatBan}</p>
                <p class="product-price">
                    <c:choose>
                        <c:when test="${product.giaGiam > 0 && product.giaGiam < product.giaBan}">
                            <span class="old-price" style="text-decoration: line-through; color: #888; font-size: 18px;">
                                <fmt:formatNumber value="${product.giaBan}" pattern="#,###" /> VNĐ
                            </span>
                            <span class="discount-price" style="color: red; font-size: 22px; font-weight: bold; margin-left: 10px;">
                                <fmt:formatNumber value="${product.giaGiam}" pattern="#,###" /> VNĐ
                            </span>
                        </c:when>
                        <c:otherwise>
                            <span style="font-size: 20px; font-weight: bold;">
                                <fmt:formatNumber value="${product.giaBan}" pattern="#,###" /> VNĐ
                            </span>
                        </c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Thể loại:</strong> ${product.theLoai.tenTheLoai}</p>
                <p><strong>Mô tả:</strong> ${product.moTa}</p>
                
                <!-- Điều khiển số lượng -->
                <form action="${pageContext.request.contextPath}/gio-hang" method="get">
                    <input type="hidden" name="productId" value="${product.maSanPham}">
                    
                    <div class="quantity-control">
                        <button type="button" class="quantity-btn" onclick="decreaseQuantity()">-</button>
                        <input type="number" name="quantity" id="quantity" value="1" min="1" max="${product.soLuong}" class="quantity-input">
                        <button type="button" class="quantity-btn" onclick="increaseQuantity()">+</button>
                    </div>
                    
                    <div class="action-buttons">
                        <button type="submit" class="btn btn-primary">
                            <i class="fa-solid fa-cart-plus"></i> Thêm vào giỏ hàng
                        </button>
                        
                        <a href="../donhang/thongtindonhang.jsp" class="btn btn-warning">
                            <i class="fa-solid fa-bag-shopping"></i> Mua ngay
                        </a>
                    </div>
                </form>
                
                <!-- Hiển thị nút Chỉnh sửa và Xóa nếu người dùng đã đăng nhập -->
                <c:if test="${not empty sessionScope.khachHang}">
                    <div class="mt-3">
                        <a href="../Bookstore/lay-danh-sach-the-loai?page=edit&idSanPham=${product.maSanPham}" class="btn btn-warning">
                            <i class="fa-solid fa-pen"></i> Chỉnh sửa
                        </a>
                        <a href="../Bookstore/xoa-san-pham?maSanPham=${product.maSanPham}" class="btn btn-danger" 
                           onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này không?');">
                            <i class="fa-solid fa-trash"></i> Xóa
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <script>
        let currentIndex = 0;
        const images = document.querySelectorAll('.image-slider img');
        const dots = document.querySelectorAll('.dot');
        
        // Nếu không có hình ảnh, không cần chạy script
        if (images.length > 0) {
            function updateSlider() {
                images.forEach((img, index) => {
                    img.classList.toggle('active', index === currentIndex);
                    if (dots.length > 0) {
                        dots[index].classList.toggle('active', index === currentIndex);
                    }
                });
            }

            function nextImage() {
                currentIndex = (currentIndex + 1) % images.length;
                updateSlider();
            }

            function prevImage() {
                currentIndex = (currentIndex - 1 + images.length) % images.length;
                updateSlider();
            }

            function setImage(index) {
                currentIndex = index;
                updateSlider();
            }
            
            function decreaseQuantity() {
                const quantityInput = document.getElementById('quantity');
                let currentQuantity = parseInt(quantityInput.value);
                
                if (currentQuantity > 1) {
                    quantityInput.value = currentQuantity - 1;
                }
            }
            
            function increaseQuantity() {
                const quantityInput = document.getElementById('quantity');
                const maxQuantity = parseInt(quantityInput.getAttribute('max'));
                let currentQuantity = parseInt(quantityInput.value);
                
                if (currentQuantity < maxQuantity) {
                    quantityInput.value = currentQuantity + 1;
                }
            }
        }
    </script>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>