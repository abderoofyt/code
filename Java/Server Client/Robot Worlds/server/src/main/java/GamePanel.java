import ThreadManager.ClientHandler;
import ThreadManager.ServerHandler;
import World.Obstacle;
import World.Pitfall;
import World.World;
import Robot.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {

    //final for non mutable == namingConv(ALL CAPS)
    static final int multiple = 2;
    static final int GAME_WIDTH = 204 * multiple;
    static final int GAME_HEIGHT = 204 * multiple;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 4 * multiple;

    private World world;

    Thread gameThread;
    Image image;
    Graphics graphics;

    static List<Obstacle> obstacleList;
    static List<Pitfall> pitfallsList;

    List<Obstacle> obstacles;
    List<Pitfall> pitfalls;

    List<Robot> robots;

    public GamePanel(GridBagLayout gridLayout, World world) {
        newObstacle(world);
        newPitfall(world);
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_SIZE);
        this.world = world;

        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Creates a list of all the Obstacle objects in the World object's
     * list of Obstacles.
     *
     * @param world   the World object used for the 'world'-simulation.
     */
    public void newObstacle(World world) {
        obstacleList = new ArrayList<>();
        obstacles = world.getObstacles();

        for (Obstacle obs: obstacles) {
            int x = obs.getBottomLeftX();
            int y = obs.getBottomLeftY();
            obstacleList.add(new Obstacle(x, y));
        }
    }

    /**
     * Creates a list of all the Pitfall objects in the World object's
     * list of Pitfalls.
     *
     * @param world   the World object used for the 'world'-simulation.
     */
    public void newPitfall(World world) {
        pitfallsList = new ArrayList<>();
        pitfalls = world.getPitfalls();

        for (Pitfall pits: pitfalls) {
            int x = pits.getBottomLeftX();
            int y = pits.getBottomLeftY();
            pitfallsList.add(new Pitfall(x, y));
        }
    }

    /**
     * Draws the graphics on the GUI.
     *
     * @param g the Graphics object used to draw.
     */
    public void paint(Graphics g){
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }

    /**
     * Draws the robots, obstacles and pitfalls, that exist in the
     * 'world'-simulation, on the GUI.
     *
     * @param g the Graphics object used to draw.
     */
    public void draw(Graphics g){
        robots = world.getAllRobots();
        for (Robot robot : robots){
            robot.draw(g);
        }
        for (Obstacle obs: obstacleList){
            obs.draw(g);
        }
        for (Pitfall pits: pitfallsList){
            pits.draw(g);
        }
    }

    /**
     * Function, that continuously runs, and prints the updated GUI
     *'world'-simulation, including the robots, obstacles
     * and pitfalls.
     */
    @Override
    public void run() {
        //Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        while(true) {
            long now = System.nanoTime();
            delta += (now -lastTime)/ns;
            lastTime = now;
            if(delta >= 1){
                repaint();
                delta--;
            }
        }
    }
}
