package no.ntnu.idatt2003.group22.millions;

import java.util.LinkedList;
import java.util.List;

public class TransactionArchive {
    private final LinkedList<Transaction> transactions;

    public TransactionArchive() {
        transactions = new LinkedList<>();
    }
    public boolean add(Transaction transaction){
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
        return weeklyTransactions;
    }
    public List<Purchase> getPurchase(int week){
        return new LinkedList<>();
    }
    public List<Sale> getSale(int week){
        return new LinkedList<>();
    }
    public int countDistinctWeeks(){
        return 0;
    }

}
