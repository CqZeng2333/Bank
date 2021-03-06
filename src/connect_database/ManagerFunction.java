/*
 * database function for manager
 */
package connect_database;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
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
	 * Get the money amount in manager account
	 * Input currency type{"Dollar", "RMB", "Pound"}
	 * Return money_amount if success
	 * Return null if fail
	 */
	public static BigDecimal searchManagerAccount(String currency_type) {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rset;

			rset = stmt.executeQuery("SELECT * FROM MANAGER_ACCOUNT;");
			if (rset.next()) {
				if (rset.getString("CURRENCY_TYPE").equals(currency_type)) return rset.getBigDecimal("MONEY_AMOUNT");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return null;
		}
		return null;
	}
	
	/*
	 * Alter the balance with changedAmount in manager account according to currency type
	 * Input currency_type, changed money amount
	 * Return 0 if success, -1 not success(no customer or no saving account)
	 */
	public static int alterManagerAccount(String currency_type, BigDecimal changedAmount) {
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rset;

			// change total currency amount
			BigDecimal old_amount = null;
			rset = stmt.executeQuery("SELECT * FROM MANAGER_ACCOUNT WHERE CURRENCY_TYPE = \'"+currency_type+"\';");
			if (rset.next()) {
				old_amount = rset.getBigDecimal("MONEY_AMOUNT");
				stmt.execute("UPDATE MANAGER_ACCOUNT SET MONEY_AMOUNT = "+old_amount.add(changedAmount).toPlainString()+
						" WHERE CURRENCY_TYPE = \'"+currency_type+"\';");
			}


			//CustomerAddingFunction.addTransaction(customerID, "SAVING", currency_type, newAmount.subtract(old_amount), newAmount, new Time());

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return 0;
	}

	/*
	 * Get all the customer list for manager
	 * Return List object, each one a String[] {customer_ID, customer_name}
	 * Return a zero-size list if no customer record
	 * Return null if error
	 */
	public static List<String[]> searchAllCustomer() {
		List<String[]> all_customers = new ArrayList<>();
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rset;
			
			// get all customer record
			rset = stmt.executeQuery("SELECT * FROM CUSTOMER;");
			if (rset.next()) {
				rset.previous();
				while (rset.next()) {
					String[] record = new String[2];
					record[0] = rset.getInt("ID")+"";
					record[1] = rset.getString("NAME");
					//for (String str : record) System.out.print(str+" "); // 1 first_customer SAVING Dollar 3600.00
					//System.out.print("\n");
					all_customers.add(record);
				}
			}
			return all_customers;

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	
	/*
	 * Get all the accounts of customers for manager
	 * Return List object, each one a String[] {customer_ID, customer_name, account_type, currency_type, money_amount}
	 * Return a zero-size list if no record
	 * Return null if error
	 */
	public static List<String[]> searchAllCustomerAccount() {
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
					//for (String str : record) System.out.print(str+" "); // 1 first_customer SAVING Dollar -200.00 3800.00 2020-12-15 08:19:43.0
					//System.out.print("\n");
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
	
	/*
	 * Update daily 0.01% the interest paid to high-balance customers
	 * and 0.1% interest charged to loan accounts
	 */
	public static int updateDailyInterest() {
		try {
			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			ResultSet rset1;

			// count days between last update day and now
			// and update the last_update_day to now
			int sum_day = 0; // days between last update day and now
			rset1 = stmt1.executeQuery("SELECT * FROM MANAGER;");
			if (rset1.next()) {
				String last_update_date = rset1.getDate("LAST_UPDATE_DATE").toString();
				Period period = Period.between(LocalDate.parse(last_update_date), LocalDate.now());
				sum_day = period.getDays() + period.getMonths() * 30 + period.getYears() * 365;
				stmt1.execute("UPDATE MANAGER SET LAST_UPDATE_DATE = \'"+LocalDate.now().toString()+"\';");
			}
			
			for (int i = 0; i < sum_day; i++) {
				// update interest for saving
				rset1 = stmt1.executeQuery("SELECT * FROM SAVING_ACCOUNT WHERE MONEY_AMOUNT >= 5000;");
				while (rset1.next()) {
					int accountID = rset1.getInt("ID");
					BigDecimal old_amount = rset1.getBigDecimal("MONEY_AMOUNT");
					stmt2.execute("UPDATE SAVING_ACCOUNT SET MONEY_AMOUNT = "+old_amount.multiply(new BigDecimal(1.0001)).toPlainString()
							+" WHERE ID = "+accountID+";");
				}
				
				// update interest for loan
				rset1 = stmt1.executeQuery("SELECT * FROM LOAN;");
				while (rset1.next()) {
					int loanID = rset1.getInt("ID");
					BigDecimal old_amount = rset1.getBigDecimal("LOAN_AMOUNT");
					stmt2.execute("UPDATE LOAN SET LOAN_AMOUNT = "+old_amount.multiply(new BigDecimal(1.001)).toPlainString()
							+" WHERE ID = "+loanID+";");
				}
				rset1 = stmt1.executeQuery("SELECT * FROM LOAN_ACCOUNT;");
				while (rset1.next()) {
					int accountID = rset1.getInt("ID");
					BigDecimal old_amount = rset1.getBigDecimal("MONEY_AMOUNT");
					stmt2.execute("UPDATE LOAN_ACCOUNT SET MONEY_AMOUNT = "+old_amount.multiply(new BigDecimal(1.001)).toPlainString()
							+" WHERE ID = "+accountID+";");
				}
			}
			return 0;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return -1;
		}
	}

	/*
	public static void main(String[] args) {
		//System.out.println(ManagerFunction.searchAllStockList());
		//ManagerFunction.searchAllCustomer();
		//ManagerFunction.searchAllLoanCustomer();
		//ManagerFunction.searchTransactionToday();
		//ManagerFunction.addStock("S&P 500", new BigDecimal(15));
		//ManagerFunction.alterStockPrice(2, new BigDecimal(18));
		//ManagerFunction.alterManagerAccount("Dollar", new BigDecimal(100));
		//System.out.println(ManagerFunction.searchManagerAccount("Dollar"));
		//ManagerFunction.updateDailyInterest();
	}
	*/
}