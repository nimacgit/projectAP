package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Things;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;


public class Wall extends Things {
    public static final int WALLPARAMS = 2;

    public Wall(Location location, Map map) {
        super(location, map);
        currentPic = MapFunctions.wallPic.get(0);
    }

    public static Wall makeInstance(String[] param, int pos, Map map) {
        return new Wall(new Location(Integer.valueOf(param[pos]),
                Integer.valueOf(param[pos + 1])), map);
    }

    public String instanceToString(String sep) {
        return "wall" + sep
                + location.getX() + sep
                + location.getY();
    }

}
