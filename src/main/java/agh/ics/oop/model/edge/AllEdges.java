package agh.ics.oop.model.edge;

public enum AllEdges {
    GlobeEdgeBehavior;

    public static AllEdges parse(int number){
        return switch (number){
            case 0 -> GlobeEdgeBehavior;
            default -> null;
        };
    }
}
