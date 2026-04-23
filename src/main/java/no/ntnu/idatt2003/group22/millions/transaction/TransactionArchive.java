package no.ntnu.idatt2003.group22.millions.transaction;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TransactionArchive {
    private final List<Transaction> transactions;

    public TransactionArchive() {
        this.transactions = new ArrayList<>();
    }

    public boolean add(Transaction transaction){
        if(transaction == null){
            throw new IllegalArgumentException("transaction cannot be null");
        }
        return transactions.add(transaction);
    }

    public boolean isEmpty(){
        return transactions.isEmpty();
    }

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

    public int countDistinctWeeks(){
        Set<Integer> weeks = new HashSet<>();
        for(Transaction transaction : transactions){
            weeks.add(transaction.getWeek());
        }
        return weeks.size();
    }

    public List<Transaction> getAllTransactions(){
        return List.copyOf(transactions);
    }

    private static void validateWeek(int week){
        if(week < 1){
            throw new IllegalArgumentException("week must be >= 1");
        }
    }

}
