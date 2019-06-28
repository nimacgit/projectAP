package ir.nimac.model.map;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Enemy;
import ir.nimac.model.Clevel.EnemyFactory;
import ir.nimac.model.Clevel.EnemyListClassLoader;
import org.junit.Test;

public class EnemyListClassLoaderTest {

    @Test
    public void get() throws ClassNotFoundException {
//        Class<?> clazz = Class.forName("testEnemy");
//        System.out.println(clazz.getSimpleName());
        EnemyListClassLoader enemyListClassLoader = new EnemyListClassLoader();
        Enemy enemy = EnemyFactory.build(new Location(10,10), 5, new Map());
        System.err.println(enemy.getMinLevel());
        System.err.println(enemy.getType());
        System.err.println(enemy.step());
    }
}