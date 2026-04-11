<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Order confirmed! Your food is on its way.">
    <title>FoodExpress — Order Confirmed! 🎉</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <!-- ===== Navbar ===== -->
    <nav class="navbar">
        <div class="navbar-container">
            <a href="${pageContext.request.contextPath}/home" class="navbar-brand">
                <span class="logo-icon">🍕</span>
                Food<span class="brand-accent">Express</span>
            </a>

            <div class="navbar-links">
                <a href="${pageContext.request.contextPath}/home">🏠 Home</a>
                <a href="${pageContext.request.contextPath}/cart" class="cart-link">🛒 Cart</a>
                <div class="user-info">
                    <div class="user-avatar">${fn:substring(sessionScope.userName, 0, 1)}</div>
                    <span>${sessionScope.userName}</span>
                </div>
                <a href="${pageContext.request.contextPath}/logout" class="btn-logout">🚪 Logout</a>
            </div>
        </div>
    </nav>

    <!-- ===== Main Content ===== -->
    <div class="container">
        <div class="confirmation-container">

            <!-- Success Icon -->
            <div class="confirmation-icon">🎉</div>

            <h1>Order Placed Successfully!</h1>
            <p class="order-id">
                Your Order ID: <span>#${order.orderId}</span>
            </p>

            <!-- Order Details Card -->
            <div class="order-details-card">
                <div class="detail-row">
                    <span class="label">Restaurant</span>
                    <span class="value">${order.restaurantName}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Status</span>
                    <span class="value" style="color: var(--success);">✅ ${order.status}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Payment Mode</span>
                    <span class="value">${order.paymentMode}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Delivery Address</span>
                    <span class="value">${order.address}</span>
                </div>
                <div class="detail-row">
                    <span class="label">Order Date</span>
                    <span class="value">
                        <fmt:formatDate value="${order.orderDate}" pattern="dd MMM yyyy, hh:mm a"/>
                    </span>
                </div>

                <!-- Order Items -->
                <div style="margin-top: 1rem; padding-top: 1rem; border-top: 1px solid var(--border-color);">
                    <h4 style="margin-bottom: 0.8rem; font-family: var(--font-display); font-weight: 700;">Items Ordered</h4>
                    <c:forEach var="item" items="${orderItems}">
                        <div style="display: flex; justify-content: space-between; padding: 6px 0; color: var(--text-secondary); font-size: 0.9rem;">
                            <span>${item.menuName} × ${item.quantity}</span>
                            <span style="font-weight: 600; color: var(--text-primary);">₹<fmt:formatNumber value="${item.price * item.quantity}" pattern="#,##0.00"/></span>
                        </div>
                    </c:forEach>
                </div>

                <!-- Total -->
                <div class="detail-row" style="margin-top: 0.8rem; padding-top: 0.8rem; border-top: 1px solid var(--border-color);">
                    <span class="label" style="font-size: 1.1rem; font-weight: 700; color: var(--text-primary);">Total Amount</span>
                    <span class="value" style="font-size: 1.3rem; color: var(--primary); font-family: var(--font-display);">₹<fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.00"/></span>
                </div>
            </div>

            <!-- Action Buttons -->
            <div class="cart-actions" style="max-width: 500px; margin: 0 auto;">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">
                    🍕 Order More Food
                </a>
            </div>

            <!-- Delivery Estimate -->
            <div style="margin-top: 2rem; padding: 1rem; background: var(--bg-glass); border-radius: var(--radius-md); color: var(--text-secondary);">
                <p>🚚 Estimated delivery time: <strong style="color: var(--accent);">25-35 minutes</strong></p>
                <p style="font-size: 0.85rem; margin-top: 4px; color: var(--text-muted);">You can track your order status in the app.</p>
            </div>

        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
