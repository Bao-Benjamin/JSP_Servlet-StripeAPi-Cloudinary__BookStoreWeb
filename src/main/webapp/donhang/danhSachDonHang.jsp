<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, model.DonHang, model.ChiTietDonHang, java.text.SimpleDateFormat, java.text.NumberFormat, java.util.Locale" %>

<%
    String baoLoi = (String) request.getAttribute("baoLoi");
    List<DonHang> donHangList = (List<DonHang>) request.getAttribute("donhang");
    Map<String, List<ChiTietDonHang>> chiTietDonHangMap = 
        (Map<String, List<ChiTietDonHang>>) request.getAttribute("chiTietDonHangMap");
    
    // Format tiền tệ
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    
    // Format ngày tháng
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lịch sử đơn hàng - Bookstore</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    
    <style>
        body {
            background-color: #f8f9fa;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        
        .order-history-container {
            padding: 30px 0;
        }
        
        .page-title {
            color: #2c3e50;
            margin-bottom: 30px;
            font-weight: 600;
            position: relative;
            padding-bottom: 10px;
        }
        
        .page-title:after {
            content: '';
            position: absolute;
            bottom: 0;
            left: 0;
            width: 60px;
            height: 3px;
            background-color: #3498db;
        }
        
        .order-card {
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
            margin-bottom: 25px;
            overflow: hidden;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        
        .order-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
        
        .order-header {
            background-color: #f8f9fa;
            border-bottom: 1px solid #e9ecef;
            padding: 15px 20px;
        }
        
        .order-id {
            font-weight: 600;
            color: #2c3e50;
        }
        
        .order-date {
            color: #7f8c8d;
        }
        
        .order-body {
            padding: 20px;
        }
        
        .order-info {
            margin-bottom: 20px;
        }
        
        .info-label {
            font-weight: 600;
            color: #34495e;
            margin-bottom: 5px;
        }
        
        .info-value {
            color: #7f8c8d;
        }
        
        .payment-method {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
        }
        
        .payment-cod {
            background-color: #e9f7ef;
            color: #27ae60;
        }
        
        .payment-card {
            background-color: #eaf2f8;
            color: #3498db;
        }
        
        .product-list {
            margin-top: 20px;
            border-top: 1px solid #e9ecef;
            padding-top: 20px;
        }
        
        .product-item {
            display: flex;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #f1f1f1;
        }
        
        .product-image {
            width: 60px;
            height: 60px;
            background-color: #f1f1f1;
            border-radius: 5px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 15px;
        }
        
        .product-details {
            flex: 1;
        }
        
        .product-name {
            font-weight: 600;
            color: #2c3e50;
            margin-bottom: 5px;
        }
        
        .product-price {
            color: #e74c3c;
            font-weight: 600;
        }
        
        .product-quantity {
            color: #7f8c8d;
            font-size: 0.9rem;
        }
        
        .empty-orders {
            text-align: center;
            padding: 50px 0;
        }
        
        .empty-icon {
            font-size: 60px;
            color: #bdc3c7;
            margin-bottom: 20px;
        }
        
        .empty-message {
            color: #7f8c8d;
            font-size: 1.2rem;
            margin-bottom: 30px;
        }
        
        .btn-shop-now {
            background-color: #3498db;
            color: white;
            padding: 10px 25px;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }
        
        .btn-shop-now:hover {
            background-color: #2980b9;
            color: white;
        }
        
        .error-message {
            background-color: #fdedec;
            color: #e74c3c;
            padding: 15px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .order-total {
            text-align: right;
            margin-top: 20px;
            padding-top: 15px;
            border-top: 1px dashed #e9ecef;
        }
        
        .total-label {
            font-weight: 600;
            color: #2c3e50;
            margin-right: 10px;
        }
        
        .total-value {
            font-size: 1.2rem;
            font-weight: 700;
            color: #e74c3c;
        }
        
        .status-badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 0.85rem;
            font-weight: 600;
            margin-left: 10px;
        }
        
        .status-pending {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .status-shipped {
            background-color: #d4edda;
            color: #155724;
        }
        
        .status-delivered {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .status-cancelled {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .accordion-button:not(.collapsed) {
            background-color: #e8f4f8;
            color: #2c3e50;
        }
        
        .accordion-button:focus {
            box-shadow: none;
            border-color: rgba(0,0,0,.125);
        }
        
        @media (max-width: 768px) {
            .order-card {
                margin-bottom: 15px;
            }
            
            .product-image {
                width: 50px;
                height: 50px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container order-history-container">
        <h1 class="page-title">Lịch sử đơn hàng</h1>
        
        <% if (baoLoi != null && !baoLoi.isEmpty()) { %>
            <div class="error-message">
                <i class="fas fa-exclamation-circle me-2"></i>
                <%= baoLoi %>
            </div>
        <% } %>
        
        <% if (donHangList != null && !donHangList.isEmpty()) { %>
            <div class="row">
                <div class="col-12">
                    <div class="accordion" id="orderAccordion">
                        <% 
                        int index = 0;
                        for (DonHang donHang : donHangList) { 
                            index++;
                            String maDonHang = donHang.getMaDonHang();
                            List<ChiTietDonHang> chiTietList = chiTietDonHangMap.get(maDonHang);
                            
                            // Tính tổng tiền đơn hàng
                            double tongTien = 0;
                            if (chiTietList != null) {
                                for (ChiTietDonHang chiTiet : chiTietList) {
                                    tongTien += chiTiet.getTongTien();
                                }
                            }
                            String trangThaiClass = "";
                            // Xác định trạng thái đơn hàng (giả định)
                            String trangThai = donHang.getTrangThai()+"";
                            if(trangThai.equals("cho xu ly")){
                            	 trangThaiClass = "status-pending";
                            }
                            if(trangThai.equals("dang xu ly")){
                           	  trangThaiClass = "status-shipped";
                           }
                            if(trangThai.equals("hoan thanh")){
                           	 trangThaiClass = "status-delivered";
                           }
                            if(trangThai.equals("da huy")){
                           	 trangThaiClass = "status-cancelled";
                           }
                            
                           
                            
                            // Xác định loại thanh toán
                            String paymentClass = "Ship COD".equals(donHang.getHinhThucThanhToan()) ? 
                                "payment-cod" : "payment-card";
                        %>
                            <div class="accordion-item order-card">
                                <h2 class="accordion-header" id="heading<%= index %>">
                                    <button class="accordion-button" type="button" data-bs-toggle="collapse" 
                                            data-bs-target="#collapse<%= index %>" aria-expanded="true" 
                                            aria-controls="collapse<%= index %>">
                                        <div class="d-flex justify-content-between align-items-center w-100">
                                            <div>
                                                <span class="order-id">Đơn hàng #<%= maDonHang %></span>
                                                <span class="status-badge <%= trangThaiClass %>"><%= trangThai %></span>
                                            </div>
                                            <div class="order-date">
                                                <i class="far fa-calendar-alt me-1"></i>
                                                <%= dateFormatter.format(donHang.getNgayDatHang()) %>
                                            </div>
                                        </div>
                                    </button>
                                </h2>
                                <div id="collapse<%= index %>" class="accordion-collapse collapse <%= index == 1 ? "show" : "" %>" 
                                     aria-labelledby="heading<%= index %>" data-bs-parent="#orderAccordion">
                                    <div class="accordion-body">
                                        <div class="row order-info">
                                            <div class="col-md-4 mb-3">
                                                <div class="info-label">
                                                    <i class="fas fa-map-marker-alt me-2"></i>Địa chỉ nhận hàng
                                                </div>
                                                <div class="info-value"><%= donHang.getDiaChiNhanHang() %></div>
                                            </div>
                                            <div class="col-md-4 mb-3">
                                                <div class="info-label">
                                                    <i class="fas fa-phone me-2"></i>Số điện thoại
                                                </div>
                                                <div class="info-value"><%= donHang.getSoDienThoai() %></div>
                                            </div>
                                            <div class="col-md-4 mb-3">
                                                <div class="info-label">
                                                    <i class="fas fa-credit-card me-2"></i>Phương thức thanh toán
                                                </div>
                                                <div class="payment-method <%= paymentClass %>">
                                                    <% if ("Ship COD".equals(donHang.getHinhThucThanhToan())) { %>
                                                        <i class="fas fa-money-bill-wave me-1"></i>
                                                    <% } else { %>
                                                        <i class="far fa-credit-card me-1"></i>
                                                    <% } %>
                                                    <%= donHang.getHinhThucThanhToan() %>
                                                </div>
                                            </div>
                                        </div>
                                        
                                        <% if (donHang.getGhiChu() != null && !donHang.getGhiChu().isEmpty()) { %>
                                            <div class="row mb-4">
                                                <div class="col-12">
                                                    <div class="info-label">
                                                        <i class="fas fa-sticky-note me-2"></i>Ghi chú
                                                    </div>
                                                    <div class="info-value"><%= donHang.getGhiChu() %></div>
                                                </div>
                                            </div>
                                        <% } %>
                                        
                                        <div class="product-list">
                                            <h5 class="mb-3">Chi tiết sản phẩm</h5>
                                            
                                            <% if (chiTietList != null && !chiTietList.isEmpty()) { %>
                                                <% for (ChiTietDonHang chiTiet : chiTietList) { %>
                                                    <div class="product-item">
                                                        <div class="product-image">
                                                            <i class="fas fa-book fa-lg"></i>
                                                        </div>
                                                        <div class="product-details">
                                                            <div class="product-name"><%= chiTiet.getSanPham().getTenSanPham() %></div>
                                                            <div class="d-flex justify-content-between">
                                                                <div class="product-quantity">
                                                                    Số lượng: <%= chiTiet.getSoLuong() %>
                                                                </div>
                                                                <div class="product-price">
                                                                    <%= currencyFormatter.format(chiTiet.getGiaBan()) %>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                <% } %>
                                                
                                                <div class="order-total">
                                                    <span class="total-label">Tổng tiền:</span>
                                                    <span class="total-value"><%= currencyFormatter.format(tongTien) %></span>
                                                </div>
                                            <% } else { %>
                                                <div class="text-center text-muted">Không có thông tin chi tiết sản phẩm</div>
                                            <% } %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="empty-orders">
                <div class="empty-icon">
                    <i class="fas fa-shopping-bag"></i>
                </div>
                <div class="empty-message">Bạn chưa có đơn hàng nào</div>
                <a href="${pageContext.request.contextPath}/xu-ly-phan-trang" class="btn-shop-now">
                    <i class="fas fa-shopping-cart me-2"></i>Mua sắm ngay
                </a>
            </div>
        <% } %>
    </div>
    
    <jsp:include page="../footer.jsp" />
    
    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

