package com.movieticket.model;

public class EmailVerificationBean {

	private String email;
	private String otp;
	private long generatedAt;
	private boolean verified;

	public EmailVerificationBean() {
	}

	public EmailVerificationBean(String email, String otp, long generatedAt) {
		this.email = email;
		this.otp = otp;
		this.generatedAt = generatedAt;
		this.verified = false;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public long getGeneratedAt() {
		return generatedAt;
	}

	public void setGeneratedAt(long generatedAt) {
		this.generatedAt = generatedAt;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}
}