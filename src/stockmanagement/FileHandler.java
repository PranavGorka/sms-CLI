package stockmanagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String DATA_DIR  = "data";
    private static final String FILE_PATH = DATA_DIR + File.separator + "stocks.csv";

    /** Save all stocks to CSV file (overwrites existing file) */
    public static void saveStocks(List<Stock> stocks) {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_PATH))) {
            // Write header
            pw.println("id,companyName,symbol,quantity,purchasePrice,currentPrice,sector");
            for (Stock s : stocks) {
                pw.println(s.toCSV());
            }
            System.out.println("\n[FILE] Data saved to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("\n[ERROR] Could not save file: " + e.getMessage());
        }
    }

    /** Load stocks from CSV file; returns empty list if file doesn't exist */
    public static List<Stock> loadStocks() {
        List<Stock> stocks = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return stocks; // First run – nothing to load
        }
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) { firstLine = false; continue; } // skip header
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        stocks.add(Stock.fromCSV(line));
                    } catch (Exception e) {
                        System.out.println("[WARN] Skipping malformed line: " + line);
                    }
                }
            }
            System.out.println("[FILE] Loaded " + stocks.size() + " stock(s) from " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("[ERROR] Could not load file: " + e.getMessage());
        }
        return stocks;
    }

    /** Export a plain-text report to data/report.txt */
    public static void exportReport(List<Stock> stocks) {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();

        String reportPath = DATA_DIR + File.separator + "report.txt";
        try (PrintWriter pw = new PrintWriter(new FileWriter(reportPath))) {
            pw.println("=".repeat(105));
            pw.println("                         STOCK MANAGEMENT SYSTEM — PORTFOLIO REPORT");
            pw.println("=".repeat(105));
            pw.printf("%-6s %-22s %-8s %-10s %-12s %-14s %-17s %-14s%n",
                "ID", "Company", "Symbol", "Qty", "Buy Price", "Curr Price", "Sector", "P/L");
            pw.println("-".repeat(105));
            double totalPL = 0;
            for (Stock s : stocks) {
                pw.printf("%-6d %-22s %-8s %-10d %-12.2f %-14.2f %-17s %+.2f%n",
                    s.getId(), s.getCompanyName(), s.getSymbol(), s.getQuantity(),
                    s.getPurchasePrice(), s.getCurrentPrice(), s.getSector(), s.getProfitLoss());
                totalPL += s.getProfitLoss();
            }
            pw.println("=".repeat(105));
            pw.printf("Total Portfolio P/L: %+.2f%n", totalPL);
            pw.println("=".repeat(105));
            System.out.println("[FILE] Report exported to " + reportPath);
        } catch (IOException e) {
            System.out.println("[ERROR] Could not export report: " + e.getMessage());
        }
    }
}
