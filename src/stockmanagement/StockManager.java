package stockmanagement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StockManager {

    private List<Stock> stocks;
    private int nextId;

    public StockManager() {
        stocks = FileHandler.loadStocks();
        nextId = stocks.stream().mapToInt(Stock::getId).max().orElse(0) + 1;
    }

    // ─────────────────────────────────────────────
    //  CREATE
    // ─────────────────────────────────────────────
    public void addStock(String companyName, String symbol, int quantity,
                         double purchasePrice, double currentPrice, String sector) {
        Stock s = new Stock(nextId++, companyName, symbol, quantity,
                            purchasePrice, currentPrice, sector);
        stocks.add(s);
        FileHandler.saveStocks(stocks);
        System.out.println("\n[SUCCESS] Stock added with ID " + s.getId());
    }

    // ─────────────────────────────────────────────
    //  READ
    // ─────────────────────────────────────────────
    public void viewAllStocks() {
        if (stocks.isEmpty()) {
            System.out.println("\n[INFO] No stocks found in portfolio.");
            return;
        }
        printTable(stocks);
    }

    public void viewStockById(int id) {
        Stock s = findById(id);
        if (s == null) {
            System.out.println("\n[ERROR] Stock with ID " + id + " not found.");
        } else {
            printTableHeader();
            System.out.println(s);
            printTableFooter();
        }
    }

    // ─────────────────────────────────────────────
    //  UPDATE
    // ─────────────────────────────────────────────
    public boolean updateStock(int id, String companyName, String symbol, int quantity,
                                double purchasePrice, double currentPrice, String sector) {
        Stock s = findById(id);
        if (s == null) return false;
        s.setCompanyName(companyName);
        s.setSymbol(symbol);
        s.setQuantity(quantity);
        s.setPurchasePrice(purchasePrice);
        s.setCurrentPrice(currentPrice);
        s.setSector(sector);
        FileHandler.saveStocks(stocks);
        System.out.println("\n[SUCCESS] Stock ID " + id + " updated.");
        return true;
    }

    // ─────────────────────────────────────────────
    //  DELETE
    // ─────────────────────────────────────────────
    public boolean deleteStock(int id) {
        Stock s = findById(id);
        if (s == null) return false;
        stocks.remove(s);
        FileHandler.saveStocks(stocks);
        System.out.println("\n[SUCCESS] Stock ID " + id + " deleted.");
        return true;
    }

    // ─────────────────────────────────────────────
    //  SEARCH
    // ─────────────────────────────────────────────
    public void searchByCompanyName(String keyword) {
        String kw = keyword.toLowerCase();
        List<Stock> results = stocks.stream()
            .filter(s -> s.getCompanyName().toLowerCase().contains(kw))
            .collect(Collectors.toList());
        printSearchResults(results, "Company Name contains \"" + keyword + "\"");
    }

    public void searchBySymbol(String symbol) {
        List<Stock> results = stocks.stream()
            .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
            .collect(Collectors.toList());
        printSearchResults(results, "Symbol = " + symbol.toUpperCase());
    }

    public void searchBySector(String sector) {
        String kw = sector.toLowerCase();
        List<Stock> results = stocks.stream()
            .filter(s -> s.getSector().toLowerCase().contains(kw))
            .collect(Collectors.toList());
        printSearchResults(results, "Sector contains \"" + sector + "\"");
    }

    // ─────────────────────────────────────────────
    //  SORT
    // ─────────────────────────────────────────────
    public void sortByCompanyName() {
        List<Stock> sorted = new ArrayList<>(stocks);
        sorted.sort(Comparator.comparing(s -> s.getCompanyName().toLowerCase()));
        System.out.println("\n[Sorted by Company Name]");
        printTable(sorted);
    }

    public void sortByCurrentPrice() {
        List<Stock> sorted = new ArrayList<>(stocks);
        sorted.sort(Comparator.comparingDouble(Stock::getCurrentPrice).reversed());
        System.out.println("\n[Sorted by Current Price — High to Low]");
        printTable(sorted);
    }

    public void sortByProfitLoss() {
        List<Stock> sorted = new ArrayList<>(stocks);
        sorted.sort(Comparator.comparingDouble(Stock::getProfitLoss).reversed());
        System.out.println("\n[Sorted by Profit/Loss — Best First]");
        printTable(sorted);
    }

    public void sortByQuantity() {
        List<Stock> sorted = new ArrayList<>(stocks);
        sorted.sort(Comparator.comparingInt(Stock::getQuantity).reversed());
        System.out.println("\n[Sorted by Quantity — High to Low]");
        printTable(sorted);
    }

    // ─────────────────────────────────────────────
    //  PORTFOLIO SUMMARY
    // ─────────────────────────────────────────────
    public void showPortfolioSummary() {
        if (stocks.isEmpty()) {
            System.out.println("\n[INFO] Portfolio is empty.");
            return;
        }
        double totalInvested = stocks.stream().mapToDouble(s -> s.getPurchasePrice() * s.getQuantity()).sum();
        double totalCurrent  = stocks.stream().mapToDouble(Stock::getTotalValue).sum();
        double totalPL       = stocks.stream().mapToDouble(Stock::getProfitLoss).sum();
        long gainers         = stocks.stream().filter(s -> s.getProfitLoss() > 0).count();
        long losers          = stocks.stream().filter(s -> s.getProfitLoss() < 0).count();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("          PORTFOLIO SUMMARY");
        System.out.println("=".repeat(50));
        System.out.printf("  Total Stocks         : %d%n", stocks.size());
        System.out.printf("  Total Invested       : Rs %.2f%n", totalInvested);
        System.out.printf("  Current Value        : Rs %.2f%n", totalCurrent);
        System.out.printf("  Total Profit/Loss    : Rs %+.2f%n", totalPL);
        System.out.printf("  Gainers / Losers     : %d / %d%n", gainers, losers);
        System.out.println("=".repeat(50));
    }

    // ─────────────────────────────────────────────
    //  EXPORT REPORT
    // ─────────────────────────────────────────────
    public void exportReport() {
        FileHandler.exportReport(stocks);
    }

    // ─────────────────────────────────────────────
    //  HELPERS
    // ─────────────────────────────────────────────
    private Stock findById(int id) {
        return stocks.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    public boolean stockExists(int id) {
        return findById(id) != null;
    }

    private void printSearchResults(List<Stock> results, String criteria) {
        if (results.isEmpty()) {
            System.out.println("\n[INFO] No results found for: " + criteria);
        } else {
            System.out.println("\n[Search Results — " + criteria + "]");
            printTable(results);
        }
    }

    private void printTableHeader() {
        System.out.println("\n" + "=".repeat(105));
        System.out.printf("| %-4s | %-20s | %-6s | %-8s | %10s | %12s | %-15s | %12s |%n",
            "ID", "Company Name", "Symbol", "Qty", "Buy Price", "Curr Price", "Sector", "Profit/Loss");
        System.out.println("=".repeat(105));
    }

    private void printTableFooter() {
        System.out.println("=".repeat(105));
    }

    private void printTable(List<Stock> list) {
        printTableHeader();
        for (Stock s : list) {
            System.out.println(s);
        }
        printTableFooter();
    }
}
