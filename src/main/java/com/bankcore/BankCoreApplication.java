package com.bankcore;

import com.bankcore.exceptions.AccountNotFoundException;
import com.bankcore.exceptions.InsufficientFundsException;
import com.bankcore.model.*;
import com.bankcore.repository.AccountRepository;
import com.bankcore.service.BankService;

import java.util.*;

public class BankCoreApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static AccountRepository repository = new AccountRepository();
    private static BankService bankService= new BankService(repository);

    public static void main(String[] args)  {
        // create account
        createAccount(createCheckingAccount());
        createAccount(createSavingAccount());
        // transfer between two accounts
        transfer( );
        // deposit amount of money
        deposit();
        // withdraw
        withdraw();
        // apply interest
        applyInterest();
        // apply Tax
        applyTax();
        // get transaction history
        List<Transaction> history = printTransactionHistory();
        history.forEach(System.out::println);

    }
    private static SavingsAccount createSavingAccount(){
        System.out.println("==== creation Saving Account =====");

        System.out.print("enter Owner Name :"); String ownerName=scanner.nextLine();
        System.out.print("enter your initial balance : "); double initBalance= scanner.nextDouble();
        System.out.print("interest Rate : "); double interestRate= scanner.nextDouble();
        scanner.nextLine();
        return  new SavingsAccount(ownerName,initBalance,interestRate);
    }
    private static CheckingAccount createCheckingAccount(){
        System.out.println("==== creation Checking Account =====");

        System.out.print("enter Owner Name :"); String ownerName=scanner.nextLine();
        System.out.print("enter your initial balance : "); double initBalance= scanner.nextDouble();
        System.out.print("overdraft Limit  : "); double overdraftLimit= scanner.nextDouble();
        System.out.print("transaction Fee  : "); double transactionFee= scanner.nextDouble();
        scanner.nextLine();

        return  new CheckingAccount(ownerName,initBalance,overdraftLimit,transactionFee);
    }
    private static void createAccount(Account account){
        bankService.createAccount(account);
        System.out.println(account);
    }
    private static void transfer(){
        int tentative=0;
        boolean succes=false;

        while(tentative<3 && !succes){
            try{
                System.out.println("==== Transfer amount of money ====");
                System.out.print("Sender account ID:"); String senderAccountId=scanner.nextLine();
                System.out.print("receiver account ID:"); String receiverAccountId=scanner.nextLine();
                System.out.print("Amount to transfer:"); double amount=scanner.nextDouble();
                scanner.nextLine();

                bankService.transfer(senderAccountId,receiverAccountId,amount);

                System.out.println("transaction pass with success !");
                succes = true;
            }  catch (AccountNotFoundException e) {
                tentative++;
                System.err.println(" account dont exist (Tentative " + tentative + "/3)");
                System.err.println("check account id and try again.");
            } catch (InsufficientFundsException e) {
                System.err.println(" insufficient funds !");
                System.err.println("transaction failed.");
                break;
            }
        }
    }
    public static void deposit(){
        int tentative=0;
        boolean succes=false;
        System.out.println("=== deposit amount of money ===");
        while (tentative<3 && !succes){
            try {
                System.out.print("enter account ID :");String accountId=scanner.nextLine();
                System.out.print("enter amount :");double amount=scanner.nextDouble();
                scanner.nextLine();
                bankService.deposit(accountId,amount);
                succes=true;
                System.out.println("transaction pass with success !");
            } catch (AccountNotFoundException e) {
                tentative++;
                System.err.println(" account dont exist (Tentative " + tentative + "/3)");
                System.err.println("check account id and try again.");
            }
        }

    }
    public static void withdraw(){
        int tentative=0;
        boolean succes=false;
        System.out.println("== withdraw amount of money == ");
        while (tentative<3 && !succes){
            try {
                System.out.print("enter account ID :");String accountId=scanner.nextLine();
                System.out.print("enter amount :");double amount=scanner.nextDouble();
                scanner.nextLine();

                bankService.withdraw(accountId,amount);

                succes=true;

                System.out.println("transaction pass with success !");
            } catch (AccountNotFoundException e) {
                tentative++;
                System.err.println(" account dont exist (attempts " + tentative + "/3)");
                System.err.println("check account id and try again.");
            }
            catch (InsufficientFundsException e){
                System.err.println(" insufficient funds !");
                System.err.println("transaction failed.");
                break;
            }
        }
    }
    public static void applyInterest(){
        int tentative=0;
        boolean succes=false;
        System.out.println("== apply  interest  == ");
        while (tentative<3 && !succes){
            try {
                System.out.print("enter account ID :");String accountId=scanner.nextLine();

                bankService.applyInterest(accountId);
                succes=true;

                System.out.println("interest applied with success !");
            } catch (AccountNotFoundException e) {
                tentative++;
                System.err.println(" account dont exist (attempts " + tentative + "/3)");
                System.err.println("check account id and try again.");
            } catch (IllegalArgumentException e) {
                // no saving Account
            System.err.println(e.getMessage());
            break;
        }

        }
    }
    public static void applyTax(){
        int tentative=0;
        boolean succes=false;
        System.out.println("== apply  Tax  == ");
        while (tentative<3 && !succes){
            try {
                System.out.print("enter account ID :");String accountId = scanner.nextLine();
                System.out.print("Tax Rate : "); double taxRate = scanner.nextDouble();
                scanner.nextLine();

                bankService.applyTax(accountId,taxRate);

                succes=true;
                System.out.println("tax applied with success !");

            } catch (AccountNotFoundException e) {
                tentative++;
                System.err.println(" account dont exist (attempts " + tentative + "/3)");
                System.err.println("check account id and try again.");
            }
        }
    }
    public static List<Transaction> printTransactionHistory(){
        int tentative=0;

        System.out.println("== Transaction History  == ");
        while (tentative<3 ){
            try {
                System.out.print("enter account ID :");String accountId = scanner.nextLine();

                return bankService.getTransactionHistory(accountId);

            } catch (AccountNotFoundException e) {
                tentative++;
                System.err.println(" account dont exist (attempts " + tentative + "/3)");
                System.err.println("check account id and try again.");
            }

        }
        System.err.println("Failed to retrieve transaction history after 3 attempts.");
        return Collections.emptyList();
    }

}