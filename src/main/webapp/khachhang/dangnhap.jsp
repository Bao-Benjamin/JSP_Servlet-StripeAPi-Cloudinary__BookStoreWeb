<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Đăng nhập</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-iYQeCzEYFbKjA/T2uDLTpkwGzCiq6soy8tYaI1GyVh/UjpbCx/TYkiZhlZB6+fzT"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"
	integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.min.js"
	integrity="sha384-7VPbUDkoPSGFnVtYi0QogXtr74QeVeeIs99Qfg5YCF+TidwNdjvaKZX19NZ/e6oz"
	crossorigin="anonymous"></script>
<style>
.red {
	color: red;
}
</style>
<!-- Custom styles for this template -->
<%
String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
%>
<link href="<%=url%>/css/signin.css" rel="stylesheet">


</head>
<body>
	
	<main class="form-signin w-100 m-auto">
		<form class="text-center" action="${pageContext.request.contextPath}/khach-hang" method="POST">
		<input type ="hidden" name="hanhDong" value="dang-nhap">
			<img class="mb-4" src="<%=url %>/img/logo/logo.png"
				alt="" width="72">
			<h1 class="h3 mb-3 fw-normal">ĐĂNG NHẬP</h1>
			<%
				String baoLoi = request.getAttribute("baoLoi")+"";
				if(baoLoi.equals("null")){
					baoLoi = "";
				}
			%>
			<div class="text-center"><span class="red"><%=baoLoi %></span></div>
			<div class="form-floating">
				<input type="text" class="form-control" id="tenDangNhap"
					placeholder="Tên đăng nhập" name="tenDangNhap"> <label for="tenDangNhap">Tên đăng nhập</label>
			</div>
			<div class="form-floating">
				<input type="password" class="form-control" id="matKhau"
					placeholder="Mật khẩu" name="matKhau"> <label for="matKhau">Mật khẩu</label>
			</div>

			<div class="checkbox mb-3">
				<label> <input type="checkbox" value="remember-me">
					Ghi nhớ tài khoản này
				</label>
			</div>
			<button class="w-100 btn btn-lg btn-primary" type="submit">Đăng nhập</button>
			<a href="dangky.jsp">Đăng ký tài khoản mới</a>
			<p class="mt-5 mb-3 text-muted">&copy; 2017–2025</p>
		</form>
	</main>
	
	<!-- Modal popup for login notification -->
	<div class="modal fade" id="loginRequiredModal" tabindex="-1" aria-labelledby="loginRequiredModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
		  <div class="modal-header">
			<h5 class="modal-title" id="loginRequiredModalLabel">Thông báo</h5>
			<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		  </div>
		  <div class="modal-body">
			Bạn cần đăng nhập trước khi thêm giỏ hàng
		  </div>
		  <div class="modal-footer">
			<button type="button" class="btn btn-primary" data-bs-dismiss="modal">Đồng ý</button>
		  </div>
		</div>
	  </div>
	</div>
	
	<script>
	document.addEventListener("DOMContentLoaded", function() {
		// Check if the URL has the needsLogin parameter
		const urlParams = new URLSearchParams(window.location.search);
		if (urlParams.get('needsLogin') === 'true') {
			// Show the Bootstrap modal
			var loginModal = new bootstrap.Modal(document.getElementById('loginRequiredModal'));
			loginModal.show();
		}
	});
	</script>
	
</body>
</html>