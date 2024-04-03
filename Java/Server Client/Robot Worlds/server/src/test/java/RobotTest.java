import Robot.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    @Test
    public void createNormalRobot() {
        Robot normal = new Robot("CrashTestDummy", "normal", 0, 0);
        normal.setPosition(new Position(0,0));
        assertEquals(normal.getName(), "CrashTestDummy");
        assertEquals(normal.getPosition().getX(), 0);
        assertEquals(normal.getPosition().getY(), 0);
    }
    @Test
    public void createSniperRobot() {
        Robot normal = new Robot("CrashTestDummy", "sniper", 0, 0);
        normal.setPosition(new Position(0, 0));
        assertEquals(normal.getName(), "CrashTestDummy");
        assertEquals(normal.getPosition().getX(), 0);
        assertEquals(normal.getPosition().getY(), 0);
    }
    @Test
    public void createBomberRobot() {
        Robot normal = new Robot("CrashTestDummy", "bomber", 0, 0);
        normal.setPosition(new Position(0, 0));
        assertEquals(normal.getName(), "CrashTestDummy");
        assertEquals(normal.getPosition().getX(), 0);
        assertEquals(normal.getPosition().getY(), 0);
    }

    @Test
    public void changeNormalPosition() {
        Robot normal = new Robot("CrashTestDummy", "normal", 0, 0);
        normal.setPosition(new Position(4, 5));
        assertEquals(normal.getPosition().getX(), 4);
        assertEquals(normal.getPosition().getY(), 5);
    }

    @Test
    public void changeSniperPosition() {
        Robot normal = new Robot("CrashTestDummy", "sniper", 0, 0);
        normal.setPosition(new Position(4, 5));
        assertEquals(normal.getPosition().getX(), 4);
        assertEquals(normal.getPosition().getY(), 5);
    }
    @Test
    public void changeBomberPosition() {
        Robot normal = new Robot("CrashTestDummy", "bomber", 0, 0);
        normal.setPosition(new Position(4, 5));
        assertEquals(normal.getPosition().getX(), 4);
        assertEquals(normal.getPosition().getY(), 5);
    }

}
