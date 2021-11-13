import Entities.Entities;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import java.io.IOException;

import java.util.ArrayList;

import Entities.*;

import Graphics.Sprites;

import static Graphics.Sprites.*;
import static Graphics.TextMap.*;

public class Main extends JPanel implements KeyListener {
    public static final int R = 15;
    public static final int C = 31;

    public static final int windowWidth = 1512;
    public static final int windowHeight = 768;

    // window
    public BufferedImage scene = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);


    // player
    boolean right, left, up, down, moving;

    int playerX = 48, playerY = 48;

    int speed = 1;

    int indexAnimation = 0, framePlayer = 0, intervalPlayer = 10;

    Entities player = new BomberMan(playerX, playerY, player_down);


    public void draw() {
        this.getGraphics().drawImage(scene, 0, 0, null);

    }

    public boolean isFreeToMove(int nextX, int nextY) {
        int trueNextX_1 = nextX / 48;
        int trueNextY_1 = nextY / 48;
        int trueNextX_2 = (nextX + 48 - 1) / 48;
        int trueNextY_2 = nextY / 48;
        int trueNextX_3 =  nextX / 48;
        int trueNextY_3 = (nextY + 48 - 1) / 48;
        int trueNextX_4 = (nextX + 48 - 1) / 48;
        int trueNextY_4 = (nextY + 48 - 1) / 48;
        return !(map1[trueNextY_1][trueNextX_1] == 1 || map1[trueNextY_1][trueNextX_1] == 2 ||
                map1[trueNextY_2][trueNextX_2] == 1 || map1[trueNextY_2][trueNextX_2] == 2 ||
                map1[trueNextY_3][trueNextX_3] == 1 || map1[trueNextY_3][trueNextX_3] == 2 ||
                map1[trueNextY_4][trueNextX_4] == 1 || map1[trueNextY_4][trueNextX_4] == 2);
    }

    public void update() {
        moving = false;

        if (right) {
            if (playerX <= windowWidth - 48 * 3 + 20 && isFreeToMove(playerX + speed, playerY)) {
                playerX += speed;
                moving = true;
            }
        }
        if (left) {
            if (playerX >= 48 && isFreeToMove(playerX - speed,playerY)) {
                playerX -= speed;
                moving = true;
            }
        }
        if (down) {
            if (playerY <= windowHeight - 48 * 3 && isFreeToMove(playerX,playerY + speed)) {
                playerY += speed;
                moving = true;
            }

        }
        if (up) {
            if (playerY >= 48 && isFreeToMove(playerX, playerY - speed)) {
                playerY -= speed;
                moving = true;
            }
        }

        if (moving) {
            framePlayer++;
            if (framePlayer > intervalPlayer) {
                framePlayer = 0;
                indexAnimation++;
                if (indexAnimation > 2) {
                    indexAnimation = 0;
                }
            }
            if (right) {
                player.setCurrentSprite(playerAnimationRight[indexAnimation]);
            } else if (left) {
                player.setCurrentSprite(playerAnimationLeft[indexAnimation]);
            } else if (up) {
                player.setCurrentSprite(playerAnimationUp[indexAnimation]);
            } else if (down) {
                player.setCurrentSprite(playerAnimationDown[indexAnimation]);
            }
        }
        player.setX(playerX);
        player.setY(playerY);
    }

    public void createMap() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                Entities object;
                if (map1[i][j] == 1) {
                    object = new Wall(j * 48, i * 48, wall);
                } else if (map1[i][j] == 0) {
                    object = new Grass(j * 48, i * 48, grass);
                } else {
                    object = new Brick(j * 48, i * 48, brick);
                }
                object.draw(scene.getGraphics());
            }
        }
        player.draw(scene.getGraphics());

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            down = false;
        }
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setTitle("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(150, 50);
        frame.setSize(windowWidth, windowHeight);
        Main myGame = new Main();
        frame.setFocusable(true);
        myGame.requestFocusInWindow();
        frame.addKeyListener(myGame);
        frame.add(myGame);
        frame.setVisible(true);

        while (true) {
            myGame.createMap();
            myGame.update();
            myGame.draw();
        }
    }
}