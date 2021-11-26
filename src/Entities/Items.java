package Entities;

import Graphics.Sprites;

import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.io.IOException;

import static Graphics.Sprites.*;
import static Sound.Sound.itemSound;

public class Items extends Brick {
    private int index;
    private boolean used;
    public static Sprites[] itemsLists = {
            powerup_bombs,
            powerup_flames,
            powerup_speed
    };
    public Items(int x, int y, Sprites sprite) {
        super(x, y, sprite);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    @Override
    public void draw(Graphics g) {
        if (!isDestroyed()) {
            currentSprite = brick;
            g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        } else {
            if (!used) {
                switch (index) {
                    case -10: {
                        currentSprite = powerup_bombs;
                        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                                Sprites.DEFAULT_SIZE, null);
                        break;
                    }
                    case -11: {
                        currentSprite = powerup_flames;
                        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                                Sprites.DEFAULT_SIZE, null);
                        break;
                    }
                    default: {
                        currentSprite = powerup_speed;
                        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                                Sprites.DEFAULT_SIZE, null);
                        break;
                    }
                }
            } else {
                currentSprite = grass;
                g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
            }
        }
    }

    @Override
    public void collide(Object e) {
        if (e instanceof Flame) {
            if (isDestroyed())return;
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
                index = -index;
            }
        }
        if (e instanceof BomberMan) {
            if (!isDestroyed() || isUsed())return;
            BomberMan player = (BomberMan) e;
            boolean xOverlaps = (x < player.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > player.getX());
            boolean yOverlaps = (y < player.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > player.getY());
            if (xOverlaps && yOverlaps) {
                try {
                    itemSound.playSound(false);
                } catch (IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
                this.setUsed(true);
            }
        }
    }
}
