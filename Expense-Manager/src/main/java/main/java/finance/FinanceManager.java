package main.java.finance;

import com.opencsv.exceptions.CsvValidationException;
import main.java.expense.Expense;
import main.java.income.Income;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinanceManager {
    private double balance;
    private List<Expense> expenses;
    private List<Income> incomes;

    public FinanceManager(double initialBalance) {
        this.balance = initialBalance;
        this.expenses = new ArrayList<>();
        this.incomes = new ArrayList<>();
    }

    public void addExpense(Expense expense) {
        expenses.add(expense);
        balance -= expense.getAmount();
    }

    public void addIncome(Income income) {
        incomes.add(income);
        balance += income.getAmount();
    }

    public double getBalance() {
        return balance;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    // Save expenses to CSV
    public void saveExpensesToCSV(String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"Name", "Amount", "Date"});
            for (Expense expense : expenses) {
                writer.writeNext(new String[]{
                        expense.getName(),
                        String.valueOf(expense.getAmount()),
                        expense.getDate().toString()
                });
            }
        }
    }

    // Save incomes to CSV
    public void saveIncomesToCSV(String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            writer.writeNext(new String[]{"Source", "Amount", "Date"});
            for (Income income : incomes) {
                writer.writeNext(new String[]{
                        income.getSource(),
                        String.valueOf(income.getAmount()),
                        income.getDate().toString()
                });
            }
        }
    }

    // Load expenses from CSV
    public void loadExpensesFromCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String name = nextLine[0];
                double amount = Double.parseDouble(nextLine[1]);
                LocalDate date = LocalDate.parse(nextLine[2]);
                Expense expense = new Expense(name, amount, date);
                expenses.add(expense);
            }
        }catch (CsvValidationException cv){
            int x=0;
        }
    }

    // Load incomes from CSV
    public void loadIncomesFromCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String source = nextLine[0];
                double amount = Double.parseDouble(nextLine[1]);
                LocalDate date = LocalDate.parse(nextLine[2]);
                Income income = new Income(source, amount, date);
                incomes.add(income);
            }
        }catch (CsvValidationException cv){
            int x=0;
        }
    }
}
