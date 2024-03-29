package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLHelper {
	public static Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdb?" + "user=root");

			// Do something with the Connection

		} catch (Exception ex) {
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

	public static boolean performQuery(String sql) {
		Connection conn = getConnection();

		try {
			Statement st = conn.createStatement();
			int m = st.executeUpdate(sql);

			if (m == 1) {

				conn.close();
				return true;
			} else {

			}

			conn.close();
		} catch (Exception e) {
			System.out.println("Exception: " + e.getMessage());
		}

		return false;
	}
}
