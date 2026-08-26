package com.movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

	public PaymentBean getPaymentByBookingId(String bookingId) {
		String sql = "SELECT * FROM payments WHERE booking_id = ?";

		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, bookingId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				PaymentBean payment = new PaymentBean();

				payment.setId(rs.getString("id"));
				payment.setBookingId(rs.getString("booking_id"));
				payment.setAmount(rs.getBigDecimal("amount"));
				payment.setPaymentMethod(rs.getString("payment_method"));
				payment.setTransactionId(rs.getString("transaction_id"));
				payment.setPaymentStatus(rs.getString("payment_status"));
				payment.setPaidAt(rs.getTimestamp("paid_at"));
				payment.setCreatedAt(rs.getTimestamp("created_at"));

				return payment;

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean markPaymentSuccess(String bookingId, String paymentMethod, String transactionId) {
		String sql = """
				UPDATE payments
				SET payment_status = 'SUCCESS',
					payment_method = ?,
					transaction_id = ?,
					paid_at = CURRENT_TIMESTAMP
				WHERE booking_id = ?

				""";

		try {
			Connection conn = DBConnection.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, paymentMethod);
			ps.setString(2, transactionId);
			ps.setString(3, bookingId);

			return ps.executeUpdate() > 0;
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}
}
