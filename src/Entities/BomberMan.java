package Entities;

import java.awt.*;
import java.io.IOException;

import Graphics.Sprites;

import javax.sound.sampled.LineUnavailableException;

import static Graphics.Sprites.*;
import static Main.GameBomberMan.map;
import static Sound.Sound.footSound;


public class BomberMan extends Entities {
    private boolean right, left, up, down, moving;
    private int indexAnimationPlayer = 0;
    private int framePlayer = 0;
    private int intervalPlayer = 10;
    private int playerSpeed = 1;
    private int playerBomb = 1;
    private int playerFlame = 1;
    private boolean isDead, isEndDeadAnimation;
    private int deadTime;
    public static Sprites[] playerAnimationLeft = {
            player_left,
            player_left_1,
            player_left_2
    };
    public static Sprites[] playerAnimationRight = {
            player_right,
            player_right_1,
            player_right_2
    };
    public static Sprites[] playerAnimationUp = {
            player_up,
            player_up_1,
            player_up_2
    };
    public static Sprites[] playerAnimationDown = {
            player_down,
            player_down_1,
            player_down_2
    };

    public static Sprites[] playerAnimationDead = {
            player_dead1,
            player_dead2,
            player_dead3
    };

    public BomberMan(int x, int y, Sprites sprite) {
        super(x, y, sprite);
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getIndexAnimationPlayer() {
        return indexAnimationPlayer;
    }

    public void setIndexAnimationPlayer(int indexAnimationPlayer) {
        this.indexAnimationPlayer = indexAnimationPlayer;
    }

    public int getFramePlayer() {
        return framePlayer;
    }

    public void setFramePlayer(int framePlayer) {
        this.framePlayer = framePlayer;
    }

    public int getIntervalPlayer() {
        return intervalPlayer;
    }

    public void setIntervalPlayer(int intervalPlayer) {
        this.intervalPlayer = intervalPlayer;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public int getPlayerBomb() {
        return playerBomb;
    }

    public void setPlayerBomb(int playerBomb) {
        this.playerBomb = playerBomb;
    }

    public int getPlayerFlame() {
        return playerFlame;
    }

    public void setPlayerFlame(int playerFlame) {
        this.playerFlame = playerFlame;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isEndDeadAnimation() {
        return isEndDeadAnimation;
    }

    public void setEndDeadAnimation(boolean endDeadAnimation) {
        isEndDeadAnimation = endDeadAnimation;
    }

    @Override
    public void draw(Graphics g) {
        if (!isDead) {
            g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                    Sprites.DEFAULT_SIZE, null);
        } else {
            int existTime = (int) System.currentTimeMillis() - deadTime;
            if (existTime < 600) {
                g.drawImage(playerAnimationDead[existTime / 200].getImage(), x, y, Sprites.DEFAULT_SIZE,
                        Sprites.DEFAULT_SIZE, null);
            } else {
                isEndDeadAnimation = true;
            }
        }
    }

    public void resetBomberMan() {
        playerSpeed = 1;
        playerBomb = 1;
        playerFlame = 1;
        isDead = false;
        isEndDeadAnimation = false;
    }
    public boolean isFreeToMove(int nextX, int nextY) {
        int trueNextX_1 = nextX / DEFAULT_SIZE;
        int trueNextY_1 = nextY / DEFAULT_SIZE;
        int trueNextX_2 = (nextX + DEFAULT_SIZE - 1) / DEFAULT_SIZE;
        int trueNextY_2 = nextY / DEFAULT_SIZE;
        int trueNextX_3 = nextX / DEFAULT_SIZE;
        int trueNextY_3 = (nextY + DEFAULT_SIZE - 1) / DEFAULT_SIZE;
        int trueNextX_4 = (nextX + DEFAULT_SIZE - 1) / DEFAULT_SIZE;
        int trueNextY_4 = (nextY + DEFAULT_SIZE - 1) / DEFAULT_SIZE;
        return !(map[trueNextY_1][trueNextX_1] > 0 ||
                map[trueNextY_2][trueNextX_2] > 0 ||
                map[trueNextY_3][trueNextX_3] > 0 ||
                map[trueNextY_4][trueNextX_4] > 0);
    }

    public void moveRight() {
        int trueSpeed = 0;
        int checkY = y % DEFAULT_SIZE;
        int X = (x + DEFAULT_SIZE) / DEFAULT_SIZE;
        int Y1 = (y / DEFAULT_SIZE);
        int Y2 = (y + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
        int Y3 = (y + DEFAULT_SIZE) / DEFAULT_SIZE;
        for (int i = 1; i <= playerSpeed; i++) {
            if (x >= DEFAULT_SIZE && isFreeToMove(x + i, y)) {
                trueSpeed = i;
            }
            else {
                break;
            }
        }
        if (checkY == 0) {
            x += trueSpeed;
            if (trueSpeed > 0) {
                moving = true;
            }
        }
    }

    public void moveLeft() {
        int trueSpeed = 0;
        for (int i = 1; i <= playerSpeed; i++) {
            if (x >= DEFAULT_SIZE && isFreeToMove(x - i, y)) {
                trueSpeed = i;
            }
            else {
                break;
            }
        }
        x -= trueSpeed;
        if (trueSpeed > 0) {
            moving = true;
        }
    }

    public void moveUp() {
        int trueSpeed = 0;
        for (int i = 1; i <= playerSpeed; i++) {
            if (x >= DEFAULT_SIZE && isFreeToMove(x, y - i)) {
                trueSpeed = i;
            }
            else {
                break;
            }
        }
        y -= trueSpeed;
        if (trueSpeed > 0) {
            moving = true;
        }
    }

    public void moveDown() {
        int trueSpeed = 0;
        for (int i = 1; i <= playerSpeed; i++) {
            if (x >= DEFAULT_SIZE && isFreeToMove(x, y + i)) {
                trueSpeed = i;
            }
            else {
                break;
            }
        }
        y += trueSpeed;
        if (trueSpeed > 0) {
            moving = true;
        }
    }

    public void move() {
        if (isDead)return;
        moving = false;
        if (right) {
            int modY = y % DEFAULT_SIZE;
            int checkY = y / DEFAULT_SIZE;
            int checkX = x / DEFAULT_SIZE;
            int floorY = checkY * DEFAULT_SIZE;
            int roofY = floorY + DEFAULT_SIZE;
            if (modY == 0) {
                moveRight();
            }
            else {
                if (map[checkY + 1][checkX + 1] == 2) {
                    return;
                }
                if (map[checkY][checkX + 1]  == 0) {
                    if (y < floorY + DEFAULT_SIZE / 2 && y > floorY) {
                        if (y - playerSpeed >= floorY) {
                            moveUp();
                        }
                        else {
                            y = floorY;
                            moveUp();
                        }
                    }
                }
                if (map[checkY][checkX + 1] == 1) {
                    if (y > floorY + DEFAULT_SIZE / 2  && y < roofY) {
                        if (y - playerSpeed >= floorY) {
                            moveDown();
                        }
                        else {
                            y = floorY;
                            moveDown();
                        }
                    }
                }

            }
        }
        if (left) {
            int modY = y % DEFAULT_SIZE;
            int checkY = y / DEFAULT_SIZE;
            int checkX = x / DEFAULT_SIZE;
            int floorY = checkY * DEFAULT_SIZE;
            int roofY = floorY + DEFAULT_SIZE;
            if (modY == 0) {
                moveLeft();
            }
            else {
                if (map[checkY + 1][checkX - 1] == 2) {
                    return;
                }
                if (map[checkY][checkX - 1]  == 0) {
                    if (y < floorY + DEFAULT_SIZE / 2 && y > floorY) {
                        if (y - playerSpeed >= floorY) {
                            moveUp();
                        }
                        else {
                            y = floorY;
                            moveUp();
                        }
                    }
                }
                if (map[checkY][checkX - 1] == 1) {
                    if (y > floorY + DEFAULT_SIZE / 2  && y < roofY) {
                        if (y - playerSpeed >= floorY) {
                            moveDown();
                        }
                        else {
                            y = floorY;
                            moveDown();
                        }
                    }
                }
            }
        }
        if (down) {
            int modX = x % DEFAULT_SIZE;
            int checkY = y / DEFAULT_SIZE;
            int checkX = x / DEFAULT_SIZE;
            int floorX = checkX * DEFAULT_SIZE;
            int roofX = floorX + DEFAULT_SIZE;
            if (modX == 0) {
                moveDown();
            }
            else {
                if (map[checkY + 1][checkX - 1] == 2) {
                    return;
                }
                if (map[checkY + 1][checkX]  == 0) {
                    if (x < floorX + DEFAULT_SIZE / 2 && x > floorX) {
                        if (x - playerSpeed >= floorX) {
                            moveLeft();
                        }
                        else {
                            x = floorX;
                            moveLeft();
                        }
                    }
                }
                if (map[checkY + 1][checkX] == 1) {
                    if (x > floorX + DEFAULT_SIZE / 2  && x < roofX) {
                        if (x - playerSpeed >= floorX) {
                            moveRight();
                        }
                        else {
                            x = floorX;
                            moveRight();
                        }
                    }
                }
            }
        }
        if (up) {
            int modX = x % DEFAULT_SIZE;
            int checkY = y / DEFAULT_SIZE;
            int checkX = x / DEFAULT_SIZE;
            int floorX = checkX * DEFAULT_SIZE;
            int roofX = floorX + DEFAULT_SIZE;
            if (modX == 0) {
                moveUp();
            }
            else {
                if (map[checkY - 1][checkX + 1] == 2) {
                    return;
                }
                if (map[checkY - 1][checkX]  == 0) {
                    if (x < floorX + DEFAULT_SIZE / 2 && x > floorX) {
                        if (x - playerSpeed >= floorX) {
                            moveLeft();
                        }
                        else {
                            x = floorX;
                            moveLeft();
                        }
                    }
                }
                if (map[checkY - 1][checkX] == 1) {
                    if (x > floorX + DEFAULT_SIZE / 2  && x < roofX) {
                        if (x - playerSpeed >= floorX) {
                            moveRight();
                        }
                        else {
                            x = floorX;
                            moveRight();
                        }
                    }
                }
            }
        }
        if (moving) {
            framePlayer++;
            if (framePlayer > intervalPlayer) {
                framePlayer = 0;
                indexAnimationPlayer++;
                if (indexAnimationPlayer > 2) {
                    indexAnimationPlayer = 0;
                    try {
                        footSound.playSound(false);
                    } catch (IOException | LineUnavailableException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            if (right) {
                setCurrentSprite(playerAnimationRight[indexAnimationPlayer]);
            } else if (left) {
                setCurrentSprite(playerAnimationLeft[indexAnimationPlayer]);
            } else if (up) {
                setCurrentSprite(playerAnimationUp[indexAnimationPlayer]);
            } else if (down) {
                setCurrentSprite(playerAnimationDown[indexAnimationPlayer]);
            }
        }
        setX(x);
        setY(y);
    }

    @Override
    public void collide(Object e) {
        if (isDead)return;
        if (e instanceof Items) {
            Items items = (Items) e;
            if (!items.isDestroyed() || items.isUsed())return;
            boolean xOverlaps = (x < items.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > items.getX());
            boolean yOverlaps = (y < items.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > items.getY());
            if (xOverlaps && yOverlaps) {
                switch (items.getIndex()) {
                    case -10: {
                        playerBomb++;
                        break;
                    }
                    case -11: {
                        playerFlame++;
                        break;
                    }
                    default: {
                        playerSpeed++;
                        break;
                    }
                }
            }
        }
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
                    deadTime = (int) System.currentTimeMillis();
                    return;
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
                    deadTime = (int) System.currentTimeMillis();
                    return;
                }
            }
        }
        if (e instanceof Balloom) {
            Balloom enemy = (Balloom) e;
            boolean xOverlaps = (x < enemy.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > enemy.getX());
            boolean yOverlaps = (y < enemy.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > enemy.getY());
            if (xOverlaps && yOverlaps) {
                deadTime = (int) System.currentTimeMillis();
                isDead = true;
            }
        }
        if (e instanceof Oneal) {
            Oneal enemy = (Oneal) e;
            boolean xOverlaps = (x < enemy.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > enemy.getX());
            boolean yOverlaps = (y < enemy.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > enemy.getY());
            if (xOverlaps && yOverlaps) {
                deadTime = (int) System.currentTimeMillis();
                isDead = true;
            }
        }
        if (e instanceof Kondoria) {
            Kondoria enemy = (Kondoria) e;
            boolean xOverlaps = (x < enemy.getX() + DEFAULT_SIZE) && (x + DEFAULT_SIZE > enemy.getX());
            boolean yOverlaps = (y < enemy.getY() + DEFAULT_SIZE) && (y + DEFAULT_SIZE > enemy.getY());
            if (xOverlaps && yOverlaps) {
                deadTime = (int) System.currentTimeMillis();
                isDead = true;
            }
        }
    }
}
