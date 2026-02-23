package no.ntnu.idatt2003.group22.millions;

import java.util.Objects;

public abstract class Transaction {
    private final Share share;
    private final int week;
    private final TransactionCalculator calculator;

    private boolean committed = false;

    protected Transaction(Share share, int week, TransactionCalculator calculator) {
        this.share = Objects.requireNonNull(share, "share can not be null");
        this.calculator = Objects.requireNonNull(calculator, "calculator can not be null");

        if (week < 1) {
            throw new IllegalArgumentException("week must be >= 1");
        }
        this.week = week;
    }

    public final void commit(Player player) {
        Objects.requireNonNull(player, "player can not be null");
        if(committed){
            throw new IllegalStateException("Transaction already commited");
        }
        doCommitt(player);

        committed = true;
    }

    protected abstract void doCommitt(Player player);

    public final boolean isCommitted() {
        return committed;
    }

    public final Share getShare(){
        return share;
    }
    
    public final int getWeek(){
        return week;
    }

    public final TransactionCalculator getCalculator(){
        return calculator;
    }
    
}
