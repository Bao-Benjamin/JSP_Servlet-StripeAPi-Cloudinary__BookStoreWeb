<%@page import="model.TheLoai"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.KhachHang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
    /* Thêm vào phần style hiện có */
    .category-filter-container {
        transition: all 0.3s ease;
        border-bottom: 1px solid #e0e0e0;
    }
    
    .category-filter-container .btn {
        transition: all 0.2s ease;
        border-radius: 20px;
        padding: 0.25rem 0.75rem;
    }
    
    .category-filter-container .btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    
    .category-filter-container .btn-primary {
        background-color: #0d6efd;
        border-color: #0d6efd;
    }
    
    .category-filter-container .btn-outline-primary:hover {
        background-color: #0d6efd;
        border-color: #0d6efd;
        color: white;
    }
    .category-filter-container {
        transition: all 0.3s ease;
        border-bottom: 1px solid #e0e0e0;
        animation: slideDown 0.3s ease-out;
    }
    
    @keyframes slideDown {
        from {
            opacity: 0;
            transform: translateY(-20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }
    
    .category-filter-container .btn {
        transition: all 0.2s ease;
        border-radius: 20px;
        padding: 0.25rem 0.75rem;
    }
    
    .category-filter-container .btn:hover {
        transform: translateY(-2px);
        box-shadow: 0 2px 5px rgba(0,0,0,0.1);
    }
    
    .category-filter-container .btn-primary {
        background-color: #0d6efd;
        border-color: #0d6efd;
    }
    
    .category-filter-container .btn-outline-primary:hover {
        background-color: #0d6efd;
        border-color: #0d6efd;
        color: white;
    }
    
    .category-filter-container .btn-close {
        font-size: 0.75rem;
        opacity: 0.7;
        transition: opacity 0.2s;
    }
    
    .category-filter-container .btn-close:hover {
        opacity: 1;
    }
    #categoryFilterContainer{
    	 position: fixed;
            top: 20;
            left: 0;
            width: 100%;
            z-index: 1000;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
    }
</style>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    Object obj = session.getAttribute("khachHang");
    KhachHang khachHang = null;
    if (obj != null) {
        khachHang = (KhachHang) obj;
    }
%>

<!-- Navbar -->
<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> 
            <img src="img/logo/logo.png" alt="Bootstrap" height="24">
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="xu-ly-phan-trang">Trang chủ</a>
                </li>
                 <% if (khachHang != null) { 
                		if ("admin".equals(khachHang.getVaiTro())){
                
                
                %>
                <li class="nav-item">
                    <a class="nav-link" href="the-loai?action=hienThi">Quản lý thể loại</a>
                </li>
                <% }} %>
               <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
        aria-expanded="false" id="categoryDropdown"> Thể loại </a>
    <ul class="dropdown-menu">
        <%
            ArrayList<TheLoai> danhSachTheLoai = (ArrayList<TheLoai>) request.getAttribute("danhSachTheLoai");
            if (danhSachTheLoai != null) {
                for (TheLoai tl : danhSachTheLoai) {
        %>
                    <li><a class="dropdown-item" href="loc-san-pham?tenTheLoai=<%=tl.getTenTheLoai()%>"><%=tl.getTenTheLoai()%></a></li>
        <%
                }
            } else {
        %>
                <li><a class="dropdown-item" href="/Bookstore/lay-danh-sach-the-loai?page=filter">Lọc theo thể loại</a></li>
        <%
            }
        %>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#" id="toggleCategoryFilter">
            <i class="fas fa-filter me-1"></i> Hiển thị bộ lọc nhanh
        </a></li>
    </ul>
</li>
                <li class="nav-item">
                        <a class="nav-link" href="loc-san-pham-toys">Đồ chơi</a>
                    </li>
                 <li class="nav-item">
                        <a class="nav-link" href="loc-san-pham-vpp">Văn phòng phẩm</a>
                    </li>
				 <% if (khachHang != null) { 
                		if ("admin".equals(khachHang.getVaiTro())){
                
                
                %>
                <li class="nav-item">
                    <a class="nav-link" href="/Bookstore/lay-danh-sach-the-loai?page=add">Thêm Sản Phẩm</a>
                </li>

				 <% }else{%>
					 
					 <li class="nav-item">
	                    <a class="nav-link" href="/Bookstore/hien-thi-don-hang-da-mua">Đơn Hàng Của Bạn</a>
	                </li>
				 
				 
				  
				 <% }}%>
				 
                <%-- Kiểm tra nếu đã đăng nhập thì hiển thị mục "Quản lý đơn hàng" --%>
                <% if (khachHang != null) { 
                		if ("admin".equals(khachHang.getVaiTro())){
                
                
                %>
                
                    <li class="nav-item">
                        <a class="nav-link" href="quan-ly-don-hang">Quản lý </a>
                    </li>
                <% }} %>

                <%-- Nếu chưa đăng nhập thì hiển thị giỏ hàng --%>
                <% if (khachHang != null) { 
                	if("customer".equals(khachHang.getVaiTro())){
                		%>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=url %>/gio-hang">🛒</a>
                    </li>
                <% }} %>
            </ul>
				
           
                <!-- Form tìm kiếm -->
            <form class="d-flex" role="search" id="searchForm">
                <input class="form-control me-2" type="search" id="searchInput" placeholder="Nội dung tìm kiếm" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Tìm</button>
            </form>
                        <!-- Dropdown gợi ý -->
            <div id="suggestions" style="position: absolute; z-index: 1000; background-color: white; border: 1px solid #ccc; display: none;"></div>
            
                <%-- Nếu chưa đăng nhập thì hiển thị nút Đăng nhập --%>
                <% if (khachHang == null) { %>
                    <a class="btn btn-primary ms-2" href="khachhang/dangnhap.jsp">Đăng nhập</a>
                <% } else { %>
                    <ul class="navbar-nav ms-2">
                        <li class="nav-item dropdown dropstart">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                aria-expanded="false"> Tài khoản </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Đơn hàng của tôi</a></li>
                                <li><a class="dropdown-item" href="#">Thông báo</a></li>
                  
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/khachhang/thaydoithongtin.jsp">Thay đổi thông tin</a></li>
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/khachhang/doimatkhau.jsp">Đổi mật khẩu</a></li>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/khach-hang?hanhDong=dang-xuat">Thoát tài khoản</a></li>
                            </ul>
                        </li>
                    </ul>
                <% } %>
           
        </div>
    </div>
</nav>
<!-- Category Filter Buttons - Chỉ hiển thị khi showCategoryFilter = true -->


<!-- Category Filter Buttons - Ẩn ban đầu -->
<div class="container-fluid bg-light py-2 mb-3 shadow-sm category-filter-container" id="categoryFilterContainer" style="display: none;">
    <div class="container">
        <div class="d-flex flex-wrap align-items-center">
            <div class="me-3 mb-2 mb-md-0">
                <span class="fw-bold text-primary">
                    <i class="fas fa-filter me-1"></i> Lọc theo thể loại:
                </span>
                <button type="button" class="btn-close ms-2" aria-label="Close" id="closeCategoryFilter"></button>
            </div>
            <div class="d-flex flex-wrap gap-2">
                <a href="${pageContext.request.contextPath}/loc-san-pham-theo-the-loai" 
                   class="btn btn-sm ${maTheLoai == 0 ? 'btn-primary' : 'btn-outline-primary'}">
                    Tất cả
                </a>
                
                <c:forEach var="tl" items="${danhSachTheLoai}">
                    <a href="${pageContext.request.contextPath}/loc-san-pham-theo-the-loai?maTheLoai=${tl.maTheLoai}" 
                       class="btn btn-sm ${maTheLoai == tl.maTheLoai ? 'btn-primary' : 'btn-outline-primary'}">
                        ${tl.tenTheLoai}
                    </a>
                </c:forEach>
                
                <% 
                    if (danhSachTheLoai != null && (request.getAttribute("danhSachTheLoai") == null || ((ArrayList<TheLoai>)request.getAttribute("danhSachTheLoai")).isEmpty())) {
                        for (TheLoai tl : danhSachTheLoai) {
                            int tlMaTheLoai = tl.getMaTheLoai();
                            String tlTenTheLoai = tl.getTenTheLoai();
                            int currentMaTheLoai = 0;
                            try {
                                Object maTheLoaiObj = request.getAttribute("maTheLoai");
                                if (maTheLoaiObj != null) {
                                    currentMaTheLoai = (Integer) maTheLoaiObj;
                                }
                            } catch (Exception e) {
                                // Không làm gì nếu không có maTheLoai
                            }
                            String btnClass = (tlMaTheLoai == currentMaTheLoai) ? "btn-primary" : "btn-outline-primary";
                %>
                            <a href="${pageContext.request.contextPath}/loc-san-pham-theo-the-loai?maTheLoai=<%=tlMaTheLoai%>" 
                               class="btn btn-sm <%=btnClass%>">
                                <%=tlTenTheLoai%>
                            </a>
                <%
                        }
                    }
                %>
            </div>
        </div>
    </div>
</div>
<!-- Thêm thư viện jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<script>
$(document).ready(function() {
    // Xử lý sự kiện nhấn Enter
    $('#searchInput').on('keypress', function(e) {
        if (e.which === 13) { // 13 là mã phím Enter
            e.preventDefault();
            performSearch();
        }
    });

    $('#searchForm').on('submit', function(e) {
        e.preventDefault(); // Ngăn form gửi theo cách truyền thống
        var query = $('#searchInput').val();
        window.location.href = 'tim-kiem?query=' + encodeURIComponent(query);
    });

    // Xử lý sự kiện nhập liệu để gợi ý
    $('#searchInput').on('input', function() {
        var query = $(this).val();
        if (query.length >= 2) { // Chỉ gửi yêu cầu khi có ít nhất 2 ký tự
            $.ajax({
                url: 'de-xuat-tim-kiem',
                type: 'GET',
                data: { query: query },
                success: function(data) {
                    $('#suggestions').html(data).show();
                }
            });
        } else {
            $('#suggestions').hide();
        }
    });

    // Xử lý sự kiện click vào gợi ý
    $('#suggestions').on('click', 'div', function() {
        $('#searchInput').val($(this).text());
        $('#suggestions').hide();
        performSearch();
    });

    // Hàm thực hiện tìm kiếm
    function performSearch() {
        var query = $('#searchInput').val();
        window.location.href = 'tim-kiem?query=' + encodeURIComponent(query);
    }
});

document.addEventListener('DOMContentLoaded', function() {
    // Lấy các phần tử
    const toggleButton = document.getElementById('toggleCategoryFilter');
    const categoryDropdown = document.getElementById('categoryDropdown');
    const filterContainer = document.getElementById('categoryFilterContainer');
    const closeButton = document.getElementById('closeCategoryFilter');
    
    // Lưu trạng thái hiển thị vào localStorage
    const loadFilterState = () => {
        const isVisible = localStorage.getItem('categoryFilterVisible') === 'true';
        filterContainer.style.display = isVisible ? 'block' : 'none';
    };
    
    // Lưu trạng thái hiển thị
    const saveFilterState = (isVisible) => {
        localStorage.setItem('categoryFilterVisible', isVisible);
    };
    
    // Khởi tạo trạng thái ban đầu
    loadFilterState();
    
    // Xử lý sự kiện click vào nút toggle
    if (toggleButton) {
        toggleButton.addEventListener('click', function(e) {
            e.preventDefault();
            const isVisible = filterContainer.style.display === 'block';
            filterContainer.style.display = isVisible ? 'none' : 'block';
            saveFilterState(!isVisible);
            
            // Hiệu ứng cuộn đến bộ lọc nếu hiển thị
            if (!isVisible) {
                filterContainer.scrollIntoView({ behavior: 'smooth', block: 'start' });
            }
        });
    }
    
    // Xử lý sự kiện click vào nút đóng
    if (closeButton) {
        closeButton.addEventListener('click', function() {
            filterContainer.style.display = 'none';
            saveFilterState(false);
        });
    }
    
    // Xử lý sự kiện click vào dropdown "Thể loại"
    if (categoryDropdown) {
        categoryDropdown.addEventListener('click', function(e) {
            // Không làm gì đặc biệt ở đây, chỉ để dropdown hoạt động bình thường
        });
    }
    
    // Hiển thị bộ lọc nếu có tham số maTheLoai trong URL
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has('maTheLoai')) {
        filterContainer.style.display = 'block';
        saveFilterState(true);
    }
});
</script>
<!-- End Navbar -->
