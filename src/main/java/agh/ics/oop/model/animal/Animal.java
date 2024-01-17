package agh.ics.oop.model.animal;

import agh.ics.oop.model.map.WorldElement;
import agh.ics.oop.model.behavior.AllAnimalBehaviors;
import agh.ics.oop.model.behavior.BehaviorStrategy;
import agh.ics.oop.model.behavior.FullPredestination;
import agh.ics.oop.model.mutation.AllMutations;
import agh.ics.oop.model.mutation.MutationStrategy;
import agh.ics.oop.model.mutation.RandomMutation;
import agh.ics.oop.model.mutation.SlightAdjustmentMutation;

public class Animal implements WorldElement{
    private final MutationStrategy mutationStrategy;
    private final BehaviorStrategy behaviorStrategy;
    private final Genome genom;
    private Vector2D position;
    private int energy;
    private MoveDirection direction;
    private int numberOfChilds;
    private int numberOfEatenGrass;
    private int dayOfBirth;
    private int dayOfDeath;

    public Animal(Vector2D position, int energy, MoveDirection direction, Genome genom, AllMutations allMutations, AllAnimalBehaviors allAnimalBehaviors) {
        this.position = position;
        this.energy = energy;
        this.direction = direction;
        this.genom = genom;
        this.mutationStrategy = mutation(allMutations);
        this.behaviorStrategy = behav(allAnimalBehaviors);
        this.numberOfChilds = 0;
        this.numberOfChilds = 0;
    }
    public Animal(Vector2D position, int energy, MoveDirection direction, Genome genom, MutationStrategy mutationStrategy, BehaviorStrategy behaviorStrategy) {
        this.position = position;
        this.energy = energy;
        this.direction = direction;
        this.genom = genom;
        this.mutationStrategy = mutationStrategy;
        this.behaviorStrategy = behaviorStrategy;
        this.numberOfChilds = 0;
        this.numberOfChilds = 0;
    }
    private MutationStrategy mutation(AllMutations allMutations){
        return switch (allMutations){
            case RandomMutation -> new RandomMutation();
            case SlightAdjustmentMutation -> new SlightAdjustmentMutation();
        };
    }

    private BehaviorStrategy behav(AllAnimalBehaviors allAnimalBehaviors){
        return switch (allAnimalBehaviors){
            case FullPredestination -> new FullPredestination();
        };
    }

    public Genome getGenom() {
        return genom;
    }

    public void haveDoneChild() {
        numberOfChilds++;
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

    public void setNewDirection(){direction = genom.getActiveGen();}

    public void changeEnergy(int e) {
        energy = energy + e;
    }

    public Vector2D nextMove() {
        Vector2D unitVector = direction.toUnitVector();
        return position.add(unitVector);
    }

    public void mutate(int numberOfGenToMutate) {
        mutationStrategy.mutate(genom, numberOfGenToMutate);
    }

    public void rotateAndDecreaseEnergy() {
        direction = direction.rotate(genom.getActiveGen());
        energy --;
    }

    public String toString(){
        return "@";
    }

    public int getDayOfDeath() {
        return dayOfDeath;
    }

    public int getDayOfBirth() {
        return dayOfBirth;
    }

    public int getNumberOfChilds() {
        return numberOfChilds;
    }

    public int getNumberOfEatenGrass() {
        return numberOfEatenGrass;
    }
}
