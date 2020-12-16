package backstage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Account {

    String accountType;
    int accountID;
    ArrayList<Transaction> transactions = new ArrayList<>();
    ArrayList<Collateral> collaterals = new ArrayList<>();
    int customerID;
    Currency currency;

    Account(int id, Currency currency) {
        customerID = id;
        this.currency = currency;
    }

    Account(int id, Currency currency, ArrayList<Collateral> collaterals) {
        customerID = id;
        this.currency = currency;
        this.collaterals = collaterals;
    }

    public String toString() {
        return accountType;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCurrency() {
        return currency.toString();
    }

    public int getAccountID() {
        return accountID;
    }

    public String save(String type,BigDecimal least) {
        System.out.println("How much would you save this time?");
        Scanner money = new Scanner(System.in);
        String cash = money.nextLine();
        while (!Tool.is_number(cash)) {
            System.out.println("Invalid input. Please input a number.");
            cash = money.nextLine();
        }
        if (least.compareTo(new BigDecimal(cash))<=0){
            currency.add(type, Double.parseDouble(cash), "1");
            return cash;
        }else {
            return "";
        }

    }
    public String save(String type) {
        System.out.println("How much would you save this time?");
        Scanner money = new Scanner(System.in);
        String cash = money.nextLine();
        while (!Tool.is_number(cash)) {
            System.out.println("Invalid input. Please input a number.");
            cash = money.nextLine();
        }
        currency.add(type, Double.parseDouble(cash), "1");
        return cash;
        }


    public String save(String type, String cash) {
        //assert cash is a number
        currency.add(type, Double.parseDouble(cash), "1");
        return cash;
    }

    public String[] withdraw(String type) {
        String[] str = new String[2];
        str[0] = "";
        str[1] = "";
        System.out.println("How much cash would you take this time?");
        System.out.println(
                "Please take care that we will charge you 2% service charges.");
        System.out.println("Here's your deposit:");
        currency.print();
        Scanner money = new Scanner(System.in);
        String cash = money.nextLine();
        while (!Tool.is_number(cash)) {
            System.out.println("Invalid input. Please input a number.");
            cash = money.nextLine();
        }
        boolean success = currency.sub(type, Double.parseDouble(cash),
                                       "1.02");
        BigDecimal subsum = new BigDecimal(cash);
        subsum = subsum.multiply(new BigDecimal("0.02"));
        if (!success) {
            return str;
        }
        else {
            str[0] = cash;
            str[1] = subsum.toString();
            return str;
        }
    }

    public String[] withdraw(String type, String cash) {
        //assert cash is a number
        String[] str = new String[2];
        str[0] = "";
        str[1] = "";
        Scanner money = new Scanner(System.in);
        boolean success = currency.sub(type, Double.parseDouble(cash),
                                       "1.02");
        BigDecimal subsum = new BigDecimal(cash);
        subsum = subsum.multiply(new BigDecimal("0.02"));
        if (!success) {
            return str;
        }
        else {
            str[0] = cash;
            str[1] = subsum.toString();
            return str;
        }
    }

    public abstract void createTransaction(String moneychange, String type,
                                           String action);
}
