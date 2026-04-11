<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Your shopping cart - FoodExpress">
    <title>FoodExpress — Cart</title>
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
                <a href="${pageContext.request.contextPath}/cart" class="cart-link active">
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
        <div class="cart-container">

            <c:choose>
                <%-- ===== Cart has items ===== --%>
                <c:when test="${not empty cart && !cart.empty}">

                    <!-- Cart Header -->
                    <div class="cart-header">
                        <h2>🛒 Your Cart</h2>
                        <div class="cart-restaurant-info">
                            🏪 Ordering from: <strong>${cart.restaurantName}</strong>
                        </div>
                    </div>

                    <!-- Cart Items -->
                    <c:forEach var="item" items="${cart.cartItems}">
                        <div class="cart-item">
                            <div class="item-image">
                                <img src="${item.imageUrl}" alt="${item.name}"
                                     onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=100'">
                            </div>

                            <div class="item-details">
                                <div class="item-name">${item.name}</div>
                                <div class="item-price">₹<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/> each</div>
                            </div>

                            <!-- Quantity Controls -->
                            <div class="quantity-controls">
                                <form action="${pageContext.request.contextPath}/cart" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="menuId" value="${item.menuId}">
                                    <input type="hidden" name="quantity" value="${item.quantity - 1}">
                                    <button type="submit" class="qty-btn" ${item.quantity <= 1 ? 'disabled' : ''}>−</button>
                                </form>

                                <span class="qty-value" id="qty-${item.menuId}">${item.quantity}</span>

                                <form action="${pageContext.request.contextPath}/cart" method="POST" style="display:inline;">
                                    <input type="hidden" name="action" value="update">
                                    <input type="hidden" name="menuId" value="${item.menuId}">
                                    <input type="hidden" name="quantity" value="${item.quantity + 1}">
                                    <button type="submit" class="qty-btn">+</button>
                                </form>
                            </div>

                            <!-- Subtotal -->
                            <div class="item-subtotal">
                                ₹<fmt:formatNumber value="${item.subtotal}" pattern="#,##0.00"/>
                            </div>

                            <!-- Remove Button -->
                            <form action="${pageContext.request.contextPath}/cart" method="POST" style="display:inline;">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="menuId" value="${item.menuId}">
                                <button type="submit" class="btn-remove" title="Remove item">✕</button>
                            </form>
                        </div>
                    </c:forEach>

                    <!-- Cart Summary -->
                    <div class="cart-summary">
                        <div class="summary-row">
                            <span>Items in cart</span>
                            <span>${cart.itemCount}</span>
                        </div>
                        <div class="summary-row">
                            <span>Delivery Fee</span>
                            <span style="color: var(--success);">FREE</span>
                        </div>
                        <div class="summary-row total">
                            <span>Total Amount</span>
                            <span class="amount">₹<fmt:formatNumber value="${cart.total}" pattern="#,##0.00"/></span>
                        </div>
                    </div>

                    <!-- Cart Actions -->
                    <div class="cart-actions">
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">
                            ← Continue Shopping
                        </a>
                        <a href="${pageContext.request.contextPath}/checkout" class="btn btn-primary btn-lg">
                            🚀 Proceed to Checkout
                        </a>
                    </div>
                </c:when>

                <%-- ===== Cart is empty ===== --%>
                <c:otherwise>
                    <div class="empty-state">
                        <div class="empty-icon">🛒</div>
                        <h2>Your cart is empty</h2>
                        <p>Looks like you haven't added anything to your cart yet. Explore restaurants and add some delicious food!</p>
                        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary btn-lg">
                            🍕 Browse Restaurants
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
