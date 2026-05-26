package no.ntnu.idatt2003.group22.millions.transaction;

import no.ntnu.idatt2003.group22.millions.transaction.calculator.PurchaseCalculator;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;

/**
 * Represents a purchase transaaction
 */
public class Purchase extends Transaction {

    /**
     * Creates a purchase transaction
     * 
     * @param share the purchased share
     * @param week the transaction week
     */
    public Purchase(Share share, int week) {
        super(share, week, new PurchaseCalculator(share));
    }

    /**
     * Completes the purchase transaction
     * 
     * @param player the player making the purchase
     * @throws IllegalArgumentException if player is null
     * @throws IllegalStateException if the player lacks funds
     */
    @Override
    protected void doCommit(Player player) {
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }

        BigDecimal totalCost = getCalculator().calculateTotal();

        if (player.getMoney().compareTo(totalCost) < 0) {
            throw new IllegalStateException("Not enough cash to complete purchase");
        }

        player.withdrawMoney(totalCost);

        player.getPortfolio().addShare(getShare());

        player.getTransactionArchive()
        .add(this);
    }

}
