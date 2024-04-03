package World;

import Robot.Position;
import Robot.Robot;
import Robot.Robot.Direction;

import java.awt.*;
import java.util.List;

public class Pitfall {

    int bottomLeftX;
    int bottomLeftY;
    int topRightX;
    int topRightY;

    public Pitfall(int bottomLeftX, int bottomLeftY) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        topRightX = bottomLeftX+4;
        topRightY = bottomLeftY+4;
    }

    /**
     * Retrieves the top right position's x-value.
     *
     * @return the top right position's x-value.
     */
    public int getTopRightX() {
        return topRightX;
    }

    /**
     * Retrieves the top right position's y-value.
     *
     * @return the top right position's y-value.
     */
    public int getTopRightY() {
        return topRightY;
    }

    /**
     * Retrieves the bottom left position's x-value.
     *
     * @return the bottom left position's x-value.
     */
    public int getBottomLeftX() {
        return this.bottomLeftX;
    }

    /**
     * Retrieves the bottom left position's y-value.
     *
     * @return the bottom left position's y-value.
     */
    public int getBottomLeftY() {
        return this.bottomLeftY;
    }

    /**
     * Calculates the amount of steps from the given position to given Pitfall object.
     *
     * @param robotPos   the robot's current position.
     * @param pitfall    the pitfall in question.
     * @param direction  the direction the robot is facing.
     * @return the amount of steps.
     */
    public static int getSteps(Position robotPos, Pitfall pitfall, String direction) {
        switch (direction) {
            case "north":
                return (pitfall.getBottomLeftY() - robotPos.getY());
            case "south":
                return(robotPos.getY() - pitfall.getTopRightY())+1;
            case "west":
                return (robotPos.getX() - pitfall.getTopRightX())+1;
        }
        // east
        return (pitfall.getBottomLeftX() - robotPos.getX());
    }

    /**
     * Retrieves the position to which the robot can see, according to the given visibility.
     *
     * @param robotPos     the robot's current position.
     * @param direction    the robot's current direction.
     * @param visibility   the visibility range.
     * @return the Position object with the x- and y-values of the position the robot can see to.
     */
    private static Position getVisibilityFinalDest(Position robotPos, Direction direction, int visibility) {
        int x;
        int y;
        int newX;
        int newY;

        x = robotPos.getX();
        y = robotPos.getY();
        newX = x;
        newY = y;
        switch (direction) {
            case NORTH:
                newY = y + visibility;
                break;
            case WEST:
                newX = x - visibility;
                break;
            case SOUTH:
                newY = y - visibility;
                break;
            case EAST:
                newX = x + visibility;
                break;
        }
        return new Position(newX, newY);
    }

    /**
     * Converts the given String value to the correlating Direction eNum.
     *
     * @param direction   the direction, in String form.
     * @return NORTH : if the given value is "NORTH".
     *         SOUTH : if the given value is "SOUTH".
     *         WEST  : if the given value is "WEST".
     *         EAST  : if the given value is "EAST".
     */
    private static Robot.Direction convertStringToDirection(String direction) {
        switch (direction.toLowerCase()) {
            case "north":
                return Robot.Direction.NORTH;
            case "south":
                return Robot.Direction.SOUTH;
            case "west":
                return Robot.Direction.WEST;
        }
        return Robot.Direction.EAST;
    }

    /**
     * Retrieves the nearest pitfall in the robot's visibility range, and returns it.
     *
     * @param direction   the String value of the direction the robot is currently facing.
     * @param world       the World object that represents the 'world'-simulation.
     * @param robot       the Robot object in question.
     * @return the Pitfall object : the nearest Pitfall object in the given Robot object's
     *                              range of visibility.
     *         null               : if there is no Pitfall object in the Robot object's range
     *                              of visibility.
     */
    public static Pitfall getNearestVisiblePitfall(String direction, World world, Robot robot) {
        int visibility;
        Position startDest;
        Position finalDest;
        List<Pitfall> pitfalls;
        int nearest;
        Pitfall nearestPitfall;
        int steps;

        visibility = Config.fileUtils.getVisibility();
        startDest = robot.getPosition();
        finalDest = getVisibilityFinalDest(startDest, convertStringToDirection(direction), visibility);
        pitfalls = world.getPitfalls();
        nearest = 200;
        nearestPitfall = null;
        for (Pitfall pitfall : pitfalls) {
            if (!(pitfall.inPitfall(startDest, finalDest))) continue;
            steps = getSteps(startDest, pitfall, direction);
            if (steps > 0) {
                if ((visibility >= steps) && (steps < nearest)) {
                    nearest = steps;
                    nearestPitfall = pitfall;
                }
            }
        }
        return nearestPitfall;
    }

    /**
     * Determines whether the given position is in the Pitfall object's position.
     *
     * @param position   the Position object to check.
     * @return true  : if the given position is in the Pitfall object's position.
     *         false : if the given position is not in the Pitfall object's position.
     */
    public boolean fallIn(Position position) {
        int x;
        int y;
        boolean holeX;
        boolean holeY;

        x = position.getX();
        y = position.getY();
        holeX = (x >= bottomLeftX) && (x <= topRightX);
        holeY = (y >= bottomLeftY) && (y <= topRightY);
        return (holeX && holeY);
    }


    /**
     * Determines whether the Pitfall object is in the path between the given positions.
     *
     * @param a   the start position.
     * @param b   the end position.
     * @return true  : if the Pitfall object's is in the path.
     *         false : if the Pitfall object is not in the path.
     */
    public boolean inPitfall(Position a, Position b) {
        int startX;
        int startY;
        int endX;
        int endY;

        if (a.getX() == b.getX()) {
            startY = Math.min(a.getY(), b.getY());
            endY = Math.max(a.getY(), b.getY());
            for (int y = startY; y <= endY; y++) {
                if (fallIn(new Position(a.getX(), y))) {
                    return true;
                }
            }
        } else {
            startX = Math.min(a.getX(), b.getX());
            endX = Math.max(a.getX(), b.getX());
            for (int x = startX; x <= endX; x++) {
                if (fallIn(new Position(x, a.getY()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Determines the given coordinate's value in terms of the displayed window's
     * coordinates.
     *
     * @param isX          true if the coordinate is an x-value.
     * @param coordinate   the coordinate to convert.
     * @return the given coordinate's value in terms of the displayed window's
     *         coordinates.
     */
    private int getCoordinate(boolean isX, int coordinate) {
        int x;
        int y;

        y = 200;
        for (int row = 0; row < 401; row++) {
            x = -200;
            for (int col = 0; col < 401; col++) {
                if (isX && (coordinate == x)) {
                    return col;
                }
                x++;
            }
            if (!isX && (coordinate == y)) {
                return row;
            }
            y--;
        }
        return -1;
    }

    /**
     * Draws the square Pitfall object on the displayed GUI window, in the color red.
     *
     * @param g   the Graphics object used to draw the Pitfall object.
     */
    public void draw(Graphics g){
        g.setColor(Color.red);
        g.drawRect(getCoordinate(true, bottomLeftX), getCoordinate(false, bottomLeftY), 4, 4);
    }

}
