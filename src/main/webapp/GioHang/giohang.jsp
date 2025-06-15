<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            padding-top: 10px;
            padding-bottom: 10px;
        }
        nav.navbar {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        .cart-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 25px;
            margin-bottom: 30px;
        }
        .cart-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #e9ecef;
        }
        .cart-title {
            font-size: 24px;
            font-weight: 600;
            color: #343a40;
            margin: 0;
        }
        .product-image {
            width: 80px;
            height: 80px;
            object-fit: contain;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        .product-name {
            font-weight: 500;
            color: #343a40;
        }
        .product-price {
            font-weight: 500;
            color: #28a745;
        }
        .quantity-input {
            width: 70px;
            text-align: center;
        }
        .btn-update {
            padding: 0.25rem 0.5rem;
            font-size: 0.875rem;
        }
        .summary-card {
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
            font-weight: 600;
            font-size: 18px;
            margin-top: 15px;
            padding-top: 15px;
            border-top: 1px solid #dee2e6;
        }
        .empty-cart {
            text-align: center;
            padding: 50px 0;
        }
        .empty-cart i {
            font-size: 48px;
            color: #6c757d;
            margin-bottom: 20px;
        }
        .empty-cart p {
            font-size: 18px;
            color: #6c757d;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container mt-4">
        <div class="cart-container">
            <div class="cart-header">
                <h2 class="cart-title">Giỏ hàng của bạn</h2>
                <c:if test="${not empty gioHang.danhSachChiTiet}">
                    <form action="${pageContext.request.contextPath}/cap-nhat-gio-hang" method="post">
                        <input type="hidden" name="action" value="clear">
                        <button type="submit" class="btn btn-outline-danger">
                            <i class="fas fa-trash"></i> Xóa tất cả
                        </button>
                    </form>
                </c:if>
            </div>
            
            <!-- Thông báo -->
            <c:if test="${param.success != null}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <c:choose>
                        <c:when test="${param.success == 'add'}">
                            <i class="fas fa-check-circle me-2"></i> Đã thêm sản phẩm vào giỏ hàng thành công!
                        </c:when>
                        <c:when test="${param.success == 'update'}">
                            <i class="fas fa-check-circle me-2"></i> Đã cập nhật số lượng sản phẩm thành công!
                        </c:when>
                        <c:when test="${param.success == 'remove'}">
                            <i class="fas fa-check-circle me-2"></i> Đã xóa sản phẩm khỏi giỏ hàng thành công!
                        </c:when>
                        <c:when test="${param.success == 'clear'}">
                            <i class="fas fa-check-circle me-2"></i> Đã xóa tất cả sản phẩm khỏi giỏ hàng thành công!
                        </c:when>
                    </c:choose>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <c:if test="${param.error != null}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <i class="fas fa-exclamation-circle me-2"></i> Đã xảy ra lỗi. Vui lòng thử lại sau!
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            
            <c:choose>
                <c:when test="${empty gioHang.danhSachChiTiet}">
                    <!-- Giỏ hàng trống -->
                    <div class="empty-cart">
                        <i class="fas fa-shopping-cart"></i>
                        <p>Giỏ hàng của bạn đang trống</p>
                        <a href="${pageContext.request.contextPath}/xu-ly-phan-trang" class="btn btn-primary">
                            <i class="fas fa-shopping-bag"></i> Tiếp tục mua sắm
                        </a>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Giỏ hàng có sản phẩm -->
                    <div class="row">
                        <div class="col-md-8">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Sản phẩm</th>
                                            <th scope="col">Giá</th>
                                            <th scope="col">Số lượng</th>
                                            <th scope="col">Thành tiền</th>
                                            <th scope="col"></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="chiTiet" items="${gioHang.danhSachChiTiet}">
                                            <tr>
                                                <td>
                                                    <div class="d-flex align-items-center">
                                                        <!-- Lấy hình ảnh từ map -->
                                                        <c:set var="danhSachHinhAnh" value="${hinhAnhSanPhamMap[chiTiet.sanPham.maSanPham]}" />
                                                        <c:set var="hinhAnh" value="/placeholder.svg" />
                                                        <c:if test="${not empty danhSachHinhAnh and not empty danhSachHinhAnh[0]}">
                                                            <c:set var="hinhAnh" value="${danhSachHinhAnh[0].url}" />
                                                        </c:if>
                                                        
                                                        <img src="${hinhAnh}" alt="${chiTiet.sanPham.tenSanPham}" class="product-image me-3">
                                                        <div>
                                                            <a href="${pageContext.request.contextPath}/chiTietSanPham?id=${chiTiet.sanPham.maSanPham}" class="product-name">
                                                                ${chiTiet.sanPham.tenSanPham}
                                                            </a>
                                                            <div class="text-muted small">
                                                                <c:if test="${not empty chiTiet.sanPham.tacGia}">
                                                                    Tác giả: ${chiTiet.sanPham.tacGia}
                                                                </c:if>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${chiTiet.sanPham.giaGiam > 0 && chiTiet.sanPham.giaGiam < chiTiet.sanPham.giaBan}">
                                                            <div class="text-decoration-line-through text-muted">
                                                                <fmt:formatNumber value="${chiTiet.sanPham.giaBan}" pattern="#,###" />₫
                                                            </div>
                                                            <div class="product-price">
                                                                <fmt:formatNumber value="${chiTiet.sanPham.giaGiam}" pattern="#,###" />₫
                                                            </div>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="product-price">
                                                                <fmt:formatNumber value="${chiTiet.sanPham.giaBan}" pattern="#,###" />₫
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <form action="${pageContext.request.contextPath}/cap-nhat-gio-hang" method="post" class="d-flex align-items-center">
                                                        <input type="hidden" name="action" value="update">
                                                        <input type="hidden" name="maChiTiet" value="${chiTiet.maChiTietGioHang}">
                                                        <input type="number" name="soLuong" value="${chiTiet.soLuong}" min="1" max="99" class="form-control quantity-input me-2">
                                                        <button type="submit" class="btn btn-sm btn-outline-primary btn-update">
                                                            <i class="fas fa-sync-alt"></i>
                                                        </button>
                                                    </form>
                                                </td>
                                                <td class="product-price">
                                                    <!-- Tính toán thành tiền trực tiếp trong JSP -->
                                                    <c:set var="donGia" value="${chiTiet.sanPham.giaGiam > 0 && chiTiet.sanPham.giaGiam < chiTiet.sanPham.giaBan ? chiTiet.sanPham.giaGiam : chiTiet.sanPham.giaBan}" />
                                                    <c:set var="thanhTien" value="${donGia * chiTiet.soLuong}" />
                                                    <fmt:formatNumber value="${thanhTien}" pattern="#,###" />₫
                                                </td>
                                                <td>
                                                    <form action="${pageContext.request.contextPath}/cap-nhat-gio-hang" method="post">
                                                        <input type="hidden" name="action" value="remove">
                                                        <input type="hidden" name="maChiTiet" value="${chiTiet.maChiTietGioHang}">
                                                        <input type="hidden" name="soLuong" value="${chiTiet.soLuong}">
                                                        <button type="submit" class="btn btn-sm btn-outline-danger">
                                                            <i class="fas fa-trash"></i>
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        
                        <div class="col-md-4">
                            <div class="summary-card">
                                <h3 class="summary-title">Tóm tắt đơn hàng</h3>
                                
                                <div class="summary-item">
                                    <span>Tổng sản phẩm:</span>
                                    <span>${gioHang.danhSachChiTiet.size()}</span>
                                </div>
                                
                                <!-- Tính tổng tiền -->
                                <c:set var="tongTien" value="0" />
                                <c:forEach var="chiTiet" items="${gioHang.danhSachChiTiet}">
                                    <c:set var="donGia" value="${chiTiet.sanPham.giaGiam > 0 && chiTiet.sanPham.giaGiam < chiTiet.sanPham.giaBan ? chiTiet.sanPham.giaGiam : chiTiet.sanPham.giaBan}" />
                                    <c:set var="tongTien" value="${tongTien + (donGia * chiTiet.soLuong)}" />
                                </c:forEach>
                                
                                <div class="summary-item">
                                    <span>Tạm tính:</span>
                                    <span><fmt:formatNumber value="${tongTien}" pattern="#,###" />₫</span>
                                </div>
                                
                                <div class="summary-item">
                                    <span>Phí vận chuyển:</span>
                                    <span>Miễn phí</span>
                                </div>
                                
                                <div class="summary-total">
                                    <span>Tổng cộng:</span>
                                    <span><fmt:formatNumber value="${tongTien}" pattern="#,###" />₫</span>
                                </div>
                                
                                <div class="d-grid gap-2 mt-4">
                                    <a href="${pageContext.request.contextPath}/thanh-toan" class="btn btn-primary">
                                        <i class="fas fa-credit-card"></i> Tiến hành thanh toán
                                    </a>
                                    <a href="${pageContext.request.contextPath}/xu-ly-phan-trang" class="btn btn-outline-secondary">
                                        <i class="fas fa-arrow-left"></i> Tiếp tục mua sắm
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>