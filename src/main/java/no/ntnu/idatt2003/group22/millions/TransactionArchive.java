package no.ntnu.idatt2003.group22.millions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TransactionArchive {
    private final LinkedList<Transaction> transactions;

    public TransactionArchive() {
        this.transactions = new LinkedList<>();
    }

    public boolean add(Transaction transaction){
        Objects.requireNonNull(transaction, "transaction can not be null");
        return transactions.add(transaction);
    }

    public boolean isEmpty(){
        return transactions.isEmpty();
    }

    public List<Transaction> getTransactions(int week){
        LinkedList<Transaction> weeklyTransactions = new LinkedList<>();
        for(Transaction transaction : transactions){
            if(transaction.getWeek() == week){
                weeklyTransactions.add(transaction);
            }
        }
        return List.copyOf(weeklyTransactions);
    }

    public List<Purchase> getPurchases(int week){
        LinkedList<Purchase> result = new LinkedList<>();
         for(Transaction transaction : transactions){
            if(transaction.getWeek() == week && transaction instanceof Purchase purchase){
                result.add(purchase);
            }
        }
        return List.copyOf(result);

    }

    public List<Sale> getSales(int week){
        LinkedList<Sale> result = new LinkedList<>();
         for(Transaction transaction : transactions){
            if(transaction.getWeek() == week && transaction instanceof Sale sale){
                result.add(sale);
            }
        }
        return List.copyOf(result);
    }

    public int countDistinctWeeks(){
        Set<Integer> weeks = new HashSet<>();
        for(Transaction transaction : transactions){
            weeks.add(transaction.getWeek());
        }
        return weeks.size();
    }

}
