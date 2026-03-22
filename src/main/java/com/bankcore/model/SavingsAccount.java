package com.bankcore.model;

import com.bankcore.interfaces.Interestable;
import com.bankcore.interfaces.Taxable;

/**
 * Represents a savings account with interest rate.
 * Extends Account with interest calculation capability.
 *
 * @author Haytam
 * @version 1.0
 */
public class SavingsAccount extends Account implements Interestable, Taxable {

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
        return "SavingAccount : id= " + getAccountId() +
                ", owner=" + getOwnerName() +
                ", created at= "+ getCreatedAt() +
                ", balance=" + getBalance() +
                ", interestRate=" + interestRate + " ";
    }

    /**
     * apply tax in the current balance
     * @param taxRate tax from external
     */
    @Override
    public void applyTax(double taxRate) {
        if (taxRate <= 0 || taxRate >= 1) throw new IllegalArgumentException("Tax rate must be between 0 and 1");
        updateBalance(-(getBalance()*taxRate));
    }

}