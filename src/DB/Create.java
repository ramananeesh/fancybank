package DB;

import model.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Create {

	public static boolean insertNewCustomer(BankCustomer customer) {

		String sql = "insert into customer values('" + customer.getCustomerId() + "','" + customer.getName() + "','"
				+ customer.getPhoneNumber() + "','" + customer.getSsn() + "','" + customer.getEmail() + "','"
				+ customer.getPassword() + "')";

		return SQLHelper.performQuery(sql);

	}

	public static boolean insertNewAccount(BankAccount account, String customerId) {
		String sql = "Insert into bankAccount values('" + account.getAccountName() + "','" + account.getType() + "',"
				+ account.getBalance() + "," + account.getRate() + "," + account.getWithdrawalFee() + ","
				+ account.getAccountOperationFee() + "," + account.getTransactionFee() + "," + account.isNewAccount()
				+ ",'" + customerId + "'," + account.isTradable() + "," + account.getTradeThreshold() + ","
				+ account.getTradingFee() + ")";

		return SQLHelper.performQuery(sql);
	}

	public static boolean insertNewTransaction(Transaction t, String customerId) {
		String sql = "Insert into transaction (fromAccount, toAccount, customerId, type, fromCustomer, toCustomer, amount) values('"
				+ t.getFromAccount() + "','" + t.getToAccount() + "','" + customerId + "','" + t.getType() + "','"
				+ t.getFromCustomer() + "','" + t.getToCustomer() + "'," + t.getAmount() + ")";
		return SQLHelper.performQuery(sql);
	}

	public static boolean insertNewLoan(Loan l) {

		String sql = "Insert into loan values('" + l.getLoanId() + "','" + l.getCustomerId() + "','"
				+ l.getCustomerName() + "'," + l.getLoanAmount() + "," + l.getInterestRate() + "," + l.getTenure() + ","
				+ l.isActive() + "," + l.isApproved() + ",'" + l.getLoanStartDate() + "','" + l.getCollateral() + "',"
				+ l.getCollateralAmount() + ")";

		return SQLHelper.performQuery(sql);
	}

	public static boolean insertNewCustomerStock(CustomerStock stock, String customerId, String accountName) {
		String sql = "Insert into customerStock values('" + stock.getStockID() + "','" + stock.getStockName() + "','"
				+ customerId + "'," + stock.getBuyingValue() + "," + stock.getCurrentValue() + ","
				+ stock.getNumStocks() + ",'" + accountName + "'" + ")";

		return SQLHelper.performQuery(sql);
	}

	public static boolean insertNewBankStock(BankStock stock) {
		String sql = "Insert into bankStock values('" + stock.getStockName() + "'," + stock.getValue() + ","
				+ stock.getNumStocks() + ")";

		return SQLHelper.performQuery(sql);
	}

	public static boolean insertNewManager(BankManager manager) {
		String sql = "Insert into manager values('" + manager.getName() + "','" + manager.getId() + "','"
				+ manager.getEmail() + "','" + manager.getSecurityCode() + "','" + manager.getPassword() + "')";

		return SQLHelper.performQuery(sql);
	}

	public static void main(String[] args) {
		insertNewCustomer(new BankCustomer("Aneesh", "1234", new Address("", "", "", "", ""), "1234567891", "12345678",
				"test", "1234"));
	}
}
