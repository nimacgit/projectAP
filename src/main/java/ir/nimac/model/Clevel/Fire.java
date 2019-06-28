package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Alevel.animations.FireAnimation;
import ir.nimac.model.Blevel.Things;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class Fire extends Things {
    public static final int FIREPARAMS = 2;

    public Fire(Location location, Map map) {
        super(location, map);
        currentPic = MapFunctions.firePic.get(0);
        this.setAnimation(new FireAnimation(this, map));
    }

    public static Fire makeInstance(String[] param, int pos, Map map) {
        return new Fire(new Location(Integer.valueOf(param[pos]),
                Integer.valueOf(param[pos + 1])), map);
    }

    public String instanceToString(String sep) {
        return "fire" + sep
                + location.getX() + sep
                + location.getY();
    }
}

