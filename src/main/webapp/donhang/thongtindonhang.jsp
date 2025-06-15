<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="model.GioHang" %>
<%@ page import="model.ChiTietGioHang" %>
<%@ page import="model.KhachHang" %>
<%@ page import="database.GioHangDAO" %>
<%@ page import="database.HinhAnhSanPhamDAO" %>
<%@ page import="model.HinhAnhSanPham" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thông tin đơn hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
            padding-top: 20px;
            padding-bottom: 50px;
        }
        .checkout-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0, 0, 0, 0.1);
            padding: 25px;
            margin-bottom: 30px;
        }
        .checkout-header {
            border-bottom: 1px solid #e9ecef;
            padding-bottom: 15px;
            margin-bottom: 20px;
        }
        .checkout-title {
            font-size: 24px;
            font-weight: 600;
            color: #343a40;
        }
        .form-label {
            font-weight: 500;
            margin-bottom: 8px;
        }
        .form-control {
            padding: 10px 15px;
            border-radius: 5px;
            border: 1px solid #ced4da;
            margin-bottom: 15px;
        }
        .form-control:focus {
            border-color: #80bdff;
            box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
        }
        .payment-methods {
            margin-top: 20px;
        }
        .payment-method {
            display: flex;
            align-items: center;
            padding: 15px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            margin-bottom: 15px;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        .payment-method:hover {
            border-color: #80bdff;
        }
        .payment-method.selected {
            border-color: #007bff;
            background-color: #f0f7ff;
        }
        .payment-method-icon {
            font-size: 24px;
            margin-right: 15px;
            color: #6c757d;
        }
        .payment-method-details {
            flex-grow: 1;
        }
        .payment-method-title {
            font-weight: 600;
            margin-bottom: 5px;
        }
        .payment-method-description {
            font-size: 14px;
            color: #6c757d;
        }
        .order-summary {
            background-color: #f8f9fa;
            border-radius: 10px;
            padding: 20px;
        }
        .summary-title {
            font-size: 18px;
            font-weight: 600;
            margin-bottom: 15px;
        }
        .summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
        }
        .summary-total {
            font-size: 18px;
            font-weight: 600;
            border-top: 1px solid #dee2e6;
            padding-top: 15px;
            margin-top: 15px;
        }
        .place-order-btn {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 12px;
            border-radius: 5px;
            font-weight: 600;
            width: 100%;
            margin-top: 15px;
        }
        .place-order-btn:hover {
            background-color: #218838;
        }
        .cart-item {
            padding: 15px 0;
            border-bottom: 1px solid #e9ecef;
        }
        .cart-item:last-child {
            border-bottom: none;
        }
        .product-image {
            width: 60px;
            height: 60px;
            object-fit: contain;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        .product-name {
            font-weight: 600;
            color: #343a40;
        }
        .product-price {
            font-weight: 600;
        }
        .required-field::after {
            content: "*";
            color: red;
            margin-left: 4px;
        }
        .back-to-cart {
            color: #007bff;
            text-decoration: none;
            display: inline-block;
            margin-top: 15px;
        }
        .back-to-cart:hover {
            text-decoration: underline;
        }
        .alert {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="../header.jsp" />
    
    <div class="container mt-4">
        <%
            // Hiển thị thông báo lỗi nếu có
            String error = request.getParameter("error");
            if (error != null) {
        %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <% if ("empty_cart".equals(error)) { %>
                    <i class="fas fa-exclamation-circle me-2"></i> Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.
                <% } else if ("missing_fields".equals(error)) { %>
                    <i class="fas fa-exclamation-circle me-2"></i> Vui lòng điền đầy đủ thông tin bắt buộc.
                <% } else if ("payment_cancelled".equals(error)) { %>
                    <i class="fas fa-exclamation-circle me-2"></i> Bạn đã hủy quá trình thanh toán. Vui lòng thử lại.
                <% } else if ("invalid_payment_method".equals(error)) { %>
                    <i class="fas fa-exclamation-circle me-2"></i> Phương thức thanh toán không hợp lệ. Vui lòng chọn phương thức thanh toán khác.
                <% } else { %>
                    <i class="fas fa-exclamation-circle me-2"></i> Đã xảy ra lỗi. Vui lòng thử lại sau.
                <% } %>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <%
            }
        
            // Lấy thông tin khách hàng từ session
            KhachHang khachHang = (KhachHang) session.getAttribute("khachHang");
            if (khachHang == null) {
                response.sendRedirect(request.getContextPath() + "/dang-nhap?redirect=thanh-toan");
                return;
            }
            
            // Lấy giỏ hàng từ database
            GioHangDAO gioHangDAO = new GioHangDAO();
            GioHang gioHang = gioHangDAO.getGioHangByKhachHang(khachHang.getMaKhachHang());
            
            if (gioHang == null || gioHang.getDanhSachChiTiet() == null || gioHang.getDanhSachChiTiet().isEmpty()) {
        %>
            <div class="alert alert-warning" role="alert">
                <i class="fas fa-shopping-cart me-2"></i> Giỏ hàng của bạn đang trống. Vui lòng thêm sản phẩm vào giỏ hàng trước khi thanh toán.
                <a href="${pageContext.request.contextPath}/trang-chu" class="alert-link">Tiếp tục mua sắm</a>
            </div>
        <%
            } else {
                // Lấy hình ảnh sản phẩm
                HinhAnhSanPhamDAO hinhAnhDAO = new HinhAnhSanPhamDAO();
                Map<Integer, List<HinhAnhSanPham>> hinhAnhMap = (Map<Integer, List<HinhAnhSanPham>>) request.getAttribute("hinhAnhSanPhamMap");
                
                if (hinhAnhMap == null) {
                    hinhAnhMap = new HashMap<>();
                    for (ChiTietGioHang chiTiet : gioHang.getDanhSachChiTiet()) {
                        int maSanPham = chiTiet.getSanPham().getMaSanPham();
                        List<HinhAnhSanPham> danhSachHinhAnh = hinhAnhDAO.getSanPhamsByMaSanPham(maSanPham);
                        hinhAnhMap.put(maSanPham, danhSachHinhAnh);
                    }
                }
                
                // Tính tổng tiền
                double tongTien = 0;
                for (ChiTietGioHang chiTiet : gioHang.getDanhSachChiTiet()) {
                    double donGia = chiTiet.getSanPham().getGiaGiam() > 0 && chiTiet.getSanPham().getGiaGiam() < chiTiet.getSanPham().getGiaBan() 
                                  ? chiTiet.getSanPham().getGiaGiam() : chiTiet.getSanPham().getGiaBan();
                    tongTien += donGia * chiTiet.getSoLuong();
                }
        %>
        
        <div class="row">
            <div class="col-lg-8">
                <div class="checkout-container">
                    <div class="checkout-header">
                        <h2 class="checkout-title">Thông tin đơn hàng</h2>
                    </div>
                    
                    <form action="${pageContext.request.contextPath}/thanh-toan" method="post" id="orderForm">
                        <input type="hidden" name="ngayDatHang" value="<%= LocalDate.now() %>">
                        
                        <!-- Thông tin khách hàng -->
                        <div class="mb-4">
                            <h4>Thông tin khách hàng</h4>
                            
                            <div class="mb-3">
                                <label for="hoVaTen" class="form-label required-field">Họ và tên</label>
                                <input type="text" class="form-control" id="hoVaTen" name="hoVaTen" value="<%= khachHang.getHoVaTen() %>" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="soDienThoai" class="form-label required-field">Số điện thoại</label>
                                <input type="tel" class="form-control" id="soDienThoai" name="soDienThoai" value="<%= khachHang.getSoDienThoai() != null ? khachHang.getSoDienThoai() : "" %>" required pattern="[0-9]{10}">
                                <small class="text-muted">Vui lòng nhập số điện thoại 10 chữ số</small>
                            </div>
                            
                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email" value="<%= khachHang.getEmail() != null ? khachHang.getEmail() : "" %>">
                            </div>
                        </div>
                        
                        <!-- Thông tin giao hàng -->
                        <div class="mb-4">
                            <h4>Thông tin giao hàng</h4>
                            
                            <div class="mb-3">
                                <label for="diaChiNhanHang" class="form-label required-field">Địa chỉ nhận hàng</label>
                                <textarea class="form-control" id="diaChiNhanHang" name="diaChiNhanHang" rows="3" required><%= khachHang.getDiaChi() != null ? khachHang.getDiaChi() : "" %></textarea>
                            </div>
                            
                            <div class="mb-3">
                                <label for="ghiChu" class="form-label">Ghi chú</label>
                                <textarea class="form-control" id="ghiChu" name="ghiChu" rows="3" placeholder="Ghi chú về đơn hàng, ví dụ: thời gian hay chỉ dẫn địa điểm giao hàng chi tiết hơn."></textarea>
                            </div>
                        </div>
                        
                        <!-- Phương thức thanh toán -->
                        <div class="payment-methods">
                            <h4>Phương thức thanh toán</h4>
                            
                            <div class="payment-method selected" onclick="selectPaymentMethod(this, 'Thanh toán khi nhận hàng')">
                                <div class="payment-method-icon">
                                    <i class="fas fa-money-bill-wave"></i>
                                </div>
                                <div class="payment-method-details">
                                    <div class="payment-method-title">Ship COD</div>
                                    <div class="payment-method-description">Bạn chỉ phải thanh toán khi nhận được hàng</div>
                                </div>
                                <div class="payment-method-radio">
                                    <input type="radio" name="hinhThucThanhToan" value="Ship COD" checked>
                                </div>
                            </div>
                            
                            <div class="payment-method" onclick="selectPaymentMethod(this, 'Thanh toán qua thẻ')">
                                <div class="payment-method-icon">
                                    <i class="fas fa-credit-card"></i>
                                </div>
                                <div class="payment-method-details">
                                    <div class="payment-method-title">Credit Card</div>
                                    <div class="payment-method-description">Thanh toán an toàn với Stripe</div>
                                </div>
                                <div class="payment-method-radio">
                                    <input type="radio" name="hinhThucThanhToan" value="Credit Card">
                                </div>
                            </div>
                        </div>
                        
                        <button type="submit" class="place-order-btn">
                            <i class="fas fa-check-circle me-2"></i> Đặt hàng
                        </button>
                        
                        <a href="${pageContext.request.contextPath}/gio-hang" class="back-to-cart">
                            <i class="fas fa-arrow-left"></i> Quay lại giỏ hàng
                        </a>
                    </form>
                </div>
            </div>
            
            <div class="col-lg-4">
                <div class="order-summary">
                    <h3 class="summary-title">Đơn hàng của bạn</h3>
                    
                    <!-- Danh sách sản phẩm -->
                    <div class="cart-items mb-4">
                        <% for (ChiTietGioHang chiTiet : gioHang.getDanhSachChiTiet()) { 
                            int maSanPham = chiTiet.getSanPham().getMaSanPham();
                            List<HinhAnhSanPham> danhSachHinhAnh = hinhAnhMap.get(maSanPham);
                            String hinhAnh = "/placeholder.svg?height=60&width=60";
                            if (danhSachHinhAnh != null && !danhSachHinhAnh.isEmpty()) {
                                hinhAnh = danhSachHinhAnh.get(0).getUrl();
                            }
                            
                            double donGia = chiTiet.getSanPham().getGiaGiam() > 0 && chiTiet.getSanPham().getGiaGiam() < chiTiet.getSanPham().getGiaBan() 
                                          ? chiTiet.getSanPham().getGiaGiam() : chiTiet.getSanPham().getGiaBan();
                            double thanhTien = donGia * chiTiet.getSoLuong();
                        %>
                            <div class="cart-item row align-items-center">
                                <div class="col-3">
                                    <img src="<%= hinhAnh %>" alt="<%= chiTiet.getSanPham().getTenSanPham() %>" class="product-image">
                                </div>
                                <div class="col-6">
                                    <div class="product-name"><%= chiTiet.getSanPham().getTenSanPham() %></div>
                                    <div class="text-muted small">SL: <%= chiTiet.getSoLuong() %></div>
                                </div>
                                <div class="col-3 text-end">
                                    <div class="product-price">
                                        <fmt:formatNumber value="<%= thanhTien %>" pattern="#,###" />₫
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                    
                    <!-- Tổng tiền -->
                    <div class="summary-item">
                        <span>Tạm tính:</span>
                        <span><fmt:formatNumber value="<%= tongTien %>" pattern="#,###" />₫</span>
                    </div>
                    
                    <div class="summary-item">
                        <span>Phí vận chuyển:</span>
                        <span>Miễn phí</span>
                    </div>
                    
                    <div class="summary-total">
                        <span>Tổng cộng:</span>
                        <span><fmt:formatNumber value="<%= tongTien %>" pattern="#,###" />₫</span>
                    </div>
                </div>
            </div>
        </div>
        <% } %>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.1/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        function selectPaymentMethod(element, method) {
            // Xóa class selected từ tất cả các phương thức thanh toán
            document.querySelectorAll('.payment-method').forEach(el => {
                el.classList.remove('selected');
                el.querySelector('input[type="radio"]').checked = false;
            });
            
            // Thêm class selected cho phương thức được chọn
            element.classList.add('selected');
            element.querySelector('input[type="radio"]').checked = true;
        }
        
        // Kiểm tra form trước khi submit
        document.getElementById('orderForm').addEventListener('submit', function(event) {
            const soDienThoai = document.getElementById('soDienThoai').value;
            const diaChiNhanHang = document.getElementById('diaChiNhanHang').value;
            
            if (!soDienThoai || !diaChiNhanHang) {
                event.preventDefault();
                alert('Vui lòng điền đầy đủ thông tin bắt buộc.');
                return;
            }
            
            // Kiểm tra định dạng số điện thoại
            const phonePattern = /^[0-9]{10}$/;
            if (!phonePattern.test(soDienThoai)) {
                event.preventDefault();
                alert('Số điện thoại phải có 10 chữ số.');
                return;
            }
        });
    </script>
    
    <jsp:include page="../footer.jsp" />
</body>
</html>