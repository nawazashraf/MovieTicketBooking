package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.UserBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // User must be logged in
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        String userId = (String) session.getAttribute("userId");

        UserBean user = userDAO.getUserById(userId);

        if (user != null) {
            request.setAttribute("user", user);

            request.getRequestDispatcher("/profile.jsp")
                   .forward(request, response);
        } else {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }
}