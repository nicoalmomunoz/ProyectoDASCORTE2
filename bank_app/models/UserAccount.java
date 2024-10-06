package models;

import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private double balance;
    private List<Transaction> transactions;

    public UserAccount() {
        this.balance = 1000.0; // Balance inicial
        this.transactions = new ArrayList<>();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactions.add(new Transaction("Deposit", amount));
    }

    public void withdraw(double amount) throws Exception {
        if (amount > balance) {
            throw new Exception("Insufficient funds");
        }
        balance -= amount;
        transactions.add(new Transaction("Withdrawal", amount));
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
