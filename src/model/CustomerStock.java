package model;

public class CustomerStock {
    private String stockID;
    private String stockName;
    private double buyingValue;
    private double currentValue;
    private int numStocks;
    private String accountName; 

    public CustomerStock(String stockID, String stockName, double buyingValue,
                 double currentValue, int numStocks, String accountName) {
        super();
        this.stockID = stockID;
        this.stockName = stockName;
        this.buyingValue = buyingValue;
        this.currentValue = currentValue;
        this.numStocks = numStocks;
        this.accountName = accountName; 
    }

    public String getStockID(){
        return stockID;
    }
    public String getStockName() {
        return stockName;
    }
    public String getBuyingValue() {
        return Double.toString(buyingValue);
    }
    public String getCurrentValue() {
        return Double.toString(currentValue);
    }
    public String getNumStocks() {
        return Integer.toString(numStocks);
    }
    
    public int getNumberOfStocks() {
    	return numStocks;
    }

    public void setStockID(String stockID){
        this.stockID = stockID;
    }
    public void setStockName(String customerName) {
        this.stockName = stockName;
    }
    public void setBuyingValue(double buyingValue) {
        this.buyingValue = buyingValue;
    }
    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }
    public void setNumStocks(int numStocks) {
        this.numStocks = numStocks;
    }

    public String[] getShortStockDisplayForCustomer() {
        return new String[] {this.stockID, this.stockName, Double.toString(this.buyingValue),
                Double.toString(this.currentValue), Integer.toString(this.numStocks)};
    }

    public String[] getShortStockDisplayForManager() {
        return new String[] {this.stockID, this.stockName, Double.toString(this.buyingValue),
                Double.toString(this.currentValue), Integer.toString(this.numStocks)};
    }

    public String getDetailedStockDisplayForCustomer() {
        String ret = "";

        ret += "Stock ID: " + this.stockID + "\n";
        ret += "Stock Name: " + this.stockName + "\n";
        ret += "Buying Value: $" + this.buyingValue + "\n";
        ret += "Current Value: $" + this.currentValue + "\n";
        ret += "Num of Stocks: " + this.numStocks + "\n";
        ret += "AccountName: " + this.accountName + "\n";
        return ret;
    }

    public String getDetailedStockDisplayForManager() {
        String ret = "";

        ret += "Stock ID: " + this.stockID + "\n";
        ret += "Stock Name: " + this.stockName + "\n";
        ret += "Buying Value: $" + this.buyingValue + "\n";
        ret += "Current Value: $" + this.currentValue + "\n";
        ret += "Num of Stocks: " + this.numStocks + "\n";
        ret += "AccountName: " + this.accountName + "\n";
        return ret;
    }

    public String toString() {
        return "Stock [stockID=" + stockID + "stockName=" + stockName + ", buyingValue=" + buyingValue
                + ", currentValue=" + currentValue + ", numStocks=" + numStocks + "]";
    }

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
    
}

