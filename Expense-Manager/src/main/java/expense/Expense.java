package main.java.expense;

import java.time.LocalDate;

public class Expense {
    private String name;
    private double amount;
    private LocalDate date;

    public Expense(String name, double amount, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
