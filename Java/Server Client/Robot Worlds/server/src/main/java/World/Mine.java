package World;

import Robot.Position;

public class Mine {

    int bottomLeftX;
    int bottomLeftY;
    int topRightX;
    int topRightY;
    boolean isActive;

    public Mine(int bottomLeftX, int bottomLeftY) {
        this.bottomLeftX = bottomLeftX;
        this.bottomLeftY = bottomLeftY;
        topRightX = bottomLeftX+4;
        topRightY = bottomLeftY+4;
        isActive = false;
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
     * Sets the Mine object's 'isActive' boolean to true.
     */
    public void Activate(){
        isActive = true;
    }

    /**
     * Determines whether the Mine object is active and able
     * to explode.
     */
    public boolean getIsActive(){
        return isActive;
    }

    /**
     * Determines whether the given position is in the Mine object's position.
     *
     * @param position   the Position object to check.
     * @return true  : if the given position is in the Mine object's position.
     *         false : if the given position is not in the Mine object's position.
     */
    public boolean mineInPosition(Position position) {
        int x;
        int y;
        boolean holeX;
        boolean holeY;

        if (!isActive) return false;
        x = position.getX();
        y = position.getY();
        holeX = (x >= bottomLeftX) && (x <= topRightX);
        holeY = (y >= bottomLeftY) && (y <= topRightY);
        return (holeX && holeY);
    }

    /**
     * Determines whether the Mine object is in the path between the given positions.
     *
     * @param a   the start position.
     * @param b   the end position.
     * @return true  : if the Mine object's is in the path.
     *         false : if the Mine object is not in the path.
     */
    public boolean mineInPath(Position a, Position b) {
        int startX;
        int startY;
        int endX;
        int endY;

        if (a.getX() == b.getX()) {
            startY = Math.min(a.getY(), b.getY());
            endY = Math.max(a.getY(), b.getY());
            for (int y = startY; y <= endY; y++) {
                if (mineInPosition(new Position(a.getX(), y))) return true;
            }
        } else {
            startX = Math.min(a.getX(), b.getX());
            endX = Math.max(a.getX(), b.getX());
            for (int x = startX; x <= endX; x++) {
                if (mineInPosition(new Position(x, a.getY()))) return true;
            }
        }
        return false;
    }
}
