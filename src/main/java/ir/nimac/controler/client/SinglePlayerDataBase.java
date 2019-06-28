package ir.nimac.controler.client;

import ir.nimac.model.Clevel.*;
import ir.nimac.model.Clevel.powerups.PowerUp;
import ir.nimac.model.map.Map;
import ir.nimac.view.MainFrame;

import java.io.*;
import java.util.Scanner;

public class SinglePlayerDataBase {
    static FileReader inputReader;
    static FileWriter outputWriter;
    static Scanner inp;
    static String tempStr[] = new String[20];

    public static void load(File file, MainFrame frame, Map map) {
        try {
            inputReader = new FileReader(file);
            inp = new Scanner(inputReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        map.setMapDimention(inp.nextInt(), inp.nextInt());
        map.reset();
        String s;
        while (inp.hasNext()) {
            s = inp.next();
            switch (s) {
                case "user":
                    for (int i = 0; i < Player.PLAYERPARAMS; i++)
                        tempStr[i] = inp.next();
                    Player u = Player.makeInstance(tempStr, 0, map);
                    map.playerList.add(u);
                    break;
                case "bomb":
                    for (int i = 0; i < Bomb.getBOMBPARAMS(); i++)
                        tempStr[i] = inp.next();
                    Bomb b = Bomb.makeInstance(tempStr, 0, map);
                    map.bombList.add(b);
                    break;
                case "wall":
                    for (int i = 0; i < Wall.WALLPARAMS; i++)
                        tempStr[i] = inp.next();
                    Wall w = Wall.makeInstance(tempStr, 0, map);
                    map.wallList.add(w);
                    break;
                case "door":
                    for (int i = 0; i < Door.DOORPARAMS; i++)
                        tempStr[i] = inp.next();
                    Door d = Door.makeInstance(tempStr, 0, map);
                    map.doorList.add(d);
                    break;
                case "enemy":
                    for (int i = 0; i < Enemy.ENEMYPARAMS; i++)
                        tempStr[i] = inp.next();
                    Enemy e = EnemyFactory.makeInstance(tempStr, 0, map);
                    map.enemyList.add(e);
                    break;
                case "fire":
                    for (int i = 0; i < Fire.FIREPARAMS; i++)
                        tempStr[i] = inp.next();
                    Fire f = Fire.makeInstance(tempStr, 0, map);
                    map.fireList.add(f);
                    break;
                case "powerUp":
                    for (int i = 0; i < PowerUp.POWERUPPARAMS; i++)
                        tempStr[i] = inp.next();
                    PowerUp p = PowerUp.makeInstance(tempStr, 0, map);
                    map.powerUpList.add(p);
                    break;
            }
        }
        frame.pack();
    }

    static void save(File file, Map map) {

        try {
            outputWriter = new FileWriter(file.getPath() + "\\savegame.nimac.view");
        } catch (
                IOException e1) {
            System.err.println("WTF?");
        }
        try {
            outputWriter.write(map.getMapWidth() + " " + map.getMapHeight() + "\n");
            for (Player u : map.playerList)
                outputWriter.write(u.instanceToString(" ") + "\n");
            for (Bomb b : map.bombList)
                outputWriter.write(b.instanceToString(" ") + "\n");
            for (Wall w : map.wallList)
                outputWriter.write(w.instanceToString(" ") + "\n");
            for (Door d : map.doorList)
                outputWriter.write(d.instanceToString(" ") + "\n");
            for (Enemy e : map.enemyList)
                outputWriter.write(e.instanceToString(" ") + "\n");
            for (Fire f : map.fireList)
                outputWriter.write(f.instanceToString(" ") + "\n");
            for (PowerUp p : map.powerUpList)
                outputWriter.write(p.instanceToString(" ") + "\n");
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
