package ir.nimac.model.Blevel;

import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.map.Map;

abstract public class Things extends Cell {
    public Things(Location location, Map map) {
        super(location, map);
    }

}
