package ir.nimac.controler;

import ir.nimac.Logger;
import ir.nimac.model.Clevel.Enemy;
import ir.nimac.model.map.Map;
import ir.nimac.view.MainFrame;


public class AnimationThread extends Thread{

    Map map;
    private MapUpdater mapUpdater;
    private int waitTime = 100;

    public AnimationThread(Map map) {
        this.map = map;
        mapUpdater = new MapUpdater(map);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(waitTime);
                if (!map.isGamePause()) {
                    jobs();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e1) {
                System.err.println("WTF in AnimationThread?");
                Logger.getInstance().debugLog(e1.getMessage());
            }

        }
    }

    private synchronized void jobs() {
        mapUpdater.updateBomb();
        mapUpdater.updateDoor();
        mapUpdater.updateUser();
        for (int i = 0; i < map.fireList.size(); i++) {
            if (!map.fireList.get(i).animation.animate()) {
                map.fireList.remove(i);
                i--;
            }
        }
        Enemy enemy;
        for (int i = 0; i < map.enemyList.size(); i++) {
            enemy = map.enemyList.get(i);
            if (!enemy.animation.animate()) {
                map.enemyList.remove(i);
                i--;
            }
        }
        mapUpdater.updatePointBoard();
        mapUpdater.checkDone();
        map.getMainFrame().repaint();
        map.getMainFrame().revalidate();
    }

    public Map getMap() {
        return map;
    }
}
