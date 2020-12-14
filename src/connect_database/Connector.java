/*
 * To connect the database
 */
package connect_database;
import java.sql.*;

public class Connector {
    // Change the parameters accordingly.
	private final static String DBURL = "jdbc:mysql://localhost:3306/BANK?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT";
	private final static String USER = "root";
	private final static String PASSWORD = "000000";

	public static Connection getConn() {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                return DriverManager.getConnection(DBURL, USER, PASSWORD);
            } catch (Exception e) {
                System.out.println("Error while opening a connection to database server: " + e.getMessage());
                return null;
            }
	}
}
