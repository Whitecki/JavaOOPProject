package agh.ics.oop.model.mutation;

import agh.ics.oop.model.animal.Genome;

import java.util.Random;

public class SlightAdjustmentMutation implements MutationStrategy{
    @Override
    public void mutate(Genome genome, int indexOfGenToMutate) {
        Random random = new Random();
        //musze zmienić dowolny gen
        if (random.nextInt(2) == 1){
            genome.setGenNewDirection(genome.getActiveGen().next(),indexOfGenToMutate);
        }
        else {genome.setGenNewDirection(genome.getActiveGen().previous(),indexOfGenToMutate);}
    }
}
