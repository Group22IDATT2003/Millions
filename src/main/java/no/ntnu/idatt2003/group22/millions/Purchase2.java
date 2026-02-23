package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class Purchase2 extends Transaction2{
    public Purchase2(Share share, int week){
        super(share, week, new PurchaseCalculator(share));
    }

    @Override
    protected void doCommit(Player player){
        Objects.requireNonNull(player, "player can not be null");

        // finner total kostnad
        BigDecimal totalCost = getCalculator().calculateNetAmount();

        // sjekker at brukeren har nok penger
        if(player.getMoney().compareTo(totalCost) < 0){
            throw new IllegalStateException("Not enough cash to complete purchase");
        }

        // trekker penger
        player.withdrawMoney(player.getMoney().subtract(totalCost));

        // legger akjsen i portoføljen
        player.getPortfolio().addShare(getShare());

        // arkiverer transaksjonen
        player.getTransactionArchive().add(this);
    }
    
}
