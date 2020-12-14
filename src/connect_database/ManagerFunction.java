/*
 * database function for manager
 */
package connect_database;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerFunction {
	private final static Connection conn = Connector.getConn();
	
	/*
	 * Login for manager
	 * Input manager name and password(admin 000000)
	 * Return 0 if successfully login
	 * Return -1 if fail
	 */
	public static int managerLogin(String name, String password) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            rset = stmt.executeQuery("SELECT * FROM MANAGER WHERE NAME = \'"+name+"\' AND PASSWORD = \'"+password+"\';");
            if (rset.next()) {
            	return 0;
            }
		} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
	}
	
	/*
	 * Get all the customer list for manager
	 * Return List object, each one a String[] {customer_ID, customer_name, account_type, currency_type, money_amount}
	 * Return a zero-size list if no record
	 * Return null if error
	 */
	public static List<String[]> searchAllCustomer() {
		List<String[]> all_customer_accounts = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            String[] account_types = {"SAVING", "CHECKING", "LOAN", "STOCK"};
            for (String s : account_types) {
	            // get all accounts record
	            rset = stmt.executeQuery("SELECT * FROM CUSTOMER, "+s+"_ACCOUNT WHERE CUSTOMER.ID = "+s+"_ACCOUNT.CUSTOMER_ID;");
	            if (rset.next()) {
	            	rset.previous();
	            	while (rset.next()) {
	            		String[] record = new String[5];
	            		record[0] = rset.getInt("CUSTOMER.ID")+"";
	            		record[1] = rset.getString("CUSTOMER.NAME");
	            		record[2] = s;
	            		if (s.equals("SAVING") || s.equals("CHECKING")) record[3] = rset.getString(s+"_ACCOUNT.CURRENCY_TYPE");
	            		else record[3] = "Dollar";
	            		record[4] = rset.getBigDecimal(s+"_ACCOUNT.MONEY_AMOUNT").toPlainString();
	            		//for (String str : record) System.out.print(str+" "); // 1 first_customer SAVING Dollar 3600.00 
	            		//System.out.print("\n");
	            		all_customer_accounts.add(record);
	            	}
	            }
            }
            return all_customer_accounts;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get all the customer list with loan for manager
	 * Return List object, each one a String[] {customer_ID, customer_name, "LOAN", "Dollar", money_amount}
	 * Return a zero-size list if no loan-customer
	 * Return null if error
	 */
	public static List<String[]> searchAllLoanCustomer() {
		List<String[]> all_customer_accounts = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            String[] account_types = {"LOAN"};
            for (String s : account_types) {
	            // get all accounts record
	            rset = stmt.executeQuery("SELECT * FROM CUSTOMER, "+s+"_ACCOUNT WHERE CUSTOMER.ID = "+s+"_ACCOUNT.CUSTOMER_ID;");
	            if (rset.next()) {
	            	rset.previous();
	            	while (rset.next()) {
	            		String[] record = new String[5];
	            		record[0] = rset.getInt("CUSTOMER.ID")+"";
	            		record[1] = rset.getString("CUSTOMER.NAME");
	            		record[2] = s;
	            		record[3] = "Dollar";
	            		record[4] = rset.getBigDecimal(s+"_ACCOUNT.MONEY_AMOUNT").toPlainString();
	            		//for (String str : record) System.out.print(str+" "); // 2 second_customer LOAN Dollar 160.00 
	            		//System.out.print("\n");
	            		if (!rset.getBigDecimal(s+"_ACCOUNT.MONEY_AMOUNT").toPlainString().equals("0.00")) {
	            			all_customer_accounts.add(record);
	            		}
	            	}
	            }
            }
            return all_customer_accounts;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get all the transaction list for today for manager
	 * Return List object, each one a String[] {customer_ID, customer_name, account_type, currency_type, money_changed, current_balance, time}
	 * Return a zero-size list if no transaction
	 * Return null if error
	 */
	public static List<String[]> searchTransactionToday() {
		List<String[]> all_transaction = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get all transaction record
            rset = stmt.executeQuery("SELECT * FROM TRANSACTION, CUSTOMER WHERE TRANSACTION.CUSTOMER_ID  = CUSTOMER.ID AND to_days(TRANSACTION.TIME) = to_days(now());");
            if (rset.next()) {
            	rset.previous();
            	while (rset.next()) {
            		String[] record = new String[7];
            		record[0] = rset.getInt("CUSTOMER.ID")+"";
            		record[1] = rset.getString("CUSTOMER.NAME");
            		record[2] = rset.getString("TRANSACTION.ACCOUNT_TYPE");
            		record[3] = rset.getString("TRANSACTION.CURRENCY_TYPE");
            		record[4] = rset.getBigDecimal("TRANSACTION.MONEY_CHANGED").toPlainString();
            		record[5] = rset.getBigDecimal("TRANSACTION.CURRENT_BALANCE").toPlainString();
            		record[6] = rset.getTimestamp("TRANSACTION.Time").toString();
            		for (String str : record) System.out.print(str+" "); // 1 first_customer SAVING Dollar -200.00 3800.00 2020-12-15 08:19:43.0 
            		System.out.print("\n");
            		all_transaction.add(record);
            	}
            }
            return all_transaction;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get all the stock list for manager
	 * Return List object, each one a String[] {stock_ID, stock_name, stock_original_price}
	 * Return a zero-size list if no stock
	 * Return null if not success(no customer or no stock account)
	 */
	public static List<String[]> searchAllStockList() {
		List<String[]> all_stock = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get all stock record
            rset = stmt.executeQuery("SELECT * FROM STOCK_LIST;");
            if (rset.next()) {
            	rset.previous();
            	while (rset.next()) {
            		String[] record = new String[3];
            		record[0] = rset.getInt("ID")+"";
            		record[1] = rset.getString("NAME");
            		record[2] = rset.getBigDecimal("STOCK_LIST.PRICE").toPlainString();
            		//for (String s : record) System.out.print(s+" "); //1 NASDAQ 10.00 10.00 10 
            		//System.out.print("\n");
            		all_stock.add(record);
            	}
            }
            return all_stock;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Insert a new stock into the stock list
	 * Input stock name, stock price
	 * Return 0 if success, -1 if fail
	 */
	public static int addStock(String name, BigDecimal price) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            int stockID = 0;
            // get current max ID
            rset = stmt.executeQuery("SELECT * FROM STOCK_LIST ORDER BY ID desc;");
            if (rset.next()) {
            	stockID =  rset.getInt("ID");
            }

            // insert a new stock
            stockID += 1;
            stmt.execute("INSERT INTO STOCK_LIST (ID, NAME, PRICE) VALUES ("
                    +stockID+", \'"+name+"\', "+price.toPlainString()+");");
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
	}
	
	/*
	 * Alter a stock price according to stock_ID
	 * Input stock_ID, new_price
	 * Return 0 if success, -1 if fail
	 */
	public static int alterStockPrice(int stockID, BigDecimal new_price) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get stock with ID
            rset = stmt.executeQuery("SELECT * FROM STOCK_LIST WHERE ID = "+stockID+";");
            if (rset.next()) {
            	stmt.execute("UPDATE STOCK_LIST SET PRICE = "+new_price.toPlainString()+
                        " WHERE ID = "+stockID+";");
            }
            return 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
	}
        
    public static void main(String[] args) {
		//System.out.println(ManagerFunction.searchAllStockList());
    	//ManagerFunction.searchAllCustomer();
    	//ManagerFunction.searchAllLoanCustomer();
    	//ManagerFunction.searchTransactionToday();
    	//ManagerFunction.addStock("S&P 500", new BigDecimal(15));
    	//ManagerFunction.alterStockPrice(2, new BigDecimal(18));
	}
}
