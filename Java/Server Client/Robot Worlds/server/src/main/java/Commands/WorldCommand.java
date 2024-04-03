package Commands;

import World.World;

public abstract class WorldCommand {

    private final String name;
    private String argument;

    public WorldCommand(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public WorldCommand(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    /**
     * Executes WorldCommand's logic.
     *
     * @return true if the WorldCommand is not the Shutdown Command.
     */
    public abstract boolean execute(World world);

    /**
     * Retrieves the WorldCommand object's allocated name.
     *
     * @return the WorldCommand object's name
     */
    public String getName() {                                                                           //<2>
        return name;
    }

    /**
     * Retrieves the WorldCommand object's argument.
     *
     * @return the WorldCommand object's argument
     */
    public String getArgument() {
        return this.argument;
    }

}
