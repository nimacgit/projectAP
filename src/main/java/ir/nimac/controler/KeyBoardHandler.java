package ir.nimac.controler;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Bomb;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class KeyBoardHandler {
    static final int keyPressLimit = 5;

    public static void keyHandle(String key, Player newPlayer, Map map) {
        newPlayer.keyPressed++;
        switch (key.toUpperCase()) {
            case "UP":
                if(newPlayer.lastKey != 0 || newPlayer.keyPressed + newPlayer.getSpeed() >= keyPressLimit) {
                    newPlayer.keyPressed = 0;
                    newPlayer.lastKey = 0;
                    newPlayer.dir = 0;
                    newPlayer.move();
                }
                break;
            case "DOWN":
                if(newPlayer.lastKey != 1 || newPlayer.keyPressed + newPlayer.getSpeed() >= keyPressLimit) {
                    newPlayer.keyPressed = 0;
                    newPlayer.lastKey = 1;
                    newPlayer.dir = 2;
                    newPlayer.move();
                }
                break;
            case "RIGHT":
                if(newPlayer.lastKey != 2 || newPlayer.keyPressed + newPlayer.getSpeed() >= keyPressLimit) {
                    newPlayer.keyPressed = 0;
                    newPlayer.lastKey = 2;
                    newPlayer.dir = 1;
                    newPlayer.move();
                }
                break;
            case "LEFT":
                if(newPlayer.lastKey != 3 || newPlayer.keyPressed + newPlayer.getSpeed() >= keyPressLimit) {
                    newPlayer.keyPressed = 0;
                    newPlayer.lastKey = 3;
                    newPlayer.dir = 3;
                    newPlayer.move();
                }
                break;
            case "SPACE":
                if (newPlayer.getBombLeft() > 0 && MapFunctions.canUserGo(newPlayer.location) > 0) {
                    int x = newPlayer.location.getX(), y = newPlayer.location.getY();
                    Bomb newBomb = new Bomb(new Location(x, y), newPlayer.getId(), newPlayer.getBombRadius(), Map.BOMB_TIME_OUT, map);
                    newPlayer.setBombLeft(newPlayer.getBombLeft() - 1);
                    newPlayer.bombList.add(newBomb);
                    map.bombList.add(newBomb);
                }
                break;
            case "B":
                if (newPlayer.isBombControl()) {
                    if (newPlayer.bombList.size() > 0) {
                        newPlayer.bombList.get(0).setRemaining(0L);
                    }
                }
        }
    }
}
