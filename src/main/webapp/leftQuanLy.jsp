<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="card">
	<div class="card-header">
		<h5>Quản trị</h5>
	</div>
	<div class="list-group list-group-flush">
		<a href="<%=request.getContextPath()%>/xu-ly-phan-trang"
			class="list-group-item list-group-item-action"> <i
			class="fas fa-tachometer-alt mr-2"></i> Trang chủ
		</a> <a href="quanlysanpham.jsp"
			class="list-group-item list-group-item-action"> <i
			class="fas fa-book mr-2"></i> Quản lý sản phẩm
		</a> <a href="${pageContext.request.contextPath}/index.jsp"
			class="list-group-item list-group-item-action active"> <i
			class="fas fa-shopping-cart mr-2"></i> Quản lý đơn hàng
		</a> <a href="${pageContext.request.contextPath}/quan-ly-khach-hang"
			class="list-group-item list-group-item-action"> <i
			class="fas fa-users mr-2"></i> Quản lý khách hàng
		</a> <a href="${pageContext.request.contextPath}/thong-ke"
			class="list-group-item list-group-item-action"> <i
			class="fas fa-chart-bar mr-2"></i> Thống kê
		</a>
	</div>
</div>