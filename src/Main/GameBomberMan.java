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

    public void draw() {
        this.getGraphics().drawImage(scene, 0, 0, null);
    }

    public void createMap() {
        int bomCount = 0;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                Entities object;
                if (map1[i][j] == 1) {
                    object = new Wall(j * 48, i * 48, wall);
                } else if (map1[i][j] == 0) {
                    object = new Grass(j * 48, i * 48, grass);
                } else if (map1[i][j] == 2) {
                    object = new Brick(j * 48, i * 48, brick);
                } else {
                    object = new Grass(j * 48, i * 48, grass);
                }
                object.draw(scene.getGraphics());
            }
        }
        if (currentBomb != null) currentBomb.draw(scene.getGraphics());
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
                int bombX = (player.getX() + 24) / 48;
                int bombY = (player.getY() + 24) / 48;
                bombX *= 48;
                bombY *= 48;
                currentBomb = new Bomb(bombX, bombY, bomb);
                currentBomb.setPlantedTime((int)System.currentTimeMillis());
                //map1[bombY / 48][bombX / 48] = 3;
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
        frame.setLocation(150, 50);
        frame.setSize(windowWidth, windowHeight);
        GameBomberMan myGame = new GameBomberMan();
        frame.setFocusable(true);
        myGame.requestFocusInWindow();
        frame.addKeyListener(myGame);
        frame.add(myGame);
        frame.setVisible(true);

        while (true) {
            myGame.createMap();
            myGame.player.move(myGame);
            if (myGame.currentBomb != null) {
                myGame.currentBomb.explode(myGame);
                if (myGame.currentBomb.isExplored()) myGame.currentBomb = null;
            }
            myGame.draw();
            System.out.println(myGame.player.getX() + " " + myGame.player.getY());
        }
    }
}