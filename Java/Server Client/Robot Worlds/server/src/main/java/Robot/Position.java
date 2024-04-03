package Robot;

public class Position {

    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Retrieves the Position object's x-value.
     *
     * @return      the x-value of the Position object.
     */
    public int getX() {
        return x;
    }

    /**
     * Retrieves the Position object's y-value.
     *
     * @return      the y-value of the Position object.
     */
    public int getY() {
        return y;
    }

    /**
     * Updates the Position's current x-value, with the new x-value.
     *
     * @param  x    the x-value to assign to the Position object.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Updates the Position's current y-value, with the new y-value.
     *
     * @param  y    the y-value to assign to the Position object.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks if the x and y positions are within the given boundary.
     * Positions topLeft and bottomRight are anchor points of a block.
     *
     * @return true  : if the position is within the boundary.
     *         false : if the position is not within the boundary.
     */
    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop;
        boolean withinBottom;
        boolean withinLeft;
        boolean withinRight;

        withinTop = this.y <= topLeft.getY();
        withinBottom = this.y >= bottomRight.getY();
        withinLeft = this.x >= topLeft.getX();
        withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

}
