package no.ntnu.idatt2003.group22.millions;

public class Purchase extends Transaction {
    public Purchase(Share share, int week){
        super(share, week, new PurchaseCalculator(share));

    }

    public<Player> void commit(Player player) {
        super.commit(player);
    }
}
