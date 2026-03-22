package com.bankcore.model;

/**
 * Represents a savings account with interest rate.
 * Extends Account with interest calculation capability.
 *
 * @author Haytam
 * @version 1.0
 */
public class SavingsAccount extends Account {

    private double interestRate;

    /**
     * @param ownerName owner of the account
     * @param initBalance starting balance
     * @param interestRate annual interest rate (e.g. 0.03 for 3%)
     */
    public SavingsAccount(String ownerName, double initBalance, double interestRate) {
        super(ownerName, initBalance);
        this.interestRate = interestRate;
    }

    public double getInterestRate() { return interestRate; }
    public void setInterestRate(double interestRate) { this.interestRate = interestRate; }

    /**
     * Applies annual interest to the balance.
     */
    public void applyInterest() {
        double interest = getBalance() * interestRate;
        updateBalance(interest);
    }
    /**
     * Withdraws amount from the savings account.
     * @param amount must be positive and not exceed current balance
     */

    @Override
    public void withdraw(double amount) {

        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        if (amount > getBalance()) throw new RuntimeException("Insufficient funds");
        updateBalance(-amount);

    }
    @Override
    public String toString() {
        return "CheckingAccount : id= " + getAccountId() +
                ", owner=" + getOwnerName() +
                ", balance=" + getBalance() +
                ", interestRate=" + interestRate + " ";
    }
}