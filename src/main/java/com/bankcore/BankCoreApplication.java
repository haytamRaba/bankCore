package com.bankcore;

import com.bankcore.model.CheckingAccount;
import com.bankcore.model.SavingsAccount;
import com.bankcore.model.Transaction;
import com.bankcore.model.TransactionType;

public class BankCoreApplication {
    public static void main(String[] args) {

        // test SavingsAccount
        SavingsAccount savings = new SavingsAccount("Haytam", 1000, 0.03);
        savings.deposit(500);
        savings.withdraw(200);
        savings.applyInterest();
        System.out.println(savings);

        // test CheckingAccount
        CheckingAccount checking = new CheckingAccount("Ali", 500, 300, 5);
        checking.deposit(100);
        checking.withdraw(200);
        System.out.println(checking);

        // test Transaction
        Transaction t = new Transaction(savings.getAccountId(), 200, savings.getBalance(), TransactionType.WITHDRAWAL);
        System.out.println(t);
    }
}