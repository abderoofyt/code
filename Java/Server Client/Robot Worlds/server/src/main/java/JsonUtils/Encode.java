package JsonUtils;

import Commands.RobotCommand;
import Robot.Robot;
import Robot.Position;
import Robot.Robot.Direction;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Dictionary;

public class Encode {
    String command;
    boolean executed;

    public Encode(boolean executed, RobotCommand command) {
        this.executed = executed;
        this.command = command.getName();
    }

    /**
     * Returns "OK" if the command was executed, and "ERROR" if the
     * command was not executed and an error occurred.
     *
     * @return "OK"    : if the command was successfully executed.
     *         "ERROR" : if the command was not successfully executed.
     */
    public String getResult() {
        if (executed) {
            return "OK";
        }
        return "ERROR";
    }

    /**
     * Creates the 'data'-key value of the look command's response message to
     * the client program.
     *
     * @param obstructions     Dictionary object that contains the obstructions
     *                         within the visibility range of the robot that
     *                         requested the look command.
     * @param directionSteps   int array containing the steps to the closest
     *                         obstruction in each direction.
     * @return the 'data'-key value to incorporate into the response message
     *         that will be sent back to the client program.
     */
    public JSONObject createLookCommandData(Dictionary<String, String> obstructions, int[] directionSteps) {
        JSONObject data;
        JSONArray objects;
        String[] directions;
        int i;
        JSONObject object;
        String type;

        data = new JSONObject();
        objects = new JSONArray();
        directions = new String[]{"NORTH", "EAST", "SOUTH", "WEST"};
        i = 0;
        for (String direction : directions) {
            object = new JSONObject();
            type = obstructions.get(direction.toLowerCase());
            if (type == null) {
                i++;
                continue;
            }
            object.put("direction", direction);
            object.put("type", type);
            object.put("distance", directionSteps[i]);
            objects.add(object);
            i++;
        }
        data.put("objects", objects);
        return data;
    }

    /**
     * Create the 'data'-key value of the fire command's response message to
     * the client program.
     *
     * @param shotRobotDistance   the distance between the shooter robot and
     *                            the shot robot.
     * @param shotRobotName       the name of the shot robot.
     * @param hit                 true if the shooter robot shot a robot.
     * @param shotRobotResponse   the response sent to the shot robot after
     *                            the shooter robot shot the robot.
     * @return the 'data'-key value to incorporate into the response message
     *         that will be sent back to the client program.
     */
    public JSONObject createFireCommandShooterData(int shotRobotDistance, String shotRobotName, boolean hit, JSONObject shotRobotResponse) {
        JSONObject data;
        String message;

        data = new JSONObject();
        message = (hit) ? "Hit" : "Miss";
        data.put("message", message);
        if (message.equals("Hit")) {
            data.put("distance", shotRobotDistance);
            data.put("robot", shotRobotName);
            data.put("state", shotRobotResponse.get("state"));
        }
        return data;
    }

    /**
     * Create the 'data'-key value of the launch command's response message to
     * the client program.
     *
     * @param robot   the Robot object created when the launch command was
     *                executed.
     * @return the 'data'-key value to incorporate into the response message
     *         that will be sent back to the client program.
     */
    public JSONObject createLaunchCommandData(Robot robot) {
        JSONObject data;

        data = new JSONObject();
        data.put("position", createPositionJsonArr(robot.getPosition()));
        data.put("visibility", Config.fileUtils.getVisibility());
        data.put("reload", Config.fileUtils.getWeaponReloadTime());
        data.put("repair", Config.fileUtils.getRepairTime());
        data.put("mine", Config.fileUtils.getTimeToSetMine());
        data.put("shields", Config.fileUtils.getMaxShieldStrength());
        return data;
    }

    /**
     * Creates the 'data'-key value of the forward, back, right, left, repair,
     * or reload command's response message to the client program.
     *
     * @param message   the String value to assign to the 'data'-key's
     *                  JSONObject object's 'message'-key.
     * @return the 'data'-key value to incorporate into the response message
     *         that will be sent back to the client program.
     */
    public JSONObject otherCommandData(String message) {
        //forward; back; right; left; repair; reload
        JSONObject data;

        data = new JSONObject();
        data.put("message", message);
        return data;
    }

    /**
     * Creates the 'state'-key value of the fire command's response message
     * to the client program.
     *
     * @param shotsLeft   the amount of shots the robot has left.
     * @return the 'state'-key value to incorporate into the response message
     *         that will be sent back to the client program.
     */
    public JSONObject createFireCommandState(int shotsLeft) {
        JSONObject state;

        state = new JSONObject();
        state.put("shots", shotsLeft);
        return state;
    }

    /**
     * Creates a JSONArray containing the x-value of the Position object as the
     * first value in the JSONArray, and the y-value of the Position object as the
     * second value in the JSONArray.
     *
     * @param position   the position to convert into a JSONArray object.
     * @return the JSONArray containing the x-, and y-values of the Position
     *         object.
     */
    private JSONArray createPositionJsonArr(Position position) {
        JSONArray arrPosition;

        arrPosition = new JSONArray();
        arrPosition.add(position.getX());
        arrPosition.add(position.getY());
        return arrPosition;
    }

    /**
     * Returns the String value that correlates with the given Direction eNum.
     *
     * @param direction   the Direction eNum to convert to a String.
     * @return the String value of the Direction eNum that was given.
     */
    private String directionToString(Direction direction) {
        switch (direction) {
            case NORTH:
                return "NORTH";
            case EAST:
                return "EAST";
            case SOUTH:
                return "SOUTH";
        }
        return "WEST";
    }

    /**
     * Creates the 'state'-key value of the forward, back, right, left, repair,
     * reload, or state command's response message to the client program.
     *
     * @param robot   the Robot object that executed the command.
     * @param status  the Robot object's status
     *                (NORMAL, RELOADING, REPAIRING, DEAD, or SETMINE).
     * @return the 'state'-key value to incorporate into the response message
     *         that will be sent back to the client program.
     */
    public JSONObject createOtherCommandState(Robot robot, String status) {
        //forward; back; right; left; repair; reload; state
        JSONObject state;

        state = new JSONObject();
        state.put("position", createPositionJsonArr(robot.getPosition()));
        state.put("direction", directionToString(robot.getDirection()));
        state.put("shields", robot.getShieldStrength());
        state.put("shots", robot.getShotsLeft());
        state.put("status", status.toUpperCase());
        return state;
    }

}
