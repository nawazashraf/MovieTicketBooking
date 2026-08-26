package com.movieticket.test;

import java.sql.Connection;

import com.movieticket.util.DBConnection;


public class TestDBConnection {

    public static void main(String[] args) {
    	
    	Connection con = DBConnection.getConnection();
        

        if (con != null) {
            System.out.println("Database connected successfully!");
        } else {
            System.out.println("Database connection failed!");
        }
    }
}