package DB;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Read {

	public static boolean checkCredentials(String email, String password) {
		String query = "Select password from customer where email='" + email + "'";
		ResultSet rs = SQLHelper.performRead(query);

		try {
			while (rs.next()) {
				String p = rs.getString("password");

				if (!p.equals(password))
					return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static BankCustomer getCustomer(String email) {
		BankCustomer cust = null;

		// get customer info
		HashMap<String, String> customerInfo = getCustomerInfo(email);
		String customerId = customerInfo.get("customerId");
		// get address
		Address addr = getAddress(customerId);

		// get accounts
		ArrayList<BankAccount> accounts = getAccounts(customerId);

		// get Transactions
		ArrayList<Transaction> t = getTransactions(customerId);

		// get Loans
		ArrayList<Loan> l = getLoans(customerId);

		// get securities/stocks
		ArrayList<CustomerStock> stocks = getCustomerStocks(customerId);
		accounts = matchStocksWithAccounts(accounts, stocks);

		cust = new BankCustomer(customerInfo.get("name"), customerInfo.get("customerId"), addr,
				customerInfo.get("phoneNumber"), customerInfo.get("ssn"), customerInfo.get("email"),
				customerInfo.get("password"), accounts, t, l);
		return cust;
	}

	public static HashMap<String, String> getCustomerInfo(String email) {
		HashMap<String, String> map = new HashMap<>();
		map.put("email", email);
		String query = "Select customerId, name, phoneNumber, " + "ssn, password from customer where " + "email='"
				+ email + "'";
		ResultSet rs = SQLHelper.performRead(query);
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
		Address address = new Address("", "", "", "", "");
		String query = "Select houseNumber, street, city, state, zipcode " + "from address where customerId='"
				+ customerId + "'";
		ResultSet rs = SQLHelper.performRead(query);
		try {
			while (rs.next()) {
				address = new Address(rs.getString("houseNumber"), rs.getString("street"), rs.getString("city"),
						rs.getString("state"), rs.getString("zipcode"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return address;
	}

	public static ArrayList<BankAccount> getAccounts(String customerId) {
		ArrayList<BankAccount> accounts = new ArrayList<BankAccount>();
		String query = "select accountName, accountType, balance, rate, "
				+ "withdrawalFee, accountOperationFee, transactionFee,"
				+ "isNewAccount, tradeThreshold, tradingFee from bankaccount where customerId='" + customerId + "'";
		ResultSet rs = SQLHelper.performRead(query);
		try {
			while (rs.next()) {
				String name = rs.getString("accountName");
				String accountType = rs.getString("accountType");
				double balance = Double.parseDouble(rs.getString("balance"));
				double rate = Double.parseDouble(rs.getString("rate"));
				double withdrawalFee = Double.parseDouble(rs.getString("withdrawalFee"));
				double accountOperationFee = Double.parseDouble(rs.getString("accountOperationFee"));
				double transactionFee = Double.parseDouble(rs.getString("transactionFee"));
				boolean isNewAccount = Boolean.parseBoolean(rs.getString("isNewAccount"));
				double tradingThreshold = Double.parseDouble(rs.getString("tradeThreshold"));
				double tradingFee = Double.parseDouble(rs.getString("tradingFee"));
				accounts.add(new BankAccount(name, accountType, balance, rate, withdrawalFee, transactionFee,
						accountOperationFee, isNewAccount, tradingThreshold, tradingFee));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return accounts;
	}

	public static ArrayList<Transaction> getTransactions(String customerId) {
		ArrayList<Transaction> t = new ArrayList<Transaction>();
		String query = "Select id, fromAccount, toAccount, type, fromCustomer, toCustomer, "
				+ "amount from transaction";

		// customerId = manager when manager is querying
		if (!customerId.equals("manager")) {
			query += " where customerId='" + customerId + "'";
		}

		ResultSet rs = SQLHelper.performRead(query);
		try {
			while (rs.next()) {
				String id = rs.getString("id");
				String fromAccount = rs.getString("fromAccount");
				String toAccount = rs.getString("toAccount");
				String type = rs.getString("type");
				String fromCustomer = rs.getString("fromCustomer");
				String toCustomer = rs.getString("toCustomer");
				String amount = rs.getString("amount");
				t.add(new Transaction(id, fromCustomer, toCustomer, type, Double.parseDouble(amount), fromAccount,
						toAccount));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	public static ArrayList<Loan> getLoans(String customerId) {
		ArrayList<Loan> loans = new ArrayList<Loan>();

//		String query = "Select 'loanId', 'customerId', 'customerName', "
//				+ "'loanAmount', 'interestRate', 'tenure', 'isActive',"
//				+ "'isApproved', 'startDate', 'collateral', 'collateralAmount'" + "from loan";

		String query = "Select loanId, customerId, customerName, loanAmount, interestRate, tenure, isActive, isApproved, startDate, collateral, collateralAmount from loan"
				+ "";

		// customerId = manager when manager is querying
		if (!customerId.equals("manager")) {
			query += " where customerId='" + customerId + "'";
		}

		ResultSet rs = SQLHelper.performRead(query);

		try {
			while (rs.next()) {
				String loanId = rs.getString("loanId");
				String customerName = rs.getString("customerName");

//				double loanAmount = Double.parseDouble(rs.getString("loanAmount"));
//				double interestRate = Double.parseDouble(rs.getString("interestRate"));
				double loanAmount = rs.getDouble("loanAmount");
				double interestRate = rs.getDouble("interestRate");
				int tenure = rs.getInt("tenure");
				boolean isActive = rs.getBoolean("isActive");
				boolean isApproved = rs.getBoolean("isApproved");
				Date startDate = rs.getDate("startDate");
				String collateral = rs.getString("collateral");
				double collateralAmount = rs.getDouble("collateralAmount");
				loans.add(new Loan(loanId, customerName, customerId, loanAmount, interestRate, tenure, isActive,
						isApproved, startDate, collateral, collateralAmount));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loans;
	}

	public static ArrayList<CustomerStock> getCustomerStocks(String customerId) {
		ArrayList<CustomerStock> stocks = new ArrayList<CustomerStock>();
		String query = "Select stockId, stockName, buyingValue, currentValue,"
				+ "numstocks, accountName from customerStock where customerId='" + customerId + "'";
		ResultSet rs = SQLHelper.performRead(query);

		try {
			while (rs.next()) {
				String stockId = rs.getString("stockId");
				String stockName = rs.getString("stockName");
				double buyingValue = rs.getDouble("buyingValue");
				double currentValue = rs.getDouble("currentValue");
				int numStocks = rs.getInt("numStocks");
				String accountName = rs.getString("accountName");
				stocks.add(new CustomerStock(stockId, stockName, buyingValue, currentValue, numStocks, accountName));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocks;
	}

	public static ArrayList<BankAccount> matchStocksWithAccounts(ArrayList<BankAccount> accounts,
			ArrayList<CustomerStock> stocks) {
		for (CustomerStock stock : stocks) {
			for (BankAccount acc : accounts) {
				if (stock.getAccountName().equals(acc.getAccountName())) {
					acc.addStock(stock);
				}
			}
		}

		return accounts;
	}

	public static ArrayList<BankStock> getBankStocks() {
		ArrayList<BankStock> stocks = new ArrayList<BankStock>();
		String query = "Select stockName, value, numberOfStocks from bankStock";
		ResultSet rs = SQLHelper.performRead(query);

		try {
			while (rs.next()) {
				String stockName = rs.getString("stockName");
				double value = rs.getDouble("value");
				int numberOfStocks = rs.getInt("numberOfStocks");

				stocks.add(new BankStock(stockName, value, numberOfStocks));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stocks;
	}
	
	public static HashMap<String,Double> getAllFees(){
		HashMap<String,Double> map=new HashMap<String, Double>();
		String query = "Select * from fees";
		ResultSet rs = SQLHelper.performRead(query);
		
		try {
			while (rs.next()) {
				map.put(rs.getString("name"),rs.getDouble("value"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	public static ArrayList<BankCustomer> getAllCustomers() {
		ArrayList<BankCustomer> customers = new ArrayList<BankCustomer>();
		ArrayList<String> emails = new ArrayList<String>();
		String query = "Select email from customer";

		ResultSet rs = SQLHelper.performRead(query);

		try {
			while (rs.next()) {
				emails.add(rs.getString("email"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// loop through emails and get customers
		for (String email : emails) {
			customers.add(getCustomer(email));
		}

		return customers;
	}

	public static Date getDateFromString(String date) {
		String[] x = date.split("/");

		return new Date(Integer.parseInt(x[0]), Integer.parseInt(x[1]), Integer.parseInt(x[2]));
	}
	
	public static ArrayList<BankManager> getAllManagers(){
		ArrayList<BankManager> m=new ArrayList<BankManager>();
		
		String query="select * from manager";
		ResultSet rs = SQLHelper.performRead(query);
		
		try {
			while(rs.next()) {
				String name=rs.getString("name");
				String id=rs.getString("id");
				String email=rs.getString("email");
				String securityCode=rs.getString("securityCode");
				String password=rs.getString("password");
				m.add(new BankManager(name, id, email, securityCode, password));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return m;
	}

}
