package Commands.RobotCommands.MineCommand;

import Commands.RobotCommand;
import JsonUtils.Encode;
import Robot.Robot;
import World.Mine;
import World.World.UpdateResponse;
import Robot.Position;
import World.World;
import org.json.simple.JSONObject;

public class MineCommand extends RobotCommand {

    public MineCommand(){super("mine");}

    /**
     * Takes into consideration the robots current Direction,
     * then places the mine at the appropriate position.
     *
     * @param target   Robots position.
     * @param world    used for the setMine param to access the mines list.
     * @return the status and message that will be sent to the client.
     */
    @Override
    public JSONObject execute(Robot target, World world) {
        int mineBottomLeftX;
        int mineBottomLeftY;
        Mine mine;
        MineActivator activator;
        JSONObject response;

        mineBottomLeftX = target.getPosition().getX();
        mineBottomLeftY = target.getPosition().getY();
        if (target.getDirection() == Robot.Direction.NORTH){
            mineBottomLeftY = mineBottomLeftY - 4;
        } else if (target.getDirection() == Robot.Direction.SOUTH){
            mineBottomLeftY = mineBottomLeftY + 4;
        } else if (target.getDirection() == Robot.Direction.EAST){
            mineBottomLeftX = mineBottomLeftX - 4;
            mineBottomLeftY = mineBottomLeftY + 4;
        } else {
            mineBottomLeftX = mineBottomLeftX + 4;
        }
        mine = createMine(new Position(mineBottomLeftX , mineBottomLeftY), world);
        activator = new MineActivator(target, mine);
        response = createMineResponse(target, moveRobotForwardOneStep(target, world));
        target.setStatus(Robot.Status.SETMINE);
        activator.activateMine();
        target.setStatus(Robot.Status.NORMAL);
        return response;
    }

    /**
     * Creates Mine object behind the Robot object's position.
     * @param position the Mine object's bottom left Position object.
     * @param world the World object the Robot object exists in.
     * @return the Mine object created in the world.
     */
    private Mine createMine(Position position, World world){
        int x;
        int y;
        Mine mine;

        x = position.getX();
        y = position.getY();
        mine = new Mine(x, y);
        world.addMine(mine);
        return mine;
    }

    /**
     * Moves robot forward by one step.
     *
     * @param target   the Robot object that requested for the mine command
     *                 to be executed.
     * @param world    the World object that represents the 'world'-simulation.
     * @return the message to add to the response message.
     */
    private String moveRobotForwardOneStep(Robot target, World world) {
        UpdateResponse response;

        response = world.updatePosition(target, 1);
        switch (response) {
            case FAILED_OBSTRUCTED_PITFALL:
            case FAILED_OBSTRUCTED_MINE:
            case FAILED_OBSTRUCTED_OBSTACLE:
            case FAILED_OBSTRUCTED_ROBOT:
            case FAILED_OUTSIDE_WORLD:
                return "DEAD";
        }
        //SUCCESS
        return "SETMINE";
    }

    /**
     * Status and message that will be sent to the client server through JSON files.
     * @param target setStatus and setMessage.
     * @return the response that will be sent to the encoder.
     */
    private JSONObject createMineResponse(Robot target, String status){
        Encode encoder;
        JSONObject response;
        JSONObject data;
        JSONObject state;

        encoder = new Encode(true, (this));
        response = new JSONObject();
        data = encoder.otherCommandData("Done");
        state = encoder.createOtherCommandState(target, status);
        response.put("data", data);
        response.put("state", state);
        return response;
    }

}