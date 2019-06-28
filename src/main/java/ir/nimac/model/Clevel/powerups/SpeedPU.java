package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class SpeedPU extends PowerUp {
    public SpeedPU(Location location, Map map) {
        super(location, map);
        type = 5;
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(4);
        else
            currentPic = MapFunctions.powerUpPic.get(9);
    }

    public SpeedPU(Location location, int effectAmount, boolean visible, Map map) {
        super(location, effectAmount, visible, map);
        if (effectAmount < 0)
            currentPic = MapFunctions.powerUpPic.get(4);
        else
            currentPic = MapFunctions.powerUpPic.get(9);
        type = 5;
    }

    @Override
    public void effect(Player player) {
        if (player.getSpeed() > 1 || effectAmount > 0)
            player.setSpeed(player.getSpeed() + effectAmount);
    }

    @Override
    public void makeType() {
        if (MapFunctions.rand.nextInt(3) >= 1)
            effectAmount = 1;
        else
            effectAmount = -1;
    }
}
