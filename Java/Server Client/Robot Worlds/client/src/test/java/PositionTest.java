import Robot.Position;
import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    @Test
    public void getX() {
        Position position;

        position = new Position(0,1);
        assertEquals(position.getX(), 0);
    }

    @Test
    public void getY() {
        Position position;

        position = new Position(1,0);
        assertEquals(position.getY(), 0);
    }

}
