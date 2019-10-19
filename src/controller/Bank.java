package controller;

import java.util.ArrayList;

public class Bank {

	private String name; 
	private ArrayList<BankBranch> branches;
	
	public Bank(String name) {
		this.name = name;
		this.branches = new ArrayList<BankBranch>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<BankBranch> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<BankBranch> branches) {
		this.branches = branches;
	}
	
	public void addBranch(BankBranch newBranch) {
		this.branches.add(newBranch);
	}
	
	public void removeBranch(int branchIndex) {
		this.branches.remove(branchIndex);
	}
}
