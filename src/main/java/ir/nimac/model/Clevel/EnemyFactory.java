package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Animation;
import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Alevel.animations.Enemy1;
import ir.nimac.model.Alevel.animations.Enemy2;
import ir.nimac.model.Alevel.animations.Enemy3;
import ir.nimac.model.Alevel.animations.Enemy4;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public abstract class EnemyFactory {

    private static EnemyListClassLoader enemyListClassLoader = new EnemyListClassLoader();

    public static Enemy build(Location location, int type, Map map) {
        Enemy enemy = new Enemy(location, type, map);
        if(type == 1) {
            enemy.setAnimation(new Enemy1(MapFunctions.ENEMYSPEED, enemy, map));
        } else if(type == 2) {
            enemy.setAnimation(new Enemy2(MapFunctions.ENEMYSPEED, enemy, map));
            enemy.setMinLevel(2);
        } else if(type == 3) {
            enemy.setAnimation(new Enemy3(MapFunctions.ENEMYSPEED / 2, enemy, map));
            enemy.setMinLevel(3);
        } else if(type == 4) {
            enemy.setAnimation(new Enemy4(MapFunctions.ENEMYSPEED / 2, enemy, map));
            enemy.setGost(true);
            enemy.setMinLevel(4);
        } else {
            Class enemyClass = enemyListClassLoader.get(type - 5, map);
            EnemyReflect enemyReflect = EnemyListClassLoader.getInstance(enemyClass, enemy, map);
            if (enemyReflect != null) {
                enemy.setAnimation(enemyReflect.animation);
                enemy.setMinLevel(enemyReflect.minLevel);
                enemy.setGost(enemyReflect.isGost);
                enemy.setType(enemyReflect.type);
            }
        }
        return enemy;
    }

    public static Enemy makeInstance(String[] param, int pos, Map map) {
        return EnemyFactory.build(new Location(Integer.valueOf(param[pos + 1]),
                Integer.valueOf(param[pos + 2])), Integer.valueOf(param[pos]), map);
    }

    public static EnemyListClassLoader getEnemyListClassLoader() {
        return enemyListClassLoader;
    }
}
