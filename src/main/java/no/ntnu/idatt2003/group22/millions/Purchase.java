package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Class representing a purchase transaction in the context of a financial game or simulation.
 * This class extends the abstract Transaction class and implements the specific logic
 * for committing a purchase transaction.
 * A purchase transaction involves buying a share, which requires the player to have sufficient funds.
 * The class ensures that the player's balance is updated accordingly
 * and that the purchased share is added to the player's portfolio.
 * The class also handles the recording of the transaction in the player's transaction archive.
 * The class is designed to be used in a simulation where players can buy and sell shares,
 * and it ensures that all operations are performed in a consistent and atomic manner.
 * The class validates the input parameters and throws appropriate exceptions if the player
 * does not have enough funds or if the player object is null.
 * The class relies on the TransactionCalculator to compute the financial details of the transaction,
 * such as the total cost of the purchase, including any fees or taxes that may apply.
 * The class is intended to be used in conjunction with other classes such as Player, Share,
 * and TransactionArchive to manage the state of the simulation and the players' portfolios effectively.
 * The class adheres to the principles of object-oriented design,
 * encapsulating the behavior and state related to a purchase transaction within a single class,
 * and it promotes code reuse and maintainability by extending the Transaction class
 * and utilizing the TransactionCalculator for financial calculations.
 * The class is designed to be robust and to handle edge cases,
 * such as attempting to commit a transaction multiple times or trying to purchase a share without sufficient funds,
 * by throwing appropriate exceptions and ensuring that the player's state remains consistent.
 * The class is also designed to be extensible, allowing for the possibility of adding additional features
 * or types of transactions in the future without requiring significant changes to the existing codebase,
 * as it follows a clear and consistent structure for transactions
 * and relies on well-defined interfaces and abstract classes to manage the behavior of different transaction types.
 */
public class Purchase extends Transaction {
    public Purchase(Share share, int week) {
        super(share, week, new PurchaseCalculator(share));
    }

    /**
     * Performs the commit logic for a purchase transaction.
     * This method checks if the player has enough money to complete the purchase,
     * withdraws the necessary amount from the player's balance,
     * adds the purchased share to the player's portfolio,
     * and records the transaction in the player's transaction archive.
     * The method ensures that all operations are performed atomically
     * and that the player's state is updated consistently.
     * The method throws an IllegalStateException
     * if the player does not have enough money to complete the purchase.
     * The method also validates that the player object is not null
     * before proceeding with the transaction.
     *
     * @param player the player involved in the transaction. Cannot be null.
     * @throws IllegalStateException if the player does not have enough money to complete the purchase.
     * @throws NullPointerException  if the player is null.
     * @implSpec This method is called by the commit() method of the Transaction class,
     * which ensures that the transaction is only committed once and that the player object is valid.
     */
    @Override
    protected void doCommit(Player player) {
        Objects.requireNonNull(player, "player can not be null");

        // find the total cost of the purchase
        BigDecimal totalCost = getCalculator().calculateNetAmount();

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
