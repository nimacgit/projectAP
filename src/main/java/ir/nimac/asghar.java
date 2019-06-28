package ir.nimac;

import ir.nimac.model.Alevel.Animation;
import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.Clevel.Enemy;
import ir.nimac.model.Clevel.EnemyReflect;
import ir.nimac.model.map.Map;

public class asghar extends EnemyReflect {


    public asghar(Map map, Cell shape) {

        type = 5;
        minLevel = 0;
        isGost = true;
        step = 10;
        animation = new Animation(step, shape, map) {
            @Override
            public boolean step() {
                ((Enemy)shape).dir = 0;
                ((Enemy)shape).move();
                return true;
            }
        };
    }


}

