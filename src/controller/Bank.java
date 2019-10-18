package controller;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;

import model.*;

public class Bank extends Observable {

	private ArrayList<BankManager> managers;
	private ArrayList<BankCustomer> customers;
	private ArrayList<Transaction> transactions;
	private ArrayList<Loan> loans;
	private double moneyEarned;
	private double accountOperationFee;
	private double checkingTransactionFee;
	private double withdrawalFee;
	private double loanInterestRate;

	public Bank() {
		super();
		managers = new ArrayList<BankManager>();
		customers = new ArrayList<BankCustomer>();
		transactions = new ArrayList<Transaction>();
		loans = new ArrayList<Loan>();
		this.loanInterestRate = 0.1;
		this.moneyEarned = 0;
		this.accountOperationFee = 5;
		this.checkingTransactionFee = 2;
		this.withdrawalFee = 2;
	}

	public BankManager addManager(String name, String id, String email, String securityCode, String password) {
		BankManager newManager = new BankManager(name, id, email, securityCode, password);
		managers.add(newManager);
		return newManager;
	}

	public Transaction addTransaction(String fromCustomer, String toCustomer, String type, double amount,
			String fromAccount, String toAccount) {
		Transaction newTransaction = new Transaction(fromCustomer, toCustomer, type, amount, fromAccount, toAccount);
		transactions.add(newTransaction);
		return newTransaction;
	}

	public BankCustomer addCustomer(String name, Address address, String phoneNumber, String ssn, String email,
			String password) {

		int customerId = BankCustomer.generateCustomerId(customers);
		BankCustomer newCustomer = new BankCustomer(name, Integer.toString(customerId), address, phoneNumber, ssn,
				email, password);
		this.customers.add(newCustomer);
		return newCustomer;
	}

	public void addAccount(BankCustomer customer, String accountName, String accountType) {
		this.getCustomerByEmail(customer.getEmail()).addAccount(new BankAccount(accountName, accountType,
				loanInterestRate, withdrawalFee, checkingTransactionFee, accountOperationFee));
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
		double fees = acc.getFees("Deposit");
		if (t) {
			Transaction transaction = this.addTransaction(customer.getName(), "Bank", "Transaction fees - Account Opening",
					fees + accountOperationFee, accountName, "My Fancy Bank");
			this.addTransactionForCustomer(customer, transaction);
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
		setChanged();
		notifyObservers();
		return true;
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

	public double getCheckingTransactionFee() {
		return checkingTransactionFee;
	}

	public void setCheckingTransactionFee(double checkingTransactionFee) {
		this.checkingTransactionFee = checkingTransactionFee;
	}

	public double getWithdrawalFee() {
		return withdrawalFee;
	}

	public void setWithdrawalFee(double withdrawalFee) {
		this.withdrawalFee = withdrawalFee;
	}

}
