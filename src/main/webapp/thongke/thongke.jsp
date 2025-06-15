<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <style>
        .card {
            margin-bottom: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .card-header {
            background-color: #f8f9fa;
        }
        .stats-card {
            border-left: 4px solid;
            transition: transform 0.3s;
        }
        .stats-card:hover {
            transform: translateY(-5px);
        }
        .stats-card.primary {
            border-left-color: #4e73df;
        }
        .stats-card.success {
            border-left-color: #1cc88a;
        }
        .stats-card.info {
            border-left-color: #36b9cc;
        }
        .stats-card.warning {
            border-left-color: #f6c23e;
        }
        .stats-icon {
            font-size: 2rem;
            opacity: 0.3;
        }
        .chart-container {
            position: relative;
            height: 300px;
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="container-fluid mt-4">
        <div class="row">
            <div class="col-md-2">
                <!-- Sidebar Menu -->
                <div class="card">
                    <div class="card-header">
                        <h5>Quản trị</h5>
                    </div>
                    <div class="list-group list-group-flush">
                        <a href="${pageContext.request.contextPath}/xu-ly-phan-trang" class="list-group-item list-group-item-action">
                            <i class="fas fa-tachometer-alt mr-2"></i> Trang chủ
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-san-pham" class="list-group-item list-group-item-action">
                            <i class="fas fa-book mr-2"></i> Quản lý sản phẩm
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-don-hang" class="list-group-item list-group-item-action">
                            <i class="fas fa-shopping-cart mr-2"></i> Quản lý đơn hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/quan-ly-khach-hang" class="list-group-item list-group-item-action">
                            <i class="fas fa-users mr-2"></i> Quản lý khách hàng
                        </a>
                        <a href="${pageContext.request.contextPath}/thong-ke" class="list-group-item list-group-item-action active">
                            <i class="fas fa-chart-bar mr-2"></i> Thống kê
                        </a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-10">
                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h4><i class="fas fa-chart-bar mr-2"></i> Thống kê</h4>
                    </div>
                    <div class="card-body">
                        <!-- Bộ lọc thời gian -->
                        <div class="row mb-4">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-body">
                                        <form action="thong-ke" method="get" class="form-inline">
                                            <div class="form-group mr-3">
                                                <label for="startDate" class="mr-2">Từ ngày:</label>
                                                <input type="date" id="startDate" name="startDate" class="form-control" value="${startDate}">
                                            </div>
                                            <div class="form-group mr-3">
                                                <label for="endDate" class="mr-2">Đến ngày:</label>
                                                <input type="date" id="endDate" name="endDate" class="form-control" value="${endDate}">
                                            </div>
                                            <div class="form-group mr-3">
                                                <label for="period" class="mr-2">Thống kê theo:</label>
                                                <select id="period" name="period" class="form-control">
                                                    <option value="day" ${period == 'day' ? 'selected' : ''}>Ngày</option>
                                                    <option value="month" ${period == 'month' ? 'selected' : ''}>Tháng</option>
                                                    <option value="year" ${period == 'year' ? 'selected' : ''}>Năm</option>
                                                </select>
                                            </div>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="fas fa-filter mr-1"></i> Lọc
                                            </button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Thống kê tổng quan -->
                        <div class="row mb-4">
                            <div class="col-md-3">
                                <div class="card stats-card primary">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    Doanh thu
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    <fmt:formatNumber value="${tongDoanhThu}" type="currency" currencySymbol="₫" />
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-dollar-sign stats-icon text-primary"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-3">
                                <div class="card stats-card success">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    Đơn hàng
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    ${tongDonHang}
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-shopping-cart stats-icon text-success"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-3">
                                <div class="card stats-card info">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                    Khách hàng mới
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">
                                                    ${soKhachHangMoi}
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-users stats-icon text-info"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-3">
                                <div class="card stats-card warning">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Tỷ lệ hoàn thành
                                                </div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800" id="tyLeHoanThanh">
                                                    0%
                                                </div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-percentage stats-icon text-warning"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Biểu đồ doanh thu -->
                        <div class="row mb-4">
                            <div class="col-md-8">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title">Biểu đồ doanh thu</h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="chart-container">
                                            <canvas id="revenueChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="col-md-4">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title">Đơn hàng theo trạng thái</h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="chart-container">
                                            <canvas id="orderStatusChart"></canvas>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <!-- Top sản phẩm bán chạy -->
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <h5 class="card-title">Top 5 sản phẩm bán chạy</h5>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table class="table table-bordered">
                                                <thead class="thead-light">
                                                    <tr>
                                                        <th>STT</th>
                                                        <th>Mã sản phẩm</th>
                                                        <th>Tên sản phẩm</th>
                                                        <th>Số lượng bán</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="topProductsTable">
                                                    <tbody>
    <c:forEach var="sanPham" items="${topSanPhamBanChay}" varStatus="loop">
        <tr>
            <td>${loop.index + 1}</td>
            <td>${sanPham.maSanPham}</td>
            <td>${sanPham.tenSanPham}</td>
            <td>${sanPham.soLuongBan}</td>
        </tr>
    </c:forEach>
</tbody>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        // Dữ liệu từ server
        const doanhThuData = ${doanhThuJson};
        const donHangTheoTrangThaiData = ${donHangTheoTrangThaiJson};
        console.log("trangthai: ",donHangTheoTrangThaiData);
        const topSanPhamData = ${topSanPhamJson};
        
        // Tính tỷ lệ hoàn thành
        function calculateCompletionRate() {
            const hoanThanh = donHangTheoTrangThaiData['Hoàn thành'] || 0;
            const tongDon = Object.values(donHangTheoTrangThaiData).reduce((a, b) => a + b, 0);
            const tyLe = tongDon > 0 ? (hoanThanh / tongDon * 100).toFixed(1) : 0;
            document.getElementById('tyLeHoanThanh').textContent = tyLe + '%';
        }
        
        // Vẽ biểu đồ doanh thu
        function drawRevenueChart() {
            const ctx = document.getElementById('revenueChart').getContext('2d');
            
            const labels = Object.keys(doanhThuData);
            const data = Object.values(doanhThuData);
            
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Doanh thu (VNĐ)',
                        data: data,
                        backgroundColor: 'rgba(78, 115, 223, 0.05)',
                        borderColor: 'rgba(78, 115, 223, 1)',
                        pointRadius: 3,
                        pointBackgroundColor: 'rgba(78, 115, 223, 1)',
                        pointBorderColor: 'rgba(78, 115, 223, 1)',
                        pointHoverRadius: 5,
                        pointHoverBackgroundColor: 'rgba(78, 115, 223, 1)',
                        pointHoverBorderColor: 'rgba(78, 115, 223, 1)',
                        pointHitRadius: 10,
                        pointBorderWidth: 2,
                        tension: 0.3,
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    scales: {
                        y: {
                            beginAtZero: true,
                            ticks: {
                                callback: function(value) {
                                    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(value);
                                }
                            }
                        }
                    },
                    plugins: {
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(context.raw);
                                }
                            }
                        }
                    }
                }
            });
        }
        
        // Vẽ biểu đồ đơn hàng theo trạng thái
        function drawOrderStatusChart() {
            const ctx = document.getElementById('orderStatusChart').getContext('2d');
            
            const labels = Object.keys(donHangTheoTrangThaiData);
            const data = Object.values(donHangTheoTrangThaiData);
            
            new Chart(ctx, {
                type: 'doughnut',
                data: {
                    labels: labels,
                    datasets: [{
                        data: data,
                        backgroundColor: [
                            '#FFF3CD', // Chờ xử lý
                            '#D1ECF1', // Đang xử lý
                            '#D4EDDA', // Hoàn thành
                            '#F8D7DA'  // Đã hủy
                        ],
                        hoverBackgroundColor: [
                            '#FFE69C', // Chờ xử lý
                            '#BEE5EB', // Đang xử lý
                            '#C3E6CB', // Hoàn thành
                            '#F5C6CB'  // Đã hủy
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false,
                    plugins: {
                        legend: {
                            position: 'bottom'
                        }
                    }
                }
            });
        }
        
        // Hiển thị top sản phẩm bán chạy
       // function displayTopProducts() {
         //   const tableBody = document.getElementById('topProductsTable');
           // console.log("topSanPhamdataa: ",topSanPhamData)
            //tableBody.innerHTML = '';
            
            //topSanPhamData.forEach((product, index) => {
              //  const row = document.createElement('tr');
                
                // row.innerHTML = `
                  //  <td>${index + 1}</td>
                  //  <td>${product.maSanPham}</td>
                   // <td>${product.tenSanPham}</td>
                   // <td>${product.soLuongBan}</td>
               // `;
                
               // tableBody.appendChild(row);
           // });
            
            // Nếu không có dữ liệu
            //if (topSanPhamData.length === 0) {
              //  const row = document.createElement('tr');
               // row.innerHTML = '<td colspan="4" class="text-center">Không có dữ liệu</td>';
               // tableBody.appendChild(row);
           // }
        //}
        
        // Khởi tạo trang
        document.addEventListener('DOMContentLoaded', function() {
            calculateCompletionRate();
            drawRevenueChart();
            drawOrderStatusChart();
           // displayTopProducts();
        });
    </script>
</body>
</html>