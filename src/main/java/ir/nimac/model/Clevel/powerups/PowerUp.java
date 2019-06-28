package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Things;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;

public abstract class PowerUp extends Things {
    public static final int POWERUPPARAMS = 5;
    int effectAmount;
    int type;

    public PowerUp(Location location, Map map) {
        super(location, map);
        makeType();
        setVisible(false);
    }

    public PowerUp(Location location, int effectAmount, boolean visible, Map map) {
        super(location, map);
        this.effectAmount = effectAmount;
        this.setVisible(visible);
    }

    public static PowerUp makeInstance(String[] param, int pos, Map map) {
        int x = Integer.valueOf(param[pos]), y = Integer.valueOf(param[pos + 1]), e = Integer.valueOf(param[pos + 3]);
        boolean visible = Boolean.valueOf(param[pos + 4]);
        switch (Integer.valueOf(param[pos + 2])) {
            case 1:
                return new BombRadiusPU(new Location(x, y), e, visible, map);
            case 2:
                return new BombsNumberPU(new Location(x, y), e, visible, map);
            case 3:
                return new ControlBombsPU(new Location(x, y), e, visible, map);
            case 4:
                return new PointsPU(new Location(x, y), e, visible, map);
            case 5:
                return new SpeedPU(new Location(x, y), e, visible, map);
            case 6:
                return new GostPU(new Location(x, y), e, visible, map);
        }
        return null;
    }

    public abstract void effect(Player player);

    public abstract void makeType();

    public String instanceToString(String sep) {
        return "powerUp" + sep
                + location.getX() + sep
                + location.getY() + sep
                + type + sep
                + effectAmount + sep
                + isVisible();
    }

    public int getEffectAmount() {
        return effectAmount;
    }

    public int getType() {
        return type;
    }
}
