package model;

public class Currency {
	private String name;
	private String abbreviation;
	private double exchangeRate; //exchange rate w.r.t. USD
	private double reverseRate; //exchange rate from USD to currency
	
	public Currency(String name, String abbreviation, double exchangeRate, double reverseRate) {
		this.name = name;
		this.abbreviation=abbreviation;
		this.exchangeRate = exchangeRate;
		this.reverseRate = reverseRate;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public double getAmountInUSD(double amount) {
		return amount*exchangeRate;
	}
	
	public double getAmountInCurrency(double amount) {
		return amount*reverseRate;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public double getReverseRate() {
		return reverseRate;
	}

	public void setReverseRate(double reverseRate) {
		this.reverseRate = reverseRate;
	}
	
}
