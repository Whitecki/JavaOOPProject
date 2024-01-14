package agh.ics.oop.model.growth;

import agh.ics.oop.model.map.Map;
import agh.ics.oop.model.animal.Vector2D;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class RandomPositionGenerator implements Iterator<Vector2D>, Iterable<Vector2D> {
    public GrowthStrategy growthStrategy;
    public LinkedList<Vector2D> result = new LinkedList<>();
    private Iterator<Vector2D> iterator;
    private int minWidth;
    private int minHeight;
    private int maxWidth;
    private int maxHeight;
    private int grassCount;
    private ArrayList<Vector2D> nwm;
    public RandomPositionGenerator(Map grassHashMap, ArrayList<Vector2D> nwm) {
        this.maxWidth = grassHashMap.getUpperRightBands().getX();
        this.maxHeight = grassHashMap.getUpperRightBands().getY();
        this.minWidth = grassHashMap.getLowerLeftBands().getX();
        this.minHeight = grassHashMap.getLowerLeftBands().getY();

        int numberOfGrassToGrowth = 7;
        Random random = new Random();
        LinkedList<Vector2D> result = new LinkedList<>();
        int index = 0;
        while (numberOfGrassToGrowth > 0){
            int selectedFieldIdx = random.nextInt(nwm.size() - index + 1);
            if (!nwm.get(selectedFieldIdx).isChosen()) {
                result.add(nwm.get(selectedFieldIdx));
                nwm.get(selectedFieldIdx).setChosen(true);
                numberOfGrassToGrowth--;
            }
            Vector2D temp = nwm.get(selectedFieldIdx);
            nwm.set(selectedFieldIdx,nwm.get(nwm.size() - index - 1));
            nwm.set(nwm.size()-index-1,temp);
            index++;

        }
    }

    public void generate(){
        Random random = new Random();
        for (int i = 0; i < grassCount; i++) {

        }
    }
    @Override
    public Iterator<Vector2D> iterator() {
        iterator = result.iterator();
        return iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Vector2D next() {
        return iterator.next();
    }
}
