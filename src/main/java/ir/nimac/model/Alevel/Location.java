package ir.nimac.model.Alevel;

public class Location {
    private int posX, posY;

    public Location(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getX() {
        return posX;
    }

    public void setX(int x) {
        posX = x;
    }

    public int getY() {
        return posY;
    }

    public void setY(int y) {
        posY = y;
    }


}
