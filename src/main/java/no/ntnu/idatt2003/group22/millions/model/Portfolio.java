package no.ntnu.idatt2003.group22.millions.model;
import no.ntnu.idatt2003.group22.millions.transaction.calculator.SaleCalculator;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * Kommentarer for hvorfor jeg endret
 * 1. metodene returnerte alltid false eller så returnerte de hele lista og ikke bare symbolet
 * - getShares returnerte den interne lista direkte som gjør at en annen kode kunne forandre og ødelegge porteføljen uten kontroll
 * - getShares burde kunne filtrere symbol og returnere kopi av lista
 */

public class Portfolio {
    private final List<Share> shares;
    
    public Portfolio(){
        this.shares = new ArrayList<>();
    }

    public boolean addShare(Share share){
        if(share == null){
            throw new IllegalArgumentException("Share cannot be null");
        }
        return shares.add(share);
    }

    public boolean removeShare(Share share) {
        if(share == null){
            throw new IllegalArgumentException("Share can not be null");
        }
        return shares.remove(share);
    }

    public boolean containsShare(Share share) {
        if(share == null){
            throw new IllegalArgumentException("Share cannot be null");
        }
        return shares.contains(share);
    }
    
    public List<Share> getShares() {
        return List.copyOf(shares);
    }

    public List<Share> getShares(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("symbol cannot be null");
        }
        List<Share> matchingShares = new ArrayList<>();
        for(Share share : shares){
            if(share.getSymbol().equalsIgnoreCase(symbol)){
                matchingShares.add(share);
            }
        }
        return List.copyOf(matchingShares);
    }

    public boolean isEmpty() {
        return shares.isEmpty();
    }

    public BigDecimal getNetWorth() {
        BigDecimal total = BigDecimal.ZERO;

        for(Share share : shares){
            SaleCalculator calculator = new SaleCalculator(share);
            total = total.add(calculator.calculateTotal());
        }
        return total;
    }

}
