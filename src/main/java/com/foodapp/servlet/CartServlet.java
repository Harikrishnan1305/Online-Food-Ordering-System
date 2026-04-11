package com.foodapp.servlet;

import com.foodapp.dao.MenuDAO;
import com.foodapp.dao.impl.MenuDAOImpl;
import com.foodapp.model.Cart;
import com.foodapp.model.CartItem;
import com.foodapp.model.Menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * CartServlet — Handles all cart operations via session.
 * 
 * Actions (via 'action' parameter):
 *   add    → Add item to cart (POST)
 *   remove → Remove item from cart (POST)
 *   update → Update item quantity (POST)
 *   view   → Display cart contents (GET)
 */
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private MenuDAO menuDAO;

    @Override
    public void init() throws ServletException {
        menuDAO = new MenuDAOImpl();
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

        // Get or create cart from session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        request.setAttribute("cart", cart);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Get or create cart from session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
        }

        switch (action) {
            case "add":
                handleAddItem(request, cart);
                break;

            case "remove":
                handleRemoveItem(request, cart);
                break;

            case "update":
                handleUpdateQuantity(request, cart);
                break;

            default:
                break;
        }

        // Save updated cart back to session
        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.getItemCount());

        // Determine redirect target
        String redirectTo = request.getParameter("redirectTo");
        if (redirectTo != null && !redirectTo.isEmpty()) {
            response.sendRedirect(request.getContextPath() + redirectTo);
        } else {
            response.sendRedirect(request.getContextPath() + "/cart");
        }
    }

    /**
     * Add an item to the cart.
     * Fetches the Menu item from DB to get accurate details.
     */
    private void handleAddItem(HttpServletRequest request, Cart cart) {
        String menuIdParam = request.getParameter("menuId");
        if (menuIdParam == null) return;

        try {
            int menuId = Integer.parseInt(menuIdParam);
            Menu menu = menuDAO.getMenuById(menuId);

            if (menu != null) {
                CartItem cartItem = new CartItem(
                    menu.getId(),
                    menu.getName(),
                    menu.getPrice(),
                    1,
                    menu.getRestaurantId(),
                    menu.getRestaurantName(),
                    menu.getImageUrl()
                );

                boolean wasReset = cart.addItem(cartItem);

                if (wasReset) {
                    // Cart was reset because of restaurant change
                    HttpSession session = request.getSession();
                    session.setAttribute("cartMessage", "Cart was reset. Items from " + menu.getRestaurantName() + " added.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove an item from the cart.
     */
    private void handleRemoveItem(HttpServletRequest request, Cart cart) {
        String menuIdParam = request.getParameter("menuId");
        if (menuIdParam == null) return;

        try {
            int menuId = Integer.parseInt(menuIdParam);
            cart.removeItem(menuId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update item quantity (increase / decrease).
     */
    private void handleUpdateQuantity(HttpServletRequest request, Cart cart) {
        String menuIdParam = request.getParameter("menuId");
        String quantityParam = request.getParameter("quantity");
        if (menuIdParam == null || quantityParam == null) return;

        try {
            int menuId = Integer.parseInt(menuIdParam);
            int quantity = Integer.parseInt(quantityParam);
            cart.updateQuantity(menuId, quantity);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
