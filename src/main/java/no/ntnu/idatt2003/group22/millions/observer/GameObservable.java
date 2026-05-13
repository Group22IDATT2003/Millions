package no.ntnu.idatt2003.group22.millions.observer;

import java.util.ArrayList;
import java.util.List;

public class GameObservable {

    private final List<GameObserver> observers = new ArrayList<>();

    public void addObserver(GameObserver observer){
        if(observer == null){
            throw new IllegalArgumentException("observer cannot be null");
        }

        observers.add(observer);
    }

    public void removeObserver(GameObserver observer){
        observers.remove(observer);
    }
    
    protected void notifyObservers(){
        for(GameObserver observer : observers){
            observer.update();
        }
    }
}
