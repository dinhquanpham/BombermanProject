package Entities;

import java.awt.*;
import Graphics.Sprites;

public abstract class Entities {
    protected int x;
    protected int y;

    protected Sprites currentSprite;
    private boolean died;
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprites getCurrentSprite() {
        return currentSprite;
    }

    public boolean isDied() {
        return died;
    }

    public void setDied(boolean died) {
        this.died = died;
    }

    public void setCurrentSprite(Sprites currentSprite) {
        this.currentSprite = currentSprite;
    }

    public Entities(int x, int y, Sprites sprite) {
        this.x = x;
        this.y = y;
        this.currentSprite = sprite;
    }

    public abstract void draw(Graphics g);
    public abstract void collide(Flame flame);
}
