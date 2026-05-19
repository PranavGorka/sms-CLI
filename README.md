# 📈 Stock Management System

A **CLI-Based Java CRUD Application** built for 4th Semester CSE Engineering Practical (Assignment 1).

---

## 📌 Features

| Feature | Description |
|---|---|
| ➕ Add Stock | Add a new stock with company, symbol, quantity, prices, sector |
| 📋 View All | Display all stocks in a formatted table |
| ✏️ Update | Modify any stock record by ID |
| 🗑️ Delete | Remove a stock with confirmation prompt |
| 🔍 Search | Search by Company Name, Symbol, or Sector |
| 📊 Sort | Sort by Name, Price, Profit/Loss, or Quantity |
| 💼 Portfolio | Quick summary of total investment, value, P/L |
| 💾 File Handling | Auto-saves to `data/stocks.csv`; export report to `data/report.txt` |

---

## 🗂️ Project Structure

```
StockManagementSystem/
├── src/
│   └── stockmanagement/
│       ├── Main.java          ← Entry point, menu-driven CLI
│       ├── Stock.java         ← Data model (fields, CSV serialization)
│       ├── StockManager.java  ← Business logic (CRUD, search, sort)
│       └── FileHandler.java   ← File I/O (CSV read/write, report export)
├── data/
│   ├── stocks.csv             ← Auto-generated persistent storage
│   └── report.txt             ← Exported report (generated on demand)
└── README.md
```

---

## 🚀 How to Run

### Prerequisites
- Java JDK 11 or higher
- VS Code with [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

### Steps

**Option A — VS Code**
1. Open the `StockManagementSystem` folder in VS Code.
2. Open `src/stockmanagement/Main.java`.
3. Click **Run** (▶) above the `main` method.

**Option B — Terminal / Command Line**
```bash
# From the StockManagementSystem directory
javac -d out src/stockmanagement/*.java
java -cp out stockmanagement.Main
```

> ⚠️ Run from the **project root** so that the `data/` folder is created in the right place.

---

## 📋 Menu Options

```
1. Add Stock
2. View All Stocks
3. Update Stock
4. Delete Stock
5. Search Stock
6. Sort Stocks
7. Portfolio Summary
8. View Stock by ID
9. Export Report to File
0. Exit
```

---

## 📁 File Handling

- All stocks are **automatically persisted** to `data/stocks.csv` after every Add / Update / Delete.
- On startup the app **loads existing data** from the CSV file.
- Use option **9** to export a formatted plain-text report to `data/report.txt`.

---

## 🛠️ Technologies Used

- Java (JDK 11+)
- `ArrayList` for in-memory storage
- `Scanner` for CLI input
- `FileWriter` / `BufferedReader` for CSV persistence
- Java Streams for search & sort

---

## 📚 Assignment Details

- **Course:** 4th Semester CSE
- **Subject:** Java Programming Practical
- **Assignment:** CLI-Based CRUD Application (Student-Defined Problem)
- **Domain:** Stock Management System
