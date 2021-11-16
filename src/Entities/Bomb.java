package Entities;

import Graphics.Sprites;

import static Graphics.Sprites.*;

import java.awt.*;

import static Graphics.TextMap.*;

public class Bomb extends Entities {
    boolean explored;
    public static Sprites[] bombAnimation = {
            bomb,
            bomb_1,
            bomb_2
    };
    private int plantedTime, explodedTime = 3000;
    private int indexAnimationBomb = 0;

    public Bomb(int x, int y, Sprites sprite) {
        super(x,y,sprite);
    }

    public boolean isExplored() {
        return explored;
    }

    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    public int getPlantedTime() {
        return plantedTime;
    }

    public void setPlantedTime(int plantedTime) {
        this.plantedTime = plantedTime;
    }

    public int getExplodedTime() {
        return explodedTime;
    }

    public void setExplodedTime(int explodedTime) {
        this.explodedTime = explodedTime;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(),x ,y,Sprites.DEFAULT_SIZE * 3,
                Sprites.DEFAULT_SIZE * 3,null);
    }

    public void explode(Bomb bomb) {
        int existTime = (int) System.currentTimeMillis() - bomb.getPlantedTime();
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationBomb = cycle % 3;
        }
        else {
            indexAnimationBomb = 2 - cycle % 3;
        }
        bomb.setCurrentSprite(bombAnimation[indexAnimationBomb]);
        if (existTime > bomb.explodedTime) {
            map1[y / 48][x / 48] = 0;
            explored = true;
        }
    }
}
