package no.ntnu.idatt2003.group22.millions.transaction;

import no.ntnu.idatt2003.group22.millions.transaction.calculator.TransactionCalculator;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;

/**
 * Represents a financial transaction involving shares
 */
public abstract class Transaction {
    private final Share share;
    private final int week;
    private final TransactionCalculator calculator;
    protected boolean committed;

    /**
     * Creates a transaction with share, week and calculator
     *
     * @param share the share involved
     * @param week the transaction week
     * @param calculator the transaction calculator
     * @throws IllegalArgumentException if values are invalid
     */
    protected Transaction(Share share, int week, TransactionCalculator calculator) {
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }
        this.share = share;

        if(calculator == null){
            throw new IllegalArgumentException("calculator cannot be null");
        }
        this.calculator = calculator;

        if (week < 1) {
            throw new IllegalArgumentException("week must be >= 1");
        }
        this.week = week;
        this.committed = false;
    }

    /**
     * Commits the transaction
     *
     * @param player the player performing the transaction
     * @throws IllegalArgumentException if palyer is null
     * @throws IllegalStateException if transaction is already committed
     */
    public final void commit(Player player) {
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }
        if (committed) {
            throw new IllegalStateException("Transaction already committed");
        }
        doCommit(player);
        committed = true;
    }


    /**
     * Executes transaction spesific logic
     *
     * @param player the player performing the transaction.
     */
    protected abstract void doCommit(Player player);

    /**
     * Returns the transaction share
     *
     * @return the share
     */
    public final Share getShare(){
        return share;
    }

    /**
     * Returns the transaction week
     *
     * @return the week number
     */
    public final int getWeek() {
        return week;
    }


    /**
     * Returns the transaction calculator
     *
     * @return the calculator
     */
    public final TransactionCalculator getCalculator() {
        return calculator;
    }

    /**
     * Checks if the transaction is committed
     *
     * @return true if committed
     */
    public final boolean isCommitted() {
        return committed;
    }

    /**
     * Returns the gross transaction value
     * 
     * @return the gross value
     */
    public final BigDecimal calculateGross(){
        return calculator.calculateGross();
    }

    /**
     * Returns the transaction commission
     * 
     * @return the commission
     */
    public final BigDecimal calculateCommission() {
        return calculator.calculateCommission();
    }

    /**
     * Returns the transaction tax
     * 
     * @return the tax
     */
    public final BigDecimal calculateTax(){
        return calculator.calculateTax();
    }

    /**
     * Returns the total transaction value
     * 
     * @return the total value
     */
    public final BigDecimal calculateTotal(){
        return calculator.calculateTotal();
    }


}

