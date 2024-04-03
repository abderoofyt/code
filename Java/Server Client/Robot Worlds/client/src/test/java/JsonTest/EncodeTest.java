package JsonTest;

import Json.Encode;
import Robot.IRobot;
import Robot.Types.NormalRobot;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EncodeTest {

    @Test
    public void createLaunchCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "launch", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[\"normal\",\"5\",\"5\"],\"command\":\"launch\"}");
    }

    @Test
    public void createStateCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "state", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[],\"command\":\"state\"}");
    }

    @Test
    public void createLookCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "look", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[],\"command\":\"look\"}");
    }

    @Test
    public void createForwardCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "forward", new String[]{"10"});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[\"10\"],\"command\":\"forward\"}");
    }

    @Test
    public void createBackCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "back", new String[]{"10"});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[\"10\"],\"command\":\"back\"}");
    }

    @Test
    public void createRightCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "right", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[\"right\"],\"command\":\"right\"}");
    }

    @Test
    public void createLeftCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "left", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[\"left\"],\"command\":\"left\"}");
    }

    @Test
    public void createRepairCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "repair", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[],\"command\":\"repair\"}");
    }

    @Test
    public void createReloadCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "reload", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[],\"command\":\"reload\"}");
    }

    @Test
    public void createMineCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "mine", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[],\"command\":\"mine\"}");
    }

    @Test
    public void createFireCommand() {
        IRobot robot;
        Encode encoder;

        robot = new NormalRobot("CrashTestDummy");
        encoder = new Encode(robot, "fire", new String[]{});
        assertEquals(encoder.getRequest().toString(), "{\"robot\":\"CrashTestDummy\",\"arguments\":[],\"command\":\"fire\"}");
    }

}
