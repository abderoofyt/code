package Commands.RobotCommands;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;

public class ReloadCommand extends RobotCommand {
    int reloadTime;

    public ReloadCommand() {
        super("reload", "");
        reloadTime = Config.fileUtils.getWeaponReloadTime();
    }

    /**
     * Executes the reload command.
     *
     * @param target   the Robot object that requested the reload command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the reload command.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        int maxShots;
        boolean isDoneReloading;

        maxShots = getRobotMaxShots(target);
        target.setStatus(Robot.Status.RELOADING);
        isDoneReloading = reloadTimer();
        target.setStatus(Robot.Status.NORMAL);
        if (isDoneReloading) {
            target.setShotsLeft(maxShots);
            target.setShotDistance(FireCommand.getMaxShotDistance());
        }
        return getReloadCommandResponse(target, isDoneReloading);
    }

    /**
     * Timer that runs for the configured reload time period.
     *
     * @return true  : if the time is up.
     *         false : if the timer did not finish.
     */
    private boolean reloadTimer() {
        int milliseconds;

        milliseconds = reloadTime * 1000;
        try {
            Thread.sleep(milliseconds);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * Retrieves the robots amount of gun shots from when it entered the world.
     *
     * @param target   the Robot object that requested the reload command to
     *                 be executed.
     * @return the amount of gun shots.
     */
    private int getRobotMaxShots(Robot target) {
        return target.getMaxShots();
    }

    /**
     * Creates the specific reload command JSONObject response, to send to the
     * client program.
     *
     * @param target   the Robot object that requested the reload command to be
     *                 executed.
     * @return the response JSONObject specific to the reload command.
     */
    private JSONObject getReloadCommandResponse(Robot target, boolean isDoneReloading) {
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(isDoneReloading, (this));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        data = encoder.otherCommandData("Done");
        state = encoder.createOtherCommandState(target, "RELOADING");
        response.put("data", data);
        response.put("state", state);
        return response;
    }
}
