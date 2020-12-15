package backstage;

import connect_database.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LoanAccount extends Account {
    public static final String loanRate="0.9";

    public BigDecimal getTotalloan() {
        return totalloan;
    }

    public void setTotalloan(BigDecimal totalloan) {
        this.totalloan = totalloan;
    }

    BigDecimal totalloan=new BigDecimal("0");
    ArrayList<Collateral> collaterals=new ArrayList<>();
    LoanAccount(int id,Currency currency,ArrayList<Collateral> collaterals){
        super(id,currency,collaterals);
        accountType="LOAN";
    }
    LoanAccount(int id,Currency currency){
        super(id,currency);
        accountType="LOAN";
    }
    public String toString(){
        String str="Collaterals: "+totalloan;
        return str;
    }
    public boolean initAccount(){
        System.out.println("Welcome! You will be applying loans and pay for collterals here, starting today.");
        System.out.println("Opening this count will charge you 8 dollars. Make sure you have created the saving count and save at least 8 dollars in it.");
        System.out.println("LOAN account only permits dollars.");
        if (currency.get("Dollar").compareTo(new BigDecimal(8)) < 0){
            System.out.println("You don't have 8 dollars!");
            System.out.println("Fail to open a loan account.");
            createTransaction("0","Dollar","Failed to open loan account.");
            return false;
        }else {
            //withdraw
            currency.sub("Dollar",8,"1");
            //alter saving account
            CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-8"));
            //new loan account in database
            CustomerAddingFunction.addLoanAccount(customerID);
            //alter manager
            ManagerFunction.alterManagerAccount("Dollar",new BigDecimal("8"));
            createTransaction("-8","Dollar","Open loan account.");
            return true;
        }
    }
    public boolean is_empty(){
        if (collaterals.size()==0){
            return true;
        }else return false;
    }
    public String printCollaterals(){
        String str="\n";
        for(int i=0;i<collaterals.size();i++){
            str+=collaterals.get(i);
        }
        return str;
    }
    public void Menu(){
        System.out.println("1. apply for a loan 2. check loans 3. pay for loans 4. Exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)||!Tool.in_range(num,1,9)||(Integer.parseInt(num)<1)||(Integer.parseInt(num)>4)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        if (number==1){
            applyLoan();
        }else if (number==2){
            checkLoans();
        }else if (number==3){
            payLoan();

        }else return;
    }
    public void payLoan(){
        List<String[]> loanlist= CustomerSearchingFunction.searchLoanList(customerID);
        assert loanlist != null;
        if (loanlist.size()==0){
            System.out.println("You don't have any loans yet.");
            return;
        }else {
            //{loan_record_ID, loan_amount, collateral_name}
            for (String[] strings : loanlist) {
                System.out.println(Arrays.toString(strings));
            }
            System.out.println("Please enter the name of the collteral you want to redeem.");
            Scanner item=new Scanner(System.in);
            String name=item.nextLine();
            int index=-1;
            boolean flag=false;
            for (int i=0;i<loanlist.size();i++) {
                if (name.equals(loanlist.get(i)[2])){
                    index=i;
                    flag=true;
                    break;
                }}
            if (!flag){
                System.out.println(name+" is not in your collateral list!");
                return;}

            if(index>=0){
                BigDecimal price=new BigDecimal(loanlist.get(index)[1]);
                boolean success= currency.sub("Dollar",price.doubleValue(),"1");
                if (success){
                    System.out.println("Now you have your "+loanlist.get(index)[2]+" again!");
                    System.out.println("Thank you for using our bank!");
                    createTransaction("-"+loanlist.get(index)[1],"Dollar","Pay for the chosen collateral "+loanlist.get(index)[2]+".");
                    //alter manager
                    ManagerFunction.alterManagerAccount("Dollar",price);
                    //alter saving account
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-"+loanlist.get(index)[1]));
                    CustomerDeletingFunction.deleteOneLoan(customerID,Integer.parseInt(loanlist.get(index)[0]));
                }else {
                    createTransaction("0","Dollar","Failed to pay for the chosen collateral. "+loanlist.get(index)[2]+".");
                }
            }
        }
        }


    public void applyLoan(){
        System.out.println("Input the name and price you have assessed here, we will loan you 90% of your collateral.");
        Scanner colla=new Scanner(System.in);
        System.out.println("The name of your collateral:");
        String item=colla.nextLine();
        String name=item;
        boolean alreadyExist=true;
        if (collaterals.size()>0){
            while(alreadyExist){
                for (int i=0;i<collaterals.size();i++){
                    if (collaterals.get(i).getItem().equals(name)){
                        System.out.println("You cannot have items with the same name, please input again.");
                        System.out.println("For example, you already pledged a 'car'. You want to pledge a car now, you can name it 'car1'.");
                        name=colla.nextLine();
                        break;
                    }alreadyExist=false;} }}
        System.out.println("Please make sure that your collateral is over $250 or we won't make a loan for you.");
        System.out.println("The price of your collateral($):");
        item = colla.nextLine();
        while (!Tool.is_number(item)) {
            System.out.println("Invalid input. Input again.");
            item = colla.nextLine();
        }
        double priceD = Double.parseDouble(item);
        if (priceD < 250) {
            System.out.println("Your item is too cheap and cannot be a collateral in our bank, sorry.");
            //createTransaction("0","Dollar","Failed to loan cause the collateral is too cheap.");
        } else {
            System.out.println("Ok! We will loan you 90% of this collateral.");
            currency.add("Dollar", priceD, loanRate);
            BigDecimal p=new BigDecimal(item);
            p=p.multiply(new BigDecimal("0.9"));
            Collateral collateral = new Collateral(name,p);
            collaterals.add(collateral);
            createTransaction(p.toString(),"Dollar","Apply for a loan. " + collateral);
            //alter saving account
            CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",p);
            //alter manager
            ManagerFunction.alterManagerAccount("Dollar",new BigDecimal("-"+p.toString()));
            //new loan in database
            CustomerAddingFunction.addLoan(customerID,p,collateral);
        }
    }
    public void checkLoans(){
        List<String[]> loanlist= CustomerSearchingFunction.searchLoanList(customerID);
        assert loanlist != null;
        if (loanlist.size()==0){
              System.out.println("You don't have any loans yet.");
          }else {
              //createTransaction("0","Dollar","Check all the collterals.");
            for (String[] strings : loanlist) {
                System.out.println(Arrays.toString(strings));
            }
          }
    }
    public void createTransaction(String moneychange,String currencyType,String action){
        Time time=new Time();
        String str=time+ " Customer "+(customerID+1)+" in LOAN account: "+action;
        Transaction transaction=new Transaction(str,accountType,currencyType,currency.get(currencyType).toString(),moneychange,time.toString());
        transactions.add(transaction);
        //transaction in database
        CustomerAddingFunction.addTransaction(customerID,accountType,currencyType,new BigDecimal(moneychange),currency.get(currencyType),time);
    }
}
