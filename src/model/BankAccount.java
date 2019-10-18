package model;

public class BankAccount {

	private String accountName;
	private String type; 
	private double balance; 
	private double rate; 
	private double withdrawalFee;
	private double accountOperationFee; 
	private double transactionFee;
	
	public BankAccount(String name, String type, double rate, double withdrawalFee, double transactionFee, double accountOperationFee) {
		super();
		this.accountName = name;
		this.type = type;
		this.rate = rate;
		this.balance = 0;
		this.withdrawalFee = withdrawalFee;
		this.transactionFee=transactionFee;
		this.accountOperationFee = accountOperationFee;
	}
	
	public BankAccount(String name,String type, double balance, double rate, double fee) {
		super();
		this.accountName = name;
		this.type = type;
		this.balance = balance;
		this.rate = rate;
		this.withdrawalFee = fee;
	}
	
	public boolean deposit(double deposit) {
		if(deposit<=0)
			return false;
		
		if((balance+deposit-getFees("Deposit")< 0))
			return false;
		
		this.balance+=(deposit-getFees("Deposit"));
		
		return true;
	}
	
	public double getFees(String transactionType) {
		double fees=0;
		if(transactionType.equals("Deposit") && this.balance==0)
			fees+=accountOperationFee;
		
		if(type.equals("Checking"))
			fees+=transactionFee;
		
		if(transactionType.equals("Withdrawal")) {
			if(type.equals("Savings"))
				fees+=withdrawalFee;
		}
		
		return fees;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public boolean withdraw(double amount) {
		if(amount<=0 || amount>this.balance) {
			return false;
		}
		
		if(balance-(amount+getFees("Withdrawal"))<0)
			return false;
		
		this.balance-=(amount+getFees("Withdrawal"));
		
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

	public double getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(double fee) {
		this.withdrawalFee = fee;
	}
	
	public String[] getDetails() {
		return new String[] {this.accountName,this.type};
	}

	@Override
	public String toString() {
		return "Account [type=" + type + ", balance=" + balance + ", rate=" + rate + ", fee=" + withdrawalFee + "]";
	} 
	
	public String toString(int args) {
		return type+"\t"+ balance +"\t"+rate+ "\t"+withdrawalFee+"\t";
	}
	
	public String toString(double args) {
		return "\t\tAccount Type: "+type+"\n\t\tBalance: "+balance+"\n";
	}
}
