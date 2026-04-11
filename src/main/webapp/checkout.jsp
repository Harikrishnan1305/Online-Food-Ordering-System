<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Checkout - Complete your food order on FoodExpress">
    <title>FoodExpress — Checkout</title>
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
                <a href="${pageContext.request.contextPath}/cart" class="cart-link">
                    🛒 Cart
                    <c:if test="${not empty sessionScope.cartCount && sessionScope.cartCount > 0}">
                        <span class="cart-badge">${sessionScope.cartCount}</span>
                    </c:if>
                </a>
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
        <div class="checkout-container">

            <div class="page-header" style="padding: 2rem 0 1.5rem;">
                <h1>📦 Checkout</h1>
                <p>Complete your order from <strong>${cart.restaurantName}</strong></p>
            </div>

            <!-- Error Message -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ❌ ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/checkout" method="POST" id="checkoutForm">

                <!-- Order Summary Section -->
                <div class="checkout-section">
                    <h3>🛍️ Order Summary</h3>
                    <c:forEach var="item" items="${cart.cartItems}">
                        <div style="display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px solid var(--border-color); color: var(--text-secondary); font-size: 0.95rem;">
                            <span>${item.name} × ${item.quantity}</span>
                            <span style="font-weight: 600; color: var(--text-primary);">₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/></span>
                        </div>
                    </c:forEach>
                    <div style="display: flex; justify-content: space-between; padding: 12px 0 0; font-weight: 700; font-size: 1.1rem; color: var(--primary); font-family: var(--font-display);">
                        <span>Total</span>
                        <span>₹<fmt:formatNumber value="${cart.total}" pattern="#,##0.00"/></span>
                    </div>
                </div>

                <!-- Delivery Address Section -->
                <div class="checkout-section">
                    <h3>📍 Delivery Address</h3>
                    <div class="form-group" style="margin-bottom: 0;">
                        <textarea name="address" id="address" rows="3"
                                  placeholder="Enter your full delivery address"
                                  required>${not empty userAddress ? userAddress : ''}</textarea>
                    </div>
                </div>

                <!-- Payment Method Section -->
                <div class="checkout-section">
                    <h3>💳 Payment Method</h3>
                    <div class="payment-options">
                        <div class="payment-option">
                            <input type="radio" name="paymentMode" id="cod" value="Cash on Delivery" checked>
                            <label for="cod">
                                <span class="pay-icon">💵</span>
                                Cash on Delivery
                            </label>
                        </div>
                        <div class="payment-option">
                            <input type="radio" name="paymentMode" id="upi" value="UPI">
                            <label for="upi">
                                <span class="pay-icon">📱</span>
                                UPI Payment
                            </label>
                        </div>
                        <div class="payment-option">
                            <input type="radio" name="paymentMode" id="card" value="Credit/Debit Card">
                            <label for="card">
                                <span class="pay-icon">💳</span>
                                Card Payment
                            </label>
                        </div>
                        <div class="payment-option">
                            <input type="radio" name="paymentMode" id="wallet" value="Wallet">
                            <label for="wallet">
                                <span class="pay-icon">👛</span>
                                Digital Wallet
                            </label>
                        </div>
                    </div>
                </div>

                <!-- Action Buttons -->
                <div class="cart-actions">
                    <a href="${pageContext.request.contextPath}/cart" class="btn btn-secondary">
                        ← Back to Cart
                    </a>
                    <button type="submit" class="btn btn-success btn-lg" id="placeOrderBtn" style="flex: 2;">
                        ✅ Place Order — ₹<fmt:formatNumber value="${cart.total}" pattern="#,##0.00"/>
                    </button>
                </div>
            </form>

        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script>
        // Prevent double submission
        document.getElementById('checkoutForm').addEventListener('submit', function() {
            const btn = document.getElementById('placeOrderBtn');
            btn.disabled = true;
            btn.textContent = '⏳ Placing Order...';
        });
    </script>
</body>
</html>
