package Commands.RobotCommands;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;

public class RepairCommand extends RobotCommand {
    boolean complete;

    public RepairCommand() {
        super("repair", "");
    }

    /**
     * Executes the repair command.
     *
     * @param target   the Robot object that requested the repair command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the repair command.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        int shield;

        shield = getShieldStrength(target.getMaxShieldStrength(), Config.fileUtils.getMaxShieldStrength());
        target.setStatus(Robot.Status.REPAIRING);
        complete = repairTimer();
        target.setStatus(Robot.Status.NORMAL);
        target.setShieldStrength(shield);
        return getRepairResponse(target);
    }

    /**
     * Retrieves the robots shield strength.
     *
     * @param robotMaxShield   the shield strength of the robot before shields
     *                         were damaged.
     * @param worldMaxShield   the world's maximum allowed shield strength.
     * @return
     */
    private int getShieldStrength(int robotMaxShield, int worldMaxShield) {
        if (robotMaxShield > worldMaxShield) {
            return worldMaxShield;
        }
        return robotMaxShield;
    }

    /**
     * Timer that runs for the configured repair time period.
     *
     * @return true  : if the time is up.
     *         false : if the timer did not finish.
     */
    private boolean repairTimer() {
        int milliseconds;

        milliseconds = (Config.fileUtils.getRepairTime()) * 1000;
        try {
            Thread.sleep(milliseconds);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * Creates the specific repair command JSONObject response, to send to the
     * client program.
     *
     * @param target   the Robot object that requested the repair command to be
     *                 executed.
     * @return the response JSONObject specific to the repair command.
     */
    private JSONObject getRepairResponse(Robot target) {
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(complete, (this));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        data = encoder.otherCommandData("Done");
        state = encoder.createOtherCommandState(target, "REPAIR");
        response.put("data", data);
        response.put("state", state);
        return response;
    }
}
