<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Browse restaurants and order food online with FoodExpress">
    <title>FoodExpress — Home</title>
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
                <a href="${pageContext.request.contextPath}/home" class="active">🏠 Home</a>
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
        <!-- Page Header -->
        <div class="page-header">
            <h1>🔥 Discover Restaurants</h1>
            <p>Order delicious food from the best restaurants near you</p>
        </div>

        <!-- Search Bar -->
        <div class="search-container">
            <form class="search-form" action="${pageContext.request.contextPath}/home" method="GET">
                <span class="search-icon">🔍</span>
                <input type="text" name="search" id="searchInput"
                       placeholder="Search restaurants or cuisines..."
                       value="${searchQuery}" autocomplete="off">
                <button type="submit">Search</button>
            </form>
        </div>

        <!-- Search Results Info -->
        <c:if test="${not empty searchQuery}">
            <div class="alert alert-info" style="max-width: 600px; margin: 0 auto 1.5rem;">
                🔍 Showing results for "<strong>${searchQuery}</strong>"
                <a href="${pageContext.request.contextPath}/home" style="margin-left: auto; color: var(--primary);">✕ Clear</a>
            </div>
        </c:if>

        <!-- Restaurant Cards Grid -->
        <c:choose>
            <c:when test="${not empty restaurants}">
                <div class="cards-grid">
                    <c:forEach var="restaurant" items="${restaurants}" varStatus="status">
                        <div class="restaurant-card"
                             data-href="${pageContext.request.contextPath}/menu?restaurantId=${restaurant.id}"
                             style="animation-delay: ${status.index * 0.05}s">
                            <div class="card-image">
                                <img src="${restaurant.imageUrl}" alt="${restaurant.name}"
                                     onerror="this.src='https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=400'">
                                <div class="rating-badge">
                                    ⭐ ${restaurant.rating}
                                </div>
                            </div>
                            <div class="card-body">
                                <h3 class="card-title">${restaurant.name}</h3>
                                <p class="card-cuisine">${restaurant.cuisine}</p>
                                <div class="card-meta">
                                    <span>🕐 ${restaurant.deliveryTime} min</span>
                                    <span>📍 Nearby</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
            <c:otherwise>
                <div class="empty-state">
                    <div class="empty-icon">🍽️</div>
                    <h2>No restaurants found</h2>
                    <p>Try a different search term or browse all restaurants</p>
                    <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">View All Restaurants</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
