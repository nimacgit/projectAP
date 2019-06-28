package ir.nimac.view;

import ir.nimac.controler.Core;
import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapPanel extends JPanel {
    Map map;
    //boolean isKeyPressed = false;
    //int numberOfKeyPress = 0;
    private Dimension preferredDimension = new Dimension(1000, 1000);

    public MapPanel(Map map) {
        this.map = map;
        setLayout(new GridLayout(map.getMapHeight(), map.getMapWidth()));
        preferredDimension = new Dimension(map.getMapWidth() * 30, map.getMapHeight() * 30);
        addKeyListener(new KeyAdapter() {
            @Override
            public synchronized void keyPressed(KeyEvent e) {
                Core.getInstance().doKeyBoard(map, e, map.getMainFrame());
//                if (map.playerList.size() > 0) {
//                    if (!keyPressed ||
//                            (e.getKeyCode() == KeyEvent.VK_O && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) ||
//                            e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0 ||
//                            (KeyEvent.getKeyText(e.getKeyCode())).toUpperCase().equals("SPACE") ||
//                            (KeyEvent.getKeyText(e.getKeyCode())).toUpperCase().equals("B")) {
//                        Core.getInstance().doKeyBoard(map, e, map.getMainFrame());
//                        keyPressed = true;
//                        numberOfKeyPress = 0;
//                    } else {
//                        numberOfKeyPress++;
//                        if (map.getMe().isOnline() || numberOfKeyPress +  map.getMe().getPlayer().getSpeed() >= keyPressLimit) {
//                            Core.getInstance().doKeyBoard(map, e, map.getMainFrame());
//                            numberOfKeyPress = 0;
//                        }
//                    }
//                }
            }

            @Override
            public synchronized void keyReleased(KeyEvent e) {
                Core.getInstance().doKeyBoardReset(map.getMe());
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                requestFocus();
            }
        });
        setPreferredSize(preferredDimension);
        setVisible(true);

    }

    protected synchronized void paintComponent(Graphics G) {
        setLayout(new GridLayout(map.getMapHeight(), map.getMapWidth()));
        repaint();
    }

    synchronized public void reset() {
        synchronized (map.syncObj) {
            this.removeAll();
            map.bombList.clear();
            map.enemyList.clear();
            map.doorList.clear();
            map.wallList.clear();
            //map.playerList.clear();
            map.fireList.clear();
            map.powerUpList.clear();
            setLayout(new GridLayout(map.getMapHeight(), map.getMapWidth()));
            preferredDimension = new Dimension(map.getMapWidth() * 30, map.getMapHeight() * 30);
            setPreferredSize(preferredDimension);
        }
    }

}
