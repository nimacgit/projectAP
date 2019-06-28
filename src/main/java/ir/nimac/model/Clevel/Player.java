package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Mover;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Player extends Mover {

    public static final int PLAYERPARAMS = 14;
    public ArrayList<Bomb> bombList = new ArrayList<>();
    private Long id = 1L;
    private String name = "";
    private int bombLeft = 1;
    private boolean isDead = false;
    private int level = 1;
    private int bombRadius = 1;
    private boolean bombControl = false;
    private Long points = 0L;
    private int speed = 1;
    private Timestamp initDate;
    private boolean isGost = false;
    private String ip;
    private Object speedLock = new Object();
    public int keyPressed = 0;
    public int lastKey = 0;

    public Player(Location location, String name, Long id, String ip, Map map) {
        super(location, map);
        initDate = new Timestamp(System.currentTimeMillis());
        this.name = name;
        this.id = id;
        this.ip = ip;
        currentPic = MapFunctions.userPic.get(0);

    }

    public Player(Location location, Long id, String ip, String name, int bombLeft, boolean isDead, int level,
                  int bombRadius, boolean bombControl, Long points, int speed, boolean isGost, Timestamp timeStamp, Map map) {
        super(location, map);
        this.id = id;
        this.ip = ip;
        this.name = name;
        this.bombLeft = bombLeft;
        this.isDead = isDead;
        this.level = level;
        this.bombRadius = bombRadius;
        this.bombControl = bombControl;
        this.points = points;
        this.speed = speed;
        this.isGost = isGost;
        this.initDate = timeStamp;
        currentPic = MapFunctions.userPic.get(0);
    }

    @Override
    public boolean move() {
        Location nPos = moveInDir();
        if (MapFunctions.canUserGo(nPos) > 0 || (isGost() && MapFunctions.canUserGo(nPos) != -2)) {
            map.playerList.remove(this);
            location.setX(nPos.getX());
            location.setY(nPos.getY());
            map.playerList.add(this);
            MapFunctions.getPowerUp(this);
            MapFunctions.checkEnemyHit(this);
            MapFunctions.checkIsOnDoor(this);
            return true;
        }
        return false;
    }

    public static Player makeInstance(String[] param, int pos, Map map) {
        return new Player(new Location(Integer.valueOf(param[pos]),
                Integer.valueOf(param[pos + 1])),
                Long.valueOf(param[pos + 2]),
                param[pos + 3],
                param[pos + 4],
                Integer.valueOf(param[pos + 5]),
                Boolean.getBoolean(param[pos + 6]),
                Integer.valueOf(param[pos + 7]),
                Integer.valueOf(param[pos + 8]),
                Boolean.valueOf(param[pos + 9]),
                Long.valueOf(param[pos + 10]),
                Integer.valueOf(param[pos + 11]),
                Boolean.valueOf(param[pos + 12]),
                new Timestamp(Long.valueOf(param[pos + 13])), map);
    }

    public String instanceToString(String sep) {
        return "user" + sep
                + location.getX() + sep
                + location.getY() + sep
                + id + sep
                + ip + sep
                + name + sep
                + bombLeft + sep
                + isDead + sep
                + level + sep
                + bombRadius + sep
                + bombControl + sep
                + points + sep
                + speed + sep
                + isGost + sep
                + initDate.getTime();
    }

    public int getSpeed() {
        synchronized (speedLock) {
            return speed;
        }
    }

    public void setSpeed(int val) {
        synchronized (speedLock) {
            speed = val;
        }
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getInitDate() {
        return initDate;
    }

    public void setInitDate(Timestamp date) {
        initDate = date;
    }

    public boolean isGost() {
        return isGost;
    }

    public void setGost(boolean gost) {
        isGost = gost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBombLeft() {
        return bombLeft;
    }

    public void setBombLeft(int bombLeft) {
        this.bombLeft = bombLeft;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBombRadius() {
        return bombRadius;
    }

    public void setBombRadius(int bombRadius) {
        this.bombRadius = bombRadius;
    }

    public boolean isBombControl() {
        return bombControl;
    }

    public void setBombControl(boolean bombControl) {
        this.bombControl = bombControl;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
