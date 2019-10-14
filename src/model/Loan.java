package model;

import java.util.Date;

public class Loan {

	private String loanId; 
	private String customerId; 
	private String loanAmount;
	private float interestRate; 
	private int tenure; //in months
	private boolean isActive; 
	private boolean isApproved; 
	private Date loanStartDate;
	
	public Loan(String loanId, String customerId, String loanAmount, float interestRate, int tenure, boolean isActive,
			boolean isApproved, Date loanStartDate) {
		super();
		this.loanId = loanId;
		this.customerId = customerId;
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.tenure = tenure;
		this.isActive = isActive;
		this.isApproved = isApproved;
		this.loanStartDate = loanStartDate;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public float getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(float interestRate) {
		this.interestRate = interestRate;
	}

	public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", customerId=" + customerId + ", loanAmount=" + loanAmount
				+ ", interestRate=" + interestRate + ", tenure=" + tenure + ", isActive=" + isActive + ", isApproved="
				+ isApproved + ", loanStartDate=" + loanStartDate + "]";
	} 
	
}
