package backstage;

import java.math.BigDecimal;

public class Stock {
    private int id;
    private BigDecimal originprice;

    public BigDecimal getOriginprice() {
        return originprice;
    }

    public void setOriginprice(BigDecimal originprice) {
        this.originprice = originprice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;
    private String stockName;
    private BigDecimal stockPrice;

    public Stock() {
    }
    public Stock(int id,String name,BigDecimal originprice){
        this.id=id;
        this.stockName=name;
        this.originprice=originprice;
    }
    public Stock(int id, String stockName, BigDecimal originprice,BigDecimal stockPrice,int number) {
        this.id = id;
        this.originprice=originprice;
        this.stockName=stockName;
        this.number=number;
        this.stockPrice=stockPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public BigDecimal getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(BigDecimal stockPrice) {
        this.stockPrice = stockPrice;
    }

    @Override
    public String toString() {
        return "id=" + id + '\t' +
                ", Name=" + stockName + '\t' +
                ", (buy in)Price=" + originprice+'\t'+
                ", Number="+number;
    }
}
