package Commands.WorldCommands;

import Commands.CommandUtils;
import Commands.WorldCommand;
import Display.TerminalDisplay;
import JsonUtils.Encode;
import Robot.Robot;
import ThreadManager.ClientHandler;
import World.World;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class PurgeCommand extends WorldCommand {

    private ArrayList<ClientHandler> clients;

    public PurgeCommand(String arguments, ArrayList<ClientHandler> clients) {
        super("purge", arguments);
        this.clients = clients;
    }

    /**
     * Executes the purge command.
     *
     * @param world   the World object that represents the 'world'-simulation.
     * @return true
     */
    @Override
    public boolean execute(World world) {
        JSONObject response;
        TerminalDisplay display;
        Robot robot;

        robot = world.getRobotWithName(getArgument());
        display = new TerminalDisplay(world);
        if (robot == null) {
            display.displayPurge(getArgument(), false);
        } else {
            robot.setStatus(Robot.Status.DEAD);
            response = createPurgeResponse(robot);
            sendResponseToClient(robot, response);
            world.getAllRobots().remove(robot);
            display.displayPurge(getArgument(), true);
        }
        return true;
    }

    /**
     * Sends the given response message to the ClientHandler's client program
     * with the assigned Robot object.
     *
     * @param robot      the Robot object assigned to the ClientHandler object.
     * @param response   the response JSONObject message to send to the client
     *                   program.
     */
    private void sendResponseToClient(Robot robot, JSONObject response) {
        for (ClientHandler client: clients) {
            if (client.getRobot().equals(robot)) {
                client.sendToClient(response);
                return;
            }
        }
    }

    /**
     * Creates JSONObject response to send to the client program of the robot that
     * was purged from the 'world'-simulation.
     *
     * @param target   the Robot object that was purged from the world.
     * @return the JSONObject response to send to the client program.
     */
    private JSONObject createPurgeResponse(Robot target){
        Encode encoder;
        JSONObject response;
        JSONObject state;

        encoder = new Encode(true, CommandUtils.createRobotCommand("state", ""));
        response = new JSONObject();
        state = encoder.createOtherCommandState(target, "DEAD");
        response.put("state", state);
        return response;
    }

}
