package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class PointsPU extends PowerUp {
    public PointsPU(Location location, Map map) {
        super(location, map);
        type = 4;
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(3);
        else
            currentPic = MapFunctions.powerUpPic.get(8);
    }

    public PointsPU(Location location, int effectAmount, boolean visible, Map map) {
        super(location, effectAmount, visible, map);
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(3);
        else
            currentPic = MapFunctions.powerUpPic.get(8);
        type = 4;
    }

    @Override
    public void effect(Player player) {
        player.setPoints(player.getPoints() + effectAmount);
    }

    @Override
    public void makeType() {
        if (MapFunctions.rand.nextInt(3) >= 1)
            effectAmount = 100;
        else
            effectAmount = -100;
    }
}
