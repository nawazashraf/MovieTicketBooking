package com.movieticket.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class TicketBean {

	private String bookingId;

	private String bookingReference;

	private String movieTitle;

	private String mallName;

	private Date showDate;

	private Time startTime;

	private String seats;

	private BigDecimal totalAmount;

	private String paymentMethod;

	private String transactionId;

	private String paymentStatus;

	// Additional movie details
	private String language;

	private Integer durationMinutes;

	private String certificate;

	private String posterUrl;

	private String genre;

	// Additional cinema details
	private String mallAddress;

	private String city;

	private String state;

	private Integer pincode;

	// Show details
	private Time endTime;

	// Customer details
	private String customerName;

	private String customerEmail;

	private String customerPhone;

	// Booking details
	private String bookingStatus;

	private Timestamp bookingDate;

	// Payment details
	private Timestamp paidAt;

	public String getBookingId() {

		return bookingId;

	}

	public void setBookingId(String bookingId) {

		this.bookingId = bookingId;

	}

	public String getBookingReference() {

		return bookingReference;

	}

	public void setBookingReference(String bookingReference) {

		this.bookingReference = bookingReference;

	}

	public String getMovieTitle() {

		return movieTitle;

	}

	public void setMovieTitle(String movieTitle) {

		this.movieTitle = movieTitle;

	}

	public String getMallName() {

		return mallName;

	}

	public void setMallName(String mallName) {

		this.mallName = mallName;

	}

	public Date getShowDate() {

		return showDate;

	}

	public void setShowDate(Date showDate) {

		this.showDate = showDate;

	}

	public Time getStartTime() {

		return startTime;

	}

	public void setStartTime(Time startTime) {

		this.startTime = startTime;

	}

	public String getSeats() {

		return seats;

	}

	public void setSeats(String seats) {

		this.seats = seats;

	}

	public BigDecimal getTotalAmount() {

		return totalAmount;

	}

	public void setTotalAmount(BigDecimal totalAmount) {

		this.totalAmount = totalAmount;

	}

	public String getPaymentMethod() {

		return paymentMethod;

	}

	public void setPaymentMethod(String paymentMethod) {

		this.paymentMethod = paymentMethod;

	}

	public String getTransactionId() {

		return transactionId;

	}

	public void setTransactionId(String transactionId) {

		this.transactionId = transactionId;

	}

	public String getPaymentStatus() {

		return paymentStatus;

	}

	public void setPaymentStatus(String paymentStatus) {

		this.paymentStatus = paymentStatus;

	}

	public String getLanguage() {

		return language;

	}

	public void setLanguage(String language) {

		this.language = language;

	}

	public Integer getDurationMinutes() {

		return durationMinutes;

	}

	public void setDurationMinutes(Integer durationMinutes) {

		this.durationMinutes = durationMinutes;

	}

	public String getCertificate() {

		return certificate;

	}

	public void setCertificate(String certificate) {

		this.certificate = certificate;

	}

	public String getPosterUrl() {

		return posterUrl;

	}

	public void setPosterUrl(String posterUrl) {

		this.posterUrl = posterUrl;

	}

	public String getGenre() {

		return genre;

	}

	public void setGenre(String genre) {

		this.genre = genre;

	}

	public String getMallAddress() {

		return mallAddress;

	}

	public void setMallAddress(String mallAddress) {

		this.mallAddress = mallAddress;

	}

	public String getCity() {

		return city;

	}

	public void setCity(String city) {

		this.city = city;

	}

	public String getState() {

		return state;

	}

	public void setState(String state) {

		this.state = state;

	}

	public Integer getPincode() {

		return pincode;

	}

	public void setPincode(Integer pincode) {

		this.pincode = pincode;

	}

	public Time getEndTime() {

		return endTime;

	}

	public void setEndTime(Time endTime) {

		this.endTime = endTime;

	}

	public String getCustomerName() {

		return customerName;

	}

	public void setCustomerName(String customerName) {

		this.customerName = customerName;

	}

	public String getCustomerEmail() {

		return customerEmail;

	}

	public void setCustomerEmail(String customerEmail) {

		this.customerEmail = customerEmail;

	}

	public String getCustomerPhone() {

		return customerPhone;

	}

	public void setCustomerPhone(String customerPhone) {

		this.customerPhone = customerPhone;

	}

	public String getBookingStatus() {

		return bookingStatus;

	}

	public void setBookingStatus(String bookingStatus) {

		this.bookingStatus = bookingStatus;

	}

	public Timestamp getBookingDate() {

		return bookingDate;

	}

	public void setBookingDate(Timestamp bookingDate) {

		this.bookingDate = bookingDate;

	}

	public Timestamp getPaidAt() {

		return paidAt;

	}

	public void setPaidAt(Timestamp paidAt) {

		this.paidAt = paidAt;

	}

}