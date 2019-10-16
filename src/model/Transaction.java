package model;

public class Transaction {

	private String type;
	private String amount;
	private String fromAccount;
	private String toAccount;

	public Transaction(String type, String amount, String fromAccount, String toAccount) {
		super();
		this.type = type;
		this.amount = amount;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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
