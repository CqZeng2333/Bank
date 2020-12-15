/*
 * database adding function related to customer
 */
package connect_database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Map;
import java.util.HashMap;

import backstage.Collateral;
import backstage.Time;

public class CustomerAddingFunction {
    private final static Connection conn = Connector.getConn();
    
    /*
    Add a new customer into the database
    Input customer's name and password
    Return the ID of this customer; 0 if not success
    */
    public static int addCustomer(String name, String password) {
        int customerID = 0;
    	try {
            // get current max ID
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset = stmt.executeQuery("SELECT ID FROM CUSTOMER ORDER BY ID desc;");
            if (rset.next()) {
                customerID = rset.getInt("ID");
            }
            
            // insert a new customer
            customerID += 1;
            stmt.execute("INSERT INTO CUSTOMER (ID, NAME, PASSWORD, SAVING_ID, CHECKING_ID, LOAN_ID, STOCK_ID) VALUES ("
                    +customerID+", \'"+name+"\', \'"+password+"\', -1, -1, -1, -1);");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return customerID;
    }
    
    /*
    Add 3 saving accounts for 3 different kinds of currency for a customer into the database
    Input customer's ID
    Return the Map, with <key, value> as <currency_type, accountID>; <currency_type, 0> if not success
    */
    public static Map<String, Integer> addSavingAccount(int customerID) {
        int accountID = 0;
        String[] currencies = {"Dollar", "RMB", "Pound"};
        Map<String, Integer> accountIDs = new HashMap<>();
        for (String s : currencies) accountIDs.put(s, 0);
        
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // avoid to re-create new accounts if this customer already has saving accounts
            rset = stmt.executeQuery("SELECT * FROM SAVING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset.previous();
                while (rset.next()) {
                    String currency_type = rset.getString("CURRENCY_TYPE");
                    accountID = rset.getInt("ID");
                    for (String s : currencies) {
                        if (currency_type.equals(s)) {
                            accountIDs.replace(s, accountID++);
                        }
                    }
                }
                return accountIDs;
            }
            
            // get current max ID
            rset = stmt.executeQuery("SELECT ID FROM SAVING_ACCOUNT ORDER BY ID desc;");
            if (rset.next()) {
                accountID = rset.getInt("ID");
            }
            
            // insert 3 new account for 3 different languages
            stmt.execute("UPDATE CUSTOMER SET SAVING_ID = "+(accountID+1)+
                        " WHERE ID = "+customerID+";");
            for (String s : currencies) {
                accountID += 1;
                stmt.execute("INSERT INTO SAVING_ACCOUNT (ID, CUSTOMER_ID, CURRENCY_TYPE, MONEY_AMOUNT) VALUES ("
                        +accountID+", "+customerID+", \'"+s+"\', 0);");
                accountIDs.replace(s, accountID);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return accountIDs;
    }
    
    /*
    Add 3 checking accounts for 3 different kinds of currency for a customer into the database
    Input customer's ID
    Return the Map, with <key, value> as <currency_type, accountID>; <currency_type, 0> if not success
    */
    public static Map<String, Integer> addCheckingAccount(int customerID) {
        int accountID = 0;
        String[] currencies = {"Dollar", "RMB", "Pound"};
        Map<String, Integer> accountIDs = new HashMap<>();
        for (String s : currencies) accountIDs.put(s, 0);
        
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // avoid to re-create new accounts if this customer already has checking accounts
            rset = stmt.executeQuery("SELECT * FROM CHECKING_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
            	rset.previous();
                while (rset.next()) {
                    String currency_type = rset.getString("CURRENCY_TYPE");
                    accountID = rset.getInt("ID");
                    for (String s : currencies) {
                        if (currency_type.equals(s)) {
                            accountIDs.replace(s, accountID++);
                        }
                    }
                }
                return accountIDs;
            }
            
            // get current max ID
            rset = stmt.executeQuery("SELECT ID FROM CHECKING_ACCOUNT ORDER BY ID desc;");
            if (rset.next()) {
                accountID = rset.getInt("ID");
            }
            
            // insert 3 new account for 3 different languages
            stmt.execute("UPDATE CUSTOMER SET CHECKING_ID = "+(accountID+1)+
                        " WHERE ID = "+customerID+";");
            for (String s : currencies) {
                accountID += 1;
                stmt.execute("INSERT INTO CHECKING_ACCOUNT (ID, CUSTOMER_ID, CURRENCY_TYPE, MONEY_AMOUNT) VALUES ("
                        +accountID+", "+customerID+", \'"+s+"\', 0);");
                accountIDs.replace(s, accountID);
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return accountIDs;
    }
    

    /*
    Add a loan accounts for dollar for a customer into the database
    Input customer's ID
    Return the loan account ID; 0 if not success
    */
    public static int addLoanAccount(int customerID) {
        int accountID = 0;
        
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // avoid to re-create new account if this customer already has loan account
            rset = stmt.executeQuery("SELECT ID FROM LOAN_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
                accountID = rset.getInt("ID");
                return accountID;
            }
            
            // get current max ID
            rset = stmt.executeQuery("SELECT ID FROM LOAN_ACCOUNT ORDER BY ID desc;");
            if (rset.next()) {
                accountID = rset.getInt("ID");
            }
            
            // insert a new account
            accountID += 1;
            stmt.execute("INSERT INTO LOAN_ACCOUNT (ID, CUSTOMER_ID, MONEY_AMOUNT) VALUES ("
                    +accountID+", "+customerID+", 0);");
            stmt.execute("UPDATE CUSTOMER SET LOAN_ID = "+accountID+
                        " WHERE ID = "+customerID+";");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return accountID;
    }
    
    /*
    Add a stock accounts for dollar for a customer into the database
    Input customer's ID
    Return the stock account ID; 0 if not success
    */
    public static int addStockAccount(int customerID) {
        int accountID = 0;
        
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // avoid to re-create new account if this customer already has loan account
            rset = stmt.executeQuery("SELECT ID FROM STOCK_ACCOUNT WHERE CUSTOMER_ID = "+customerID+";");
            if (rset.next()) {
                accountID = rset.getInt("ID");
                return accountID;
            }
            
            // get current max ID
            rset = stmt.executeQuery("SELECT ID FROM STOCK_ACCOUNT ORDER BY ID desc;");
            if (rset.next()) {
                accountID = rset.getInt("ID");
            }
            
            // insert a new account
            accountID += 1;
            stmt.execute("INSERT INTO STOCK_ACCOUNT (ID, CUSTOMER_ID, MONEY_AMOUNT) VALUES ("
                    +accountID+", "+customerID+", 0);");
            stmt.execute("UPDATE CUSTOMER SET STOCK_ID = "+accountID+
                        " WHERE ID = "+customerID+";");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    	return accountID;
    }
    
    /*
    Add a loan record for dollar for a customer into the database
    Also add to the loan amount in the loan account
    Also create according transaction
    Input customer's ID, the money amount to borrow, the Collateral object
    Return 0 if success, -1 not success(no customer or no loan account)
    */
    public static int addLoan(int customerID, BigDecimal loan_amount, Collateral cltr) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get loan account ID if this customer already has loan account
            rset = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE ID = "+customerID+";");
            int accountID = 0;
            if (!rset.next()) return -1;
            else {
                accountID = rset.getInt("LOAN_ID");
                if (accountID == -1) return -1;
            }
            
            // get current max collateral ID
            int collateralID = 0;
            rset = stmt.executeQuery("SELECT * FROM COLLATERAL ORDER BY ID desc;");
            if (rset.next()) {
                collateralID = rset.getInt("ID");
            }
            
            // add a new collateral
            collateralID += 1;
            stmt.execute("INSERT INTO COLLATERAL (ID, NAME, ACCOUNT_ID, VALUE) VALUES ("
                    +collateralID+", \'"+cltr.getItem()+"\', "+accountID+", "+cltr.getPrice().toPlainString()+");");
            
            // get current max loan ID
            int loanID = 0;
            rset = stmt.executeQuery("SELECT * FROM LOAN ORDER BY ID desc;");
            if (rset.next()) {
                loanID = rset.getInt("ID");
            }
            
            // add a loan record
            loanID += 1;
            stmt.execute("INSERT INTO LOAN (ID, ACCOUNT_ID, COLLATERAL_ID, LOAN_AMOUNT) VALUES ("
                    +loanID+", "+accountID+", "+collateralID+", "+loan_amount.toPlainString()+");");
            
            // change total loan amount
            BigDecimal total_loan = null;
            rset = stmt.executeQuery("SELECT * FROM LOAN_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	total_loan = rset.getBigDecimal("MONEY_AMOUNT");
            	total_loan = total_loan.add(loan_amount);
            }
            stmt.execute("UPDATE LOAN_ACCOUNT SET MONEY_AMOUNT = "+total_loan.toPlainString()+
                    " WHERE ID = "+accountID+";");
            
            //CustomerAddingFunction.addTransaction(customerID, "LOAN", "Dollar", loan_amount, total_loan, new Time());
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    /*
    Add a stock owning record for dollar for a customer into the database
    Also minus the money from the stocking account
    Also create according transaction
    Input customer's ID, the stock ID to buy, the buy-in price for 1 stock, the buy-in number of stock
    Return 0 if success, -1 not success(no customer or no loan account)
    */
    public static int addStockOwnership(int customerID, int stockID, BigDecimal price, int holding_number) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            // get stock account ID if this customer already has stock account
            rset = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE ID = "+customerID+";");
            int accountID = 0;
            if (!rset.next()) return -1;
            else {
                accountID = rset.getInt("STOCK_ID");
                if (accountID == -1) return -1;
            }
            
            // get current max stock_ownership ID
            int ownershipID = 0;
            rset = stmt.executeQuery("SELECT * FROM STOCK_OWNERSHIP ORDER BY ID desc;");
            if (rset.next()) {
                ownershipID = rset.getInt("ID");
            }
            
            // add a stock record
            ownershipID += 1;
            stmt.execute("INSERT INTO STOCK_OWNERSHIP (ID, ACCOUNT_ID, STOCK_ID, PURCHASE_PRICE, HOLDINGS) VALUES ("
                    +ownershipID+", "+accountID+", "+stockID+", "+price.toPlainString()+", "+holding_number+");");
            
            BigDecimal total_Price = null;
            rset = stmt.executeQuery("SELECT * FROM STOCK_ACCOUNT WHERE ID = "+accountID+";");
            if (rset.next()) {
            	total_Price = rset.getBigDecimal("MONEY_AMOUNT");
            	total_Price = total_Price.subtract(price.multiply(new BigDecimal(holding_number)));
            }
            stmt.execute("UPDATE STOCK_ACCOUNT SET MONEY_AMOUNT = "+total_Price.toPlainString()+
                    " WHERE ID = "+accountID+";");
            
            CustomerAddingFunction.addTransaction(customerID, "STOCK", "Dollar", new BigDecimal(0).subtract(price.multiply(new BigDecimal(holding_number))), total_Price, new Time());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    /*
    Add a transaction record for a customer into the database
    Input customer's ID, the account_type(in {"SAVING", "CHECKING", "LOAN", "STOCK"}), 
        the currency_type(in {"Dollar", "RMB", "Pound"}), money_changed in amount, 
        current balance, and Time object. 
    Return 0 if success, -1 not success(no customer or no account or wrong account_type)
    */
    public static int addTransaction(int customerID, String account_type, String currency_type, BigDecimal money_changed, BigDecimal current_balance, Time time) {
        try {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rset;
            
            int accountID = 0;
            // get account ID if this customer already has such account
            String[] accountTypes = {"SAVING", "CHECKING", "LOAN", "STOCK"};
            for (String s : accountTypes) {
                if (account_type.equals(s)) {
                    rset = stmt.executeQuery("SELECT * FROM CUSTOMER WHERE ID = "+customerID+";");
                    
                    if (!rset.next()) return -1;
                    else {
                        accountID = rset.getInt(s+"_ID");
                        if (accountID == -1) return -1;
                    }
                }
            }
            if (accountID == 0) return -1;
            
            // get current max transaction ID
            int transactionID = 0;
            rset = stmt.executeQuery("SELECT * FROM TRANSACTION ORDER BY ID desc;");
            if (rset.next()) {
                transactionID = rset.getInt("ID");
            }
            
            // add a new transaction
            transactionID += 1;
            stmt.execute("INSERT INTO TRANSACTION (ID, CUSTOMER_ID, ACCOUNT_ID, ACCOUNT_TYPE, CURRENCY_TYPE, MONEY_CHANGED, CURRENT_BALANCE, TIME) VALUES ("
                    +transactionID+", "+customerID+", "+accountID+", \'"+account_type+"\', \'"+currency_type+"\', "
                    +money_changed.toPlainString()+", "+current_balance.toPlainString()+", \'"+time.toString()+"\');");
                    
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    public static void main(String[] args) {
        //CustomerAddingFunction.addCustomer("second_customer", "111111");
        //CustomerAddingFunction.addSavingAccount(2);
        //CustomerAddingFunction.addCheckingAccount(2);
        //CustomerAddingFunction.addLoanAccount(2);
        //CustomerAddingFunction.addStockAccount(2);
        //CustomerAddingFunction.addLoan(2, new BigDecimal(80), new Collateral("car", new BigDecimal(100), "Dollar"));
        //CustomerAddingFunction.addStockOwnership(1, 1, new BigDecimal(10), 10);
        //CustomerAddingFunction.addTransaction(1, "STOCK", "Dollar", new BigDecimal(-100), new BigDecimal(800), new Time());
    }
}
