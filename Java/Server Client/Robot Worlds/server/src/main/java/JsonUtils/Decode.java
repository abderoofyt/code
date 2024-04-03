package JsonUtils;

import Robot.Robot;
import World.World;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;

public class Decode {
    private final Robot robot;
    private final String robotName;
    private final String command;
    private final String arguments;

    public Decode(World world, JSONObject request) {
        this.robot = getRobot(world, request);
        this.robotName = getName(request);
        this.command = getCommand(request);
        this.arguments = getArguments(request);
    }

    /**
     * Retrieves the given JSONObject's 'robot'-key's paired value, which is the
     * robot's name. This name is used to return the World object's Robot object with
     * the matching name.
     *
     * @return      Robot : If an Robot object, with the given name value, exists in the World.World.
     *              null   : If there is no Robot object, with the given name value, that exists
     *                       in the world.
     */
    private Robot getRobot(World world, JSONObject request) {
        Robot robot;

        robot = world.getRobotWithName(String.valueOf(request.get("robot")));
        return robot;
    }

    /**
     * Retrieves the Robot object's, which has to perform the requested command, name attribute.
     * @param request the JSONObject, containing request information, retrieved from client program.
     * @return the Robot object's name attribute.
     */
    public String getName(JSONObject request) {
        return String.valueOf(request.get("robot"));
    }

    /**
     * Retrieves the given JSONObject's 'command'-key's paired value.
     *
     * @return      command the robot should execute.
     */
    private String getCommand(JSONObject request) {
        String command;
        String[] validCommands;

        command = ((String)request.get("command")).trim();
        validCommands = new String[]{"forward", "back", "right", "left", "state", "look", "reload", "repair", "mine", "launch", "fire"};
        if (Arrays.asList(validCommands).contains(command)) return command;
        return "errorCommand";
    }

    /**
     * Retrieves the given JSONObject's 'argument'-key's paired value in String form.
     *
     * @return      String containing the user's command arguments.
     */
    private String getArguments(JSONObject request) {
        StringBuilder builder;
        JSONArray arguments;

        builder = new StringBuilder();
        arguments = (JSONArray) request.get("arguments");
        if (arguments.size() == 0) return "";
        for (int i = 0; i < arguments.size(); i++) {
            String arg = (String) arguments.get(i);
            builder.append(arg);
            if (i != arguments.size()-1) builder.append(" ");
        }
        return builder.toString();
    }

    /**
     * Retrieves the IRobot object of the JsonDecode object.
     *
     * @return      the, json-format, request's IRobot object.
     */
    public Robot getRobot() {
        return this.robot;
    }

    /**
     * Retrieves the command of the JsonDecode object.
     *
     * @return      the, json-format, request's command.
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Retrieves the arguments of the JsonDecode object in String form.
     *
     * @return      the, json-format, request's String of arguments.
     */
    public String getArguments() {
        return this.arguments;
    }

}
