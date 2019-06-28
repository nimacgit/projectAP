package ir.nimac.model.map;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Mover;
import ir.nimac.model.Clevel.*;
import ir.nimac.model.Clevel.powerups.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class MapFunctions {
    public static final int ENEMYSPEED = 800;
    private static final String grassDir = "src/main/resources/icons/grass.jpg";
    private static final String bombDir = "src/main/resources/icons/bomb2.png";
    private static final String enemyDir = "src/main/resources/icons/enemy.png";
    private static final String userDir = "src/main/resources/icons/user3.png";
    private static final String wallDir = "src/main/resources/icons/wall.jpg";
    private static final String rockDir = "src/main/resources/icons/rock3.png";
    private static final String doorDir = "src/main/resources/icons/door.jpg";
    private static final String fireDir = "src/main/resources/icons/fire2.png";
    private static final String bombRadiusDir = "src/main/resources/icons/BombRadius.png";
    private static final String bombNumberDir = "src/main/resources/icons/BombNumber.png";
    private static final String controlBombDir = "src/main/resources/icons/ControlBomb.png";
    private static final String pointsDir = "src/main/resources/icons/Points.png";
    private static final String speedDir = "src/main/resources/icons/Speed.png";
    private static final String bombRadiusPDir = "src/main/resources/icons/BombRadiusP.png";
    private static final String bombNumberPDir = "src/main/resources/icons/BombNumberP.png";
    private static final String controlBombPDir = "src/main/resources/icons/ControlBombP.png";
    private static final String pointsPDir = "src/main/resources/icons/PointsP.png";
    private static final String speedPDir = "src/main/resources/icons/SpeedP.png";
    private static final String gostPDir = "src/main/resources/icons/GostP.png";
    public static ArrayList<Image> grassPic = new ArrayList<>();
    public static ArrayList<Image> bombPic = new ArrayList<>();
    public static ArrayList<Image> enemyPic = new ArrayList<>();
    public static ArrayList<Image> userPic = new ArrayList<>();
    public static ArrayList<Image> wallPic = new ArrayList<>();
    public static ArrayList<Image> rockPic = new ArrayList<>();
    public static ArrayList<Image> doorPic = new ArrayList<>();
    public static ArrayList<Image> firePic = new ArrayList<>();
    public static ArrayList<Image> powerUpPic = new ArrayList<>();
    public static Random rand = new Random();
    public static Map map;

    public MapFunctions(Map map) {
        MapFunctions.map = map;
    }

    static void loadImages() throws Exception {
        grassPic.add(ImageIO.read(new File(grassDir)));
        bombPic.add(ImageIO.read(new File(bombDir)));
        enemyPic.add(ImageIO.read(new File(enemyDir)));
        userPic.add(ImageIO.read(new File(userDir)));
        wallPic.add(ImageIO.read(new File(wallDir)));
        rockPic.add(ImageIO.read(new File(rockDir)));
        doorPic.add(ImageIO.read(new File(doorDir)));
        firePic.add(ImageIO.read(new File(fireDir)));
        powerUpPic.add(ImageIO.read(new File(bombRadiusDir)));
        powerUpPic.add(ImageIO.read(new File(bombNumberDir)));
        powerUpPic.add(ImageIO.read(new File(controlBombDir)));
        powerUpPic.add(ImageIO.read(new File(pointsDir)));
        powerUpPic.add(ImageIO.read(new File(speedDir)));
        powerUpPic.add(ImageIO.read(new File(bombRadiusPDir)));
        powerUpPic.add(ImageIO.read(new File(bombNumberPDir)));
        powerUpPic.add(ImageIO.read(new File(controlBombPDir)));
        powerUpPic.add(ImageIO.read(new File(pointsPDir)));
        powerUpPic.add(ImageIO.read(new File(speedPDir)));
        powerUpPic.add(ImageIO.read(new File(gostPDir)));
    }

    private static boolean hasWall(int x, int y) {
        for (int i = 0; i < map.wallList.size(); i++) {
            if (map.wallList.get(i).isIn(new Location(x, y))) return true;
        }
        return false;
    }

    public static void checkIsOnDoor(Player player) {
        if (map.doorList.get(0).isOpen && map.doorList.get(0).isIn(player.location))
            map.levelUp();
    }

    public static boolean hasRock(Location loc) {
        return loc.getY() == 0
                || loc.getY() == map.getMapHeight() - 1
                || loc.getX() == 0
                || loc.getX() == map.getMapWidth() - 1
                || (loc.getX() % 2 == 1 && loc.getY() % 2 == 1);
    }

    public static boolean hasUser(int x, int y) {
        for (int i = 0; i < map.playerList.size(); i++) {
            if (map.playerList.get(i).isIn(new Location(x, y))) return true;
        }
        return false;
    }

    public static boolean hasEnemy(int x, int y) {
        for (int i = 0; i < map.enemyList.size(); i++) {
            if (map.enemyList.get(i).isIn(new Location(x, y))) return true;
        }
        return false;
    }

    private static boolean canAddUserCheckEntity(int x, int y) {
        return (hasWall(x, y) ||
                hasRock(new Location(x, y)) ||
                hasUser(x, y) ||
                hasEnemy(x, y));
    }

    private static boolean canAddUser(int x, int y) {
        if (!hasWall(x, y)) {
            boolean can = true;
            for (int i = 0; i < 3; i++) {
                if (canAddUserCheckEntity(x + i, y)) {
                    can = false;
                }
            }
            if (can) {
                return true;
            }
            can = true;
            for (int i = 0; i < 3; i++) {
                if (canAddUserCheckEntity(x - i, y)) {
                    can = false;
                }
            }
            if (can) {
                return true;
            }

            can = true;
            for (int i = 0; i < 3; i++) {
                if (canAddUserCheckEntity(x, y + i)) {
                    can = false;
                }
            }
            if (can) return true;
            can = true;
            for (int i = 0; i < 3; i++) {
                if (canAddUserCheckEntity(x, y - i)) {
                    can = false;
                }
            }
            return can;
        }
        return false;
    }

    public static synchronized Location getNewUserLoc() {
        while (true) {
            int x = rand.nextInt(map.getMapWidth());
            int y = rand.nextInt(map.getMapHeight());
            if (canAddUser(x, y)) {
                return new Location(x, y);
            }
        }
    }

    public static synchronized int canUserGo(Location loc) {
        for (Bomb bomb : map.bombList) {
            if (bomb.isIn(loc)) {
                return -2;
            }
        }
        int res = 1;
        for (Wall wall : map.wallList) {
            if (wall.isIn(loc)) return -1;
        }
        if (hasRock(loc)) return -3;
        return res;
    }

    public static synchronized int canEnemyGo(Location loc, int type) {

        int res = 1;
        for (Wall wall : map.wallList) {
            if (wall.isIn(loc) && type < 4) return -1;
        }
        if (hasRock(loc) && type < 4) return -3;
        for (Bomb bomb : map.bombList) {
            if (bomb.isIn(loc)) {
                return -2;
            }
        }
        return res;
    }

    public static void getPowerUp(Player player) {
        for (int i = 0; i < map.powerUpList.size(); i++) {
            if (map.powerUpList.get(i).isIn(player.location) && map.powerUpList.get(i).isVisible()) {
                map.powerUpList.get(i).effect(player);
                map.powerUpList.remove(i);
                i--;
            }
        }
    }

    public static Player getUserById(Long id) {
        for (int i = 0; i < map.playerList.size(); i++)
            if (map.playerList.get(i).getId().equals(id)) return map.playerList.get(i);
        return null;
    }

    public static void findEnemyPathRandom(Mover mv) {
        ArrayList<Integer> tempArr = new ArrayList<Integer>();
        tempArr.add(0);
        tempArr.add(1);
        tempArr.add(2);
        tempArr.add(3);
        mv.dir = tempArr.remove(MapFunctions.rand.nextInt(tempArr.size()));
        while (!mv.move() && tempArr.size() > 0)
            mv.dir = tempArr.remove(MapFunctions.rand.nextInt(tempArr.size()));
    }

    public static boolean findEnemyPath(Enemy enemy, Location loc) {
        int dx = loc.getX() - enemy.location.getX(), dy = loc.getY() - enemy.location.getY();
        if (dx != 0) {
            if (dx > 0) dx = 1;
            else dx = -1;
        }
        if (dy != 0) {
            if (dy > 0) dy = 1;
            else dy = -1;
        }

        if (dx == 0) {
            if (canEnemyGo(
                    new Location(enemy.location.getX(), enemy.location.getY() + dy), enemy.getType())
                    >= 0) enemy.dir = 1 + dy;
            else return false;
        }
        if (dy == 0) {
            if (canEnemyGo(
                    new Location(enemy.location.getX() + dx, enemy.location.getY()), enemy.getType())
                    >= 0) enemy.dir = 2 - dx;
            else return false;
        }
        if (MapFunctions.rand.nextInt(2) == 0) {
            if (canEnemyGo(
                    new Location(enemy.location.getX() + dx, enemy.location.getY()), enemy.getType())
                    >= 0) enemy.dir = 2 - dx;
            else if (canEnemyGo(
                    new Location(enemy.location.getX(), enemy.location.getY() + dy), enemy.getType())
                    >= 0) enemy.dir = 1 + dy;
            else return false;
        } else {
            if (canEnemyGo(
                    new Location(enemy.location.getX(), enemy.location.getY() + dy), enemy.getType())
                    >= 0) enemy.dir = 1 + dy;
            else if (canEnemyGo(
                    new Location(enemy.location.getX() + dx, enemy.location.getY()), enemy.getType())
                    >= 0) enemy.dir = 2 - dx;
            else return false;
        }
        return true;
    }

    public static void checkUserHit(Location loc) {
        for (int i = 0; i < map.playerList.size(); i++)
            if (map.playerList.get(i).location.getX() == loc.getX()
                    && map.playerList.get(i).location.getY() == loc.getY()) {
                map.playerList.get(i).setDead(true);
                map.playerList.get(i).setVisible(false);
                //map.playerList.remove(i);
                //i--;
            }
    }

    public static void checkEnemyHit(Player player) {
        for (int i = 0; i < map.enemyList.size(); i++) {
            if (map.enemyList.get(i).location.getX() == player.location.getX()
                    && map.enemyList.get(i).location.getY() == player.location.getY()) {
                player.setDead(true);
                player.setVisible(false);
                //map.playerList.remove(player);
                return;
            }
        }
    }

    void buildRockGrass() {
        for (int i = 0; i < map.getMapHeight(); i++) {
            for (int j = 0; j < map.getMapWidth(); j++) {
                if ((j % 2 == 1 && i % 2 == 1)
                        || j == 0
                        || j == map.getMapWidth() - 1
                        || i == map.getMapHeight() - 1
                        || i == 0) {
                    map.blocks[i][j].add(new Rock(new Location(j, i), map));
                } else {
                    map.blocks[i][j].add(new Grass(new Location(j, i), map));
                }
            }
        }
    }

    void buildWall() {
        for (int i = 0; i < map.getMapHeight(); i++) {
            for (int j = 0; j < map.getMapWidth(); j++) {
                if (!hasRock(new Location(j, i))) {
                    if (rand.nextInt(3) < 1) {
                        map.wallList.add(new Wall(new Location(j, i), map));
                    }
                }
            }
        }
    }

    void buildPowerUp() {
        for (int i = 0; i < map.wallList.size(); i++) {
            if (rand.nextInt(6) >= 2) {
                switch (rand.nextInt(7)) {
                    case 1:
                        map.powerUpList.add(new BombRadiusPU(map.wallList.get(i).location, map));
                        break;
                    case 2:
                        map.powerUpList.add(new BombsNumberPU(map.wallList.get(i).location, map));
                        break;
                    case 3:
                        map.powerUpList.add(new ControlBombsPU(map.wallList.get(i).location, map));
                        break;
                    case 4:
                        map.powerUpList.add(new PointsPU(map.wallList.get(i).location, map));
                        break;
                    case 5:
                        map.powerUpList.add(new SpeedPU(map.wallList.get(i).location, map));
                        break;
                    case 6:
                        map.powerUpList.add(new GostPU(map.wallList.get(i).location, map));
                        break;

                }
            }
        }
    }

    void buildDoor() {
        while (true) {
            int k = rand.nextInt(map.wallList.size() - 1);
            boolean has = false;
            for (int i = 0; i < map.powerUpList.size(); i++) {
                if (map.powerUpList.get(i).isIn(map.wallList.get(k).location)) {
                    has = true;
                }
            }
            if (!has) {
                map.doorList.add(new Door(map.wallList.get(k).location, map));
                return;
            }
        }
    }

    void buildEnemy() {
        EnemyFactory.getEnemyListClassLoader().load(map);
        Location pos;
        for (int i = 0; i < map.getEnemyNumber(); i++) {
            pos = getNewUserLoc();
            int type = MapFunctions.rand.nextInt(map.getNumberOfEnemyTypes()) + 1;
            Enemy enemy = EnemyFactory.build(pos, type, map);
            if (map.getLevel() >= enemy.getMinLevel()) {
                map.enemyList.add(enemy);
            }
            else {
                i--;
            }
        }
    }
}
