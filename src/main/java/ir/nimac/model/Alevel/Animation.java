package ir.nimac.model.Alevel;


import ir.nimac.model.map.Map;

import java.util.Date;

public abstract class Animation implements Animatable {
    protected Cell thisShape;
    protected Map map;
    String name = "";
    boolean isActivate = false;
    int durationTime = 0;
    boolean forwardOrBackward = true;
    private boolean animating = false;
    private Date nextAnimation = new Date();
    private int stepDelay;

    public Animation(int stepDelay, Cell shape, Map map) {
        this.map = map;
        this.stepDelay = stepDelay;
        nextAnimation = new Date();
        animating = true;
        thisShape = shape;
    }

    public boolean getAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    public int getStepDelay() {
        return stepDelay;
    }

    public void setStepDelay(int stepDelay) {
        this.stepDelay = stepDelay;
    }

    public boolean animate() {
        if (animating) {
            if (nextAnimation.before(new Date())) {
                nextAnimation.setTime(new Date().getTime() + stepDelay);
                return step();
            }
            return true;
        }
        return false;
    }
}
