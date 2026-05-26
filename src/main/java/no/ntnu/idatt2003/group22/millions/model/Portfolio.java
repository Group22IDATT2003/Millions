package no.ntnu.idatt2003.group22.millions.model;
import no.ntnu.idatt2003.group22.millions.transaction.calculator.SaleCalculator;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

/**
 * Represents a portfolio containing shares
 */

public class Portfolio {
    private final List<Share> shares;
    
    /**
     * Creates an empty portfolio
     */
    public Portfolio(){
        this.shares = new ArrayList<>();
    }

    /**
     *Adds a share to the portfolio
     * 
     * @param share the share to add
     * @return true if the share was added
     * @throws IllegalArgumentException if share is null
     */
    public boolean addShare(Share share){
        if(share == null){
            throw new IllegalArgumentException("Share cannot be null");
        }
        return shares.add(share);
    }

    /**
     * Removes a share from the portfolio
     * 
     * @param share the share to remove
     * @return true if the share was removed
     * @throws IllegalArgumentException if share is null
     */
    public boolean removeShare(Share share) {
        if(share == null){
            throw new IllegalArgumentException("Share can not be null");
        }
        return shares.remove(share);
    }

    /**
     * Checks if the portfolio contains a share
     * 
     * @param share the share to check
     * @return true if the share exists
     * @throws IllegalArgumentException if share in null
     */
    public boolean containsShare(Share share) {
        if(share == null){
            throw new IllegalArgumentException("Share cannot be null");
        }
        return shares.contains(share);
    }
    
    /**
     * Returns all shares in the portfolio
     * 
     * @return an immutable list of shares
     */
    public List<Share> getShares() {
        return List.copyOf(shares);
    }

    /**
     * 
     * 
     * @param symbol the stock symbol
     * @return matching shares
     * @throws IllegalArgumentException if symbol in null
     */
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

    /**
     * Checks if the portfolio is empty
     * 
     * @return true if the portfolio is empty
     */
    public boolean isEmpty() {
        return shares.isEmpty();
    }

    /**
     * Returns the total net worth of the portfolio
     * 
     * @return the portfolio net worth
     */
    public BigDecimal getNetWorth() {
        BigDecimal total = BigDecimal.ZERO;

        for(Share share : shares){
            SaleCalculator calculator = new SaleCalculator(share);
            total = total.add(calculator.calculateTotal());
        }
        return total;
    }

}
