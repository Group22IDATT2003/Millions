package no.ntnu.idatt2003.group22.millions.transaction;

import no.ntnu.idatt2003.group22.millions.transaction.calculator.SaleCalculator;
import no.ntnu.idatt2003.group22.millions.transaction.Transaction;
import no.ntnu.idatt2003.group22.millions.transaction.calculator.TransactionCalculator;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a sale transaction where a player sells a share.
 * A sale adds the share to the player's portfolio,
 * calculates the total cost of the transaction,
 * and deducts the total cost from the player's balance.
 */
public class Sale extends Transaction {
    public Sale(Share share, int week){
        super(share, week, (TransactionCalculator) new SaleCalculator(share));
    }

    /**
     * Executes the sale logic for this transaction.
     * This method checks that the player owns the share to be sold,
     * removes the share from the player's portfolio,
     * calculates the total cost of the transaction,
     * and deducts the total cost from the player's balance.
     * If any of these steps fail, an exception is thrown.
     * @param player the player involved in the transaction.
     */
    @Override

    protected void doCommit(Player player){
        Objects.requireNonNull(player, "player can not be null");

        if(!player.getPortfolio().containsShare(getShare())){
            throw new IllegalStateException("Player does not own this share");
        }

        boolean removed = player.getPortfolio().removeShare(getShare());
        if(!removed){
            throw new IllegalStateException("Failed to remove share from portfolio");
        }

        // calculate the total cost of the transaction
        BigDecimal payout = getCalculator().calculateTotal();

        // add money to player
        player.addMoney(payout);

        // add transaction to the archive
        player.getTransactionArchive().add(this);
    }
}
