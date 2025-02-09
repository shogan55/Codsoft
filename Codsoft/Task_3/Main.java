package Task_3;

import java.util.Scanner;

// ATM Operations Interface
interface ATMOperations {
    void deposit(double amount);
    void withdraw(double amount);
    void checkBalance();
}

// Bank Account Class
class BankAccount {
    private double balance;
    private int pin;

    public BankAccount(double initialBalance, int pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public boolean validatePin(int enteredPin) {
        return this.pin == enteredPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: ₹" + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: ₹" + amount);
            return true;
        } else {
            System.out.println("Insufficient balance or invalid amount.");
            return false;
        }
    }
}

// ATM Class
class ATM implements ATMOperations {
    private BankAccount account;

    public ATM(BankAccount account) {
        this.account = account;
    }

    @Override
    public void deposit(double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdraw(double amount) {
        account.withdraw(amount);
    }

    @Override
    public void checkBalance() {
        System.out.println("Current Balance: ₹" + account.getBalance());
    }
}

// Main Class
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize account with ₹5000 balance and PIN 1234
        BankAccount myAccount = new BankAccount(5000.0, 1234);
        ATM atm = new ATM(myAccount);

        System.out.print("Enter PIN: ");
        int enteredPin = scanner.nextInt();

        if (!myAccount.validatePin(enteredPin)) {
            System.out.println("Incorrect PIN! Exiting...");
            scanner.close();
            return;
        }

        while (true) {
            System.out.println("\n--- ATM Menu ---");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    atm.checkBalance();
                    break;
                case 2:
                    System.out.print("Enter amount to deposit: ₹");
                    double depositAmount = scanner.nextDouble();
                    atm.deposit(depositAmount);
                    break;
                case 3:
                    System.out.print("Enter amount to withdraw: ₹");
                    double withdrawAmount = scanner.nextDouble();
                    atm.withdraw(withdrawAmount);
                    break;
                case 4:
                    System.out.println("Thank you for using the ATM!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}

