package Commands;

import Robot.*;
import World.World;
import org.json.simple.JSONObject;

public abstract class RobotCommand {

    private final String name;
    private String argument;

    public RobotCommand(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public RobotCommand(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    /**
     * Executes RobotCommand's logic.
     *
     * @return true  : if the command is not the Shutdown/Quit command.
     *         false : if the command is a Shutdown/Quit command.
     */
    public abstract JSONObject execute(Robot target, World world);

    /**
     * Retrieves the Command object's allocated name.
     *
     * @return the Command object's name
     */
    public String getName() {                                                                           //<2>
        return name;
    }

    /**
     * Retrieves the Command object's argument.
     *
     * @return the Command object's argument
     */
    public String getArgument() {
        return this.argument;
    }

}
