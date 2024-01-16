package agh.ics.oop.model.behavior;

import agh.ics.oop.model.edge.AllEdges;

public enum AllAnimalBehaviors {
    FullPredestination;

    public static AllAnimalBehaviors parse(int number){

        return switch (number){
            case 0 -> FullPredestination;
            default -> null;
        };
    }
}
