package backstage;

import java.math.BigDecimal;

public class Stock {
    private int id;
    private String stockName;
    private BigDecimal stockPrice;

    public Stock() {
    }

    public Stock(int id, String stockName, BigDecimal stockPrice) {
        this.id = id;
        this.stockName = stockName;
        this.stockPrice = stockPrice;
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
                ", Name='" + stockName + '\t' +
                ", Price=" + stockPrice ;
    }
}
