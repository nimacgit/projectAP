package ir.nimac.model.Windows;

import ir.nimac.controler.User;
import ir.nimac.controler.dataBase.DataBase;
import ir.nimac.model.map.Map;
import ir.nimac.view.MapListerPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MapLister {
    private static final Point frameLocationPoint = new Point(700, 200);
    private static final Dimension preferredDimension = new Dimension(1000, 600);
    JFrame frame;
    MapListerPanel mapListerPanel;
    Map map;

    public MapLister(Map map, JFrame frameToTrue) {
        this.map = map;
        frame = new JFrame();
        initialFrame();
        ArrayList<Long> mapsId = null;
        mapsId = (ArrayList<Long>) DataBase.getInstance().getAllMaps();
        mapListerPanel = new MapListerPanel(map, mapsId, new MapListerButtonAction(map, frame, frameToTrue));
        frame.add(mapListerPanel);
        frame.pack();
        frame.setVisible(true);
    }
    public MapLister(Map map, JFrame frameToTrue, ArrayList<Long> mapsId) {
        this.map = map;
        frame = new JFrame();
        initialFrame();
        mapListerPanel = new MapListerPanel(this.map, mapsId, new MapListerButtonAction(map, frame, frameToTrue));
        frame.add(mapListerPanel);
        frame.pack();
        frame.setVisible(true);
    }


    private void initialFrame() {
        frame.setTitle("MapLister");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(frameLocationPoint);
        frame.setPreferredSize(preferredDimension);
        frame.setLayout(new GridLayout(1, 1));
    }


}
