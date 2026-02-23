package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

/**
 /**
 * Represents a purchase transaction where a player buys a share.
 *
 * A purchase deducts the total cost (calculated by a TransactionCalculator)
 * from the player's balance, adds the share to the player's portfolio,
 * and records the transaction in the transaction archive.
 *
 * An exception is thrown if the player has insufficient funds
 * or if the transaction is committed more than once.
 */
public class Purchase extends Transaction {
    public Purchase(Share share, int week) {
        super(share, week, new PurchaseCalculator(share));
    }

    /**
     * Executes the purchase logic for this transaction.
     *
     * Deducts the total cost from the player's balance,
     * adds the share to the player's portfolio,
     * and records the transaction in the transaction archive.
     *
     * @param player the player involved in the transaction. Cannot be null.
     * @throws IllegalStateException if the player does not have enough money to complete the purchase.
     * @throws NullPointerException  if the player is null.
     * @implSpec This method is called by the commit() method of the Transaction class,
     * which ensures that the transaction is only committed once and that the player object is valid.
     */
    @Override
    public void doCommit(Player player) {
        Objects.requireNonNull(player, "player can not be null");

        // find the total cost of the purchase
        BigDecimal totalCost = getCalculator().calculateTotal();

        // checks if the user has enough money to complete the purchase
        if (player.getMoney().compareTo(totalCost) < 0) {
            throw new IllegalStateException("Not enough cash to complete purchase");
        }

        // withdraws money from the player
        player.withdrawMoney(player.getMoney().subtract(totalCost));

        // places the share in the player's portfolio
        player.getPortfolio().addShare(getShare());

        // place the transaction in the player's transaction archive
        player.getTransactionArchive().add(this);
    }

}
