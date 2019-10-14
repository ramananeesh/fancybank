package model;

public class Address {

	private String houseNumber;
	private String street;
	private String city;
	private String zipCode;
	private String state;

	public Address(String houseNumber, String street, String city, String zipCode, String state) {
		super();
		this.houseNumber = houseNumber;
		this.street = street;
		this.city = city;
		this.zipCode = zipCode;
		this.state = state;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String toString() {
		return "Address: #" + houseNumber + ", " + street + ", " + city + ", " + zipCode + ", " + state;
	}

}
