package model;

public class Currency {
	private String name;
	private String abbreviation;
	private double exchangeRate; //exchange rate w.r.t. USD
	
	public Currency(String name, String abbreviation, double exchangeRate) {
		this.name = name;
		this.abbreviation=abbreviation;
		this.exchangeRate = exchangeRate;
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
	
}
