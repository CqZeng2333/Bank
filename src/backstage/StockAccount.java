package backstage;

import connect_database.CustomerAddingFunction;
import connect_database.CustomerAlteringFunction;
import connect_database.ManagerFunction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class StockAccount extends Account {
    private ArrayList<Stock>stocks;
    private HashMap<Stock,Integer>ownStocks=new HashMap<>(5);
    public final static BigDecimal LEASTMONEY = new BigDecimal("1000");
    public final static BigDecimal LEASTBALANCE = new BigDecimal("2500");
    private BigDecimal stockBalance;
    StockAccount(int id,Currency currency){
        super(id,currency);
        accountType="STOCK";
    }
    //5000
//1000
    public boolean initAccount(){
        System.out.println("Welcome! You will start your trip in the stock market!.");
        System.out.println("Opening this count will charge you 5 dollars." + '\n'
                + "You should have more than 5000 dollars in your saving account.");
        System.out.println("Stock market in our bank only permits dollars.");
        if (currency.get("Dollar").compareTo(new BigDecimal("5000")) < 0){
            System.out.println("You don't have more than 5000 dollars!");
            System.out.println("Fail to open a stock account.");
            createTransaction("0","Dollar","Failed to open stock account.");
            return false;
        }else {
            boolean success=currency.sub("Dollar",5,"1");
            if (success){
                while (true){
                String cash=save("Dollar",LEASTMONEY);
                if (cash.equals("")){
                    System.out.println("You should save more than 1000 dollars to initialize this account!");
                }else {
                    //alter manager
                    ManagerFunction.alterManagerAccount("Dollar",new BigDecimal("5"));
                    //add saving account in database
                    CustomerAddingFunction.addStockAccount(customerID);
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-5"));
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal(cash));
                    createTransaction("-5","Dollar","Open Stock account.");
                    createTransaction(cash,"Dollar","Stock account capital.");
                    return true;
                }
                }
            }else {
                System.out.println("You don't have 5 dollars!");
                System.out.println("Fail to open a stock account. Pay your money back.");
                createTransaction("0","Dollar","Failed to open stock account.");
                return false;
            }
        }
    }

    public void Menu(Customer customer){
        System.out.println("0. save money to the stock account");
        System.out.println("1. See all stocks");
        System.out.println("2. buy a stock");
        System.out.println("3. sell a stock");
        System.out.println("4. see the current open positions");
        System.out.println("5. see the realized and unrealized profit");
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while(!Tool.is_number(num)){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
        }
        int number=Integer.parseInt(num);
        while (number<0||number>5){
            System.out.println("Invalid input. Input again.");
            num=choice.nextLine();
            number=Integer.parseInt(num);
        }
        switch (number){
            case 0:
                System.out.println("You have to save over 1000 dollars, and you must maintain a $2500.00 balance");
                boolean flag = true;
                Scanner save = new Scanner(System.in);
                while(flag) {
                    String saveMoney = save.nextLine();
                    if (!Tool.is_number(saveMoney)) {
                        System.out.println("Invalid input. Please input a number.");
                    } else {
                        BigDecimal stockMoney = new BigDecimal(saveMoney);
                        if (stockMoney.compareTo(LEASTMONEY) != 1) {
                            System.out.println("You have to save over 1000 dollars one time!Try again");
                        } else {
                            if (Account.currency.get("Dollar").compareTo(stockMoney.add(LEASTBALANCE)) == -1) {
                                System.out.println("You have to have at least 2500 dollars balance in saving account! Try again");
                            } else {
                                Account.currency.sub("Dollar",stockMoney.doubleValue(),"1");
                                if(this.stockBalance == null)
                                {
                                    this.stockBalance = stockMoney;
                                }
                                else
                                {
                                    this.stockBalance = this.stockBalance.add(stockMoney);
                                }
                                flag = false;
                            }
                        }
                    }
                }
                break;

            case 1:
                for (int i = 0; i < stocks.size(); i++)
                {
                    System.out.println(stocks.get(i));
                }
                break;

            case 2:
                System.out.println("What's the stock and what's the number you want to buy?");
                System.out.println("Please enter the stock ID at first:");
                Scanner id = new Scanner(System.in);
                int stockId = id.nextInt();
                id.nextLine();
                while(checkID(stockId)==-1){
                    System.out.println("This stock doesn't exist! Try again");
                    stockId = id.nextInt();
                    id.nextLine();
                }
                Stock currentStock = stocks.get(checkID(stockId));
                System.out.println("You choose:"+stockId);
                System.out.println("What's the number you want to buy?");
                Scanner sc = new Scanner(System.in);
                boolean flag2 = true;
                while (flag2){
                    int stockNum = sc.nextInt();
                    sc.nextLine();
                    if (stockNum <= 0) {
                        System.out.println("Invalid input. Try again.");
                    }
                    else{
                        BigDecimal payMoney = currentStock.getStockPrice().multiply(new BigDecimal(stockNum));
                        if (stockBalance!=null && payMoney.compareTo(stockBalance)>0)
                        {
                            System.out.println("You do not have enough balance!");
                        }
                        else
                        {
                            System.out.println("You have buy this stock successfully!");
                            flag2 = false;
                            this.stockBalance = this.stockBalance.subtract(payMoney);
                            //需要加上判断原来有没有买这个股票
                            if (this.ownStocks.get(currentStock)==null)
                            {
                                this.ownStocks.put(currentStock,stockNum);
                            }
                            else
                            {
                                this.ownStocks.put(currentStock,this.ownStocks.get(currentStock)+stockNum);
                            }

                        }
                    }
                }
                break;

            case 3:
                break;

            case 4:
                break;




        }

    }

    public int checkID(int id){
        for (int i = 0; i < stocks.size(); i++){
            if (id == stocks.get(i).getId())
            {
                return i;
            }
        }
        return -1;
    }


    public void createTransaction(String moneychange,String currencyType,String action){
        Time time=new Time();
        String str=time+ " Customer "+(customerID+1)+" in Stock account: "+action;
        Transaction transaction=new Transaction();
        transaction.setInfo(str);
        transaction.setAccountType(accountType);
        transaction.setCurrencyType(currencyType);
        transaction.setCurrentCurrency(Account.currency);
        transaction.setCustomerID(customerID);
        transaction.setTime(time);
        transaction.setMoneyChange(moneychange);
        transactions.add(transaction);
    }

    public static void main(String[] args) {
        System.out.println("Hi");
        Stock test1 = new Stock(1,"first",new BigDecimal("1000"));
        Stock test2 = new Stock(2,"second",new BigDecimal("500"));
        ArrayList<Stock> test = new ArrayList<>();
        test.add(test1);
        test.add(test2);
        Customer user = new Customer("hxt","123123123",1);
        SavingAccount testSave = new SavingAccount(1);
        testSave.initAccount();
        StockAccount testStock = new StockAccount(1,test);
        testStock.initAccount();
        boolean flag = true;
        while (flag) {
            testStock.Menu(user);
        }
    }


}