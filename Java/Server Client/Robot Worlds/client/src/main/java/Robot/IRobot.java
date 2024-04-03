package Robot;

public interface IRobot {

    /**
     * Retrieve the Robot object's name.
     */
    String getName();

    /**
     * Retrieve the Robot object's type.
     */
    String getType();

    /**
     * Retrieve the Robot object's Position object.
     */
    Position getPosition();

    /**
     * Retrieve the Robot object's shield strength.
     */
    int getShieldStrength();

    /**
     * Retrieve the Robot object's maximum gun shots.
     */
    int getShots();

    /**
     * Retrieves the Direction object of the Robot object.
     *
     * @return the Direction object assigned to the Robot object.
     */
    Direction getDirection();

    /**
     * Retrieves the status of the Robot object.
     *
     * @return the status assigned to the Robot object.
     */
    String getStatus();

    /**
     * Updates the Robot's current Position object, with the new Position object.
     *
     * @param  position    the new Position object to assign to the Robot object.
     */
    void setPosition(Position position);

    /**
     * Updates the Robot's current Direction enum value, with the new Direction enum value.
     *
     * @param  direction    the new Direction enum value to assign to the Robot object.
     */
    void setDirection(Direction direction);

    /**
     * Updates the Robot's shield strength value, with the new shield strength value.
     *
     * @param  shieldStrength    the new shield strength value to assign to the Robot object.
     */
    void setShieldStrength(int shieldStrength);

    /**
     * Updates the Robot's gun shot value, with the new gun shot value.
     *
     * @param  shots    the new gun shot value to assign to the Robot object.
     */
    void setShots(int shots);

    /**
     * Updates the Robot's status, with the status.
     *
     * @param status   the status to assign to the Robot object.
     */
    void setStatus(String status);

    /**
     * Enum used to track direction
     */
    enum Direction {
        NORTH, WEST, SOUTH, EAST
    }

}
