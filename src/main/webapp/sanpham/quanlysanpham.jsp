<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #f8f9fa;
        }
        .product-image {
            width: 80px;
            height: 100px;
            object-fit: cover;
        }
        .badge-active {
            background-color: #28a745;
        }
        .badge-inactive {
            background-color: #dc3545;
        }
        .pagination {
            justify-content: center;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-md-2">
                <!-- Sidebar Menu -->
                <div class="card">
                    <div class="card-header">
                        <h5>Quản trị</h5>
                    </div>
                    <div class="list-group list-group-flush">
                        <a href="${pageContext.request.contextPath}/xu-ly-phan-trang" class="list-group-item list-group-item-action">
                            <i class="fas fa-tachometer-alt mr-2"></i> Trang chủ
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-san-pham" class="list-group-item list-group-item-action active">
                            <i class="fas fa-book mr-2"></i> Quản lý sản phẩm
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-don-hang" class="list-group-item list-group-item-action">
                            <i class="fas fa-shopping-cart mr-2"></i> Quản lý đơn hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-khach-hang" class="list-group-item list-group-item-action">
                            <i class="fas fa-users mr-2"></i> Quản lý khách hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/thong-ke" class="list-group-item list-group-item-action">
                            <i class="fas fa-chart-bar mr-2"></i> Thống kê
                        </a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-10">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4><i class="fas fa-book mr-2"></i> Quản lý sản phẩm</h4>
                    </div>
                    <div class="card-body">
                        <!-- Thông báo -->
                        <c:if test="${param.success eq 'add'}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong>Thành công!</strong> Đã thêm sản phẩm mới.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                        <c:if test="${param.success eq 'update'}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong>Thành công!</strong> Đã cập nhật thông tin sản phẩm.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                        <c:if test="${param.success eq 'delete'}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                <strong>Thành công!</strong> Đã xóa sản phẩm.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                        <c:if test="${param.error ne null}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                                <strong>Lỗi!</strong> Đã xảy ra lỗi khi xử lý yêu cầu.
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                        
                        <!-- Bộ lọc -->
                        <div class="card mb-4">
                            <div class="card-body">
                                <form action="${pageContext.request.contextPath}/quan-ly-san-pham" method="get" class="form-row align-items-center">
                                    <div class="col-md-4 my-1">
                                        <label class="sr-only" for="tenSanPham">Tên sản phẩm</label>
                                        <input type="text" class="form-control" id="tenSanPham" name="tenSanPham" placeholder="Tên sản phẩm" value="${tenSanPham}">
                                    </div>
                                    <div class="col-md-3 my-1">
                                        <label class="sr-only" for="maTheLoai">Thể loại</label>
                                        <select class="form-control" id="maTheLoai" name="maTheLoai">
                                            <option value="">-- Tất cả thể loại --</option>
                                            <c:forEach items="${danhSachTheLoai}" var="tl">
                                                <option value="${tl.maTheLoai}" ${maTheLoai eq tl.maTheLoai ? 'selected' : ''}>${tl.tenTheLoai}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-auto my-1">
                                        <button type="submit" class="btn btn-primary">
                                            <i class="fas fa-search mr-1"></i> Tìm kiếm
                                        </button>
                                    </div>
                                    <div class="col-auto my-1">
                                        <a href="${pageContext.request.contextPath}/lay-danh-sach-the-loai?page=add" class="btn btn-success">
                                            <i class="fas fa-plus mr-1"></i> Thêm sản phẩm
                                        </a>
                                    </div>
                                </form>
                            </div>
                        </div>
                        
                        <!-- Danh sách sản phẩm -->
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover">
                                <thead class="thead-light">
                                    <tr>
                                        <th>Mã SP</th>
                                        <th>Tên sản phẩm</th>
                                        <th>Thể loại</th>
                                        <th>Tác giả</th>
                                        <th>Giá nhập</th>
                                        <th>Giá bán</th>
                                        <th>Giảm giá</th>
                                        <th>Tồn kho</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${danhSachSanPham}" var="sanPham">
                                        <tr>
                                            <td>${sanPham.maSanPham}</td>
                                            <td>${sanPham.tenSanPham}</td>
                                            <td>${sanPham.theLoai.tenTheLoai}</td>
                                            <td>${sanPham.tacGia}</td>
                                            <td><fmt:formatNumber value="${sanPham.giaNhap}" type="currency" currencySymbol="₫" /></td>
                                            <td><fmt:formatNumber value="${sanPham.giaBan}" type="currency" currencySymbol="₫" /></td>
                                            <td><fmt:formatNumber value="${sanPham.giaGiam}" type="percent" /></td>
                                            <td>${sanPham.soLuong}</td>
                                            <td>
                                                <a href="${pageContext.request.contextPath}/chinh-sua-san-pham?maSanPham=${sanPham.maSanPham}" class="btn btn-sm btn-info">
                                                    <i class="fas fa-edit"></i>
                                                </a>
                                                <a href="#" class="btn btn-sm btn-danger" onclick="confirmDelete(${sanPham.maSanPham}, '${sanPham.tenSanPham}')">
                                                    <i class="fas fa-trash"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    <c:if test="${empty danhSachSanPham}">
                                        <tr>
                                            <td colspan="9" class="text-center">Không có sản phẩm nào</td>
                                        </tr>
                                    </c:if>
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Phân trang -->
                        <c:if test="${totalPages > 1}">
                            <nav aria-label="Page navigation">
                                <ul class="pagination">
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/quan-ly-san-pham?page=${currentPage - 1}&tenSanPham=${tenSanPham}&maTheLoai=${maTheLoai}" aria-label="Previous">
                                            <span aria-hidden="true">&laquo;</span>
                                        </a>
                                    </li>
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="${pageContext.request.contextPath}/quan-ly-san-pham?page=${i}&tenSanPham=${tenSanPham}&maTheLoai=${maTheLoai}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="${pageContext.request.contextPath}/quan-ly-san-pham?page=${currentPage + 1}&tenSanPham=${tenSanPham}&maTheLoai=${maTheLoai}" aria-label="Next">
                                            <span aria-hidden="true">&raquo;</span>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        // Xử lý xóa sản phẩm
        function confirmDelete(maSanPham, tenSanPham) {
            if (confirm(`Bạn có chắc chắn muốn xóa sản phẩm "${tenSanPham}" không?`)) {
                window.location.href = "${pageContext.request.contextPath}/xoa-san-pham?maSanPham=" + maSanPham;
            }
        }
    </script>
</body>
</html>