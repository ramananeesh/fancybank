package DB;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Read {

	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb?" + "user=root");

			// Do something with the Connection

		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
			// handle any errors
			System.out.println("Exception: " + ex.getMessage());

		}

		return conn;
	}

	public static ResultSet performRead(String query) {
		ResultSet rs = null;

		try {
			Connection conn = getConnection();

			Statement stmt = conn.createStatement();

			rs = stmt.executeQuery(query);
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		return rs;
	}

	public static BankCustomer getCustomer(String email) {
		BankCustomer cust = null;
		String customerId, name, phoneNumber, ssn, password;

		// get customer info
		HashMap<String, String> customerInfo = getCustomerInfo(email);

		// get address
		Address addr = getAddress(customerInfo.get("customerId"));
		
		//get Transactions
		
		//get Loans
		return cust;
	}

	public static HashMap<String, String> getCustomerInfo(String email) {
		HashMap<String, String> map = new HashMap<>();
		map.put("email", email);
		String query = "Select customerId, name, phoneNumber, " + "ssn, password from customer where " + "email='"
				+ email + "'";
		ResultSet rs = performRead(query);
		try {
			while (rs.next()) {
				map.put("customerId", rs.getString("customerId"));
				map.put("name", rs.getString("name"));
				map.put("phoneNumber", rs.getString("phoneNumber"));
				map.put("ssn", rs.getString("ssn"));
				map.put("password", rs.getString("password"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static Address getAddress(String customerId) {
		Address address = null;
		String query = "Select houseNumber, street, city, state, zipcode " + "from address where customerId='"
				+ customerId + "'";
		ResultSet rs = performRead(query);
		try {
			while (rs.next()) {
				address = new Address(rs.getString("houseNumber"), rs.getString("street"),
						rs.getString("city"), rs.getString("state"), rs.getString("zipcode"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}
	
	public static ArrayList<Transaction> getTransactions(String customerId) {
		ArrayList<Transaction> t = null; 
		String query = "Select id, fromAccount, toAccount, type, fromCustomer, toCustomer, "
				+ "amount from transaction where customerId='"+customerId+"'";
		ResultSet rs = performRead(query);
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String fromAccount = rs.getString("fromAccount");
				String toAccount = rs.getString("toAccount");
				String type = rs.getString("type");
				String fromCustomer = rs.getString("fromCustomer");
				String toCustomer=  rs.getString("toCustomer");
				String amount= rs.getString("amount");
				t.add(new Transaction(id, fromCustomer, toCustomer, type, Double.parseDouble(amount),fromAccount, toAccount));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t; 
	}
	
	public static ArrayList<Loan> getLoans(String customerId){
		ArrayList<Loan> loans = null;
		
		return loans;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getCustomerInfo("test");
	}

}
