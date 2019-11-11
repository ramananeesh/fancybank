package DB;

import model.*;
import java.sql.*;

public class Update {

	public static boolean updateBankStocksForBuyOrSell(String stockName, int quantity, double value) {
		String sql = "Update bankStock set numberOfStocks=" + quantity + ", value="+value+" where " + "stockName='" + stockName + "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateDepositOrWithdrawal(String customerId, String accountName, double balance) {
		String sql = "Update bankaccount set balance=" + balance + " where customerId='" + customerId
				+ "' and accountName='" + accountName + "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateLoanForSettle(String loanId, String customerId) {
		String sql = "Update loan set isActive=false where loanId='" + loanId + "' and customerId='" + customerId + "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateLoanForApproval(String loanId, String customerId) {
		String sql = "Update loan set isApproved=true where loanId='" + loanId + "' and customerId='" + customerId
				+ "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateFees(String feeName, double value) {
		String sql = "Update fees set value=" + value + " where name='" + feeName + "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateFeeForCustomer(String feeName, double value, String customerId) {
		if (feeName.equals("savingsInterestRate"))
			feeName = "rate";
		if (feeName.equals("highBalance"))
			return true;

		String sql = "Update bankAccount set " + feeName + "=" + value + " where ";

		if (customerId.equals(""))
			sql += "1";
		else
			sql += " customerId='" + customerId + "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateLoanInterestRateForCustomer(String customerId, double value) {
		String sql = "Update loan set interestRate=" + value + " where ";

		if (customerId.equals(""))
			sql += "1";
		else
			sql += " customerId='" + customerId + "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean updateCustomerStockNumber(String stockName, String customerId, int value) {
		String sql = "Update customerStock set numStocks=" + value + " where customerId='" + customerId
				+ "' and stockName='" + stockName + "'";

		return SQLHelper.performQuery(sql);
	}
	public static boolean updateCustomerStockValue(String stockName, double value) {
		String sql = "Update customerStock set currentValue=" + value + " where stockName='" + stockName + "'";

		return SQLHelper.performQuery(sql);
	}

}
