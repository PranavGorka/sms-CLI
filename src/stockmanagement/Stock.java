package stockmanagement;

public class Stock {
    private int id;
    private String companyName;
    private String symbol;
    private int quantity;
    private double purchasePrice;
    private double currentPrice;
    private String sector;

    public Stock(int id, String companyName, String symbol, int quantity,
                 double purchasePrice, double currentPrice, String sector) {
        this.id = id;
        this.companyName = companyName;
        this.symbol = symbol.toUpperCase();
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
        this.currentPrice = currentPrice;
        this.sector = sector;
    }

    // Getters
    public int getId()              { return id; }
    public String getCompanyName()  { return companyName; }
    public String getSymbol()       { return symbol; }
    public int getQuantity()        { return quantity; }
    public double getPurchasePrice(){ return purchasePrice; }
    public double getCurrentPrice() { return currentPrice; }
    public String getSector()       { return sector; }

    // Setters
    public void setCompanyName(String companyName)   { this.companyName = companyName; }
    public void setSymbol(String symbol)             { this.symbol = symbol.toUpperCase(); }
    public void setQuantity(int quantity)            { this.quantity = quantity; }
    public void setPurchasePrice(double purchasePrice){ this.purchasePrice = purchasePrice; }
    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }
    public void setSector(String sector)             { this.sector = sector; }

    public double getProfitLoss() {
        return (currentPrice - purchasePrice) * quantity;
    }

    public double getTotalValue() {
        return currentPrice * quantity;
    }

    /** CSV format: id,companyName,symbol,quantity,purchasePrice,currentPrice,sector */
    public String toCSV() {
        return id + "," + companyName + "," + symbol + "," + quantity + ","
                + purchasePrice + "," + currentPrice + "," + sector;
    }

    /** Parse a CSV line back into a Stock object */
    public static Stock fromCSV(String line) {
        String[] parts = line.split(",", 7);
        return new Stock(
            Integer.parseInt(parts[0].trim()),
            parts[1].trim(),
            parts[2].trim(),
            Integer.parseInt(parts[3].trim()),
            Double.parseDouble(parts[4].trim()),
            Double.parseDouble(parts[5].trim()),
            parts[6].trim()
        );
    }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-6s | %-8d | %10.2f | %12.2f | %-15s | %+12.2f |",
            id, companyName, symbol, quantity, purchasePrice, currentPrice, sector, getProfitLoss());
    }
}
