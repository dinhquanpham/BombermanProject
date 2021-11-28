package Entities;

import Graphics.Sprites;

import javax.sound.sampled.LineUnavailableException;

import static Graphics.Sprites.*;

import java.awt.*;
import java.io.IOException;

import static Main.GameBomberMan.map;
import static Sound.Sound.explodedBombSound;

public class Bomb extends Entities {
    boolean exploded, checked;

    private int plantedTime;
    private final int plantedTiming = 3000;
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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getIndexAnimationBomb() {
        return indexAnimationBomb;
    }

    public void setIndexAnimationBomb(int indexAnimationBomb) {
        this.indexAnimationBomb = indexAnimationBomb;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);
    }

    public void explode(BomberMan player) throws LineUnavailableException, IOException {
        if (exploded) {
            return;
        }
        int existTime = (int) System.currentTimeMillis() - plantedTime;
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationBomb = cycle % 3;
        } else {
            indexAnimationBomb = 2 - cycle % 3;
        }
        currentSprite = bombAnimation[indexAnimationBomb];
        boolean xOverlaps = (x < player.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > player.getX());
        boolean yOverlaps = (y < player.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > player.getY());
        if (!(xOverlaps && yOverlaps)) {
            map[y / DEFAULT_SIZE][x / DEFAULT_SIZE] = 3;
        }
        if (existTime > plantedTiming) {
            explodedBombSound.playSound(false);
            System.out.println("LOI SOUND");
            exploded = true;
        }
    }

    @Override
    public void collide(Object e) {
        if(isExploded())return;
        if (e instanceof  Flame) {
            Flame flame = (Flame) e;
            int upBomb = (flame.getY() - y) / DEFAULT_SIZE;
            int downBomb = (y - flame.getY()) / DEFAULT_SIZE;
            int leftBomb = (flame.getX() - x) / DEFAULT_SIZE;
            int rightBomb = (x - flame.getX()) / DEFAULT_SIZE;
            if ((upBomb == 0 && downBomb == 0 && 0 <= rightBomb && rightBomb <= flame.getRight())
                    || (upBomb == 0 && downBomb == 0 && 0 <= leftBomb && leftBomb <= flame.getLeft())
                    || (rightBomb == 0 && leftBomb == 0 && 0 <= upBomb && upBomb <= flame.getUp())
                    || (rightBomb == 0 && leftBomb == 0 && 0 <= downBomb && downBomb <= flame.getDown())) {
                exploded = true;
                try {
                    explodedBombSound.playSound(false);
                } catch (IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                    System.out.println("LOI SOUND");
                }
            }
        }
    }
}