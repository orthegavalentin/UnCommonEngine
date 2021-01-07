package observers;

import UnCommon.GameObject;
import observers.events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {
    public static List<Observer> observers=new ArrayList<>();

    public static void addObserver(Observer observer ){
        observers.add(observer);


    }

    public static void notify(GameObject obj, Event event){
        for(Observer observer :observers){
            observer.onNotify(obj,event);

        }

    }


}
