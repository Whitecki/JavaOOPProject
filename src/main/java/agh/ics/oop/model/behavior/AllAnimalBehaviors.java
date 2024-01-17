package agh.ics.oop.model.behavior;

public enum AllAnimalBehaviors {
    FullPredestination;

    public static AllAnimalBehaviors parse(int number){

        return switch (number){
            case 0 -> FullPredestination;
            default -> null;
        };
    }
}
