package Entities;

import java.awt.*;
import java.util.Random;

import Graphics.Sprites;
import static Graphics.Sprites.*;
import static Graphics.TextMap.map1;

public class Balloom extends Entities {
    private int[][] moved = new int[15][31];
    private boolean isDead, isEndAnimation;
    private int deadTime;
    private int indexDirection = 0;
    private int preIndexDirection = 0;
    private int spawnTime;
    private int indexAnimationBalloom = 0;
    private final int balloomSpeed = 1;
    private int dx[] = {0, -1, 0, 1};
    private int dy[] = {-1, 0, 1, 0};

    public static Sprites[] balloomAnimationLeft = {
            balloom_left1,
            balloom_left2,
            balloom_left3
    };

    public static Sprites[] balloomAnimationRight = {
            balloom_right1,
            balloom_right2,
            balloom_right3
    };
    public static Sprites[] balloomAnimationDead = {
            mob_dead1,
            mob_dead2,
            mob_dead3
    };

    public Balloom(int x, int y, Sprites sprite) {
        super(x, y, sprite);
    }

    public void draw(Graphics g) {
        if (!isDead) {
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);
        } else {
            int existTime = (int) System.currentTimeMillis() - deadTime;
            if (existTime < 300) {
                g.drawImage(balloomAnimationDead[existTime / 100].getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
            } else {
                isEndAnimation = true;
            }
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isEndAnimation() {
        return isEndAnimation;
    }

    public boolean isFreeToMove(int nextX, int nextY) {
        // check va cham balloom khac o day
        return true;
    }

    public int chooseDirection() {
        int trueX = x / DEFAULT_SIZE;
        int trueY = y / DEFAULT_SIZE;
        int options[] = new int[4];
        int totalOptions = -1;
        for (int i = 0; i <= 3; i++) {
            int trueNextX = trueX + dx[i];
            int trueNextY = trueY + dy[i];
            if (map1[trueNextY][trueNextX] != 1 &&
                    map1[trueNextY][trueNextX] != 10 &&
                    map1[trueNextY][trueNextX] != 11 &&
                    map1[trueNextY][trueNextX] != 12 &&
                    map1[trueNextY][trueNextX] != 2 && map1[trueNextY][trueNextX] != -2 &&
                    map1[trueNextY][trueNextX] != 3 && map1[trueNextY][trueNextX] != -3 &&
                    moved[trueNextY][trueNextX] != 1) {
                totalOptions++;
                options[totalOptions] = i;
            }
        }
        totalOptions++;

        if (totalOptions > 0) {
            int rnd = new Random().nextInt(totalOptions);
            return options[rnd];
        }
        return -1;
    }

    public void move() {
        if (isDead)return;
        int trueX = x / DEFAULT_SIZE;
        int trueY = y / DEFAULT_SIZE;
        if (x % 48 == 0 && y % 48 == 0) {
            indexDirection = chooseDirection();
        }
        if (indexDirection == -1) {
            moved[trueY - dy[preIndexDirection]][trueX - dx[preIndexDirection]] = 0;
            return;
        } else {
            preIndexDirection = indexDirection;
        }
        x += dx[indexDirection] * balloomSpeed;
        y += dy[indexDirection] * balloomSpeed;
        int existTime = (int) System.currentTimeMillis() - spawnTime;
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationBalloom = cycle % 3;
        } else {
            indexAnimationBalloom = 2 - cycle % 3;
        }
        if (indexDirection == 0 || indexDirection == 3) currentSprite = balloomAnimationRight[indexAnimationBalloom];
        else currentSprite = balloomAnimationLeft[indexAnimationBalloom];
    }

    public void collide(Object e) {
        if (e instanceof Flame) {
            if (isDead)return;
            Flame flame = (Flame) e;
            //check left right
            boolean xOverlapsLeftRight = (x <= flame.getX() + DEFAULT_SIZE * flame.getRight()) &&
                    (x >= flame.getX() - DEFAULT_SIZE * flame.getLeft());
            boolean yOverlapsLeftRight = (y < flame.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > flame.getY());
            if (xOverlapsLeftRight && yOverlapsLeftRight) {
                isDead = true;
                deadTime = (int) System.currentTimeMillis();
                return;
            }
            // check up down
            boolean xOverlapsUpDown = (x < flame.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > flame.getX());
            boolean yOverlapsUpDown = (y <= flame.getY() + DEFAULT_SIZE * flame.getDown()) &&
                    (y >= flame.getY() - DEFAULT_SIZE * flame.getUp());
            if (xOverlapsUpDown && yOverlapsUpDown) {
                isDead = true;
                deadTime = (int) System.currentTimeMillis();

            }
        }
    }
}
