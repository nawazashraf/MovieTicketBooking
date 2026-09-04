package com.movieticket.test;

import java.util.List;

import com.movieticket.dao.TheatreDAO;
import com.movieticket.model.TheatreBean;

public class TestTheatreDAO {

    public static void main(String[] args) {
        TheatreDAO theatreDAO = new TheatreDAO();

        List<TheatreBean> theatres = theatreDAO.getAllTheatres();

        System.out.println("Total theatres: " + theatres.size());

        for (TheatreBean theatre : theatres) {
            System.out.println(
                theatre.getId() + " | "
                + theatre.getName() + " | "
                + theatre.getCity()
            );
        }
    }
}