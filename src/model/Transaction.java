package model;

public class Transaction {

	private String type;
	private String amount; 
	private String accountName;
	
	public Transaction(String type, String amount, String accountName) {
		super();
		this.type = type;
		this.amount = amount;
		this.accountName = accountName;
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

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Override
	public String toString() {
		return "Transaction [type=" + type + ", amount=" + amount + ", accountName=" + accountName + "]";
	} 
	
}
