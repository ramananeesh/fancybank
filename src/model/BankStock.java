package model;

public class BankStock {
    private String stockName;
    private double value;
    private int numStocks;

    public BankStock(String stockName, double value, int numStocks) {
        this.stockName = stockName;
        this.value = value;
        this.numStocks = numStocks;
    }

    public String getStockName() {
        return stockName;
    }

    public double getValue() {
        return value;
    }

    public int getNumStocks() {
        return numStocks;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setNumStocks(int numStocks) {
        this.numStocks = numStocks;
    }

    public String[] getShortAllStockDisplayForCustomer() {
        return new String[] {this.stockName, Double.toString(this.value), Integer.toString(this.numStocks)};
    }

    public String[] getShortAllStockDisplayForManager() {
        return new String[] {this.stockName, Double.toString(this.value), Integer.toString(this.numStocks)};
    }

    public String getDetailedAllStockDisplayForCustomer(){
        String ret = "";

        ret += "Stock Name: " + this.stockName + "\n";
        ret += "Value: $" + this.value + "\n";
        ret += "Num of Stocks: " + this.numStocks + "\n";
        return ret;
    }

    public String getDetailedAllStockDisplayForManager() {
        String ret = "";

        ret += "Stock Name: " + this.stockName + "\n";
        ret += "Value: $" + this.value + "\n";
        ret += "Num of Stocks: " + this.numStocks + "\n";
        return ret;
    }

    public String toString() {
        return "All Stock [stockName=" + stockName + ", value=" + value + ", # stocks=" + numStocks + "]";
    }
}
