package no.ntnu.idatt2003.group22.millions.observer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameObservableTest {
    private static class TestObserver implements GameObserver{
        private boolean updated = false;

        @Override
        public void update(){
            updated = true;
        }
    }

    private static class TestObservable extends GameObservable{
        public void triggerUpdate(){
            notifyObservers();
        }
    }

    @Test
    void notifyObservers_callsUpdate(){

        TestObservable observable = new TestObservable();
        TestObserver observer = new TestObserver();

        observable.addObserver(observer);

        observable.triggerUpdate();

        assertTrue(observer.updated);
    }
    
}
