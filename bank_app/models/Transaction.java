package models;

import java.util.Date;

public class Transaction {
    private String description;
    private double amount;
    private Date date;

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
        this.date = new Date();
    }

    @Override
    public String toString() {
        return String.format("%s: $%.2f on %s", description, amount, date);
    }
}
