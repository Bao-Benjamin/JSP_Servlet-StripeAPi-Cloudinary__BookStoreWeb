<%@page import="model.HinhAnhSanPham"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        /* Thiết kế Card */
        nav.navbar {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        leftnav {
            border-width: 3px;
            border-color: black;
        }
        body {
            padding-top: 60px; /* Điều chỉnh theo chiều cao navbar */
            position: relative;
        }
        .card {
            transition: all 0.3s ease-in-out;
            height: 100%;
            display: flex;
            flex-direction: column;
            border-radius: 10px;
            overflow: hidden;
        }
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0px 8px 20px rgba(0, 0, 0, 0.2);
        }
        .card-body {
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
        }
        .card-title {
            font-size: 1rem;
            font-weight: bold;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }
        .card-text {
            font-size: 0.9rem;
            max-height: 45px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
        .card-footer {
            background-color: #fff;
            border-top: none;
            text-align: center;
        }
        .btn-add-cart {
            width: 100%;
            font-weight: bold;
        }
        .row {
            position: relative;
        }
        .sale-badge {
            position: absolute;
            top: 10px;
            left: 10px;
            background: red;
            color: white;
            font-size: 14px;
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 5px;
            z-index: 5;
        }
        .gia-giam {
            font-size: 18px;
            font-weight: bold;
            color: red;
        }
        .gia-goc {
            text-decoration: line-through;
            color: gray;
            font-size: 14px;
        }
        .text {
            color: green;
        }
        
        /* Carousel styling */
        .image-carousel {
            position: relative;
            width: 100%;
            height: 220px;
        }
        .carousel-item {
            display: none;
            width: 100%;
            height: 100%;
            object-fit: contain;
        }
        .carousel-item.active {
            display: block;
        }
        .carousel-prev, .carousel-next {
            position: absolute;
            top: 50%;
            transform: translateY(-50%);
            background: rgba(255, 255, 255, 0.7);
            border: none;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            font-size: 16px;
            cursor: pointer;
            z-index: 2;
        }
        .carousel-prev {
            left: 10px;
        }
        .carousel-next {
            right: 10px;
        }
        .carousel-indicators {
            position: absolute;
            bottom: 10px;
            left: 0;
            right: 0;
            display: flex;
            justify-content: center;
            gap: 5px;
        }
        .indicator {
            width: 8px;
            height: 8px;
            border-radius: 50%;
            background: rgba(200, 200, 200, 0.7);
            cursor: pointer;
        }
        .indicator.active {
            background: #007bff;
        }
        
        /* Toast notification */
        .toast-container {
            position: fixed;
            bottom: 20px;
            right: 20px;
            z-index: 1050;
        }
        .toast {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.15);
            max-width: 350px;
        }
        .toast-header {
            display: flex;
            align-items: center;
            padding: 0.5rem 0.75rem;
            background-color: rgba(0, 0, 0, 0.03);
            border-bottom: 1px solid rgba(0, 0, 0, 0.05);
        }
        .toast-body {
            padding: 0.75rem;
        }
    </style>
    <%
    String urlhead = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
    %>
    <link href="<%=urlhead%>/css/left.css" rel="stylesheet">
</head>
<body>
    <% String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath(); %>
    <jsp:include page="header.jsp" />

    <div class="container mt-4">
        <div class="row">
            <jsp:include page="left.jsp" />
            <div class="col-lg-9">
                <!-- Thông báo thành công -->
                <c:if test="${param.success != null}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <c:choose>
                            <c:when test="${param.success == 'add'}">
                                <i class="fas fa-check-circle me-2"></i> Đã thêm sản phẩm vào giỏ hàng thành công!
                            </c:when>
                            <c:otherwise>
                                <i class="fas fa-check-circle me-2"></i> Thao tác thành công!
                            </c:otherwise>
                        </c:choose>
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <!-- Thông báo lỗi -->
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <i class="fas fa-exclamation-circle me-2"></i> Đã xảy ra lỗi. Vui lòng thử lại sau!
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>
                
                <div class="row">
                    <c:choose>
                        <c:when test="${not empty sanPhams}">
                            <c:forEach var="sp" items="${sanPhams}">
                                <div class="col-lg-4 col-md-6 mb-4">
                                    <div class="card h-100">
                                        <c:if test="${sp.giaGiam > 0 && sp.giaGiam < sp.giaBan}">
                                            <span class="sale-badge">🔥 SALE ${Math.round(100 - (sp.giaGiam / sp.giaBan * 100))}%</span>
                                        </c:if>
                                        
                                        <!-- Phần hiển thị hình ảnh với carousel -->
                                        <div onclick="window.location.href='chiTietSanPham?id=${sp.maSanPham}';" style="cursor: pointer; position: relative;">
                                            <div class="image-carousel" id="carousel-${sp.maSanPham}">
                                                <c:set var="haspList" value="${hinhAnhSanPhamMap[sp.maSanPham]}" />
                                                <c:if test="${not empty haspList}">
                                                    <c:forEach var="hasp" items="${haspList}" varStatus="status">
                                                        <img class="card-img-top carousel-item ${status.first ? 'active' : ''}" 
                                                            src="${hasp.url}" 
                                                            alt="${sp.tenSanPham}" 
                                                            data-index="${status.index}">
                                                    </c:forEach>
                                                    
                                                    <c:if test="${haspList.size() > 1}">
                                                        <button class="carousel-prev" onclick="prevImage(event, ${sp.maSanPham})">&#10094;</button>
                                                        <button class="carousel-next" onclick="nextImage(event, ${sp.maSanPham})">&#10095;</button>
                                                        <div class="carousel-indicators">
                                                            <c:forEach var="hasp" items="${haspList}" varStatus="status">
                                                                <span class="indicator ${status.first ? 'active' : ''}" 
                                                                    onclick="showImage(event, ${sp.maSanPham}, ${status.index})"></span>
                                                            </c:forEach>
                                                        </div>
                                                    </c:if>
                                                </c:if>
                                            </div>
                                        </div>
                                        
                                        <div class="card-body">
                                            <h4 class="card-title">${sp.tenSanPham}</h4>
                                            <h5>
                                                <c:choose>
                                                    <c:when test="${sp.giaGiam > 0 && sp.giaGiam < sp.giaBan}">
                                                        <span class="text-muted text-decoration-line-through">${sp.giaBan}₫</span>
                                                        <span class="text-danger fw-bold ms-2">${sp.giaGiam}₫</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text fw-bold">${sp.giaBan}₫</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </h5>
                                            <p class="card-text">${sp.moTa}</p>
                                        </div>
                                        
                                        <div class="card-footer">
                                            <button class="btn btn-primary btn-add-cart"
                                                onclick="themVaoGioHang(${sp.maSanPham}, 1); event.stopPropagation();">
                                                <i class="fas fa-shopping-cart me-1"></i> Thêm vào giỏ
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="col-12">
                                <p class="text-center">Không có sản phẩm nào được hiển thị.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    
                    <!-- Phân trang -->
                    <!-- Replace the existing pagination code with this: -->
<nav aria-label="Page navigation">
    <ul class="pagination justify-content-center">
        <c:if test="${currentPage > 1}">
            <li class="page-item">
                <c:choose>
                    <c:when test="${productType == 'sgk'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-sgk?page=${currentPage - 1}">Trước</a>
                    </c:when>
                    <c:when test="${productType == 'sale'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-sale?page=${currentPage - 1}">Trước</a>
                    </c:when>
                    <c:when test="${productType == 'bestseller'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/san-pham-ban-chay?page=${currentPage - 1}&startDate=${startDate}&endDate=${endDate}">Trước</a>
                    </c:when>
                    <c:when test="${productType == 'category'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-theo-the-loai?page=${currentPage - 1}&maTheLoai=${maTheLoai}">Trước</a>
                    </c:when>
                     <c:when test="${productType == 'vpp'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-vpp?page=${currentPage - 1}&maTheLoai=${maTheLoai}">Trước</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link" href="${pageContext.request.contextPath}/xu-ly-phan-trang?page=${currentPage - 1}">Trước</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:if>

        <c:forEach begin="1" end="${totalPages}" var="i">
            <li class="page-item ${i == currentPage ? 'active' : ''}">
                <c:choose>
                    <c:when test="${productType == 'sgk'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-sgk?page=${i}">${i}</a>
                    </c:when>
                    <c:when test="${productType == 'sale'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-sale?page=${i}">${i}</a>
                    </c:when>
                    <c:when test="${productType == 'bestseller'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/san-pham-ban-chay?page=${i}&startDate=${startDate}&endDate=${endDate}">${i}</a>
                    </c:when>
                    <c:when test="${productType == 'category'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-theo-the-loai?page=${i}&maTheLoai=${maTheLoai}">${i}</a>
                    </c:when>
                     <c:when test="${productType == 'vpp'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-vpp?page=${currentPage - 1}&maTheLoai=${maTheLoai}">Trước</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link" href="${pageContext.request.contextPath}/xu-ly-phan-trang?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:forEach>

        <c:if test="${currentPage < totalPages}">
            <li class="page-item">
                <c:choose>
                    <c:when test="${productType == 'sgk'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-sgk?page=${currentPage + 1}">Sau</a>
                    </c:when>
                    <c:when test="${productType == 'sale'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-sale?page=${currentPage + 1}">Sau</a>
                    </c:when>
                    <c:when test="${productType == 'bestseller'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/san-pham-ban-chay?page=${currentPage + 1}&startDate=${startDate}&endDate=${endDate}">Sau</a>
                    </c:when>
                    <c:when test="${productType == 'category'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-theo-the-loai?page=${currentPage + 1}&maTheLoai=${maTheLoai}">Sau</a>
                    </c:when>
                     <c:when test="${productType == 'vpp'}">
                        <a class="page-link" href="${pageContext.request.contextPath}/loc-san-pham-vpp?page=${currentPage - 1}&maTheLoai=${maTheLoai}">Trước</a>
                    </c:when>
                    <c:otherwise>
                        <a class="page-link" href="${pageContext.request.contextPath}/xu-ly-phan-trang?page=${currentPage + 1}">Sau</a>
                    </c:otherwise>
                </c:choose>
            </li>
        </c:if>
    </ul>
</nav>


                </div>
            </div>
        </div>
    </div>
    
    <!-- Toast notification container -->
    <div class="toast-container"></div>
    
    <jsp:include page="footer.jsp" />
    
    <!-- JavaScript cho carousel và thêm vào giỏ hàng -->
    <script>
        function themVaoGioHang(maSanPham, soLuong) {
            // Chuyển hướng đến servlet mới
            window.location.href = "${pageContext.request.contextPath}/them-vao-gio-hang?maSanPham=" + maSanPham + "&soLuong=" + soLuong;
        }
        
        function nextImage(event, productId) {
            event.stopPropagation();
            const carousel = document.getElementById('carousel-' + productId);
            const items = carousel.querySelectorAll('.carousel-item');
            const indicators = carousel.querySelectorAll('.indicator');
            
            let activeIndex = 0;
            for (let i = 0; i < items.length; i++) {
                if (items[i].classList.contains('active')) {
                    activeIndex = i;
                    items[i].classList.remove('active');
                    indicators[i].classList.remove('active');
                    break;
                }
            }
            
            const nextIndex = (activeIndex + 1) % items.length;
            items[nextIndex].classList.add('active');
            indicators[nextIndex].classList.add('active');
        }

        function prevImage(event, productId) {
            event.stopPropagation();
            const carousel = document.getElementById('carousel-' + productId);
            const items = carousel.querySelectorAll('.carousel-item');
            const indicators = carousel.querySelectorAll('.indicator');
            
            let activeIndex = 0;
            for (let i = 0; i < items.length; i++) {
                if (items[i].classList.contains('active')) {
                    activeIndex = i;
                    items[i].classList.remove('active');
                    indicators[i].classList.remove('active');
                    break;
                }
            }
            
            const prevIndex = (activeIndex - 1 + items.length) % items.length;
            items[prevIndex].classList.add('active');
            indicators[prevIndex].classList.add('active');
        }

        function showImage(event, productId, index) {
            event.stopPropagation();
            const carousel = document.getElementById('carousel-' + productId);
            const items = carousel.querySelectorAll('.carousel-item');
            const indicators = carousel.querySelectorAll('.indicator');
            
            for (let i = 0; i < items.length; i++) {
                items[i].classList.remove('active');
                indicators[i].classList.remove('active');
            }
            
            items[index].classList.add('active');
            indicators[index].classList.add('active');
        }
        
        // Hiển thị thông báo toast
        function showToast(message, type = 'success') {
            const toastContainer = document.querySelector('.toast-container');
            
            const toast = document.createElement('div');
            toast.className = 'toast show';
            toast.setAttribute('role', 'alert');
            toast.setAttribute('aria-live', 'assertive');
            toast.setAttribute('aria-atomic', 'true');
            
            const iconClass = type === 'success' ? 'fas fa-check-circle text-success' : 'fas fa-exclamation-circle text-danger';
            
            toast.innerHTML = `
                <div class="toast-header">
                    <i class="${iconClass} me-2"></i>
                    <strong class="me-auto">Thông báo</strong>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    ${message}
                </div>
            `;
            
            toastContainer.appendChild(toast);
            
            // Tự động đóng toast sau 3 giây
            setTimeout(() => {
                toast.classList.remove('show');
                setTimeout(() => {
                    toastContainer.removeChild(toast);
                }, 300);
            }, 3000);
            
            // Xử lý nút đóng
            const closeButton = toast.querySelector('.btn-close');
            closeButton.addEventListener('click', () => {
                toast.classList.remove('show');
                setTimeout(() => {
                    toastContainer.removeChild(toast);
                }, 300);
            });
        }
        
        // Kiểm tra thông báo từ URL
        document.addEventListener('DOMContentLoaded', function() {
            const urlParams = new URLSearchParams(window.location.search);
            if (urlParams.has('success')) {
                if (urlParams.get('success') === 'add') {
                    showToast('Đã thêm sản phẩm vào giỏ hàng thành công!');
                }
            }
            if (urlParams.has('error')) {
                showToast('Đã xảy ra lỗi. Vui lòng thử lại sau!', 'error');
            }
        });
    </script>
</body>
</html>