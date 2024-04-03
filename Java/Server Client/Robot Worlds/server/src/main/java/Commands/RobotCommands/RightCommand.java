package Commands.RobotCommands;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;

public class RightCommand extends RobotCommand {

    public RightCommand(){super("right");}

    /**
     * Executes the right command.
     *
     * @param target   the Robot object that requested the right command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the right command.
     */
    @Override
    public JSONObject execute(Robot target, World world){
        target.updateDirection(true);
        return createTurnResponse(target);
    }

    /**
     * Creates the specific right command JSONObject response, to send to the
     * client program.
     *
     * @param robot   the Robot object that requested the right command to be
     *                 executed.
     * @return the response JSONObject specific to the right command.
     */
    private JSONObject createTurnResponse(Robot robot) {
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        data = encoder.otherCommandData("Done");
        state = encoder.createOtherCommandState(robot, "NORMAL");
        response.put("data", data);
        response.put("state", state);
        return response;
    }
}