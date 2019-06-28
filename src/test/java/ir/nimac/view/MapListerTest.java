package ir.nimac.view;

import ir.nimac.controler.User;
import ir.nimac.model.Windows.MapLister;
import ir.nimac.model.map.Map;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.swing.*;

public class MapListerTest {

    static MapLister mapLister;
    static Map map;

    @BeforeClass
    public static void setUp() throws Exception {
        map = new Map();
        mapLister = new MapLister(map, new JFrame());
    }

    @Test
    public void runTest() throws InterruptedException {
        Thread.sleep(5000);
        System.err.println(map.getGameId());
    }
}