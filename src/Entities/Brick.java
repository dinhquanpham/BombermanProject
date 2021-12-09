package Entities;

import java.awt.*;
import Graphics.Sprites;

import static Graphics.Sprites.*;

public class Brick extends Entities {
    private boolean isDestroyed = false;
    private long collidedTime;
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


    @Override
    public void draw(Graphics g) {
        if(!isDestroyed) {
            g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        }
        else {
            int existTime = (int) (System.currentTimeMillis() - collidedTime);
            if (existTime < 300) {
                g.drawImage(grass.getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
                g.drawImage(brickAnimation[existTime / 100].getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
            } else {
                currentSprite = grass;
                g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
            }
        }
    }

    @Override
    public void collide(Object e) {
        if (isDestroyed)return;
        if (e instanceof Flame) {
            Flame flame = (Flame) e;
            int upBrick = (flame.getY() - y) / DEFAULT_SIZE;
            int downBrick = (y - flame.getY()) / DEFAULT_SIZE;
            int leftBrick = (flame.getX() - x) / DEFAULT_SIZE;
            int rightBrick = (x - flame.getX()) / DEFAULT_SIZE;
            if ((upBrick == 0 && downBrick == 0 && rightBrick == flame.getRight())
                    || (upBrick == 0 && downBrick == 0 && leftBrick == flame.getLeft())
                    || (rightBrick == 0 && leftBrick == 0 && upBrick == flame.getUp())
                    || (rightBrick == 0 && leftBrick == 0 && downBrick == flame.getDown())) {
                this.setDestroyed(true);
                collidedTime = System.currentTimeMillis();
            }
        }
    }
}
