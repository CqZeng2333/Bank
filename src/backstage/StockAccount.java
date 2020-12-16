package backstage;

import connect_database.*;

import java.math.BigDecimal;
import java.util.*;

public class StockAccount extends Account {
    public final static BigDecimal LEASTMONEY = new BigDecimal("1000");
    public final static BigDecimal LEASTBALANCE = new BigDecimal("2500");
    private BigDecimal stockBalance;
    public final static BigDecimal TRANS=new BigDecimal("-1");
    StockAccount(int id,Currency currency){
        super(id,currency);
        accountType="STOCK";
        if (CustomerSearchingFunction.searchStockMoneyAmount(customerID)!=null){
            stockBalance=CustomerSearchingFunction.searchStockMoneyAmount(customerID);
        }else {
            stockBalance=null;
        }

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
                String[] cash=withdraw("Dollar",LEASTMONEY,LEASTBALANCE);
                if (cash[0].equals("")){
                    System.out.println("You should save more than 1000 dollars to initialize this account!");
                }else {
                    //alter manager
                    ManagerFunction.alterManagerAccount("Dollar",new BigDecimal("5"));
                    //add saving account in database
                    CustomerAddingFunction.addStockAccount(customerID);
                    BigDecimal cap=new BigDecimal(cash[0]);
                    cap=cap.add(new BigDecimal(cash[1]));
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-5"));
                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",new BigDecimal("-"+cap.toString()));
                    CustomerAlteringFunction.alterStockAccount(customerID,new BigDecimal(cash[0]));
                    ManagerFunction.alterManagerAccount("Dollar",new BigDecimal(cash[1]));
                    createTransaction("-5","Dollar","Open Stock account.");
                    createTransaction(cap.toString(),"Dollar","Stock account capital.");
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

    public void Menu(){
        System.out.println("0. save money to the stock account");
        System.out.println("1. See all stocks");
        System.out.println("2. buy a stock");
        System.out.println("3. sell a stock");
        /*System.out.println("4. see the current open positions");
        System.out.println("5. see the realized and unrealized profit");*/
        Scanner choice=new Scanner(System.in);
        String num=choice.nextLine();
        while ((!Tool.is_number(num)) || !Tool.in_range(num, 1, 9) || (Integer.parseInt(num) < 0) || (Integer.parseInt(num) > 5)) {
            System.out.println("Invalid input. Input again.");
            num = choice.nextLine();
        }
        int number = Integer.parseInt(num);
        switch (number){
            case 0:
                System.out.println("You have to save over 1000 dollars, and you must maintain a $2500.00 balance in your saving account.");
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
                            if (currency.get("Dollar").compareTo(stockMoney.add(LEASTBALANCE)) == -1) {
                                System.out.println("You have to have at least 2500 dollars balance in saving account!");
                                flag=false;
                            } else {
                                boolean success=currency.sub("Dollar",stockMoney.doubleValue(),"1");
                                if (success){
                                    createTransaction(stockMoney.multiply(TRANS).toString(),"Dollar","Transfer money to stock account.");
                                    CustomerAlteringFunction.alterSavingAccount(customerID,"Dollar",stockMoney.multiply(TRANS));
                                    if(this.stockBalance == null)
                                    {
                                        this.stockBalance = stockMoney;
                                        CustomerAlteringFunction.alterStockAccount(customerID,stockMoney);
                                    }
                                    else
                                    {
                                        this.stockBalance = this.stockBalance.add(stockMoney);
                                        CustomerAlteringFunction.alterStockAccount(customerID,stockMoney);
                                    }
                                }
                                flag = false;
                            }
                        }
                    }
                }
                break;

            case 1:
                if (CustomerSearchingFunction.searchStockList(customerID)!=null){
                    List<String[]> stockList=CustomerSearchingFunction.searchStockList(customerID);
                    for (int i=0;i<stockList.size();i++){
                        System.out.println(Arrays.toString(stockList.get(i)));
                    }
                }else {
                    System.out.println("You don't have stocks.");
                }

                break;

            case 2:
                List<String[]> stocklist=ManagerFunction.searchAllStockList();
                for (int i=0;i<stocklist.size();i++){
                    System.out.println(Arrays.toString(stocklist.get(i)));
                }
                System.out.println("What's the stock and what's the number you want to buy?");
                System.out.println("Please enter the stock ID at first:");
                Scanner id = new Scanner(System.in);
                int stockId = id.nextInt();
                id.nextLine();
                int index=checkID(stockId,stocklist);
                while(index==-1){
                    System.out.println("This stock doesn't exist! Try again");
                    stockId = id.nextInt();
                    id.nextLine();
                }
                Stock currentStock = new Stock(Integer.parseInt(stocklist.get(index)[0]),stocklist.get(index)[1],new BigDecimal(stocklist.get(index)[2]));
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
                        BigDecimal payMoney = currentStock.getOriginprice().multiply(new BigDecimal(stockNum));
                        if (stockBalance!=null && payMoney.compareTo(stockBalance)>0)
                        {
                            System.out.println("You do not have enough balance!");
                        }
                        else
                        {
                            System.out.println("You have bought this stock successfully!");
                            flag2 = false;
                            this.stockBalance = this.stockBalance.subtract(payMoney);
                            createTransaction("-"+payMoney.toString(),"Dollar","Buy stocks.");
                            CustomerAlteringFunction.alterStockAccount(customerID,payMoney.multiply(TRANS));
                            CustomerAddingFunction.addStockOwnership(customerID,stockId,currentStock.getOriginprice(),stockNum);
                        }
                    }
                }
                break;

            case 3:
                List<String[]> cusStocklist=CustomerSearchingFunction.searchStockList(customerID);
                if (cusStocklist!=null){
                for (int i=0;i<cusStocklist.size();i++){
                    System.out.println(Arrays.toString(cusStocklist.get(i)));
                }
                System.out.println("What's the stock you want to sell?");
                System.out.println("Please enter the stock ID at first:");
                Scanner id1 = new Scanner(System.in);
                int stockId1 = id1.nextInt();
                id1.nextLine();
                int index1=checkID(stockId1,cusStocklist);
                while(index1==-1){
                    System.out.println("This stock doesn't exist! Try again");
                    stockId1 = id1.nextInt();
                    id1.nextLine();
                }
                CustomerDeletingFunction.deleteOneStock(customerID,Integer.parseInt(cusStocklist.get(index1)[0]));
                BigDecimal ori=new BigDecimal(cusStocklist.get(index1)[2]);
                BigDecimal cur=new BigDecimal(cusStocklist.get(index1)[3]);
                BigDecimal profit=(cur.subtract(ori)).multiply(new BigDecimal(cusStocklist.get(index1)[4]));
                CustomerAlteringFunction.alterStockAccount(customerID,profit);
                stockBalance=stockBalance.add(profit);
                createTransaction(profit.toString(),"Dollar","Sell stocks.");}
                else {
                    System.out.println("You don't have stocks.");
                }
                break;

     /*       case 4:
                break;*/

        }

    }

    public int checkID(int id,List<String[]> stocklist){
        for (int i = 0; i < stocklist.size(); i++){
            if (id == Integer.parseInt(stocklist.get(i)[0]))
            {
                return i;
            }
        }
        return -1;
    }

    //String str,String accountType,String currencyType,String moneychange,String currentBalance,Time time
    public void createTransaction(String moneychange,String currencyType,String action){
        Time time=new Time();
        String str=time+ " Customer "+(customerID+1)+" in Stock account: "+action;
        Transaction transaction=new Transaction(str,accountType,currencyType,currency.get(currencyType).toString(),moneychange,time.toString());
        transactions.add(transaction);
        //transaction in database
        CustomerAddingFunction.addTransaction(customerID,accountType,currencyType,new BigDecimal(moneychange),currency.get(currencyType),time);
    }

/*    public static void main(String[] args) {
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
    }*/


}