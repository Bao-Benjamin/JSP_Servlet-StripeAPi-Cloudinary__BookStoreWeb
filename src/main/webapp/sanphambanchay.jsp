<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sản phẩm bán chạy</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            transition: transform 0.3s;
        }
        .card:hover {
            transform: translateY(-5px);
        }
        .card-img-top {
            height: 200px;
            object-fit: cover;
        }
        .badge-bestseller {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: #dc3545;
            color: white;
            padding: 5px 10px;
            border-radius: 20px;
            font-weight: bold;
        }
        .price-container {
            display: flex;
            align-items: center;
        }
        .original-price {
            text-decoration: line-through;
            color: #6c757d;
            margin-right: 10px;
        }
        .discount-price {
            color: #dc3545;
            font-weight: bold;
        }
        .filter-container {
            margin-bottom: 20px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-md-3">
                <jsp:include page="left.jsp" />
            </div>
            
            <div class="col-md-9">
                <h2 class="text-center mb-4">Sản phẩm bán chạy <i class="fas fa-crown text-warning"></i></h2>
                
                <!-- Bộ lọc theo ngày -->
                <div class="filter-container">
                    <form action="${pageContext.request.contextPath}/san-pham-ban-chay" method="get" class="form-row align-items-center">
                        <div class="col-md-4 my-1">
                            <label for="startDate">Từ ngày:</label>
                            <input type="date" class="form-control" id="startDate" name="startDate" value="${startDate}">
                        </div>
                        <div class="col-md-4 my-1">
                            <label for="endDate">Đến ngày:</label>
                            <input type="date" class="form-control" id="endDate" name="endDate" value="${endDate}">
                        </div>
                        <div class="col-md-4 my-1 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-filter mr-1"></i> Lọc
                            </button>
                            <a href="${pageContext.request.contextPath}/san-pham-ban-chay" class="btn btn-secondary ml-2">
                                <i class="fas fa-sync-alt mr-1"></i> Đặt lại
                            </a>
                        </div>
                    </form>
                </div>
                
                <!-- Hiển thị sản phẩm -->
                <div class="row">
                    <c:forEach var="sanPham" items="${danhSachSanPham}" varStatus="status">
                        <c:set var="info" value="${topSanPhamInfo[status.index]}" />
                        <div class="col-md-3">
                            <div class="card">
                                <span class="badge-bestseller">Top ${(currentPage-1)*8 + status.index + 1}</span>
                                <img src="${pageContext.request.contextPath}/images/products/${sanPham.maSanPham}.jpg" 
                                     class="card-img-top" alt="${sanPham.tenSanPham}" 
                                     onerror="this.src='${pageContext.request.contextPath}/images/no-image.jpg'">
                                <div class="card-body">
                                    <h5 class="card-title">${sanPham.tenSanPham}</h5>
                                    <p class="card-text text-muted">${sanPham.tacGia}</p>
                                    <div class="price-container">
                                        <c:if test="${sanPham.giaGiam > 0}">
                                            <span class="original-price"><fmt:formatNumber value="${sanPham.giaBan}" type="currency" currencySymbol="₫" /></span>
                                            <span class="discount-price"><fmt:formatNumber value="${sanPham.giaBan - sanPham.giaGiam}" type="currency" currencySymbol="₫" /></span>
                                        </c:if>
                                        <c:if test="${sanPham.giaGiam <= 0}">
                                            <span class="discount-price"><fmt:formatNumber value="${sanPham.giaBan}" type="currency" currencySymbol="₫" /></span>
                                        </c:if>
                                    </div>
                                    <p class="mt-2 mb-0">
                                        <span class="badge badge-success">Đã bán: ${info.soLuongBan}</span>
                                    </p>
                                    <a href="${pageContext.request.contextPath}/chi-tiet-san-pham?id=${sanPham.maSanPham}" class="btn btn-primary mt-2 w-100">Xem chi tiết</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    
                    <c:if test="${empty danhSachSanPham}">
                        <div class="col-12 text-center">
                            <div class="alert alert-info">
                                <i class="fas fa-info-circle mr-2"></i> Không có sản phẩm nào trong khoảng thời gian này.
                            </div>
                        </div>
                    </c:if>
                </div>
                
                <!-- Phân trang -->
                <c:if test="${totalPages > 1}">
                    <nav aria-label="Page navigation">
                        <ul class="pagination justify-content-center">
                            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/san-pham-ban-chay?page=${currentPage - 1}&startDate=${startDate}&endDate=${endDate}" tabindex="-1" ${currentPage == 1 ? 'aria-disabled="true"' : ''}>Trước</a>
                            </li>
                            
                            <c:forEach begin="1" end="${totalPages}" var="i">
                                <li class="page-item ${currentPage == i ? 'active' : ''}">
                                    <a class="page-link" href="${pageContext.request.contextPath}/san-pham-ban-chay?page=${i}&startDate=${startDate}&endDate=${endDate}">${i}</a>
                                </li>
                            </c:forEach>
                            
                            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="${pageContext.request.contextPath}/san-pham-ban-chay?page=${currentPage + 1}&startDate=${startDate}&endDate=${endDate}">Sau</a>
                            </li>
                        </ul>
                    </nav>
                </c:if>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>