package DB;

import model.*;
import java.sql.*;

public class Insert {

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

	public static boolean performInsert(String sql) {
		Connection conn = getConnection();

		try {
			Statement st = conn.createStatement();
			int m = st.executeUpdate(sql);

			if (m == 1) {
				System.out.println("Insert successful");
				conn.close();
				return true;
			}
			else {
				System.out.println("Insert unsuccessful");
			}

			conn.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		return false;
	}

	public static boolean insertNewCustomer(BankCustomer customer) {

		String sql = "insert into customer values('" + customer.getCustomerId() + "','" + customer.getName() + "','"
				+ customer.getPhoneNumber() + "','" + customer.getSsn() + "','" + customer.getEmail() + "','"
				+ customer.getPassword() + "')";
		
		return performInsert(sql);

	}

	public static boolean insertNewTransaction(Transaction t, String customerId) {
		String sql = "Insert into transaction values('" + t.getFromAccount() + "','" + t.getToAccount() + "','"
				+ customerId + "','" + t.getType() + "','" + t.getFromCustomer() + "','" + t.getToCustomer()
				+ t.getAmount() + "')";
		return performInsert(sql);
	}

	public static boolean insertNewLoan(Loan l) {
		
		String sql = "Insert into loan values('"+l.getLoanId()+ "','" +l.getCustomerId()+ "','" +l.getCustomerName()+ "','" +l.getLoanAmount()
		+ "','" +l.getInterestRate()+ "','" +l.getTenure()+ "','" +l.isActive()+ "','" +l.isApproved()+ "','" +l.getLoanStartDate()
		+ "','" +l.getCollateral()+ "','" +l.getCollateralAmount()+"')";
		
		return performInsert(sql);
	}

	public static void main(String[] args) {
		insertNewCustomer(new BankCustomer("Aneesh", "1234", new Address("", "", "", "", ""), "1234567891", "12345678",
				"test", "1234"));
	}
}
