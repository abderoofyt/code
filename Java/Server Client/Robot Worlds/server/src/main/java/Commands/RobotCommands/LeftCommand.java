package Commands.RobotCommands;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;

public class LeftCommand extends RobotCommand {

    public LeftCommand(){super("left");}

    /**
     * Executes the left command.
     *
     * @param target   the Robot object that requested the left command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the left command.
     */
    @Override
    public JSONObject execute(Robot target, World world){
        target.updateDirection(false);
        return createTurnResponse(target, "NORMAL");
    }

    /**
     * Creates the specific left command JSONObject response, to send to the
     * client program.
     *
     * @param robot   the Robot object that requested the left command to be
     *                 executed.
     * @return the response JSONObject specific to the left command.
     */
    private JSONObject createTurnResponse(Robot robot, String status) {
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
