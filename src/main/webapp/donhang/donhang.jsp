<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt Hàng</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">Thông Tin Đơn Hàng</h2>
        <form action="../dat-hang" method="post" class="border p-4 rounded shadow">
            
            <div class="mb-3">
                <label for="tenKhachHang" class="form-label">Tên Khách Hàng</label>
                <input type="text" class="form-control" id="tenKhachHang" name="tenKhachHang" required>
            </div>
            <div class="mb-3">
                <label for="soDienThoai" class="form-label">Số Điện Thoại</label>
                <input type="text" class="form-control" id="soDienThoai" name="soDienThoai" required>
            </div>
            <div class="mb-3">
                <label for="diaChiNhanHang" class="form-label">Địa Chỉ Nhận Hàng</label>
                <textarea class="form-control" id="diaChiNhanHang" name="diaChiNhanHang" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <label for="hinhThucThanhToan" class="form-label">Hình Thức Thanh Toán</label>
                <select class="form-select" id="hinhThucThanhToan" name="hinhThucThanhToan" required>
                    <option value="Chuyển khoản">Chuyển khoản</option>
                    <option value="Thanh toán khi nhận hàng">Thanh toán khi nhận hàng</option>
                </select>
            </div>
            <div class="mb-3">
                <label for="diaChiNhanHang" class="form-label">Ghi chú đơn hàng</label>
                <textarea class="form-control" id="ghiChu" name="ghiChu" rows="3" ></textarea>
            </div>

            <button type="submit" class="btn btn-primary w-100">Đặt Hàng</button>
        </form>
    </div>
</body>
</html>
