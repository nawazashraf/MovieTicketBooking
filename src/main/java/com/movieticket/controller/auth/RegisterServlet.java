package com.movieticket.controller.auth;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.UserBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // Get data from registration form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        // Create UserBean
        UserBean user = new UserBean();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);

        // Normal users register with USER role
        user.setRole("USER");
        user.setStatus(true);

        // Save user in database
        boolean registered = userDAO.registerUser(user);

        if (registered) {

            response.sendRedirect(
                request.getContextPath() + "/login.jsp"
            );

        } else {

            request.setAttribute(
                "error",
                "Registration failed. Email or phone may already exist."
            );

            request.getRequestDispatcher("/register.jsp")
                   .forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
            request.getContextPath() + "/register.jsp"
        );
    }
}