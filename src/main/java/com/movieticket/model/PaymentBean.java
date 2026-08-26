package com.movieticket.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class PaymentBean {
	
	private String id;
	private String bookingId;
	private BigDecimal amount;
	private String paymentMethod;
	private String transactionId;
	private String paymentStatus;
	private Timestamp paidAt;
	private Timestamp createdAt;
	
	
	public PaymentBean() {
		
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getBookingId() {
		return bookingId;
	}


	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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


	public Timestamp getPaidAt() {
		return paidAt;
	}


	public void setPaidAt(Timestamp paidAt) {
		this.paidAt = paidAt;
	}


	public Timestamp getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
