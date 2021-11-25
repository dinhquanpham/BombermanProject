package Entities;

import java.awt.*;
import Graphics.Sprites;

import static Graphics.Sprites.*;
import static Graphics.Sprites.explosion_vertical_down_last2;
import static Main.GameBomberMan.map;

public class Flame extends Entities{
    private boolean flaming, endFlame;
    private boolean blockUp, blockDown, blockLeft, blockRight;
    private int explodedTime;
    private final int explodedTiming = 300;
    private int indexAnimationFlames = 0;
    private int sizeFlames = 1;
    private int up = 0, down = 0, right = 0, left = 0;
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

    public static Sprites[] flamesAnimationUp = {
            explosion_vertical_up_last,
            explosion_vertical_up_last1,
            explosion_vertical_up_last2
    };

    public static Sprites[] flamesAnimationDown = {
            explosion_vertical_down_last,
            explosion_vertical_down_last1,
            explosion_vertical_down_last2
    };
    public Flame(int x, int y, Sprites sprite) {
        super(x, y, sprite);
    }

    public boolean isFlaming() {
        return flaming;
    }

    public void setFlaming(boolean flaming) {
        this.flaming = flaming;
    }

    public boolean isEndFlame() {
        return endFlame;
    }

    public void setEndFlame(boolean endFlame) {
        this.endFlame = endFlame;
    }

    public int getExplodedTime() {
        return explodedTime;
    }

    public void setExplodedTime(int explodedTime) {
        this.explodedTime = explodedTime;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getSizeFlames() {
        return sizeFlames;
    }

    public void setSizeFlames(int sizeFlames) {
        this.sizeFlames = sizeFlames;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(flamesAnimation[indexAnimationFlames].getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);

        // LeftExplosion
        for (int i = 1; i <= left; i++) {
            if (map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 3 ||
                    map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == -3)break;
            if (i < left) {
                g.drawImage(flamesAnimationHorizontal[indexAnimationFlames].getImage(), x - DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else {
                if (blockLeft)break;
                g.drawImage(flamesAnimationLeft[indexAnimationFlames].getImage(), x - DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }

        //RightExplosion
        for (int i = 1; i <= right; i++) {
            if (map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 3 ||
                    map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == -3)break;
            if (i < right) {
                g.drawImage(flamesAnimationHorizontal[indexAnimationFlames].getImage(), x + DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else {
                if (blockRight)break;
                g.drawImage(flamesAnimationRight[indexAnimationFlames].getImage(), x + DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }

        //UpExplosion
        for (int i = 1; i <= up; i++) {
            if (map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 3 ||
                    map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == -3)break;
            if (i < up) {
                g.drawImage(flamesAnimationVertical[indexAnimationFlames].getImage(), x,
                        y - DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else  {
                if (blockUp)break;
                g.drawImage(flamesAnimationUp[indexAnimationFlames].getImage(), x,
                        y - DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }

        //DownExplosion
        for (int i = 1; i <= down; i++) {
            if (map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 3 ||
                    map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == -3)break;
            if (i < down) {
                g.drawImage(flamesAnimationVertical[indexAnimationFlames].getImage(), x,
                        y + DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else {
                if (blockDown)break;
                g.drawImage(flamesAnimationDown[indexAnimationFlames].getImage(), x,
                        y + DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }
    }

    @Override
    public void collide(Object e) {
        /**
         if (e instanceof Flame) {
         Flame flame = (Flame) e;
         int topFlame = (flame.getY() - y) / DEFAULT_SIZE;
         int downFlame = (y - flame.getY()) / DEFAULT_SIZE;
         int leftFlame = (flame.getX() - x) / DEFAULT_SIZE;
         int rightFlame = (x - flame.getX()) / DEFAULT_SIZE;
         if (topFlame == 0 && downFlame == 0) {
         if (rightFlame <= flame.getRight())right = rightFlame;
         if (leftFlame <= flame.getLeft())left = leftFlame;
         }
         if (rightFlame == 0 && leftFlame == 0) {
         if (topFlame <= flame.getTop())top = topFlame;
         if (downFlame <= flame.getDown())down = downFlame;
         }
         }
         **/
    }

    public void check() {
        // LeftExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 1 ||
                    map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == -2) {
                break;
            }
            if (map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 2 ||
                    map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] >= 10) {
                map[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] = -2;
                left = i;
                blockLeft = true;
                break;
            }
            else {
                left = i;
            }
        }
        //RightExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 1 ||
                    map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == -2) {
                break;
            }
            if (map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 2 ||
                    map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] >= 10) {
                map[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] = -2;
                right = i;
                blockRight = true;
                break;
            }
            else {
                right = i;
            }
        }
        //UpExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 1 ||
                    map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == -2) {
                break;
            }
            if (map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 2 ||
                    map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] >= 10) {
                map[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] = -2;
                up = i;
                blockUp = true;
                break;
            }
            else {
                up = i;
            }
        }
        //DownExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 1 ||
                    map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == -2) {
                break;
            }
            if (map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 2 ||
                    map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] >= 10) {
                map[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] = -2;
                down = i;
                blockDown = true;
                break;
            }
            else {
                down = i;
            }
        }
    }

    public void handleFlame() {
        int existTime = (int) System.currentTimeMillis() - explodedTime;
        int cycle = existTime / 50;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationFlames = cycle % 3;
        } else {
            indexAnimationFlames = 2 - cycle % 3;
        }
        if (existTime > explodedTiming) {
            endFlame = true;
        }
    }
}
