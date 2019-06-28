package ir.nimac.view;

import javax.swing.*;
import java.awt.*;

public class ServerFrame extends JFrame {
    private static final Point frameLocationPoint = new Point(1000, 400);
    private Dimension preferredDimension = new Dimension(2000, 1000);
    JPanel gamesPanel = new JPanel();
    JScrollPane gamesScroller = new JScrollPane(gamesPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JPanel playerPanel = new JPanel();
    JScrollPane playerScroller = new JScrollPane(playerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JPanel rankPanel = new JPanel();
    JScrollPane rankScroller = new JScrollPane(rankPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    JPanel panel = new JPanel();
    ServerNewGamePanel serverNewGamePanel = new ServerNewGamePanel();

    public ServerFrame(){
        init();
        setPreferredSize(preferredDimension);
        panel.setLayout(new GridLayout(1, 4));
//        gamesPanel.setLayout(new BoxLayout(gamesPanel, BoxLayout.PAGE_AXIS));
//        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.PAGE_AXIS));
//        rankPanel.setLayout(new BoxLayout(rankPanel, BoxLayout.PAGE_AXIS));
        gamesPanel.setLayout(new GridLayout(10,1));
        playerPanel.setLayout(new GridLayout(10, 1));
        rankPanel.setLayout(new GridLayout(10, 1));
        panel.add(gamesScroller);
        panel.add(playerScroller);
        panel.add(rankScroller);
        panel.add(serverNewGamePanel);
        add(panel);
        pack();
    }

    private void init(){
        setTitle("MainFrame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(frameLocationPoint);
        setLayout(new BorderLayout());
    }

    public JPanel getGamesPanel() {
        return gamesPanel;
    }

    public void setGamesPanel(JPanel gamesPanel) {
        this.gamesPanel = gamesPanel;
    }

    public JPanel getPlayerPanel() {
        return playerPanel;
    }

    public void setPlayerPanel(JPanel playerPanel) {
        this.playerPanel = playerPanel;
    }

    public JPanel getRankPanel() {
        return rankPanel;
    }

    public void setRankPanel(JPanel rankPanel) {
        this.rankPanel = rankPanel;
    }

    public ServerNewGamePanel getServerNewGamePanel() {
        return serverNewGamePanel;
    }
}
