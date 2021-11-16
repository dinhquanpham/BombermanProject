package Entities;

import Graphics.Sprites;
import Main.GameBomberMan;

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
    public int plantedTime, explodedTime = 3000;
    public int indexAnimationBomb = 0;

    public Bomb(int x, int y, Sprites sprite) {
        super(x, y, sprite);
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

    public void explode(GameBomberMan myGame) {
        int existTime = (int) System.currentTimeMillis() - plantedTime;
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationBomb = cycle % 3;
        }
        else {
            indexAnimationBomb = 2 - cycle % 3;
        }
        currentSprite = bombAnimation[indexAnimationBomb];
        boolean xOverlaps = (x < myGame.player.getX() + 48) && (x + 48 > myGame.player.getX());
        boolean yOverlaps = (y < myGame.player.getY() + 48) && (y + 48 > myGame.player.getY());
        System.out.println(x + " " + myGame.player.getX() + " " + y + " " + myGame.player.getY());
        if (!(xOverlaps && yOverlaps)) {
            map1[y / 48][x / 48] = 3;
        }

        if (existTime > explodedTime) {
            map1[y / 48][x / 48] = 0;
            explored = true;
        }
    }
}
