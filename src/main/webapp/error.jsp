<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FoodExpress — Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="error-container" style="min-height: 80vh; display: flex; flex-direction: column; align-items: center; justify-content: center;">

            <div class="error-code">
                <c:choose>
                    <c:when test="${not empty pageContext.errorData.statusCode}">
                        ${pageContext.errorData.statusCode}
                    </c:when>
                    <c:otherwise>
                        Oops!
                    </c:otherwise>
                </c:choose>
            </div>

            <h2 style="font-family: var(--font-display); font-size: 1.8rem; margin-bottom: 0.5rem;">
                Something went wrong
            </h2>

            <c:if test="${not empty error}">
                <p style="color: var(--text-secondary); margin-bottom: 1rem;">${error}</p>
            </c:if>

            <p style="color: var(--text-muted); margin-bottom: 2rem; max-width: 400px; text-align: center;">
                The page you're looking for might have been moved, deleted, or doesn't exist. Let's get you back on track.
            </p>

            <div style="display: flex; gap: 1rem;">
                <a href="${pageContext.request.contextPath}/home" class="btn btn-primary">
                    🏠 Go to Home
                </a>
                <a href="javascript:history.back()" class="btn btn-secondary">
                    ← Go Back
                </a>
            </div>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>
