package ir.nimac.model.Blevel;

import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.map.Map;

abstract public class Mover extends Cell {

    public int dir = 0;
    public int speed = 1;


    public Mover(Location location, Map map) {
        super(location, map);
    }

    public abstract boolean move();

    public Location moveInDir() {
        Location nPos = new Location(location.getX(), location.getY());
        switch (dir) {
            case 0:
                nPos.setY((location.getY() + map.getMapHeight() - speed) % (map.getMapHeight()));
                break;
            case 1:
                nPos.setX((location.getX() + map.getMapWidth() + speed) % (map.getMapWidth()));
                break;
            case 2:
                nPos.setY((location.getY() + map.getMapHeight() + speed) % (map.getMapHeight()));
                break;
            case 3:
                nPos.setX((location.getX() + map.getMapWidth() - speed) % (map.getMapWidth()));
                break;
        }
        return nPos;
    }


}
