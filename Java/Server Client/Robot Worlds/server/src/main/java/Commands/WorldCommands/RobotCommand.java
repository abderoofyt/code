package Commands.WorldCommands;

import Commands.WorldCommand;
import Display.TerminalDisplay;
import World.World;

public class RobotCommand extends WorldCommand {

    public RobotCommand(){super("robots");}

    /**
     * Executes the robots command.
     *
     * @param world   the World object that represents the 'world'-simulation.
     * @return
     */
    @Override
    public boolean execute(World world) {
        TerminalDisplay display;

        display = new TerminalDisplay(world);
        display.displayRobots();
        return true;
    }

}
