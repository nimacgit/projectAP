package ir.nimac.model.Clevel.powerups;

import ir.nimac.model.Alevel.Location;
import ir.nimac.model.Clevel.Player;
import ir.nimac.model.map.Map;
import ir.nimac.model.map.MapFunctions;

public class GostPU extends PowerUp {
    public GostPU(Location location, Map map) {
        super(location, map);
        type = 6;
        currentPic = MapFunctions.powerUpPic.get(10);
    }

    public GostPU(Location location, int effectAmount, boolean visible, Map map) {
        super(location, effectAmount, visible, map);
        type = 6;
        currentPic = MapFunctions.powerUpPic.get(10);
    }

    @Override
    public void effect(Player player) {
        player.setGost(true);
    }

    @Override
    public void makeType() {
    }
}
