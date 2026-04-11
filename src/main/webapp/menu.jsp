<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Menu items from ${restaurant.name} - Order now on FoodExpress">
    <title>FoodExpress — ${restaurant.name} Menu</title>
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

        <!-- Cart Reset Message -->
        <c:if test="${not empty sessionScope.cartMessage}">
            <div class="alert alert-warning" style="max-width: 900px; margin: 1rem auto;">
                ⚠️ ${sessionScope.cartMessage}
            </div>
            <c:remove var="cartMessage" scope="session"/>
        </c:if>

        <!-- Restaurant Header -->
        <div class="restaurant-header">
            <div class="rest-image">
                <img src="${restaurant.imageUrl}" alt="${restaurant.name}"
                     onerror="this.src='https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=200'">
            </div>
            <div class="rest-info">
                <h1>${restaurant.name}</h1>
                <p class="rest-cuisine">${restaurant.cuisine}</p>
                <div class="rest-meta">
                    <span><span class="rating-value">⭐ ${restaurant.rating}</span></span>
                    <span>🕐 ${restaurant.deliveryTime} min delivery</span>
                </div>
            </div>
        </div>

        <!-- Menu Section Title -->
        <div class="page-header" style="padding: 1.5rem 0 1rem;">
            <h1 style="font-size: 1.8rem;">📋 Menu</h1>
            <p>${fn:length(menuList)} items available</p>
        </div>

        <!-- Menu Items Grid -->
        <c:choose>
            <c:when test="${not empty menuList}">
                <div class="cards-grid">
                    <c:forEach var="item" items="${menuList}" varStatus="status">
                        <div class="menu-card" style="animation-delay: ${status.index * 0.05}s">
                            <div class="card-image">
                                <img src="${item.imageUrl}" alt="${item.name}"
                                     onerror="this.src='https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=300'">
                            </div>
                            <div class="card-body">
                                <h3 class="card-title">${item.name}</h3>
                                <p class="card-description">${item.description}</p>
                                <div class="card-footer">
                                    <span class="price">₹<fmt:formatNumber value="${item.price}" pattern="#,##0.00"/></span>
                                    <span class="rating">⭐ ${item.rating}</span>
                                </div>

                                <!-- Add to Cart Button -->
                                <form action="${pageContext.request.contextPath}/cart" method="POST" style="margin-top: 12px;">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="menuId" value="${item.id}">
                                    <input type="hidden" name="redirectTo" value="/menu?restaurantId=${restaurant.id}">
                                    <button type="submit" class="btn-add-cart" id="addToCart_${item.id}">
                                        🛒 Add to Cart
                                    </button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-icon">📋</div>
                    <h2>No menu items available</h2>
                    <p>This restaurant hasn't added any items yet</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">← Back to Restaurants</a>
                </div>
            </c:otherwise>
        </c:choose>

        <!-- Back Button -->
        <div style="text-align: center; padding: 2rem 0;">
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">← Back to Restaurants</a>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
