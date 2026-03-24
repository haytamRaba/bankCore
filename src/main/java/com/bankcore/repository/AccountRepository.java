package com.bankcore.repository;

import com.bankcore.exceptions.AccountNotFoundException;
import com.bankcore.model.Account;
import com.bankcore.model.Transaction;

import java.util.*;

/**
 * Reprsent Account history that holds all accounts and all transactions
 *
 * @author Haytam
 */

public class AccountRepository{

    private Map<String, Account> accounts ;
    private Map<String, List<Transaction>> transactionHistory ;

    public AccountRepository() {
        this.accounts = new HashMap<>();
        this.transactionHistory = new HashMap<>();
    }
    /**
     * add acount in accounts and initialize an empty array list for transactionH history.
     * @param account the account that will be added
     */
    public void save(Account account){
        accounts.put(account.getAccountId(), account);
        transactionHistory.put(account.getAccountId(), new ArrayList<>());
    }

    /**
     * find an acout by id
     * @param accountId the id
     * @return Optional if account exist if not exist an optional empty to avoid exception in runtime nullpointer
     */
    public Optional<Account> findById(String accountId) {
        if (accounts.containsKey(accountId)) {
            return Optional.of(accounts.get(accountId));
        }
        return Optional.empty();
    }

    /**
     * find all  accounts in the system.
     * @return accounts
     */
    public List<Account> findAll() { return new ArrayList<>(accounts.values()); }

    /**
     * delete an account by account id if accoun id dont exist throw exception in compile time
     * @param accountId  account id lloking for
     * @throws AccountNotFoundException exception account not found
     */
    public void delete(String accountId)throws AccountNotFoundException{
        if(findById(accountId).isEmpty()) throw new AccountNotFoundException(accountId);
        accounts.remove(accountId);
    }

    /**
     * update an account information by providing an account
     * @param account the new information here only the account id must be the old one
     * @throws AccountNotFoundException exception in compile time account not found
     */
    public void update(Account account )throws AccountNotFoundException{
        if(findById(account.getAccountId()).isEmpty()) throw new AccountNotFoundException(account.getAccountId());
        accounts.put(account.getAccountId(), account);
    }

    /**
     * add a transaction to transactionhistory.
     * @param accountId the account id represent the id of all transactions by an account
     * @param transaction repersent transaction by an account
     * @throws AccountNotFoundException exception in compile time if the accountid not exist
     */
    public void addTransaction(String accountId, Transaction transaction )throws AccountNotFoundException
    {
        if(findById(accountId).isEmpty()) throw new AccountNotFoundException(accountId);
        transactionHistory.get(accountId).add(transaction);
    }

    /**
     * get all tranasction acheived by an account.
     * @param accountId account that acheive transactions
     * @return transactionhistory that made by the account
     * @throws AccountNotFoundException exception in compile time if the accountid not exist
     */
    public List<Transaction> getTransactionHistory(String accountId)throws AccountNotFoundException{
        if(findById(accountId).isEmpty()) throw new AccountNotFoundException(accountId);
        return transactionHistory.get(accountId);
    }



}