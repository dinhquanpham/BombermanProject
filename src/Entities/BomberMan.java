package Entities;

import java.awt.*;
import Graphics.Sprites;
import static Graphics.Sprites.*;
import static Graphics.TextMap.*;
import Main.GameBomberMan;


public class BomberMan extends Entities {
    private boolean right, left, up, down, moving;
    private int indexAnimationPlayer = 0, framePlayer = 0, intervalPlayer = 10, playerSpeed = 1;
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

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE * 3,
                Sprites.DEFAULT_SIZE * 3, null);
    }

    public boolean isFreeToMove(int nextX, int nextY) {
        int trueNextX_1 = nextX / 48;
        int trueNextY_1 = nextY / 48;
        int trueNextX_2 = (nextX + 48 - 1) / 48;
        int trueNextY_2 = nextY / 48;
        int trueNextX_3 = nextX / 48;
        int trueNextY_3 = (nextY + 48 - 1) / 48;
        int trueNextX_4 = (nextX + 48 - 1) / 48;
        int trueNextY_4 = (nextY + 48 - 1) / 48;
        return !(map1[trueNextY_1][trueNextX_1] == 1 || map1[trueNextY_1][trueNextX_1] == 2 || map1[trueNextY_1][trueNextX_1] == 3 ||
                map1[trueNextY_2][trueNextX_2] == 1 || map1[trueNextY_2][trueNextX_2] == 2 || map1[trueNextY_2][trueNextX_2] == 3 ||
                map1[trueNextY_3][trueNextX_3] == 1 || map1[trueNextY_3][trueNextX_3] == 2 || map1[trueNextY_3][trueNextX_3] == 3 ||
                map1[trueNextY_4][trueNextX_4] == 1 || map1[trueNextY_4][trueNextX_4] == 2 || map1[trueNextY_4][trueNextX_4] == 3);
    }

    public void move(GameBomberMan game) {
        moving = false;
        if (right) {
            if (x <= game.windowWidth - 48 * 3 + 24 && isFreeToMove(x + playerSpeed, y)) {
                x += playerSpeed;
                moving = true;
            }
        }
        if (left) {
            if (x >= 48 && isFreeToMove(x - playerSpeed, y)) {
                x -= playerSpeed;
                moving = true;
            }
        }
        if (down) {
            if (y <= game.windowHeight - 48 * 3 && isFreeToMove(x, y + playerSpeed)) {
                y += playerSpeed;
                moving = true;
            }
        }
        if (up) {
            if (y >= 48 && isFreeToMove(x, y - playerSpeed)) {
                y -= playerSpeed;
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
}
