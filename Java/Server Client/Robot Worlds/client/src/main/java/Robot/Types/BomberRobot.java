package Robot.Types;

import Robot.AbstractRobot;

public class BomberRobot extends AbstractRobot {

    /**
     * Shield strength is higher than other robot types, since this robot should be able to
     * take more mine explosions before being pronounced dead. The number of shots is zero,
     * since this robot type can only set mines.
     *
     * @param name   the name given to the robot.
     */
    public BomberRobot(String name){
        super(name, "bomber", 0, 15);
    }

}
