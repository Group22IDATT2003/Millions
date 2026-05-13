package no.ntnu.idatt2003.group22.millions.transaction.factory;

import no.ntnu.idatt2003.group22.millions.model.Share;
import no.ntnu.idatt2003.group22.millions.transaction.Purchase;
import no.ntnu.idatt2003.group22.millions.transaction.Sale;

public class TransactionFactory {
    public Purchase createPurchase(Share share, int week){
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }
        return new Purchase(share, week);
    }

    public Sale createSale(Share share, int week){
        if(share == null){
            throw new IllegalArgumentException("share cannot be null");
        }
        return new Sale(share, week);
    }
    
}
