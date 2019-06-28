package ir.nimac.controler.client;

import ir.nimac.controler.Core;
import ir.nimac.controler.User;
import ir.nimac.model.Alevel.SynchronizeDataOutPut;
import ir.nimac.model.Clevel.*;
import ir.nimac.model.Clevel.powerups.PowerUp;
import ir.nimac.model.Windows.MapLister;
import ir.nimac.model.map.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ServerHandler implements Runnable {

    private static final int PORT = 2020;
    SynchronizeDataOutPut outToServer;
    private Scanner inFromServer;
    private String name;
    private int id;
    private Client client;
    private static final int WAIT_TIME  = 100;
    private Map map;
    private Socket clientSocket = null;
    private Thread getUpdates;

    ServerHandler(String name, String host, int port, int width, int height, Client client,  Map map, int mod) {
        this.map = map;
        this.name = name;
        this.client = client;

        try {
            clientSocket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("cant connet to server");
        }
        try {
            if (clientSocket != null) {
                outToServer = new SynchronizeDataOutPut(clientSocket.getOutputStream());
            }
        } catch (Exception e) {
            System.err.println("cant send to server");
        }
        try {
            if (clientSocket != null) {
                inFromServer = new Scanner(clientSocket.getInputStream());
            }
        } catch (Exception e) {
            System.err.println("cant recive from servver");
        }
        outToServer.write("setname " + name + "\n");
        if(mod == 0){
            outToServer.write("newgame " + width + " " + height + "\n");
            map.setMapDimention(width, height);
            map.reset();
        } else {
            new MapLister(map, map.getMainFrame(), getAllMapsId());
        }
    }

    public void sendMessage(String text) {
        outToServer.write(text);
    }

    @Override
    public void run() {
        String command;
        map.reset();
        getUpdates = new Thread(() -> {
            Long lastGet = System.currentTimeMillis();
            while(true) {
                if (System.currentTimeMillis() - lastGet > WAIT_TIME) {
                    lastGet = System.currentTimeMillis();
                    outToServer.write("getmap\n");
                    try {
                        outToServer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(WAIT_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        getUpdates.start();
        while (!clientSocket.isClosed()) {
            try {

                command = inFromServer.next();
                if(command.equals("message")){
                    String text = inFromServer.next();
                    System.err.println(text);
                    map.getMainFrame().getUtilPanel().getChatBox().write(Core.readMessageHelper(text));
                } else if(command.equals("getmap")) {
                    command = inFromServer.next();

                    updateMapFromServer(command);
                    map.getMainFrame().getUtilPanel().getBoardPanel().setUser();
                    map.getMainFrame().repaint();
                    map.getMainFrame().revalidate();
                } else if(command.equals("setDimension")){
                    map.setMapDimention(Integer.valueOf(inFromServer.next()),
                            Integer.valueOf(inFromServer.nextInt()));
                    map.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private synchronized void updateMapFromServer(String command) {
        String[] slices = command.split(Core.SEP);
        map.playerList.clear();
        map.bombList.clear();
        map.enemyList.clear();
        map.wallList.clear();
        map.doorList.clear();
        map.fireList.clear();
        map.powerUpList.clear();
        for (int i = 0; i < slices.length; i++) {

            switch (slices[i]) {
                case "dimension":
                    i += 2;
                    break;
                case "user":
                    map.playerList.add(Player.makeInstance(slices, i + 1, map));
                    i += Player.PLAYERPARAMS;
                    break;
                case "wall":
                    map.wallList.add(Wall.makeInstance(slices, i + 1, map));
                    i += Wall.WALLPARAMS;
                    break;
                case "enemy":
                    map.enemyList.add(EnemyFactory.makeInstance(slices, i + 1, map));
                    i += Enemy.ENEMYPARAMS;
                    break;
                case "bomb":
                    map.bombList.add(Bomb.makeInstance(slices, i + 1, map));
                    i += Bomb.getBOMBPARAMS();
                    break;
                case "door":
                    map.doorList.add(Door.makeInstance(slices, i + 1, map));
                    i += Door.DOORPARAMS;
                    break;
                case "fire":
                    map.fireList.add(Fire.makeInstance(slices, i + 1, map));
                    i += Fire.FIREPARAMS;
                    break;
                case "powerUp":
                    map.powerUpList.add(PowerUp.makeInstance(slices, i + 1, map));
                    i += PowerUp.POWERUPPARAMS;
                    break;
            }
        }
    }

    private ArrayList<Long> getAllMapsId(){
        outToServer.write("loadgames\n");
        String ans = inFromServer.next();
        while (!ans.equals("mapsid")){
            ans = inFromServer.next();
        }
        String[] maps = inFromServer.next().split(Core.SEP.toString());
        ArrayList<Long> mapsId = new ArrayList<>();
        for(int i = 0; i < maps.length; i++){
            mapsId.add(Long.valueOf(maps[i]));
        }
        return mapsId;
    }

    public SynchronizeDataOutPut getOutToServer() {
        return outToServer;
    }

    public void loadMapFromServer(Long id) {
        client.getServerHandler().outToServer.write("loadthisgame\n");
        client.getServerHandler().outToServer.write(String.valueOf(id) + "\n");
        updateMapFromServer(inFromServer.next());
        client.getServerHandlerThread().start();
    }

    public void watchMapFromServer(Long id){
        client.getServerHandler().outToServer.write("watchthisgame\n");
        client.getServerHandler().outToServer.write(String.valueOf(id) + "\n");
        updateMapFromServer(inFromServer.next());
        client.getServerHandlerThread().start();
    }
}
