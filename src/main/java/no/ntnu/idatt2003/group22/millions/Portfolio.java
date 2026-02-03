package no.ntnu.idatt2003.group22.millions;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    private List<Share> shares;

    public Portfolio() {
        this.shares = new ArrayList<>();
    }

    public boolean addShare(Share share) {
        return false;
    }

    public boolean removeShare(Share share) {
        return false;
    }


    public List<Share> getShares(){
        return shares;
    }

    public List<Share> getShares(String symbol) {
        return shares;
    }

    public boolean contains(Share share) {
        return false;
    }
}
