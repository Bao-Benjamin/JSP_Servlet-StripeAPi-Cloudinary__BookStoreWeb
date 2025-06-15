<%@page import="model.ChiTietDonHang"%>
<%@page import="java.util.List"%>
<%@page import="model.DonHang"%>
<%@page import="database.ChiTietDonHangDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý đơn hàng</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <style>
        .order-status {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-pending {
            background-color: #FFF3CD;
            color: #856404;
        }
        .status-processing {
            background-color: #D1ECF1;
            color: #0C5460;
        }
        .status-completed {
            background-color: #D4EDDA;
            color: #155724;
        }
        .status-cancelled {
            background-color: #F8D7DA;
            color: #721C24;
        }
        .order-details {
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
    <%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
</head>
<body>
	
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-md-2">
                <!-- Sidebar Menu -->
                <!-- <jsp:include page="../leftQuanLy.jsp" /> -->
                <div class="card">
                    <div class="card-header">
                        <h5>Quản trị</h5>
                    </div>
                    <div class="list-group list-group-flush">
                        <a href="<%=request.getContextPath()%>/xu-ly-phan-trang" class="list-group-item list-group-item-action">
                            <i class="fas fa-tachometer-alt mr-2"></i> Trang chủ
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-san-pham" class="list-group-item list-group-item-action">
                            <i class="fas fa-book mr-2"></i> Quản lý sản phẩm
                        </a>
                        <a href="${pageContext.request.contextPath}/index.jsp" class="list-group-item list-group-item-action active">
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
                        <h4><i class="fas fa-shopping-cart mr-2"></i> Quản lý đơn hàng</h4>
                        
                    </div>
                    <div class="card-body">
                        <!-- Thông báo -->
                        <c:if test="${param.message != null}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                                ${param.message}
                                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                        </c:if>
                        
                        <!-- Tìm kiếm và lọc -->
                        <div class="row search-container">
                            <div class="col-md-6">
                                <form action="quan-ly-don-hang" method="get" class="form-inline">
                                    <div class="input-group w-100">
                                        <input type="text" name="search" class="form-control" placeholder="Tìm kiếm theo mã đơn hàng, tên khách hàng..." value="${search}">
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
                                    <form action="quan-ly-don-hang" method="get" class="form-inline">
                                        <input type="hidden" name="search" value="${search}">
                                        <select name="status" class="form-control mr-2" onchange="this.form.submit()">
                                            <option value="" ${status == null || status == '' ? 'selected' : ''}>Tất cả trạng thái</option>
                                            <option value="cho xu ly" ${status == 'cho xu ly' ? 'selected' : ''}>Chờ xử lý</option>
                                            <option value="dang xu ly" ${status == 'dang xu ly' ? 'selected' : ''}>Đang xử lý</option>
                                            <option value="hoan thanh" ${status == 'hoan thanh' ? 'selected' : ''}>Hoàn thành</option>
                                            <option value="da huy" ${status == 'da huy' ? 'selected' : ''}>Đã hủy</option>
                                        </select>
                                        <select name="payment" class="form-control" onchange="this.form.submit()">
                                            <option value="" ${payment == null || payment == '' ? 'selected' : ''}>Tất cả phương thức</option>
                                            <option value="cod" ${payment == 'cod' ? 'selected' : ''}>Thanh toán khi nhận hàng</option>
                                            <option value="card" ${payment == 'card' ? 'selected' : ''}>Thanh toán qua thẻ</option>
                                        </select>
                                    </form>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Danh sách đơn hàng -->
                        <div class="table-responsive">
                            <table class="table table-striped table-hover">
                                <thead class="thead-dark">
                                    <tr>
                                        <th>Mã đơn hàng</th>
                                        <th>Khách hàng</th>
                                        <th>Ngày đặt</th>
                                        <th>Tổng tiền</th>
                                        <th>Phương thức</th>
                                        <th>Trạng thái</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
    <c:forEach var="donHang" items="${danhSachDonHang}">
        <%-- Lấy chi tiết đơn hàng từ DAO --%>
        <%
            ChiTietDonHangDAO chiTietDonHangDAO = new ChiTietDonHangDAO();
            DonHang donHang = (DonHang)pageContext.getAttribute("donHang");
            List<ChiTietDonHang> chiTietList = chiTietDonHangDAO.selectByMaDonHang(donHang.getMaDonHang());
            double tongTien = 0;
            for (ChiTietDonHang ct : chiTietList) {
                tongTien += ct.getTongTien();
            }
            pageContext.setAttribute("chiTietList", chiTietList);
            pageContext.setAttribute("tongTien", tongTien);
           
        %>
        
        <tr>
            <td><a href="#" class="toggle-details" data-target="details-${donHang.maDonHang}">${donHang.maDonHang}</a></td>
            <td>${donHang.tenKhachHang}</td>
            <td><fmt:formatDate value="${donHang.ngayDatHang}" pattern="dd/MM/yyyy" /></td>
            <td><fmt:formatNumber value="${tongTien}" type="currency" currencySymbol="₫" /></td>
            <td>${donHang.hinhThucThanhToan}</td>
            <td>
                <c:choose>
                    <c:when test="${donHang.trangThai == 'cho xu ly'}">
                        <span class="order-status status-pending">Chờ xử lý</span>
                    </c:when>
                    <c:when test="${donHang.trangThai == 'dang xu ly'}">
                        <span class="order-status status-processing">Đang xử lý</span>
                    </c:when>
                    <c:when test="${donHang.trangThai == 'hoan thanh'}">
                        <span class="order-status status-completed">Hoàn thành</span>
                    </c:when>
                    <c:when test="${donHang.trangThai == 'da huy'}">
                        <span class="order-status status-cancelled">Đã hủy</span>
                    </c:when>
                    <c:otherwise>
                        <span class="order-status status-pending">Chờ xử lý</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <div class="btn-group">
                   
                    <a href="#" class="btn btn-sm btn-primary toggle-details" data-target="details-${donHang.maDonHang}">
                        <i class="fas fa-edit"></i>
                    </a>
                    <a href="#" class="btn btn-sm btn-info toggle-details" data-target="details-${donHang.maDonHang}">
                        <i class="fas fa-eye"></i>
                    </a>
                    <a href="#" class="btn btn-sm btn-danger" onclick="confirmDelete('${donHang.maDonHang}')">
                        <i class="fas fa-trash"></i>
                    </a>
                </div>
            </td>
        </tr>
        <tr class="order-details" id="details-${donHang.maDonHang}">
            <td colspan="7">
                <div class="card">
                    <div class="card-body">
                        <!-- ... phần thông tin đơn hàng ... -->
                        
                        <h5 class="mt-3">Chi tiết đơn hàng</h5>
                        <div class="table-responsive">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th>Sản phẩm</th>
                                        <th>Đơn giá</th>
                                        <th>Số lượng</th>
                                        <th>Thành tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="chiTiet" items="${chiTietList}">
                                        <tr>
                                            <td>${chiTiet.sanPham.tenSanPham}</td>
                                            <td><fmt:formatNumber value="${chiTiet.giamGia}" type="currency" currencySymbol="₫" /></td>
                                            <td>${chiTiet.soLuong}</td>
                                            <td><fmt:formatNumber value="${chiTiet.tongTien}" type="currency" currencySymbol="₫" /></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan="3" class="text-right">Tổng cộng:</th>
                                        <th><fmt:formatNumber value="${tongTien}" type="currency" currencySymbol="₫" /></th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div> 
                                                        
                                                        <div class="mt-3">
                                                            <h5>Cập nhật trạng thái</h5>
                                                            <form action="updateOrderStatus" method="post" class="form-inline">
                                                                <input type="hidden" name="orderId" value="${donHang.maDonHang}">
                                                                <select name="status" class="form-control mr-2">
                                                                    <option value="cho xu ly" ${donHang.trangThai == 'cho xu ly' ? 'selected' : ''}>Chờ xử lý</option>
                                                                    <option value="dang xu ly" ${donHang.trangThai == 'dang xu ly' ? 'selected' : ''}>Đang xử lý</option>
                                                                    <option value="hoan thanh" ${donHang.trangThai == 'hoan thanh' ? 'selected' : ''}>Hoàn thành</option>
                                                                    <option value="da huy" ${donHang.trangThai == 'da huy' ? 'selected' : ''}>Đã hủy</option>
                                                                </select>
                                                                <button type="submit" class="btn btn-primary">Cập nhật</button>
                                                            </form>
                                                            <!-- Phần ghi chú đơn hàng - chỉ hiển thị khi trạng thái là "cho xu ly" -->
<c:if test="${donHang.trangThai == 'cho xu ly'}">
    <div class="mt-3">
        <h5>Ghi chú đơn hàng</h5>
        <div class="form-group">
            <textarea id="ghiChu_${donHang.maDonHang}" class="form-control" rows="3">${donHang.ghiChu}</textarea>
            <button type="button" class="btn btn-info mt-2" onclick="capNhatGhiChu('${donHang.maDonHang}')">Cập nhật ghi chú</button>
        </div>
    </div>
</c:if>
<!-- Nếu trạng thái khác "cho xu ly" thì chỉ hiển thị ghi chú dạng text -->
<c:if test="${donHang.trangThai != 'cho xu ly' && not empty donHang.ghiChu}">
    <div class="mt-3">
        <h5>Ghi chú đơn hàng</h5>
        <p>${donHang.ghiChu}</p>
    </div>
</c:if>
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
                                        <a class="page-link" href="quan-ly-don-hang?page=${currentPage - 1}&search=${search}&status=${status}&payment=${payment}" tabindex="-1" ${currentPage == 1 ? 'aria-disabled="true"' : ''}>Trước</a>
                                    </li>
                                    
                                    <c:forEach begin="1" end="${totalPages}" var="i">
                                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                                            <a class="page-link" href="quan-ly-don-hang?page=${i}&search=${search}&status=${status}&payment=${payment}">${i}</a>
                                        </li>
                                    </c:forEach>
                                    
                                    <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                                        <a class="page-link" href="quan-ly-don-hang?page=${currentPage + 1}&search=${search}&status=${status}&payment=${payment}">Sau</a>
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
                    Bạn có chắc chắn muốn xóa đơn hàng này không?
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
            // Hiển thị/ẩn chi tiết đơn hàng
            $('.toggle-details').click(function(e) {
                e.preventDefault();
                var targetId = $(this).data('target');
                $('#' + targetId).toggle();
            });
            
            // Xác nhận xóa đơn hàng
            window.confirmDelete = function(orderId) {
                $('#confirmDeleteBtn').attr('href', 'deleteOrder?id=' + orderId);
                $('#deleteModal').modal('show');
            };
        });
    </script>
    <script>
    function capNhatGhiChu(maDonHang) {
        let ghiChuMoi = document.getElementById("ghiChu_" + maDonHang).value;
        
        // Sử dụng đường dẫn tương đối đến servlet
        fetch("cap-nhat-ghi-chu", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "maDonHang=" + encodeURIComponent(maDonHang) + "&ghiChuMoi=" + encodeURIComponent(ghiChuMoi)
        })
        .then(response => response.json())
        .then(data => {
            if(data.status === "success") {
                alert("Cập nhật ghi chú thành công!");
            } else {
                alert("Cập nhật ghi chú thất bại!");
            }
        })
        .catch(error => {
            console.error("Lỗi khi cập nhật ghi chú:", error);
            alert("Đã xảy ra lỗi khi cập nhật ghi chú!");
        });
    }
</script>
    
</body>
</html>