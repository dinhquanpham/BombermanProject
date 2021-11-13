package Entities;

import Graphics.Sprites;

import javax.imageio.ImageIO;
import java.awt.*;

public abstract class Entities {
    protected int x;
    protected int y;

    protected Sprites currentSprite;

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

    public void setCurrentSprite(Sprites currentSprite) {
        this.currentSprite = currentSprite;
    }

    public Entities(int x, int y, Sprites sprite) {
        this.x = x;
        this.y = y;
        this.currentSprite = sprite;
    }

    public abstract void draw(Graphics g);
}
