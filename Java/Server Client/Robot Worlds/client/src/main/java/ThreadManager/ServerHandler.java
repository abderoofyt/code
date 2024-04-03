package ThreadManager;

import TerminalView.Display;
import Robot.IRobot;
import Json.*;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;;

public class ServerHandler extends Thread {
    private IRobot robot;
    private BufferedReader reader;
    private Display display;

    public ServerHandler(BufferedReader reader, IRobot robot, Display display) {
        this.robot = robot;
        this.reader = reader;
        this.display = display;
    }

    /**
     * Main controller function used to continuously listen for and receive response messages from the server
     * program. The response messages are used to update the player's Robot object and the updated state of
     * the robot is displayed to the user.
     */
    @Override
    public void run() {
        String response;
        JSONObject jsonResponse;
        Decode decoder;

        response = "";
        while (true) {
            try {
                while (true) {
                    response = reader.readLine();
                    if (response == null) {
                        display.display("World disconnected. Please try again later.", false, false, false);
                        this.interrupt();
                        System.exit(0);
                    } else if (!response.isBlank()){
                        break;
                    }
                }
            } catch (IOException e) {
                display.display("Something went wrong. Try again later", false, false, false);
                this.interrupt();
                System.exit(0);
            }
            jsonResponse = new Utilities().stringToJson(response);
            if(jsonResponse != null) {
                decoder = new Decode(jsonResponse, robot, display);
                decoder.decodeResponse(robot);
                display.refresh(true, false, decoder.getCommand().equals("look"));
            }
        }
    }
}
