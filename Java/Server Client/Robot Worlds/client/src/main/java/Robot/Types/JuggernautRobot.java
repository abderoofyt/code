package Robot.Types;

import Robot.AbstractRobot;

public class JuggernautRobot extends AbstractRobot {

    /**
     * Shield strength is higher than other robot types, since this robot should be able to
     * take more mine explosions before being pronounced dead.
     *
     * @param name   the name given to the robot.
     */
    public JuggernautRobot(String name){
        super(name,"juggernaut",5,10);
    }

}

