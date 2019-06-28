package ir.nimac.model.Clevel;

import ir.nimac.model.Alevel.Cell;
import ir.nimac.model.map.Map;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Scanner;

public class EnemyListClassLoader {

    private ArrayList<Class<?>> arrayList = new ArrayList<>();
    private final Object lock = new Object();
    private boolean init = false;
    private Scanner list;


    public void load(Map map) {
        synchronized (lock) {
            if (!init) {
                list = new Scanner(getClass().getClassLoader().getResourceAsStream("enemyName"));
                init = true;
            }
            while (list.hasNext()) {
                try {
                    String className = list.next();
                    Class<?> clazz = Class.forName(className);
                    arrayList.add(clazz);
                } catch (NoClassDefFoundError | ClassNotFoundException e) {
                    System.out.println(e.getMessage() + " " + e.getCause());
                }
            }
            map.setEnemyNumber(arrayList.size() + 4);
        }
    }

    public Class get(int index, Map map) {
        synchronized (lock) {
            load(map);
            if (index < size()) {
                return arrayList.get(index);
            } else {
                return null;
            }
        }
    }

    public int size() {
        synchronized (lock) {
            return arrayList.size();
        }
    }

    public static EnemyReflect getInstance(Class clazz, Cell shape, Map map) {
        try {
            return (EnemyReflect) clazz.getConstructor(Map.class, Cell.class).newInstance(map, shape);
        } catch (NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
