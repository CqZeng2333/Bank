package backstage;

import connect_database.CustomerAddingFunction;
import connect_database.CustomerAlteringFunction;
import connect_database.ManagerFunction;

import java.math.BigDecimal;
import java.util.Scanner;

public class CheckingAccount extends Account {
    CheckingAccount(int id,Currency currency){
        super(id,currency);
        accountType="CHECKING";
    }
    public boolean initAccount(){
        System.out.println("Welcome! You will make checks here, starting today.");
        System.out.println("Opening this count will charge you 5 dollars. Make sure you have created the saving count and save at least 5 dollars in it.");
        System.out.println("Although you can use 3 types of currency in our bank(Dollar, RMB and Pound), you need to pay dollars this time.");
        if (currency.get("Dollar").compareTo(new BigDecimal(5)) < 0){
            System.out.println("You don't have 5 dollars!");
            System.out.println("Fail to open a checking account.");
            createTransaction("0","Dollar","Failed to open checking account.");
            return false;
        }else {
            //withdraw
            currency.sub("Dollar",5,"1");
            //add checking account in database
            CustomerAddingFunction.addCheckingAccount(customerID);
            //alter saving account, sub 5 dollars
            CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-5"));
            //alter manager
            ManagerFunction.alterManagerAccount("Dollar",new BigDecimal("5"));
            createTransaction("-5","Dollar","Open checking account.");
            return true;
        }
    }
    public void Menu(Customer customer){
        System.out.println("1. Make a check 2. Exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)||!Tool.in_range(num,1,9)||(Integer.parseInt(num)<1)||(Integer.parseInt(num)>2)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        if (number==1){
            checkMoney();
        }else return;
    }
    public boolean checkMoney(){
        System.out.println("Make a check will cost you 10 dollars.");
        boolean success= currency.sub("Dollar",(double)10,"1");
        if (success){
            createTransaction("-1","Dollar","Make a check.");
            //alter manager
            ManagerFunction.alterManagerAccount("Dollar",new BigDecimal("1"));
            //alter saving account
            CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-1"));
            //start writing a check
            System.out.println("Here's your deposit:");
            currency.print();
            System.out.println("Choose a currency type you want to trade: 1. Dollar 2. RMB 3. Pound");
            Scanner choice1 = new Scanner(System.in);
            String num1 = choice1.nextLine();
            while ((!Tool.is_number(num1)) || !Tool.in_range(num1, 1, 9) || (Integer.parseInt(num1) < 1) || (Integer.parseInt(num1) > 3)) {
                System.out.println("Invalid input. Input again.");
                num1 = choice1.nextLine();
            }
            int number1 = Integer.parseInt(num1);
            if (number1 == 1) {
                System.out.println("How much cash would this check value? Please make sure that you don't write checks bigger than your deposit.");
                Scanner money = new Scanner(System.in);
                String cash = money.nextLine();
                while (!Tool.is_number(cash)) {
                    System.out.println("Invalid input. Please input a number.");
                    cash = money.nextLine();
                }
                success = currency.sub("Dollar", Double.parseDouble(cash), "1");
                /*BigDecimal subsum=new BigDecimal(cash);
                subsum=subsum.multiply(new BigDecimal("0.02"));*/
                if (!success) {
                    createTransaction("0","Dollar","Failed to make a check.");
                }
                else {
                    createTransaction(cash,"Dollar","Successfully making a check.");
                    //alter saving account
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-"+cash));
                }
            }else if (number1==2){
                System.out.println("How much cash would this check value? Please make sure that you don't write checks bigger than your deposit.");
                Scanner money = new Scanner(System.in);
                String cash = money.nextLine();
                while (!Tool.is_number(cash)) {
                    System.out.println("Invalid input. Please input a number.");
                    cash = money.nextLine();
                }
                success = currency.sub("RMB", Double.parseDouble(cash), "1");
                /*BigDecimal subsum=new BigDecimal(cash);
                subsum=subsum.multiply(new BigDecimal("0.02"));*/
                if (!success) {
                    createTransaction("0","RMB","Failed to make a check.");
                }
                else {
                    createTransaction(cash,"RMB","Successfully making a check.");
                    //alter saving account
                    CustomerAlteringFunction.alterSavingAccount(customerID,"RMB",new BigDecimal("-"+cash));
                }
            }else {
                System.out.println("How much cash would this check value? Please make sure that you don't write checks bigger than your deposit.");
                Scanner money = new Scanner(System.in);
                String cash = money.nextLine();
                while (!Tool.is_number(cash)) {
                    System.out.println("Invalid input. Please input a number.");
                    cash = money.nextLine();
                }
                success = currency.sub("Pound", Double.parseDouble(cash), "1");
                /*BigDecimal subsum=new BigDecimal(cash);
                subsum=subsum.multiply(new BigDecimal("0.02"));*/
                if (!success) {
                    createTransaction("0", "Pound", "Failed to make a check.");
                } else {
                    createTransaction(cash, "Pound", "Successfully making a check.");
                    //alter saving account
                    CustomerAlteringFunction.alterSavingAccount(customerID, "Pound", new BigDecimal("-" + cash));
                }
            }
        }
        else {
            System.out.println("Failed to make a checking due to lack of dollars.");
            createTransaction("0","Dollar","Failed to make a check.");
        }
        return success;
    }

    public void createTransaction(String moneychange,String currencyType,String action){
        Time time=new Time();
        String str=time+ " Customer "+(customerID+1)+" in Checking account: "+action;
        Transaction transaction=new Transaction(str,accountType,currencyType,currency.get(currencyType).toString(),moneychange,time.toString());
        transactions.add(transaction);
        //transaction in database
        CustomerAddingFunction.addTransaction(customerID,accountType,currencyType,new BigDecimal(moneychange),currency.get(currencyType),time);

    }
}

 