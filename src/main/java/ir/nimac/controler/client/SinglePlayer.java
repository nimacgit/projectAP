package ir.nimac.controler.client;

import ir.nimac.controler.AnimationThread;
import ir.nimac.model.map.Map;

public class SinglePlayer {
    private final Object lock = new Object();
    private String name;
    //static Player newPlayer;
    //SinglePlayerDataBase dataBase;
    private Map map;

    public SinglePlayer(String name, Map map) {
        this.map = map;
        this.name = name;
        //dataBase = new SinglePlayerDataBase();
        Thread mapUpdateThread = new Thread(new AnimationThread(map));
        mapUpdateThread.start();
//        JPanel newGameButtonPanel = new JPanel(new GridLayout(1, 1));
//        JButton newGameButton = new JButton("new Game");
//    newGameButton.addActionListener(e -> {
//              if(newPlayer.isDead){
//                map.buildMap();
//                location = MapFunctions.getNewUserLoc();
//                newPlayer = new Player(location, name, 0);
//                newPlayer.isDead = false;
//                map.playerList.clear();
//                map.playerList.add(newPlayer);
//              }
//            });
//        newGameButtonPanel.add(newGameButton);
//        panel.add(newGameButtonPanel,  BorderLayout.SOUTH);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
