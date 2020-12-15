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
        //{customer_ID, customer_name, account_type, currency_type, money_amount}

        //id loop
        for (int i = 0; i < Objects.requireNonNull(existcustomers).size(); i++) {
            if (customers.size() > 0) {
                for (int j = 0; j < customers.size(); j++) {
                    if (customers.get(j).id == Integer.parseInt(existcustomers.get(i)[0])) {
                        break;
                    }
                    Customer newCustomer = new Customer(Integer.parseInt(existcustomers.get(i)[0]));
                    newCustomer.name = existcustomers.get(i)[1];
                    customers.add(newCustomer);
                }
            }
        }
        //account loop
        int sc = 0, cc = 0, lc = 0;
        for (int i = 0; i < customers.size(); i++) {
            for (int j = 0; j < Objects.requireNonNull(existcustomers).size(); j++) {
                if (customers.get(i).id == Integer.parseInt(existcustomers.get(j)[0])) {
                    if (existcustomers.get(j)[2].equals("SAVING") && sc == 0) {
                        SavingAccount savingAccount = new SavingAccount(customers.get(i).id, customers.get(i).currency);
                        sc = 1;
                        customers.get(i).accounts.add(savingAccount);
                    }
                    if (existcustomers.get(j)[2].equals("CHECKING") && cc == 0) {
                        CheckingAccount checkingAccount = new CheckingAccount(customers.get(i).id, customers.get(i).currency);
                        cc = 1;
                        customers.get(i).accounts.add(checkingAccount);
                    }
                    if (existcustomers.get(j)[2].equals("LOAN") && lc == 0) {
                        LoanAccount loanAccount = new LoanAccount(customers.get(i).id, customers.get(i).currency,new ArrayList<>());
                        lc = 1;
                        customers.get(i).accounts.add(loanAccount);
                    }
                }
            }
        }
        //currency loop
        for (int i = 0; i < customers.size(); i++) {
            int index = -1;
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
            for (int j = 0; j < Objects.requireNonNull(loanCustomer).size(); j++) {
                if (customers.get(i).id == Integer.parseInt(loanCustomer.get(j)[0])) {
                    ((LoanAccount) customers.get(i).accounts.get(index)).setTotalloan(new BigDecimal(loanCustomer.get(j)[-1]));
                }
            }
        }
        //loan collaterals loop
        for (int i = 0; i < customers.size(); i++) {
            List<String[]> collaterallist=CustomerSearchingFunction.searchLoanList(customers.get(i).id);
            // {loan_record_ID, loan_amount, collateral_name}
            if (collaterallist.size()>0){
                for (int j=0;j<collaterallist.size();j++){
                    Collateral collateral=new Collateral(collaterallist.get(j)[2],new BigDecimal(collaterallist.get(j)[1]));
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

    public static void userMenu(){
        userend = true;
        System.out.println("You are 1. new customer 2. old customer 3. manager login 4. exit");
        Scanner choice = new Scanner(System.in);
        String num = choice.nextLine();

        while (!Tool.is_number(num)) {
            System.out.println("Invalid input. Input again.");
            num = choice.nextLine();
        }
        int number = Integer.parseInt(num);
        while (number < 1 || number > 5) {
            System.out.println("Invalid input. Input again.");
            num = choice.nextLine();
            number = Integer.parseInt(num);
        }
        if (number == 1) {
            int id = createNewCustomer();
            while (userend) {
                bankMenu(id);
            }
        } else if (number == 5) {
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
                return;
            }else {
                bankMenu(login);
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
        System.out.println("Choose an action you wanna take: ");
        System.out.println("1. check 2. save/withdraw 3. loan 4. delete accounts 5. exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        while (number<1||number>5){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
            number=Integer.parseInt(num);
        }
        int exist;
        switch (number) {
            case 1 -> {
                exist = isthereAccount(id, "CHECKING");
                if (exist < 0) {
                    int accountID = customers.get(id).createAccount("CHECKING");
                    if (accountID >= 0) {
                        System.out.println("You can check your money and your transaction records now.");
                        ((CheckingAccount) customers.get(id).accounts.get(accountID)).Menu(customers.get(id));
                    }
                } else {
                    ((CheckingAccount) customers.get(id).accounts.get(exist)).Menu(customers.get(id));
                }
            }
            case 2 -> {
                exist = isthereAccount(id, "SAVING");
                if (exist < 0) {
                    int accountID = customers.get(id).createAccount("SAVING");
                    System.out.println("You can make savings and withdrawals now.");
                    ((SavingAccount) customers.get(id).accounts.get(accountID)).Menu();
                } else {
                    ((SavingAccount) customers.get(id).accounts.get(exist)).Menu();
                }
            }
            case 3 -> {
                exist = isthereAccount(id, "LOAN");
                if (exist < 0) {
                    int accountID = customers.get(id).createAccount("LOAN");
                    ((LoanAccount) customers.get(id).accounts.get(accountID)).Menu();
                } else {
                    ((LoanAccount) customers.get(id).accounts.get(exist)).Menu();
                }
            }
            case 4->{
                customers.get(id).removeAccount();
            }
            case 5 -> {
                System.out.println("Bye bye!");
                userend = false;
            }
        }
    }
    public static void managerMenu(){
        System.out.println("Choose an action you wanna take: ");
        System.out.println("1. check all the customers 2. search for one customer 3. check for debtors 4. transactions records 5. exit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        while (number<1||number>5){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
            number=Integer.parseInt(num);
        }
        switch (number){
            case 1:
                bankManager.printAll();
                break;
            case 2:
                System.out.println("1. search by name 2. search by ID");
                Scanner choice1=new Scanner(System.in);
                String num1=choice1.nextLine();
                while(!Tool.is_number(num1)){
                    System.out.println("Invalid input. Input again.");
                    num1=choice1.nextLine();
                }
                int number1=Integer.parseInt(num1);
                while (number1<1||number1>2){
                    System.out.println("Invalid input. Input again.");
                    num1=choice1.nextLine();
                    number1=Integer.parseInt(num1);
                }
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
                    System.out.println("Input the ID:(start from 0)");
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
                System.out.println(ManagerFunction.searchTransactionToday());
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
            System.out.println("You don't have "+str+" account it. You can create one now.");
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