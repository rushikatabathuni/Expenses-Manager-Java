package main.java.gui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JLabel bankBalanceLabel;
    private JLabel totalExpensesLabel;
    private JLabel totalIncomeLabel;
    private ArrayList<String> expenses;
    private ArrayList<Double> expenseAmounts;
    private ArrayList<String> incomeSources;
    private ArrayList<Double> incomeAmounts;
    private double bankBalance;

    private static final String EXPENSES_FILE = "expenses.csv";
    private static final String INCOMES_FILE = "incomes.csv";
    private static final String BANK_BALANCE_FILE = "bank_balance.csv";  // Separate file for bank balance

    public MainFrame() {
        expenses = new ArrayList<>();
        expenseAmounts = new ArrayList<>();
        incomeSources = new ArrayList<>();
        incomeAmounts = new ArrayList<>();
        bankBalance = 0.0;

        loadData();
        initComponents();
    }

    private void loadData() {
        // Load expenses from CSV
        loadCSV(EXPENSES_FILE, expenses, expenseAmounts);
        // Load incomes from CSV
        loadCSV(INCOMES_FILE, incomeSources, incomeAmounts);
        // Load bank balance from CSV
        loadBankBalance();
    }

    private void loadCSV(String fileName, ArrayList<String> names, ArrayList<Double> amounts) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                names.add(data[0]);
                amounts.add(Double.parseDouble(data[1]));
            }
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + fileName);
        }
    }

    private void loadBankBalance() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BANK_BALANCE_FILE))) {
            String line = reader.readLine();
            if (line != null) {
                bankBalance = Double.parseDouble(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading bank balance from file.");
        }
    }

    private void saveData() {
        // Save expenses to CSV
        saveCSV(EXPENSES_FILE, expenses, expenseAmounts);
        // Save incomes to CSV
        saveCSV(INCOMES_FILE, incomeSources, incomeAmounts);
        // Save bank balance to CSV
        saveBankBalance();
    }

    private void saveCSV(String fileName, ArrayList<String> names, ArrayList<Double> amounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < names.size(); i++) {
                writer.write(names.get(i) + "," + amounts.get(i));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + fileName);
        }
    }

    private void saveBankBalance() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BANK_BALANCE_FILE))) {
            writer.write(Double.toString(bankBalance));
        } catch (IOException e) {
            System.out.println("Error saving bank balance to file.");
        }
    }

    private void initComponents() {
        setTitle("Personal Finance Tracker");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel for main content
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create sub-panels
        JPanel expensePanel = createExpensePanel();
        JPanel incomePanel = createIncomePanel();
        JPanel summaryPanel = createSummaryPanel();

        mainPanel.add(expensePanel);
        mainPanel.add(incomePanel);
        mainPanel.add(summaryPanel);

        // Add mainPanel to center of the frame
        add(mainPanel, BorderLayout.CENTER);

        // Button panel for viewing data
        JPanel viewButtonPanel = new JPanel();
        viewButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton viewExpensesButton = new JButton("View Expenses");
        JButton viewIncomesButton = new JButton("View Incomes");
        JButton viewExpenseNamesButton = new JButton("View Expense Names");
        JButton viewIncomeSourcesButton = new JButton("View Income Sources");

        // Button action listeners
        viewExpensesButton.addActionListener(e -> viewExpenses());
        viewIncomesButton.addActionListener(e -> viewIncomes());
        viewExpenseNamesButton.addActionListener(e -> viewExpenseNames());
        viewIncomeSourcesButton.addActionListener(e -> viewIncomeSources());

        viewButtonPanel.add(viewExpensesButton);
        viewButtonPanel.add(viewIncomesButton);
        viewButtonPanel.add(viewExpenseNamesButton);
        viewButtonPanel.add(viewIncomeSourcesButton);

        // Add viewButtonPanel to the bottom of the frame
        add(viewButtonPanel, BorderLayout.SOUTH);
    }

    // Create expense panel for adding expenses
    private JPanel createExpensePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Add Expense"));

        JTextField nameField = new JTextField();
        JTextField amountField = new JTextField();
        JButton addExpenseButton = new JButton("Add Expense");

        addExpenseButton.addActionListener(e -> {
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText());
            expenses.add(name);
            expenseAmounts.add(amount);
            bankBalance -= amount;
            saveData();  // Save data to CSV after adding an expense
            updateSummary();
        });

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel()); // Empty label for layout
        panel.add(addExpenseButton);

        return panel;
    }

    // Create income panel for adding incomes
    private JPanel createIncomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Add Income"));

        JTextField sourceField = new JTextField();
        JTextField amountField = new JTextField();
        JButton addIncomeButton = new JButton("Add Income");

        addIncomeButton.addActionListener(e -> {
            String source = sourceField.getText();
            double amount = Double.parseDouble(amountField.getText());
            incomeSources.add(source);
            incomeAmounts.add(amount);
            bankBalance += amount;
            saveData();  // Save data to CSV after adding an income
            updateSummary();
        });

        panel.add(new JLabel("Source:"));
        panel.add(sourceField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel()); // Empty label for layout
        panel.add(addIncomeButton);

        return panel;
    }

    // Create summary panel for displaying balance and totals
    private JPanel createSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Financial Summary"));

        bankBalanceLabel = new JLabel();
        totalExpensesLabel = new JLabel();
        totalIncomeLabel = new JLabel();

        panel.add(new JLabel("Bank Balance:"));
        panel.add(bankBalanceLabel);
        panel.add(new JLabel("Total Expenses:"));
        panel.add(totalExpensesLabel);
        panel.add(new JLabel("Total Income:"));
        panel.add(totalIncomeLabel);

        updateSummary();

        return panel;
    }

    // Update summary labels
    private void updateSummary() {
        double totalExpenses = 0.0;
        for (Double amount : expenseAmounts) {
            totalExpenses += amount;
        }

        double totalIncome = 0.0;
        for (Double amount : incomeAmounts) {
            totalIncome += amount;
        }

        bankBalanceLabel.setText(String.format("₹ %.2f", bankBalance));
        totalExpensesLabel.setText(String.format("₹ %.2f", totalExpenses));
        totalIncomeLabel.setText(String.format("₹ %.2f", totalIncome));
    }

    // View expenses
    private void viewExpenses() {
        StringBuilder expenseList = new StringBuilder("Expenses:\n");
        for (int i = 0; i < expenses.size(); i++) {
            expenseList.append(expenses.get(i)).append(": ₹ ").append(expenseAmounts.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(this, expenseList.toString());
    }

    // View incomes
    private void viewIncomes() {
        StringBuilder incomeList = new StringBuilder("Incomes:\n");
        for (int i = 0; i < incomeSources.size(); i++) {
            incomeList.append(incomeSources.get(i)).append(": ₹ ").append(incomeAmounts.get(i)).append("\n");
        }
        JOptionPane.showMessageDialog(this, incomeList.toString());
    }

    // View expense names
    private void viewExpenseNames() {
        StringBuilder expenseNames = new StringBuilder("Expense Names:\n");
        for (String expense : expenses) {
            expenseNames.append(expense).append("\n");
        }
        JOptionPane.showMessageDialog(this, expenseNames.toString());
    }

    // View income sources
    private void viewIncomeSources() {
        StringBuilder incomeSourcesList = new StringBuilder("Income Sources:\n");
        for (String source : incomeSources) {
            incomeSourcesList.append(source).append("\n");
        }
        JOptionPane.showMessageDialog(this, incomeSourcesList.toString());
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame mainFrame = new MainFrame();
            mainFrame.setLocationRelativeTo(null);
            mainFrame.setVisible(true);
        });
    }
}

