package ir.nimac.model.Alevel;


import ir.nimac.model.map.Map;

import java.util.ArrayList;

public class SyncListMap<E extends Cell> extends ArrayList<E> {
    Map map;

    public SyncListMap(Map map) {
        super();
        this.map = map;
    }

    @Override
    public synchronized E get(int index) {
        synchronized (map.syncObj) {
            return super.get(index);
        }
    }

    @Override
    public synchronized boolean add(E cell) {
        synchronized (map.syncObj) {
            boolean t = super.add(cell);
            map.blocks[cell.location.getY()][cell.location.getX()].add(cell);
            return t;
        }
    }

    @Override
    public synchronized boolean remove(Object o) {
        synchronized (map.syncObj) {
            Cell cell = (Cell) o;
            boolean t = super.remove(o);
            map.blocks[cell.location.getY()][cell.location.getX()].remove(cell);
            return t;
        }
    }

    @Override
    public synchronized E remove(int index) {
        synchronized (map.syncObj) {
            E cell = super.remove(index);
            map.blocks[cell.location.getY()][cell.location.getX()].remove(cell);
            return cell;
        }
    }

    @Override
    public synchronized void clear() {
        synchronized (map.syncObj) {
            while (size() > 0) {
                remove(0);
            }
        }
    }
}
