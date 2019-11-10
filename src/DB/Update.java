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
		String sql="Update bankStock set numberOfStocks="+quantity+" where "
				+ "stockName='"+stockName+"'";
		
		return performUpdate(sql);
	}

	
}
