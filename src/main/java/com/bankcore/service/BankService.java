package com.bankcore.service;

import com.bankcore.exceptions.AccountNotFoundException;
import com.bankcore.exceptions.InsufficientFundsException;
import com.bankcore.model.*;
import com.bankcore.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

/**
 * Core banking service that handles all business operations.
 * Acts as the main entry point for account management, transactions,
 * and financial operations in the BankCore system.
 *
 * @author Haytam
 * @version 1.0
 */
public class BankService {

    private AccountRepository accountRepository;

    /**
     * Constructs a BankService with the given account repository.
     *
     * @param accountRepository the repository used for account storage and retrieval
     */
    public BankService(AccountRepository accountRepository){
        this.accountRepository=accountRepository;
    }

    /**
     * Creates and persists a new bank account in the system.
     *
     * @param account the account to create
     * @return the created account
     */
   public Account createAccount(Account account){
       accountRepository.save(account);
       return account;
   }

    /**
     * Permanently deletes an account from the system.
     *
     * @param accountId the ID of the account to delete
     * @throws AccountNotFoundException if no account exists with the given ID
     */
   public void deleteAccount(String accountId) throws AccountNotFoundException {
        accountRepository.delete(accountId);
   }

    /**
     * Deposits the specified amount into the account and records the transaction.
     *
     * @param accountId the ID of the account to deposit into
     * @param amount the amount to deposit — must be positive
     * @throws AccountNotFoundException if no account exists with the given ID
     */
   public void deposit(String accountId,double amount)throws AccountNotFoundException{

       Optional<Account> opt = accountRepository.findById(accountId);

       if (opt.isEmpty()) {
           throw new AccountNotFoundException(accountId);
       }
       Account account = opt.get();

       account.deposit(amount);
       double balanceAfter = account.getBalance();


       accountRepository.addTransaction(accountId,new Transaction(accountId,amount,balanceAfter, TransactionType.DEPOSIT));
   }

    /**
     * Withdraws the specified amount from the account and records the transaction.
     *
     * @param accountId the ID of the account to withdraw from
     * @param amount the amount to withdraw — must be positive and not exceed balance
     * @throws AccountNotFoundException if no account exists with the given ID
     * @throws InsufficientFundsException if the amount exceeds the available balance
     */
   public void withdraw(String accountId,double amount) throws AccountNotFoundException, InsufficientFundsException {
       Account account = accountRepository.findById(accountId)
               .orElseThrow(() -> new AccountNotFoundException( accountId));

       account.withdraw(amount);
       double balanceAfter=account.getBalance();

       accountRepository.addTransaction(accountId,new Transaction(accountId,amount,balanceAfter,TransactionType.WITHDRAWAL));
   }

    /**
     * Transfers the specified amount from one account to another.
     * Records a transaction on both the sender and receiver accounts.
     *
     * @param idAccountSender the ID of the account sending the money
     * @param idAccountReceiver the ID of the account receiving the money
     * @param amount the amount to transfer — must be positive
     * @throws AccountNotFoundException if either account does not exist
     * @throws InsufficientFundsException if the sender has insufficient funds
     */
   public void transfer(String idAccountSender,String idAccountReceiver,double amount)throws AccountNotFoundException, InsufficientFundsException{

        Optional<Account> opt = accountRepository.findById(idAccountSender);
        Optional<Account> opt2= accountRepository.findById(idAccountReceiver);

       if (opt.isEmpty()) {
           throw new AccountNotFoundException(idAccountSender);
       }
       if (opt2.isEmpty()) {
           throw new AccountNotFoundException(idAccountReceiver);
       }
       Account accountSender = opt.get();
       Account accountReceiver = opt2.get();

       accountSender.withdraw(amount);
       double balanceAfterAccountSender = accountSender.getBalance();

       accountReceiver.deposit(amount);
       double balanceAfterAccountReceiver = accountReceiver.getBalance();

       accountRepository.addTransaction(idAccountSender,new Transaction(idAccountSender,idAccountReceiver,amount,balanceAfterAccountSender, TransactionType.TRANSFER));
       accountRepository.addTransaction(idAccountReceiver,new Transaction(idAccountSender,idAccountReceiver,amount,balanceAfterAccountReceiver, TransactionType.TRANSFER));

   }

    /**
     * Returns the current balance of the specified account.
     *
     * @param accountId the ID of the account
     * @return the current balance as a double
     * @throws AccountNotFoundException if no account exists with the given ID
     */
   public double getBalance(String accountId) throws AccountNotFoundException {
       Optional<Account> opt = accountRepository.findById(accountId);
       if (opt.isEmpty())throw new AccountNotFoundException(accountId);
       Account account = opt.get();

       return account.getBalance();
   }

    /**
     * Returns the full transaction history of the specified account.
     *
     * @param accountId the ID of the account
     * @return a list of all transactions made by this account
     * @throws AccountNotFoundException if no account exists with the given ID
     */

    public List<Transaction> getTransactionHistory(String accountId) throws AccountNotFoundException {
       return accountRepository.getTransactionHistory(accountId);
   }

    /**
     * Applies the annual interest rate to a savings account balance.
     * The interest amount is calculated internally based on the account's interest rate.
     * Records the operation as an INTEREST transaction.
     *
     * @param accountId the ID of the savings account
     * @throws AccountNotFoundException if no account exists with the given ID
     * @throws IllegalArgumentException if the account is not a SavingsAccount
     */
   public void applyInterest(String accountId) throws AccountNotFoundException{
       Account account = accountRepository.findById(accountId)
               .orElseThrow(() -> new AccountNotFoundException( accountId));
       if (!(account instanceof SavingsAccount savingsAccount)) {
           throw new IllegalArgumentException("Account " + accountId + " is not a SavingsAccount");
       }
//       SavingsAccount savingsAccount = (SavingsAccount) account;
       double balanceBefore= savingsAccount.getBalance();
       savingsAccount.applyInterest();
       double balanceAfter = savingsAccount.getBalance();
       double interestAmount=balanceAfter-balanceBefore;
       accountRepository.addTransaction(accountId, new Transaction(accountId, interestAmount, balanceAfter, TransactionType.INTEREST));
   }

    /**
     * Applies a tax rate to the balance of a savings account.
     * The tax amount is deducted from the current balance.
     * Records the operation as a TAX transaction.
     *
     * @param accountId the ID of the savings account
     * @param taxRate the tax rate to apply — must be between 0 and 1 exclusive
     * @throws AccountNotFoundException if no account exists with the given ID
     * @throws IllegalArgumentException if the account is not a SavingsAccount
     */
   public void applyTax(String accountId, double taxRate) throws AccountNotFoundException{
       Account account = accountRepository.findById(accountId)
               .orElseThrow(() -> new AccountNotFoundException( accountId));
       if (!(account instanceof SavingsAccount savingsAccount)) {
           throw new IllegalArgumentException("Account " + accountId + " is not a SavingsAccount");
       }
       double balanceBefore = savingsAccount.getBalance();
       savingsAccount.applyTax(taxRate);
       double balanceAfter = savingsAccount.getBalance();
       double taxAmount = balanceBefore - balanceAfter;
       accountRepository.addTransaction(accountId, new Transaction(accountId, taxAmount, balanceAfter, TransactionType.TAX));

    }

}



