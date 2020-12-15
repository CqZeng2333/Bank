/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backstage;

import java.math.BigDecimal;

public class Collateral {
    private String item;
    private BigDecimal price;

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    private String currencyType;

    public Collateral(String item) {
        this.item = item;
        this.currencyType="Dollar";
    }


    public Collateral(String item, BigDecimal price) {
        this.item = item;
        this.price = price;
        this.currencyType="Dollar";
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Collateral information:" + '\n' +
                "item = " + item + ' ' +
                "currencyType = "+currencyType+
                " price = " + price+'\n';
    }

//    public static void main(String[] args) {
//        Collateral myCar = new Collateral("car",10000);
//        System.out.println(myCar);
//    }
}
