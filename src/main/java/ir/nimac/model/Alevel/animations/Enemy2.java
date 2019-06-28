package ir.nimac.model.Alevel.animations;

import ir.nimac.model.Alevel.Animation;
import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Enemy;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class Enemy2 extends Animation {
    int repRandom = 0;

    public Enemy2(int stepDelay, Cell shape, Map map) {
        super(stepDelay, shape, map);
    }

    @Override
    public boolean step() {
        if (((Enemy) thisShape).isDead()) return false;
        if (map.playerList.size() == 0) return true;
        if (repRandom > 0) {
            repRandom--;
            MapFunctions.findEnemyPathRandom((Enemy) thisShape);
            return true;
        }
        Location userLoc = null;
        for (int i = 0; i < map.playerList.size(); i++) {
            if (!map.playerList.get(i).isDead())
                userLoc = map.playerList.get(i).location;
        }
        if (userLoc != null) {
            if (MapFunctions.findEnemyPath((Enemy) thisShape, userLoc)) ((Enemy) thisShape).move();
            else {
                repRandom = 10;
                MapFunctions.findEnemyPathRandom((Enemy) thisShape);
            }
        }
        return true;
    }
}
