package Robot.Types;

import Robot.*;

public class NormalRobot extends AbstractRobot {

    /**
     * Normal robot type with the average number of shots and shield strength.
     *
     * @param name   the name given to the robot.
     */
    public NormalRobot(String name) {
        super(name, "normal", 5, 5);
    }

}
