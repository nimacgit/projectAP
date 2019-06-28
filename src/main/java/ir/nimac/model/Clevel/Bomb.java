package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Mover;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;


public class Bomb extends Mover {

    private static final int BOMBPARAMS = 5;
    private int power = 1;
    private Long ownerId;
    private long remaining;

    public Bomb(Location location, Long ownerId, int power, Long remaining, Map map) {
        super(location, map);
        this.power = power;
        this.ownerId = ownerId;
        this.remaining = remaining;
        currentPic = MapFunctions.bombPic.get(0);
    }

    public static Bomb makeInstance(String[] param, int pos, Map map) {
        return new Bomb(new Location(Integer.valueOf(param[pos]),
                Integer.valueOf(param[pos + 1])),
                Long.valueOf(param[pos + 2]),
                Integer.valueOf(param[pos + 4]),
                Long.valueOf(param[pos + 3]),
                map);
    }

    public static int getBOMBPARAMS() {
        return BOMBPARAMS;
    }

    @Override
    public boolean move() {
        return true;
    }

    public Long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }

    public String instanceToString(String sep) {
        return "bomb" + sep
                + location.getX() + sep
                + location.getY() + sep
                + ownerId + sep
                + power + sep
                + getRemaining();
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}
