package Commands.RobotCommands;

import Commands.CommandUtils;
import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import Robot.Position;
import Robot.Robot.Direction;
import World.World;
import org.json.simple.JSONObject;

public class ForwardCommand extends RobotCommand {
    private Position oldPos;

    public ForwardCommand(String argument) {
        super("forward", argument);
    }

    /**
     * Executes the forward command.
     *
     * @param target   the Robot object that requested the forward command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the response JSONObject specific to the forward command.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        int nrSteps;
        World.UpdateResponse response;
        String argument = getArgument();


        if (isArgumentInt(argument) && !(argument.isBlank())) {
            nrSteps = Integer.parseInt(getArgument());
            oldPos = target.getPosition();
            response = world.updatePosition(target, nrSteps);
            return getCorrectResponse(target, response, world, nrSteps);
        }
        return CommandUtils.createRobotCommand("command", "").execute(target, world);
    }

    /**
     * Determines the new position of the robot, after the forward command is executed.
     *
     * @param currentDirection   the robot's current direction.
     * @param nrSteps            the amount of steps the robot need to move forward.
     * @return the position after the forward command was executed.
     */
    private Position getNewPosition(Direction currentDirection, int nrSteps) {
        int newX;
        int newY;

        newX = oldPos.getX();
        newY = oldPos.getY();
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
        return new Position(newX, newY);
    }

    /**
     * Executes an explosion when the robot steps into a mine.
     *
     * @param target   the Robot object that requested the forward command.
     * @param world    the World object that represents the 'world'-simulation.
     * @param steps    the amount of steps the robot was requested to move forward.
     * @return the JSONObject state response, after the explosion.
     */
    private JSONObject executeExplosion(Robot target, World world, int steps) {
        int updatedShield;

        world.removeMineInPos(oldPos, getNewPosition(target.getDirection(), steps));
        updatedShield = target.getShieldStrength() - 3;
        if (updatedShield < 0) {
            //DEAD
            return createMovementResponse(target, "DEAD", "", true);
        }
        //OBSTRUCTED
        target.setShieldStrength(updatedShield);
        return createMovementResponse(target, "NORMAL", "Obstructed", true);
    }

    /**
     * Removes the robot from the 'world'-simulation.
     *
     * @param target   the Robot object that requested the forward command to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the JSONObject state response to send to the client.
     */
    private JSONObject killRobot(Robot target, World world) {
        JSONObject response = createMovementResponse(target, "DEAD", "Fell", false);
        world.removeRobot(target);
        return response;
    }

    /**
     * Retrieves the JSONObject response correlating with the given update response.
     *
     * @param target           the Robot object that requested the forward command to be executed.
     * @param updateResponse   the update response after the command was executed.
     * @param world            the World object that represents the 'world'-simulation.
     * @param steps            the amount of steps the robot had to move.
     * @return the corresponding JSONObject response.
     */
    private JSONObject getCorrectResponse(Robot target, World.UpdateResponse updateResponse, World world, int steps) {
        switch (updateResponse) {
            case FAILED_OBSTRUCTED_PITFALL:
                return killRobot(target, world);
            case FAILED_OBSTRUCTED_MINE:
                return executeExplosion(target, world, steps);
            case FAILED_OBSTRUCTED_OBSTACLE:
            case FAILED_OUTSIDE_WORLD:
            case FAILED_OBSTRUCTED_ROBOT:
                return createMovementResponse(target, "NORMAL", "Obstructed", false);
        }
        //SUCCESS
        return createMovementResponse(target, "NORMAL", "Done", false);
    }

    /**
     * Creates the specific forward command JSONObject response, to send to the
     * client program.
     *
     * @param robot       the Robot object that requested the forward command to be
     *                    executed.
     * @param status      the status to add to the response message.
     * @param message     the message to add to the response message.
     * @param foundMine   true if the robot stepped into a mine.
     * @return the response JSONObject specific to the forward command.
     */
    private JSONObject createMovementResponse(Robot robot, String status, String message, boolean foundMine) {
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        response.put("result", encoder.getResult());
        if (foundMine) {
            state = encoder.createOtherCommandState(robot, status);
        } else {
            data = encoder.otherCommandData(message);
            state = encoder.createOtherCommandState(robot, status);
            response.put("data", data);
        }
        response.put("state", state);
        return response;
    }

    /**
     * Determines whether the given String value can be converted into
     * an int value.
     *
     * @param argument   the String value to check.
     * @return true  : if the String can be converted into an int.
     *         false : if the String cannot be converted into an int.
     */
    public boolean isArgumentInt(String argument)
    {
        try
        {
            Integer.parseInt(argument);
            return true;
        } catch (NumberFormatException ex)
        {
            return false;
        }
    }
}
