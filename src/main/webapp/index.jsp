<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="FoodExpress - Order delicious food online from your favorite restaurants">
    <title>FoodExpress — Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-card">
            <div class="auth-header">
                <div class="logo">🍕 Food<span class="accent">Express</span></div>
                <p>Welcome back! Sign in to order your favorite food.</p>
            </div>

            <!-- Success Message (from registration) -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">
                    ✅ ${success}
                </div>
            </c:if>

            <!-- Logout Message -->
            <c:if test="${param.logout == 'true'}">
                <div class="alert alert-info">
                    ℹ️ You have been logged out successfully.
                </div>
            </c:if>

            <!-- Error Message -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ❌ ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="POST" id="loginForm">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="Enter your username"
                           value="${username}" required autocomplete="username">
                </div>

                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password"
                           required autocomplete="current-password">
                </div>

                <button type="submit" class="btn btn-primary btn-block btn-lg" id="loginButton">
                    🔓 Sign In
                </button>
            </form>

            <div class="auth-footer">
                Don't have an account? <a href="${pageContext.request.contextPath}/register">Create one</a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
