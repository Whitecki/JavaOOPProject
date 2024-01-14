package agh.ics.oop.simulation;

import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.behavior.BehaviorStrategy;
import agh.ics.oop.model.edge.AllEdges;
import agh.ics.oop.model.edge.EdgeBehavior;
import agh.ics.oop.model.growth.GrowthStrategy;
import agh.ics.oop.model.growth.GrowthTypes;
import agh.ics.oop.model.mutation.AllMutations;
import agh.ics.oop.model.mutation.MutationStrategy;

public record ConfigurationData(int mapHeight, int mapWidth, AllEdges allEdges, int initialPlantCount,
                                int energyFromPlant, int dailyPlantGrowth, GrowthTypes growthTypes,
                                int initialAnimalCount, int initialAnimalEnergy, int energyToReproduce,
                                int energyUsedToCreateChild, int minMutationCount, int maxMutationCount,
                                AllMutations allMutations, int genomeLength,
                                AllAnimalBehaviors allAnimalBehaviors) {
}
