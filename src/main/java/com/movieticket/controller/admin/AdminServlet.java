package com.movieticket.controller.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.movieticket.dao.MovieDAO;
import com.movieticket.dao.SeatManagementDAO;
import com.movieticket.dao.ShowDAO;
import com.movieticket.dao.TheatreDAO;
import com.movieticket.model.MovieBean;
import com.movieticket.model.SeatBean;
import com.movieticket.model.ShowBean;
import com.movieticket.model.TheatreBean;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final MovieDAO movieDAO = new MovieDAO();
    private final TheatreDAO theatreDAO = new TheatreDAO();
    private final ShowDAO showDAO = new ShowDAO();
    private final SeatManagementDAO seatDAO = new SeatManagementDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request, response)) return;

        loadData(request);
        request.getRequestDispatcher("/admin/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!isAdmin(request, response)) return;

        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        boolean success = false;
        String message = "";

        try {
            switch (action == null ? "" : action) {
            case "addMovie":
                success = movieDAO.addMovie(readMovie(request, null));
                message = success ? "Movie added successfully." : "Unable to add movie.";
                break;
            case "updateMovie":
                success = movieDAO.updateMovie(readMovie(request, request.getParameter("id")));
                message = success ? "Movie updated successfully." : "Unable to update movie.";
                break;
            case "deleteMovie":
                success = movieDAO.deleteMovie(request.getParameter("id"));
                message = success ? "Movie deleted successfully." : "Unable to delete movie. It may have related shows.";
                break;
            case "addTheatre":
                success = theatreDAO.addTheatre(readTheatre(request, null));
                message = success ? "Theatre added successfully." : "Unable to add theatre.";
                break;
            case "updateTheatre":
                success = theatreDAO.updateTheatre(readTheatre(request, request.getParameter("id")));
                message = success ? "Theatre updated successfully." : "Unable to update theatre.";
                break;
            case "deleteTheatre":
                success = theatreDAO.deleteTheatre(request.getParameter("id"));
                message = success ? "Theatre deleted successfully." : "Unable to delete theatre. It may have related shows/seats.";
                break;
            case "addShow": {
                ShowBean show = readShow(request, null);
                success = showDAO.addShow(show);
                if (success) {
                    double price = parseDouble(request.getParameter("seatPrice"), 0.0);
                    showDAO.syncShowSeats(show.getShowId(), show.getMallId(), price);
                }
                message = success ? "Show added successfully and seats prepared." : "Unable to add show.";
                break;
            }
            case "updateShow": {
                ShowBean show = readShow(request, request.getParameter("id"));
                success = showDAO.updateShow(show);
                if (success && request.getParameter("seatPrice") != null && !request.getParameter("seatPrice").isBlank()) {
                    showDAO.syncShowSeats(show.getShowId(), show.getMallId(), parseDouble(request.getParameter("seatPrice"), 0.0));
                }
                message = success ? "Show updated successfully." : "Unable to update show.";
                break;
            }
            case "deleteShow":
                success = showDAO.deleteShow(request.getParameter("id"));
                message = success ? "Show deleted successfully." : "Unable to delete show.";
                break;
            case "generateShowSeats":
                success = showDAO.syncShowSeats(request.getParameter("showId"), request.getParameter("mallId"),
                        parseDouble(request.getParameter("seatPrice"), 0.0));
                message = success ? "Show seats generated/updated successfully." : "Unable to generate show seats.";
                break;
            case "addSeat":
                success = seatDAO.addSeat(readSeat(request, null));
                message = success ? "Seat added successfully." : "Unable to add seat.";
                break;
            case "updateSeat":
                success = seatDAO.updateSeat(readSeat(request, request.getParameter("id")));
                message = success ? "Seat updated successfully." : "Unable to update seat.";
                break;
            case "deleteSeat":
                success = seatDAO.deleteSeat(request.getParameter("id"));
                message = success ? "Seat deleted successfully." : "Unable to delete seat. It may already be used by a show.";
                break;
            default:
                message = "Unknown admin action.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "Operation failed: " + e.getMessage();
        }

        response.sendRedirect(request.getContextPath() + "/admin?message="
                + java.net.URLEncoder.encode(message, java.nio.charset.StandardCharsets.UTF_8));
    }

    private boolean isAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return false;
        }
        String role = String.valueOf(session.getAttribute("userRole"));
        if (!"ADMIN".equalsIgnoreCase(role) && !"MALL_ADMIN".equalsIgnoreCase(role)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access required");
            return false;
        }
        return true;
    }

    private void loadData(HttpServletRequest request) {
        request.setAttribute("movies", movieDAO.getAllMovies());
        request.setAttribute("theatres", theatreDAO.getAllTheatres());
        request.setAttribute("shows", showDAO.getAllShows());
        request.setAttribute("seats", seatDAO.getAllSeats());
        request.setAttribute("seatTypes", seatDAO.getSeatTypes());
        request.setAttribute("genres", movieDAO.getAllGenres());
        String editMovie = request.getParameter("editMovie");
        String editTheatre = request.getParameter("editTheatre");
        String editShow = request.getParameter("editShow");
        String editSeat = request.getParameter("editSeat");
        if (editMovie != null) request.setAttribute("editMovieBean", movieDAO.getMovieById(editMovie));
        if (editTheatre != null) request.setAttribute("editTheatreBean", theatreDAO.getTheatreById(editTheatre));
        if (editShow != null) request.setAttribute("editShowBean", showDAO.getShowById(editShow));
        if (editSeat != null) request.setAttribute("editSeatBean", seatDAO.getSeatById(editSeat));
        String message = request.getParameter("message");
        if (message != null) request.setAttribute("message", message);
    }

    private MovieBean readMovie(HttpServletRequest r, String id) {
        MovieBean m = new MovieBean();
        m.setId(id);
        m.setTitle(r.getParameter("title"));
        m.setDescription(r.getParameter("description"));
        m.setDurationMinutes(parseInt(r.getParameter("durationMinutes"), 0));
        m.setLanguage(r.getParameter("language"));
        String release = r.getParameter("releaseDate");
        if (release != null && !release.isBlank()) m.setReleaseDate(Date.valueOf(release));
        m.setCertificate(r.getParameter("certificate"));
        m.setPosterUrl(r.getParameter("posterUrl"));
        m.setTrailerUrl(r.getParameter("trailerUrl"));
        m.setStatus(r.getParameter("status"));
        String[] genres = r.getParameterValues("genreIds");
        m.setGenreIds(genres == null ? List.of() : Arrays.asList(genres));
        return m;
    }

    private TheatreBean readTheatre(HttpServletRequest r, String id) {
        TheatreBean t = new TheatreBean();
        t.setId(id);
        t.setName(r.getParameter("name"));
        t.setAddress(r.getParameter("address"));
        t.setCity(r.getParameter("city"));
        t.setState(r.getParameter("state"));
        t.setPincode(parseInt(r.getParameter("pincode"), 0));
        t.setStatus("on".equalsIgnoreCase(r.getParameter("status")) || "true".equalsIgnoreCase(r.getParameter("status")));
        return t;
    }

    private ShowBean readShow(HttpServletRequest r, String id) {
        ShowBean s = new ShowBean();
        s.setShowId(id);
        s.setMovieId(r.getParameter("movieId"));
        s.setMallId(r.getParameter("mallId"));
        s.setShowDate(r.getParameter("showDate"));
        s.setStartTime(r.getParameter("startTime"));
        s.setEndTime(r.getParameter("endTime"));
        s.setStatus(r.getParameter("status"));
        return s;
    }

    private SeatBean readSeat(HttpServletRequest r, String id) {
        SeatBean s = new SeatBean();
        s.setSeatId(id);
        s.setMallId(r.getParameter("mallId"));
        s.setSeatTypeId(r.getParameter("seatTypeId"));
        s.setRowName(r.getParameter("rowName"));
        s.setSeatNumber(parseInt(r.getParameter("seatNumber"), 0));
        s.setActive("on".equalsIgnoreCase(r.getParameter("status")) || "true".equalsIgnoreCase(r.getParameter("status")));
        return s;
    }

    private int parseInt(String value, int fallback) {
        try { return Integer.parseInt(value); } catch (Exception e) { return fallback; }
    }

    private double parseDouble(String value, double fallback) {
        try { return Double.parseDouble(value); } catch (Exception e) { return fallback; }
    }
}