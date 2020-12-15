package backstage;

import connect_database.CustomerAddingFunction;
import connect_database.CustomerSearchingFunction;
import connect_database.ManagerFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Bank {
    static ArrayList<Customer> customers = new ArrayList<>();
    static Manager bankManager;
    static boolean end=true;
    static boolean userend=true;
    public static void main(String[] args){
        System.out.println("Welcome to the Bank!");
        getExistUser();
        while (end){
            userMenu();}
    }
    public static void getExistUser(){
        List<String[]> existcustomers = ManagerFunction.searchAllCustomer();


        //id loop
        for (int i = 0; i < Objects.requireNonNull(existcustomers).size(); i++) {
                Customer customer = new Customer(Integer.parseInt(existcustomers.get(i)[0]));
                customer.name = existcustomers.get(i)[1];
                customers.add(customer);
        }
        if (customers.size()>0){
            //currency loop
            for (int i = 0; i < customers.size(); i++) {
                if (CustomerSearchingFunction.searchSavingMoneyAmount(customers.get(i).id,"Dollar")!=null){
                    customers.get(i).currency.getMoney().put("Dollar",CustomerSearchingFunction.searchSavingMoneyAmount(customers.get(i).id,"Dollar"));
                }
                if (CustomerSearchingFunction.searchSavingMoneyAmount(customers.get(i).id,"RMB")!=null){
                    customers.get(i).currency.getMoney().put("RMB",CustomerSearchingFunction.searchSavingMoneyAmount(customers.get(i).id,"RMB"));
                }
                if (CustomerSearchingFunction.searchSavingMoneyAmount(customers.get(i).id,"Pound")!=null){
                    customers.get(i).currency.getMoney().put("Pound",CustomerSearchingFunction.searchSavingMoneyAmount(customers.get(i).id,"Pound"));
                }
/*                int index = -1;
                for (int k = 0; k < customers.get(i).accounts.size(); k++) {
                    if (customers.get(i).accounts.get(k).accountType.equals("SAVING")) {
                        index = k;
                        break;
                    }
                }
                for (int j = 0; j < Objects.requireNonNull(existcustomers).size(); j++) {
                    if (customers.get(i).id == Integer.parseInt(existcustomers.get(j)[0])) {
                        if (index >= 0) {
                            if (existcustomers.get(j)[2].equals("SAVING") && existcustomers.get(j)[3].equals("Dollar")) {
                                customers.get(i).accounts.get(index).currency.getMoney().put("Dollar", new BigDecimal(existcustomers.get(j)[4]));
                            }
                            if (existcustomers.get(j)[2].equals("SAVING") && existcustomers.get(j)[3].equals("RMB")) {
                                customers.get(i).accounts.get(index).currency.getMoney().put("RMB", new BigDecimal(existcustomers.get(j)[4]));
                            }
                            if (existcustomers.get(j)[2].equals("SAVING") && existcustomers.get(j)[3].equals("Pound")) {
                                customers.get(i).accounts.get(index).currency.getMoney().put("Pound", new BigDecimal(existcustomers.get(j)[4]));
                            }
                        }
                    }
                }*/
            }
        //account loop
            List<String[]> existaccounts=ManagerFunction.searchAllCustomerAccount();
            //{customer_ID, customer_name, account_type, currency_type, money_amount}
        for (int i = 0; i < customers.size(); i++) {
            int sc = 0, cc = 0, lc = 0;
            for (int j = 0; j < Objects.requireNonNull(existaccounts).size(); j++) {
                if (customers.get(i).id == Integer.parseInt(existaccounts.get(j)[0])) {
                    if (existaccounts.get(j)[2].equals("SAVING") && sc == 0) {
                        SavingAccount savingAccount = new SavingAccount(customers.get(i).id, customers.get(i).currency);
                        sc = 1;
                        customers.get(i).accounts.add(savingAccount);
                    }
                    if (existaccounts.get(j)[2].equals("CHECKING") && cc == 0) {
                        CheckingAccount checkingAccount = new CheckingAccount(customers.get(i).id, customers.get(i).currency);
                        cc = 1;
                        customers.get(i).accounts.add(checkingAccount);
                    }
                    if (existaccounts.get(j)[2].equals("LOAN") && lc == 0) {
                        LoanAccount loanAccount = new LoanAccount(customers.get(i).id, customers.get(i).currency);
                        lc = 1;
                        customers.get(i).accounts.add(loanAccount);
                    }
                }
            }
        }

        //loan amount loop
        List<String[]> loanCustomer = ManagerFunction.searchAllLoanCustomer();
        //{customer_ID, customer_name, "LOAN", "Dollar", money_amount}
        for (int i = 0; i < customers.size(); i++) {
            int index = -1;
            for (int k = 0; k < customers.get(i).accounts.size(); k++) {
                if (customers.get(i).accounts.get(k).accountType.equals("LOAN")) {
                    index = k;
                    break;
                }
            }
            if (index>=0) {
                for (int j = 0; j < Objects.requireNonNull(loanCustomer).size(); j++) {
                    if (customers.get(i).id == Integer.parseInt(loanCustomer.get(j)[0])) {
                        ((LoanAccount) customers.get(i).accounts.get(index)).setTotalloan(new BigDecimal(loanCustomer.get(j)[4]));
                    }
                }
            }
        }
        //loan collaterals loop
        for (int i = 0; i < customers.size(); i++) {
            List<String[]> collaterallist=CustomerSearchingFunction.searchLoanList(customers.get(i).id);
            // {loan_record_ID, loan_amount, collateral_name}
            if (collaterallist!=null&&collaterallist.size()>0){
                for (int j=0;j<collaterallist.size();j++){
                    //BigDecimal price=new BigDecimal(collaterallist.get(j)[1]);
                    //price=price.divide(new BigDecimal("0.9"));
                    Collateral collateral=new Collateral(collaterallist.get(j)[2]);
                    customers.get(i).collaterals.add(collateral);
                }
                int index = -1;
                for (int k = 0; k < customers.get(i).accounts.size(); k++) {
                    if (customers.get(i).accounts.get(k).accountType.equals("LOAN")) {
                        index = k;
                        break;
                    }
                }
                if (index>=0){
                    customers.get(i).accounts.get(index).collaterals=customers.get(i).collaterals;
                }
            }
        }
        //transactions loop
        //{account_type, currency_type, money_changed, current_balance, time}
        for (int i = 0; i < customers.size(); i++) {
            List<String[]> transactionlist=CustomerSearchingFunction.searchTransaction(customers.get(i).id);
            for (int j = 0; j< Objects.requireNonNull(transactionlist).size(); j++) {
                Transaction transaction = new Transaction("Details only for today's actions", transactionlist.get(j)[0],
                        transactionlist.get(j)[1], transactionlist.get(j)[2], transactionlist.get(j)[3], transactionlist.get(j)[4]);
                customers.get(i).transactions.add(transaction);
            }
        }
        }
    }

    public static void userMenu(){
        userend = true;
        System.out.println("You are 1. new customer 2. old customer 3. manager login 4. exit");
        Scanner choice = new Scanner(System.in);
        String num = choice.nextLine();

        while(!Tool.is_number(num)||!Tool.in_range(num,1,9)||(Integer.parseInt(num)<1)||(Integer.parseInt(num)>4)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        if (number == 1) {
            int id = createNewCustomer();
            while (userend) {
                bankMenu(id);
            }
        } else if (number == 4) {
            end = false;
        }
        else if(number==2){
            Scanner username = new Scanner(System.in);
            System.out.println("Dear customer, please enter your name: ");
            String name = username.nextLine();
            while (!Tool.is_alpha(name)) {
                System.out.println("Invalid name. A name should consist of letters.");
                name = username.nextLine();
            }
            Scanner password = new Scanner(System.in);
            System.out.println("Dear customer, please enter your password(It should be between 6-16): ");
            String pwd = password.nextLine();
            while (!Tool.in_range(pwd, 6, 16)) {
                System.out.println("Invalid length of password. Input again.");
                pwd = password.nextLine();
            }
            int login = CustomerSearchingFunction.customerLogin(name,pwd);
            if (login==-1){
                System.out.println("Wrong login!");
            }else {
                while (userend){
                bankMenu(login);}
            }
        }
        else if (number == 3) {
            Scanner username = new Scanner(System.in);
            System.out.println("Dear manager, please enter your name: ");
            String name = username.nextLine();
            while (!Tool.is_alpha(name)) {
                System.out.println("Invalid name. A name should consist of letters.");
                name = username.nextLine();
            }
            Scanner password = new Scanner(System.in);
            System.out.println("Dear manager, please enter your password(It should be between 6-16): ");
            String pwd = password.nextLine();
            while (!Tool.in_range(pwd, 6, 16)) {
                System.out.println("Invalid length of password. Input again.");
                pwd = password.nextLine();
            }
            int login = ManagerFunction.managerLogin(name, pwd);
            if (login == 0) {
                bankManager = new Manager(name, pwd);
                bankManager.setCustomers(customers);
                while (userend) {
                    managerMenu();
                }
            } else {
                System.out.println("Wrong name or password.");
            }
        }
    }

    public static void bankMenu(int id){
        int index=-1;
        for (int i=0;i<customers.size();i++){
            if (customers.get(i).id==id){
                index=i;
            }
        }
        System.out.println("Choose an action you wanna take: ");
        System.out.println("1. check 2. save/withdraw 3. loan 4. delete accounts 5. exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)||!Tool.in_range(num,1,9)||(Integer.parseInt(num)<1)||(Integer.parseInt(num)>5)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);

        int exist;
        switch (number) {
            case 1:
                exist = isthereAccount(index, "CHECKING");
                if (exist < 0) {
                    int accountID = customers.get(index).createAccount("CHECKING");
                    if (accountID >= 0) {
                        System.out.println("You can check your money and your transaction records now.");
                        ((CheckingAccount) customers.get(index).accounts.get(accountID)).Menu(customers.get(index));
                    }
                } else {
                    ((CheckingAccount) customers.get(index).accounts.get(exist)).Menu(customers.get(index));
                }
                break;
            case 2 :
                exist = isthereAccount(index, "SAVING");
                if (exist < 0) {
                    int accountID = customers.get(index).createAccount("SAVING");
                    if (accountID >= 0) {
                    System.out.println("You can make savings and withdrawals now.");
                    ((SavingAccount) customers.get(index).accounts.get(accountID)).Menu();}
                } else {
                    ((SavingAccount) customers.get(index).accounts.get(exist)).Menu();
                }
                break;
            case 3:
                exist = isthereAccount(index, "LOAN");
                if (exist < 0) {
                    int accountID = customers.get(index).createAccount("LOAN");
                    if (accountID >= 0) {
                    ((LoanAccount) customers.get(index).accounts.get(accountID)).Menu();}
                } else {
                    ((LoanAccount) customers.get(index).accounts.get(exist)).Menu();
                }
                break;
            case 4:
                customers.get(index).removeAccount();
                break;
            case 5:
                System.out.println("Bye bye!");
                userend = false;
                break;

        }
    }
    public static void managerMenu(){
        System.out.println("Choose an action you wanna take: ");
        System.out.println("1. check all the customers 2. search for one customer 3. check for debtors 4. transactions records 5. exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)||!Tool.in_range(num,1,9)||(Integer.parseInt(num)<1)||(Integer.parseInt(num)>5)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        switch (number){
            case 1:
                bankManager.printAll();
                break;
            case 2:
                System.out.println("1. search by name 2. search by ID");
                Scanner choice1=new Scanner(System.in);
                String num1=choice1.nextLine();
                while(!Tool.is_number(num1)||!Tool.in_range(num,1,9)||(Integer.parseInt(num1)<1)||(Integer.parseInt(num1)>2)){
                    System.out.println("Invalid input. Input again.");
                    num1=choice1.nextLine();
                }
                int number1=Integer.parseInt(num1);
                if (number1==1){
                    System.out.println("Input the name:");
                    Scanner username=new Scanner(System.in);
                    String name=username.nextLine();
                    while (!Tool.is_alpha(name)){
                        System.out.println("Invalid name. A name should consist of letters.");
                        name=username.nextLine();
                    }
                    bankManager.checkCustomer(name);
                }else {
                    System.out.println("Input the ID:");
                    Scanner id=new Scanner(System.in);
                    String cid=id.nextLine();
                    while (!Tool.is_number(cid)){
                        System.out.println("Invalid input. Input again.");
                        cid=id.nextLine();
                    }
                    int ccid=Integer.parseInt(cid);
                    bankManager.checkCustomer(ccid);
                }
                break;
            case 3:
                bankManager.printLoan();
                break;
            case 4:
                //System.out.println(ManagerFunction.searchTransactionToday());
                List<String[]> records=ManagerFunction.searchTransactionToday();
                for (int i = 0; i< Objects.requireNonNull(records).size(); i++){
                    for (int j=0;j<records.get(i).length;j++){
                        System.out.print(records.get(i)[j]+" ");
                    }
                    System.out.print("\n");
                }
                break;
            case 5:
                System.out.println("Bye bye!");
                userend = false;
                break;

        }
    }
    public static int isthereAccount(int id,String str){
        Customer customer=customers.get(id);
        int accountID=-1;
        for (int i=0;i<customer.accounts.size();i++){
            if (customer.accounts.get(i).accountType.equals(str)){
                accountID=i;
                break;
            }
        }
        if (accountID<0){
            System.out.println("You don't have "+str+" account. You can create one now.");
        }
        return accountID;
    }
    public static Customer initCustomerInfo(){
        String name=initUserName();
        String pwd=initUserPwd();
        Customer customer=new Customer(name,pwd);
        return customer;
    }
    public static String initUserName(){
        Scanner username=new Scanner(System.in);
        System.out.println("Dear new user, please enter your name: ");
        String name=username.nextLine();
        while (!Tool.is_alpha(name)){
            System.out.println("Invalid name. A name should consist of letters.");
            name=username.nextLine();
        }
        return name;
    }
    public static String initUserPwd(){
        Scanner password=new Scanner(System.in);
        System.out.println("Dear user, please enter your password(It should be between 6-16): ");
        String pwd=password.nextLine();

        while (!Tool.in_range(pwd,6,16)){
            System.out.println("Invalid length of password. Construct again.");
            pwd=password.nextLine();
        }
        System.out.println("Please enter your password again: ");
        String pwd1=password.nextLine();
        while (!pwd.equals(pwd1)){
            System.out.println("This input doesn't match last one. Construct your password again: ");
            while (!Tool.in_range(pwd,6,16)){
                System.out.println("Invalid length of password. Construct again.");
                pwd=password.nextLine();
            }
            System.out.println("Please enter your password again: ");
            pwd1=password.nextLine();
            while (!Tool.in_range(pwd1,6,16)){
                System.out.println("Invalid length of password. Construct again.");
                pwd1=password.nextLine();
            }
        }
        return pwd;
    }
    //!!database new customer
    public static int createNewCustomer(){
        Customer customer= initCustomerInfo();
        //new customer in database
        int cid=CustomerAddingFunction.addCustomer(customer.name, customer.pwd);
        customers.add(customer);
        customer.setId(cid);
        //return the index of this customer in arraylist, not its customerID
        int location=customers.size()-1;
        return location;
    }
}
