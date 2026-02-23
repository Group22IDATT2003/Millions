package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase extends Transaction {
    public Purchase(Share share, int week){
        super(share, week, new PurchaseCalculator(share));

    }

    /**
     * hva som skjer:
     * transacrion.commit(), vil sjeke at den ikke er allered e commited
     * kaller doCommit(player)
     * Purchase.doCommit():
     * - beregner pris
     * - trekker penger
     * - legger aksjen i portfolio
     * arkiverer transaksjonen
     * transaction.commit setter committed = true
     * @param player
     */
    @Override
    protected void doCommit(Player player){
        Objects.requireNonNull(player, "player can not be null");

        BigDecimal totalCost = getCalculator().calculateNetAmount();

        player.withdrawMoney(totalCost);

        player.getPortfolio().addShare(getShare());

        player.getTransactionArchive().add(this);
        }

    /**
    * public<Player> void commit(Player player) {
        super.commit(player);
    }
    */
    
}
