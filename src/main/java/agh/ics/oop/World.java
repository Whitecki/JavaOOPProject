package agh.ics.oop;

import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.mutation.AllMutations;
import agh.ics.oop.simulation.ConfigurationData;
import agh.ics.oop.simulation.Simulation;
import javafx.application.Application;

import static agh.ics.oop.model.behavior.AllAnimalBehaviors.FullPredestination;
import static agh.ics.oop.model.edge.AllEdges.GlobeEdgeBehavior;
import static agh.ics.oop.model.growth.GrowthTypes.EquatorialGrowth;
import static agh.ics.oop.model.growth.GrowthTypes.NearToGrassGrowth;
import static agh.ics.oop.model.mutation.AllMutations.RandomMutation;

public class World {
    public static void main(String[] args) {
        Application.launch(SimulationApp.class, args);
    }
}
