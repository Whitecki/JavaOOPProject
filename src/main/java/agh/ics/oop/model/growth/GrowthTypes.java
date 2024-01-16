package agh.ics.oop.model.growth;

import agh.ics.oop.model.mutation.AllMutations;

public enum GrowthTypes {
    EquatorialGrowth,
    NearToGrassGrowth;

    public static GrowthTypes parse(int number){

        return switch (number){
            case 0 -> EquatorialGrowth;
            case 1 -> NearToGrassGrowth;
            default -> null;
        };
    }
}
