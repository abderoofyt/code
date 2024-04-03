
import ThreadManager.ClientHandler;
import World.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ServerGui extends JFrame{
    private ArrayList<ClientHandler> clients;
    JTable jt;
    JFrame f;
    World world;
    static JFrame frame = new JFrame("ServerGui");
    private JLabel portLabel;
    int portNum;

    public ServerGui(ArrayList<ClientHandler> clients, int portNum, World world){
        this.clients = clients;
        this.portNum = portNum;
        this.world = world;
        GamePanel panelText = getGamePanel();
        this.setTitle("Team 0120's  Robot World!");
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); //middle screen
        portLabel = new JLabel();
        portLabel.setText(toString().valueOf(portNum));
    }

    /**
     * Retrieves and adds GamePanel object to the JFrame.
     *
     * @return the GamePanel object.
     */
    private GamePanel getGamePanel() {
        GamePanel panelText;

        panelText = new GamePanel(new GridBagLayout(), world);
        this.add(panelText);
        return panelText;
    }
}
