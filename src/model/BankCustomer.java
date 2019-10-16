package model;

import java.util.ArrayList;
import java.util.Random;

public class BankCustomer extends Customer {

	private String password;
	private ArrayList<BankAccount> accounts;
	private ArrayList<Transaction> transactions;
	static Random rand = new Random();

	public BankCustomer(String name, int customerId, Address address, String phoneNumber, String ssn, String email,
			String password) {
		super(name, customerId, address, phoneNumber, ssn, email);
		this.password = password;
		this.accounts = new ArrayList<BankAccount>();
		this.transactions = new ArrayList<Transaction>();
	}

	public BankCustomer(String name, int customerId, Address address, String phoneNumber, String ssn, String email,
			String password, ArrayList<BankAccount> accounts, ArrayList<Transaction> transactions) {
		super(name, customerId, address, phoneNumber, ssn, email);
		this.password = password;
		this.accounts = accounts;
		this.transactions = transactions;
	}

	public void addAccount(BankAccount newAccount) {
		this.accounts.add(newAccount);
	}

	public void addTransaction(Transaction newTransaction) {
		this.transactions.add(newTransaction);
	}

	public BankAccount removeAccount(int accountIndex) {
		BankAccount removedAccount = this.accounts.get(accountIndex);
		if (removedAccount != null)
			this.accounts.remove(accountIndex);

		return removedAccount;
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

	public static int generateCustomerId(ArrayList<BankCustomer> existingCustomers) {
		rand = new Random();
		int c = 0;
		while (true) {

			c = rand.nextInt(10000) + 1;
			for (int i = 0; i < existingCustomers.size(); i++) {
				if (existingCustomers.get(i).getCustomerId() == c) {
					break;
				}
			}

			break;
		}

		return c;
	}

}
