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

}
