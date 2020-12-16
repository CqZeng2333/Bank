package backstage;

public class Transaction {
     String info;
     String moneyChange;

     public String getBalance() {
          return balance;
     }

     public void setBalance(String balance) {
          this.balance = balance;
     }

     String balance;

     public String getMoneyChange() {
          return moneyChange;
     }

     public void setMoneyChange(String moneyChange) {
          this.moneyChange = moneyChange;
     }

     Transaction(String str,String accountType,String currencyType,String moneychange,String currentBalance,String time){
          setInfo(str);
          setAccountType(accountType);
          setCurrencyType(currencyType);
          setBalance(currentBalance);
          setTime(time);
          setMoneyChange(moneychange);
     }

     public int getCustomerID() {
          return customerID;
     }

     public void setCustomerID(int customerID) {
          this.customerID = customerID;
     }

     public String getAccountType() {
          return accountType;
     }

     public void setAccountType(String accountType) {
          this.accountType = accountType;
     }

     public String getCurrencyType() {
          return currencyType;
     }

     public void setCurrencyType(String currencyType) {
          this.currencyType = currencyType;
     }

     public Currency getCurrentCurrency() {
          return currentCurrency;
     }

     public void setCurrentCurrency(Currency currentCurrency) {
          this.currentCurrency = currentCurrency;
     }

     Currency currentCurrency;
     int customerID;
     String accountType;
     String currencyType;

     public String getTime() {
          return time;
     }

     public void setTime(String time) {
          this.time = time;
     }

     String time;
     Transaction(){

     }
     public String toString(){
          //{account_type, currency_type, money_changed, current_balance, time}
          return accountType+" "+currencyType+" "+moneyChange+" "+balance+" "+time;
     }
     public void setInfo(String str){info=str;}
}
