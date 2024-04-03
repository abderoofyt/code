import Display.*;
import ThreadManager.*;
import World.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ServerController {

    /**
     * Retrieves input from the user.
     *
     * @param prompt the message to display before accepting input.
     * @return the input received from the user.
     */
    private static String getInput(String prompt) {
        String input;
        Scanner scan;

        scan = new Scanner(System.in);
        System.out.print(prompt);
        input = (scan.hasNextLine()) ? scan.nextLine() : "";
        while (input.isBlank()) {
            System.out.print(prompt);
            input = (scan.hasNextLine()) ? scan.nextLine() : input;
        }
        return input;
    }

    /**
     * Retrieves the port number, that the server program is going to use
     * to communicate with the client program, from the user.
     *
     * @return the port number.
     */
    private static int getPortNumber() {
        String portNum;

        while (true) {
            portNum = getInput("Enter port number : ");
            try {
                return Integer.parseInt(portNum);
            } catch (Exception e) {
                continue;
            }
        }
    }

    /**
     * Main controller function for the server program.
     *
     * @param world   the World object used for the 'world'-simulation.
     */
    public static void run(World world, ArrayList<ClientHandler> threadList) {
        int portNum;
        Socket socket;
        ClientHandler clientThread;

        portNum = getPortNumber();
        new ServerHandler(world, threadList).start();
        new ServerGui(threadList, portNum, world).frame.setVisible(true);
        try (ServerSocket serverSocket = new ServerSocket(portNum)) {
            while(true) {
                socket = serverSocket.accept();
                clientThread = new ClientHandler(socket, threadList, world);
                threadList.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Something went wrong in the Server main");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        World world;
        ArrayList<ClientHandler> threadList;

        threadList = new ArrayList<>();
        world = new World(threadList);
        run(world, threadList);
    }

}
