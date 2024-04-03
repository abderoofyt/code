package Commands.WorldCommands;

import Commands.WorldCommand;
import Display.TerminalDisplay;
import World.World;

public class WorldCommandError extends WorldCommand {
    String instruction;

    public WorldCommandError(String instruction) {
        super("error", "");
        this.instruction = instruction;
    }

    /**
     * Executes an error if the given instruction was invalid.
     *
     * @param world   the World object that represents the 'world'-simulation.
     * @return true
     */
    @Override
    public boolean execute(World world) {
        TerminalDisplay display;

        display = new TerminalDisplay(world);
        display.displayError(instruction);
        return true;
    }
}
