package Commands.WorldCommands;

import Commands.WorldCommand;
import Display.TerminalDisplay;
import World.World;

public class DumpCommand extends WorldCommand {

    public DumpCommand(){
        super("dump");
    }

    /**
     * Executes the dump command.
     *
     * @param world  the World object that represents the 'world'-simulation.
     * @return true
     */
    @Override
    public boolean execute(World world) {
        TerminalDisplay display = new TerminalDisplay(world);
        display.displayDump();
        return true;
    }
}
