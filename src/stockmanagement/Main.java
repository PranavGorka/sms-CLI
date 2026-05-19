package stockmanagement;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final StockManager manager = new StockManager();

    public static void main(String[] args) {
        printBanner();
        int choice;
        do {
            printMainMenu();
            choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1  -> addStockMenu();
                case 2  -> manager.viewAllStocks();
                case 3  -> updateStockMenu();
                case 4  -> deleteStockMenu();
                case 5  -> searchMenu();
                case 6  -> sortMenu();
                case 7  -> manager.showPortfolioSummary();
                case 8  -> viewByIdMenu();
                case 9  -> { manager.exportReport();
                             System.out.println("[INFO] Report saved to data/report.txt"); }
                case 0  -> System.out.println("\nExiting... Goodbye!\n");
                default -> System.out.println("\n[WARN] Invalid option. Try again.");
            }
        } while (choice != 0);
        sc.close();
    }

    // ─────────────────────────────────────────────
    //  MENUS
    // ─────────────────────────────────────────────

    private static void printBanner() {
        System.out.println("\n" + "=".repeat(55));
        System.out.println("       STOCK MANAGEMENT SYSTEM  v1.0");
        System.out.println("       CLI-Based Java CRUD Application");
        System.out.println("       4th Sem CSE — Assignment 1");
        System.out.println("=".repeat(55));
    }

    private static void printMainMenu() {
        System.out.println("\n┌─────────────────────────────────────┐");
        System.out.println("│           M A I N   M E N U          │");
        System.out.println("├─────────────────────────────────────┤");
        System.out.println("│  1. Add Stock                        │");
        System.out.println("│  2. View All Stocks                  │");
        System.out.println("│  3. Update Stock                     │");
        System.out.println("│  4. Delete Stock                     │");
        System.out.println("│  5. Search Stock                     │");
        System.out.println("│  6. Sort Stocks                      │");
        System.out.println("│  7. Portfolio Summary                │");
        System.out.println("│  8. View Stock by ID                 │");
        System.out.println("│  9. Export Report to File            │");
        System.out.println("│  0. Exit                             │");
        System.out.println("└─────────────────────────────────────┘");
    }

    private static void addStockMenu() {
        System.out.println("\n--- ADD NEW STOCK ---");
        String company = readString("Company Name    : ");
        String symbol  = readString("Stock Symbol    : ");
        int    qty     = readPositiveInt("Quantity (shares): ");
        double buyPx   = readPositiveDouble("Purchase Price  : ");
        double curPx   = readPositiveDouble("Current Price   : ");
        String sector  = readString("Sector          : ");
        manager.addStock(company, symbol, qty, buyPx, curPx, sector);
    }

    private static void updateStockMenu() {
        System.out.println("\n--- UPDATE STOCK ---");
        int id = readInt("Enter Stock ID to update: ");
        if (!manager.stockExists(id)) {
            System.out.println("[ERROR] Stock ID " + id + " not found.");
            return;
        }
        System.out.println("Enter new values (press Enter to keep existing is NOT supported — enter all fields):");
        String company = readString("New Company Name    : ");
        String symbol  = readString("New Stock Symbol    : ");
        int    qty     = readPositiveInt("New Quantity        : ");
        double buyPx   = readPositiveDouble("New Purchase Price  : ");
        double curPx   = readPositiveDouble("New Current Price   : ");
        String sector  = readString("New Sector          : ");
        manager.updateStock(id, company, symbol, qty, buyPx, curPx, sector);
    }

    private static void deleteStockMenu() {
        System.out.println("\n--- DELETE STOCK ---");
        int id = readInt("Enter Stock ID to delete: ");
        System.out.print("Are you sure you want to delete ID " + id + "? (yes/no): ");
        String confirm = sc.nextLine().trim();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            if (!manager.deleteStock(id)) {
                System.out.println("[ERROR] Stock ID " + id + " not found.");
            }
        } else {
            System.out.println("[INFO] Delete cancelled.");
        }
    }

    private static void searchMenu() {
        System.out.println("\n--- SEARCH STOCKS ---");
        System.out.println("1. Search by Company Name");
        System.out.println("2. Search by Symbol");
        System.out.println("3. Search by Sector");
        int opt = readInt("Choose search type: ");
        switch (opt) {
            case 1 -> { String kw = readString("Enter company name keyword: ");
                        manager.searchByCompanyName(kw); }
            case 2 -> { String sym = readString("Enter symbol: ");
                        manager.searchBySymbol(sym); }
            case 3 -> { String sec = readString("Enter sector keyword: ");
                        manager.searchBySector(sec); }
            default -> System.out.println("[WARN] Invalid option.");
        }
    }

    private static void sortMenu() {
        System.out.println("\n--- SORT STOCKS ---");
        System.out.println("1. Sort by Company Name (A-Z)");
        System.out.println("2. Sort by Current Price (High to Low)");
        System.out.println("3. Sort by Profit/Loss (Best First)");
        System.out.println("4. Sort by Quantity (High to Low)");
        int opt = readInt("Choose sort option: ");
        switch (opt) {
            case 1 -> manager.sortByCompanyName();
            case 2 -> manager.sortByCurrentPrice();
            case 3 -> manager.sortByProfitLoss();
            case 4 -> manager.sortByQuantity();
            default -> System.out.println("[WARN] Invalid option.");
        }
    }

    private static void viewByIdMenu() {
        int id = readInt("Enter Stock ID to view: ");
        manager.viewStockById(id);
    }

    // ─────────────────────────────────────────────
    //  INPUT HELPERS
    // ─────────────────────────────────────────────

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = sc.nextInt();
                sc.nextLine();
                return val;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("[ERROR] Please enter a valid integer.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int val = readInt(prompt);
            if (val > 0) return val;
            System.out.println("[ERROR] Value must be greater than 0.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = sc.nextDouble();
                sc.nextLine();
                if (val > 0) return val;
                System.out.println("[ERROR] Value must be greater than 0.");
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("[ERROR] Please enter a valid number.");
            }
        }
    }

    private static String readString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String val = sc.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println("[ERROR] Field cannot be empty.");
        }
    }
}
