package com.foodapp.servlet;

import com.foodapp.dao.UserDAO;
import com.foodapp.dao.impl.UserDAOImpl;
import com.foodapp.model.User;
import com.foodapp.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * RegisterServlet — Handles new user registration.
 * GET  → Shows registration form
 * POST → Creates user with hashed password
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        // Validation
        if (name == null || name.trim().isEmpty() ||
            username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            email == null || email.trim().isEmpty()) {

            request.setAttribute("error", "Please fill in all required fields.");
            preserveFormData(request, name, username, email, phone, address);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Password match check
        if (!password.equals(confirmPassword)) {
            request.setAttribute("error", "Passwords do not match.");
            preserveFormData(request, name, username, email, phone, address);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Password length check
        if (password.length() < 6) {
            request.setAttribute("error", "Password must be at least 6 characters long.");
            preserveFormData(request, name, username, email, phone, address);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Check if username already exists
        if (userDAO.isUsernameExists(username.trim())) {
            request.setAttribute("error", "Username '" + username + "' is already taken. Please choose another.");
            preserveFormData(request, name, "", email, phone, address);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // Hash password with SHA-256
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Create user object
        User user = new User(
            name.trim(),
            username.trim(),
            hashedPassword,
            email.trim(),
            phone != null ? phone.trim() : "",
            address != null ? address.trim() : "",
            "CUSTOMER"
        );

        // Save to database
        boolean success = userDAO.addUser(user);

        if (success) {
            request.setAttribute("success", "Registration successful! Please login with your credentials.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            preserveFormData(request, name, username, email, phone, address);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }

    private void preserveFormData(HttpServletRequest request, String name, String username, String email, String phone, String address) {
        request.setAttribute("name", name);
        request.setAttribute("username", username);
        request.setAttribute("email", email);
        request.setAttribute("phone", phone);
        request.setAttribute("address", address);
    }
}
