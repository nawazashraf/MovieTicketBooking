package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.movieticket.model.PaymentBean;
import com.movieticket.util.DBConnection;

public class PaymentDAO {
	public boolean createPayment(PaymentBean payment) {
		String sql = """
				INSERT INTO payments
				(id, booking_id, amount, payment_method, transaction_id, payment_status)
				VALUES (?, ?, ?, ?, ?, ?)
				""";

		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, payment.getId());
			ps.setString(2, payment.getBookingId());
			ps.setBigDecimal(3, payment.getAmount());
			ps.setString(4, payment.getPaymentMethod());
			ps.setString(5, payment.getTransactionId());
			ps.setString(6, payment.getPaymentStatus());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
