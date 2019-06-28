package ir.nimac.model.Windows;

import ir.nimac.controler.User;
import ir.nimac.controler.client.Client;
import ir.nimac.controler.client.SinglePlayer;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.view.GameConfigPanel;
import ir.nimac.view.MainFrame;
import ir.nimac.view.MapListerPanel;

import javax.swing.*;
import java.awt.*;

public class GameConfig {
    private static final Dimension preferredDimension = new Dimension(1000, 600);
    private static final Point frameLocationPoint = new Point(1200, 600);
    GameConfigPanel gameConfigPanel;
    protected Map map;
    JFrame frame;

    public GameConfig() {
        this.map = new Map();
        frame = new JFrame();
        initFrame();
        gameConfigPanel = new GameConfigPanel();
        setActions();
        frame.add(gameConfigPanel);
        frame.pack();
        frame.setVisible(true);
    }


    private void initFrame() {
        //frame.setFont(new Font(null,Font.PLAIN, fontSize));
        frame.setLocation(frameLocationPoint);
        frame.setPreferredSize(preferredDimension);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setActions() {

        gameConfigPanel.getSubmit().addActionListener(e -> {
            if (isValidData()) {
                String userName = gameConfigPanel.getNameField().getText();
                int mWidth = Integer.valueOf(gameConfigPanel.getWidthField().getText());
                int mHeight = Integer.valueOf(gameConfigPanel.getHeightField().getText());
                if (gameConfigPanel.getSingle().isSelected()) {
                    map.setMapDimention(mWidth, mHeight);
                    map.reset();
                    map.buildMap();
                    map.playerList.clear();
                    map.getMe().setPlayer(map.addNewPlayer("nimac.view", "127.0.0.1"));
                    SinglePlayer play = new SinglePlayer(userName, map);
                    map.getMainFrame().setVisible(true);
                    map.setGamePause(false);
                } else {
                    String ip = gameConfigPanel.getIpField().getText();
                    int port = Integer.valueOf(gameConfigPanel.getPortField().getText());
                    Client client = new Client(userName, ip, port, mWidth, mHeight, map, 0);
                    map.getMainFrame().setVisible(true);
                }
                frame.setVisible(false);
            }
        });

        gameConfigPanel.getLoadButton().addActionListener(e -> {
                String userName = gameConfigPanel.getNameField().getText();
                if (gameConfigPanel.getSingle().isSelected()) {
                    SinglePlayer play = new SinglePlayer("new", map);
                    map.getMainFrame().setVisible(false);
                    MapLister mapLister = new MapLister(map, map.getMainFrame());
                } else {
                    try {
                        String ip = gameConfigPanel.getIpField().getText();
                        int port = Integer.valueOf(gameConfigPanel.getPortField().getText());
                        Client client = new Client(userName, ip, port, 0, 0, map, 1);
                        map.getMainFrame().setVisible(false);
                    } catch (Exception e1){
                        System.err.println("malformat");
                    }
                }
                frame.setVisible(false);
        });
    }


    private boolean isValidData() {
        if (gameConfigPanel.getMulti().isSelected() || gameConfigPanel.getSingle().isSelected()) {
            if (!gameConfigPanel.getNameField().getText().equals("")) {
                try {
                    int h = Integer.valueOf(gameConfigPanel.getHeightField().getText()),
                            w = Integer.valueOf(gameConfigPanel.getWidthField().getText());
                    if (h * w > 0)
                        return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

}
