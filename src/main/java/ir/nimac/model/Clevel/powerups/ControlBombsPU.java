package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class ControlBombsPU extends PowerUp {
    public ControlBombsPU(Location location, Map map) {
        super(location, map);
        type = 3;
        if (effectAmount >= 0)
            currentPic = MapFunctions.powerUpPic.get(2);
        else
            currentPic = MapFunctions.powerUpPic.get(7);
    }

    public ControlBombsPU(Location location, int effectAmount, boolean visible, Map map) {
        super(location, effectAmount, visible, map);
        if (effectAmount >= 0)
            currentPic = MapFunctions.powerUpPic.get(2);
        else
            currentPic = MapFunctions.powerUpPic.get(7);
        type = 3;
    }

    @Override
    public void effect(Player player) {
        if (effectAmount < 0)
            player.setBombControl(true);
        else
            player.setBombControl(false);
    }

    @Override
    public void makeType() {
        if (MapFunctions.rand.nextInt(3) >= 1)
            effectAmount = 1;
        else
            effectAmount = -1;
    }
}
