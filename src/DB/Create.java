package DB;

import model.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Create {

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
			} else {
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

	public static boolean insertNewAccount(BankAccount account, String customerId) {
		String sql = "Insert into bankAccount values('" + account.getAccountName() + "','" + account.getType() + "',"
				+ account.getBalance() + "," + account.getRate() + "," + account.getWithdrawalFee() + ","
				+ account.getAccountOperationFee() + "," + account.getTransactionFee() + "," + account.isNewAccount()
				+ ",'" + customerId + "'," + account.isTradable() + "," + account.getTradeThreshold() + ","
				+ account.getTradingFee() + ")";

		return performInsert(sql);
	}

	public static boolean insertNewTransaction(Transaction t, String customerId) {
		String sql = "Insert into transaction (fromAccount, toAccount, customerId, type, fromCustomer, toCustomer, amount) values('"
				+ t.getFromAccount() + "','" + t.getToAccount() + "','" + customerId + "','" + t.getType() + "','"
				+ t.getFromCustomer() + "','" + t.getToCustomer() + "'," + t.getAmount() + ")";
		return performInsert(sql);
	}

	public static boolean insertNewLoan(Loan l) {

		String sql = "Insert into loan values('" + l.getLoanId() + "','" + l.getCustomerId() + "','"
				+ l.getCustomerName() + "'," + l.getLoanAmount() + "," + l.getInterestRate() + "," + l.getTenure() + ","
				+ l.isActive() + "," + l.isApproved() + ",'" + l.getLoanStartDate() + "','" + l.getCollateral() + "',"
				+ l.getCollateralAmount() + ")";

		return performInsert(sql);
	}

	public static boolean insertNewCustomerStock(CustomerStock stock, String customerId) {
		String sql = "Insert into customerStock values('" + stock.getStockID() + "','" + stock.getStockName() + "','"
				+ customerId + "'," + stock.getBuyingValue() + "," + stock.getCurrentValue() + ","
				+ stock.getNumStocks() + ")";

		return performInsert(sql);
	}

	public static boolean insertNewBankStock(BankStock stock) {
		String sql = "Insert into bankStock values('" + stock.getStockName() + "'," + stock.getValue() + ","
				+ stock.getNumStocks() + ")";

		return performInsert(sql);
	}

	public static boolean insertNewManager(BankManager manager) {
		String sql = "Insert into manager values('" + manager.getName() + "','" + manager.getId() + "','"
				+ manager.getEmail() + "','" + manager.getSecurityCode() + "','" + manager.getPassword() + "')";

		return performInsert(sql);
	}

	public static void main(String[] args) {
		insertNewCustomer(new BankCustomer("Aneesh", "1234", new Address("", "", "", "", ""), "1234567891", "12345678",
				"test", "1234"));
	}
}
