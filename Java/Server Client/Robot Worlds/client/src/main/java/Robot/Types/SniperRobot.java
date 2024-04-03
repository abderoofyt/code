package Robot.Types;

import Robot.AbstractRobot;

public class SniperRobot extends AbstractRobot {

    /**
     * Shots is higher since this robot type can shoot more robots at a greater distance.
     * The shield strength is also higher than average, since this robot type should be
     * able to more shots before being pronounced dead.
     *
     * @param name   the name given to the robot.
     */
    public SniperRobot(String name) {
        super(name, "sniper", 20, 7);
    }

}
