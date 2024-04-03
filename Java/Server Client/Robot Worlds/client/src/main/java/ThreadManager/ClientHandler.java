package ThreadManager;

import TerminalView.Display;
import Json.*;
import Robot.IRobot;
import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private final String IPAddress;
    private final int portNum;
    private IRobot robot;
    private int mineTime;
    private int reloadTime;
    private int repairTime;

    public ClientHandler(IRobot robot, String IPAddress, int portNum) {
        this.IPAddress = IPAddress;
        this.portNum = portNum;
        this.robot = robot;
    }

    /**
     * Determines whether the given instruction is a repair command.
     *
     * @param instruction   the instruction given by the user, in
     *                      the form of a String array.
     * @return true  : if the instruction is a repair command.
     *         false : if the instruction is not a repair command.
     */
    private boolean isRepairCommand(String[] instruction) {
        return instruction[0].equals("repair");
    }

    /**
     * Determines whether the given instruction is a reload command.
     *
     * @param instruction   the instruction given by the user, in
     *                      the form of a String array.
     * @return true  : if the instruction is a reload command.
     *         false : if the instruction is not a reload command.
     */
    private boolean isReloadCommand(String[] instruction) {
        return instruction[0].equals("reload");
    }

    /**
     * Determines whether the given instruction is a mine command.
     *
     * @param instruction   the instruction given by the user, in
     *                      the form of a String array.
     * @return true  : if the instruction is a mine command.
     *         false : if the instruction is not a mine command.
     */
    private boolean isMineCommand(String[] instruction) {
        return instruction[0].equals("mine");
    }

    /**
     * Returns the amount of time the client program needs to pause after
     * sending the request to the server program. This time is determined
     * by the type of command the user has given.
     *
     * @param instruction   the instruction given by the user.
     * @return the amount of time, in milliseconds, that the program needs
     *         to pause.
     */
    private int setWaitTime(String[] instruction) {
        if (isMineCommand(instruction)){
            return (mineTime*1000)+100;
        } else if (isReloadCommand(instruction)) {
            return (reloadTime*1000)+100;
        } else if (isRepairCommand(instruction)) {
            return (repairTime*1000)+100;
        }
        return 100;
    }

    /**
     * Pause program for the given amount of time and restarts the program
     * when the response from the server is received and displayed to the
     * user.
     *
     * @param display        the Display object used to display the outcome
     *                       of the command to the user.
     * @param milliseconds   the amount of time the program needs to pause
     *                       in milliseconds.
     */
    private void wait(Display display, int milliseconds) {
        try {
            Thread.sleep(milliseconds);
            while (true) {
                if (display.isPrinted()) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets time values, in seconds, for the timed commands. The timed
     * commands include the mine -, reload-, and repair command.
     *
     * @param decoder   the Decoder object used to decode the response
     *                  given by the server program.
     */
    private void setTimerCommandTimes(Decode decoder) {
        mineTime = decoder.getMineTime();
        reloadTime = decoder.getReloadTime();
        repairTime = decoder.getRepairTime();
    }

    /**
     * Retrieves a JSONObject response, in the form of a String, from the server program.
     *
     * @param reader   the BufferedReader object used to communicate with the server program.
     * @return the response from the server in the form of a JSONObject data type.
     */
    private JSONObject getResponse(BufferedReader reader) {
        String response;

        while (true) {
            try {
                response = reader.readLine();
                break;
            } catch (Exception e) {
                continue;
            }
        }
        return new Utilities().stringToJson(response);
    }

    /**
     * Sends the server program a JSONObject request message, in the form of a String data type.
     *
     * @param request   the JSONObject containing the command the server program needs to execute,
     *                  and the information needed to execute the command.
     * @param writer    the PrintWriter object used to communicate with the server program.
     */
    private void sendRequest(JSONObject request, PrintWriter writer) {
        while (true) {
            try {
                writer.println(request.toString());
                break;
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * Creates a String array of command arguments using the instruction given by the user.
     *
     * @param instruction   command and command arguments given by the user.
     * @return a String array containing the command arguments.
     */
    private String[] getArgs(String[] instruction) {
        String[] args;

        if (instruction.length < 2) return new String[]{};
        args = new String[]{instruction[1]};
        return args;
    }

    /**
     * Launches the robot into the server program's 'world'-simulation and displays outcome of the launch
     * to the user.
     *
     * @param robot   the IRobot object to launch into the world.
     * @param writer  the PrintWriter object used to communicate with the server program.
     * @param reader  the BufferedReader object used to retrieve the response from the server program.
     * @return true  : if the robot could join the server program's 'world'-simulation.
     *         false : if the robot could not join the server program's 'world'-simulation.
     */
    public boolean launchRobot(IRobot robot, PrintWriter writer, BufferedReader reader, Display display) {
        Encode encoder;
        Decode decoder;
        JSONObject request;
        JSONObject response;

        encoder = new Encode(robot, "launch", getArgs(new String[]{"launch"}));
        request = encoder.getRequest();
        sendRequest(request, writer);
        response = getResponse(reader);
        decoder = new Decode(response, robot, display);
        decoder.decodeResponse(robot);
        setTimerCommandTimes(decoder);
        display.displayLaunch(decoder.isSuccessful());
        return decoder.isSuccessful();
    }

    /**
     * The main controller function used to retrieve and send the request from the user
     * to the server program.
     */
    public void run() {
        BufferedReader reader;
        PrintWriter writer;
        boolean shouldContinue;
        int time;
        String[] instruction;
        Encode encoder;
        Display display;

        display = new Display(robot);
        try (Socket socket = new Socket(IPAddress, portNum)) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
            shouldContinue = launchRobot(robot, writer, reader, display);
            new ServerHandler(reader, robot, display).start();
            time = 100;
            while(shouldContinue) {
                wait(display, time);
                instruction = display.getInstructionFromUser().split(" ");
                time = setWaitTime(instruction);
                display.displayTimerCommand(isMineCommand(instruction), isRepairCommand(instruction), isReloadCommand(instruction));
                encoder = new Encode(robot, instruction[0], getArgs(instruction));
                sendRequest(encoder.getRequest(), writer);
            }
        } catch (IOException e) {
            display.display("An error occurred. Try again later.", false, false, false);
            System.exit(0);
        }
    }
}
