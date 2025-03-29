package ca.mcmaster.se2aa4.mazerunner.observer_pattern;
import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public void detach(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifyAllObservers() {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }
}
