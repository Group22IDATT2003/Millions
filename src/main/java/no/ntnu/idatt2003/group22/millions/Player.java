package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;

public class Player {
    private String name;
    private BigDecimal startingMoney;
    private BigDecimal money;
    private Portfolio portfolio;
    private TransactionArchive transactionArchive;

    public Player(String name, BigDecimal startingMoney) {
        this.name = name;
        this.startingMoney = startingMoney;
        this.money = startingMoney;
    }

    public String getName() {
        return name;
    }
    public BigDecimal getMoney() {
        return money;
    }

    public void addMoney(BigDecimal amount) {
        money = money.add(amount);
    }
    public void withdrawMoney(BigDecimal amount){
        money = money.subtract(amount);
    }
    public Portfolio getPortfolio() {
        return portfolio;
    }
    public TransactionArchive getTransactionArchive() {
        return transactionArchive;
    }


}
