package model;

public class Customer extends Person {

	private int customerId;
	private Address address;
	private String phoneNumber;
	private String ssn;
	private String email;

	public Customer(String name, int customerId, Address address, String phoneNumber, String ssn, String email) {
		super(name);
		this.customerId = customerId;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.ssn = ssn;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", address=" + address + ", phoneNumber=" + phoneNumber + ", ssn="
				+ ssn + ", email=" + email + "]";
	}

}
