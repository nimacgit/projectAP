package ir.nimac;

import ir.nimac.controler.User;
import ir.nimac.controler.client.SoundPlayer;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.Windows.GameConfig;
import ir.nimac.model.map.Map;
import ir.nimac.view.GameConfigPanel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Main {

    public static String soundUrl = "src/main/resources/yann.mp3";
    //sound
    //logger
    //database
    //junit
    //oop
    //properties
    //preparedstatment


    public static void main(String args[]) throws Exception {
        SoundPlayer.getInstance().playContinuessSound(soundUrl);
        GameConfig gameConfing = new GameConfig();
    }
}
