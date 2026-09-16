package com.movieticket.model;

public class SeatBean {

	private String showSeatId;

	private String seatId;

	private String mallId;

	private String mallName;

	private String seatTypeId;

	private String rowName;

	private int seatNumber;

	private String typeName;

	private double price;

	private String status;

	private String section;

	private boolean active;

	public String getShowSeatId() {
		return showSeatId;
	}

	public void setShowSeatId(String showSeatId) {
		this.showSeatId = showSeatId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getMallId() {
		return mallId;
	}

	public void setMallId(String mallId) {
		this.mallId = mallId;
	}

	public String getMallName() {
		return mallName;
	}

	public void setMallName(String mallName) {
		this.mallName = mallName;
	}

	public String getSeatTypeId() {
		return seatTypeId;
	}

	public void setSeatTypeId(String seatTypeId) {
		this.seatTypeId = seatTypeId;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}