package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/changepassword")
public class ChangePasswordServlet extends HttpServlet {

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

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        request.getRequestDispatcher("/changepassword.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );
            return;
        }

        String userId = (String) session.getAttribute("userId");

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (currentPassword == null ||
            newPassword == null ||
            confirmPassword == null ||
            currentPassword.isEmpty() ||
            newPassword.isEmpty() ||
            confirmPassword.isEmpty()) {

            request.setAttribute("error", "All fields are required.");

            request.getRequestDispatcher("/changepassword.jsp")
                   .forward(request, response);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {

            request.setAttribute(
                "error",
                "New password and confirm password do not match."
            );

            request.getRequestDispatcher("/changepassword.jsp")
                   .forward(request, response);
            return;
        }

        boolean changed = userDAO.changePassword(
            userId,
            currentPassword,
            newPassword
        );

        if (changed) {

            response.sendRedirect(
                request.getContextPath() + "/profile"
            );

        } else {

            request.setAttribute(
                "error",
                "Current password is incorrect."
            );

            request.getRequestDispatcher("/changepassword.jsp")
                   .forward(request, response);
        }
    }
}