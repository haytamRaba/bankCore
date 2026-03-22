package com.bankcore.exceptions;

/**
 * Represents a checked exception thrown when an account id don't exist.
 * @author Haytam
 */
public class AccountNotFoundException extends Exception{

    private final String accountId;

    /**
     * create a new AccountNotFoundException thrown when an account id don't exist.
     * @param accountId the account id
     */
    public AccountNotFoundException(String accountId){

        super("account not Found with account id= "+accountId);
        this.accountId=accountId;
    }

    public String getAccountId() {return accountId;}
}