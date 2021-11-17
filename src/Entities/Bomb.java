package Entities;

import Graphics.Sprites;
import Main.GameBomberMan;

import static Graphics.Sprites.*;

import java.awt.*;

import static Graphics.TextMap.*;

public class Bomb extends Entities {
    boolean exploded, endFlames;

    private int plantedTime;
    private final int plantedTiming = 2000;
    private int indexAnimationBomb = 0;
    private int explodedTime;
    private final int explodedTiming = 300;
    private int indexAnimationFlames = 0;
    private int sizeFlames = 4;
    public static Sprites[] bombAnimation = {
            bomb,
            bomb_1,
            bomb_2
    };
    public static Sprites[] flamesAnimation = {
            bomb_exploded,
            bomb_exploded1,
            bomb_exploded2
    };
    public static Sprites[] flamesAnimationVertical = {
            explosion_vertical,
            explosion_vertical1,
            explosion_vertical2
    };
    public static Sprites[] flamesAnimationHorizontal = {
            explosion_horizontal,
            explosion_horizontal1,
            explosion_horizontal2
    };
    public static Sprites[] flamesAnimationLeft = {
            explosion_horizontal_left_last,
            explosion_horizontal_left_last1,
            explosion_horizontal_left_last2
    };

    public static Sprites[] flamesAnimationRight = {
            explosion_horizontal_right_last,
            explosion_horizontal_right_last1,
            explosion_horizontal_right_last2
    };

    public static Sprites[] flamesAnimationTop = {
            explosion_vertical_top_last,
            explosion_vertical_top_last1,
            explosion_vertical_top_last2
    };

    public static Sprites[] flamesAnimationDown = {
            explosion_vertical_down_last,
            explosion_vertical_down_last1,
            explosion_vertical_down_last2
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

    public boolean isEndFlames() {
        return endFlames;
    }

    public void setEndFlames(boolean endFlames) {
        this.endFlames = endFlames;
    }

    @Override
    public void draw(Graphics g) {
        if (!exploded) {
            g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        } else {
            g.drawImage(flamesAnimation[indexAnimationFlames].getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);

            // LeftExplosion
            for (int i = 1; i <= sizeFlames; i++) {
                if (map1[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i)/ DEFAULT_SIZE] == 0) {
                    if (i < sizeFlames) {
                        g.drawImage(flamesAnimationHorizontal[indexAnimationFlames].getImage(), x - DEFAULT_SIZE * i,
                                y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                    else {
                        g.drawImage(flamesAnimationLeft[indexAnimationFlames].getImage(), x - DEFAULT_SIZE * i,
                                y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                }
                if (map1[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 2 || map1[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 1) {
                    break;
                }
            }

            //RightExplosion
            for (int i = 1; i <= sizeFlames; i++) {
                if (map1[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 0) {
                    if (i < sizeFlames) {
                        g.drawImage(flamesAnimationHorizontal[indexAnimationFlames].getImage(), x + DEFAULT_SIZE * i,
                                y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                    else {
                        g.drawImage(flamesAnimationRight[indexAnimationFlames].getImage(), x + DEFAULT_SIZE * i,
                                y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                }
                if (map1[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 2 || map1[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 1) {
                    break;
                }
            }

            //TopExplosion
            for (int i = 1; i <= sizeFlames; i++) {
                if (map1[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 0) {
                    if (i < sizeFlames) {
                        g.drawImage(flamesAnimationVertical[indexAnimationFlames].getImage(), x,
                                y - DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                    else  {
                        g.drawImage(flamesAnimationTop[indexAnimationFlames].getImage(), x,
                                y - DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                }
                if (map1[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 2 || map1[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 1) {
                    break;
                }
            }

            //DownExplosion
            for (int i = 1; i <= sizeFlames; i++) {
                if (map1[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 0) {
                    if (i < sizeFlames) {
                        g.drawImage(flamesAnimationVertical[indexAnimationFlames].getImage(), x,
                                y + DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                    else {
                        g.drawImage(flamesAnimationDown[indexAnimationFlames].getImage(), x,
                                y + DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
                    }
                }
                if (map1[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 2 || map1[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 1) {
                    break;
                }
            }
        }
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
            explodedTime = ((int) System.currentTimeMillis());
        }
    }

    public void handleFlames(GameBomberMan myGame) {
        if (!exploded) return;
        int existTime = (int) System.currentTimeMillis() - explodedTime;
        int cycle = existTime / 50;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationFlames = cycle % 3;
        } else {
            indexAnimationFlames = 2 - cycle % 3;
        }
        if (existTime > explodedTiming) {
            endFlames = true;
        }
    }
}