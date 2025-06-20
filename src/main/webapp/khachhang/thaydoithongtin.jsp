<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport"content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Đăng ký</title>
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
</head>
<body>
	<jsp:include page = "../header.jsp"/>
	<%
		Object obj = session.getAttribute("khachHang");
		if(obj==null){		
		
	%>
	<h1>Ban chua dang nhap vao tai khaon</h1>
	<%
		}else{
			KhachHang kh = (KhachHang)obj;
		
			String baoLoi = request.getAttribute("baoLoi")+"";
			baoLoi= (baoLoi.equals("null")) ? "" : baoLoi;
	
		
		
			
			String hovaten = kh.getHoVaTen();
			String gioiTinh = kh.getGioiTinh();
		
			String ngaySinh = kh.getNgaySinh()+"";
			String diaChiKhachHang = kh.getDiaChi();
			String diaChiMuaHang = kh.getDiaChiMuaHang();
		
			String diaChiNhanHang = kh.getDiaChiNhanHang();
		
			String dienThoai = kh.getSoDienThoai();
		
			String email = kh.getEmail();
		
			Boolean dongYNhanMail = kh.isDangKyNhanBangTin();
			
		
	%>
	
	
	<div class="container">
		<div class="text-center">
			<h1>THAY ĐỔI THÔNG TIN</h1>
		</div>

		<div class="red" id="baoLoi">
			<%=baoLoi%>
		</div>
		<form class="form" action="../khach-hang" method="post">
		<input type = "hidden" name = "hanhDong" value="thay-doi-thong-tin" >
			<div class="row">
				<div class="col-sm-6">
					
					<h3>Thông tin khách hàng</h3>
					<div class="mb-3">
						<label for="hoVaTen" class="form-label">Họ và tên</label> 
						<input type="text" class="form-control" id="hoVaTen" name="hoVaTen" value="<%=hovaten%>">
					</div>
					<div class="mb-3">
						<label for="gioiTinh" class="form-label">Giới tính</label> 
						<select
							class="form-control" id="gioiTinh" name="gioiTinh">
							<option></option>
							<option value="Nam"<%=(gioiTinh != null && gioiTinh.equals("Nam")) ? "selected='selected'" : ""%>>Nam</option>
							<option value="Nữ"<%=(gioiTinh != null && gioiTinh.equals("Nữ")) ? "selected='selected'" : ""%>>Nữ</option>
							<option value="Khác"<%=(gioiTinh != null && gioiTinh.equals("Khác")) ? "selected='selected'" : ""%>>Khác</option>
						</select>
					</div>
					<div class="mb-3">
						<label for="ngaySinh" class="form-label">Ngày sinh</label> 
						<input type="date" class="form-control" id="ngaySinh" name="ngaySinh"value="<%=ngaySinh%>">
					</div>
				</div>
				<div class="col-sm-6">
					<h3>Địa chỉ</h3>
					<div class="mb-3">
						<label for="diaChiKhachHang" class="form-label">Địa chỉ khách hàng</label> 
						<input type="text" class="form-control"id="diaChiKhachHang" name="diaChiKhachHang"value="<%=diaChiKhachHang%>">
					</div>
					<div class="mb-3">
						<label for="diaChiMuaHang" class="form-label">Địa chỉ mua hàng</label> 
						<input type="text" class="form-control" id="diaChiMuaHang"name="diaChiMuaHang" value="<%=diaChiMuaHang%>">
					</div>
					<div class="mb-3">
						<label for="diaChiNhanHang" class="form-label">Địa chỉ nhận hàng</label> 
						<input type="text" class="form-control" id="diaChiNhanHang" name="diaChiNhanHang" value="<%=diaChiNhanHang%>">
					</div>
					<div class="mb-3">
						<label for="dienThoai" class="form-label">Điện thoại</label>
						<input type="tel" class="form-control" id="dienThoai" name="dienThoai" value="<%=dienThoai%>">
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email</label> 
						<input type="email" class="form-control" id="email" name="email" value="<%=email%>">
					</div>
					<hr />
				
					<div class="mb-3">
						<label for="dongYNhanMail" class="form-label">Đồng ý nhận email </label> 
						<input type="checkbox" class="form-check-input" id="dongYNhanMail" name="dongYNhanMail" <%=(dongYNhanMail ? "checked" : "" )%>>
					</div>
					<input class="btn btn-primary form-control" type="submit" value="Lưu thay đổi" name="submit" id="submit"  />
				</div>
			</div>
		</form>
	</div>
	<%} %>
	<jsp:include page = "../footer.jsp"/>
</body>


</html>