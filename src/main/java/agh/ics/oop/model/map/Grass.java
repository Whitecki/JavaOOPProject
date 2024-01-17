package agh.ics.oop.model.map;

import agh.ics.oop.model.animal.Vector2D;

public class Grass implements WorldElement {
    private Vector2D position;
    public Grass(Vector2D position) {
        this.position = position;
    }

    @Override
    public Vector2D getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
