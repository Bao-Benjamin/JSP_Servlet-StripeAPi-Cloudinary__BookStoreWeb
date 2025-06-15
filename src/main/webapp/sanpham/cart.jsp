<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            padding-top: 20px;
        }
        .cart-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 25px;
            margin-bottom: 30px;
        }
        .cart-header {
            border-bottom: 1px solid #e9ecef;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .cart-title {
            font-size: 24px;
            font-weight: 600;
            color: #343a40;
        }
        .cart-item {
            padding: 15px 0;
            border-bottom: 1px solid #e9ecef;
        }
        .cart-item:last-child {
            border-bottom: none;
        }
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: contain;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        .product-name {
            font-weight: 600;
            color: #343a40;
            text-decoration: none;
        }
        .product-name:hover {
            color: #007bff;
        }
        .price {
            font-weight: 600;
        }
        .original-price {
            text-decoration: line-through;
            color: #6c757d;
            font-size: 0.9em;
        }
        .discount-price {
            color: #dc3545;
        }
        .quantity-control {
            display: flex;
            align-items: center;
            justify-content: center;
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
        .remove-btn {
            color: #dc3545;
            background: none;
            border: none;
            font-size: 18px;
            cursor: pointer;
        }
        .remove-btn:hover {
            color: #c82333;
        }
        .cart-summary {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
        }
        .summary-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 15px;
        }
        .summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .summary-total {
            font-size: 18px;
            font-weight: 600;
            border-top: 1px solid #dee2e6;
            padding-top: 15px;
            margin-top: 15px;
        }
        .checkout-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 5px;
            font-weight: 600;
            width: 100%;
            margin-top: 15px;
        }
        .checkout-btn:hover {
            background-color: #218838;
        }
        .continue-shopping {
            color: #007bff;
            text-decoration: none;
            display: inline-block;
            margin-top: 15px;
        }
        .continue-shopping:hover {
            text-decoration: underline;
        }
        .empty-cart {
            text-align: center;
            padding: 50px 0;
        }
        .empty-cart i {
            font-size: 60px;
            color: #dee2e6;
            margin-bottom: 20px;
        }
        .empty-cart p {
            font-size: 18px;
            color: #6c757d;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container mt-4">
        <!-- Hiển thị thông báo từ session -->
        <c:if test="${not empty sessionScope.cartMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${sessionScope.cartMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
            <c:remove var="cartMessage" scope="session" />
        </c:if>
        
        <div class="row">
            <div class="col-lg-8">
                <div class="cart-container">
                    <div class="cart-header">
                        <h2 class="cart-title">Giỏ hàng của bạn</h2>
                    </div>
                    
                    <c:choose>
                        <c:when test="${empty cart.items}">
                            <div class="empty-cart">
                                <i class="fas fa-shopping-cart"></i>
                                <p>Giỏ hàng của bạn đang trống</p>
                                <a href="xu-ly-phan-trang" class="btn btn-primary">Tiếp tục mua sắm</a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach var="entry" items="${cart.items}">
                                <c:set var="productId" value="${entry.key}" />
                                <c:set var="cartItem" value="${entry.value}" />
                                <c:set var="product" value="${cartItem.sanPham}" />
                                
                                <div class="cart-item row align-items-center">
                                    <div class="col-md-2 col-3">
                                        <c:set var="images" value="${productImagesMap[productId]}" />
                                        
                                        <c:choose>
                                            <c:when test="${not empty images}">
                                                <img src="${images[0].url}" alt="${product.tenSanPham}" class="product-image">
                                            </c:when>
                                            <c:otherwise>
                                                <div class="product-image d-flex align-items-center justify-content-center bg-light">
                                                    <i class="fas fa-image text-muted"></i>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    
                                    <div class="col-md-4 col-9">
                                        <a href="chiTietSanPham?id=${productId}" class="product-name">
                                            ${product.tenSanPham}
                                        </a>
                                        <div class="text-muted small">Tác giả: ${product.tacGia}</div>
                                    </div>
                                    
                                    <div class="col-md-2 col-4 mt-3 mt-md-0">
                                        <c:choose>
                                            <c:when test="${product.giaGiam > 0 && product.giaGiam < product.giaBan}">
                                                <div class="original-price">
                                                    <fmt:formatNumber value="${product.giaBan}" pattern="#,###" />₫
                                                </div>
                                                <div class="discount-price">
                                                    <fmt:formatNumber value="${product.giaGiam}" pattern="#,###" />₫
                                                </div>
                                            </c:when>
                                            <c:otherwise>
                                                <div class="price">
                                                    <fmt:formatNumber value="${product.giaBan}" pattern="#,###" />₫
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    
                                    <div class="col-md-2 col-4 mt-3 mt-md-0">
                                        <form action="update-cart" method="post" class="quantity-control">
                                            <input type="hidden" name="productId" value="${productId}">
                                            <button type="button" class="quantity-btn" onclick="decreaseQuantity(this)">-</button>
                                            <input type="number" name="quantity" value="${cartItem.quantity}" min="1" max="${product.soLuong}" class="quantity-input" onchange="this.form.submit()">
                                            <button type="button" class="quantity-btn" onclick="increaseQuantity(this, ${product.soLuong})">+</button>
                                        </form>
                                    </div>
                                    
                                    <div class="col-md-1 col-2 mt-3 mt-md-0 text-end">
                                        <div class="price">
                                            <fmt:formatNumber value="${cartItem.totalPrice}" pattern="#,###" />₫
                                        </div>
                                    </div>
                                    
                                    <div class="col-md-1 col-2 mt-3 mt-md-0 text-end">
                                        <form action="remove-from-cart" method="get">
                                            <input type="hidden" name="productId" value="${productId}">
                                            <button type="submit" class="remove-btn" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này khỏi giỏ hàng?')">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <div class="col-lg-4">
                <c:if test="${not empty cart.items}">
                    <div class="cart-summary">
                        <h3 class="summary-title">Tổng giỏ hàng</h3>
                        
                        <div class="summary-item">
                            <span>Tổng sản phẩm:</span>
                            <span>${cart.items.size()}</span>
                        </div>
                        
                        <div class="summary-total">
                            <span>Tổng tiền:</span>
                            <span><fmt:formatNumber value="${cart.totalPrice}" pattern="#,###" />₫</span>
                        </div>
                        
                        <a href="donhang/thongtindonhang.jsp" class="btn checkout-btn">
                            Tiến hành thanh toán
                        </a>
                        
                        <a href="xu-ly-phan-trang" class="continue-shopping">
                            <i class="fas fa-arrow-left"></i> Tiếp tục mua sắm
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <script>
        function decreaseQuantity(button) {
            const input = button.nextElementSibling;
            let value = parseInt(input.value);
            if (value > 1) {
                input.value = value - 1;
                input.form.submit();
            }
        }
        
        function increaseQuantity(button, maxQuantity) {
            const input = button.previousElementSibling;
            let value = parseInt(input.value);
            if (value < maxQuantity) {
                input.value = value + 1;
                input.form.submit();
            }
        }
    </script>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>