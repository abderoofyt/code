package Commands.RobotCommands;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import Robot.Robot.Direction;
import Robot.Position;
import World.*;
import org.json.simple.JSONObject;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

public class LookCommand extends RobotCommand {
    int northObstructSteps;
    int southObstructSteps;
    int westObstructSteps;
    int eastObstructSteps;

    public LookCommand() {
        super("look");
        northObstructSteps = 0;
        southObstructSteps = 0;
        westObstructSteps = 0;
        eastObstructSteps = 0;
    }

    /**
     * Executes the look command.
     *
     * @param target   the Robot object that requested the look command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the look command.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        Dictionary<String, String> obstructions;

        obstructions = populateDictionary(world, target);
        return createLookResponse(obstructions, target);
    }

    /**
     * Creates the specific look command JSONObject response, to send to the
     * client program.
     *
     * @param obstructions   the Dictionary of obstructions in the visible range of the robot.
     * @param robot          the Robot object that requested the look command to be
     *                       executed.
     * @return the response JSONObject specific to the look command.
     */
    private JSONObject createLookResponse(Dictionary<String, String> obstructions , Robot robot) {
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        data = encoder.createLookCommandData(obstructions, new int[]{northObstructSteps, eastObstructSteps, southObstructSteps, westObstructSteps});
        state = encoder.createOtherCommandState(robot, "NORMAL");
        response.put("data", data);
        response.put("state", state);
        return response;
    }

    /**
     * Fills the dictionary with the closest, visible obstruction in each direction.
     *
     * @param world    the World object used to represent the 'world'-simulation.
     * @param target   the Robot object that requested for the look command to
     *                 be executed.
     * @return the Dictionary of obstructions.
     */
    private Dictionary<String, String> populateDictionary(World world, Robot target) {
        Dictionary<String, String> obstructions;

        obstructions = new Hashtable<>();
        obstructions = addNearestObstruction("north", world, target, obstructions);
        obstructions = addNearestObstruction("east", world, target, obstructions);
        obstructions = addNearestObstruction("south", world, target, obstructions);
        obstructions = addNearestObstruction("west", world, target, obstructions);
        return obstructions;
    }

    /**
     * Determines the distance between two robot positions.
     * @param robotPos     the robot's, that requested the look command, position.
     * @param otherRobot   the other robot.
     * @param direction    the direction to check (north, east, south, west).
     * @return
     */
    private int getRobotDistance(Position robotPos, Robot otherRobot, String direction) {
        Position otherRobotPos;

        otherRobotPos = otherRobot.getPosition();
        switch (direction) {
            case "north":
                return (otherRobotPos.getY() - robotPos.getY());
            case "south":
                return (robotPos.getY() - otherRobotPos.getY());
            case "west":
                return (robotPos.getX() - otherRobotPos.getX());
        }
        return (otherRobotPos.getX() - robotPos.getX());
    }

    /**
     * Retrieves the nearest, visible robot.
     *
     * @param direction   the direction to check.
     * @param world       the World object that represents the 'world'-simulation.
     * @param robot       the Robot object that requested the look command to be
     *                    executed.
     * @return the Robot object : if there is a visible robot.
     *         null             : if there is no visible robot.
     */
    private Robot getNearestVisibleRobot(String direction, World world, Robot robot) {
        Position robotPos;
        List<Robot> robots;
        int visibility;
        int nearest;
        int steps;
        Robot nearestRobot;

        robotPos = robot.getPosition();
        robots = world.getAllRobots();
        visibility = Config.fileUtils.getVisibility();
        nearest = 200;
        nearestRobot = null;
        for (Robot foundRobot : robots) {
            if (robot.equals(foundRobot)) continue;
            steps = getRobotDistance(robotPos, foundRobot, direction);
            if (steps > 0) {
                if ((visibility >= steps) && (steps < nearest)) {
                    nearest = steps;
                    nearestRobot = foundRobot;
                }
            }
        }
        return nearestRobot;
    }

    /**
     * Determines the amount of steps to the edge, in the specified direction.
     *
     * @param robotPos    the robot's position.
     * @param direction   the direction to check.
     * @return the amount of steps.
     */
    private int getStepsToEdge(Position robotPos, String direction) {
        Position edgeBottomRight;
        Position edgeTopLeft;

        edgeBottomRight = Config.fileUtils.getBoundaryBottomRight();
        edgeTopLeft = Config.fileUtils.getBoundaryTopLeft();
        switch (direction) {
            case "north":
                return (edgeTopLeft.getY() - robotPos.getY());
            case "south":
                return(robotPos.getY() - edgeBottomRight.getY());
            case "west":
                return (robotPos.getX() - edgeTopLeft.getX());
        }
        return (edgeBottomRight.getX() - robotPos.getX());
    }

    /**
     * Determines the smallest value amongst three given values.
     * @param a   first value to compare.
     * @param b   second value to compare.
     * @param c   third value to compare.
     * @return the smallest value.
     */
    private int findSmallestValue(int a, int b, int c) {
        if (a <= b && a <= c) {
            return a;
        } else if (b <= c && b <= a) {
            return b;
        }
        return c;
    }

    /**
     * Updates the direction's steps to the closest obstruction.
     *
     * @param steps       the amount of steps.
     * @param direction   the direction to check.
     */
    private void setDirectionSteps(int steps, String direction) {
        switch (direction) {
            case "north":
                northObstructSteps = steps;
                break;
            case "south":
                southObstructSteps = steps;
                break;
            case "west":
                westObstructSteps = steps;
                break;
            case "east":
                eastObstructSteps = steps;
        }
    }

    /**
     * Determines to which position the robot's visibility reaches.
     *
     * @param robotPos     the robot's position.
     * @param direction    the direction to check.
     * @param visibility   the configured visibility.
     * @return the position to which the robot's visibility reaches.
     */
    private static Position getVisibilityFinalDest(Position robotPos, Direction direction, int visibility) {
        int x;
        int y;
        int newX;
        int newY;

        x = robotPos.getX();
        y = robotPos.getY();
        newX = x;
        newY = y;
        switch (direction) {
            case NORTH:
                newY = y + visibility;
                break;
            case WEST:
                newX = x - visibility;
                break;
            case SOUTH:
                newY = y - visibility;
                break;
            case EAST:
                newX = x + visibility;
                break;
        }
        return new Position(newX, newY);
    }

    /**
     * Converts the given String value to the correlating Direction eNum.
     *
     * @param direction   the direction, in String form.
     * @return the Direction eNum that correlates.
     */
    private static Robot.Direction convertStringToDirection(String direction) {
        switch (direction.toLowerCase()) {
            case "north":
                return Robot.Direction.NORTH;
            case "south":
                return Robot.Direction.SOUTH;
            case "west":
                return Robot.Direction.WEST;
        }
        return Robot.Direction.EAST;
    }

    /**
     * Determines whether the edge is visible in the given direction.
     * @param robot        the Robot object that requested the look command
     *                     to be executed.
     * @param visibility   the configured visibility.
     * @param direction    the direction to check.
     * @return true  : if the edge is visible in the specified direction.
     *         false : if the edge is not visible in the specified direction.
     */
    private boolean edgeIsVisible(Position robot, int visibility, String direction) {
        Position finalDest;
        Position edgeBottomRight;
        Position edgeTopLeft;

        edgeBottomRight = Config.fileUtils.getBoundaryBottomRight();
        edgeTopLeft = Config.fileUtils.getBoundaryTopLeft();
        finalDest = getVisibilityFinalDest(robot, convertStringToDirection(direction), visibility);
        switch (direction.toLowerCase()) {
            case "north":
                return (finalDest.getY() >= (edgeTopLeft.getY()-visibility));
            case "east":
                return (finalDest.getX() >= (edgeBottomRight.getX()-visibility));
            case "south":
                return (finalDest.getY() <= (edgeBottomRight.getY()+visibility));
        }
        return (finalDest.getX() <= (edgeTopLeft.getX()+visibility));
    }

    /**
     * Add the nearest, visible obstruction, in the specified direction, to the given Dictionary.
     *
     * @param direction      the direction to check.
     * @param world          the World object that represents the 'world'-simulation.
     * @param robot          the Robot object that requested the look command to be executed.
     * @param obstructions   the Dictionary containing the different obstructions in each direction.
     * @return the Dictionary of obstructions in each direction.
     */
    private Dictionary<String, String> addNearestObstruction(String direction, World world, Robot robot, Dictionary<String, String> obstructions) {
        Obstacle foundObstacle;
        Pitfall foundPitfall;
        Robot foundRobot;
        int obsSteps;
        int pitSteps;
        int robotSteps;
        int edgeSteps;
        int smallest;

        foundObstacle = Obstacle.getNearestVisibleObstacle(direction, world, robot);
        foundPitfall = Pitfall.getNearestVisiblePitfall(direction, world, robot);
        foundRobot = getNearestVisibleRobot(direction, world, robot);

        obsSteps = (foundObstacle != null) ? Obstacle.getSteps(robot.getPosition(), foundObstacle, direction) : 200;
        pitSteps = (foundPitfall != null) ? Pitfall.getSteps(robot.getPosition(), foundPitfall, direction) : 200;
        robotSteps = (foundRobot != null) ? getRobotDistance(robot.getPosition(), foundRobot, direction) : 200;
        edgeSteps = (edgeIsVisible(robot.getPosition(), Config.fileUtils.getVisibility(), direction)) ? getStepsToEdge(robot.getPosition(), direction) : 200;
        smallest = findSmallestValue(obsSteps, pitSteps, robotSteps);
        smallest = Math.min(edgeSteps, smallest);
        if (smallest == 200) return obstructions;
        if ((smallest == edgeSteps) && edgeSteps <= Config.fileUtils.getVisibility()) {
            setDirectionSteps(edgeSteps, direction);
            obstructions.put(direction, "EDGE");
        } else if (smallest == obsSteps) {
            setDirectionSteps(obsSteps, direction);
            obstructions.put(direction, "OBSTACLE");
        } else if (smallest == pitSteps) {
            setDirectionSteps(pitSteps, direction);
            obstructions.put(direction, "PITFALL");
        } else if (smallest == robotSteps) {
            setDirectionSteps(robotSteps, direction);
            obstructions.put(direction, "ROBOT");
        }
        return obstructions;
    }
}
