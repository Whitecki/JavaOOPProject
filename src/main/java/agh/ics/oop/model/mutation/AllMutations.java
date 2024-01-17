package agh.ics.oop.model.mutation;

import agh.ics.oop.model.edge.AllEdges;

public enum AllMutations {
    RandomMutation,
    SlightAdjustmentMutation;

    public static AllMutations parse(int number){

        return switch (number){
            case 0 -> RandomMutation;
            case 1 -> SlightAdjustmentMutation;
            default -> null;
        };
    }
}
