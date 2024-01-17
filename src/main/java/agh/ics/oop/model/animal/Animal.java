package agh.ics.oop.model.animal;

import agh.ics.oop.model.map.WorldElement;
import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.behavior.BehaviorStrategy;
import agh.ics.oop.model.behavior.FullPredestination;
import agh.ics.oop.model.mutation.AllMutations;
import agh.ics.oop.model.mutation.MutationStrategy;
import agh.ics.oop.model.mutation.RandomMutation;
import agh.ics.oop.model.mutation.SlightAdjustmentMutation;

public class Animal implements WorldElement {
    private final MutationStrategy mutationStrategy;
    private final BehaviorStrategy behaviorStrategy;
    private final Genome genome;
    private Vector2D position;
    private int energy;
    private MoveDirection direction;
    private int numberOfChildren = 0;
    private int numberOfEatenGrass = 0;
    private int dayOfBirth;
    private int dayOfDeath;

    public Animal(Vector2D position, int energy, MoveDirection direction, Genome genome, AllMutations allMutations, AllAnimalBehaviors allAnimalBehaviors) {
        this.position = position;
        this.energy = energy;
        this.direction = direction;
        this.genome = genome;
        this.mutationStrategy = mutation(allMutations);
        this.behaviorStrategy = behave(allAnimalBehaviors);
    }

    public Animal(Vector2D position, int energy, MoveDirection direction, Genome genome, MutationStrategy mutationStrategy, BehaviorStrategy behaviorStrategy) {
        this.position = position;
        this.energy = energy;
        this.direction = direction;
        this.genome = genome;
        this.mutationStrategy = mutationStrategy;
        this.behaviorStrategy = behaviorStrategy;
    }

    private MutationStrategy mutation(AllMutations allMutations) {
        return switch (allMutations) {
            case RandomMutation -> new RandomMutation();
            case SlightAdjustmentMutation -> new SlightAdjustmentMutation();
        };
    }

    private BehaviorStrategy behave(AllAnimalBehaviors allAnimalBehaviors) {
        return switch (allAnimalBehaviors) {
            case FullPredestination -> new FullPredestination();
        };
    }

    public Genome getGenome() {
        return genome;
    }

    public void haveDoneChild() {
        numberOfChildren++;
    }

    public void died(int day) {
        dayOfDeath = day;
    }

    public void haveEaten() {
        numberOfEatenGrass++;
    }

    public void Birth(int day) {
        dayOfBirth = day;
    }

    public BehaviorStrategy getBehaviorStrategy() {
        return behaviorStrategy;
    }

    public MutationStrategy getMutationStrategy() {
        return mutationStrategy;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public int getEnergy() {
        return energy;
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    public void setNewDirection() {
        direction = genome.getActiveGen();
    }

    public void changeEnergy(int e) {
        energy = energy + e;
    }

    public Vector2D nextMove() {
        Vector2D unitVector = direction.toUnitVector();
        return position.add(unitVector);
    }

    public void mutate(int numberOfGenToMutate) {
        mutationStrategy.mutate(genome, numberOfGenToMutate);
    }

    public void rotateAndDecreaseEnergy() {
        direction = direction.rotate(genome.getActiveGen());
        energy--;
    }

    public String toString() {
        return "@";
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }
}
