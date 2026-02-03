package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

public abstract class Transaction {
    private Share share;
    private int week;
    private TransactionCalculator calculator;
    private boolean committed;

    protected Transaction(Share share, int week, TransactionCalculator calculator) {
        this.share = share;
        this.week = week;
        this.calculator = calculator;
    }


    public Share getShare() {
        return share;
    }

    public int getWeek() {
        return week;
    }

    public TransactionCalculator getCalculator() {
        return calculator;
    }

    public boolean isCommitted() {
        return committed;
    }

    public <Player> void commit(Player player) {
        committed = true;
    }


}
