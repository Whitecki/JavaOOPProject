package agh.ics.oop.model.visualization;

import agh.ics.oop.model.map.Map;

public class ConsoleMapDisplay implements MapChangeListener {

    private int actionsCounter = 0;
    @Override
    public synchronized void mapChanged(Map worldMap, String message) {
        actionsCounter++;
        System.out.println(message);
        System.out.println(worldMap.toString());
        System.out.println("updates " + actionsCounter);
        System.out.println("-------------------------------");
    }


}
