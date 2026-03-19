package no.ntnu.idatt2003.group22.millions;

import java.util.Objects;

/**
 * Abstract class representing a financial transaction involving shares.
 * This class provides a template for specific types of transactions,
 * such as purchases and sales,
 * and ensures that all transactions adhere to a consistent structure and behavior.
 *
 */
public abstract class Transaction {
    private final Share share;
    private final int week;
    private final TransactionCalculator calculator;
    protected boolean committed = false;

    /**
     * Constructor for Transaction.
     * This constructor initializes the transaction with the specified share, week, and calculator.
     * The constructor performs validation to ensure that the share and calculator are not null
     * and that the week is greater than or equal to 1.
     *
     * @param share      the share involved in the transaction. Cannot be null.
     * @param week       the week of the transaction. Must be >= 1.
     * @param calculator the calculator to compute the transaction's financial details. Cannot be null.
     */
    protected Transaction(Share share, int week, TransactionCalculator calculator) {
        this.share = Objects.requireNonNull(share, "share can not be null");
        this.calculator = Objects.requireNonNull(calculator, "calculator can not be null");

        if (week < 1) {
            throw new IllegalArgumentException("week must be >= 1");
        }
        this.week = week;
    }

    /**
     * Commits the transaction.
     * This method checks if the transaction has already been committed
     * and throws an exception if it has.
     * Otherwise, it calls the doCommit method to perform the actual commit logic.
     * The doCommit method is implemented by subclasses
     * to define specific commit behavior for different transaction types.
     * After the commit logic is executed, the transaction is marked as committed.
     *
     * @param player the player involved in the transaction. Cannot be null.
     * @throws IllegalStateException if the transaction has already been committed.
     * @throws NullPointerException  if the player is null.
     */
    public final void commit(Player player) {
        Objects.requireNonNull(player, "player can not be null");
        if (committed) {

            throw new IllegalStateException("Transaction already committed");
        }
        doCommit(player);
        committed = true;
    }

    /**
     * Returns the share involved in the transaction.
     * This method provides access to the share associated with the transaction,
     * allowing subclasses and external code to retrieve information about the share.
     *
     * @return the share involved in the transaction.
     */
    public final Share getShare() {
        return share;
    }

    /**
     * This method provides access to the week of the transaction.
     * It allows subclasses and external code to retrieve the timing of the transaction,
     * which can be important for calculating the transaction's financial details
     * and for tracking the player's activities over time.
     *
     * @return the week of the transaction.
     */
    public final int getWeek() {
        return week;
    }

    /**
     * Returns the calculator used to compute the transaction's financial details.
     * This method allows subclasses to access the calculator instance
     * used to compute the transaction details.
     *
     * @return the calculator used for this transaction.
     */
    public final TransactionCalculator getCalculator() {
        return calculator;
    }

    /**
     * Returns true if the transaction has been committed, false otherwise.
     * This method allows subclasses to check whether the transaction
     * has already been committed.
     * This method is intended to be used by subclasses
     * to ensure that certain actions are only performed on committed transactions
     * or to prevent actions from being performed on transactions that have not yet been committed.
     *
     * @return true if the transaction has been committed, false otherwise.
     */
    public final boolean isCommitted() {
        return committed;
    }

    /**
     * This method is an abstract method that subclasses must implement
     * to define the specific logic for committing the transaction.
     * The commit method calls the doCommit method after it checks that the transaction
     * has not already been committed.
     * Subclasses will implement this method to perform the necessary actions
     * to complete the transaction, such as updating the player's portfolio,
     * adjusting their cash balance, and recording the transaction in their transaction history.
     *
     * @param player the player involved in the transaction.
     */
    protected abstract void doCommit(Player player);

}

