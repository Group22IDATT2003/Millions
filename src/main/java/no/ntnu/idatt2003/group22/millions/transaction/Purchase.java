package no.ntnu.idatt2003.group22.millions.transaction;

import no.ntnu.idatt2003.group22.millions.transaction.calculator.PurchaseCalculator;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;

 /**
 *
 */
public class Purchase extends Transaction {
    public Purchase(Share share, int week) {
        super(share, week, new PurchaseCalculator(share));
    }

    /**
     * Executes the purchase logic for this transaction.
     */
    @Override
    protected void doCommit(Player player) {
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }

        // find the total cost of the purchase
        BigDecimal totalCost = getCalculator().calculateTotal();

        // checks if the user has enough money to complete the purchase
        if (player.getMoney().compareTo(totalCost) < 0) {
            throw new IllegalStateException("Not enough cash to complete purchase");
        }

        // trekker penger
        player.withdrawMoney(totalCost);

        // places the share in the player's portfolio
        player.getPortfolio().addShare(getShare());

        // place the transaction in the player's transaction archive
        player.getTransactionArchive()
        .add(this);
    }

}
