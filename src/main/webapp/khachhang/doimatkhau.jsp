<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet" >
<script
	src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"
	integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT"
	crossorigin="anonymous"></script>
</head>
<body>
	<%@include file="../header.jsp"  %>
	<%
		Object kh = session.getAttribute("khachHang");
		if(kh==null){
	
			
		
	%>
	<h1>Ban chua dang nhap vao tai khaon</h1>
	<%
		}else{
			String baoLoi = request.getAttribute("baoLoi")+"";
			if(baoLoi.equals("null")){
				baoLoi = "";
			}
	%>
	<div class="container mt-5" >
		<div class = "text-center"><h1>ĐỔI MẬT KHẨU</h1></div>
		
		<span style="color: red">
			<%=baoLoi%>
		</span>
		<form action="../khach-hang" method="POST">
		<input type = "hidden" name="hanhDong" value="doi-mat-khau">
		  <div class="mb-3">
		    <label for="matKhauHienTai" class="form-label">Mật khẩu hiện tại</label>
		    <input type="password" class="form-control" id="matKhauHienTai" name="matKhauHienTai">
		  </div>
		  <div class="mb-3">
		    <label for="matKhauMoi" class="form-label">Mật khẩu mới</label>
		    <input type="password" class="form-control" id="matKhauMoi" name="matKhauMoi">
		  </div>
		  <div class="mb-3">
		    <label for="matKhauMoiNhapLai" class="form-label">Nhập lại mật khẩu mới</label>
		    <input type="password" class="form-control" id="matKhauMoiNhapLai" name="matKhauMoiNhapLai">
		  </div>
		  <button type="submit" class="btn btn-primary">Lưu mật khẩu</button>
		</form>
	</div>
	<%} %>
	
	<jsp:include page = "../footer.jsp"/>
</body>
</html>