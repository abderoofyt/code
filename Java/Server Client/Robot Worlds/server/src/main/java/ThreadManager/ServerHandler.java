package ThreadManager;

import Commands.*;
import Display.TerminalDisplay;
import World.World;

import java.util.ArrayList;
import java.util.Scanner;

public class ServerHandler extends Thread {
    private World world;
    private ArrayList<ClientHandler> clients;

    public ServerHandler(World world, ArrayList<ClientHandler> clients) {
        this.world = world;
        this.clients = clients;
    }

    /**
     * Retrieves the command from the given instruction.
     *
     * @param instruction   the command and command arguments given
     *                      by the user.
     * @return the command.
     */
    private String getCommand(String instruction) {
        String[] args;

        args = instruction.split(" ");
        return args[0].toLowerCase();
    }

    /**
     * Retrieves the arguments from the given instruction.
     *
     * @param instruction   the command and command arguments given
     *                      by the user.
     * @return the arguments.
     */
    private String getArgs(String instruction) {
        int i;
        String strArgs;
        String[] args;

        i = 0;
        strArgs = "";
        args = instruction.split(" ");
        for (String arg: args) {
            if (i != 0) {
                strArgs += arg;
            }
            i++;
        }
        return strArgs;
    }

    /**
     * Retrieves input from the user.
     *
     * @param prompt   the message to display before accepting input.
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
     * Retrieves the command and command arguments from the user.
     *
     * @return a String consisting out of the chosen command and command arguments.
     */
    private static String getInstruction() {
        String instruction;

        instruction = getInput("< WORLD > :\n What should I do next? ");
        return instruction;
    }

    /**
     * The main controller function that retrieves and executes the server commands.
     */
    @Override
    public void run() {
        TerminalDisplay display;
        String instruction;
        WorldCommand worldCommand;
        boolean shouldContinue;

        display = new TerminalDisplay(world);
        display.clearScreen();
        display.printHeading();
        while (true) {
            instruction = getInstruction();
            worldCommand = new CommandUtils().createWorldCommand(getCommand(instruction), getArgs(instruction), clients);
            shouldContinue = worldCommand.execute(world);
            if (!shouldContinue) {
                this.interrupt();
                System.exit(0);
            }
        }
    }

}
