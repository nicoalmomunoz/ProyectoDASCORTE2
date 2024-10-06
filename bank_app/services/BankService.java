package services;

import models.UserAccount;
import resilience.Bulkhead;
import resilience.CircuitBreaker;
import resilience.Retry;

import java.util.Random;

public class BankService {
    private final UserAccount userAccount;
    private final Retry retry;
    private final CircuitBreaker circuitBreaker;
    private final Bulkhead bulkhead;

    public BankService() {
        this.userAccount = new UserAccount();
        this.retry = new Retry(3); // Número máximo de reintentos
        this.circuitBreaker = new CircuitBreaker(3, 10000); // 3 fallos, timeout de 10 segundos
        this.bulkhead = new Bulkhead(3); // Máximo de 3 solicitudes concurrentes
    }

    public double checkBalance() {
        return userAccount.getBalance();
    }

    public void deposit(double amount) {
        userAccount.deposit(amount);
    }

    public void withdraw(double amount) throws Exception {
        userAccount.withdraw(amount);
    }

    public double transfer(double amount, String accountNumber) throws Exception {
        if (!circuitBreaker.allowRequest()) {
            throw new Exception("Transfer service is unavailable");
        }
        
        bulkhead.call(() -> {
            try {
                retry.call(() -> {
                    // Simulando que el servicio puede fallar con un 30% de probabilidad
                    if (new Random().nextDouble() < 0.3) {
                        circuitBreaker.recordFailure(); // Registrar fallo en el circuito
                        throw new Exception("Transfer service is unavailable");
                    }
                    withdraw(amount);
                    System.out.println("Transferred $" + amount + " to account " + accountNumber);
                    return amount;
                });
            } catch (Exception e) {
                System.out.println("Failed to transfer: " + e.getMessage());
            }
        });

        return userAccount.getBalance();
    }

    public String getTransactionHistory() {
        StringBuilder history = new StringBuilder();
        for (var transaction : userAccount.getTransactions()) {
            history.append(transaction.toString()).append("\n");
        }
        return history.toString();
    }
}
