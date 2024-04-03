package Robot;

public class AbstractRobot implements IRobot {

    private final String name;
    private final String type;
    private Direction direction;
    private Position position;
    private int shots;
    private int shield;
    private String status;

    public AbstractRobot(String name, String type, int shots, int shield) {
        this.name = name;
        this.type = type;
        direction = Direction.NORTH;
        this.shots = shots;
        this.shield = shield;
    }

    /**
     * Retrieves the Robot object's name.
     *
     * @return      the name of the Robot object.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Retrieves the Robot object's Position object.
     *
     * @return      the Position object of the Robot object.
     */
    @Override
    public Position getPosition() {
        return position;
    }

    /**
     * Retrieves the Robot object's type.
     *
     * @return      the type of the Robot object.
     */
    @Override
    public String getType() {
        return type;
    }

    /**
     * Retrieves the Robot object's shield strength.
     *
     * @return      the shield strength of the Robot object.
     */
    @Override
    public int getShieldStrength() {
        return shield;
    }

    /**
     * Retrieves the Robot object's maximum gun shots.
     *
     * @return      the maximum gun shots of the Robot object.
     */
    @Override
    public int getShots() {
        return shots;
    }

    /**
     * Updates the Robot's current Position object, with the new Position object.
     *
     * @param  position    the new Position object to assign to the Robot object.
     */
    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Updates the Robot's current Direction enum value, with the new Direction enum value.
     *
     * @param  direction    the new Direction enum value to assign to the Robot object.
     */
    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Retrieves the Direction object of the Robot object.
     *
     * @return the Direction object assigned to the Robot object.
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Updates the Robot's shield strength value, with the new shield strength value.
     *
     * @param  shield    the new shield strength value to assign to the Robot object.
     */
    public void setShieldStrength(int shield) {
        this.shield = shield;
    }

    /**
     * Updates the Robot's maximum gun shot value, with the new maximum gun shot value.
     *
     * @param  shots    the new maximum gun shot value to assign to the Robot object.
     */
    public void setShots(int shots) {
        this.shots = shots;
    }

    /**
     * Updates the Robot's status, with the status.
     *
     * @param status   the status to assign to the Robot object.
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the status of the Robot object.
     *
     * @return the status assigned to the Robot object.
     */
    @Override
    public String getStatus() {
        return status;
    }
}
