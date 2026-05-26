package no.ntnu.idatt2003.group22.millions.model;

import no.ntnu.idatt2003.group22.millions.transaction.TransactionArchive;
import java.math.BigDecimal;

/**
 * Represents a player in the stock game
 */

public class Player {
    private static final BigDecimal INVESTOR_RATIO = new BigDecimal("1.2");
    private static final BigDecimal SPECULATOR_RATIO = new BigDecimal("2.0");

    private final String name;
    private final BigDecimal startingMoney;
    private BigDecimal money;
    private final Portfolio portfolio;
    private final TransactionArchive transactionArchive;
    private BigDecimal previousNetWorth = BigDecimal.ZERO;

    /**
     * Creates a player with a name and starting balance
     * 
     * @param name the player name
     * @param startingMoney the starting amount of money
     * @throws IllegalArgumentException if values are invalid
     */
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

    /**
     * Returns the player name
     * 
     * @return the player name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the starting balance
     * 
     * @return the starting balance
     */
    public BigDecimal getStartingMoney() {
        return startingMoney;
    }

    /**
     * Returns the current balance
     * 
     * @return the current balance
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * Returns the players portfolio
     * 
     * @return the portfolio
     */
    public Portfolio getPortfolio() {
        return portfolio;
    }

    /**
     * Returns the transaction archive
     * 
     * @return the transaction archive
     */
    public TransactionArchive getTransactionArchive(){
        return transactionArchive;
    }

    /**
     * Adds money to the players balance
     * 
     * @param amount the amount to add
     * @throws IllegalArgumentException if amount is invalid
     */
    public void addMoney(BigDecimal amount) {
        if(amount == null){
            throw new IllegalArgumentException("amount cannot be null");
        }
        if(amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("amount must be >= 0");
        }
        money = money.add(amount);
    }

    /**
     * Withdraws money from the players balance
     * 
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if amount is invalid
     * @throws IllegalStateException if balance is too low
     */
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

    /**
     * Returns the players total net worth
     * 
     * @return the net worth
     */
    public BigDecimal getNetWorth(){
        return money.add(portfolio.getNetWorth());
    }

    /**
     * Updates the previous net worth value
     * 
     * @param previousNetWorth the previous net worth
     */
    public void setPreviousNetWorth(BigDecimal previousNetWorth) {
        this.previousNetWorth = previousNetWorth;
    }

    /**
     * Returns the change in net worth
     * 
     * @return the net worth change
     */
    public BigDecimal getChange(){
        return getNetWorth().subtract(previousNetWorth);
    }

    /**
     * Returns the players status level
     * 
     * @return the player status
     */
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
