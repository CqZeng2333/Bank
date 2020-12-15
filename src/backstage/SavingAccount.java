package backstage;

import connect_database.CustomerAddingFunction;
import connect_database.CustomerAlteringFunction;

import java.math.BigDecimal;
import java.util.Scanner;

public class SavingAccount extends Account {
    SavingAccount(int id,Currency currency){
         super(id,currency);
         accountType="SAVING";
    }
    public void initAccount(){
        System.out.println("Welcome! You will save and withdraw money here, starting today.");
        System.out.println("Opening this count will charge you 5 dollars.");
        System.out.println("You can use 3 types of currency in our bank(Dollar, RMB and Pound).");
        System.out.println("To create a checking account, you will have to save at least 5 dollars in our account.");
        save("Dollar");
        System.out.println("Automatically charge you $5!");
        currency.sub("Dollar",5,"1");
        createTransaction("-5","Dollar","Open Saving account.");
        //add saving account in database
        CustomerAddingFunction.addSavingAccount(customerID);
        CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-5"));
    }
    public void Menu(){
        System.out.println("1. Save money 2. Withdraw money 3. Exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        while (number<1||number>3){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
            number=Integer.parseInt(num);
        }
        if (number==1){
            System.out.println("1. Dollar 2. RMB 3. Pound");
            Scanner choice1=new Scanner(System.in);
            String num1=choice1.nextLine();
            while(!Tool.is_number(num1)){
                System.out.println("Invalid input. Input again.");
                num1=choice1.nextLine();
            }
            int number1=Integer.parseInt(num1);
            while (number1<1||number1>3){
                System.out.println("Invalid input. Input again.");
                num1=choice1.nextLine();
                number1=Integer.parseInt(num1);
            }
            if (number1==1){
                String cash=save("Dollar");
                CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal(cash));
                createTransaction(cash,"Dollar","Saving dollars $"+cash+".");
            }else if (number1==2){
                String cash=save("RMB");
                createTransaction(cash,"RMB","Saving RMBs ¥"+cash+".");
                CustomerAlteringFunction.alterSavingAccount(customerID,"RMB",new BigDecimal(cash));
            }else {
                String cash=save("Pound");
                createTransaction(cash,"Pound","Saving pounds ￡"+cash+".");
                CustomerAlteringFunction.alterSavingAccount(customerID,"Pound",new BigDecimal(cash));
            }
        }else if (number==2){
            System.out.println("1. Dollar 2. RMB 3. Pound");
            Scanner choice1=new Scanner(System.in);
            String num1=choice1.nextLine();
            while(!Tool.is_number(num1)){
                System.out.println("Invalid input. Input again.");
                num1=choice1.nextLine();
            }
            int number1=Integer.parseInt(num1);
            while (number1<1||number1>3){
                System.out.println("Invalid input. Input again.");
                num1=choice1.nextLine();
                number1=Integer.parseInt(num1);
            }
            if (number1==1){
                String cash=withdraw("Dollar");
                if (cash.equals("")){
                    createTransaction("0","Dollar","Failed to withdraw dollars.");
                }else {
                createTransaction("-"+cash,"Dollar","Withdraw dollars $"+cash+".");
                CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-"+cash));}
            }else if (number1==2){
                String cash=withdraw("RMB");
                if (cash.equals("")){
                    createTransaction("0","RMB","Failed to withdraw RMBs.");
                }else {
                    createTransaction("-"+cash,"RMB","Withdraw RMBs ¥"+cash+".");
                    CustomerAlteringFunction.alterSavingAccount(customerID,"RMB",new BigDecimal("-"+cash));}
            }else {
                String cash=withdraw("Pound");
                if (cash.equals("")){
                    createTransaction("0","Pound","Failed to withdraw pounds.");
                }else {
                    createTransaction("-"+cash,"Pound","Withdraw pounds ￡"+cash+".");
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Pound",new BigDecimal("-"+cash));}
            }
        }else {
            return;
        }
    }
//String str,String accountType,String currencyType,String moneychange,String currentBalance,Time time
    public void createTransaction(String moneychange,String currencyType,String action){
        Time time=new Time();
        String str=time+ " Customer "+(customerID+1)+" in Saving account: "+action;
        Transaction transaction=new Transaction(str,accountType,currencyType,currency.get(currencyType).toString(),moneychange,time.toString());
        transactions.add(transaction);
        //transaction in database
        //CustomerAddingFunction.addTransaction(customerID,accountType,currencyType,new BigDecimal(moneychange),Account.currency.get(currencyType),time);
    }
}
