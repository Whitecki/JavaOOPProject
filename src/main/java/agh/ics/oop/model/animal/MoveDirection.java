package agh.ics.oop.model.animal;

public enum MoveDirection {
    NORTH("0"),
    NORTH_EAST("1"),
    EAST("2"),
    SOUTH_EAST("3"),
    SOUTH("4"),
    SOUTH_WEST("5"),
    WEST("6"),
    NORTH_WEST("7");
    private final String name;

    private MoveDirection(String name) {
        this.name = name;
    }

    public Vector2D toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0, 1);
            case NORTH_EAST -> new Vector2D(1, 1);
            case EAST -> new Vector2D(1, 0);
            case SOUTH_EAST -> new Vector2D(1, -1);
            case SOUTH -> new Vector2D(0, -1);
            case SOUTH_WEST -> new Vector2D(-1, -1);
            case WEST -> new Vector2D(-1, 0);
            case NORTH_WEST -> new Vector2D(-1, 1);
        };
    }

    public MoveDirection rotate(MoveDirection other){
        return MoveDirection.valueOf(String.valueOf((Integer.parseInt(name) + Integer.parseInt(other.name()))%8));
    }

    public MoveDirection opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case NORTH_EAST -> SOUTH_WEST;
            case EAST -> SOUTH;
            case SOUTH_EAST -> NORTH_WEST;
            case SOUTH -> NORTH;
            case SOUTH_WEST -> NORTH_EAST;
            case WEST -> EAST;
            case NORTH_WEST -> SOUTH_EAST;
        };
    }

    public MoveDirection next() {
        return switch (this) {
            case NORTH -> NORTH_EAST;
            case NORTH_EAST -> EAST;
            case EAST -> SOUTH_EAST;
            case SOUTH_EAST -> SOUTH;
            case SOUTH -> SOUTH_WEST;
            case SOUTH_WEST -> WEST;
            case WEST -> NORTH_WEST;
            case NORTH_WEST -> NORTH;
        };
    }

    public MoveDirection previous() {
        return switch (this) {
            case NORTH -> NORTH_WEST;
            case NORTH_EAST -> NORTH;
            case EAST -> NORTH_EAST;
            case SOUTH_EAST -> EAST;
            case SOUTH -> SOUTH_EAST;
            case SOUTH_WEST -> SOUTH;
            case WEST -> SOUTH_WEST;
            case NORTH_WEST -> WEST;
        };
    }
}