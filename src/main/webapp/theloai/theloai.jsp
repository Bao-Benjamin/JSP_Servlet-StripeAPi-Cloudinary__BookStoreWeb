<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<meta charset="UTF-8">
    <title>Danh sách Thể Loại</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" 
            integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" 
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
     nav.navbar {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
        }
        .nav-item{
        	width:auto;
        }
      
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px auto;
        }
        table, th, td {
            border: 1px solid #333;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        h2 {
            text-align: center;
        }
        form {
            width: 80%;
            margin: 20px auto;
        }
         body {
            padding-top: 60px; /* Điều chỉnh theo chiều cao navbar */
            position: relative;
        }
        .d-flex {
        	width:auto;
        	margin:0;
        }
    </style>
</head>
<body>
    <%@ include file="../header.jsp" %>
    <%
        Object kh = session.getAttribute("khachHang");
        if (kh == null) {
    %>
        <div class="container mt-5">
            <h1>Bạn chưa đăng nhập vào tài khoản</h1>
        </div>
    <%
        } else {
            String baoLoi = request.getAttribute("baoLoi") + "";
            if (baoLoi.equals("null")) {
                baoLoi = "";
            }
    %>
    <div class="container mt-5">
        <h2>Danh sách Thể Loại</h2>
        <!-- Hiển thị thông báo lỗi nếu có -->
        <c:if test="${not empty baoLoi}">
            <div class="alert alert-danger" role="alert">
                ${baoLoi}
            </div>
        </c:if>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Mã Thể Loại</th>
                    <th>Tên Thể Loại</th>
                    <th>Hành Động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="theloai" items="${listTheLoai}">
                    <tr>
                        <td>${theloai.maTheLoai}</td>
                        <td>${theloai.tenTheLoai}</td>
                        <td>
                            <a href="the-loai?action=xoa&matheloai=${theloai.maTheLoai}" 
                               onclick="return confirm('Bạn có chắc chắn muốn xóa không?');">
                                Xóa
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Form thêm mới thể loại -->
        <form action="the-loai" method="post" class="mb-4">
            <input type="hidden" name="action" value="them">
            <div class="mb-3">
                <label for="tentheloai" class="form-label">Tên Thể Loại:</label>
                <input type="text" class="form-control" id="tentheloai" name="tentheloai" required>
            </div>
            <button type="submit" class="btn btn-primary">Thêm Thể Loại</button>
        </form>
        
        <!-- Form chỉnh sửa thể loại -->
        <form action="the-loai" method="post">
            <input type="hidden" name="action" value="sua">
            <div class="mb-3">
                <label for="matheloai" class="form-label">Mã Thể Loại:</label>
                <input type="text" class="form-control" id="matheloai" name="matheloai" required>
            </div>
            <div class="mb-3">
                <label for="tentheloai" class="form-label">Tên Thể Loại:</label>
                <input type="text" class="form-control" id="tentheloai" name="tentheloai" required>
            </div>
            <button type="submit" class="btn btn-warning">Chỉnh sửa</button>
        </form>

        <!-- Nút quay về -->
        <div class="text-center mt-4">
            <a href="index.jsp" class="btn btn-secondary">Quay về</a>
        </div>
    </div>
    <%
        }
    %>
    <jsp:include page="../footer.jsp"/>
</body>
</html>
