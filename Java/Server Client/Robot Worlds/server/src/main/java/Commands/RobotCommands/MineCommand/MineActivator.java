package Commands.RobotCommands.MineCommand;

import Robot.Robot;
import World.Mine;

public class MineActivator {
    Mine mine;
    Robot robot;

    public MineActivator(Robot robot, Mine mine) {
        this.mine = mine;
        this.robot = robot;
    }

    /**
     * Activates the Mine object.
     */
    public void start() {
        boolean isReady;
        int shieldStrength;

        shieldStrength = robot.getShieldStrength();
        robot.setShieldStrength(0);
        isReady = getMineReady();
        robot.setShieldStrength(shieldStrength);
        if (isReady) {
            mine.Activate();
        }
    }

    /**
     * Timer that runs for the configured mine time.
     *
     * @return true  : if the timer is done running.
     *         false : if the timer is not done running.
     */
    private boolean getMineReady() {
        try {
            Thread.sleep(10000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Activates the Mine object.
     */
    public void activateMine() {
        (this).start();
    }

}