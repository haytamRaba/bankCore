package com.bankcore.model;

import com.bankcore.exceptions.InsufficientFundsException;

/**
 * Represents a checking account with overdraft and transaction fees.
 * Extends Account with overdraft capability.
 *
 * @author Haytam
 * @version 1.0
 */
public class CheckingAccount extends Account {
    private double overdraftLimit;
    private double transactionFee;
    /**
     * Creates a new checking account.
     *
     * @param ownerName owner of the account
     * @param initBalance starting balance
     * @param overdraftLimit maximum overdraft allowed
     * @param transactionFee fee charged per withdrawal
     */
    public CheckingAccount(String ownerName, double initBalance,double overdraftLimit,double transactionFee){
        super(ownerName,initBalance);
        this.overdraftLimit=overdraftLimit;
        this.transactionFee=transactionFee;
    }
    public double getOverdraftLimit() { return overdraftLimit; }
    public double getTransactionFee() { return transactionFee; }

    public void setOverdraftLimit(double overdraftLimit){
        if (overdraftLimit<0) throw new IllegalArgumentException("overdraftLimit must be > 0");
        this.overdraftLimit=overdraftLimit;
    }
    public void setTransactionFee(double transactionFee){
        if (transactionFee < 0) throw new IllegalArgumentException("Fee must be positive");
        this.transactionFee=transactionFee;
    }
    /**
     * Withdraws amount plus transaction fee from the account.
     * Overdraft is allowed up to the overdraft limit.
     *
     * @param amount must be positive
     * @throws IllegalArgumentException if amount is zero or negative
     * @throws InsufficientFundsException if total cost (amount + fees) exceeds balance and overdraft limit
     */

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount<=0) throw new IllegalArgumentException("must be positive");
        double amountWithFees = amount+transactionFee;
        if (amountWithFees  > getBalance()+ overdraftLimit) throw new InsufficientFundsException(amount,getBalance());
        updateBalance(-amountWithFees);

    }

    @Override
    public String toString() {
        return "CheckingAccount : id= " + getAccountId() +
                ", owner=" + getOwnerName() +
                ", balance=" + getBalance() +
                ", created at="+getCreatedAt()+
                ", overdraftLimit=" + overdraftLimit + " ";
    }
}