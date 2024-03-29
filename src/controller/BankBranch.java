package controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Date;
import java.util.HashMap;

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
		this.managers.addAll(Read.getAllManagers());
		this.transactions.addAll(Read.getTransactions("manager"));
		this.loans.addAll(Read.getLoans("manager"));
		this.allStocks.addAll(Read.getBankStocks());
		HashMap<String, Double> feeMap = Read.getAllFees();
		this.loanInterestRate = feeMap.get("loanInterestRate");
		this.accountOperationFee = feeMap.get("accountOperationFee");
		this.highBalance = feeMap.get("highBalance");
		this.moneyEarned = feeMap.get("moneyEarned");
		this.savingsInterestRate = feeMap.get("savingsInterestRate");
		this.stockFee = feeMap.get("stockFee");
		this.transactionFee = feeMap.get("transactionFee");
		this.withdrawalFee = feeMap.get("withdrawalFee");
	}

	public BankManager addManager(String name, String id, String email, String securityCode, String password) {
		BankManager newManager = new BankManager(name, id, email, securityCode, password);
		managers.add(newManager);
		Create.insertNewManager(newManager);
		return newManager;
	}

	public Transaction addTransaction(String fromCustomer, String toCustomer, String type, double amount,
			String fromAccount, String toAccount) {
		Transaction newTransaction = new Transaction(fromCustomer, toCustomer, type, amount, fromAccount, toAccount);
		transactions.add(newTransaction);
		setChanged();
		notifyObservers();
		return newTransaction;
	}

	public BankCustomer addCustomer(String name, Address address, String phoneNumber, String ssn, String email,
			String password) {

		int customerId = BankCustomer.generateCustomerId(customers);
		BankCustomer newCustomer = new BankCustomer(name, Integer.toString(customerId), address, phoneNumber, ssn,
				email, password);
		Create.insertNewCustomer(newCustomer);
		this.customers.add(newCustomer);
		return newCustomer;
	}

	public void addAccount(BankCustomer customer, String accountName, String accountType) {
		BankAccount newAccount = new BankAccount(accountName, accountType, loanInterestRate, withdrawalFee,
				transactionFee, accountOperationFee, tradeThreshold, stockFee);
		this.getCustomerByEmail(customer.getEmail()).addAccount(newAccount);
		Create.insertNewAccount(newAccount, customer.getCustomerId());
		setChanged();
		notifyObservers();
	}

	public void addLoan(BankCustomer customer, double loanAmount, double interestRate, int tenure, String collateral,
			double collateralAmount) {
		Date date = new Date();
		Loan loan = new Loan(customer.getName(), customer.getCustomerId(),
				Integer.toString(BankCustomer.generateLoanId(customer.getLoans())), loanAmount, interestRate, tenure,
				collateral, collateralAmount);
		this.loans.add(loan);
		this.getCustomerByEmail(customer.getEmail()).addLoan(loan);
		Create.insertNewLoan(loan);
		setChanged();
		notifyObservers();
	}

	public void addStock(BankCustomer customer, String accountName, int stockID, String stockName, double value,
			int numStocks, double balance) {
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		acc.setBalance(balance);
		customer.modifyAccountBalanceByAccountName(accountName, balance);
		BankStock currStock = null;
		for (int i = 0; i < allStocks.size(); i++) {
			if (allStocks.get(i).getStockName().equals(stockName)) {
				currStock = allStocks.get(i);
				currStock.setNumStocks(currStock.getNumStocks() - numStocks);
			}
		}
		int i = 0;
		boolean flag = false;
		for (CustomerStock s : acc.getStocks()) {
			if (s.getStockName().equals(stockName)) {
				int num = s.getNumberOfStocks() + numStocks;
				acc.modifyStockByStockIndex(i, num);
				Update.updateCustomerStockNumber(stockName, customer.getCustomerId(), num);
				flag = true;
			}
			i++;
		}
		if (!flag) {
			CustomerStock newStock = new CustomerStock(Integer.toString(stockID), stockName, value, value, numStocks,
					acc.getAccountName());
			acc.addStock(newStock);

			// add stock to db
			Create.insertNewCustomerStock(newStock, customer.getCustomerId(), accountName);

		}

		// update bank stock in db
		Update.updateBankStocksForBuyOrSell(stockName, currStock.getNumStocks(), currStock.getValue());

		setChanged();
		notifyObservers();
	}

	public void sellStock(BankCustomer customer, String accountName, CustomerStock stock, int number) {
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		acc.sellStock(stock, number);
		//customer.getAccounts().get(customer.getAccountIndexByName(accountName)).sellStock(stock, number);
		int num = -1;
		for (int i = 0; i < allStocks.size(); i++) {
			num = allStocks.get(i).getNumStocks() + number;
			if (allStocks.get(i).getStockName().equals(stock.getStockName())) {
				modifyAllStocks(i, Double.parseDouble(stock.getCurrentValue()), num);
			}
		}
		double amount = Double.parseDouble(stock.getCurrentValue()) * number;
		acc.setBalance(acc.getBalance() + amount - stockFee);
//		this.depositForCustomer(customer, accountName, acc.getBalance() + amount - stockFee);
		// remove or update customerStock in db
		CustomerStock s = acc.getStocks().get(acc.getStockIndexByName(stock.getStockName()));
		if(s.getNumberOfStocks()==0) {
			allStocks.remove(getStockIndex(stock.getStockName()));
			Delete.removeStockForCustomer(stock.getStockID(), customer.getCustomerId(), accountName);
		}
		else {
			Update.updateCustomerStockNumber(stock.getStockName(), customer.getCustomerId(), s.getNumberOfStocks());
		}
		
		setChanged();
		notifyObservers();
	}
	
	public int getStockIndex(String stockName) {
		for(int i=0;i<allStocks.size();i++) {
			if(stockName.equals(allStocks.get(i).getStockName()))
					return i;
		}
		return -1;
	}

	public void addAllStocks(String stockName, double value, int numStocks) {
		BankStock bankStock = new BankStock(stockName, value, numStocks);
		this.allStocks.add(bankStock);
		// insert to db
		Create.insertNewBankStock(bankStock);
		setChanged();
		notifyObservers();
	}

	public void modifyAllStocks(int stockIndex, double value, int numStocks) {
		BankStock stock = this.allStocks.get(stockIndex);
//		stock.setValue(value);
//		stock.setNumStocks(numStocks);
		boolean flag=false;
		if(this.allStocks.get(stockIndex).getValue()!=value)
			flag=true;
		this.allStocks.get(stockIndex).setValue(value);
		this.allStocks.get(stockIndex).setNumStocks(numStocks);

		for (BankCustomer c : customers) {
			ArrayList<BankAccount> accounts = c.getAccounts();
			for (BankAccount acc : accounts) {
				acc.modifyStock(stock);
			}
		}
		if(flag) {
			Update.updateCustomerStockValue(stock.getStockName(), value);
		}

		// update bankStock in db
		if (numStocks != -1)
			Update.updateBankStocksForBuyOrSell(stock.getStockName(), numStocks, value);

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
		acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		double balance = acc.getBalance();
		Transaction tr = addTransaction(customer.getName(), "Bank", "Transaction Fees - deposit", transactionFee,
				accountName, "My Fancy Bank");
		this.addMoneyEarned(transactionFee);
		this.addTransactionForCustomer(customer, tr);
		if (t) {
			Transaction transaction = this.addTransaction(customer.getName(), "Bank",
					"Transaction fees - Account Opening", accountOperationFee, accountName, "My Fancy Bank");

			this.addMoneyEarned(accountOperationFee);

			customer.getAccounts().get(customer.getAccountIndexByName(accountName)).setNewAccount(false);
			this.addTransactionForCustomer(customer, transaction);
		}
		// update account of customer in db for balance
		Update.updateDepositOrWithdrawal(customer.getCustomerId(), accountName, balance);

		return flag;
	}

	public boolean withdrawForCustomer(BankCustomer customer, String accountName, double amount) {
		boolean flag = this.getCustomerByEmail(customer.getEmail()).withdrawFromAccount(accountName, amount);
		BankAccount acc = customer.getAccounts().get(customer.getAccountIndexByName(accountName));
		if (acc.getType().equals("checking")) {
			Transaction tr = addTransaction(customer.getName(), "Bank", "Transaction Fees - withdrawal", transactionFee,
					accountName, "My Fancy Bank");
			this.addMoneyEarned(transactionFee);
			this.addTransactionForCustomer(customer, tr);
		}

		// update account of customer in db for balance
		Update.updateDepositOrWithdrawal(customer.getCustomerId(), accountName, acc.getBalance());
		return flag;
	}

	public boolean transferBetweenAccountsForCustomer(BankCustomer customer, String fromAccountName,
			String toAccountName, double amount) {
		boolean flag = this.getCustomerByEmail(customer.getEmail()).transferBetweenAccounts(fromAccountName,
				toAccountName, amount);
		BankAccount withdrawalAcc = customer.getAccounts().get(customer.getAccountIndexByName(fromAccountName));
		BankAccount depositAcc = customer.getAccounts().get(customer.getAccountIndexByName(toAccountName));

		Update.updateDepositOrWithdrawal(customer.getCustomerId(), fromAccountName, withdrawalAcc.getBalance());
		Update.updateDepositOrWithdrawal(customer.getCustomerId(), toAccountName, depositAcc.getBalance());

		return flag;
	}

	public void addTransactionForCustomer(BankCustomer customer, Transaction transaction) {
		this.getCustomerByEmail(customer.getEmail()).addTransaction(transaction);
		Create.insertNewTransaction(transaction, customer.getCustomerId());
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

		// remove account from db
		Delete.removeAccountForCustomer(accountName, customer.getCustomerId());

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

		// update loan in db
		Update.updateLoanForSettle(loanId, customer.getCustomerId());
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
						// update balance for customer account in db
						double modifiedBalance = acc.getBalance();
						Update.updateDepositOrWithdrawal(customer.getCustomerId(), acc.getAccountName(),
								modifiedBalance);
					}
				}
			}
		}
	}

	public void modifySavingsInterestRate(double newInterestRate) {
		this.setSavingsInterestRate(newInterestRate);
		Update.updateFees("savingsInterestRate", newInterestRate);
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
		Update.updateFeeForCustomer("savingsInterestRate", newInterestRate, "");
	}

	public void modifyLoanInterestRate(double newInterestRate) {
		this.setLoanInterestRate(newInterestRate);
		Update.updateFees("loanInterestRate", newInterestRate);
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
		Update.updateLoanInterestRateForCustomer("", newInterestRate);

	}

	public void modifyFees(String type, double newFees) {
		String name = "";
		if (type.equals("Account Operation")) {
			this.setAccountOperationFee(newFees);
			name = "accountOperationFee";
		} else if (type.equals("Checking")) {
			name = "transactionFee";
			this.setCheckingTransactionFee(newFees);
		} else if (type.equals("Withdrawal")) {
			name = "withdrawalFee";
			this.setWithdrawalFee(newFees);
		} else if (type.equals("BuyStock")) {
			name = "stockFee";
			this.setStockFee(newFees);
		}

		// update fees in db
		Update.updateFees(name, newFees);

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
				} else if (type.equals("BuyStock")) {
					name = "tradingFee";
					acc.setTradingFee(newFees);
				}
			}
		}
		Update.updateFeeForCustomer(name, newFees, "");
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
		Update.updateFees("moneyEarned", this.moneyEarned);
	}

	public void approveLoanForCustomer(BankCustomer customer, String loanId) {
		this.getCustomerByEmail(customer.getEmail()).approveLoan(loanId);
		this.approveLoan(loanId);
		// update loan table in db
		Update.updateLoanForApproval(loanId, customer.getCustomerId());

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
		Update.updateFees("highBalance", highBalance);
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
