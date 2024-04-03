import Robot.*;
import ThreadManager.ClientHandler;
import World.World;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class WorldTest {

    @Test
    public void getRobotFromWorld() {
        World world = new World(new ArrayList<ClientHandler>());
        Robot a = new Robot("CrashTestDummy", "normal", 0, 0);
        world.addRobot(a);
        assertEquals(world.getRobot(a).getName(), "CrashTestDummy");
        assertEquals(world.getRobot(a).getType(), "normal");
    }

    @Test
    public void getMultipleRobotsFromWorld() {
        World world = new World(new ArrayList<ClientHandler>());
        Robot a = new Robot("CrashTestDummy1", "normal", 0, 0);
        Robot b = new Robot("CrashTestDummy2", "bomber", 0, 0);
        world.addRobot(a);
        world.addRobot(b);
        assertEquals(world.getAllRobots().size(), 2);
    }

}
