package agh.ics.oop.model.animal;

import java.util.Arrays;
import java.util.List;

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

    /**
     * Adds this vector to another vector.
     *
     * @param other the other vector to add
     * @return a new Vector2D that is the sum of this vector and the other vector
     */
    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Generates a list of vectors around this vector.
     *
     * @return a list of adjacent vectors
     */
    public List<Vector2D> around() {
        return Arrays.asList(
                new Vector2D(x - 1, y - 1), new Vector2D(x - 1, y), new Vector2D(x - 1, y + 1),
                new Vector2D(x, y - 1), new Vector2D(x, y + 1),
                new Vector2D(x + 1, y - 1), new Vector2D(x + 1, y), new Vector2D(x + 1, y + 1)
        );
    }

}
