package model;

public class Transaction {

	private String fromCustomer;
	private String toCustomer;
	private String type;
	private double amount;
	private String fromAccount;
	private String toAccount;

	public Transaction(String fromCustomer, String toCustomer, String type, double amount, String fromAccount,
			String toAccount) {
		super();
		this.fromCustomer = fromCustomer;
		this.toCustomer = toCustomer;
		this.type = type;
		this.amount = amount;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}

	public String shortCustomerDisplay() {
		return this.toCustomer + " " + this.fromAccount + " " + this.toAccount + " " + this.type + " "
				+ this.amount;
	}

	public String detailedCustomerDisplay() {
		return "Transaction Detail: " + this.type + " of $" + this.amount + " between " + this.fromCustomer
				+ "'s " + this.fromAccount + " account to" + this.toCustomer + "'s " + this.toAccount + " account\n";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(String fromAccount) {
		this.fromAccount = fromAccount;
	}

	public String getToAccount() {
		return toAccount;
	}

	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", amount=" + amount + ", fromAccount=" + fromAccount + ", fromAccount="
				+ fromAccount + "]";
	}

}
