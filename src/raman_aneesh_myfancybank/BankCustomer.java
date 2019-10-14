package raman_aneesh_myfancybank;

import java.util.ArrayList;

public class BankCustomer extends Customer {

	private String password; 
	private ArrayList<Account> accounts;
	
	
	public BankCustomer(String name, int customerId, Address address, String phoneNumber, String ssn, String email,
			String password, ArrayList<Account> accounts) {
		super(name, customerId, address, phoneNumber, ssn, email);
		this.password = password;
		this.accounts = accounts;
	}

	public void addAccount(Account newAccount) {
		this.accounts.add(newAccount);
	}
	
	public Account removeAccount(int accountIndex) {
		Account removedAccount = this.accounts.get(accountIndex);
		if(removedAccount!=null)
			this.accounts.remove(accountIndex);
		
		return removedAccount;
	}

	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public ArrayList<Account> getAccounts() {
		return accounts;
	}


	public void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}
	
}
