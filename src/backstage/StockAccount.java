package backstage;

public class StockAccount extends Account {
    StockAccount(int id,Currency currency){
        super(id,currency);
        accountType="STOCK";
    }
    public void createTransaction(String moneychange,String currencyType,String action){

    }
}
