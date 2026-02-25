package no.ntnu.idatt2003.group22.millions;

import java.math.BigDecimal;
import java.util.Objects;

public class Player {
    private final String name;
    private final BigDecimal startingMoney;
    private BigDecimal money;
    private final Portfolio portfolio;
    private final TransactionArchive transactionArchive;

    public Player(String name, BigDecimal startingMoney) {
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.startingMoney = Objects.requireNonNull(startingMoney);

        if (startingMoney.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("startingMoney cannot be negative");
        }

        this.money = startingMoney;
        this.portfolio = new Portfolio();
        this.transactionArchive = new TransactionArchive();

    }

    public String getName() {
        return name;
    }

    public BigDecimal getStartingMoney() {
        return startingMoney;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public TransactionArchive getTransactionArchive(){
        return transactionArchive;
    }

    public void addMoney(BigDecimal amount) {
        Objects.requireNonNull(amount, "amount can not be null");
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("amounr must be >= 0");
        }
        money = money.add(amount);
    }
    public void withdrawMoney(BigDecimal amount){
        Objects.requireNonNull(amount, "amount can not be null");
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("amounr must be >= 0");
        }
        if(money.compareTo(amount) < 0){
            throw new IllegalStateException("Not enpugh money to withdraw");
        }
        money = money.subtract(amount);
    }
}
