package  com.bankcore.model;

import java.time.LocalDate;

/**
 * Represents a transaction with transaction type transaction time id transaction amount and balance after the transaction.
 *
 * @author Haytam
 * @version 1.0
 */

public class Transaction {

    private TransactionType transactionType;
    private LocalDate transactionAt;
    private String idTransaction;
    private String idAccountSender;
    private String idAccountReceiver;
    private double amount;
    private double balanceAfter;

    private static int cpt=0;

    /**
     * Create a new transaction for deposit
     * @param idAccountSender sender
     * @param amount the amount to deposit
     * @param balanceAfter the balance after deposit
     * @param transactionType type transaction
     */
    public Transaction(String idAccountSender,double amount, double balanceAfter,TransactionType transactionType){
        this(idAccountSender, null, amount, balanceAfter, transactionType);

    }
    /**
     * Create a new transaction for transfer
     * @param idAccountSender sender
     * @param idAccountReceiver Receiver
     * @param amount the amount to deposit
     * @param balanceAfter the balance after transfer
     * @param transactionType type transaction
     */
    public Transaction(String idAccountSender,String idAccountReceiver,double amount, double balanceAfter,TransactionType transactionType){
        if (amount <= 0) throw new IllegalArgumentException("Transaction amount must be positive");
        if (idAccountSender == null) throw new IllegalArgumentException("Sender account ID cannot be null");
        cpt++;
        transactionAt=LocalDate.now();
        idTransaction="T-"+cpt;
        this.idAccountSender=idAccountSender;
        this.idAccountReceiver=idAccountReceiver;
        this.amount=amount;
        this.balanceAfter=balanceAfter;
        this.transactionType=transactionType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public LocalDate getTransactionAt() {
        return transactionAt;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public String getIdAccountSender() {
        return idAccountSender;
    }

    public String getIdAccountReceiver() {
        return idAccountReceiver;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionType=" + transactionType +
                ", transactionAt=" + transactionAt +
                ", idTransaction='" + idTransaction + '\'' +
                ", idAccountSender='" + idAccountSender + '\'' +
                ", idAccountReceiver='" + idAccountReceiver + '\'' +
                ", amount=" + amount +
                ", balanceAfter=" + balanceAfter +
                '}';
    }
}