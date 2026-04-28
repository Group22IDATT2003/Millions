package no.ntnu.idatt2003.group22.millions.model;

import no.ntnu.idatt2003.group22.millions.transaction.TransactionArchive;
import java.math.BigDecimal;

public class Player {
    private static final BigDecimal INVESTOR_RATIO = new BigDecimal("1.2");
    private static final BigDecimal SPECULATOR_RATIO = new BigDecimal("2.0");

    private final String name;
    private final BigDecimal startingMoney;
    private BigDecimal money;
    private final Portfolio portfolio;
    private final TransactionArchive transactionArchive;
    private BigDecimal previousNetWorth = BigDecimal.ZERO;

    public Player(String name, BigDecimal startingMoney) {
        if(name == null){
            throw new IllegalArgumentException("name cannot be null");
        }

        if(startingMoney == null){
            throw new IllegalArgumentException("startingMoney cannot be null");
        }

        if (startingMoney.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("startingMoney cannot be negative");
        }

        this.name = name;
        this.startingMoney = startingMoney;
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
        if(amount == null){
            throw new IllegalArgumentException("amount cannot be null");
        }
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("amount must be >= 0");
        }
        money = money.add(amount);
    }

    public void withdrawMoney(BigDecimal amount){
        if(amount == null){
            throw new IllegalArgumentException("amount cannot be null");
        }
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("amount must be >= 0");
        }
        if(money.compareTo(amount) < 0){
            throw new IllegalStateException("Not enough money to withdraw");
        }
        money = money.subtract(amount);
    }

    public BigDecimal getNetWorth(){
        return money.add(portfolio.getNetWorth());
    }

    public void setPreviousNetWorth(BigDecimal previousNetWorth) {
        this.previousNetWorth = previousNetWorth;
    }

    public BigDecimal getChange(){
        return getNetWorth().subtract(previousNetWorth);
    }

    public String getStatus(){
        BigDecimal netWorth = getNetWorth();
        int activeWeeks = transactionArchive.countDistinctWeeks();

        if (activeWeeks < 2){
            return "NOVICE";
        }

        BigDecimal ratio;
        if(startingMoney.compareTo(BigDecimal.ZERO) == 0){
            ratio = BigDecimal.ZERO;
        } else {
            ratio = netWorth.divide(startingMoney, 4, java.math.RoundingMode.HALF_UP);
        }

        if (ratio.compareTo(SPECULATOR_RATIO) >= 0 && activeWeeks >= 20){
            return "SPECULATOR";
        }

        if(ratio.compareTo(INVESTOR_RATIO) >= 0 && activeWeeks >= 10){
            return "INVESTOR";
        }

        return "NOVICE";
    }



}
