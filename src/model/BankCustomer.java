package model;

import java.util.ArrayList;
import java.util.Random;

public class BankCustomer extends Customer {

	private String password;
	private ArrayList<BankAccount> accounts;
	private ArrayList<Transaction> transactions;
	private ArrayList<Loan> loans;
	static Random rand = new Random();

	public BankCustomer(String name, String customerId, Address address, String phoneNumber, String ssn, String email,
			String password) {
		super(name, customerId, address, phoneNumber, ssn, email);
		this.password = password;
		this.accounts = new ArrayList<BankAccount>();
		this.transactions = new ArrayList<Transaction>();
		this.loans = new ArrayList<Loan>();
	}

	public BankCustomer(String name, String customerId, Address address, String phoneNumber, String ssn, String email,
			String password, ArrayList<BankAccount> accounts, ArrayList<Transaction> transactions) {
		super(name, customerId, address, phoneNumber, ssn, email);
		this.password = password;
		this.accounts = accounts;
		this.transactions = transactions;
	}

	public void addAccount(BankAccount newAccount) {
		this.accounts.add(newAccount);
	}

	public ArrayList<Loan> getLoans() {
		return loans;
	}

	public void setLoans(ArrayList<Loan> loans) {
		this.loans = loans;
	}

	public void addTransaction(Transaction newTransaction) {
		this.transactions.add(newTransaction);
	}
	
	public void addLoan(Loan newLoan) {
		this.loans.add(newLoan);
	}

	public BankAccount removeAccount(int accountIndex) {
		BankAccount removedAccount = this.accounts.get(accountIndex);
		if (removedAccount != null)
			this.accounts.remove(accountIndex);

		return removedAccount;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void depositIntoAccount(String accountName, double amount) {
		int accountIndex = getAccountIndexByName(accountName);

		BankAccount account = this.accounts.get(accountIndex);
		account.deposit(amount);
		this.accounts.set(accountIndex, account);
	}

	public void withdrawFromAccount(String accountName, double amount) {
		int accountIndex = getAccountIndexByName(accountName);

		BankAccount account = this.accounts.get(accountIndex);
		account.withdraw(amount);
		this.accounts.set(accountIndex, account);
	}

	public void transferBetweenAccounts(String fromAccountName, String toAccountName, double amount) {
		int fromIndex = getAccountIndexByName(fromAccountName);
		int toIndex = getAccountIndexByName(toAccountName);
		
		BankAccount fromAccount = this.accounts.get(fromIndex);
		BankAccount toAccount = this.accounts.get(toIndex);
		
		fromAccount.withdraw(amount);
		toAccount.deposit(amount);
		
		this.accounts.set(fromIndex, fromAccount);
		this.accounts.set(toIndex, toAccount);
	}
	
	public void closeLoan(String loanId) {
		int loanIndex = getLoanIndexByLoanId(loanId);
		
		this.loans.get(loanIndex).close();
	}
	
	public int getLoanIndexByLoanId(String loanId) {
		
		int i=0;
		
		for(Loan l: loans) {
			if(l.getLoanId().equals(loanId)) {
				return i; 
			}
			
			i++;
		}
		return i;
	}
	
	public int getAccountIndexByName(String accountName) {
		for (int i = 0; i < this.accounts.size(); i++) {
			if (this.accounts.get(i).getAccountName().equals(accountName))
				return i;
		}
		return -1;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<BankAccount> getAccounts() {
		return accounts;
	}

	public void setAccounts(ArrayList<BankAccount> accounts) {
		this.accounts = accounts;
	}
	
	public String[] getDetails() {
		return new String[] {this.getCustomerId(),this.getName()};
	}

	public static int generateCustomerId(ArrayList<BankCustomer> existingCustomers) {
		rand = new Random();
		int c = 0;
		while (true) {

			c = rand.nextInt(10000) + 1;
			for (int i = 0; i < existingCustomers.size(); i++) {
				if (Integer.parseInt(existingCustomers.get(i).getCustomerId()) == c) {
					break;
				}
			}

			break;
		}

		return c;
	}
	
	public static int generateLoanId(ArrayList<Loan> existingLoans) {
		rand = new Random();
		int c = 0;
		while (true) {

			c = rand.nextInt(10000) + 1;
			for (int i = 0; i < existingLoans.size(); i++) {
				if (Integer.parseInt(existingLoans.get(i).getLoanId()) == c) {
					break;
				}
			}

			break;
		}

		return c;
	}
	
	public boolean customerHasBalanceInAnyAccount() {
		
		for(BankAccount acc: accounts) {
			if(acc.getBalance()>0)
				return true;
		}
		
		return false;
	}
	
	public double getMinimumLoanAmount() {
		double min = loans.get(0).getPayoffAmount();
		for(Loan l: loans) {
			if(l.getPayoffAmount()<min)
				min=l.getPayoffAmount();
		}
		return min;
	}
	
	public double getMaximumAccountBalance() {
		double max = accounts.get(0).getBalance();
		for(BankAccount acc: accounts) {
			if(acc.getBalance()>max)
				max=acc.getBalance();
		}
		return max;
	}
	
	public String getCustomerDetails() {
		String ret="";
		ret+="Customer ID: "+this.getCustomerId()+"\n";
		ret+="Customer Name: "+this.getName()+"\n";
		ret+="Accounts: \n";
		for(BankAccount acc: this.accounts) {
			ret+=acc.toString(0.0);
		}
		ret+="Loans: \n";
		for(Loan l: this.loans) {
			ret+=l.getDetailedLoanDisplayForManager();
		}
		return ret;
	}

}
