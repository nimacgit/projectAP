package ir.nimac.model.Alevel.animations;

import ir.nimac.model.Alevel.Animation;
import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.Clevel.Enemy;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class Enemy1 extends Animation {

    public Enemy1(int stepDelay, Cell shape, Map map) {
        super(stepDelay, shape, map);
    }

    @Override
    public boolean step() {
        if (((Enemy) thisShape).isDead())
            return false;
        MapFunctions.findEnemyPathRandom((Enemy) thisShape);
        return true;
    }
}
