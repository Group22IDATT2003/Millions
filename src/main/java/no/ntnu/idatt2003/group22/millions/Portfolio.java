package no.ntnu.idatt2003.group22.millions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Kommentarer for hvorfor jeg endret
 * 1. metodene returnerte alltid false eller så returnerte de hele lista og ikke bare symbolet
 * - getShares returnerte den interne lista direkte som gjør at en annen kode kunne forandre og ødelegge porteføljen uten kontroll
 * - getShares burde kunne filtrere symbol og returnere kopi av lista
 */

public class Portfolio {
    private final List<Share> shares = new ArrayList<>();

    public boolean addShare(Share share){
        Objects.requireNonNull(share, "share cannot be null");
        return shares.add(share);
    }

    public boolean removeShare(Share share) {
        Objects.requireNonNull(share, "share cannot be null");
        return shares.remove(share);
    }

    public boolean containsShare(Share share){
        Objects.requireNonNull(share, "share cannot be null");
        return shares.contains(share);
    }

    public List<Share> getShares(){
        return List.copyOf(shares);
    }

    public List<Share> getShares(String symbol) {
        Objects.requireNonNull(symbol, "symbol can not be null");
        List<Share> result = new ArrayList<>();
        for( Share share : shares) {
            if(share.getStock().getSymbol().equals(symbol)){
                result.add(share);
            }
        }
        return List.copyOf(result);
    }

    public boolean contains(Share share) {
        return shares.contains(share);
    }
}
