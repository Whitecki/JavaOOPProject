package agh.ics.oop;

import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.mutation.AllMutations;
import agh.ics.oop.simulation.ConfigurationData;
import agh.ics.oop.simulation.Simulation;

import static agh.ics.oop.model.behavior.AllAnimalBehaviors.FullPredestination;
import static agh.ics.oop.model.edge.AllEdges.GlobeEdgeBehavior;
import static agh.ics.oop.model.growth.GrowthTypes.EquatorialGrowth;
import static agh.ics.oop.model.growth.GrowthTypes.NearToGrassGrowth;
import static agh.ics.oop.model.mutation.AllMutations.RandomMutation;

public class World {
    public static void main(String[] args) {
        ConfigurationData config = new ConfigurationData(10, 10, GlobeEdgeBehavior , 6,
        2, 2, NearToGrassGrowth, 5, 12, 2,
        1, 1, 3, RandomMutation, 8,
         FullPredestination);
        Simulation simulation = new Simulation(config);
        simulation.startSimulation();
    }
}
