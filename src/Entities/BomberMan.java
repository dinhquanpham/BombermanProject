package Entities;

import java.awt.*;
import Graphics.Sprites;
import static Graphics.Sprites.*;
import static Graphics.TextMap.*;
import Main.GameBomberMan;


public class BomberMan extends Entities {
    private boolean right, left, up, down, moving;
    private int indexAnimationPlayer = 0;
    private int framePlayer = 0;
    private int intervalPlayer = 10;
    private int playerSpeed = 1;
    private int playerBomb = 2;
    private int playerFlame = 2;
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

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);
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
        return !(map1[trueNextY_1][trueNextX_1] > 0 ||
                map1[trueNextY_2][trueNextX_2] > 0 ||
                map1[trueNextY_3][trueNextX_3] > 0 ||
                map1[trueNextY_4][trueNextX_4] > 0);
    }

    public void move() {
        moving = false;
        if (right) {
            int trueSpeed = 0;
            for (int i = 1; i <= playerSpeed; i++) {
                if (x >= DEFAULT_SIZE && isFreeToMove(x + i, y)) {
                    trueSpeed = i;
                }
                else {
                    break;
                }
            }
            x += trueSpeed;
            if (trueSpeed > 0) {
                moving = true;
            }
        }

        if (left) {
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
        if (down) {
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
        if (up) {
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
        if (moving) {
            framePlayer++;
            if (framePlayer > intervalPlayer) {
                framePlayer = 0;
                indexAnimationPlayer++;
                if (indexAnimationPlayer > 2) {
                    indexAnimationPlayer = 0;
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
    }
}
