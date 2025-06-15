<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng thành công</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            padding-top: 20px;
            padding-bottom: 50px;
        }
        .success-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 40px;
            margin-top: 30px;
            text-align: center;
        }
        .success-icon {
            font-size: 80px;
            color: #28a745;
            margin-bottom: 20px;
        }
        .success-title {
            font-size: 28px;
            font-weight: 600;
            color: #343a40;
            margin-bottom: 15px;
        }
        .success-message {
            font-size: 18px;
            color: #6c757d;
            margin-bottom: 30px;
        }
        .order-code {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            font-size: 20px;
            font-weight: 600;
            margin-bottom: 30px;
        }
        .btn-continue {
            background-color: #007bff;
            color: white;
            padding: 12px 25px;
            border-radius: 5px;
            font-weight: 600;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
        }
        .btn-continue:hover {
            background-color: #0069d9;
            color: white;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="success-container">
                    <i class="fas fa-check-circle success-icon"></i>
                    <h1 class="success-title">Đặt hàng thành công!</h1>
                    
                    <p class="success-message">
                        Cảm ơn bạn đã đặt hàng. Đơn hàng của bạn đã được xác nhận và đang được xử lý.
                    </p>
                    
                    <c:if test="${not empty sessionScope.orderCode}">
                        <div class="order-code">
                            Mã đơn hàng: ${sessionScope.orderCode}
                        </div>
                        <p>
                            Vui lòng lưu lại mã đơn hàng này để theo dõi trạng thái đơn hàng của bạn.
                        </p>
                        <c:remove var="orderCode" scope="session" />
                    </c:if>
                    
                    <p>
                        Chúng tôi sẽ gửi email xác nhận đơn hàng và thông tin chi tiết đến địa chỉ email của bạn.
                        Nếu bạn có bất kỳ câu hỏi nào về đơn hàng, vui lòng liên hệ với chúng tôi.
                    </p>
                    
                    <a href="${pageContext.request.contextPath}/xu-ly-phan-trang" class="btn-continue">
                        <i class="fas fa-shopping-bag me-2"></i> Tiếp tục mua sắm
                    </a>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="footer.jsp" />
    
    <script>
        // Xóa thông báo thành công sau khi hiển thị
        <c:remove var="orderSuccess" scope="session" />
    </script>
</body>
</html>