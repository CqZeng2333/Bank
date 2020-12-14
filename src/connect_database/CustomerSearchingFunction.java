/*
 * database searching function related to customer
 */
package connect_database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerSearchingFunction {
	private final static Connection conn = Connector.getConn();
	
	/*
	 * Get the balance in saving account according to currency type
	 * Input customer ID, currency_type
	 * Return BigDecimal object of balance if success, null if not success(no customer or no saving account)
	 */
	public static BigDecimal searchSavingMoneyAmount(int customerID, String currency_type) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID and currency type
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM SAVING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset.previous();
                while (rset.next()) {
                    String c_type = rset.getString("CURRENCY_TYPE");
                    if (c_type.equals(currency_type)) {
                    	accountID = rset.getInt("ID");
                    	break;
                    }
                }
            }
            if (accountID == 0 || accountID == -1) return null;
            
            // get total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM SAVING_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            return old_amount;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get the balance in checking account according to currency type
	 * Input customer ID, currency_type
	 * Return BigDecimal object of balance if success, null if not success(no customer or no checking account)
	 */
	public static BigDecimal searchCheckingMoneyAmount(int customerID, String currency_type) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID and currency type
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM CHECKING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset.previous();
                while (rset.next()) {
                    String c_type = rset.getString("CURRENCY_TYPE");
                    if (c_type.equals(currency_type)) {
                    	accountID = rset.getInt("ID");
                    	break;
                    }
                }
            }
            if (accountID == 0 || accountID == -1) return null;
            
            // get total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM CHECKING_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            return old_amount;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get the total loan amount in loan account
	 * Input customer ID
	 * Return BigDecimal object of loan amount if success, null if not success(no customer or no loan account)
	 */
	public static BigDecimal searchLoanMoneyAmount(int customerID) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	accountID = rset.getInt("ID");
            }
            if (accountID == 0 || accountID == -1) return null;
            
            // get total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            return old_amount;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get the total loan list for customer
	 * Input customer ID
	 * Return List object, each one a String[] {loan_record_ID, loan_amount, collateral_name}
	 * Return a zero-size list if no loan
	 * Return null if not success(no customer or no loan account)
	 */
	public static List<String[]> searchLoanList(int customerID) {
		List<String[]> all_loan = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	accountID = rset.getInt("ID");
            }
            if (accountID == 0 || accountID == -1) return null;
            
            // get all loan record
            rset = stmt.executeQuery("SELECT * FROM LOAN, COLLATERAL WHERE LOAN.ACCOUNT_ID = "+accountID
            		+" AND LOAN.COLLATERAL_ID=COLLATERAL.ID;");
            if (rset.next()) {
            	rset.previous();
            	while (rset.next()) {
            		String[] record = new String[3];
            		record[0] = rset.getInt("LOAN.ID")+"";
            		record[1] = rset.getBigDecimal("LOAN.LOAN_AMOUNT").toPlainString();
            		record[2] = rset.getString("COLLATERAL.NAME");
            		all_loan.add(record);
            	}
            }
            return all_loan;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get the total balance in stock account
	 * Input customer ID
	 * Return BigDecimal object of balance if success, null if not success(no customer or no stock account)
	 */
	public static BigDecimal searchStockMoneyAmount(int customerID) {
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	accountID = rset.getInt("ID");
            }
            if (accountID == 0) return null;
            
            // get total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            return old_amount;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get the total stock list for customer
	 * Input customer ID
	 * Return List object, each one a String[] {stock_record_ID, stock_name, stock_original_price, stock_current_price, stock_holding_number}
	 * Return a zero-size list if no stock
	 * Return null if not success(no customer or no stock account)
	 */
	public static List<String[]> searchStockList(int customerID) {
		List<String[]> all_stock_records = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	accountID = rset.getInt("ID");
            }
            if (accountID == 0 || accountID == -1) return null;
            
            // get all stock record
            rset = stmt.executeQuery("SELECT * FROM STOCK_OWNERSHIP, STOCK_LIST WHERE STOCK_OWNERSHIP.ACCOUNT_ID = "+accountID
            		+" AND STOCK_OWNERSHIP.STOCK_ID=STOCK_LIST.ID;");
            if (rset.next()) {
            	rset.previous();
            	while (rset.next()) {
            		String[] record = new String[5];
            		record[0] = rset.getInt("STOCK_OWNERSHIP.ID")+"";
            		record[1] = rset.getString("STOCK_LIST.NAME");
            		record[2] = rset.getBigDecimal("STOCK_OWNERSHIP.PURCHASE_PRICE").toPlainString();
            		record[3] = rset.getBigDecimal("STOCK_LIST.PRICE").toPlainString();
            		record[4] = rset.getInt("STOCK_OWNERSHIP.HOLDINGS")+"";
            		//for (String s : record) System.out.print(s+" "); //1 NASDAQ 10.00 10.00 10 
            		//System.out.print("\n");
            		all_stock_records.add(record);
            	}
            }
            return all_stock_records;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	/*
	 * Get all the transaction list for customer
	 * Input customer ID
	 * Return List object, each one a String[] {account_type, currency_type, money_changed, current_balance, time}
	 * Return a zero-size list if no transaction
	 * Return null if not success(no customer)
	 */
	public static List<String[]> searchTransaction(int customerID) {
		List<String[]> all_stock_records = new ArrayList<>();
		try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get account ID according to customer ID
            rset = stmt.executeQuery("SELECT * FROM TRANSACTION WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset.previous();
            	while (rset.next()) {
            		String[] record = new String[5];
            		record[0] = rset.getString("ACCOUNT_TYPE");
            		record[1] = rset.getString("CURRENCY_TYPE");
            		record[2] = rset.getBigDecimal("MONEY_CHANGED").toPlainString();
            		record[3] = rset.getBigDecimal("CURRENT_BALANCE").toPlainString();
            		record[4] = rset.getTimestamp("Time").toString();
            		//for (String s : record) System.out.print(s+" ");
            		//System.out.print("\n"); //LOAN Dollar 80.00 240.00 2020-12-15 08:22:44.0
            		all_stock_records.add(record);
            	}
            }
            return all_stock_records;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
	}
	
	public static void main(String[] args) {
		//System.out.println(CustomerSearchingFunction.searchCheckingMoneyAmount(1, "RMB"));
		//System.out.println(CustomerSearchingFunction.searchLoanMoneyAmount(2));
		//System.out.println(CustomerSearchingFunction.searchStockMoneyAmount(1));
		//System.out.println(CustomerSearchingFunction.searchLoanList(2));
		//System.out.println(CustomerSearchingFunction.searchStockList(1));
		//System.out.println(CustomerSearchingFunction.searchTransaction(2));
	}
}
