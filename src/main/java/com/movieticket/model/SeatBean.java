package com.movieticket.model;

public class SeatBean {

	private String showSeatId;
	private String rowName;
	private int seatNumber;
	private String typeName;
	private double price;
	private String status;

	public String getShowSeatId() {
		return showSeatId;
	}

	public void setShowSeatId(String showSeatId) {
		this.showSeatId = showSeatId;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}