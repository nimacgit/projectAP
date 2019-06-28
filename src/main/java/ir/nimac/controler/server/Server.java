package ir.nimac.controler.server;

import ir.nimac.controler.AnimationThread;
import ir.nimac.model.map.Map;
import ir.nimac.view.ServerFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {

    ArrayList<ClientHandler> clientList = new ArrayList<ClientHandler>();
    private Long currPlayerId = 1L;
    private Long currGameId = 1L;
    private static final Long waitTime = 1000L;
    ArrayList<Map> allmaps = new ArrayList<>();
    ArrayList<AnimationThread> allmapsAnimationThread = new ArrayList<>();
    private int port = 2020;

    Server(int port){
        this.port = port;
        ServerSocket serverSocket = null;
        Socket socket = null;
        ServerFrame serverFrame = new ServerFrame();
        serverFrame.getServerNewGamePanel().getSubmit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map map = new Map();
                map.setGameId(getNewGameId());
                map.setGamePause(false);
                map.setMapDimention(Integer.valueOf(serverFrame.getServerNewGamePanel().getWidthField().getText()),
                        Integer.valueOf(serverFrame.getServerNewGamePanel().getHeightField().getText()));
                map.reset();
                map.setMaxUser(Integer.valueOf(serverFrame.getServerNewGamePanel().getMaxUserField().getText()));
                allmaps.add(map);
                map.buildMap();
                AnimationThread animationThread = new AnimationThread(map);
                allmapsAnimationThread.add(animationThread);
                animationThread.start();
                map.setGamePause(false);
            }
        });
        Thread frameUpdate = new Thread(() -> {
            while (true) {
                serverFrame.getGamesPanel().removeAll();
                for (int i = 0; i < allmaps.size(); i++) {
                    JButton newButton = new JButton(String.valueOf(allmaps.get(i).getGameId()));
                    Long gameId = allmaps.get(i).getGameId();
                    Map tempMap = allmaps.get(i);
                    newButton.setLayout(new GridLayout(1,1));
                    newButton.addActionListener(e -> {
                        serverFrame.getPlayerPanel().removeAll();
                        serverFrame.getRankPanel().removeAll();
                        for (int j = 0; j < tempMap.playerList.size(); j++) {
                            serverFrame.getPlayerPanel().add(new JLabel(tempMap.playerList.get(j).getName()));
                        }
                        for (int j = 0; j < tempMap.playerList.size(); j++) {
                            serverFrame.getRankPanel().add(new JLabel(tempMap.playerList.get(j).getName()
                                    + ":" + tempMap.playerList.get(j).getPoints()));
                        }
                    });
                    newButton.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                            if(e.getKeyCode() == KeyEvent.VK_DELETE){
                                closeGame(gameId);
                            }
                        }
                    });
                    serverFrame.getGamesPanel().add(newButton);
                    serverFrame.revalidate();
                }
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        frameUpdate.start();
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (Exception e) {
            System.err.println("cant make socket port");
        }
        serverFrame.setVisible(true);

        while (true) {
            try {
                if (serverSocket != null) {
                    ClientHandler newClient = new ClientHandler(this, serverSocket.accept());
                    new Thread(newClient).start();
                    System.err.println("new connection");
                }
            } catch (Exception e) {
                System.err.println("I/O error: " + e);
            }

        }
    }

    void sendToAll(String message, long gameId){
        try {
            System.out.println("send to all " + clientList.size());
            for (int i = 0; i < clientList.size(); i++) {
                if (clientList.get(i).map != null && clientList.get(i).map.getGameId() == gameId) {
                    System.err.println(clientList.get(i).name);
                    clientList.get(i).outToServer.write("message\n");
                    clientList.get(i).outToServer.write(message + "\n");
                    try {
                        clientList.get(i).outToServer.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e){
            System.err.println("error in send to all");
        }
    }
    synchronized Long getNewPlayerId() {
        currPlayerId++;
        return currPlayerId - 1L;
    }
    synchronized Long getNewGameId(){
        currGameId++;
        return currGameId - 1L;
    }
    Map getMap(long id){
        for(int i = 0; i < allmaps.size(); i++){
            if(allmaps.get(i).getGameId().equals(id)){
                return allmaps.get(i);
            }
        }
        return null;
    }

    public void closeGame(Long gameId){
        try {
            for (int i = 0; i < clientList.size(); i++) {
                if (clientList.get(i).map.getGameId().equals(gameId)) {
                    clientList.get(i).close();
                    clientList.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < allmapsAnimationThread.size(); i++) {
                if (allmapsAnimationThread.get(i).getMap().getGameId().equals(gameId)) {
                    allmapsAnimationThread.remove(i);
                    i--;
                }
            }
            for (int i = 0; i < allmaps.size(); i++) {
                if (allmaps.get(i).getGameId().equals(gameId)) {
                    allmaps.remove(i);
                }
            }
        } catch (Exception e){
            System.err.println("null in server");
        }

    }

}
