package backstage;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

public class Currency {
    public HashMap<String, BigDecimal> getMoney() {
        return money;
    }

    private HashMap<String, BigDecimal> money;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    int customerID;

    //default
    public Currency() {
        HashMap<String, BigDecimal> initMoney = new HashMap<>();
        initMoney.put("Dollar",BigDecimal.ZERO);
        initMoney.put("RMB",BigDecimal.ZERO);
        initMoney.put("Pound",BigDecimal.ZERO);
        this.money = initMoney;
    }

    public Currency(HashMap<String, BigDecimal> money) {
        this.money = money;
    }

    public String print() {
        String str="";
        for(String entry : money.keySet()) {
            BigDecimal value = money.get(entry);
            str+=entry + ":" + value+" ";
        }
        return str;
    }
    public boolean is_empty(){
        for (HashMap.Entry<String, BigDecimal> entry : money.entrySet()) {
            if(entry.getValue().compareTo(new BigDecimal("1"))>=0){
            return false;
        }}
        return true;
    }
    public BigDecimal get(String key){
        return money.get(key);
    }
    public int check(String key ,double value){
        if (value <= 0){
            System.out.println("The value should > 0");
            return 0;
        }
        if (!this.money.containsKey(key)){
            System.out.println("The currency was not found");
            return 0;
        } else
            return 1;
    }

    //str is the rate of the value, like when you wanna add 80% of the value to the currency, your str is "0.8"
    //This is because double multiply gets wrong answer all the time so it's hard to do computation outside add and sub functions
    public void add(String key ,double value,String str){
        if (check(key,value)==0){
        } else {
            BigDecimal addNum = new BigDecimal(Double.toString(value));
            addNum=addNum.multiply(new BigDecimal(str));
            this.money.put(key,this.money.get(key).add(addNum));
            System.out.println("Successfully save "+key+" "+addNum+"!");
            System.out.println("Now your deposit: ");
            System.out.println(print());
        }
    }

    public boolean sub(String key ,double value,String str){
        boolean success=false;
        if (check(key,value)==0){
        } else {
            BigDecimal subNum = new BigDecimal(Double.toString(value));
            subNum=subNum.multiply(new BigDecimal(str));
            if (this.money.get(key).compareTo(subNum) == -1) {
                System.out.println("Your balance is not enough!");
                }
                else{
                this.money.put(key,this.money.get(key).subtract(subNum));
                System.out.println("Successfully withdraw "+key+" "+subNum+"!");
                System.out.println("Now your deposit: ");
                System.out.println(print());
                success=true;
                    }
               }
        return success;
    }

/*public static void main(String[] args) {
        HashMap m1 = new HashMap<String, BigDecimal>();
        BigDecimal zero = new BigDecimal("-50");
        zero=zero.multiply(new BigDecimal(5));
        m1.put("Dollar",zero);
        System.out.println(m1);


    }*/
}
