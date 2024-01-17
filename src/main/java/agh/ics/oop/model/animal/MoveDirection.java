package agh.ics.oop.model.animal;

import java.util.ArrayList;
import java.util.Random;

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

    MoveDirection(String name) {
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
        int value = (Integer.parseInt(name)+Integer.parseInt(other.name))%8;
        return getMoveDirection(value);
    }

    private MoveDirection getMoveDirection(int value) {
        return switch (value){
            case 0 -> NORTH;
            case 1 -> NORTH_EAST;
            case 2 -> EAST;
            case 3 -> SOUTH_EAST;
            case 4 -> SOUTH;
            case 5 -> SOUTH_WEST;
            case 6 -> WEST;
            case 7 -> NORTH_WEST;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    public MoveDirection randomOtherThanThis(){
        Random random = new Random();
        int value1 = Integer.parseInt(name);
        int value2 = random.nextInt(1,8);
        int value = (value1+value2)%8;
        return getMoveDirection(value);
    }

    public MoveDirection opposite() {
        return getMoveDirection(SOUTH, SOUTH_WEST, SOUTH, NORTH_WEST, NORTH, NORTH_EAST, EAST, SOUTH_EAST);
    }

    private MoveDirection getMoveDirection(MoveDirection moveDirection, MoveDirection moveDirection2, MoveDirection moveDirection3, MoveDirection moveDirection4, MoveDirection moveDirection5, MoveDirection moveDirection6, MoveDirection moveDirection7, MoveDirection moveDirection8) {
        return switch (this) {
            case NORTH -> moveDirection;
            case NORTH_EAST -> moveDirection2;
            case EAST -> moveDirection3;
            case SOUTH_EAST -> moveDirection4;
            case SOUTH -> moveDirection5;
            case SOUTH_WEST -> moveDirection6;
            case WEST -> moveDirection7;
            case NORTH_WEST -> moveDirection8;
        };
    }

    public MoveDirection next() {
        return getMoveDirection(NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST, NORTH);
    }

    public MoveDirection previous() {
        return getMoveDirection(NORTH_WEST, NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST);
    }
}
