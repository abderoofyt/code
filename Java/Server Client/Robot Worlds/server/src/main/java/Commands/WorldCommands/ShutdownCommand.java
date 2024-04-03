package Commands.WorldCommands;

import Commands.WorldCommand;
import Display.TerminalDisplay;
import World.World;

public class ShutdownCommand extends WorldCommand {

    public ShutdownCommand() {
        super("quit");
    }

    /**
     * Executes the quit/shutdown command.
     *
     * @param world   the World object that represents the 'world'-simulation.
     * @return false
     */
    @Override
    public boolean execute(World world) {
        TerminalDisplay display;

        display = new TerminalDisplay(world);
        display.displayQuitCommand();
        return false;
    }
}
