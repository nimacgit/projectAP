package ir.nimac.model.Alevel.animations;

import ir.nimac.model.Alevel.Animation;
import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.map.Map;

public class FireAnimation extends Animation {
    private int remaining = 20;

    public FireAnimation(Cell shape, Map map) {
        super(20, shape, map);
    }

    @Override
    public boolean step() {
        remaining--;
        if (remaining <= 0) {
            return false;
        }
        return true;
    }
}
