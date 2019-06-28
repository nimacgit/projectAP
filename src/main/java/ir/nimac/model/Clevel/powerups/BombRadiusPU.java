package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class BombRadiusPU extends PowerUp {
    public BombRadiusPU(Location location, Map map) {
        super(location, map);
        type = 1;
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(0);
        else
            currentPic = MapFunctions.powerUpPic.get(5);
    }

    public BombRadiusPU(Location location, int effectAmount, boolean visible, Map map) {
        super(location, effectAmount, visible, map);
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(0);
        else
            currentPic = MapFunctions.powerUpPic.get(5);
        type = 1;
    }


    @Override
    public void effect(Player player) {
        if (player.getBombRadius() > 1 || effectAmount > 0)
            player.setBombRadius(player.getBombRadius() + effectAmount);
    }

    @Override
    public void makeType() {
        if (MapFunctions.rand.nextInt(3) >= 1)
            effectAmount = 1;
        else
            effectAmount = -1;
    }


}
