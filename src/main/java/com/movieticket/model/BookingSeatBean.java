package com.movieticket.model;

import java.math.BigDecimal;

public class BookingSeatBean {

    private String id;
    private String bookingId;
    private String showSeatId;
    private BigDecimal price;

    public BookingSeatBean() {
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

    public String getShowSeatId() {
        return showSeatId;
    }

    public void setShowSeatId(String showSeatId) {
        this.showSeatId = showSeatId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}