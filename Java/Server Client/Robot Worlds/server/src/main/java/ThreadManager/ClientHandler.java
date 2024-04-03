package ThreadManager;

import Commands.CommandUtils;
import Commands.RobotCommand;
import JsonUtils.Decode;
import Robot.Robot;
import World.World;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket socket;
    private ArrayList<ClientHandler> clients;
    private World world;
    private PrintWriter writer;
    private Robot robot;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> clients, World world) {
        this.socket = socket;
        this.clients = clients;
        this.world = world;
        this.writer = generatePrintWriter();
    }

    /**
     * Converts the given String into a JSONObject object, if possible.
     * If the String cannot be converted into a JSONObject object, null
     * is returned.
     *
     * @param request   the String value to convert into a JSONObject object.
     * @return the JSONObject object : if the String could be converted.
     *         null                  : if the String could not be converted.
     */
    private JSONObject stringToJson(String request) {
        JSONParser parser;
        try {
            parser = new JSONParser();
            return (JSONObject) parser.parse(request);
        } catch (Exception ParseException) {
            return null;
        }
    }

    /**
     * Retrieves the request, received from the client program, and converts it
     * into a JSONObject object. This JSONObject is returned.
     * @param reader   the BufferedReader object used to communicate with the
     *                 client program.
     * @return the JSONObject object : if the request message could be converted
     *                                 into a JSONObject object.
     *         null                  : if the request message could not be converted
     *                                 into a JSONObject object.
     */
    private JSONObject getRequest(BufferedReader reader) {
        String request;

        try {
            request = reader.readLine();
            return stringToJson(request);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieves the response, to send to the client program.
     *
     * @param request   the request message, received from the
     *                  client program.
     * @param world     the World object that represents the
     *                  'world'-simulation.
     * @return the JSONObject object : response message to send to the client program.
     *         null                  : if the given request message was null.
     */
    private JSONObject getResponse(JSONObject request, World world) {
        Robot robot;
        Decode decoder;
        JSONObject response;
        RobotCommand command;

        if (!(request==null)) {
            decoder = new Decode(world, request);
            robot = decoder.getRobot();
            if( robot == null && !(decoder.getCommand().equals("launch")) ) {
                return null;
            } else {
                if(decoder.getCommand().equals("launch") ) {
                    String[] args = decoder.getArguments().split(" ");
                    robot = new Robot(decoder.getName(request), args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                    setRobot(robot);
                }
                command = CommandUtils.createRobotCommand(decoder.getCommand(), decoder.getArguments());
                response = world.handleCommand(robot, command, world);
            }
        } else {
            response = null;
        }
        return response;
    }

    /**
     * Sends the given response message to the ClientHandler's assigned client program.
     *
     * @param response   the response message to send to the client program.
     */
    public void sendToClient(JSONObject response) {
        writer.println(response.toString());
    }

    /**
     * Creates a PrintWriter object and returns it.
     *
     * @return the PrintWriter object that was created.
     */
    private PrintWriter generatePrintWriter() {
        PrintWriter writer;

        writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer;
    }

    /**
     * Assigns the given value to the ClientHandler object's Robot object.
     *
     * @param robot   the Robot object to assign to the ClientHandler object's
     *                Robot object.
     */
    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    /**
     * Retrieves the ClientHandler object's assigned Robot object.
     *
     * @return the Robot object assigned to the ClientHandler object.
     */
    public Robot getRobot() {
        return this.robot;
    }

    /**
     * Main controller function that facilitates communication with the client program,
     * and executes the given request messages given by the client program.
     */
    @Override
    public void run() {
        BufferedReader reader;
        JSONObject request;
        JSONObject response;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                request = getRequest(reader);
                if (request != null){
                    response = getResponse(request, world);
                    if (response != null) writer.println(response.toString());
                    if (response == null) break;
                }
            }
            socket.close();
            this.interrupt();
        } catch (NullPointerException | IOException e) {
            clients.remove(this);
            world.removeRobot(this.robot);
            this.interrupt();
        }
    }
}
