package ir.nimac.model.Windows;

import ir.nimac.controler.Core;
import ir.nimac.controler.dataBase.DataBase;
import ir.nimac.model.map.Map;

import javax.swing.*;

public class MapListerButtonAction{
    Map map;
    JFrame frameToFalse;
    JFrame frameToTrue;

    public MapListerButtonAction(Map map, JFrame frameTofalse, JFrame frameToTrue){
        this.map = map;
        this.frameToFalse = frameTofalse;
        this.frameToTrue = frameToTrue;

    }
    public void doAction(Long id){
        map.playerList.clear();
        Core.getInstance().doLoadGame(id, map);
        map.setGamePause(false);
        frameToFalse.setVisible(false);
        frameToTrue.setVisible(true);
    }

}
