package Commands.RobotCommands;

import Commands.CommandUtils;
import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.*;
import ThreadManager.ClientHandler;
import World.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FireCommand extends RobotCommand {
    private static final int maxDistance = 5;
    private List<ClientHandler> clients;

    public FireCommand() {
        super("fire");
    }

    /**
     * Executes the fire command.
     *
     * @param shooter   the Robot object that requested the fire command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the fire command.
     */
    @Override
    public JSONObject execute(Robot shooter, World world) {
        int shotsLeft;
        int currentDistance;
        boolean executed;
        Robot shotRobot;
        JSONObject shotRobotResponse;

        setClient(world.getClients());
        shotsLeft = shooter.getShotsLeft();
        currentDistance = shooter.getShotDistance();
        executed = false;
        shotRobot = null;
        shotRobotResponse = new JSONObject();
        if (shotsLeft < 1) {
            return createShooterRobotResponse(shooter, null, false, shotRobotResponse);
        } else if (currentDistance > 0) {
            shotRobot = makeShot(shooter, world, currentDistance);
            shotRobot = updateShotRobot(shotRobot);
            shooter = updateShooterRobot(shooter);
        }
        if (shotRobot != null) {
            executed = true;
            shotRobotResponse = sendResponseToShotRobot(shotRobot, world);
            if (shotRobotResponse == null) {
                executed = false;
                shotRobotResponse = new JSONObject();
                shotRobot = null;
            } else if ((shotRobot.getShieldStrength() < 0)) {
                world.removeRobot(shotRobot);
            }
        }
        return createShooterRobotResponse(shooter, shotRobot, executed, shotRobotResponse);
    }

    private void setClient(ArrayList<ClientHandler> clients) {
        this.clients = clients;
    }

    /**
     * Creates a JSONObject response message for the shot robot.
     *
     * @param shotRobot   the Robot object that was shot.
     * @return the JSONObject response message for the shot robot.
     */
    private JSONObject createShotRobotResponse(Robot shotRobot) {
        Encode encoder;
        JSONObject response;
        String status;
        JSONObject state;

        encoder = new Encode(true, CommandUtils.createRobotCommand("state", ""));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        status = (shotRobot.getShieldStrength() < 0) ? "DEAD" : "NORMAL";
        state = encoder.createOtherCommandState(shotRobot, status);
        response.put("state", state);
        return response;
    }

    /**
     * Sends the given response message to the shot robot's client program.
     *
     * @param robot      the Robot object that got shot.
     * @param response   the JSONObject response message to send.
     * @param world      the World object that represents a 'world'-simulation.
     * @return true  : if the response was sent.
     *         false : if the response was not sent.
     */
    private boolean sendResponseToClient(Robot robot, JSONObject response, World world) {
        for (ClientHandler client: clients) {
            if (client.getRobot().equals(robot)) {
                client.sendToClient(response);
                return true;
            }
        }
        world.removeRobot(robot);
        return false;
    }

    /**
     * Sends the correct response to the shot robot's client program.
     *
     * @param shotRobot   the Robot object that was shot.
     * @param world       the World object that represents the 'world'-simulation.
     * @return the response that was sent to the shot robot's client program.
     */
    private JSONObject sendResponseToShotRobot(Robot shotRobot, World world) {
        JSONObject response;

        response = createShotRobotResponse(shotRobot);
        if (response != null) {
            if (!sendResponseToClient(shotRobot, response, world)) {
                return null;
            }
        }
        return response;
    }

    /**
     * Determines the final destination of the bullet, when the bullet is obstructed by
     * an obstacle.
     *
     * @param obstacle   the Obstacle object to check.
     * @param shooter    the Robot object of the shooter.
     * @return the Position of the obstructed bullet.
     */
    private Position getObstructedBulletDestination(Obstacle obstacle, Robot shooter) {
        int bulletX;
        int bulletY;

        bulletX = shooter.getPosition().getX();
        bulletY = shooter.getPosition().getY();
        switch (shooter.getDirection()) {
            case NORTH:
                bulletY = obstacle.getBottomLeftY();
                break;
            case EAST:
                bulletX = obstacle.getBottomLeftX();
                break;
            case SOUTH:
                bulletY = obstacle.getTopRightY();
                break;
            case WEST:
                bulletX = obstacle.getTopRightX();
                break;
        }
        return new Position(bulletX, bulletY);
    }

    /**
     * Determines the bullet's final destination.
     *
     * @param currentShotDistance   the shot distance of the shooter robot.
     * @param obstacles             the list of obstacles in the world.
     * @param shooter               the Robot object that represents the shooter.
     * @return the final position of the bullet.
     */
    private Position getBulletFinalPosition(int currentShotDistance, List<Obstacle> obstacles, Robot shooter) {
        int newX;
        int newY;
        Position bulletPosition;

        newX = shooter.getPosition().getX();
        newY = shooter.getPosition().getY();
        switch (shooter.getDirection()) {
            case NORTH:
                newY = newY + currentShotDistance;
                break;
            case EAST:
                newX = newX + currentShotDistance;
                break;
            case SOUTH:
                newY = newY - currentShotDistance;
                break;
            case WEST:
                newX = newX - currentShotDistance;
                break;
        }
        bulletPosition = new Position(newX, newY);
        for (Obstacle obstacle: obstacles) {
            if (obstacle.blockPath(shooter.getPosition(), bulletPosition)) {
                return getObstructedBulletDestination(obstacle, shooter);
            }
        }
        return bulletPosition;
    }

    /**
     * Execute shot.
     *
     * @param shooter           the Robot object that represents the shooter.
     * @param world             the World object that represents the 'world'-simulation.
     * @param currentDistance   the shooter robot's shot distance.
     * @return the Robot object : of the shot robot.
     *         null             : if no robot was shot.
     */
    private Robot makeShot(Robot shooter, World world, int currentDistance) {
        List<Robot> robots;
        Position bulletPosition;

        robots = world.getAllRobots();
        bulletPosition = getBulletFinalPosition(currentDistance, world.getObstacles(), shooter);
        for (Robot robot : robots) {
            if (robot.equals(shooter)) continue;
            if (shooter.robotBlocksPath(bulletPosition, robot.getPosition())) {
                return robot;
            }
        }
        return null;
    }

    /**
     * Updates shot robot's state.
     *
     * @param shotRobot   the Robot object of the shot robot.
     * @return the shot robot.
     */
    public Robot updateShotRobot(Robot shotRobot) {
        if (shotRobot != null) {
            shotRobot.setShieldStrength(shotRobot.getShieldStrength()-1);
        }
        return shotRobot;
    }

    /**
     * Updates the shooter robot's state.
     *
     * @param shooter   the Robot object of the shooter robot.
     * @return the shooter robot.
     */
    public Robot updateShooterRobot(Robot shooter) {
        int shotsLeft;
        int shotDistance;

        shotsLeft = shooter.getShotsLeft();
        shotDistance = shooter.getShotDistance();
        if (shooter.getShotsLeft() > 0 && shotDistance > 0) {
            shooter.setShotsLeft(shotsLeft-1);
            shooter.setShotDistance(shotDistance-1);
        }
        return shooter;
    }

    /**
     * Determine the amount of steps to the shot robot.
     *
     * @param shooter     the Robot object that represents the shooter robot.
     * @param shotRobot   the Robot object that represents the shot robot.
     * @return the amount of steps.
     */
    private int getShotRobotDistance(Robot shooter, Robot shotRobot) {
        Position shooterPos;
        Position shotRobotPos;

        shooterPos = shooter.getPosition();
        shotRobotPos = shotRobot.getPosition();
        switch (shooter.getDirection()) {
            case NORTH:
                return (shotRobotPos.getY() - shooterPos.getY());
            case EAST:
                return (shotRobotPos.getX() - shooterPos.getX());
            case SOUTH:
                return (Math.abs(shotRobotPos.getY()) + shooterPos.getY());
        }
        //east
        return (Math.abs(shotRobotPos.getX()) + shooterPos.getX());
    }

    /**
     * Creates the response to send to the shooter robot's client program.
     *
     * @param shooter             the Robot object that represents the shooter robot.
     * @param shotRobot           the Robot object that represents the shot robot.
     * @param executed            true if the shot distance and amount of shots was greater than zero.
     * @param shotRobotResponse   the JSONObject response object sent to the shot robot.
     * @return the response message to send to the shooter robot's client program.
     */
    private JSONObject createShooterRobotResponse(Robot shooter, Robot shotRobot, boolean executed, JSONObject shotRobotResponse) {
        Encode encoder;
        JSONObject response;
        String robotName;
        int steps;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        robotName = (shotRobot == null) ? "" : shotRobot.getName();
        steps = (shotRobot == null) ? -1 : getShotRobotDistance(shooter, shotRobot);
        data = encoder.createFireCommandShooterData(steps, robotName, executed, shotRobotResponse);
        state = encoder.createFireCommandState(shooter.getShotsLeft());
        response.put("data", data);
        response.put("state", state);
        return response;
    }

    /**
     * Retrieves the robot's maximum shot distance.
     *
     * @return the maximum shot distance.
     */
    public static int getMaxShotDistance() {
        return maxDistance;
    }
}
