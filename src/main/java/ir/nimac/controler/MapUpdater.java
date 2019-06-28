package ir.nimac.controler;


import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Alevel.SynchronizeDataOutPut;
import ir.nimac.model.Clevel.*;
import ir.nimac.model.Clevel.powerups.PowerUp;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;


public class MapUpdater {
    private Map map;

    MapUpdater(Map map) {
        this.map = map;
    }

    public synchronized static void sendMap(SynchronizeDataOutPut out, Map map){
        StringBuilder command = new StringBuilder();
        command.append("dimension").append(Core.SEP).append(map.getMapHeight()).append(Core.SEP)
                .append(map.getMapWidth()).append(Core.SEP);

        for (Bomb b : map.bombList)
            if(b.isVisible()) {
                command.append(b.instanceToString(Core.SEP)).append(Core.SEP);
            }
        for (Door d : map.doorList)
            if(d.isVisible()) {
                command.append(d.instanceToString(Core.SEP)).append(Core.SEP);
            }
        for (PowerUp p : map.powerUpList)
            if(p.isVisible()) {
                command.append(p.instanceToString(Core.SEP)).append(Core.SEP);
            }
        for (Wall w : map.wallList)
            if(w.isVisible()) {
                command.append(w.instanceToString(Core.SEP)).append(Core.SEP);
            }
        for (Fire f : map.fireList)
            if(f.isVisible()) {
                command.append(f.instanceToString(Core.SEP)).append(Core.SEP);
            }
        for (Enemy e : map.enemyList)
            if(e.isVisible()) {
                command.append(e.instanceToString(Core.SEP)).append(Core.SEP);
            }
        for (Player u : map.playerList)
            if(u.isVisible()) {
                command.append(u.instanceToString(Core.SEP)).append(Core.SEP);
            }
        command.append("\n");
        try {
            out.writeBytes(command.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBomb() {
        Date now = new Date();
        for (int i = 0; i < map.bombList.size(); i++) {
            map.bombList.get(i).setRemaining(map.bombList.get(i).getRemaining() - 1);
            //if (now.getTime() - map.bombList.get(i).plantTime.getTime() > map.bombList.get(i).remaining || map.bombList.get(i).runImmediatly) {
            if (map.bombList.get(i).getRemaining() <= 0) {
                for (int j = 0; j < map.playerList.size(); j++)
                    if (map.playerList.get(j).getId().equals(map.bombList.get(i).getOwnerId())) {
                        map.playerList.get(j).setBombLeft(map.playerList.get(j).getBombLeft() + 1);
                        map.playerList.get(j).bombList.remove(map.bombList.get(i));
                    }
                Bomb tempBomb = map.bombList.get(i);
                map.bombList.remove(i);
                runBomb(tempBomb);
                i--;
            }
        }
    }

//    public void updateClient() {
//        for (int i = 0; i < Server.clientList.size(); i++) {
//            ClientHandler client = Server.clientList.get(i);
//            try {
//                sendMap(client.outToServer, map);
//            } catch (Exception e) {
//                Server.clientList.remove(client);
//                e.printStackTrace();
//            }
//        }
//    }

    private int FireCell(Bomb b, Location loc) {
        int res = 0;
        if (MapFunctions.hasRock(loc)) {
            return -1;
        }
        for (int j = 0; res == 0 && j < map.wallList.size(); j++) {
            if (map.wallList.get(j).isIn(loc)) {
                map.wallList.remove(j);
                res = 1;
                break;
            }
        }
        for (int j = 0; res == 0 && j < map.playerList.size(); j++) {
            if (map.playerList.get(j).isIn(loc)) {
                map.playerList.get(j).setDead(true);
                map.playerList.get(j).setVisible(false);
                //map.playerList.remove(j);
                //j--;
            }
        }
        for (int j = 0; res == 0 && j < map.enemyList.size(); j++) {
            if (map.enemyList.get(j).isIn(loc)) {
                if (MapFunctions.getUserById(b.getOwnerId()) != null)
                    MapFunctions.getUserById(b.getOwnerId()).setPoints(MapFunctions.getUserById(b.getOwnerId()).getPoints()
                            + Map.ENEMYPOINTALFA * map.enemyList.get(j).getType());
                map.enemyList.remove(j);
                j--;
            }
        }
        for (int i = 0; i < map.bombList.size(); i++) {
            if (map.bombList.get(i).isIn(loc))
                map.bombList.get(i).setRemaining(0L);
        }
        for (int i = 0; i < map.doorList.size(); i++) {
            if (map.doorList.get(i).isIn(loc))
                map.doorList.get(i).setVisible(true);
        }
        for (int i = 0; i < map.powerUpList.size(); i++) {
            if (map.powerUpList.get(i).isIn(loc))
                map.powerUpList.get(i).setVisible(true);
        }
        map.fireList.add(new Fire(loc, map));
        return res;
    }

    public void updateDoor() {
        if (map.enemyList.size() == 0 && map.doorList.size() > 0) {
            map.doorList.get(0).isOpen = true;
        }
    }

    private void runBomb(Bomb bomb) {
        int y = bomb.location.getY();
        int x = bomb.location.getX();
        int res = 0;
        for (x = bomb.location.getX(); res == 0 && x <= bomb.location.getX() + bomb.getPower(); x++) {
            res = FireCell(bomb, new Location(x, y));
            if (res == 1)
                if (MapFunctions.getUserById(bomb.getOwnerId()) != null)
                    MapFunctions.getUserById(bomb.getOwnerId()).setPoints(MapFunctions.getUserById(bomb.getOwnerId()).getPoints() + Map.WALLPOINT);
        }
        res = 0;
        for (x = bomb.location.getX(); res == 0 && x >= bomb.location.getX() - bomb.getPower(); x--) {
            res = FireCell(bomb, new Location(x, y));
            if (res == 1)
                if (MapFunctions.getUserById(bomb.getOwnerId()) != null)
                    MapFunctions.getUserById(bomb.getOwnerId()).setPoints(MapFunctions.getUserById(bomb.getOwnerId()).getPoints() + Map.WALLPOINT);
        }

        res = 0;
        x = bomb.location.getX();
        for (y = bomb.location.getY(); res == 0 && y <= bomb.location.getY() + bomb.getPower(); y++) {
            res = FireCell(bomb, new Location(x, y));
            if (res == 1)
                if (MapFunctions.getUserById(bomb.getOwnerId()) != null)
                    MapFunctions.getUserById(bomb.getOwnerId()).setPoints(MapFunctions.getUserById(bomb.getOwnerId()).getPoints() + Map.WALLPOINT);
        }

        res = 0;
        for (y = bomb.location.getY(); res == 0 && y >= bomb.location.getY() - bomb.getPower(); y--) {
            res = FireCell(bomb, new Location(x, y));
            if (res == 1)
                if (MapFunctions.getUserById(bomb.getOwnerId()) != null)
                    MapFunctions.getUserById(bomb.getOwnerId()).setPoints(MapFunctions.getUserById(bomb.getOwnerId()).getPoints() + Map.WALLPOINT);
        }
    }

    public void updatePointBoard() {
        map.getMainFrame().getUtilPanel().getBoardPanel().setUser();
    }

    public void updateUser() {
        //TODO
    }

    public void checkDone(){
        boolean flag = false;
        for(int i = 0; i < map.playerList.size(); i++){
            if(!map.playerList.get(i).isDead()) {
                flag = true;
            }
        }
        if(!flag){
            map.setGamePause(true);
        }
    }
}
