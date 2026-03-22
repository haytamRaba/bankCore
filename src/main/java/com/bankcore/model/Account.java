package com.bankcore.model;

import com.bankcore.exceptions.InsufficientFundsException;

import java.time.LocalDate;

/**
 * Represents a bank account.
 * Base class for all account types in BankCore system.
 *
 * @author Haytam
 * @version 1.0
 */
 public abstract class Account{


    private String accountId;
    private String ownerName;
    private double balance;
    private LocalDate createdAt;


    private static int cpt=0;

     public Account(String ownerName, double initBalance) {
         cpt++;
         this.accountId="A-"+cpt;
         this.ownerName=ownerName;
         this.balance=initBalance;
         this.createdAt=LocalDate.now();
     }

     public String getAccountId() {
         return accountId;
     }

     public String getOwnerName() {
         return ownerName;
     }

     public LocalDate getCreatedAt(){ return createdAt;}
     public void setOwnerName(String ownerName) {
         this.ownerName = ownerName;
     }

     public double getBalance() {
         return balance;
     }
     protected void updateBalance(double amount) {
         this.balance += amount;
     }
    /**
     * Withdraws amount from the account.
     *
     * @param amount the amount to withdraw
     */
     public abstract void withdraw(double amount)throws InsufficientFundsException;

     /**
     * Deposits amount into the account.
     * @param amount must be positive
     */

      public void deposit(double amount){

          if (amount<=0){
              throw new IllegalArgumentException("deposit must be positive");
          }
          this.balance+=amount;
     }

 }