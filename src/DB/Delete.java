package DB;

public class Delete {

	public static boolean removeAccountForCustomer(String accountName, String customerId) {
		String sql = "Delete from bankAccount where accountName='" + accountName + "' and customerId='" + customerId
				+ "'";

		return SQLHelper.performQuery(sql);
	}

	public static boolean removeStockForCustomer(String stockId, String customerId, String accountName) {
		String sql = "Delete from customerStock where stockId='" + stockId + "' and customerId='" + customerId
				+ "' and accountName='" + accountName + "'";

		return SQLHelper.performQuery(sql);
	}
}
