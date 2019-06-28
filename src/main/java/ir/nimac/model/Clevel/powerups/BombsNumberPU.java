package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class BombsNumberPU extends PowerUp {
    public BombsNumberPU(Location location, Map map) {
        super(location, map);
        type = 2;
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(1);
        else
            currentPic = MapFunctions.powerUpPic.get(6);
    }

    public BombsNumberPU(Location location, int effectAmount, boolean visible, Map map) {
        super(location, effectAmount, visible, map);
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(1);
        else
            currentPic = MapFunctions.powerUpPic.get(6);
        type = 2;
    }

    @Override
    public void effect(Player player) {
        if (player.bombList.size() + player.getBombLeft() > 1 || effectAmount > 0)
            player.setBombLeft(player.getBombLeft() + effectAmount);
    }

    @Override
    public void makeType() {
        if (MapFunctions.rand.nextInt(3) >= 1)
            effectAmount = 1;
        else
            effectAmount = -1;
    }
}
