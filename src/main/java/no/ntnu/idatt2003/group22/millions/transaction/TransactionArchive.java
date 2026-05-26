package no.ntnu.idatt2003.group22.millions.transaction;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Stores the completed transactions
 */

public class TransactionArchive {
    private final List<Transaction> transactions;

    /**
     * Creates an empty transaction archive
     */
    public TransactionArchive() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Adds a transaction to the archive
     * 
     * @param transaction the transaction to add
     * @return true if the transaction was added
     * @throws IllegalArgumentException if transaction is null
     */
    public boolean add(Transaction transaction){
        if(transaction == null){
            throw new IllegalArgumentException("transaction cannot be null");
        }
        return transactions.add(transaction);
    }

    /**
     * Checks is the archive is empty
     * 
     * @return true if empty
     */
    public boolean isEmpty(){
        return transactions.isEmpty();
    }

    /**
     * Returns all transactions for a given week
     * 
     * @param week the week number
     * @return transactions from the week
     * @throws IllegalArgumentException if week is invalid
     */
    public List<Transaction> getTransactions(int week){
        validateWeek(week);
        List<Transaction> weeklyTransactions = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getWeek() == week){
                weeklyTransactions.add(transaction);
            }
        }
        return List.copyOf(weeklyTransactions);
    }

    /**
     * Returns all purchase transactions for a given week
     * 
     * @param week the week number
     * @return purchase transactions
     * @throws IllegalArgumentException if week is invalid
     */
    public List<Purchase> getPurchases(int week){
        validateWeek(week);
        List<Purchase> purchases = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getWeek() == week && transaction instanceof Purchase purchase){
                purchases.add(purchase);
            }
        }
        return List.copyOf(purchases);

    }

    /**
     * Returns all sale transactions for a given week
     * 
     * @param week the week number
     * @return sale transactions
     * @throws IllegalArgumentException if week is invalid
     */
    public List<Sale> getSales(int week){
        validateWeek(week);
        List<Sale> sales = new ArrayList<>();
        for(Transaction transaction : transactions){
            if(transaction.getWeek() == week && transaction instanceof Sale sale){
                sales.add(sale);
            }
        }
        return List.copyOf(sales);
    }

    /**
     * Counts the number of weeks with transactions
     * 
     * @return number of distinct weeks
     */
    public int countDistinctWeeks(){
        Set<Integer> weeks = new HashSet<>();
        for(Transaction transaction : transactions){
            weeks.add(transaction.getWeek());
        }
        return weeks.size();
    }

    /**
     * Returns all transactions in the archive
     * 
     * @return an immutable list of transactions
     */
    public List<Transaction> getAllTransactions(){
        return List.copyOf(transactions);
    }

    /**
     * Validates the week number
     * 
     * @param week the week to validate
     * @throws IllegalArgumentException if week is less than 1
     */
    private static void validateWeek(int week){
        if(week < 1){
            throw new IllegalArgumentException("week must be >= 1");
        }
    }

}
