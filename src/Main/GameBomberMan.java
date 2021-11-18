package Main;

import Entities.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import static Graphics.Sprites.*;
import static Graphics.TextMap.*;

public class GameBomberMan extends JPanel implements KeyListener {
    public static final int R = 15;
    public static final int C = 31;

    public static final int windowWidth = 1512;
    public static final int windowHeight = 768;

    // window
    public BufferedImage scene = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);

    // player
    public int playerX = 48, playerY = 48;
    public BomberMan player = new BomberMan(playerX, playerY, player_down);

    //bomb
    Bomb currentBomb;
    Flame currentFlame;
    Entities [][] object = new Entities[R][C];

    public void drawMap() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (map1[i][j] == 1) {
                    object[i][j] = new Wall(j * 48, i * 48, wall);
                } else if (map1[i][j] == 0) {
                    object[i][j] = new Grass(j * 48, i * 48, grass);
                } else if (map1[i][j] == 2) {
                    object[i][j] = new Brick(j * 48, i * 48, brick);
                } else {
                    object[i][j] = new Grass(j * 48, i * 48, grass);
                }
            }
        }
    }
    public void draw() {
        this.getGraphics().drawImage(scene, 0, 0, null);
    }

    public void update() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (currentFlame != null) {
                    object[i][j].collide(currentFlame);
                }
                object[i][j].draw(scene.getGraphics());
            }
        }
        if (currentBomb != null) currentBomb.draw(scene.getGraphics());
        if (currentFlame != null) {
            if (currentFlame.isFlaming()) {
                currentFlame.draw(scene.getGraphics());
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
            player.setRight(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setLeft(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.setUp(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDown(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (currentBomb == null) {
                int bombX = (player.getX() + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
                int bombY = (player.getY() + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
                bombX *= DEFAULT_SIZE;
                bombY *= DEFAULT_SIZE;
                currentBomb = new Bomb(bombX, bombY, bomb);
                currentBomb.setPlantedTime((int)System.currentTimeMillis());
                currentFlame = new Flame(bombX, bombY, bomb_exploded);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.setRight(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.setLeft(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.setUp(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.setDown(false);
        }

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocation(25, 50);
        frame.setSize(windowWidth, windowHeight);
        GameBomberMan myGame = new GameBomberMan();
        frame.setFocusable(true);
        myGame.requestFocusInWindow();
        frame.addKeyListener(myGame);
        frame.add(myGame);
        frame.setVisible(true);
        myGame.drawMap();
        while (true) {
            myGame.update();
            myGame.player.move(myGame);
            if (myGame.currentBomb != null) {
                myGame.currentBomb.explode(myGame);
                if (myGame.currentBomb.isExploded()) {
                    myGame.currentFlame.setExplodedTime((int) System.currentTimeMillis());
                    myGame.currentFlame.setFlaming(true);
                    myGame.currentFlame.check();
                    myGame.currentBomb = null;
                }
            }
            if (myGame.currentFlame != null) {
                if(myGame.currentFlame.isFlaming()) {
                    myGame.currentFlame.handleFlame();
                }
                if (myGame.currentFlame.isEndFlame()) {
                    myGame.currentFlame = null;
                }
            }
            myGame.draw();
        }
    }
}