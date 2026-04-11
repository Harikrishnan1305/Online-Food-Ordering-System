package com.foodapp.servlet;

import com.foodapp.dao.MenuDAO;
import com.foodapp.dao.RestaurantDAO;
import com.foodapp.dao.impl.MenuDAOImpl;
import com.foodapp.dao.impl.RestaurantDAOImpl;
import com.foodapp.model.Menu;
import com.foodapp.model.Restaurant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * MenuServlet — Fetches menu items for a given restaurant.
 * URL: /menu?restaurantId=1
 */
@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private MenuDAO menuDAO;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
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

        String restaurantIdParam = request.getParameter("restaurantId");

        if (restaurantIdParam == null || restaurantIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            int restaurantId = Integer.parseInt(restaurantIdParam);

            // Get restaurant details
            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            if (restaurant == null) {
                request.setAttribute("error", "Restaurant not found.");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
                return;
            }

            // Get menu items for this restaurant
            List<Menu> menuList = menuDAO.getMenuByRestaurantId(restaurantId);

            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuList", menuList);
            request.getRequestDispatcher("/menu.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}
