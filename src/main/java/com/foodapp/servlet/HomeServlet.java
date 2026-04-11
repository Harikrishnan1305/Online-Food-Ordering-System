package com.foodapp.servlet;

import com.foodapp.dao.RestaurantDAO;
import com.foodapp.dao.impl.RestaurantDAOImpl;
import com.foodapp.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * HomeServlet — Fetches all restaurants and forwards to home.jsp.
 * Also handles search queries.
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String search = request.getParameter("search");
        List<Restaurant> restaurants;

        if (search != null && !search.trim().isEmpty()) {
            restaurants = restaurantDAO.searchRestaurants(search.trim());
            request.setAttribute("searchQuery", search.trim());
        } else {
            restaurants = restaurantDAO.getAllRestaurants();
        }

        request.setAttribute("restaurants", restaurants);
        request.getRequestDispatcher("/home.jsp").forward(request, response);
    }
}
