package com.movieticket.controller.booking;

import java.io.IOException;
import java.util.List;

import com.movieticket.dao.BookingDAO;
import com.movieticket.model.BookingBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/bookings")
public class BookingHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BookingDAO bookingDAO = new BookingDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        String userId = String.valueOf(session.getAttribute("userId"));
        List<BookingBean> bookings = bookingDAO.getBookingsByUserId(userId);
        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("/booking-history.jsp").forward(request, response);
    }
}