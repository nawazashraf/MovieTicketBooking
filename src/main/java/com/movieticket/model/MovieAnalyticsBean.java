package com.movieticket.model;

public class MovieAnalyticsBean {
	private String movitTitle;
	private int ticketsSold;
	private double revenue;

	public String getMovitTitle() {
		return movitTitle;
	}

	public void setMovitTitle(String movitTitle) {
		this.movitTitle = movitTitle;
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
