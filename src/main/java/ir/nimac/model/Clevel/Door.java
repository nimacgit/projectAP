package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Things;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class Door extends Things {
    public static final int DOORPARAMS = 2;
    public boolean isOpen = false;

    public Door(Location location, Map map) {
        super(location, map);
        setVisible(false);
        currentPic = MapFunctions.doorPic.get(0);
    }

    public static Door makeInstance(String[] param, int pos, Map map) {
        return new Door(new Location(Integer.valueOf(param[pos]),
                Integer.valueOf(param[pos + 1])), map);
    }

    public String instanceToString(String sep) {
        return "door" + sep
                + location.getX() + sep
                + location.getY() + sep
                + isVisible();
    }
}

