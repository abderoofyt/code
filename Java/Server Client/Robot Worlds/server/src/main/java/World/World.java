package World;

import Commands.RobotCommand;
import Config.fileUtils;
import Robot.*;
import Robot.Robot;
import ThreadManager.ClientHandler;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private final Position TOP_LEFT;
    private final Position BOTTOM_RIGHT;
    private List<Pitfall> pitfalls;
    private List<Obstacle> obstacles;
    private List<Robot> robots;
    private String status;
    private List<Mine> mines;
    private List<Color> robotColors;
    private ArrayList<ClientHandler> clients;

    public World() {
        fileUtils.addJsonToFile();
        TOP_LEFT = fileUtils.getBoundaryTopLeft();
        BOTTOM_RIGHT = fileUtils.getBoundaryBottomRight();
        robots = new ArrayList<>();
        pitfalls = generatePitfalls();
        obstacles = generateObstacles();
        mines = new ArrayList<>();
        robotColors = createRobotColorList();
    }

    public World(ArrayList<ClientHandler> clients) {
        fileUtils.addJsonToFile();
        TOP_LEFT = fileUtils.getBoundaryTopLeft();
        BOTTOM_RIGHT = fileUtils.getBoundaryBottomRight();
        robots = new ArrayList<>();
        pitfalls = generatePitfalls();
        obstacles = generateObstacles();
        mines = new ArrayList<>();
        robotColors = createRobotColorList();
        this.clients = clients;
    }

    /**
     * Retrieves the list of clients assigned to the World object.
     * @return
     */
    public ArrayList<ClientHandler> getClients() {
        return clients;
    }

    /**
     * Chooses random color from the list of Color objects given.
     *
     * @param colors   the list of Color objects.
     * @return the randomly chosen Color object from the list of Color objects.
     */
    private Color chooseColor(List<Color> colors) {
        Random random;
        int index;
        Color color;

        random = new Random();
        index = random.nextInt(colors.size()-1);
        color = colors.get(index);
        colors.remove(index);
        return color;
    }

    /**
     * Creates a list of four Color objects.
     *
     * @return the list of Color objects.
     */
    private List<Color> createRobotColorList() {
        List<Color> robotColors;

        robotColors = new ArrayList<>();
        robotColors.add(Color.green);
        robotColors.add(Color.magenta);
        robotColors.add(Color.cyan);
        robotColors.add(Color.yellow);
        return robotColors;
    }

    /**
     * Generates random number within the given boundaries.
     *
     * @return the randomly generated value.
     */
    private int generateRandom(int lowerBound, int upperBound) {
        Random rdm;
        int answer;
        int random;

        rdm = new Random();
        answer = 0;
        while (answer == 0) {
            random = rdm.nextInt(upperBound-lowerBound)+lowerBound;
            answer = (random <= upperBound && random >= lowerBound) ? random : 0;
        }
        return answer;
    }

    /**
     * Adds the given Mine object to the World object's list of Mine objects.
     *
     * @param mine   the Mine object to add to the list.
     */
    public void addMine(Mine mine) {
        mines.add(mine);
    }

    /**
     * Generates a random number, between zero and five, of Pitfall objects positioned in
     * random positions within the 'world'-simulation's boundaries. These Pitfall objects
     * are added to a list of Pitfall objects, and then returned.
     *
     * @return the list of, randomly positioned, Pitfall objects.
     */
    private List<Pitfall> generatePitfalls() {
        List<Pitfall> pitfalls;
        Random random;
        int numberOfPits;
        int noPitfalls;
        int x;
        int y;

        pitfalls = new ArrayList<>();
        random = new Random();
        numberOfPits = 5;
        noPitfalls = random.nextInt(numberOfPits);
        for (int i = 0; i < noPitfalls + 1; i++) {
            x = generateRandom(TOP_LEFT.getX()+5, BOTTOM_RIGHT.getX()-5);
            y = generateRandom(BOTTOM_RIGHT.getY()+5, TOP_LEFT.getY()-5);
            if (((x >= -4) && (x <= 4)) && ((y >= -4) && (y <= 4))) continue;
            pitfalls.add(new Pitfall(x, y));
        }
        return pitfalls;
    }

    /**
     * Generates a random number, between zero and five, of Obstacle objects positioned in
     * random positions within the 'world'-simulation's boundaries. These Obstacle objects
     * are added to a list of Obstacle objects, and then returned.
     *
     * @return the list of, randomly positioned, Obstacle objects.
     */
    private List<Obstacle> generateObstacles() {
        List<Obstacle> obstacles;
        Random random;
        int numberOfObstacles;
        int noObstacles;
        int x;
        int y;

        obstacles = new ArrayList<>();
        random = new Random();
        numberOfObstacles = 5;
        noObstacles= random.nextInt(numberOfObstacles);
        for (int i = 0; i < noObstacles + 1; i++) {
            x = generateRandom(TOP_LEFT.getX()+5, BOTTOM_RIGHT.getX()-5);
            y = generateRandom(BOTTOM_RIGHT.getY()+5, TOP_LEFT.getY()-5);
            if (((x >= -4) && (x <= 4)) && ((y >= -4) && (y <= 4))) continue;
            obstacles.add(new Obstacle(x, y));
        }
        return obstacles;
    }

    /**
     * Enum that indicates response for the updatePosition request.
     */
    public enum UpdateResponse {
        SUCCESS, //position was updated successfully
        FAILED_OUTSIDE_WORLD, //robot will go outside world limits if allowed, so it failed to update the position
        FAILED_OBSTRUCTED_PITFALL, //robot obstructed by at least one pitfall, thus cannot proceed and dies.
        FAILED_OBSTRUCTED_OBSTACLE, //robot obstructed by at least one obstacle, thus cannot proceed.
        FAILED_OBSTRUCTED_ROBOT, //robot obstructed by at least one other robot, thus cannot proceed.
        FAILED_OBSTRUCTED_MINE //robot stepped on mine
    }

    /**
     * Calls the execute function of the given RobotCommand object, with the given Robot
     * and World objects as parameters.
     *
     * @param target    the Robot object that needs to execute the given command.
     * @param command   the RobotCommand object that represents the given command.
     * @param world     the World object that represents the 'world'-simulation.
     * @return the JSONObject response with information on the command execution status.
     */
    public JSONObject handleCommand(Robot target, RobotCommand command, World world) {
        return command.execute(target, world);
    }

    /**
     * Checks if the new position is obstructed by a pitfall, in the 'world'-simulation.
     *
     * @param currentPosition   Position object of the robot.
     * @param newPosition       Position object after command performed.
     * @return true  : if the new position is obstructed by a pitfall.
     *         false : if the new position is not obstructed by a pitfall.
     */
    public boolean obstructedByPit(Position currentPosition, Position newPosition) {
        for (Pitfall pitfall: pitfalls) {
            if (pitfall.fallIn(newPosition) || pitfall.inPitfall(currentPosition, newPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the new position is obstructed by an obstacle, in the 'world'-simulation.
     *
     * @param currentPosition   Position object of the robot.
     * @param newPosition       Position object after command performed.
     * @return true  : if the new position is obstructed by an obstacle.
     *         false : if the new position is not obstructed by an obstacle.
     */
    public boolean obstructedByObstacle(Position currentPosition, Position newPosition) {
        for (Obstacle obstacle: obstacles) {
            if (obstacle.blockPosition(newPosition) || obstacle.blockPath(currentPosition, newPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the new position is obstructed by an mine, in the 'world'-simulation.
     *
     * @param currentPosition   Position object of the robot.
     * @param newPosition       Position object after command performed.
     * @return true  : if the new position is obstructed by a mine.
     *         false : if the new position is not obstructed by a mine.
     */
    public boolean obstructedMine(Position currentPosition, Position newPosition){
        for (Mine mine: mines){
            if (mine.mineInPosition(newPosition) || mine.mineInPath(currentPosition, newPosition)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the new position is obstructed by another robot, in the 'world'-simulation.
     *
     * @param newPosition   new Position object of the given Robot object.
     * @param robot         the Robot object that is moving to the given, new Position.
     * @return true  : if the new position is obstructed by another robot.
     *         false : if the new position is not obstructed by another robot.
     */
    public boolean obstructedByRobot(Position newPosition, Robot robot) {
        Position checkPosition;

        for (Robot checkRobot: robots) {
            if (robot.getType().equals(checkRobot.getType())) continue;
            checkPosition = checkRobot.getPosition();
            if (robot.robotBlocksPosition(checkPosition) || (robot.robotBlocksPath(newPosition, checkPosition))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given Position object's x and y coordinates are within the
     * boundary positions of the world.
     *
     * @param position   Position object to confirm whether it is within the World
     *                   object's boundary.
     * @return true  : if the position's x and y coordinates is within the boundary.
     *         false : if the position's x and y coordinates are not within the boundary.
     */
    public boolean isNewPositionAllowed(Position position) {
        return position.isIn(TOP_LEFT,BOTTOM_RIGHT);
    }

    /**
     * Determines which UpdateResponse eNum should be returned. The UpdateResponse
     * is determined by confirming if there are any obstructions present in the
     * path to, or position of, the new Position object's x and y coordinates.
     *
     * @param newPosition   the Robot object's new Position object.
     * @param target        the Robot object that has been asked to move to the
     *                      new position.
     * @return SUCCESS                    : if the new position is allowed.
     *         FAILED_OBSTRUCTED_PITFALL  : if the new position is obstructed by a pitfall.
     *         FAILED_OBSTRUCTED_OBSTACLE : if the new position is obstructed by an obstacle.
     *         FAILED_OBSTRUCTED_ROBOT    : if the new position is obstructed by a robot.
     *         FAILED_OUTSIDE_WORLD       : if the new position is not within the world's
     *                                      boundaries.
     */
    public UpdateResponse getUpdateResponse(Position newPosition, Robot target) {
        if (obstructedByPit(target.getPosition(), newPosition)) {
            return UpdateResponse.FAILED_OBSTRUCTED_PITFALL;
        } else if (obstructedByObstacle(target.getPosition(), newPosition)) {
            return UpdateResponse.FAILED_OBSTRUCTED_OBSTACLE;
        } else if (obstructedByRobot(newPosition, target)) {
            return UpdateResponse.FAILED_OBSTRUCTED_ROBOT;
        } else if (obstructedMine(target.getPosition(), newPosition)) {
            return UpdateResponse.FAILED_OBSTRUCTED_MINE;
        } else if (isNewPositionAllowed(newPosition)) {
            target.setPosition(newPosition);
            return UpdateResponse.SUCCESS;
        }
        return UpdateResponse.FAILED_OUTSIDE_WORLD;
    }

    /**
     * Updates the robot's current position if the new position is a valid position to move to.
     *
     * @param target   the Robot object in question.
     * @param nrSteps  the amount of steps the Robot object should move.
     * @return SUCCESS                    : if the new position is allowed.
     *         FAILED_OBSTRUCTED_PITFALL  : if the new position is obstructed by a pitfall.
     *         FAILED_OBSTRUCTED_OBSTACLE : if the new position is obstructed by an obstacle.
     *         FAILED_OBSTRUCTED_ROBOT    : if the new position is obstructed by a robot.
     *         FAILED_OUTSIDE_WORLD       : if the new position is not within the world's
     *                                      boundaries.
     */
    public UpdateResponse updatePosition(Robot target, int nrSteps) {
        Robot.Direction currentDirection;
        Position currentPosition;
        Position newPosition;
        int newX;
        int newY;

        currentPosition = target.getPosition();
        currentDirection = target.getDirection();
        newX = currentPosition.getX();
        newY = currentPosition.getY();
        switch (currentDirection) {
            case NORTH:
                newY = newY + nrSteps;
                break;
            case EAST:
                newX = newX + nrSteps;
                break;
            case SOUTH:
                newY = newY - nrSteps;
                break;
            case WEST:
                newX = newX - nrSteps;
                break;
        }
        newPosition = new Position(newX, newY);
        return getUpdateResponse(newPosition, target);
    }

    /**
     * Adds the given Pitfall object to the World object's list of Pitfall objects.
     *
     * @param pitfall   the Pitfall object to add to the World object's list of Pitfall
     *                  objects.
     */
    public void addPitfallToList(Pitfall pitfall) {
        pitfalls.add(pitfall);
    }

    /**
     * Checks whether there is another robot, with the exact same Robot type, or name, as
     * the given Robot object, in the World object's list of Robot objects.
     *
     * @param target   the Robot object to compare to the list of Robot objects.
     * @return true  : if the list of Robot objects contains a Robot object with
     *                 the same robot type, or name.
     *         false : if the list of Robot objects does not contain a Robot object
     *                 with the same robot type, or name.
     */
    public boolean isRobotTypeInWorld(Robot target) {
        for (Robot robot : robots) {
            if (target.getType().equals(robot.getType())) {
                return true;
            }
            if (target.getName().equals(robot.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the Robot object, from the World object's list of Robot objects,
     * with the exact same name as the given String value.
     *
     * @param name   the name of the Robot object to retrieve from the list of Robot
     *               objects.
     * @return the Robot object with the given String value as a name.
     */
    public Robot getRobotWithName(String name) {
        for (Robot robot : robots) {
            if (robot.getName().equals(name)) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Adds the given Robot object to the World object's list of Robot objects. Only
     * if the Robot object's robot type does not already exist in the Robot object list.
     *
     * @param target   the Robot object to add to the World object's list of Robot objects.
     * @return true  : if the robot was added to the Robot object list.
     *         false : if the robot was not added to the Robot object list.
     */
    public boolean addRobot(Robot target) {
        if (isRobotTypeInWorld(target)) {
            return false;
        }
        target.setColor(chooseColor(robotColors));
        robots.add(target);
        return true;
    }

    /**
     * Removes the mine, that is in the path, between the old position and new
     * position, or in the position of the old position, from the World object's
     * list of Mine objects.
     * @param oldPos   the Position object with the x and y coordinates of the
     *                 robot's current Position object.
     * @param pos      the Position object with the x and y coordinates of the
     *                 robot's new position it wants to move to.
     * @return true  : if the Mine object was found and removed from the list of
     *                 Mine objects.
     *         false : if the Mine object was not found and removed from the list
     *                 of Mine objects.
     */
    public boolean removeMineInPos(Position oldPos, Position pos) {
        int i;
        int index;

        i = 0;
        index = -1;
        for (Mine checkMine : mines) {
            if (getMineInWay(oldPos, pos).equals(checkMine)) {
                index = i;
                break;
            }
            i++;
        }
        if (index == -1) return false;
        mines.remove(i);
        return true;
    }

    /**
     * Determines whether there is a mine that obstructs the path between the
     * old position and new position, or the position of the new position. If
     * there is a Mine object found that obstructs the robot, it is returned.
     *
     * @param oldPos   the Position object with the x and y coordinates of the
     *                 robot's current Position object.
     * @param pos      the Position object with the x and y coordinates of the
     *                 new position that the robot wants to move to.
     * @return Mine object : if a Mine object, that obstructs the robot, was found.
     *         null        : if there os no Mine object that obstructs the robot.
     */
    private Mine getMineInWay(Position oldPos, Position pos) {
        for (Mine mine: mines){
            if (mine.mineInPosition(oldPos) || mine.mineInPath(oldPos, pos)){
                return mine;
            }
        }
        return null;
    }

    /**
     * Removes the given Robot object from the World object's list of
     * Robots. Only if the Robot object already exist in the Robot list.
     *
     * @param target   the Robot object to remove from the list.
     * @return true  : if the robot was removed from the Robot list.
     *         false : if the robot was not found in the Robot list, and
     *                 thus could not be removed.
     */
    public boolean removeRobot(Robot target) {
        try {
            robots.remove(target);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Retrieves the Robot object, with the same robot type as the given Robot object,
     * from the World object's list of Robot objects.
     *
     * @param target   Robot object used to compare robot types with.
     * @return Robot object  : if an Robot object, with identical Robot Class, was found.
     *         null          : if no Robot object, with identical Robot Class, was found.
     */
    public Robot getRobot(Robot target) {
        for (Robot robot : robots) {
            if (robot.getClass().equals(target.getClass())) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Retrieves the World object's assigned list of Pitfall objects.
     *
     * @return list of Pitfall objects in the 'world'-simulation.
     */
    public List<Pitfall> getPitfalls() {
        return pitfalls;
    }

    /**
     * Retrieves the World object's assigned list of Obstacle objects.
     *
     * @return list of Obstacle objects in the 'world'-simulation.
     */
    public List<Obstacle> getObstacles() {
        return obstacles;
    }

    /**
     * Retrieves the World object's assigned list of Mine objects.
     *
     * @return list of Mine objects in the 'world'-simulation.
     */
    public List<Mine> getMines(){
        return mines;
    }

    /**
     * Sets the World object's assigned list of Pitfall objects
     * equal to the given list of Pitfall objects.
     *
     * @param pitfalls   the new list of Pitfall objects to assign
     *                   to the World.
     */
    public void setPitfalls(List<Pitfall> pitfalls) {
        this.pitfalls = pitfalls;
    }

    /**
     * Retrieves the World object's assigned list of Robot objects.
     *
     * @return list of Robot objects in the 'world'-simulation.
     */
    public List<Robot> getAllRobots() {
        return this.robots;
    }

    /**
     * Sets the World object's status equal to the given String value.
     *
     * @param status   the new status of the World object.
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the World object's assigned status.
     *
     * @return the status of the World object.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the World object's list of Obstacle objects equal to the given
     * list of Obstacle objects.
     *
     * @param obstacles   new list of Obstacle objects to assign to the World
     *                    object.
     */
    public void setObstacles(List<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    /**
     * Adds an Obstacle object to the World object's list of Obstacle objects.
     *
     * @param obstacle   the Obstacle object to add to the World object's list
     *                   of Obstacle objects.
     */
    public void addObstacleToList(Obstacle obstacle) {
        obstacles.add(obstacle);
    }

}
