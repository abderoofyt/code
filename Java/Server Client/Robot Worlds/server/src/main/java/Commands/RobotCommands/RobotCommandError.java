package Commands.RobotCommands;

import Commands.RobotCommand;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;

public class RobotCommandError extends RobotCommand {
    private String error;

    public RobotCommandError(String error) {
        super("error", error);
        this.error = error;
    }

    /**
     * Retrieves the correct error response message to send to the client
     * program.
     *
     * @param target   the Robot object that requested the state command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the error command.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        JSONObject response;

        response = new JSONObject();
        response.put("result", "ERROR");
        switch (error) {
            case "command":
                return getCommandErrorResponse(response);
            case "location":
                return getLocationErrorResponse(response);
            case "type":
                return getRobotTypeErrorResponse(response);
            default:
                return getParseErrorResponse(response);
        }
    }

    /**
     * Creates the specific parse error command JSONObject response,
     * to send to the client program.
     *
     * @param response   the JSONObject to add to and return.
     * @return the response JSONObject specific to the parse error.
     */
    private JSONObject getParseErrorResponse(JSONObject response) {
        JSONObject data;

        data = new JSONObject();
        data.put("message", "Could not parse arguments");
        response.put("data", data);
        return response;
    }

    /**
     * Creates the specific type error command JSONObject response,
     * to send to the client program.
     *
     * @param response   the JSONObject to add to and return.
     * @return the response JSONObject specific to the type error.
     */
    private JSONObject getRobotTypeErrorResponse(JSONObject response) {
        JSONObject data;

        data = new JSONObject();
        data.put("message", "Too many of you in this world");
        response.put("data", data);
        return response;
    }

    /**
     * Creates the specific location error command JSONObject response,
     * to send to the client program.
     *
     * @param response   the JSONObject to add to and return.
     * @return the response JSONObject specific to the location error.
     */
    private JSONObject getLocationErrorResponse(JSONObject response) {
        JSONObject data;

        data = new JSONObject();
        data.put("message", "No more space in this world");
        response.put("data", data);
        return response;
    }

    /**
     * Creates the specific command error command JSONObject response,
     * to send to the client program.
     *
     * @param response   the JSONObject to add to and return.
     * @return the response JSONObject specific to the command error.
     */
    private JSONObject getCommandErrorResponse(JSONObject response) {
        JSONObject data;

        data = new JSONObject();
        data.put("message", "Unsupported command");
        response.put("data", data);
        return response;
    }

}
