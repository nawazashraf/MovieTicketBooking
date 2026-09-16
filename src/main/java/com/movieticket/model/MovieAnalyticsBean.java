package com.movieticket.model;

public class MovieAnalyticsBean {
	private String movieTitle;
	private int ticketsSold;
	private double revenue;

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movitTitle) {
		this.movieTitle = movitTitle;
	}

	public int getTicketsSold() {
		return ticketsSold;
	}

	public void setTicketsSold(int ticketsSold) {
		this.ticketsSold = ticketsSold;
	}

	public double getRevenue() {
		return revenue;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

}
