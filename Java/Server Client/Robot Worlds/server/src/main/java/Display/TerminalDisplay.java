package Display;

import Robot.Position;
import Robot.Robot;
import World.World;
import World.Pitfall;
import World.Obstacle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TerminalDisplay {
    private World world;

    public TerminalDisplay(World world) {
        this.world = world;
    }

    /**
     * Pads the given text with the given value of spaces.
     *
     * @param s   the String value to pad.
     * @param n   the length of the String after it is padded.
     * @return
     */
    private String pad(String s, int n) {
        String s1;

        s1 = String.format("%" + n + "s", s);
        return String.format("%-" + n + "s", s1);
    }

    /**
     * Converts plain text to bold text.
     *
     * @param s   the text to convert into bold text.
     * @return the bold text.
     */
    private String makeTextBold(String s) {
        return "\033[0;1m"+s+"\u001B[0m";
    }

    /**
     * Creates a table with the information given.
     *
     * @param rows      column values of the row.
     * @param isState   true if the command was a state command.
     * @param heading   the heading values for each column.
     * @return the table in String form.
     */
    private String createTable(List<List<String>> rows, boolean isState, String heading) {
        StringBuilder sb;
        String border;
        String headingSpace;

        border = (!isState) ? "+----------------------------------------+\n" : "+----------------------------------------------------------------------------------+\n";
        headingSpace = (!isState) ? "| "+makeTextBold(heading)+"                               |\n" : "| "+makeTextBold(heading)+"                                                                           |\n";
        sb = new StringBuilder();
        sb.append(border);
        sb.append(headingSpace);
        sb.append(border);
        for (int i = 0; i < rows.size(); i++) {
            List<String> row = rows.get(i);
            for (int j = 0; j < row.size(); j++) {
                String item = row.get(j);
                item = (i == 0) ? "\u001B[41m"+pad(item, 12)+"\u001B[0m" : pad(item, 12);
                sb.append("|"+item+"|");
            }
            sb.append("\n");
        }
        sb.append(border);
        return sb.toString();
    }

    /**
     * Creates a String of the Position object's x-, and y-values.
     *
     * @param position   the Position object to convert into a String.
     * @return the String value of the Position object.
     */
    private String createPositionField(Position position) {
        if (position == null) {
            return "";
        }
        return "["+position.getX()+","+position.getY()+"]";
    }

    /**
     * Creates a List of String lists, where each String list represents a row of
     * column values.
     *
     * @param headings   the headings of each column of the first row.
     * @param info       the list of String arrays, where each String array represents
     *                   a row of column values.
     * @return
     */
    private List<List<String>> createRows(String[] headings, List<String[]> info) {
        List<List<String>> rows;

        rows = new ArrayList<>();
        rows.add(Arrays.asList(headings));
        for (String[] row : info) {
            rows.add(Arrays.asList(row));
        }
        return rows;
    }

    /**
     * Creates a table of the information given, and returns it in String form.
     *
     * @param headings   the headings of each column of the first row.
     * @param info       the list of String arrays, where each String array represents
     *                   a row of column values.
     * @param isState    true if the state command is to be displayed in the table.
     * @param heading    the table's heading.
     * @return the table, in String form.
     */
    public String getTable(String[] headings, List<String[]> info, boolean isState, String heading) {
        return createTable(createRows(headings, info), isState, heading);
    }

    /**
     * Clears the terminal screen.
     */
    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Prints the robots, in the 'world'-simulation, in the form of a table.
     */
    public void displayRobots() {
        List<Robot> robots;
        List<String[]> info;

        robots = world.getAllRobots();
        info = new ArrayList<>();
        for (Robot robot : robots) {
            info.add(new String[]{robot.getName(), createPositionField(robot.getPosition()), robot.getDirection().toString(), String.valueOf(robot.getShieldStrength()), String.valueOf(robot.getShotsLeft()), robot.getStatus().toString()});
        }
        clearScreen();
        printHeading();
        System.out.println(getTable(new String[]{"NAME", "POSITION", "DIRECTION", "SHIELDS", "SHOTS", "STATUS"}, info, true, "ROBOTS"));
    }

    /**
     * Prints the robots, left in the 'world'-simulation, after the purge command was executed,
     * in table form.
     *
     * @param robotName   the robot's name, of the robot who was purged.
     * @param successful  true if the robot was successfully purged from the world.
     */
    public void displayPurge(String robotName, boolean successful) {
        clearScreen();
        printHeading();
        if (successful) {
            System.out.println(makeTextBold("<WORLD> :\nI have successfully removed "+robotName+".\nThe following robots are left:"));
        } else {
            System.out.println(makeTextBold("<WORLD> :\nI could not remove the robot named '"+robotName+"'.\nThe following robots are in the world:"));
        }
        displayRobots();
    }

    /**
     * Prints the robots, obstacles, and pitfalls in the 'world'-simulation.
     */
    public void displayDump() {
        clearScreen();
        printHeading();
        displayRobots();
        displayObstacles(world);
        displayPitfalls(world);
    }

    /**
     * Prints the valid server commands to the terminal.
     */
    private void printValidCommandsTable() {
        System.out.println("+------------------------------------------------------------------------------------------+");
        System.out.println("|  Commands          |  Description                                                        |");
        System.out.println("|--------------------|---------------------------------------------------------------------|");
        System.out.println("| quit               | disconnects all robots and ends the world                           |");
        System.out.println("| robots             | lists all robots in the world including the robot’s name and state  |");
        System.out.println("| purge <robot name> | kill the robot and remove it from the game                          |");
        System.out.println("| dump               | displays a representation of the world’s state                      |");
        System.out.println("+------------------------------------------------------------------------------------------+");
    }

    /**
     * Prints the valid server commands if the given command was invalid.
     *
     * @param instruction   the command and command arguments given by the user.
     */
    public void displayError(String instruction) {
        clearScreen();
        printHeading();
        System.out.println(makeTextBold("I did not understand '"+instruction+"'.\nHere's a list of valid commands:"));
        printValidCommandsTable();
    }

    /**
     * Prints the 'world'-simulations obstacles and their coordinates.
     *
     * @param world   the World object that represents the 'world'-simulation.
     */
    public void displayObstacles(World world) {
        List<String[]> info;
        List<Obstacle> obstacles;
        int bottomLeftX;
        int bottomLeftY;
        int topRightX;
        int topRightY;
        int i;

        info = new ArrayList<>();
        obstacles = world.getObstacles();
        i = 1;
        for (Obstacle obstacle : obstacles) {
            bottomLeftX = obstacle.getBottomLeftX();
            bottomLeftY = obstacle.getBottomLeftY();
            topRightX = obstacle.getTopRightX();
            topRightY = obstacle.getTopRightY();
            info.add(new String[]{String.valueOf(i), createPositionField(new Position(bottomLeftX, bottomLeftY)), createPositionField(new Position(topRightX, topRightY))});
            i++;
        }
        System.out.println(getTable(new String[]{"ID", "BOTTOM LEFT", "TOP RIGHT"}, info, false, "Obstacle"));
    }

    /**
     * Prints the 'world'-simulations pitfalls and their coordinates.
     *
     * @param world   the World object that represents the 'world'-simulation.
     */
    public void displayPitfalls(World world) {
        List<String[]> info;
        List<Pitfall> pitfalls;
        int bottomLeftX;
        int bottomLeftY;
        int topRightX;
        int topRightY;
        int i;

        info = new ArrayList<>();
        pitfalls = world.getPitfalls();
        i = 1;
        for (Pitfall pitfall : pitfalls) {
            bottomLeftX = pitfall.getBottomLeftX();
            bottomLeftY = pitfall.getBottomLeftY();
            topRightX = pitfall.getTopRightX();
            topRightY = pitfall.getTopRightY();
            info.add(new String[]{String.valueOf(i), createPositionField(new Position(bottomLeftX, bottomLeftY)), createPositionField(new Position(topRightX, topRightY))});
            i++;
        }
        System.out.println(getTable(new String[]{"ID", "BOTTOM LEFT", "TOP RIGHT"}, info, false, "Pitfalls"));
    }

    /**
     * Prints an ASCII heading to the terminal.
     */
    public void printHeading() {
        String heading;

        heading = "\u001B[33m\n" +
                " (        )           )                       )   (    (    (      (     \n" +
                " )\\ )  ( /(    (   ( /(   *   )   (  (     ( /(   )\\ ) )\\ ) )\\ )   )\\ )  \n" +
                "(()/(  )\\()) ( )\\  )\\())` )  /(   )\\))(   ')\\()) (()/((()/((()/(  (()/(  \n" +
                " /(_))((_)\\  )((_)((_)\\  ( )(_)) ((_)()\\ )((_)\\   /(_))/(_))/(_))  /(_)) \n" +
                "(_))    ((_)((_)_   ((_)(_(_())  _(())\\_)() ((_) (_)) (_)) (_))_  (_))   \n" +
                "| _ \\  / _ \\ | _ ) / _ \\|_   _|  \\ \\((_)/ // _ \\ | _ \\| |   |   \\ / __|  \n" +
                "|   / | (_) || _ \\| (_) | | |     \\ \\/\\/ /| (_) ||   /| |__ | |) |\\__ \\  \n" +
                "|_|_\\  \\___/ |___/ \\___/  |_|      \\_/\\_/  \\___/ |_|_\\|____||___/ |___/  \n" +
                "                                                                         \u001B[0m";
        System.out.println(heading);
        System.out.println("\u001B[33m\n                                                          TEAM 21\n\n\u001B[0m");
    }

    /**
     * Prints the quit command's output to the terminal.
     */
    public void displayQuitCommand() {
        clearScreen();
        System.out.println(makeTextBold("< World > :\nShutting down..."));
    }

}
