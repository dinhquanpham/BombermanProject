package Entities;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import Graphics.Sprites;

import javax.sound.sampled.LineUnavailableException;

import static Graphics.Sprites.*;
import static Main.GameBomberMan.map;
import static Sound.Sound.mobDiedSound;


public class Kondoria extends Entities {
    private int[] destination = new int[2];
    private ArrayList<Integer> way = new ArrayList<>();
    private int indexDirection = 4;
    private int indexAnimationKondoria = 0;
    private int kondoriaSpeed = 1;
    private int dx[] = {0, -1, 0, 1, 0};
    private int dy[] = {-1, 0, 1, 0, 0};
    private boolean isDead, isEndAnimation;
    private int deadTime, getScore = 500;

    public static Sprites[] kondoriaAnimationLeft = {
            kondoria_left1,
            kondoria_left2,
            kondoria_left3
    };

    public static Sprites[] kondoriaAnimationRight = {
            kondoria_right1,
            kondoria_right2,
            kondoria_right3
    };
    public static Sprites[] kondoriaAnimationDead = {
            mob_dead1,
            mob_dead2,
            mob_dead3
    };

    public Kondoria(int x, int y, Sprites sprite) {
        super(x, y, sprite);
        destination[0] = x / 48;
        destination[1] = y / 48;
    }

    public void draw(Graphics g) {
        if (!isDead) {

            g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        } else {
            int existTime = (int) System.currentTimeMillis() - deadTime;
            if (existTime < 300) {
                g.drawString("500", x + DEFAULT_SIZE / 2, y + DEFAULT_SIZE / 2);
                g.drawImage(kondoriaAnimationDead[existTime / 100].getImage(), x, y, Sprites.DEFAULT_SIZE,
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

    public int getGetScore() {
        return getScore;
    }

    public void setGetScore(int getScore) {
        this.getScore = getScore;
    }

    public void bfs() {
        Queue<int[]> q = new LinkedList<>();
        ArrayList<int[]> options = new ArrayList<>();
        int[][] bfs = new int[15][31];
        int[][] trace = new int[15][31];
        int trueX = x / DEFAULT_SIZE;
        int trueY = y / DEFAULT_SIZE;
        int[] u = {trueX, trueY};
        q.offer(u);
        bfs[u[1]][u[0]] = 1;
        while ((u = q.poll()) != null) {
            for (int i = 0; i <= 3; i++) {
                int trueNextX = u[0] + dx[i];
                int trueNextY = u[1] + dy[i];
                if (map[trueNextY][trueNextX] != 1 &&
                        map[trueNextY][trueNextX] != 3 && map[trueNextY][trueNextX] != -3 &&
                        bfs[trueNextY][trueNextX] != 1) {
                    int[] v = {trueNextX, trueNextY};
                    options.add(v);
                    trace[trueNextY][trueNextX] = i;
                    q.offer(v);
                    bfs[trueNextY][trueNextX] = 1;
                }
            }
        }
        if(options.size() > 0) {
            int rand = new Random().nextInt(options.size());
            //kondoriaSpeed = new Random().nextInt(2);
            destination = options.get(rand);
        } else {
            return;
        }
        int curX = destination[0];
        int curY = destination[1];
        while (curX != trueX || curY != trueY) {
            int direction = trace[curY][curX];
            way.add(direction);
            curX = curX - dx[direction];
            curY = curY - dy[direction];
        }
    }

    public void move() {
        if (isDead)return;
        int trueX = x / DEFAULT_SIZE;
        int trueY = y / DEFAULT_SIZE;
        if (x % 48 == 0 && y % 48 == 0) {
            if ((trueX == destination[0] && trueY == destination[1])) {
                bfs();
                indexDirection = 4;
            } else {
                indexDirection = way.get(way.size() - 1);
                way.remove(way.size() - 1);
            }
            int trueNextX = trueX + dx[indexDirection];
            int trueNextY = trueY + dy[indexDirection];
            if((trueNextX != trueX || trueNextY != trueY) &&
                    (map[trueNextY][trueNextX] == 3 || map[trueNextY][trueNextX] == -3)) {
                bfs();
                indexDirection = 4;
            }
        }

        x += dx[indexDirection] * kondoriaSpeed;
        y += dy[indexDirection] * kondoriaSpeed;
        int existTime = (int) System.currentTimeMillis();
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationKondoria = cycle % 3;
        } else {
            indexAnimationKondoria = 2 - cycle % 3;
        }
        if (indexDirection == 4);
        else if (indexDirection == 0 || indexDirection == 3) currentSprite = kondoriaAnimationRight[indexAnimationKondoria];
        else currentSprite = kondoriaAnimationLeft[indexAnimationKondoria];
    }

    public void collide(Object e) {
        if (isDead)return;
        if (e instanceof Flame) {
            Flame flame = (Flame) e;
            boolean xOverlapsLeftRight = (x <= flame.getX() + DEFAULT_SIZE * flame.getRight()) &&
                    (x >= flame.getX() - DEFAULT_SIZE * flame.getLeft());
            boolean yOverlapsLeftRight = (y < flame.getY() + DEFAULT_SIZE) &&
                    (y > flame.getY() - DEFAULT_SIZE);
            if (xOverlapsLeftRight && yOverlapsLeftRight) {
                int left = Math.max(x, flame.getX() - DEFAULT_SIZE * flame.getLeft());
                int right = Math.min(x + DEFAULT_SIZE, flame.getX() + DEFAULT_SIZE + DEFAULT_SIZE * flame.getRight());
                int bottom = Math.max(y, flame.getY());
                int top = Math.min(y + DEFAULT_SIZE, flame.getY() + DEFAULT_SIZE);
                int height = top - bottom;
                int width = right - left;
                if (height * width >= DEFAULT_SIZE * DEFAULT_SIZE / 4) {
                    isDead = true;
                }
            }
            // check up down
            boolean xOverlapsUpDown = (x < flame.getX() + DEFAULT_SIZE) &&
                    (x > flame.getX() - DEFAULT_SIZE);
            boolean yOverlapsUpDown = (y <= flame.getY() + DEFAULT_SIZE * flame.getDown()) &&
                    (y >= flame.getY() - DEFAULT_SIZE * flame.getUp());
            if (xOverlapsUpDown && yOverlapsUpDown) {
                int left = Math.max(x, flame.getX());
                int right = Math.min(x + DEFAULT_SIZE, flame.getX() + DEFAULT_SIZE);
                int bottom = Math.max(y, flame.getY() - DEFAULT_SIZE * flame.getUp());
                int top = Math.min(y + DEFAULT_SIZE, flame.getY() + DEFAULT_SIZE + DEFAULT_SIZE * flame.getDown());
                int height = top - bottom;
                int width = right - left;
                if (height * width >= DEFAULT_SIZE * DEFAULT_SIZE / 4) {
                    isDead = true;
                }
            }
            if (isDead) {
                deadTime = (int) System.currentTimeMillis();
                try {
                    mobDiedSound.playSound(false);
                } catch (IOException | LineUnavailableException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
