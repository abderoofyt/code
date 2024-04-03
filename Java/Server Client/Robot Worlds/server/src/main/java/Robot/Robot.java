package Robot;

import Commands.RobotCommands.FireCommand;
import World.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Robot {

    private String name;
    private Position position;
    private Robot.Status status;
    private Robot.Direction direction;
    private String type;
    private int shieldStrength;
    private int shotsLeft;
    private int shotDistance;
    private int maxShots;
    private int maxShieldStrength;
    private Color color;

    public Robot(String name, String type, int shieldStrength, int shotsLeft) {
        this.name = name;
        this.position = new Position(0,0); 
        this.status = Status.NORMAL;
        this.direction = Direction.NORTH;
        this.type = type;
        this.shieldStrength = shieldStrength;
        this.shotsLeft = shotsLeft;
        maxShots = shotsLeft;
        maxShieldStrength = shieldStrength;
        this.shotDistance = FireCommand.getMaxShotDistance();
    }

    /**
     * Enum used to track direction
     */
    public enum Direction {
        NORTH, SOUTH, WEST, EAST
    }

    /**
     * Enum used to track the robot's status
     *
     * RELOADING - the robot is reloading weapons
     * REPAIRING - the robot is repairing shields
     * SETMINE - the robot is setting a mine
     * NORMAL - the robot is waiting for the next command
     * DEAD - the robot is dead and is no longer in the world.
     */
    public enum Status {
        RELOADING, REPAIRING, SETMINE, NORMAL, DEAD
    }

    /**
     * Assigns the given value to the Robot object's Color object.
     * @param color   the Color object to assign to the Robot object.
     * */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns the Robot object's assigned shield strength.
     *
     * @return the robots shield strength.
     */
    public int getMaxShieldStrength() {
        return maxShieldStrength;
    }

    /**
     * Retrieves the Robot object's assigned gun shots left.
     *
     * @return the robots amount of shots left.
     */
    public int getMaxShots() {
        return maxShots;
    }

    /**
     * Retrieves the Robot object's Direction eNum, that represents
     * the direction the robot is facing.
     *
     * @return NORTH : the robot is facing north.
     *         EAST  : the robot is facing east.
     *         SOUTH : the robot is facing south.
     *         WEST  : the robot is facing west.
     */
    public Robot.Direction getDirection() {
        return direction;
    }

    /**
     * Updates the Robot object's Direction object when the robot
     * turns either right or left.
     *
     * @param turnRight   true if the robot is turning right.
     */
    public void updateDirection(boolean turnRight){
        if (turnRight) {
            if (this.direction.equals(direction.NORTH)){
                this.direction = direction.EAST;
            } else if (this.direction.equals(direction.EAST)){
                this.direction = direction.SOUTH;
            } else if (this.direction.equals(direction.SOUTH)){
                this.direction = direction.WEST;
            } else if (this.direction.equals(direction.WEST)){
                this.direction = Direction.NORTH;
            }
        }
        else {
            if (this.direction.equals(direction.NORTH)){
                this.direction = direction.WEST;
            } else if (this.direction.equals(direction.WEST)){
                this.direction = direction.SOUTH;
            } else if (this.direction.equals(direction.SOUTH)){
                this.direction = direction.EAST;
            } else if (this.direction.equals(direction.EAST)){
                this.direction = direction.NORTH;
            }
        }
    }

    /**
     * Retrieves the Robot object's name.
     *
     * @return      the name of the Robot object.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the Robot object's Position object.
     *
     * @return      the Position object of the Robot object.
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Retrieves the Robot object's status.
     *
     * @return      the status of the Robot object.
     */
    public Robot.Status getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    /**
     * Updates the Robot's current Position object, with the new Position object.
     *
     * @param  position    the new Position object to assign to the Robot object.
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Retrieves the Robot object's shield strength.
     *
     * @return      the shield strength.
     */
    public int getShieldStrength() {
        return shieldStrength;
    }

    /**
     * Retrieves the Robot object's gun distance.
     *
     * @return      the gun distance.
     */
    public int getShotsLeft() {
        return shotsLeft;
    }

    /**
     * Updates the Robot's current status, with the given Status eNum.
     *
     * @param  s    the new status to assign to the Robot object.
     */
    public void setStatus(Status s) {
        status = s;
    }

    /**
     * Updates the Robot object's type, with the given type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Updates the Robot object's shieldStrength, with the given shieldStrength.
     */
    public void setShieldStrength(int shieldStrength) {
        this.shieldStrength = shieldStrength;
    }

    /**
     * Determines whether this robot blocks the given position.
     *
     * @param a   the position to check.
     * @return true  : if the position is blocked by this Robot object.
     *         false : if the position is not blocked by this Robot object.
     */
    public boolean robotBlocksPosition(Position a) {
        int x;
        int y;
        int checkX;
        int checkY;

        x = position.getX();
        y = position.getY();
        checkX = a.getX();
        checkY = a.getY();
        return ((x == checkX) && (y == checkY));
    }

    /**
     * Determines whether this robot blocks the path between the two given positions.
     *
     * @param newPosition     the start position.
     * @param checkPosition   the end position.
     * @return true  : if this robot blocks the path between the two positions.
     *         false : if this robot does not block the path between the two positions.
     */
    public boolean robotBlocksPath(Position newPosition, Position checkPosition) {
        Position currentPosition;
        int startY;
        int endY;
        int startX;
        int endX;
        int checkX;
        int checkY;

        currentPosition = getPosition();
        checkY = checkPosition.getY();
        checkX = checkPosition.getX();
        if ((currentPosition.getX() == newPosition.getX()) && (currentPosition.getX() == checkX)) {
            startY = Math.min(currentPosition.getY(), newPosition.getY());
            endY = Math.max(currentPosition.getY(), newPosition.getY());
            for (int i = startY; i <= endY; i++) {
                if (i == checkY) return true;
            }
        } else if (currentPosition.getY() == checkY) {
            startX = Math.min(currentPosition.getX(), newPosition.getX());
            endX = Math.max(currentPosition.getX(), newPosition.getX());
            for (int j = startX; j <= endX; j++) {
                if (j == checkX) return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the shot distance of the Robot object.
     *
     * @return the shot distance of the robot.
     */
    public int getShotDistance() {
        return this.shotDistance;
    }

    /**
     * Assigns the given value to the Robot object's shot distance variable.
     *
     * @param shotDistance   the integer value to assign to the shot distance variable
     *                       of the robot.
     */
    public void setShotDistance(int shotDistance) {
        this.shotDistance = shotDistance;
    }

    /**
     * Updates the robot's amount of shots left, with the given value.
     *
     * @param shotsLeft   the robot's updated amount of shots left.
     */
    public void setShotsLeft(int shotsLeft) {
        this.shotsLeft = shotsLeft;
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
     * Draws the Robot object on the displayed GUI window, in the color assigned color.
     *
     * @param g   the Graphics object used to draw the Obstacle object.
     */
    public void draw(Graphics g){
        g.setColor(color);
        g.fillRect(getCoordinate(true, position.getX()), getCoordinate(false, position.getY()), 4, 4);
    }
}
