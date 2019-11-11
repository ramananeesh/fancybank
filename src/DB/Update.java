package DB;

import model.*;
import java.sql.*;

public class Update {

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

	public static boolean performUpdate(String sql) {
		Connection conn = getConnection();

		try {
			Statement st = conn.createStatement();
			int m = st.executeUpdate(sql);

			if (m == 1) {
				System.out.println("Update successful");
				conn.close();
				return true;
			} else {
				System.out.println("Update unsuccessful");
			}

			conn.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		return false;
	}

	public static boolean updateBankStocksForBuyOrSell(String stockName, int quantity) {
		String sql = "Update bankStock set numberOfStocks=" + quantity + " where " + "stockName='" + stockName + "'";

		return performUpdate(sql);
	}

	public static boolean updateDepositOrWithdrawal(String customerId, String accountName, double balance) {
		String sql = "Update bankaccount set balance=" + balance + " where customerId='" + customerId
				+ "' and accountName='" + accountName + "'";

		return performUpdate(sql);
	}

	public static boolean updateLoanForSettle(String loanId, String customerId) {
		String sql = "Update loan set isActive=false where loanId='" + loanId + "' and customerId='" + customerId + "'";

		return performUpdate(sql);
	}

	public static boolean updateLoanForApproval(String loanId, String customerId) {
		String sql = "Update loan set isApproved=true where loanId='" + loanId + "' and customerId='" + customerId
				+ "'";

		return performUpdate(sql);
	}

	public static boolean updateFees(String feeName, double value) {
		String sql = "Update fees set value=" + value + " where name='" + feeName + "'";

		return performUpdate(sql);
	}

	public static boolean updateFeeForCustomer(String feeName, double value, String customerId) {
		if(feeName.equals("savingsInterestRate"))
			feeName="rate";
		if(feeName.equals("highBalance"))
				return true;
		
		String sql = "Update bankAccount set " + feeName + "=" + value + " where ";

		if (customerId.equals(""))
			sql += "1";
		else
			sql += " customerId='" + customerId + "'";

		return performUpdate(sql);
	}

	public static boolean updateLoanInterestRateForCustomer(String customerId, double value) {
		String sql = "Update loan set interestRate=" + value + " where ";

		if (customerId.equals(""))
			sql += "1";
		else
			sql += " customerId='" + customerId + "'";

		return performUpdate(sql);
	}

}
