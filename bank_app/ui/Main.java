package ui;

import services.BankService;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final BankService bankService = new BankService();

    public static void main(String[] args) {
        showMenu();
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\nWelcome to the Bank App!");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Transfer");
            System.out.println("4. Deposit");
            System.out.println("5. Transaction History");
            System.out.println("6. Simulate Concurrent Transfers");
            System.out.println("7. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    checkBalance();
                    break;
                case "2":
                    withdraw();
                    break;
                case "3":
                    transfer();
                    break;
                case "4":
                    deposit();
                    break;
                case "5":
                    showTransactionHistory();
                    break;
                case "6":
                    simulateConcurrentTransfers();
                    break;
                case "7":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private static void checkBalance() {
        System.out.println("Current Balance: $" + bankService.checkBalance());
    }

    private static void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(scanner.nextLine());
        try {
            bankService.withdraw(amount);
            System.out.println("Withdraw successful! New Balance: $" + bankService.checkBalance());
        } catch (Exception e) {
            System.out.println("Withdrawal failed: " + e.getMessage());
        }
    }

    private static void transfer() {
        System.out.print("Enter amount to transfer: ");
        double amount = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter account number to transfer to: ");
        String accountNumber = scanner.nextLine();
        try {
            bankService.transfer(amount, accountNumber);
            System.out.println("Transfer successful! New Balance: $" + bankService.checkBalance());
        } catch (Exception e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }

    private static void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());
        bankService.deposit(amount);
        System.out.println("Deposit successful! New Balance: $" + bankService.checkBalance());
    }

    private static void showTransactionHistory() {
        System.out.println("Transaction History:");
        System.out.println(bankService.getTransactionHistory());
    }

    private static void simulateConcurrentTransfers() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    double amount = 100; // Monto a transferir
                    String accountNumber = "987654321"; // Número de cuenta ficticio
                    System.out.println("Initiating transfer...");
                    bankService.transfer(amount, accountNumber);
                } catch (Exception e) {
                    System.out.println("Failed transfer: " + e.getMessage());
                }
            }).start();
        }
    }
}
