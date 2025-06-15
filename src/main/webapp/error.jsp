<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh Toán Thất Bại</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            padding-top: 50px;
        }
        .error-container {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 30px;
            text-align: center;
        }
        .error-icon {
            font-size: 60px;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-title {
            font-size: 24px;
            font-weight: 600;
            color: #343a40;
            margin-bottom: 15px;
        }
        .error-message {
            font-size: 16px;
            color: #6c757d;
            margin-bottom: 25px;
        }
        .error-details {
            text-align: left;
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
            overflow-wrap: break-word;
        }
        .btn-try-again {
            background-color: #007bff;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: 500;
            transition: background-color 0.3s;
            display: inline-block;
        }
        .btn-try-again:hover {
            background-color: #0069d9;
            color: white;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="error-container">
            <i class="fas fa-exclamation-circle error-icon"></i>
            <h1 class="error-title">Thanh Toán Thất Bại</h1>
            
            <p class="error-message">
                Đã có lỗi xảy ra trong quá trình thanh toán. Vui lòng thử lại.
            </p>
            
            <% if (request.getParameter("message") != null && !request.getParameter("message").isEmpty()) { %>
                <div class="error-details">
                    <h5>Chi tiết lỗi:</h5>
                    <p><%= request.getParameter("message") %></p>
                </div>
            <% } %>
            
            <a href="${pageContext.request.contextPath}/gio-hang" class="btn-try-again">
                <i class="fas fa-redo-alt me-2"></i> Quay lại giỏ hàng
            </a>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
</body>
</html>

