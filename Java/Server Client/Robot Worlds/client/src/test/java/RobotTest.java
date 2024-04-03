import Robot.IRobot;
import Robot.Types.NormalRobot;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    @Test
    public void createNormalRobot() {
        IRobot normal;

        normal = new NormalRobot("CrashTestDummy");
        assertEquals(normal.getName(), "CrashTestDummy");
        assertEquals(normal.getType(), "normal");
    }

}
