package ir.nimac.controler.server;

import ir.nimac.controler.*;
import ir.nimac.model.Alevel.SynchronizeDataOutPut;
import ir.nimac.model.map.Map;

import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private static Socket socket;
    public SynchronizeDataOutPut outToServer = null;
    Map map;
    User me = new User();
    String name = "";
    private Scanner inpFromServer = null;
    private Server server;

    public ClientHandler(Server server, Socket clientSocket) {
        socket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            inpFromServer = new Scanner(socket.getInputStream());
            outToServer = new SynchronizeDataOutPut(socket.getOutputStream());
        } catch (Exception e) {
            System.err.println("cant run handler inside");
            return;
        }
        server.clientList.add(this);
        while (!socket.isClosed()){
            try {
                String command = inpFromServer.next();
                if(command != null) {
                    System.err.println(command);
                    if (command.equals("newgame")) {
                        map = new Map();
                        map.setMapDimention(inpFromServer.nextInt(), inpFromServer.nextInt());
                        map.reset();
                        server.allmaps.add(map);
                        map.buildMap();
                        map.setGameId(server.getNewGameId());
                        me.setPlayer(map.addNewPlayer(name, socket.getInetAddress().toString()
                                .substring(1, socket.getInetAddress().toString().length())));
                        me.getPlayer().setId(server.getNewPlayerId());
                        AnimationThread animationThread = new AnimationThread(map);
                        server.allmapsAnimationThread.add(animationThread);
                        animationThread.start();
                        map.setGamePause(false);
                    } else if (command.equals("loadgames")) {
                        outToServer.write("mapsid\n");
                        StringBuilder resp = new StringBuilder();
                        for(int i = 0; i < server.allmaps.size(); i++){
                            resp.append(server.allmaps.get(i).getGameId());
                            resp.append(Core.SEP);
                        }
                        resp.append("\n");
                        outToServer.write(resp.toString());
                        outToServer.flush();
                    } else if (command.equals("loadthisgame")) {
                        map = server.getMap(inpFromServer.nextLong());
                        map.setGamePause(false);
                        me.setPlayer(map.addNewPlayer(name, socket.getInetAddress().toString()
                        .substring(1, socket.getInetAddress().toString().length())));
                        me.getPlayer().setId(server.getNewPlayerId());
                        MapUpdater.sendMap(outToServer, map);
                        outToServer.flush();
                        outToServer.write("setDimension\n");
                        outToServer.write(String.valueOf(map.getMapWidth()));
                        outToServer.write("\n");
                        outToServer.write(String.valueOf(map.getMapHeight()));
                        outToServer.write("\n");
                    } else if (command.equals("watchthisgame")) {
                        map = server.getMap(inpFromServer.nextLong());
                        map.setGamePause(false);
                        me.setWatcher(true);
                        outToServer.write("setDimension\n");
                        outToServer.write(String.valueOf(map.getMapWidth()));
                        outToServer.write("\n");
                        outToServer.write(String.valueOf(map.getMapHeight()));
                        outToServer.write("\n");
                        MapUpdater.sendMap(outToServer, map);
                        outToServer.flush();
                    } else if (command.equals("getmap")) {
                        outToServer.write("getmap\n");
                        MapUpdater.sendMap(outToServer, map);
                        outToServer.flush();
                    } else if (command.equals("setname")){
                        name = inpFromServer.next();
                    } else if (command.equals("message")){
                        String text = inpFromServer.next();
                        text = name + text;
                        server.sendToAll(text, map.getGameId());
                    } else if (command.equals("DOWN") || command.equals("UP") || command.equals("RIGHT")
                            || command.equals("LEFT") ||command.equals("SPACE") || command.equals("B")){
                        if(!me.isWatcher() && !me.getPlayer().isDead()) {
                            KeyBoardHandler.keyHandle(command, me.getPlayer(), map);
                        }
                    } else if (!me.isWatcher() && command.equals("RESETKEYBOARD")) {
                        me.getPlayer().lastKey = -1;
                    }
                }
            } catch (NoSuchElementException e) {
                System.err.println("connection lost");
                try {
                    socket.close();
                    server.clientList.remove(this);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//
//        try {
//            name = inpFromServer.readLine();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        id = Server.getNewId();
//        try {
//            outToServer.writeBytes(id.toString() + "\n");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Location location = MapFunctions.getNewUserLoc();
//        //TODO bug localAddress
//        Player newPlayer = new Player(location, name, id, socket.getLocalAddress().toString(), map);
//        map.playerList.add(newPlayer);
//        System.err.println(socket.getInetAddress() + " is connect : " + name);
//        Server.clientList.add(this);
//        while (!newPlayer.isDead()) {
//            try {
//
//                if (action == null)
//                    return;
//
//                else if (!newPlayer.isDead()) {
//                    keyHandle(action, newPlayer, map);
//                }
//            } catch (Exception e) {
//                System.err.println("client Exit");
//                map.playerList.remove(newPlayer);
//                newPlayer.setDead(true);
//                Server.clientList.remove(this);
//                return;
//            }
//        }
    }


    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

