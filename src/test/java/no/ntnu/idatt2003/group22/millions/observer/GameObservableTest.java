package no.ntnu.idatt2003.group22.millions.observer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

/**
 * Test observer used for verifying updates.
 */
public class GameObservableTest {
    private static class TestObserver implements GameObserver{
        private boolean updated = false;

        @Override
        public void update(){
            updated = true;
        }
    }

    /**
     * Test observable used for triggering notifications.
     */
    private static class TestObservable extends GameObservable{
        public void triggerUpdate(){
            notifyObservers();
        }
    }

    @Test
    @DisplayName("Verifies that observers receive update notifications. ")
    void notifyObservers_callsUpdate(){

        TestObservable observable = new TestObservable();
        TestObserver observer = new TestObserver();

        observable.addObserver(observer);

        observable.triggerUpdate();

        assertTrue(observer.updated);
    }
    
}
