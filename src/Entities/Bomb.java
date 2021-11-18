package Entities;

import Graphics.Sprites;
import Main.GameBomberMan;

import static Graphics.Sprites.*;

import java.awt.*;

import static Graphics.TextMap.*;

public class Bomb extends Entities {
    boolean exploded;

    private int plantedTime;
    private final int plantedTiming = 2000;
    private int indexAnimationBomb = 0;
    public static Sprites[] bombAnimation = {
            bomb,
            bomb_1,
            bomb_2
    };

    public Bomb(int x, int y, Sprites sprite) {
        super(x, y, sprite);
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public int getPlantedTime() {
        return plantedTime;
    }

    public void setPlantedTime(int plantedTime) {
        this.plantedTime = plantedTime;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);
    }

    public void explode(GameBomberMan myGame) {
        if (exploded) return;
        int existTime = (int) System.currentTimeMillis() - plantedTime;
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationBomb = cycle % 3;
        } else {
            indexAnimationBomb = 2 - cycle % 3;
        }
        currentSprite = bombAnimation[indexAnimationBomb];
        boolean xOverlaps = (x < myGame.player.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > myGame.player.getX());
        boolean yOverlaps = (y < myGame.player.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > myGame.player.getY());
        if (!(xOverlaps && yOverlaps)) {
            map1[y / DEFAULT_SIZE][x / DEFAULT_SIZE] = 3;
        }
        if (existTime > plantedTiming) {
            map1[y / DEFAULT_SIZE][x / DEFAULT_SIZE] = 0;
            exploded = true;
        }
    }

    @Override
    public void collide(Flame flame) {
        return;
    }
}