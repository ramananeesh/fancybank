package raman_aneesh_myfancybank;

public class BankManager extends Person {

	private String id;
	private String email;
	private String securityCode;
	private String password;

	public BankManager(String name, String id, String email, String securityCode, String password) {
		super(name);
		this.id = id;
		this.email = email;
		this.securityCode = securityCode;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BankManager [id=" + id + ", email=" + email + ", securityCode=" + securityCode + ", password="
				+ password + "]";
	}

}
