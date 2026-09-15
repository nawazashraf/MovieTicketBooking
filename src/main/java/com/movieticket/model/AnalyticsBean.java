package com.movieticket.model;

import java.math.BigDecimal;

public class AnalyticsBean {
	private BigDecimal totalRevenue;
	private int totalBookings;
	private int confirmedBookings;
	private int totalTicketsSold;
	private int totalUsers;

	private BigDecimal revenueToday;
	private BigDecimal revenueThisMonth;

	public BigDecimal getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(BigDecimal totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalBookings() {
		return totalBookings;
	}

	public void setTotalBookings(int totalBookings) {
		this.totalBookings = totalBookings;
	}

	public int getConfirmedBookings() {
		return confirmedBookings;
	}

	public void setConfirmedBookings(int confirmedBookings) {
		this.confirmedBookings = confirmedBookings;
	}

	public int getTotalTicketsSold() {
		return totalTicketsSold;
	}

	public void setTotalTicketsSold(int totalTicketsSold) {
		this.totalTicketsSold = totalTicketsSold;
	}

	public int getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(int totalUsers) {
		this.totalUsers = totalUsers;
	}

	public BigDecimal getRevenueToday() {
		return revenueToday;
	}

	public void setRevenueToday(BigDecimal revenueToday) {
		this.revenueToday = revenueToday;
	}

	public BigDecimal getRevenueThisMonth() {
		return revenueThisMonth;
	}

	public void setRevenueThisMonth(BigDecimal revenueThisMonth) {
		this.revenueThisMonth = revenueThisMonth;
	}

}
