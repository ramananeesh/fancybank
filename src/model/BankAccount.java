package model;

import java.util.ArrayList;

public class BankAccount {

	private String accountName;
	private String type;
	private double balance;
	private double rate;
	private double withdrawalFee;
	private double accountOperationFee;
	private double transactionFee;
	private boolean isNewAccount;
	private ArrayList<CustomerStock> stocks;
	private boolean isTradable; 
	private double tradeThreshold; 
	private double tradingFee; 
	
	public BankAccount(String name, String type, double rate, double withdrawalFee, double transactionFee,
			double accountOperationFee, double tradeThreshold, double tradingFee) {
		super();
		this.accountName = name;
		this.type = type;
		this.rate = rate;
		this.balance = 0;
		this.withdrawalFee = withdrawalFee;
		this.transactionFee = transactionFee;
		this.accountOperationFee = accountOperationFee;
		this.isNewAccount = true;
		this.isTradable = false;
		this.tradeThreshold = tradeThreshold;
		this.tradingFee = tradingFee;
		this.stocks = new ArrayList<CustomerStock>();
	}

	public BankAccount(String name, String type, double balance, double rate, double withdrawalFee, double transactionFee,
			double accountOperationFee, boolean isNewAccount, double tradeThreshold, double tradingFee) {
		super();
		this.accountName = name;
		this.type = type;
		this.rate = rate;
		this.balance = balance;
		this.withdrawalFee = withdrawalFee;
		this.transactionFee = transactionFee;
		this.accountOperationFee = accountOperationFee;
		this.isNewAccount = isNewAccount; 
		this.isTradable = determineTradability();
		this.tradeThreshold = tradeThreshold;
		this.tradingFee = tradingFee;
		this.stocks = new ArrayList<CustomerStock>();
	}

	public BankAccount(String name, String type, double balance, double rate, double withdrawalFee, double transactionFee,
			double accountOperationFee, boolean isNewAccount, double tradeThreshold, double tradingFee, ArrayList<CustomerStock> stocks) {
		super();
		this.accountName = name;
		this.type = type;
		this.rate = rate;
		this.balance = balance;
		this.withdrawalFee = withdrawalFee;
		this.transactionFee = transactionFee;
		this.accountOperationFee = accountOperationFee;
		this.isNewAccount = isNewAccount; 
		this.isTradable = determineTradability();
		this.tradeThreshold = tradeThreshold;
		this.tradingFee = tradingFee;
		this.stocks = stocks; 
	}

	public boolean determineTradability() {
		return this.balance > this.tradeThreshold;
	}
	public BankAccount(String name, String type, double balance, double rate, double fee) {
		super();
		this.accountName = name;
		this.type = type;
		this.balance = balance;
		this.rate = rate;
		this.withdrawalFee = fee;
	}

	public double getAccountOperationFee() {
		return accountOperationFee;
	}

	public void setAccountOperationFee(double accountOperationFee) {
		this.accountOperationFee = accountOperationFee;
	}

	public boolean isTradable() {
		return isTradable;
	}

	public void setTradable(boolean isTradable) {
		this.isTradable = isTradable;
	}

	public ArrayList<CustomerStock> getStocks() {
		return stocks;
	}

	public double getTransactionFee() {
		return transactionFee;
	}

	public void setTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public boolean isNewAccount() {
		return isNewAccount;
	}

	public void setNewAccount(boolean isNewAccount) {
		this.isNewAccount = isNewAccount;
	}

	public boolean deposit(double deposit) {
		if (deposit <= 0)
			return false;
		double fees = getFees("Deposit");
		if ((balance + deposit - fees < 0))
			return false;

		this.balance += (deposit - fees);

		return true;
	}

	public double getFees(String transactionType) {
		double fees = 0;
		if (isNewAccount) {
			fees += accountOperationFee;
		}
		if (type.equals("Checking"))
			fees += transactionFee;

		if (transactionType.equals("Withdrawal")) {
			if (type.equals("Savings"))
				fees += withdrawalFee;
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
		if (amount <= 0 || amount > this.balance) {
			return false;
		}

		if (balance - (amount + getFees("Withdrawal")) < 0)
			return false;

		this.balance -= (amount + getFees("Withdrawal"));

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
		return new String[] { this.accountName, this.type };
	}

	@Override
	public String toString() {
		return "Account [type=" + type + ", balance=" + balance + ", rate=" + rate + ", fee=" + withdrawalFee + "]";
	}

	public String toString(int args) {
		return type + "\t" + balance + "\t" + rate + "\t" + withdrawalFee + "\t";
	}

	public String toString(double args) {
		return "\t\tAccount Type: " + type + "\n\t\tBalance: " + balance + "\n";
	}
	
	public void addStock(CustomerStock newStock) {
		this.stocks.add(newStock);
		this.isTradable = determineTradability();
	}

	public void sellStock(CustomerStock stock, int num) {
		int existing = this.stocks.get(getStockIndexByName(stock.getStockName())).getNumberOfStocks();
		this.stocks.get(getStockIndexByName(stock.getStockName())).setNumStocks(existing-num);
	}
	
	public int getStockIndexByName(String name) {
		int i=0;
		for(CustomerStock s: stocks) {
			if(s.getStockName().equals(name)) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	public void modifyStock(BankStock newStock) {
		for (CustomerStock s : this.stocks) {
			if (s.getStockName().equals(newStock.getStockName())) {
				s.setCurrentValue(newStock.getValue());
			}
		}
	}

	public void setStocks(ArrayList<CustomerStock> stocks) {
		this.stocks = stocks;
	}

	public double getTradeThreshold() {
		return tradeThreshold;
	}

	public void setTradeThreshold(double tradeThreshold) {
		this.tradeThreshold = tradeThreshold;
	}

	public double getTradingFee() {
		return tradingFee;
	}

	public void setTradingFee(double tradingFee) {
		this.tradingFee = tradingFee;
	}
	
	public void modifyStockByStockIndex(int index, int value) {
		this.stocks.get(index).setNumStocks(value);
	}
}
