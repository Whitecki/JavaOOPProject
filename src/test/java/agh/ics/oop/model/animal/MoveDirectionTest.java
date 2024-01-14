package agh.ics.oop.model.animal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveDirectionTest {

    @Test
    void rotate(){
        MoveDirection moveDirection = MoveDirection.NORTH;
        MoveDirection moveDirection1 = MoveDirection.EAST;

        MoveDirection newDirection = moveDirection.rotate(moveDirection1);

        MoveDirection expectedDirection = MoveDirection.EAST;
        assertEquals(newDirection,expectedDirection);
    }


}