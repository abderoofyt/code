package Commands.RobotCommands;

import Commands.CommandUtils;
import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import Robot.Position;
import World.World;
import World.Pitfall;
import World.Obstacle;
import World.Mine;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LaunchCommand extends RobotCommand {


    private List<Position> startList;
    private Random random = new Random();

    public LaunchCommand() {
        super("launch");
    }

    /**
     * Executes the launch command.
     *
     * @param target   the Robot object to be launched into the world.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the JSONObject response message.
     */
   @Override
    public JSONObject execute(Robot target, World world) {
       startList = new ArrayList<>();
       startingList();
       if (world.getAllRobots().size() > 4) {
           //too many robots in world
           return CommandUtils.createRobotCommand("errorLocation", "").execute(target, world);
       } else if (world.isRobotTypeInWorld(target)) {
           //robot of same type already in world
           return CommandUtils.createRobotCommand("errorType", "").execute(target, world);
       }
       //add robot
       target.setPosition(startCheck(target, world));
       world.addRobot(target);
       return createLaunchResponse(target);
   }

    /**
     * Creates the launch response JSONObject that corresponds with
     * the robot's information.
     *
     * @param robot   the Robot object that was launched into the world.
     * @return the JSONObject response message.
     */
    private JSONObject createLaunchResponse(Robot robot) {
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        data = encoder.createLaunchCommandData(robot);
        state = encoder.createOtherCommandState(robot, "NORMAL");
        response.put("data", data);
        response.put("state", state);
        return response;
    }

    /**
     * Adds the possible starting positions, for each launched robot,
     * into a list.
     */
    public void startingList(){
        startList.add(new Position(170, 170));
        startList.add(new Position(-170, 170));
        startList.add(new Position(170, -170));
        startList.add(new Position(-170, -170));
    }

    /**
     * Determines the random position the launched robot should start.
     *
     * @param target   the Robot object that was launched into the world.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the launched robot's allocated position.
     */
    public Position startCheck(Robot target, World world){
        Position newPosition;

        newPosition = startList.get(random.nextInt(startList.size()-1));
        startList.remove(newPosition);
        while (launchCheck(newPosition, target, world)){
            newPosition.setX(newPosition.getX() + 5);
            newPosition.setY(newPosition.getY() - 5);
        }
        return newPosition;
    }

    /**
     * Determines whether the given position is already occupied.
     *
     * @param newPosition   the position to check.
     * @param robot         the Robot object that was launched into the world.
     * @param world         the World object that represents the 'world'-simulation.
     * @return true  : if the position is occupied.
     *         false : if the position is not occupied.
     */
    public boolean launchCheck(Position newPosition, Robot robot, World world){
        if (pitCheck(newPosition, world)){
            return true;
        } else if (obstacleCheck(newPosition, world)){
            return true;
        } else if (mineCheck(newPosition, world)){
            return true;
        }
        else if (robotCheck(newPosition, robot, world)){
            return true;
        }
        return false;
    }

    /**
     * Determines whether the given position is occupied by a pitfall.
     *
     * @param newPosition   the position to check.
     * @param world         the World object that represents the 'world'-simulation.
     * @return true  : if the position is occupied by a pitfall.
     *         false : if the position is not occupied by a pitfall.
     */
    public boolean pitCheck(Position newPosition, World world) {
        for (Pitfall pitfall: world.getPitfalls()) {
            if (pitfall.fallIn(newPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the given position is occupied by an obstacle.
     *
     * @param newPosition   the position to check.
     * @param world         the World object that represents the 'world'-simulation.
     * @return true  : if the position is occupied by an obstacle.
     *         false : if the position is not occupied by an obstacle.
     */
    public boolean obstacleCheck(Position newPosition, World world) {
        for (Obstacle obstacle: world.getObstacles()) {
            if (obstacle.blockPosition(newPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the given position is occupied by a mine.
     *
     * @param newPosition   the position to check.
     * @param world         the World object that represents the 'world'-simulation.
     * @return true  : if the position is occupied by a mine.
     *         false : if the position is not occupied by a mine.
     */
    public boolean mineCheck(Position newPosition, World world){
        for (Mine mine: world.getMines()){
            if (mine.mineInPosition(newPosition)){
                return true;
            }
        }
        return false;
    }

    /**
     * Determines whether the given position is occupied by a robot.
     *
     * @param newPosition   the position to check.
     * @param world         the World object that represents the 'world'-simulation.
     * @return true  : if the position is occupied by a robot.
     *         false : if the position is not occupied by a robot.
     */
    public boolean robotCheck(Position newPosition, Robot robot, World world) {
        int x;
        int y;
        int checkX;
        int checkY;

        x = newPosition.getX();
        y = newPosition.getY();
        for (Robot checkRobot : world.getAllRobots()) {
            if (checkRobot.equals(robot)) continue;
            checkX = checkRobot.getPosition().getX();
            checkY = checkRobot.getPosition().getY();
            if (((x == checkX) && (y == checkY))) return true;
        }
        return false;
    }


}
