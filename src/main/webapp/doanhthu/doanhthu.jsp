<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh sách giao dịch Stripe</title>
</head>
<body>
    <h2>Danh sách các giao dịch Stripe</h2>
    <ul>
         <%
            java.util.List<String> transactions = (java.util.List<String>) request.getAttribute("transactions");
            if (transactions == null) {
                transactions = new java.util.ArrayList<>();
            }

            if (!transactions.isEmpty()) {
                for (String transaction : transactions) {
        %>
            <li><%= transaction %></li>
        <% 
                }
            } else { 
        %>
            <p>Không có giao dịch nào.</p>
        <% } %>
    </ul>
</body>
</html>
