package Commands;

import Commands.RobotCommands.*;
import Commands.RobotCommands.MineCommand.MineCommand;
import Commands.WorldCommands.*;
import Commands.WorldCommands.RobotCommand;
import ThreadManager.ClientHandler;

import java.util.ArrayList;

public class CommandUtils {

    /**
     * Creates and retrieves the appropriate WorldCommand object based off of the given instruction by the client program.
     *
     * @return the appropriate WorldCommand object
     */
    public WorldCommand createWorldCommand(String command, String arguments, ArrayList<ClientHandler> clients) {
        switch(command) {
            case "off":
            case "shutdown":
            case "quit":
                return new ShutdownCommand();
            case "purge":
                return new PurgeCommand(arguments, clients);
            case "dump":
                return new DumpCommand();
            case "robots":
                return new RobotCommand();
            default:
                return new WorldCommandError(command + " " + arguments);
        }
    }

    /**
     * Creates and retrieves the appropriate RobotCommand object based off of the given instruction by the client program.
     *
     * @return the appropriate RobotCommand object
     */
    public static Commands.RobotCommand createRobotCommand(String command, String arguments) {
        switch(command) {
            case "right":
                return new RightCommand();
            case "left":
                return new LeftCommand();
            case "forward":
                return new ForwardCommand(arguments);
            case "back":
                return new BackCommand(arguments);
            case "launch":
                return new LaunchCommand();
            case "look":
                return new LookCommand();
            case "mine":
                return new MineCommand();
            case "fire":
                return new FireCommand();
            case "reload":
                return new ReloadCommand();
            case "state":
                return new StateCommand();
            case "repair":
                return new RepairCommand();
            case "errorType":
                return new RobotCommandError("type");
            case "errorLocation":
                return new RobotCommandError("location");
            case "errorParse":
                return new RobotCommandError("parse");
            default:
                return new RobotCommandError("command");
        }
    }

}
