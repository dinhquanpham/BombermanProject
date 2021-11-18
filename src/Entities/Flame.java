package Entities;

import java.awt.*;
import Graphics.Sprites;
import Main.GameBomberMan;

import static Graphics.Sprites.*;
import static Graphics.Sprites.explosion_vertical_down_last2;
import static Graphics.TextMap.map1;

public class Flame extends Entities{
    private boolean flaming, endFlame;
    private int explodedTime;
    private final int explodedTiming = 300;
    private int indexAnimationFlames = 0;
    private int sizeFlames = 3;
    private int top = 0, down = 0, right = 0, left = 0;
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

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
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

    @Override
    public void draw(Graphics g) {
        g.drawImage(flamesAnimation[indexAnimationFlames].getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);

        // LeftExplosion
        for (int i = 1; i <= left; i++) {
            if (i < left) {
                g.drawImage(flamesAnimationHorizontal[indexAnimationFlames].getImage(), x - DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else {
                g.drawImage(flamesAnimationLeft[indexAnimationFlames].getImage(), x - DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }

        //RightExplosion
        for (int i = 1; i <= right; i++) {
            if (i < right) {
                g.drawImage(flamesAnimationHorizontal[indexAnimationFlames].getImage(), x + DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else {
                g.drawImage(flamesAnimationRight[indexAnimationFlames].getImage(), x + DEFAULT_SIZE * i,
                        y, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }

        //TopExplosion
        for (int i = 1; i <= top; i++) {
            if (i < top) {
                g.drawImage(flamesAnimationVertical[indexAnimationFlames].getImage(), x,
                        y - DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else  {
                g.drawImage(flamesAnimationTop[indexAnimationFlames].getImage(), x,
                        y - DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }

        //DownExplosion
        for (int i = 1; i <= down; i++) {
            if (i < down) {
                g.drawImage(flamesAnimationVertical[indexAnimationFlames].getImage(), x,
                        y + DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
            else {
                g.drawImage(flamesAnimationDown[indexAnimationFlames].getImage(), x,
                        y + DEFAULT_SIZE * i, Sprites.DEFAULT_SIZE, Sprites.DEFAULT_SIZE, null);
            }
        }
    }

    @Override
    public void collide(Flame flame) {
        return;
    }

    public void check() {
        // LeftExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map1[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 1) {
                break;
            }
            if (map1[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] == 2) {
                map1[y / DEFAULT_SIZE][(x - DEFAULT_SIZE * i) / DEFAULT_SIZE] = 0;
                left = i;
                break;
            }
            else {
                left = i;
            }
        }
        //RightExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map1[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 1) {
                break;
            }
            if (map1[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] == 2) {
                map1[y / DEFAULT_SIZE][(x + DEFAULT_SIZE * i) / DEFAULT_SIZE] = 0;
                right = i;
                break;
            }
            else {
                right = i;
            }
        }
        //TopExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map1[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 1) {
                break;
            }
            if (map1[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 2) {
                map1[(y - DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] = 0;
                top = i;
                break;
            }
            else {
                top = i;
            }
        }
        //DownExplosion
        for (int i = 1; i <= sizeFlames; i++) {
            if (map1[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 1) {
                break;
            }
            if (map1[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] == 2) {
                map1[(y + DEFAULT_SIZE * i) / DEFAULT_SIZE][x / DEFAULT_SIZE] = 0;
                down = i;
                break;
            }
            else {
                down = i;
            }
        }
        /**
         System.out.println("Top " + top);
         System.out.println("Down: " + down);
         System.out.println("left: " + left);
         System.out.println("Right: " + right);
         System.out.println("--------------");
         **/
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
