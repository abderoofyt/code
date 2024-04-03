package Json;

import Robot.IRobot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Encode {

    private JSONObject request;

    public Encode(IRobot robot, String command, String[] arguments) {
        this.request = createRequest(robot, command, arguments);
    }

    /**
     * Adds the corresponding JSONArray to the response JSONObject, paired with the arguments-key,
     * depending on the command type.
     *
     * @param command     the command the robot has to execute.
     * @param request     the request JSONObject to add the arguments JSONArray to.
     * @param arguments   the command's arguments in the form of a String array.
     * @param robot       the robot that has to execute the command.
     * @return the request JSONObject containing the arguments JSONArray.
     */
    private JSONObject addArguments(String command, JSONObject request, String[] arguments, IRobot robot) {
        switch (command) {
            case "launch":
                request = addLaunchArgs(robot, request);
                break;
            case "forward":
            case "back":
                if (arguments.length == 0) {
                    request.put("arguments", new JSONArray());
                    return request;
                }
                request = addStepMoveArgs(arguments[0], request);
                break;
            case "right":
                request = addTurnMoveArgs(true, request);
                break;
            case "left":
                request = addTurnMoveArgs(false, request);
                break;
            case "look":
            default:
                request = addOtherArgs(request);
        }
        return request;
    }

    /**
     * Generates a request message to send to the server program.
     *
     * @param robot      the Robot object that represents the user.
     * @param command    the command the user wants the robot to execute.
     * @param arguments  the arguments for the given command.
     * @return the request message.
     */
    private JSONObject createRequest(IRobot robot, String command, String[] arguments) {
        JSONObject request = new JSONObject();

        request.put("robot", robot.getName());
        request.put("command", command);
        addArguments(command, request, arguments, robot);
        return request;
    }

    /**
     * Adds all the values from the given array to a JSONArray object, and
     * returns this JSONArray object.
     *
     * @param arr the array with the values to add to the JSONArray object.
     * @return the JSONArray object with all the values from the given array.
     */
    private JSONArray stringArrToJson(String[] arr) {
        JSONArray jsonArr;

        jsonArr = new JSONArray();
        for (String value: arr) {
            jsonArr.add(value);
        }
        return jsonArr;
    }

    /**
     * Adds the JSONArray, corresponding with the launch command, to the request JSONObject.
     *
     * @param robot    the robot that has to execute the launch command.
     * @param request  the request JSONObject without the arguments JSONArray.
     * @return the request JSONObject containing the corresponding arguments JSONArray.
     */
    private JSONObject addLaunchArgs(IRobot robot, JSONObject request) {
        String type;
        String shieldStrength;
        String maxShots;
        JSONArray arguments;

        type = robot.getType();
        shieldStrength = String.valueOf(robot.getShieldStrength());
        maxShots = String.valueOf(robot.getShots());
        arguments = stringArrToJson(new String[]{type, shieldStrength, maxShots});
        request.put("arguments", arguments);
        return request;
    }

    /**
     * Adds the JSONArray, corresponding with the forward, or back command, to the request JSONObject.
     *
     * @param steps    the amount of steps the robot needs to take when performing the forward, or back command.
     * @param request  the request JSONObject without the arguments JSONArray.
     * @return the request JSONObject containing the corresponding arguments JSONArray.
     */
    private JSONObject addStepMoveArgs(String steps, JSONObject request) {
        JSONArray arguments;

        arguments = stringArrToJson(new String[]{steps});
        request.put("arguments", arguments);
        return request;
    }

    /**
     * Adds the JSONArray, corresponding with the right, or left command, to the request JSONObject.
     *
     * @param isRight  true if the command is a right command.
     * @param request  the request JSONObject without the arguments JSONArray.
     * @return the request JSONObject containing the corresponding arguments JSONArray.
     */
    private JSONObject addTurnMoveArgs(boolean isRight, JSONObject request) {
        JSONArray arguments;
        String turn;

        turn = (isRight) ? "right" : "left";
        arguments = stringArrToJson(new String[]{turn});
        request.put("arguments", arguments);
        return request;
    }

    /**
     * Adds the JSONArray, corresponding with the state, look, repair, reload, mine, and fire command to
     * the request JSONObject.
     *
     * @param request  the request JSONObject without the arguments JSONArray.
     * @return the request JSONObject containing the corresponding arguments JSONArray.
     */
    private JSONObject addOtherArgs(JSONObject request) {
        JSONArray arguments;

        arguments = stringArrToJson(new String[]{});
        request.put("arguments", arguments);
        return request;
    }

    /**
     * Retrieves the request JSONObject.
     *
     * @return the JSONObject to send to the Server program.
     */
    public JSONObject getRequest() {
        return request;
    }

}
