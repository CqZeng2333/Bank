/*
 * database deleting function related to customer
 */
package connect_database;

import java.math.BigDecimal;
import java.sql.*;

import backstage.Time;

public class CustomerDeletingFunction {
	private final static Connection conn = Connector.getConn();
	
	/*
	 * Delete the saving account for customer
	 * Input customer ID
	 * Return 0 if success, -1 not success(no customer or no saving account)
	 */
	public static int deleteSavingAccount(int customerID) { 
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // delete accounts according to customer ID
            rset = stmt.executeQuery("SELECT * FROM SAVING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
                stmt.execute("DELETE FROM SAVING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
                stmt.execute("UPDATE CUSTOMER SET SAVING_ID = -1 WHERE ID = "+customerID+";");
                return 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
	}
	
	/*
	 * Delete the checking account for customer
	 * Input customer ID
	 * Return 0 if success, -1 not success(no customer or no checking account)
	 */
	public static int deleteCheckingAccount(int customerID) { 
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // delete accounts according to customer ID
            rset = stmt.executeQuery("SELECT * FROM CHECKING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
                stmt.execute("DELETE FROM CHECKING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
                stmt.execute("UPDATE CUSTOMER SET CHECKING_ID = -1 WHERE ID = "+customerID+";");
                return 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
	}
	
	/*
	 * Delete the loan account without pay back the loan for customer
	 * All the collaterals remain in the bank
	 * Input customer ID
	 * Return 0 if success, -1 not success(no customer or no checking account)
	 */
	public static int deleteLoanAccountWithoutPayback(int customerID) { 
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // delete accounts according to customer ID
            rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            int accountID = 0;
            if (rset.next()) {
            	accountID = rset.getInt("ID");
            	stmt.execute("DELETE FROM LOAN WHERE ACCOUNT_ID = "+accountID+";");
                stmt.execute("DELETE FROM LOAN_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
                stmt.execute("UPDATE CUSTOMER SET LOAN_ID = -1 WHERE ID = "+customerID+";");
                return 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
	}
	
	/*
	 * Delete the loan record with paying back the loan for customer
	 * The corresponding collateral is removed from the bank
	 * Also add the corresponding transaction
	 * Input customer ID, loan_record_id
	 * Return 0 if success, -1 not success(no customer or no loan account)
	 */
	public static int deleteOneLoan(int customerID, int loan_record_id) { 
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // delete accounts according to customer ID
            rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset = stmt.executeQuery("SELECT * FROM LOAN WHERE ID = "+loan_record_id+";");
            	if (rset.next()) {
            		int collateralID = rset.getInt("COLLATERAL_ID");
            		BigDecimal loan_amount = rset.getBigDecimal("LOAN_AMOUNT");
            		stmt.execute("DELETE FROM COLLATERAL WHERE ID = "+collateralID+";");
	            	stmt.execute("DELETE FROM LOAN WHERE ID = "+loan_record_id+";");
	            	
	            	rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE CUSTOMER_ID  = "+customerID+";");
	            	rset.next();
	            	BigDecimal total_amount = rset.getBigDecimal("MONEY_AMOUNT");
	                stmt.execute("UPDATE LOAN_ACCOUNT SET MONEY_AMOUNT = "+total_amount.subtract(loan_amount).toPlainString()+" WHERE ID = "+customerID+";");
	                //CustomerAddingFunction.addTransaction(customerID, "LOAN", "Dollar", new BigDecimal(0).subtract(loan_amount), total_amount.subtract(loan_amount), new Time());
            	}
                return 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
	}
	
	/*
	 * Delete one stock record for customer
	 * The corresponding money is added to the stock account
	 * Also add the corresponding transaction
	 * Input customer ID, stock_record_id
	 * Return 0 if success, -1 not success(no customer or no loan account)
	 */
	public static int deleteOneStock(int customerID, int stock_record_id) { 
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // delete accounts according to customer ID
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset = stmt.executeQuery("SELECT * FROM STOCK_OWNERSHIP WHERE ID = "+stock_record_id+";");
            	if (rset.next()) {
            		int stock_ID = rset.getInt("STOCK_ID");
            		int holdings = rset.getInt("HOLDINGS");
            		
            		rset = stmt.executeQuery("SELECT * FROM STOCK_LIST WHERE ID = "+stock_ID+";");
            		rset.next();
            		BigDecimal stock_current_price = rset.getBigDecimal("PRICE");
            		
	            	rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE CUSTOMER_ID  = "+customerID+";");
	            	rset.next();
	            	BigDecimal total_amount = rset.getBigDecimal("MONEY_AMOUNT");
	            	
	            	stmt.execute("DELETE FROM STOCK_OWNERSHIP WHERE ID = "+stock_record_id+";");
	                stmt.execute("UPDATE STOCK_ACCOUNT SET MONEY_AMOUNT = "+total_amount.add(stock_current_price.multiply(new BigDecimal(holdings))).toPlainString()+" WHERE ID = "+customerID+";");
	                CustomerAddingFunction.addTransaction(customerID, "STOCK", "Dollar", stock_current_price.multiply(new BigDecimal(holdings)), total_amount.add(stock_current_price.multiply(new BigDecimal(holdings))), new Time());
            	}
                return 0;
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
	}
	
	public static void main(String[] args) {
		//CustomerDeletingFunction.deleteSavingAccount(2);
		//CustomerDeletingFunction.deleteCheckingAccount(2);
		//CustomerDeletingFunction.deleteOneLoan(2, 1);
		//CustomerDeletingFunction.deleteOneStock(1, 1);
	}
}
