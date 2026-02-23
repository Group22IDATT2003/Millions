package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class Sale extends Transaction {
    public Sale(Share share, int week){
        super(share, week, new SaleCalculator(share));
    }

    @Override
    protected void doCommit(Player player){
        Objects.requireNonNull(player, "player can not be null");

        if(!player.getPortfolio().contains(getShare())){
            throw new IllegalStateException("Player does not own this share");
        }

        boolean removed = player.getPortfolio().removeShare(getShare());
        if(!removed){
            throw new IllegalStateException("Failed to remove share from portfolio");
        }

        // finner total kostnad
        BigDecimal payout = getCalculator().calculateNetAmount();

        // sjekker at brukeren har nok penger
        if(player.getMoney().compareTo(totalCost) < 0){
            throw new IllegalStateException("Not enough cash to complete purchase");
        }

        // trekker penger
        player.getPortfolio().removeShare(getShare());

        // legger akjsen i portoføljen
        player.addMoney(payout);

        // arkiverer transaksjonen
        player.getTransactionArchive().add(this);
    }
}
