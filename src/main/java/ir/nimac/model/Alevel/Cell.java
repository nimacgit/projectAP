package ir.nimac.model.Alevel;

import ir.nimac.model.map.Map;

import java.awt.*;

abstract public class Cell implements Animatable {
    public Location location;
    public Dimension dimension;
    public Animation animation;
    public Image currentPic;
    protected Map map;
    private boolean visible = true;

    public Cell(Location location, Map map) {
        this.location = location;
        this.map = map;
    }

    public boolean isIn(Location p) {
        return p.getX() == location.getX() && p.getY() == location.getY();
    }


    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    @Override
    public boolean step() {
        return animation.step();
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
