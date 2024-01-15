package agh.ics.oop.model.visualization;

import agh.ics.oop.model.map.Map;

public interface MapChangeListener {

    void mapChanged(Map worldMap, String message);
}
