package no.ntnu.idatt2003.group22.millions.transaction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TransactionArchive {
    private final List<Transaction> transactions;

    public TransactionArchive() {
        this.transactions = new ArrayList<>();
    }

    public boolean add(Transaction transaction){
        Objects.requireNonNull(transaction, "transaction can not be null");
        return transactions.add(transaction);
    }

    public boolean isEmpty(){
        return transactions.isEmpty();
    }

    public List<Transaction> getTransactions(int week){
        validateWeek(week);
        List<Transaction> weeklyTransactions = new ArrayList<>();
        for(Transaction t : transactions){
            if(t.getWeek() == week){
                weeklyTransactions.add(t);
            }
        }
        return List.copyOf(weeklyTransactions);
    }

    public List<Purchase> getPurchases(int week){
        validateWeek(week);
        LinkedList<Purchase> result = new LinkedList<>();
         for(Transaction t : transactions){
            if(t.getWeek() == week && t instanceof Purchase purchase){
                result.add(purchase);
            }
        }
        return List.copyOf(result);

    }

    public List<Sale> getSales(int week){
        validateWeek(week);
        List<Sale> result = new ArrayList<>();
         for(Transaction t : transactions){
            if(t.getWeek() == week && t instanceof Sale sale){
                result.add(sale);
            }
        }
        return List.copyOf(result);
    }

    public int countDistinctWeeks(){
        Set<Integer> weeks = new HashSet<>();
        for(Transaction t : transactions){
            weeks.add(t.getWeek());
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
