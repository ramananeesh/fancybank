package model;

public class Account {

	private String type; 
	private double balance; 
	private double rate; 
	private double fee;
	
	public Account(String type, double balance, double rate, double fee) {
		super();
		this.type = type;
		this.balance = balance;
		this.rate = rate;
		this.fee = fee;
	}
	
	public boolean deposit(double deposit) {
		if(deposit<=0)
			return false;
		this.balance+=deposit;
		
		return true;
	}
	
	public boolean withdraw(double amount) {
		if(amount<=0 || amount>this.balance) {
			return false;
		}
		
		this.balance-=amount;
		
		return true;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	@Override
	public String toString() {
		return "Account [type=" + type + ", balance=" + balance + ", rate=" + rate + ", fee=" + fee + "]";
	} 
	
}
