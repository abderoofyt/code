package TerminalView;

import Robot.IRobot;

import java.util.Scanner;

public class Display {

    private boolean printed = false;
    private TableUtilities table;
    private IRobot robot;
    private String instruction;

    public Display(IRobot robot) {
        table = new TableUtilities(robot);
        this.robot = robot;
        instruction = null;
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void display(String status, boolean isAlive, boolean isSilent, boolean isLook) {
        robot.setStatus(status);
        refresh(isAlive, isSilent, isLook);
    }

    /**
     * Prints an ASCII heading to the terminal.
     */
    private static void printHeading() {
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

    private String padRight(String s, int length) {
        StringBuilder sb;

        sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(" ");
        }
        return sb+s;
    }

    public static String padCentre(String text, int len){
        String out = String.format("%"+len+"s%s%"+len+"s", "",text,"");
        float mid = (out.length()/2);
        float start = mid - (len/2);
        float end = start + len;
        return out.substring((int)start, (int)end);
    }

    private String makeTextGreen(String s) {
        return "\u001B[32m"+s+"\u001B[0m";
    }

    private String makeTextBold(String s) {
        return "\033[0;1m"+s+"\u001B[0m";
    }

    private String createMessageBox(String message) {
        StringBuilder sb;

        sb = new StringBuilder();
        sb.append(makeTextBold(makeTextGreen("+--------------------------------------------------------------------+\n")));
        sb.append(makeTextBold(makeTextGreen("|")+"                                                                    "+makeTextGreen("|\n")));
        sb.append(makeTextBold(makeTextGreen("|")+padCentre(message, 68)+makeTextGreen("|\n")));
        sb.append(makeTextBold(makeTextGreen("|")+"                                                                    "+makeTextGreen("|\n")));
        sb.append(makeTextBold(makeTextGreen("+--------------------------------------------------------------------+\n")));
        return sb.toString();
    }

    /**
     * Prints an ASCII robot figure and the robot introduction to the terminal.
     *
     */
    private void printRobotASCII(boolean isAlive, IRobot robot) {
        String robotString;

        if (isAlive) {
            robotString = (padRight("         __\n", 50) +
                    padRight(" _(\\    |@@|\n", 50) +
                    padRight("(__/\\__ \\--/ __\n", 50) +
                    padRight("   \\___|----|  |   __\n", 50) +
                    padRight("       \\ }{ /\\ )_ / _\\\n", 50) +
                    padRight("       /\\__/\\ \\__O (__\n", 50) +
                    padRight("      (--/\\--)    \\__/\n", 50) +
                    padRight("      _)(  )(_\n", 50) +
                    padRight("     `---''---`\n", 50));
        } else {
            robotString = (padRight("                 ------\n", 40)+
                    padRight("           _____/      \\\\_____\n", 40) +
                    padRight("          |  _     ___   _   ||\n", 40) +
                    padRight("          | | \\     |   | \\  ||\n" , 40)+
                    padRight("          | |  |    |   |  | ||\n" ,40)+
                    padRight("          | |_/     |   |_/  ||\n", 40) +
                    padRight("          | | \\     |   |    ||\n" ,40)+
                    padRight("          | |  \\    |   |    ||\n" ,40)+
                    padRight("          | |   \\. _|_. | .  ||\n", 40) +
                    padRight("          |                  ||\n", 40) +
                    padRight("          |                  ||\n", 40));
        }
        System.out.println(robotString);
    }

    private void printRobotName() {
        System.out.println(padCentre(makeTextBold(robot.getName()), 70));
    }

    private void printMessageBox() {
        System.out.println(createMessageBox(robot.getStatus()));
    }

    private void printHealthTable() {
        System.out.println(table.getHealthTable());
    }

    /**
     * Retrieves input from the user.
     *
     * @param prompt the message to display before accepting input.
     * @return the input received from the user.
     */
    public static String getInput(String prompt) {
        String input;
        Scanner scan;

        scan = new Scanner(System.in);
        System.out.println(prompt);
        input = (scan.hasNextLine()) ? scan.nextLine() : "";
        while (input.isBlank()) {
            System.out.println(prompt);
            input = (scan.hasNextLine()) ? scan.nextLine() : input;
        }
        return input;
    }

    public String getInstructionFromUser() {
        String instruction;

        instruction = getInput(makeTextBold("What should I do next? ")).toLowerCase().trim();
        setPrinted(false);
        return instruction;
    }

    private void displayLook() {
        System.out.println(makeTextGreen("---------------------------------------------------------------------"));
        System.out.println(makeTextBold("              "+robot.getStatus()+"\n"));
        System.out.println(makeTextGreen("---------------------------------------------------------------------"));
    }

    private void printScreen(boolean isAlive, boolean silent, boolean isLook) {
        printHeading();
        printRobotASCII(isAlive, robot);
        printRobotName();
        if (!isLook) {
            printMessageBox();
        } else {
            displayLook();
        }
        printHealthTable();
        if (!silent) setPrinted(true);
    }

    public void refresh(boolean isAlive, boolean silent, boolean isLook) {
        clearScreen();
        printScreen(isAlive, silent, isLook);
    }

    public boolean isPrinted() {
        return this.printed;
    }

    public void setPrinted(boolean printed) {
        this.printed = printed;
    }

    public void displayLaunch(boolean shouldContinue) {
        if (shouldContinue) {
            display("Hi, my name is "+robot.getName()+"!", true, false, false);
        } else {
            display("I could not launch into the world.", false, false, false);
        }
    }

    public void displayTimerCommand(boolean isMine, boolean isRepair, boolean isReload) {
        String status;

        if (isMine){
            status = "Mine is being activated...";
        } else if (isReload) {
            status = "Weapons are being reloaded...";
        } else if (isRepair) {
            status = "Shield is being repaired...";
        } else {
            return;
        }
        display(status, true, true, false);
    }
}
