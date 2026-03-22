package com.bankcore.exceptions;

/**
 * Represents a checked exception thrown when a withdrawal amount exceeds the available account balance.
 * Extends Exception .
 * @author Haytam
 */
public class InsufficientFundsException extends Exception {

    private final double requestedAmount;
    private final double availableBalance;

    /**
     * create a new InsufficientFundsException thrown when a withdrawal amount exceeds the available account balance.
     * @param requestedAmount requested Amount to withdraw
     * @param availableBalance available balance
     */
    public InsufficientFundsException(double requestedAmount, double availableBalance) {
        super("Insufficient funds: requested " + requestedAmount +
                " but available balance is " + availableBalance);
        this.requestedAmount = requestedAmount;
        this.availableBalance = availableBalance;
    }



    public double getRequestedAmount() { return requestedAmount; }
    public double getAvailableBalance() { return availableBalance; }
}