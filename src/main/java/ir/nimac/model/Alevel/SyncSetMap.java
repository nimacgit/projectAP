package ir.nimac.model.Alevel;

import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;

import java.util.Comparator;

public class SyncSetMap<E extends Player> extends SyncListMap<E> {

    public Comparator<Player> cmp = new Comparator<Player>() {
        @Override
        public int compare(Player o1, Player o2) {
            if (o1.getPoints().equals(o2.getPoints()))
                return 0;
            if (o1.getPoints() > o2.getPoints())
                return 1;
            return -1;
        }
    };

    public SyncSetMap(Map map) {
        super(map);
    }

    @Override
    public synchronized boolean add(E cell) {
        synchronized (map.syncObj) {
            boolean t = super.add(cell);
            map.blocks[cell.location.getY()][cell.location.getX()].add(cell);
            sort(cmp);
            return t;
        }
    }

}
