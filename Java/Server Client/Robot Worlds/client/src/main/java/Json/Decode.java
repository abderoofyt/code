package Json;

import TerminalView.Display;
import Robot.IRobot;
import Robot.Position;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Decode {
    private JSONObject response;
    private Display display;
    private String command;
    private int repairTime;
    private int mineTime;
    private int reloadTime;

    public Decode(JSONObject response, IRobot robot, Display display) {
        this.response = response;
        this.display = display;
        command = getCommandType(response, robot);
    }

    /**
     * Retrieves the robot's status from the response message, retrieved from
     * the server program.
     *
     * @param response   the response received from the server, in the form of a
     *                   JSONObject object.
     * @return the robot's status.
     */
    private String getRobotStatus(JSONObject response) {
        JSONObject state;
        String status;

        state = (JSONObject) response.get("state");
        status = (String) state.get("status");
        switch (status) {
            case "REPAIRING":
            case "REPAIR":
                return "repair";
            case "RELOADING":
            case "RELOAD":
                return "reload";
            case "SETMINE":
                return "mine";
        }
        return "normal";
    }

    /**
     * Retrieves the Direction object that correlates with the given String object.
     *
     * @param direction   the direction, in the form of a String.
     * @return the Direction object.
     */
    private IRobot.Direction stringToDirection(String direction) {
        switch (direction) {
            case "NORTH":
                return IRobot.Direction.NORTH;
            case "EAST":
                return IRobot.Direction.EAST;
            case "SOUTH":
                return IRobot.Direction.SOUTH;
        }
        return IRobot.Direction.WEST;
    }

    /**
     * Retrieves the error command that correlates with the given message. If no
     * error message correlates with the given message, the function returns an
     * empty String object.
     *
     * @param message   the message retrieved from the response message,
     *                  retrieved from the server program.
     * @return the error command.
     */
    private String getErrorType(String message) {
        if (message.equals("Could not parse arguments")) {
            return "errorParse";
        } else if (message.equals("Unsupported command")) {
            return "errorCommand";
        } else if (message.equals("Too many of you in this world")) {
            return "errorType";
        } else if (message.equals("No more space in this world")) {
            return "errorLocation";
        }
        return "";
    }

    /**
     * Determines which command was executed by the server program, by analysing
     * the response message layout.
     *
     * @param response   the response message received from the server program,
     *                   in the form of a JSONObject object.
     * @param robot      the Robot object assigned to the user.
     * @return the command that was executed.
     */
    private String getCommandType(JSONObject response, IRobot robot) {
        JSONObject data;
        JSONObject state;
        String status;

        data = (JSONObject) response.get("data");
        state = (JSONObject) response.get("state");
        if (data == null) return "state";
        if (data.get("message") == null) {
            if (data.get("objects") == null) {
                return "launch";
            }
            return "look";
        } else if ((state != null) && state.get("status") != null) {
            status = getRobotStatus(response);
            if (status.equals("normal")) {
                if ((robot.getDirection()).equals(stringToDirection((String)state.get("direction")))) {
                    return "movement";
                }
                return "turn";
            }
            return status;
        } else if (data.get("message") != null) {
            if ((state != null) && (state.get("shots") != null)) {
                return "fire";
            }
            return getErrorType((String)data.get("message"));
        }
        return "invalid";
    }

    /**
     * Updates the IRobot object with the information given by the response message,
     * from the server program.
     *
     * @param robot   the IRobot object to update.
     */
    public void decodeResponse(IRobot robot) {
        switch (command) {
            case "errorParse":
                robot.setStatus("Server cannot parse the command.");
                return;
            case "errorCommand":
                robot.setStatus("Unrecognised command. Please choose a valid command.");
                return;
            case "errorType":
                robot.setStatus("I cannot launch into the world. There already exists a robot of the same type.");
                return;
            case "errorLocation":
                robot.setStatus("I cannot launch into the world. There is no free location.");
                return;
            case "state":
                decodeState(robot);
                return;
            case "launch":
                decodeLaunch(robot);
                return;
            case "look":
                decodeLook(robot);
                return;
            case "fire":
                decodeFire(robot);
                return;
            case "movement":
            case "turn":
            case "repair":
            case "reload":
            case "mine":
                decodeOther(robot);
                return;
            default:
                robot.setStatus("An error occurred. Please try again later.");
                display.refresh(false, true, false);
                System.exit(0);
        }
    }

    /**
     * Retrieves the JSONObject object value, paired with the 'data'-key, in the
     * response message.
     *
     * @return the JSONObject object value, paired with the 'data'-key.
     */
    private JSONObject getDataJson() {
        return (JSONObject) response.get("data");
    }

    /**
     * Retrieves the JSONObject object value, paired with the 'state'-key, in the
     * response message.
     *
     * @return the JSONObject object value, paired with the 'state'-key.
     */
    private JSONObject getStateJson() {
        return (JSONObject) response.get("state");
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the fire command in the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private void decodeFire(IRobot robot) {
        JSONObject data;
        JSONObject state;
        int shots;
        String shotRobot;
        String status;

        data = getDataJson();
        state = getStateJson();
        shots = ((Number)state.get("shots")).intValue();
        if (((String)data.get("message")).equals("Miss")) {
            status = "Ah man, I missed the shot! I have "+shots+" shots left.";
        } else {
            shotRobot = (String)data.get("robot");
            status = "Woohoo! I shot "+shotRobot+". I have "+shots+" shots left.";
        }
        //update robot shots left
        robot.setShots(shots);
        //update robot status
        robot.setStatus(status);
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message.
     *
     * @param robot   the IRobot object to update.
     */
    private void updateRobot(IRobot robot) {
        JSONObject state;
        JSONArray position;

        state = getStateJson();
        position = (JSONArray) state.get("position");
        robot.setShieldStrength(((Number)state.get("shields")).intValue());
        robot.setShots( ((Number)state.get("shots")).intValue());
        robot.setPosition(new Position(((Number)position.get(0)).intValue(), ((Number)position.get(1)).intValue()));
        robot.setDirection(stringToDirection((String)state.get("direction")));
    }

    /**
     * Determines the amount of steps from the IRobot object's position if from the JSONArray position.
     *
     * @param currentDirection   the IRobot object's Direction eNum.
     * @param position           the JSONArray position of the robot, in the server program.
     * @param robot              the IRobot object used to compare positions with.
     * @return the amount of steps from the IRobot object's position to the JSONArray position.
     */
    private int getMovementSteps(IRobot.Direction currentDirection, JSONArray position, IRobot robot) {
        int currentX;
        int currentY;
        int newX;
        int newY;

        currentX = robot.getPosition().getX();
        currentY = robot.getPosition().getY();
        newX = ((Number)position.get(0)).intValue();
        newY = ((Number)position.get(1)).intValue();
        switch (currentDirection) {
            case NORTH:
            case SOUTH:
                return (Math.max(newY, currentY) - Math.min(newY, currentY));
        }
        return (Math.max(newX, currentX) - Math.min(newX, currentX));
    }

    /**
     * Determines whether the command executed in the server program was a forward movement.
     *
     * @param position           the JSONArray containing the robot's position in the
     *                           server program's 'world'-simulation.
     * @param currentDirection   the IRobot object's Direction eNum.
     * @param robot              the IRobot object that performed the command.
     * @return true  : if the command was a forward command.
     *         false : if the command was a back command.
     */
    private boolean isForwardMovement(JSONArray position, IRobot.Direction currentDirection, IRobot robot) {
        int currentX;
        int currentY;
        int newX;
        int newY;

        currentX = robot.getPosition().getX();
        currentY = robot.getPosition().getY();
        newX = ((Number)position.get(0)).intValue();
        newY = ((Number)position.get(1)).intValue();
        switch (currentDirection) {
            case NORTH:
                return (newY > currentY);
            case EAST:
                return (newX > currentX);
            case SOUTH:
                return (newY < currentY);
        }
        //west
        return (newX < currentX);
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the forward, or back, command in the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private String decodeMovement(IRobot robot) {
        String status;
        JSONObject state;
        JSONObject data;
        JSONArray position;
        int steps;
        boolean isForward;

        state = getStateJson();
        data = getDataJson();
        if (((String)data.get("message")).equals("Done")) {
            //moved
            position = (JSONArray) state.get("position");
            isForward = isForwardMovement(position, robot.getDirection(), robot);
            steps = getMovementSteps(robot.getDirection(), position, robot);
            status = (isForward) ? "Moved forward by "+steps+" steps." : "Moved backward by "+steps+" steps.";
        } else if (((String)data.get("message")).equals("Obstructed")) {
            //obstructed
            status = "There is an obstruction in the way.";
        } else {
            //fell
            status = "Oh no, I fell into a pitfall. Bye bye.";
            robot.setStatus(status);
            killRobot();
        }
        updateRobot(robot);
        return status;
    }

    /**
     * Determines whether the command executed in the server program was a 'right'-command.
     *
     * @param currentDirection   the IRobot object's assigned Direction eNum.
     * @param newDirection       the IRobot object's new Direction eNum.
     * @return true  : if the executed command was a 'right'-command.
     *         false : if the executed command was not a 'right'-command.
     */
    private boolean isRightTurn(IRobot.Direction currentDirection, IRobot.Direction newDirection) {
        switch (currentDirection) {
            case NORTH:
                return (newDirection.equals(IRobot.Direction.EAST));
            case EAST:
                return (newDirection.equals(IRobot.Direction.SOUTH));
            case SOUTH:
                return (newDirection.equals(IRobot.Direction.WEST));
        }
        return (newDirection.equals(IRobot.Direction.NORTH));
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the right, or left, command in the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private String decodeTurn(IRobot robot) {
        JSONObject state;
        JSONObject data;
        String status;
        boolean isRight;

        state = getStateJson();
        data = getDataJson();
        status = "I could not turn.";
        if (((String)data.get("message")).equals("Done")) {
            isRight = isRightTurn(robot.getDirection(), stringToDirection((String)state.get("direction")));
            status = (isRight) ? "I turned right. I am now facing "+state.get("direction")+"." : "I turned left. I am now facing "+state.get("direction")+".";
            updateRobot(robot);
        }
        return status;
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the forward, back, right, left, repair, reload, or mine command in
     * the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private void decodeOther(IRobot robot) {
        //movement; turn; repair; reload; mine
        String status;

        status = "Mine is activated. Be careful, I do not want to step into it!";
        switch (command) {
            case "movement":
                status = decodeMovement(robot);
                break;
            case "turn":
                status = decodeTurn(robot);
                break;
            case "repair":
                updateRobot(robot);
                status = "I repaired my shields. My shield strength is now "+robot.getShieldStrength()+".";
                break;
            case "reload":
                updateRobot(robot);
                status = "I reloaded by weapon. I now have "+robot.getShots()+" shots left.";
                break;
            case "mine":
                updateRobot(robot);
                break;
        }
        robot.setStatus(status);
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the look command in the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private void decodeLook(IRobot robot) {
        String status;
        JSONObject data;
        JSONArray objects;
        JSONObject object;
        StringBuilder sb;

        sb = new StringBuilder();
        if (!isSuccessful()) {
            status = "Could not execute the look command.";
            sb.append(status);
        } else {
            data = getDataJson();
            objects = (JSONArray) data.get("objects");
            status = (objects.size() == 0) ? "   There are no obstructions visible." : "The following obstructions are visible:\n";
            sb.append(status);
            for (int i = 0; i < objects.size(); i++) {
                object = (JSONObject) objects.get(i);
                sb.append("                "+object.get("direction")+"  :  "+object.get("type")+" : "+object.get("distance")+" steps away\n");
            }
        }
        robot.setStatus(sb.toString());
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the state command in the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private void decodeState(IRobot robot) {
        JSONObject state;

        state = (JSONObject) response.get("state");
        if (((String)state.get("status")).equals("DEAD")) {
            robot.setStatus("Oh no, I got killed. Bye bye");
            killRobot();
        }
        robot.setStatus("Status has been updated down below.");
        updateRobot(robot);
    }

    /**
     * Updates the IRobot object, with the information retrieved from the response message,
     * after executing the launch command in the server program's 'world'-simulation.
     *
     * @param robot   the IRobot object to update.
     */
    private void decodeLaunch(IRobot robot) {
        JSONObject state;
        JSONObject data;
        String status;

        state = getStateJson();
        data = getDataJson();
        status = (((String)state.get("status")).equals("NORMAL")) ? "Ready!" : "Oh no, I died.";
        this.mineTime = ((Number)data.get("mine")).intValue();
        this.repairTime = ((Number)data.get("repair")).intValue();
        this.reloadTime = ((Number)data.get("reload")).intValue();
        robot.setStatus(status);
        updateRobot(robot);
    }

    /**
     * Checks whether the command was successfully executed, by retrieving the value paired with
     * the 'result'-key in the response message.
     *
     * @return true  : if the command was successfully executed.
     *         false : if the command was not successfully executed.
     */
    public boolean isSuccessful() {
        return ((String)response.get("result")).equals("OK");
    }

    /**
     * Indicates to the user that the robot has died in the server program's 'world'-simulation,
     * then exits the program.
     */
    private void killRobot() {
        display.refresh(false, false, false);
        System.exit(0);
    }

    /**
     * Retrieves the server program's 'world'-simulation's set repair time (in seconds).
     *
     * @return the repair time, in seconds.
     */
    public int getRepairTime() {
        return this.repairTime;
    }

    /**
     * Retrieves the server program's 'world'-simulation's set mine time (in seconds).
     *
     * @return the repair time, in seconds.
     */
    public int getMineTime() {
        return this.mineTime;
    }

    /**
     * Retrieves the server program's 'world'-simulation's set reload time (in seconds).
     *
     * @return the repair time, in seconds.
     */
    public int getReloadTime() {
        return this.reloadTime;
    }

    /**
     * Retrieves the command executed by the server program.
     *
     * @return the command executed by the server program.
     */
    public String getCommand() {
        return command;
    }

}
