package no.ntnu.idatt2003.group22.millions;

import java.util.Objects;

public class Transaction2 {
    private final Share share;
    private final int week;
    private final TransactionCalculator calculator;

    private boolean commited = false;

    protected Transaction2(Share share, int week, TransactionCalculator calculator) {
        this.share = Objects.requireNonNull(share, "share can not be null");
        this.calculator = Objects.requireNonNull(calculator, "calculator can not be null");

        if (week < 1) {
            throw new IllegalArgumentException("week must be >= 1");
        }
        this.week = week;
    }

    public final void commit(Player player) {
        Objects.requireNonNull(player, "player can not be null");
        if(commited){
            throw new IllegalStateException("Transaction already commited");
        }
        doCommit(player);

        commited = true;
    }

    protected abstract void doCommit(Player player);

    public final boolean isCommited() {
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
