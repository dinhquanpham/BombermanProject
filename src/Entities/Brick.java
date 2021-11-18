package Entities;

import java.awt.*;
import Graphics.Sprites;

import static Graphics.Sprites.*;

public class Brick extends Entities {
    private boolean isDestroyed = false;
    private int collidedTime;
    public static Sprites[] brickAnimation = {
            brick_exploded,
            brick_exploded1,
            brick_exploded2
    };
    public Brick (int x, int y, Sprites sprite) {
        super(x,y,sprite);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public int getCollidedTime() {
        return collidedTime;
    }

    public void setCollidedTime(int collidedTime) {
        this.collidedTime = collidedTime;
    }

    @Override
    public void draw(Graphics g) {
        if(!isDestroyed) {
            g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        }
        else {
            int existTime = (int) System.currentTimeMillis() -collidedTime;
            if (existTime < 100) {
                g.drawImage(brickAnimation[0].getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
            } else {
                if (existTime < 200) {
                    g.drawImage(brickAnimation[1].getImage(), x, y, Sprites.DEFAULT_SIZE,
                            Sprites.DEFAULT_SIZE, null);
                } else  {
                    if (existTime < 300) {
                        g.drawImage(brickAnimation[2].getImage(), x, y, Sprites.DEFAULT_SIZE,
                                Sprites.DEFAULT_SIZE, null);
                    } else {
                        currentSprite = grass;
                        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                                Sprites.DEFAULT_SIZE, null);
                    }
                }
            }
        }
    }

    @Override
    public void collide(Flame flame) {
        int topBrick = (flame.getY() - y) / DEFAULT_SIZE;
        int downBrick = (y - flame.getY()) / DEFAULT_SIZE;
        int leftBrick = (flame.getX() - x) / DEFAULT_SIZE;
        int rightBrick = (x - flame.getX()) / DEFAULT_SIZE;
        if (isDestroyed)return;
        if ((topBrick == 0 && downBrick == 0 && rightBrick == flame.getRight())
                || (topBrick == 0 && downBrick == 0 && leftBrick == flame.getLeft())
                || (rightBrick == 0 && leftBrick == 0 && topBrick == flame.getTop())
                || (rightBrick == 0 && leftBrick == 0 && downBrick == flame.getDown())) {
            this.setDestroyed(true);
            collidedTime = (int) System.currentTimeMillis();
        }
    }
}
