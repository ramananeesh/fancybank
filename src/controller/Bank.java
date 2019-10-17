package controller;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;

import model.*;

public class Bank extends Observable {

	private ArrayList<BankManager> managers;
	private ArrayList<BankCustomer> customers;
	private ArrayList<Transaction> transactions;

	public Bank() {
		super();
		managers = new ArrayList<BankManager>();
		customers = new ArrayList<BankCustomer>();
		transactions = new ArrayList<Transaction>();
	}

	public BankManager addManager(String name, String id, String email, String securityCode, String password) {
		BankManager newManager = new BankManager(name, id, email, securityCode, password);
		managers.add(newManager);
		return newManager;
	}

	public Transaction addTransaction(String type, String amount, String fromAccount, String toAccount) {
		Transaction newTransaction = new Transaction(type, amount, fromAccount, toAccount);
		transactions.add(newTransaction);
		return newTransaction;
	}

	public BankCustomer addCustomer(String name, Address address, String phoneNumber, String ssn, String email,
			String password) {

		int customerId = BankCustomer.generateCustomerId(customers);
		BankCustomer newCustomer = new BankCustomer(name, customerId, address, phoneNumber, ssn, email, password);
		this.customers.add(newCustomer);
		return newCustomer;
	}

	public void addAccount(BankCustomer customer, String accountName, String accountType) {
		this.getCustomerByEmail(customer.getEmail()).addAccount(new BankAccount(accountName, accountType));
		setChanged();
		notifyObservers();
	}
	
	public int getCustomerIndex(String name) {
		int i=0;
		for(BankCustomer cust : this.customers) {
			if(cust.getName().equals(name))
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

	public void depositForCustomer(BankCustomer customer, String accountName, double amount) {
		this.getCustomerByEmail(customer.getEmail()).depositIntoAccount(accountName, amount);
	}
	
	public void withdrawForCustomer(BankCustomer customer, String accountName, double amount) {
		this.getCustomerByEmail(customer.getEmail()).withdrawFromAccount(accountName, amount);
	}
	
	public void transferBetweenAccountsForCustomer(BankCustomer customer, String fromAccountName, String toAccountName, double amount) {
		this.getCustomerByEmail(customer.getEmail()).transferBetweenAccounts(fromAccountName, toAccountName, amount);
	}
	
	public BankCustomer login(String email, String password) {
		BankCustomer customer = getCustomerByEmail(email);
		if (customer == null)
			return null;

		if (customer.getPassword().equals(password))
			return customer;

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

}
