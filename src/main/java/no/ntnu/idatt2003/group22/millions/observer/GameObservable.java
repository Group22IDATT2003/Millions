package no.ntnu.idatt2003.group22.millions.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an observable game model
 * Observers are notified when the game state changes
 */
public class GameObservable {

    private final List<GameObserver> observers = new ArrayList<>();

    /**
     * Adds an observer to the observable
     * 
     * @param observer the observer to add
     * @throws IllegalArgumentException if observer is null
     */
    public void addObserver(GameObserver observer){
        if(observer == null){
            throw new IllegalArgumentException("observer cannot be null");
        }

        observers.add(observer);
    }

    /**
     * Removes an observer from the observable
     * 
     * @param observer the observer to remove
     */
    public void removeObserver(GameObserver observer){
        observers.remove(observer);
    }
    
    /**
     * Notifies all observers about updates
     */
    protected void notifyObservers(){
        for(GameObserver observer : observers){
            observer.update();
        }
    }
}
