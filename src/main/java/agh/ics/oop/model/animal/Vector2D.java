package agh.ics.oop.model.animal;

import java.util.Objects;

public class Vector2D {
    private final int x;
    private final int y;
    private boolean chosen = false;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    @Override
    public boolean equals(Object other){
        if (this==other){
            return true;
        }
        if (!(other instanceof Vector2D that)){
            return false;
        }
        return this.x==that.x && this.y==that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
