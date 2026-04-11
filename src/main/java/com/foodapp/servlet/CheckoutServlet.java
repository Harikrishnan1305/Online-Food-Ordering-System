package com.foodapp.servlet;

import com.foodapp.dao.OrderDAO;
import com.foodapp.dao.impl.OrderDAOImpl;
import com.foodapp.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CheckoutServlet — Handles the checkout and order placement flow.
 * GET  → Shows checkout form (address + payment)
 * POST → Places order in DB, clears cart, shows confirmation
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
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

        // Check if cart is empty
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // Pre-fill user address
        User user = (User) session.getAttribute("user");
        request.setAttribute("cart", cart);
        request.setAttribute("userAddress", user.getAddress());
        request.getRequestDispatcher("/checkout.jsp").forward(request, response);
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

        // Get cart from session
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        User user = (User) session.getAttribute("user");

        // Get form data
        String address = request.getParameter("address");
        String paymentMode = request.getParameter("paymentMode");

        // Validation
        if (address == null || address.trim().isEmpty()) {
            request.setAttribute("error", "Please enter a delivery address.");
            request.setAttribute("cart", cart);
            request.setAttribute("userAddress", user.getAddress());
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
            return;
        }

        if (paymentMode == null || paymentMode.trim().isEmpty()) {
            request.setAttribute("error", "Please select a payment method.");
            request.setAttribute("cart", cart);
            request.setAttribute("userAddress", address);
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
            return;
        }

        // Build the Order object
        Order order = new Order();
        order.setUserId(user.getId());
        order.setRestaurantId(cart.getRestaurantId());
        order.setTotalAmount(cart.getTotal());
        order.setStatus("PLACED");
        order.setPaymentMode(paymentMode.trim());
        order.setAddress(address.trim());

        // Build OrderItem list from cart
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {
            OrderItem oi = new OrderItem();
            oi.setMenuId(cartItem.getMenuId());
            oi.setQuantity(cartItem.getQuantity());
            oi.setPrice(cartItem.getPrice());
            orderItems.add(oi);
        }

        // Place order (transactional)
        int orderId = orderDAO.placeOrder(order, orderItems);

        if (orderId > 0) {
            // Success — clear the cart
            cart.clear();
            session.setAttribute("cart", cart);
            session.setAttribute("cartCount", 0);

            // Fetch the placed order for confirmation page
            Order placedOrder = orderDAO.getOrderById(orderId);
            List<OrderItem> placedItems = orderDAO.getOrderItems(orderId);

            request.setAttribute("order", placedOrder);
            request.setAttribute("orderItems", placedItems);
            request.getRequestDispatcher("/order-confirmation.jsp").forward(request, response);

        } else {
            // Failure
            request.setAttribute("error", "Failed to place order. Please try again.");
            request.setAttribute("cart", cart);
            request.setAttribute("userAddress", address);
            request.getRequestDispatcher("/checkout.jsp").forward(request, response);
        }
    }
}
