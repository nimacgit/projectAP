package ir.nimac.view;

import ir.nimac.controler.User;
import ir.nimac.controler.client.Client;
import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private static final Point frameLocationPoint = new Point(700, 200);
    protected JPanel panel;
    protected Map map;
    private MapPanel mapPanel;
    private UtilPanel utilPanel;

    public MainFrame(Map map) {
        this.map = map;
        initialFrame();
        panel = (JPanel) getContentPane();
        utilPanel = new UtilPanel(map);
        try {
            this.mapPanel = map.getMapPanel();
            panel.add(mapPanel);
            panel.add(utilPanel, BorderLayout.EAST);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pack();
    }

    private void initialFrame() {
        setTitle("MainFrame");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocation(frameLocationPoint);
        setLayout(new BorderLayout());
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public UtilPanel getUtilPanel() {
        return utilPanel;
    }
}
