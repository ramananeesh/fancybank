package controller;

import java.util.ArrayList;
import java.util.Observable;
import DB.*;

import javax.swing.DefaultListModel;

import model.*;

public class BankBranch extends Observable {

	private ArrayList<BankManager> managers;
	private ArrayList<BankCustomer> customers;
	private ArrayList<Transaction> transactions;
	private ArrayList<Loan> loans;
	private ArrayList<BankStock> allStocks;
	private double moneyEarned;
	private double accountOperationFee;
	private double transactionFee;
	private double withdrawalFee;
	private double loanInterestRate;
	private double highBalance;
	private double savingsInterestRate;
	private double stockFee;
	private ArrayList<Currency> currencies;
	private double tradeThreshold;
	private int stockID;

	public BankBranch() {
		super();
		managers = new ArrayList<BankManager>();
		customers = new ArrayList<BankCustomer>();
		transactions = new ArrayList<Transaction>();
		loans = new ArrayList<Loan>();
		allStocks = new ArrayList<BankStock>();
		this.loanInterestRate = 0.1;
		this.moneyEarned = 0;
		this.accountOperationFee = 5;
		this.transactionFee = 2;
		this.withdrawalFee = 2;
		this.highBalance = 100;
		this.savingsInterestRate = 0.02;
		this.stockFee = 2;
		this.stockID = 0;

		this.currencies = new ArrayList<Currency>();
		currencies.add(new Currency("US Dollars", "USD", 1, 1));
		currencies.add(new Currency("Indian Rupees", "INR", 0.014, 72.14));
		currencies.add(new Currency("British Pounds", "GBP", 1.30, 0.77));
		currencies.add(new Currency("Euros", "EUR", 1.12, 0.9));

		initialize();
	}

	public void initialize() {
		this.customers.addAll(Read.getAllCustomers());
		this.transactions.addAll(Read.getTransactions(""));
		this.loans.addAll(Read.getLoans(""));
		this.allStocks.addAll(Read.getBankStocks());
//		for(BankCustomer c: this.customers) {
//			this.transactions.addAll(Read.getTransactions(c.getCustomerId()));
//			this.loans.addAll(Read.getLoans(c.getCustomerId()));
//		}
	}

	public BankManager addManager(String name, String id, String email, String securityCode, String password) {
		BankManager newManager = new BankManager(name, id, email, securityCode, password);
		managers.add(newManager);
		return newManager;
	}

	public Transaction addTransaction(String fromCustomer, String toCustomer, String type, double amount,
			String fromAccount, String toAccount) {
		Transaction newTransaction = new Transaction(fromCustomer, toCustomer, type, amount, fromAccount, toAccount);
		Insert.insertNewTransaction(newTransaction, customers.get(getCustomerIndex(fromCustomer)).getCustomerId());
		transactions.add(newTransaction);
		return newTransaction;
	}

	public BankCustomer addCustomer(String name, Address address, String phoneNumber, String ssn, String email,
			String password) {

		int customerId = BankCustomer.generateCustomerId(customers);
		BankCustomer newCustomer = new BankCustomer(name, Integer.toString(customerId), address, phoneNumber, ssn,
				email, password);
		Insert.insertNewCustomer(newCustomer);
		this.customers.add(newCustomer);
		return newCustomer;
	}

	public void addAccount(BankCustomer customer, String accountName, String accountType) {
		BankAccount newAccount = new BankAccount(accountName, accountType, loanInterestRate, withdrawalFee,
				transactionFee, accountOperationFee, tradeThreshold, stockFee);
		this.getCustomerByEmail(customer.getEmail()).addAccount(newAccount);
		Insert.insertNewAccount(newAccount);
		setChanged();
		notifyObservers();
	}

	public void addLoan(BankCustomer customer, double loanAmount, double interestRate, int tenure, String collateral,
			double collateralAmount) {
		Loan loan = new Loan(customer.getName(), customer.getCustomerId(),
				Integer.toString(BankCustomer.generateLoanId(customer.getLoans())), loanAmount, interestRate, tenure,
				collateral, collateralAmount);
		this.loans.add(loan);
		this.getCustomerByEmail(customer.getEmail()).addLoan(loan);
		Insert.insertNewLoan(loan);
		setChanged();
		notifyObservers();
	}

	public void addStock(BankCustomer customer, String accountName, int stockID, String stockName, double value,
			int numStocks, double balance) {
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		acc.setBalance(balance);
		BankStock currStock = null;
		for (int i = 0; i < allStocks.size(); i++) {
			if (allStocks.get(i).getStockName().equals(stockName)) {
				currStock = allStocks.get(i);
				currStock.setNumStocks(currStock.getNumStocks() - numStocks);
			}
		}

		CustomerStock newStock = new CustomerStock(Integer.toString(stockID), stockName, value, value, numStocks,
				acc.getAccountName());
		acc.addStock(newStock);
		
		//add stock to db
		Insert.insertNewCustomerStock(newStock, customer.getCustomerId());
		
		//update bank stock in db
		setChanged();
		notifyObservers();
	}

	public void sellStock(BankCustomer customer, String accountName, CustomerStock stock) {
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		acc.sellStock(stock);
		for (int i = 0; i < allStocks.size(); i++) {
			if (allStocks.get(i).getStockName().equals(stock.getStockName())) {
				modifyAllStocks(i, Double.parseDouble(stock.getCurrentValue()),
						allStocks.get(i).getNumStocks() + Integer.parseInt(stock.getNumStocks()));
			}
		}
		double amount = Double.parseDouble(stock.getCurrentValue()) * Integer.parseInt(stock.getNumStocks());
		acc.setBalance(acc.getBalance() + amount - stockFee);
		setChanged();
		notifyObservers();
	}

	public void addAllStocks(String stockName, double value, int numStocks) {
		BankStock bankStock = new BankStock(stockName, value, numStocks);
		this.allStocks.add(bankStock);
		setChanged();
		notifyObservers();
	}

	public void modifyAllStocks(int stockIndex, double value, int numStocks) {
		BankStock stock = this.allStocks.get(stockIndex);
		stock.setValue(value);
		stock.setNumStocks(numStocks);

		for (BankCustomer c : customers) {
			ArrayList<BankAccount> accounts = c.getAccounts();
			for (BankAccount acc : accounts) {
				acc.modifyStock(stock);
			}
		}
		setChanged();
		notifyObservers();
	}

	public int getCustomerIndex(String name) {
		int i = 0;
		for (BankCustomer cust : this.customers) {
			if (cust.getName().equals(name))
				return i;
			i++;
		}
		return -1;
	}

	public BankCustomer getCustomerByEmail(String email) {

		for (BankCustomer c : this.customers) {
			if (c.getEmail().equals(email))
				return c;
		}

		return null;
	}

	public BankManager getManagerByEmail(String email) {

		for (BankManager m : this.managers) {
			if (m.getEmail().equals(email))
				return m;
		}

		return null;
	}

	public boolean depositForCustomer(BankCustomer customer, String accountName, double amount) {
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		boolean t = acc.isNewAccount();

		boolean flag = this.getCustomerByEmail(customer.getEmail()).depositIntoAccount(accountName, amount);

		if (t) {
			Transaction transaction = this.addTransaction(customer.getName(), "Bank",
					"Transaction fees - Account Opening", accountOperationFee, accountName, "My Fancy Bank");
			this.addTransactionForCustomer(customer, transaction);
			this.addMoneyEarned(accountOperationFee);
			customer.getAccounts().get(customer.getAccountIndexByName(accountName)).setNewAccount(false);
		}

		return flag;
	}

	public boolean withdrawForCustomer(BankCustomer customer, String accountName, double amount) {
		boolean flag = this.getCustomerByEmail(customer.getEmail()).withdrawFromAccount(accountName, amount);
		return flag;
	}

	public boolean transferBetweenAccountsForCustomer(BankCustomer customer, String fromAccountName,
			String toAccountName, double amount) {
		return this.getCustomerByEmail(customer.getEmail()).transferBetweenAccounts(fromAccountName, toAccountName,
				amount);
	}

	public void addTransactionForCustomer(BankCustomer customer, Transaction transaction) {
		this.getCustomerByEmail(customer.getEmail()).addTransaction(transaction);
		Insert.insertNewTransaction(transaction, customer.getCustomerId());
		setChanged();
		notifyObservers();
	}

	public void closeAccountForCustomer(BankCustomer customer, String accountName) {
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		double fees = acc.getAccountOperationFee();
		this.getCustomerByEmail(customer.getEmail()).closeAccount(accountName);
		Transaction transaction = this.addTransaction(customer.getName(), "Bank", "Transaction fees - Account Closing",
				fees + accountOperationFee, accountName, "My Fancy Bank");
		this.addTransactionForCustomer(customer, transaction);
		this.addMoneyEarned(fees);
		setChanged();
		notifyObservers();
	}

	public boolean settleLoanForCustomer(BankCustomer customer, String accountName, String loanId, double loanAmount) {

		boolean flag = this.getCustomerByEmail(customer.getEmail()).withdrawFromAccount(accountName, loanAmount);
		if (!flag)
			return false;
		double fees = this.getCustomerByEmail(customer.getEmail()).getAccounts()
				.get(this.getCustomerByEmail(customer.getEmail()).getAccountIndexByName(accountName))
				.getFees("Withdrawal");
		this.getCustomerByEmail(customer.getEmail()).closeLoan(loanId);
		Transaction t = this.addTransaction(customer.getName(), "Bank", "Loan Settlement", loanAmount, accountName,
				"My Fancy Bank");
		this.addTransactionForCustomer(customer, t);
		t = this.addTransaction(customer.getName(), "Bank", "Transaction fees", fees, accountName, "My Fancy Bank");
		this.addTransactionForCustomer(customer, t);
		this.addMoneyEarned(fees);
		setChanged();
		notifyObservers();
		return true;
	}

	public void settleInterestsForAllCustomers() {
		if (customers.size() == 0)
			return;
		for (BankCustomer customer : customers) {
			ArrayList<BankAccount> accounts = customer.getAccounts();
			if (accounts.size() == 0)
				return;
			for (BankAccount acc : accounts) {
				if (acc.getType().equals("Savings")) {
					if (acc.getBalance() >= highBalance) {
						double interestAmount = this.savingsInterestRate * acc.getBalance();
						acc.setBalance(acc.getBalance() + interestAmount);
						Transaction t = this.addTransaction("Bank", customer.getName(), "Interest Settlement",
								interestAmount, "My Fancy Bank", acc.getAccountName());
						this.addTransactionForCustomer(customer, t);
					}
				}
			}
		}
	}

	public void modifySavingsInterestRate(double newInterestRate) {
		this.setSavingsInterestRate(newInterestRate);
		if (customers.size() == 0)
			return;
		for (BankCustomer customer : customers) {
			ArrayList<BankAccount> accounts = customer.getAccounts();
			if (accounts.size() == 0)
				return;
			for (BankAccount acc : accounts) {
				if (acc.getType().equals("Savings"))
					acc.setRate(newInterestRate);
			}
		}
	}

	public void modifyLoanInterestRate(double newInterestRate) {
		this.setLoanInterestRate(newInterestRate);
		if (customers.size() == 0)
			return;
		for (BankCustomer customer : customers) {
			ArrayList<Loan> loans = customer.getLoans();
			if (loans.size() == 0)
				return;
			for (Loan acc : loans) {
				acc.setInterestRate(newInterestRate);
			}
		}
	}

	public void modifyFees(String type, double newFees) {
		if (type.equals("Account Operation")) {
			this.setAccountOperationFee(newFees);
		} else if (type.equals("Checking")) {
			this.setCheckingTransactionFee(newFees);
		} else if (type.equals("Withdrawal")) {
			this.setWithdrawalFee(newFees);
		} else if (type.equals("BuyStock")) {
			this.setStockFee(newFees);
		}

		this.setSavingsInterestRate(newFees);
		if (customers.size() == 0)
			return;
		for (BankCustomer customer : customers) {
			ArrayList<BankAccount> accounts = customer.getAccounts();
			if (accounts.size() == 0)
				return;
			for (BankAccount acc : accounts) {
				if (type.equals("Account Operation")) {
					acc.setAccountOperationFee(newFees);
				} else if (type.equals("Checking")) {
					acc.setTransactionFee(newFees);
				} else if (type.equals("Withdrawal")) {
					acc.setWithdrawalFee(newFees);
				}
			}
		}
	}

	public Loan getLoanById(String loanId) {
		for (Loan l : loans) {
			if (l.getLoanId().equals(loanId))
				return l;
		}
		return null;
	}

	public Loan getLoanById(BankCustomer customer, String loanId) {
		for (Loan l : customer.getLoans()) {
			if (l.getLoanId().equals(loanId))
				return l;
		}
		return null;
	}

	public int getLoanIndexById(String loanId) {
		int i = 0;
		for (Loan l : loans) {
			if (l.getLoanId().equals(loanId))
				return i;
			i++;
		}
		return -1;
	}

	public void approveLoan(String loanId) {
		for (Loan l : this.loans) {
			if (l.getLoanId().equals(loanId)) {
				l.approve();
			}
		}
	}

	public void addMoneyEarned(double moneyEarned) {
		this.moneyEarned += moneyEarned;
	}

	public void approveLoanForCustomer(BankCustomer customer, String loanId) {
		this.getCustomerByEmail(customer.getEmail()).approveLoan(loanId);
		this.approveLoan(loanId);
		setChanged();
		notifyObservers();
	}

	public BankCustomer login(String email, String password) {
		BankCustomer customer = getCustomerByEmail(email);
		if (customer == null)
			return null;

		if (customer.getPassword().equals(password))
			return customer;

		return null;
	}

	public BankManager loginManager(String email, String password) {
		BankManager manager = getManagerByEmail(email);
		if (manager == null)
			return null;

		if (manager.getPassword().equals(password))
			return manager;

		return null;
	}

	public ArrayList<BankManager> getManagers() {
		return managers;
	}

	public void setManagers(ArrayList<BankManager> managers) {
		this.managers = managers;
	}

	public ArrayList<BankCustomer> getCustomers() {
		return customers;
	}

	public void setCustomers(ArrayList<BankCustomer> customers) {
		this.customers = customers;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

	public ArrayList<BankStock> getAllStocks() {
		return allStocks;
	}

	public void setAllStocks(ArrayList<BankStock> allStocks) {
		this.allStocks = allStocks;
	}

	public double getLoanInterestRate() {
		return loanInterestRate;
	}

	public void setLoanInterestRate(int loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public void setLoanInterestRate(double loanInterestRate) {
		this.loanInterestRate = loanInterestRate;
	}

	public ArrayList<Loan> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<Loan> loans) {
		this.loans = loans;
	}

	public double getMoneyEarned() {
		return moneyEarned;
	}

	public void setMoneyEarned(double moneyEarned) {
		this.moneyEarned = moneyEarned;
	}

	public double getAccountOperationFee() {
		return accountOperationFee;
	}

	public void setAccountOperationFee(double accountOperationFee) {
		this.accountOperationFee = accountOperationFee;
	}

	public double getStockFee() {
		return stockFee;
	}

	public void setStockFee(double stockFee) {
		this.stockFee = stockFee;
	}

	public double getCheckingTransactionFee() {
		return transactionFee;
	}

	public void setCheckingTransactionFee(double transactionFee) {
		this.transactionFee = transactionFee;
	}

	public double getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(double withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

	public double getHighBalance() {
		return highBalance;
	}

	public void setHighBalance(double highBalance) {
		this.highBalance = highBalance;
	}

	public double getSavingsInterestRate() {
		return savingsInterestRate;
	}

	public void setSavingsInterestRate(double savingsInterestRate) {
		this.savingsInterestRate = savingsInterestRate;
	}

	public ArrayList<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(ArrayList<Currency> currencies) {
		this.currencies = currencies;
	}

	public double getTradeThreshold() {
		return tradeThreshold;
	}

	public void setTradeThreshold(double tradeThreshold) {
		this.tradeThreshold = tradeThreshold;
	}

	public int getStockID() {
		return this.stockID;
	}

	public void setStockID(int id) {
		this.stockID = id;
	}

}
