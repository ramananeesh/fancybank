package model;

import java.util.Date;

public class Loan {

	private String loanId;
	private String customerId;
	private String customerName;
	private double loanAmount;
	private double interestRate;
	private int tenure; // in months
	private boolean isActive;
	private boolean isApproved;
	private Date loanStartDate;
	private String collateral;
	private double collateralAmount;
	private String loanApprovedStatus;

	public Loan(String customerName, String customerId, String loanId, double loanAmount, double interestRate,
			int tenure, String collateral, double collateralAmount) {
		super();
		this.customerName = customerName;
		this.customerId = customerId;
		this.loanId = loanId;
		this.loanAmount = loanAmount;
		this.interestRate = interestRate;
		this.tenure = tenure;
		this.collateral = collateral;
		this.collateralAmount = collateralAmount;

		this.isActive = false;
		this.isApproved = false;
		this.loanApprovedStatus = "Pending";
		this.loanStartDate = new Date();
	}

	public Loan(String loanId, String customerName, String customerId, double loanAmount, float interestRate,
			int tenure, boolean isActive, boolean isApproved, Date loanStartDate, String collateral,
			double collateralAmount) {
		super();
		this.loanId = loanId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.loanAmount = loanAmount;

		this.interestRate = interestRate;
		this.tenure = tenure;
		this.isActive = isActive;
		this.isApproved = isApproved;
		this.loanStartDate = loanStartDate;
		this.collateral = collateral;
		this.collateralAmount = collateralAmount;
		this.loanApprovedStatus = "Pending";
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCollateral() {
		return collateral;
	}

	public void setCollateral(String collateral) {
		this.collateral = collateral;
	}

	public double getCollateralAmount() {
		return collateralAmount;
	}

	public void setCollateralAmount(double collateralAmount) {
		this.collateralAmount = collateralAmount;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String[] getShortLoanDisplayForCustomer() {
		return new String[] { this.loanId, Double.toString(this.loanAmount), this.loanApprovedStatus,
				Boolean.toString(this.isActive) };
	}

	
	public String[] getShortLoanDisplayForManager() {
		return new String[] { this.loanId, this.customerName, Double.toString(this.loanAmount), this.loanApprovedStatus,
				Boolean.toString(this.isActive) };
	}

	public String getDetailedLoanDisplayForCustomer() {
		String ret = "";

		ret += "Loan ID: " + this.loanId + "\n";
		ret += "Loan Amount: $" + this.loanAmount + "\nInterest Rate: " + this.interestRate + "\n";
		ret += "Tenure: " + this.tenure + " months\t Start Date: " + this.loanStartDate + "\n";
		ret += "Collateral: " + this.collateral + "\nCollateral Amount: " + this.collateralAmount + "\n";
		ret += "Loan Approved Status: " + this.loanApprovedStatus + "\nLoan Active: " + this.isActive + "\n";
		return ret;
	}
	
	public String getDetailedLoanDisplayForManager() {
		String ret = "";

		ret += "\t\tLoan ID: " + this.loanId + "\n";
		ret += "\t\tLoan Amount: $" + this.loanAmount + "\n\t\tInterest Rate: " + this.interestRate + "\n";
		ret += "\t\tTenure: " + this.tenure + " months\t Start Date: " + this.loanStartDate + "\n";
		ret += "\t\tCollateral: " + this.collateral + "\n\t\tCollateral Amount: " + this.collateralAmount + "\n";
		ret += "\t\tLoan Approved Status: " + this.loanApprovedStatus + "\n\t\tLoan Active: " + this.isActive + "\n";
		return ret;
	}

	public String getDetailedLoanDisplayForManager(int args) {
		String ret = "";

		ret += "\t\tLoan ID: " + this.loanId + "\n\t\tCustomer Name: "+customerName+"\n";
		ret += "\t\tLoan Amount: $" + this.loanAmount + "\n\t\tInterest Rate: " + this.interestRate + "\n";
		ret += "\t\tTenure: " + this.tenure + " months\t Start Date: " + this.loanStartDate + "\n";
		ret += "\t\tCollateral: " + this.collateral + "\n\t\tCollateral Amount: " + this.collateralAmount + "\n";
		ret += "\t\tLoan Approved Status: " + this.loanApprovedStatus + "\n\t\tLoan Active: " + this.isActive + "\n";
		return ret;
	}
	public void approve() {
		this.isApproved = true;
		this.loanApprovedStatus = "Approved";
		this.isActive = true;
	}

	public double getInterestAmount() {
		return this.loanAmount * this.interestRate * this.tenure / (100.0);
	}

	public double getPayoffAmount() {
		return loanAmount + getInterestAmount();
	}

	public void close() {
		this.isActive = false;
	}

	public void reject() {
		this.isApproved = false;
		this.loanApprovedStatus = "Rejected";
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

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
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
