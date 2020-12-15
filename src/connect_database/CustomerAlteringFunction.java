/*
 * database altering function related to customer
 */
package connect_database;

import java.math.BigDecimal;
import java.sql.*;

import backstage.Time;

public class CustomerAlteringFunction {
    private final static Connection conn = Connector.getConn();

    /*
     * Alter the balance with changedAmount in saving account according to currency type
     * Input customer ID, currency_type, new money amount
     * Return 0 if success, -1 not success(no customer or no saving account)
     */
    public static int alterSavingAccount(int customerID, String currency_type, BigDecimal changedAmount) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;

            // get account ID according to currency type
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
            if (accountID == 0 || accountID == -1) return -1;

            // change total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM SAVING_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
                old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            stmt.execute("UPDATE SAVING_ACCOUNT SET MONEY_AMOUNT = "+old_amount.add(changedAmount).toPlainString()+
                    " WHERE ID = "+accountID+";");

            //CustomerAddingFunction.addTransaction(customerID, "SAVING", currency_type, newAmount.subtract(old_amount), newAmount, new Time());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

	/*
	 * Alter the balance in checking account according to currency type
	 * Also create according transaction
	 * Input customer ID, currency_type, new money amount
	 * Return 0 if success, -1 not success(no customer or no checking account)

	public static int alterCheckingAccount(int customerID, String currency_type, BigDecimal newAmount) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;

            // get account ID according to currency type
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
            if (accountID == 0 || accountID == -1) return -1;

            // change total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM CHECKING_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            stmt.execute("UPDATE CHECKING_ACCOUNT SET MONEY_AMOUNT = "+newAmount.toPlainString()+
                    " WHERE ID = "+accountID+";");

            //CustomerAddingFunction.addTransaction(customerID, "CHECKING", currency_type, newAmount.subtract(old_amount), newAmount, new Time());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
	}
	*/

    /*
     * Alter the balance in stock account according to currency type
     * Also create according transaction
     * Input customer ID, new money amount
     * Return 0 if success, -1 not success(no customer or no stock account)
     */
    public static int alterStockAccount(int customerID, BigDecimal newAmount) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;

            // get account ID according to currency type
            int accountID = 0;
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
                accountID = rset.getInt("ID");
            }
            if (accountID == 0 || accountID == -1) return -1;

            // change total currency amount
            BigDecimal old_amount = null;
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
                old_amount = rset.getBigDecimal("MONEY_AMOUNT");
            }
            stmt.execute("UPDATE STOCK_ACCOUNT SET MONEY_AMOUNT = "+newAmount.toPlainString()+
                    " WHERE ID = "+accountID+";");

            CustomerAddingFunction.addTransaction(customerID, "STOCK", "Dollar", newAmount.subtract(old_amount), newAmount, new Time());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static void main(String[] args) {
        //CustomerAlteringFunction.alterSavingAccount(1, "Dollar", new BigDecimal(3600));
        //CustomerAlteringFunction.alterCheckingAccount(1, "Dollar", new BigDecimal(100));
        //CustomerAlteringFunction.alterStockAccount(1, new BigDecimal(900));
    }
}