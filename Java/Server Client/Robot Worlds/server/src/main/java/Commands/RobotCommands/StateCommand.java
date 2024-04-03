package Commands.RobotCommands;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;

public class StateCommand extends RobotCommand {

    public StateCommand() {
        super("state", "");
    }

    /**
     * Executes the state command.
     *
     * @param target   the Robot object that requested the state command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the state command.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        return createStateResponse(target);
    }

    /**
     * Creates the specific state command JSONObject response, to send to the
     * client program.
     *
     * @param target   the Robot object that requested the state command to be
     *                 executed.
     * @return the response JSONObject specific to the state command.
     */
    private JSONObject createStateResponse(Robot target) {
        Encode encoder;
        JSONObject response;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        state = encoder.createOtherCommandState(target, "NORMAL");
        response.put("state", state);
        return response;
    }

}
