package ir.nimac.controler;

import ir.nimac.Logger;
import ir.nimac.controler.dataBase.DataBase;
import ir.nimac.model.Alevel.SynchronizeDataOutPut;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.Windows.MapLister;
import ir.nimac.model.map.Map;

import javax.swing.*;
import java.awt.event.KeyEvent;

import static ir.nimac.controler.KeyBoardHandler.keyHandle;

public class Core {
    private static Core core = new Core();

    public static final String  SEP = "/";
    public static final Character SEP_CHAR = '/';

    private Core() {

    }

    public static Core getInstance() {
        return core;
    }

    public void doKeyBoard(Map map, KeyEvent e, JFrame frame) {
        if (map.getMe().isOnline()) {
            sendKeyboardAction2Server(e, map.getMe());
        } else {
            String key = KeyEvent.getKeyText(e.getKeyCode());
            if (!map.getMe().getPlayer().isDead()) {
                keyHandle(key, map.getMe().getPlayer(), map);
            }
            if (e.getKeyCode() == KeyEvent.VK_S && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                map.setGamePause(true);
                DataBase.getInstance().saveMap(map);
                map.setGamePause(false);
                Logger.getInstance().debugLog("done save");
            } else if (e.getKeyCode() == KeyEvent.VK_O && (e.getModifiers() & KeyEvent.CTRL_MASK) != 0) {
                map.getMainFrame().setVisible(false);
                MapLister mapLister = new MapLister(map, map.getMainFrame());
            }
        }
    }

    public void doKeyBoardReset(User me){
        if(me.isOnline()){
            me.getClient().getServerHandler().getOutToServer().write("RESETKEYBOARD" + "\n");
        } else {
            me.getPlayer().lastKey = -1;
        }
    }

    private void sendKeyboardAction2Server(KeyEvent e, User me) {
        String key = KeyEvent.getKeyText(e.getKeyCode());
        try {
            switch (key) {
                case "Up":
                    me.getClient().getServerHandler().getOutToServer().write("UP" + "\n");
                    break;
                case "Down":
                    me.getClient().getServerHandler().getOutToServer().write("DOWN" + "\n");
                    break;
                case "Right":
                    me.getClient().getServerHandler().getOutToServer().write("RIGHT" + "\n");
                    break;
                case "Left":
                    me.getClient().getServerHandler().getOutToServer().write("LEFT" + "\n");
                    break;
                case "Space":
                    me.getClient().getServerHandler().getOutToServer().write("SPACE" + "\n");
                    break;
                case "B":
                    me.getClient().getServerHandler().getOutToServer().write("B" + "\n");
                    break;
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void doLoadGame(Long id, Map map) {
        if(map.getMe().isWatcher()){
            map.getMe().getClient().getServerHandler().watchMapFromServer(id);
            map.getMainFrame().setVisible(true);
        } else {
            if (map.getMe().isOnline()) {
                map.getMe().getClient().getServerHandler().loadMapFromServer(id);
            } else {
                DataBase.getInstance().loadMap(id, map);
            }
        }
    }

    public static void sendMessageHelper(SynchronizeDataOutPut outPut, String text){
        text = text.replace(' ' , SEP_CHAR);
        outPut.write("message\n");
        outPut.write(text);
    }

    public static String readMessageHelper(String text){
        text = text.replace(SEP_CHAR, ' ');
        return text;
    }

}
