package agh.ics.oop.model.animal;

import java.util.*;
import java.util.stream.Collectors;

public class Reproduction {
    private final Animal daddy;
    private final Animal mommy;
    private final Animal child;
    private final Random random = new Random();
    private final int minMutationCount;
    private final int maxMutationCount;

    public Reproduction(Animal daddy, Animal mommy, int energyUsedToCreateChild, int minMutationCount, int maxMutationCount) {
        this.daddy = daddy;
        this.mommy = mommy;
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;

        Genome childGenom = createNewGenom();
        MoveDirection randomDirection = MoveDirection.values()[random.nextInt(MoveDirection.values().length)];
        child = new Animal(mommy.getPosition(), energyUsedToCreateChild, randomDirection, childGenom, mommy.getMutationStrategy(), mommy.getBehaviorStrategy());

        childMutations();
        updateParentsEnergy(energyUsedToCreateChild);
    }
    public Animal getChild() {
        return child;
    }
    private void updateParentsEnergy(int energyUsedToCreateChild) {
        mommy.changeEnergy(-energyUsedToCreateChild);
        daddy.changeEnergy(-energyUsedToCreateChild);
    }
    private Genome createNewGenom() {
        int mommyEnergy = mommy.getEnergy();
        int daddyEnergy = daddy.getEnergy();
        double mommyInheritedGensNumber = (double) mommyEnergy / (daddyEnergy + mommyEnergy) * mommy.getGenom().length();
        double daddyInheritedGensNumber = (double) daddyEnergy / (daddyEnergy + mommyEnergy) * mommy.getGenom().length();

        ArrayList<MoveDirection> childGenome = new ArrayList<>(mommy.getGenom().length());

        if (random.nextInt(2) == 1) {
            MommyFirst(mommyInheritedGensNumber, daddyInheritedGensNumber, childGenome, mommy, daddy);
        } else {
            MommyFirst(daddyInheritedGensNumber, mommyInheritedGensNumber, childGenome, daddy, mommy);
        }

        if (mommyInheritedGensNumber > Math.floor(mommyInheritedGensNumber)) {
            if (random.nextInt(2) == 1) {
                childGenome.set((int) Math.floor(mommyInheritedGensNumber), mommy.getGenom().getGenome().get((int) Math.floor(mommyInheritedGensNumber)));
            } else {
                childGenome.set((int) Math.floor(mommyInheritedGensNumber), daddy.getGenom().getGenome().get((int) Math.floor(mommyInheritedGensNumber)));
            }
        }
        return new Genome(childGenome, random.nextInt(0, 9));
    }

    private void MommyFirst(double mommyInheritedGensNumber, double daddyInheritedGensNumber, ArrayList<MoveDirection> childGenom, Animal mommy, Animal daddy) {
        for (int i = 0; i < Math.floor(mommyInheritedGensNumber); i++) {
            childGenom.set(i, mommy.getGenom().getGenome().get(i));
        }
        for (int i = (int) Math.ceil(mommyInheritedGensNumber); i < daddyInheritedGensNumber; i++) {
            childGenom.set(i, daddy.getGenom().getGenome().get(i));
        }
    }
    private void childMutations() {
        int numberOfMutations = getRandomNumberInRange(minMutationCount, maxMutationCount);
        Set<Integer> mutationIndexes = generateUniqueRandomNumbers(0, mommy.getGenom().length(), numberOfMutations);
        mutationIndexes.forEach(child::mutate);
    }
    private int getRandomNumberInRange(int min, int max) {
        return random.nextInt(max - min) + min;
    }
    public Set<Integer> generateUniqueRandomNumbers(int min, int max, int count) {
        return random.ints(min, max).distinct().limit(count).boxed().collect(Collectors.toSet());
    }
}