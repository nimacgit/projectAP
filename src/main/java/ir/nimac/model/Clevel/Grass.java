package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Blevel.Things;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class Grass extends Things {

    public Grass(Location location, Map map) {
        super(location, map);
        currentPic = MapFunctions.grassPic.get(0);
    }

}
