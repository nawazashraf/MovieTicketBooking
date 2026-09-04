package com.movieticket.controller.movie;

import java.io.IOException;
import java.util.List;

import com.movieticket.dao.ShowDAO;
import com.movieticket.model.ShowBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/shows")
public class ShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final ShowDAO showDAO = new ShowDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<ShowBean> shows = showDAO.getAllShows();
        request.setAttribute("shows", shows);

        request.getRequestDispatcher("/movie/shows.jsp")
               .forward(request, response);
    }
}