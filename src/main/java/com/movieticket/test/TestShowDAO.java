package com.movieticket.test;

import java.util.List;

import com.movieticket.dao.ShowDAO;
import com.movieticket.model.ShowBean;

public class TestShowDAO {

    public static void main(String[] args) {
        ShowDAO showDAO = new ShowDAO();

        List<ShowBean> shows = showDAO.getAllShows();

        System.out.println("Total shows: " + shows.size());

        for (ShowBean show : shows) {
            System.out.println(
                show.getShowId() + " | "
                + show.getMovieName() + " | "
                + show.getMallName() + " | "
                + show.getShowDate() + " | "
                + show.getStartTime()
            );
        }
    }
}