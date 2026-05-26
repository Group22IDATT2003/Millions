package no.ntnu.idatt2003.group22.millions.transaction;

import no.ntnu.idatt2003.group22.millions.transaction.calculator.SaleCalculator;
import no.ntnu.idatt2003.group22.millions.model.Player;
import no.ntnu.idatt2003.group22.millions.model.Share;

import java.math.BigDecimal;

/**
 * Represents a sale transaction 
 */
public class Sale extends Transaction {

    /**
     * Creates a sale transaction 
     * 
     * @param share the sold share
     * @param week the transaction week
     */
    public Sale(Share share, int week){
        super(share, week, new SaleCalculator(share));
    }

    /**
     * Completes the sale transaction
     * 
     * @param player the player making the sale
     * @throws IllegalArgumentException if player is null
     * @throws IllegalStateException if the player does not own the share
     */
    @Override
    protected void doCommit(Player player){
        if(player == null){
            throw new IllegalArgumentException("player cannot be null");
        }
        
        if(!player.getPortfolio().containsShare(getShare())){
            throw new IllegalStateException("Player does not own this share");
        }

        boolean removed = player.getPortfolio().removeShare(getShare());
        if(!removed){
            throw new IllegalStateException("Failed to remove share from portfolio");
        }

        BigDecimal payout = getCalculator().calculateTotal();

        player.addMoney(payout);
        
        player.getTransactionArchive().add(this);
    }
}
