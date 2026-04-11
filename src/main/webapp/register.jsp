<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Create a new FoodExpress account">
    <title>FoodExpress — Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="auth-wrapper">
        <div class="auth-card" style="max-width: 500px;">
            <div class="auth-header">
                <div class="logo">🍕 Food<span class="accent">Express</span></div>
                <p>Create your account and start ordering!</p>
            </div>

            <!-- Error Message -->
            <c:if test="${not empty error}">
                <div class="alert alert-error">
                    ❌ ${error}
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/register" method="POST" id="registerForm">
                <div class="form-group">
                    <label for="name">Full Name *</label>
                    <input type="text" id="name" name="name" placeholder="Enter your full name"
                           value="${name}" required>
                </div>

                <div class="form-group">
                    <label for="username">Username *</label>
                    <input type="text" id="username" name="username" placeholder="Choose a username"
                           value="${username}" required autocomplete="username">
                </div>

                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" placeholder="Enter your email"
                           value="${email}" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number</label>
                    <input type="tel" id="phone" name="phone" placeholder="Enter your phone number"
                           value="${phone}">
                </div>

                <div class="form-group">
                    <label for="address">Delivery Address</label>
                    <textarea id="address" name="address" placeholder="Enter your delivery address"
                              rows="2">${address}</textarea>
                </div>

                <div class="form-group">
                    <label for="password">Password * (min 6 characters)</label>
                    <input type="password" id="password" name="password" placeholder="Create a password"
                           required minlength="6" autocomplete="new-password">
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Confirm Password *</label>
                    <input type="password" id="confirmPassword" name="confirmPassword"
                           placeholder="Re-enter your password" required autocomplete="new-password">
                </div>

                <button type="submit" class="btn btn-primary btn-block btn-lg" id="registerButton">
                    🚀 Create Account
                </button>
            </form>

            <div class="auth-footer">
                Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In</a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
    <script>
        // Client-side password match validation
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            const pass = document.getElementById('password').value;
            const confirm = document.getElementById('confirmPassword').value;
            if (pass !== confirm) {
                e.preventDefault();
                alert('Passwords do not match!');
            }
        });
    </script>
</body>
</html>
