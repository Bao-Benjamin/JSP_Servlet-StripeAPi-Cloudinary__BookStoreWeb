<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý khách hàng</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        .customer-status {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-active {
            background-color: #D4EDDA;
            color: #155724;
        }
        .status-inactive {
            background-color: #F8D7DA;
            color: #721C24;
        }
        .status-pending {
            background-color: #FFF3CD;
            color: #856404;
        }
        .customer-details {
            display: none;
        }
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #f8f9fa;
        }
        .search-container {
            margin-bottom: 20px;
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
                        <a href="${pageContext.request.contextPath}/quan-ly-san-pham" class="list-group-item list-group-item-action">
                            <i class="fas fa-book mr-2"></i> Quản lý sản phẩm
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-don-hang" class="list-group-item list-group-item-action">
                            <i class="fas fa-shopping-cart mr-2"></i> Quản lý đơn hàng
                        </a>
                        <a href="<%=request.getContextPath()%>/quan-ly-khach-hang" class="list-group-item list-group-item-action active">
                            <i class="fas fa-users mr-2"></i> Quản lý khách hàng
                        </a>
                        <a href="<%=request.getContextPath()%>/thong-ke" class="list-group-item list-group-item-action">
                            <i class="fas fa-chart-bar mr-2"></i> Thống kê
                        </a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-10">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4><i class="fas fa-users mr-2"></i> Quản lý khách hàng</h4>
                    </div>
                    <div class="card-body">
                        <!-- Thông báo -->
                        <c:if test="${param.message != null}">
                            <%
                                String encodedMessage = request.getParameter("message");
                                String decodedMessage = URLDecoder.decode(encodedMessage, "UTF-8");
                                pageContext.setAttribute("decodedMessage", decodedMessage);
                            %>
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${decodedMessage}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                        
                        <!-- Tìm kiếm và lọc -->
                        <div class="row search-container">
                            <div class="col-md-6">
                                <form action="quan-ly-khach-hang" method="get" class="form-inline">
                                    <div class="input-group w-100">
                                        <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo tên, email, số điện thoại..." value="${search}">
                                        <div class="input-group-append">
                                            <button class="btn btn-outline-secondary" type="submit">
                                                <i class="fas fa-search"></i>
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-6">
                                <div class="float-right">
                                    <form action="quan-ly-khach-hang" method="get" class="form-inline">
                                        <input type="hidden" name="search" value="${search}">
                                        <select name="role" class="form-control" onchange="this.form.submit()">
                                            <option value="" ${"role" == null || "role" == '' ? 'selected' : ''}>Tất cả vai trò</option>
                                            <option value="admin" ${"role" == 'admin' ? 'selected' : ''}>admin</option>
                                            <option value="customer" ${"role" == 'customer' ? 'selected' : ''}>customer</option>
                                           
                                        </select>
                                    </form>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Danh sách khách hàng -->
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>Mã KH</th>
                                        <th>Họ tên</th>
                                        <th>Email</th>
                                        <th>Số điện thoại</th>
                                      
                                        <th>Vai trò</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="khachHang" items="${danhSachKhachHang}">
                                        <tr>
                                            <td>${khachHang.maKhachHang}</td>
                                            <td><a href="#" class="toggle-details" data-target="details-${khachHang.maKhachHang}">${khachHang.hoVaTen}</a></td>
                                            <td>${khachHang.email}</td>
                                            <td>${khachHang.soDienThoai}</td>
                                            <td>${khachHang.vaiTro}</td>
                                            
                                           
                                            <td>
                                                <div class="btn-group">
                                                 
                                                    <a href="#" class="btn btn-sm btn-primary toggle-details" data-target="details-${khachHang.maKhachHang}">
                                                        <i class="fas fa-edit"></i>
                                                    </a>
                                                    <a href="#" class="btn btn-sm btn-info toggle-details" data-target="details-${khachHang.maKhachHang}">
                                                        <i class="fas fa-eye"></i>
                                                    </a>
                                                    
                                                </div>
                                            </td>
                                        </tr>
                                        <tr class="customer-details" id="details-${khachHang.maKhachHang}">
                                            <td colspan="7">
                                                <div class="card">
                                                    <div class="card-body">
                                                        <div class="row">
                                                            <div class="col-md-6">
                                                                <h5>Thông tin khách hàng</h5>
                                                                <p><strong>Mã khách hàng:</strong> ${khachHang.maKhachHang}</p>
                                                                <p><strong>Họ tên:</strong> ${khachHang.hoVaTen}</p>
                                                                <p><strong>Email:</strong> ${khachHang.email}</p>
                                                                <p><strong>Số điện thoại:</strong> ${khachHang.soDienThoai}</p>
                                                               
                                                            </div>
                                                            <div class="col-md-6">
                                                                <h5>Thông tin bổ sung</h5>
                                                                <p><strong>Địa chỉ:</strong> ${khachHang.diaChi != null ? khachHang.diaChi : 'Chưa cập nhật'}</p>
                                                                <p><strong>Ngày sinh:</strong> 
                                                                    <c:choose>
                                                                        <c:when test="${khachHang.ngaySinh != null}">
                                                                            <fmt:formatDate value="${khachHang.ngaySinh}" pattern="dd/MM/yyyy" />
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            Chưa cập nhật
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </p>
                                                                <p><strong>Giới tính:</strong> ${khachHang.gioiTinh != null ? khachHang.gioiTinh : 'Chưa cập nhật'}</p>
                                                                <p><strong>Tên đăng nhập:</strong> ${khachHang.tenDangNhap}</p>
                                                            </div>
                                                        </div>
                                                        
                                                        
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        
                        <!-- Phân trang -->
                        <c:if test="${totalPages > 0}">
                            <nav aria-label="Page navigation">
                                <ul class="pagination justify-content-center">
                                    <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                                        <a class="page-link" href="quan-ly-khach-hang?page=${currentPage - 1}&search=${search}&status=${status}" tabindex="-1" ${currentPage == 1 ? 'aria-disabled="true"' : ''}>Trước</a>
                                    </li>
                                    
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="quan-ly-khach-hang?page=${i}&search=${search}&status=${status}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="quan-ly-khach-hang?page=${currentPage + 1}&search=${search}&status=${status}">Sau</a>
                                    </li>
                                </ul>
                            </nav>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- Modal xác nhận xóa -->
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="deleteModalLabel">Xác nhận xóa</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Bạn có chắc chắn muốn xóa khách hàng này không?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <a href="#" id="confirmDeleteBtn" class="btn btn-danger">Xóa</a>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        $(document).ready(function() {
            // Hiển thị/ẩn chi tiết khách hàng
            $('.toggle-details').click(function(e) {
                e.preventDefault();
                var targetId = $(this).data('target');
                $('#' + targetId).toggle();
            });
            
            // Xác nhận xóa khách hàng
            window.confirmDelete = function(customerId) {
                $('#confirmDeleteBtn').attr('href', 'deleteKhachHang?id=' + customerId);
                $('#deleteModal').modal('show');
            };
        });
    </script>
</body>
</html>