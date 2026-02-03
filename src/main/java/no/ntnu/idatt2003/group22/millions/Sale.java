package no.ntnu.idatt2003.group22.millions;

public class Sale extends Transaction {
    public Sale(Share share, int week){
        super(share, week, (TransactionCalculator) new SaleCalculator(share));
    }

    public<Player> void commit(Player player){
        super.commit(player);
    }
}
