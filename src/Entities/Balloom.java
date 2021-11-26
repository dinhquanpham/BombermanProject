package Entities;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import Graphics.Sprites;
import static Graphics.Sprites.*;
import static Main.GameBomberMan.map;


public class Balloom extends Entities {
    private int[] destination = new int[2];
    private ArrayList<Integer> way = new ArrayList<>();
    private int indexDirection = 4;
    private int indexAnimationBalloom = 0;
    private final int balloomSpeed = 1;
    private int dx[] = {0, -1, 0, 1, 0};
    private int dy[] = {-1, 0, 1, 0, 0};
    private boolean isDead, isEndAnimation;
    private int deadTime;

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
                        map[trueNextY][trueNextX] != 10 &&
                        map[trueNextY][trueNextX] != 11 &&
                        map[trueNextY][trueNextX] != 12 &&
                        map[trueNextY][trueNextX] != 2 && map[trueNextY][trueNextX] != -2 &&
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
            System.out.println(map[trueY][trueX]);
            if((trueNextX != trueX || trueNextY != trueY) &&
                    (map[trueNextY][trueNextX] == 3 || map[trueNextY][trueNextX] == -3)) {
                bfs();
                System.out.println("stop");
                indexDirection = 4;
            }
        }

        x += dx[indexDirection] * balloomSpeed;
        y += dy[indexDirection] * balloomSpeed;
        int existTime = (int) System.currentTimeMillis();
        int cycle = existTime / 200;
        if ((cycle / 3) % 2 == 0) {
            indexAnimationBalloom = cycle % 3;
        } else {
            indexAnimationBalloom = 2 - cycle % 3;
        }
        if (indexDirection == 4);
        else if (indexDirection == 0 || indexDirection == 3) currentSprite = balloomAnimationRight[indexAnimationBalloom];
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
