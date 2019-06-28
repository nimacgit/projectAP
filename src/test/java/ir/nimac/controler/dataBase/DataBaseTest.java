package ir.nimac.controler.dataBase;

import ir.nimac.controler.User;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.view.MainFrame;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

public class DataBaseTest {

    DataBase dataBase;

    @Before
    public void setUp() throws Exception {
        dataBase = DataBase.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {

    }

    @Test
    public void saveMap() {
        Map map = new Map();
        map.buildMap();
        map.playerList.clear();
        map.playerList.add(new Player(new Location(0, 0), "nimac.view", 1L, "127.0.0.1", map));
        dataBase.saveMap(map);
    }

    @Test
    public void getAllMap() {
    }
}