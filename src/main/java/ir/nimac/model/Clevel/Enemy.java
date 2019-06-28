package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Alevel.animations.Enemy1;
import ir.nimac.model.Alevel.animations.Enemy2;
import ir.nimac.model.Alevel.animations.Enemy3;
import ir.nimac.model.Alevel.animations.Enemy4;
import ir.nimac.model.Blevel.Mover;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

import java.util.ArrayList;

public class Enemy extends Mover {
    public static final int ENEMYPARAMS = 3;
    private int type;
    private boolean isDead = false;
    private boolean isGost = false;
    private int minLevel = 1;



    Enemy(Location location, int type, Map map) {
        super(location, map);
        currentPic = MapFunctions.enemyPic.get(0);
        this.type = type;
    }

    public static int getENEMYPARAMS() {
        return ENEMYPARAMS;
    }

    @Override
    public boolean move() {
        Location nPos = moveInDir();
        if (MapFunctions.canEnemyGo(nPos, type) > 0) {
            map.enemyList.remove(this);
            location.setX(nPos.getX());
            location.setY(nPos.getY());
            map.enemyList.add(this);
            MapFunctions.checkUserHit(location);
            return true;
        }
        return false;
    }

    public String instanceToString(String sep) {
        return "enemy" + sep
                + type + sep
                + location.getX() + sep
                + location.getY();
    }

    public int getType() {
        return type;
    }

    public boolean isDead() {
        return isDead;
    }

    public int getMinLevel() {
        return minLevel;
    }

    public boolean isGost() {
        return isGost;
    }

    void setMinLevel(int minLevel) {
        this.minLevel = minLevel;
    }

    void setGost(boolean gost) {
        isGost = gost;
    }

    public void setType(int type) {
        this.type = type;
    }
}
