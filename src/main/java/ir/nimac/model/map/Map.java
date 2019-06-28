package ir.nimac.model.map;

import ir.nimac.controler.User;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Alevel.SyncListMap;
import ir.nimac.model.Clevel.*;
import ir.nimac.model.Clevel.powerups.PowerUp;
import ir.nimac.view.BlockPanel;
import ir.nimac.view.MainFrame;
import ir.nimac.view.MapPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Map {


    public static final int MAXHEIGTH = 100;
    public static final int MAXWIDTH = 100;
    public static final int WALLPOINT = 10;
    public static final int ENEMYPOINTALFA = 20;
    public static final long BOMB_TIME_OUT = 50;
    public SyncListMap<Player> playerList = new SyncListMap<>(this);
    public SyncListMap<Bomb> bombList = new SyncListMap<>(this);
    public SyncListMap<Enemy> enemyList = new SyncListMap<>(this);
    public SyncListMap<Wall> wallList = new SyncListMap<>(this);
    public SyncListMap<Door> doorList = new SyncListMap<>(this);
    public SyncListMap<Fire> fireList = new SyncListMap<>(this);
    public SyncListMap<PowerUp> powerUpList = new SyncListMap<>(this);
    public BlockPanel[][] blocks;
    private MapPanel mapPanel;
    private Long gameId = -1L;
    private int numberOfEnemyTypes = 5;
    private int enemyNumber = 10;
    private int mapWidth = 50;
    private int mapHeight = 50;
    //public int mapShowWidth = 10;
    //public int mapShowHeigth = 10;
    private Dimension mapDimention = new Dimension(mapWidth, mapHeight);
    //public Dimension mapShowDimention = new Dimension(mapShowWidth, mapShowHeigth);
    private MapFunctions mapFunctions;
    private boolean isGamePause = true;
    private int level = 1;
    private MainFrame mainFrame;
    private User me;
    private int maxUser = 64;
    public final Object syncObj = new Object();

    public Map() {
        gameId = System.currentTimeMillis();
        blocks = new BlockPanel[MAXHEIGTH][MAXWIDTH];
        try {
            mapFunctions = new MapFunctions(this);
            MapFunctions.loadImages();
            mapPanel = new MapPanel(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                blocks[i][j] = new BlockPanel();
                mapPanel.add(blocks[i][j]);
            }
        }
        mainFrame = new MainFrame(this);
        me = new User();
    }


    synchronized public void buildMap() {
        mapPanel.reset();
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                blocks[i][j] = new BlockPanel();
                mapPanel.add(blocks[i][j]);
            }
        }
        mapFunctions.buildRockGrass();
        mapFunctions.buildWall();
        mapFunctions.buildPowerUp();
        mapFunctions.buildDoor();
        enemyNumber = Math.min(mapHeight / 2, mapWidth / 2);
        enemyNumber += (5 * (level - 4) * enemyNumber) / 100;
        mapFunctions.buildEnemy();
    }


    synchronized public void reset() {
        synchronized (syncObj) {
            mapPanel.reset();
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    blocks[i][j] = new BlockPanel();
                    mapPanel.add(blocks[i][j]);
                }
            }
            mapFunctions.buildRockGrass();
        }
    }


    public void levelUp() {
        level++;
        for (Player u : playerList) {
            u.setInitDate(new Timestamp(System.currentTimeMillis()));
        }
        buildMap();
        for (int i = 0; i < playerList.size(); i++) {
            Player u = playerList.get(i);
            u.location = MapFunctions.getNewUserLoc();
            playerList.remove(u);
            playerList.add(u);
        }
    }

    public Player addNewPlayer(String name, String ip) {
        Location location = MapFunctions.getNewUserLoc();
        Player newPlayer = new Player(location, name, System.currentTimeMillis(), ip, this);
        //TODO mybe shit
        //me.setPlayer(newPlayer);
        playerList.add(newPlayer);
        return newPlayer;
    }

    public MapPanel getMapPanel() {
        return mapPanel;
    }

    public void setMapPanel(MapPanel mapPanel) {
        this.mapPanel = mapPanel;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumberOfEnemyTypes() {
        return numberOfEnemyTypes;
    }

    public void setNumberOfEnemyTypes(int numberOfEnemyTypes) {
        this.numberOfEnemyTypes = numberOfEnemyTypes;
    }

    public boolean isGamePause() {
        return isGamePause;
    }

    public void setGamePause(boolean gamePause) {
        isGamePause = gamePause;
    }

    public BlockPanel[][] getBlocks() {
        return blocks;
    }

    public int getEnemyNumber() {
        return enemyNumber;
    }

    public void setEnemyNumber(int enemyNumber) {
        this.enemyNumber = enemyNumber;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public Dimension getMapDimention() {
        return mapDimention;
    }

    public void setMapDimention(Dimension mapDimention) {
        this.mapDimention = mapDimention;
    }

    public void setMapDimention(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapDimention = new Dimension(mapWidth, mapHeight);
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public User getMe() {
        return me;
    }

    public void setMe(User me) {
        this.me = me;
    }

    public int getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(int maxUser) {
        this.maxUser = maxUser;
    }
}
