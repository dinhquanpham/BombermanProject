package Entities;

import Graphics.Sprites;
import java.awt.*;

import static Graphics.Sprites.*;

public class Portal extends Brick {
    private boolean switchMap;
    public static Sprites[] portalSprite = {
            portal
    };

    public Portal(int x, int y, Sprites sprite) {
        super(x, y, sprite);
    }

    public boolean isSwitchMap() {
        return switchMap;
    }

    public void setSwitchMap(boolean switchMap) {
        this.switchMap = switchMap;
    }

    @Override
    public void draw(Graphics g) {
        if (!isDestroyed()) {
            currentSprite = brick;
        } else {
            currentSprite = portal;
            g.drawImage(grass.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        }
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);
    }

    @Override
    public void collide(Object e) {
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
            }
        }
        if (e instanceof BomberMan) {
            BomberMan player = (BomberMan) e;
            boolean xOverlaps = (x < player.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > player.getX());
            boolean yOverlaps = (y < player.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > player.getY());
            if (xOverlaps && yOverlaps) {
                this.setSwitchMap(true);
            }
        }
    }
}
