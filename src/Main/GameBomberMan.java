package Main;

import Entities.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Graphics.Sprites.*;
import static Graphics.TextMap.*;

public class GameBomberMan extends JPanel implements KeyListener {
    public static final int R = 15;
    public static final int C = 31;

    public static final int windowWidth = 1512;
    public static final int windowHeight = 768;

    // window
    public static BufferedImage scene = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);

    // player
    public int playerX = 48, playerY = 48;
    public BomberMan player = new BomberMan(playerX, playerY, player_down);

    private ArrayList<Bomb> bombLists = new ArrayList<Bomb>();
    private ArrayList<Flame> flameLists = new ArrayList<Flame>();
    //enemy
    public int balloom1X = 10 * 48, balloom1Y = 1 * 48;
    public Balloom balloom1 = new Balloom(balloom1X, balloom1Y, balloom_right1);

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
                if (!flameLists.isEmpty()) {
                    for (int id = 0; id < flameLists.size(); id++) {
                        if(flameLists.get(id).isFlaming()) {
                            object[i][j].collide(flameLists.get(id));
                        }
                    }
                }
                object[i][j].draw(scene.getGraphics());
            }
        }
        if (!bombLists.isEmpty()) {
            for (Bomb bomb : bombLists) {
                bomb.draw(scene.getGraphics());
            }
        }
        if (!flameLists.isEmpty()) {
            for (int i = 0; i < bombLists.size(); i++) {
                if (flameLists.get(i).isFlaming()) {
                    flameLists.get(i).draw(scene.getGraphics());
                }
            }
        }
        balloom1.move();
        balloom1.draw(scene.getGraphics());
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
            int bombX = (player.getX() + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
            int bombY = (player.getY() + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
            if (map1[bombY][bombX] == -3)return;
            map1[bombY][bombX] = -3;
            bombX *= DEFAULT_SIZE;
            bombY *= DEFAULT_SIZE;
            Bomb newBomb = new Bomb(bombX, bombY, bomb);
            newBomb.setPlantedTime((int)System.currentTimeMillis());
            bombLists.add(newBomb);
            Flame newFlame = new Flame(bombX, bombY, bomb_exploded);
            flameLists.add(newFlame);
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
        frame.setLocation(50, 50);
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
            if (!myGame.bombLists.isEmpty()) {
                for (int i = 0; i < myGame.bombLists.size(); i++) {
                    myGame.bombLists.get(i).explode(myGame);
                    if (myGame.bombLists.get(i).isExploded()) {
                        if (!myGame.bombLists.get(i).isChecked()) {
                            myGame.bombLists.get(i).setChecked(true);
                            myGame.flameLists.get(i).setExplodedTime((int) System.currentTimeMillis());
                            myGame.flameLists.get(i).setFlaming(true);
                            myGame.flameLists.get(i).check();
                        }
                        for (int j = i + 1; j < myGame.bombLists.size(); j++) {
                            if (myGame.bombLists.get(j).isChecked()) {
                                break;
                            }
                            myGame.bombLists.get(j).explode(myGame);
                            myGame.bombLists.get(j).collide(myGame.flameLists.get(i));
                            if (myGame.bombLists.get(j).isExploded()) {
                                myGame.bombLists.get(j).setChecked(true);
                                myGame.flameLists.get(j).setExplodedTime((int) System.currentTimeMillis());
                                myGame.flameLists.get(j).setFlaming(true);
                                myGame.flameLists.get(j).check();
                            }
                        }
                    }
                }
            }
            if (!myGame.flameLists.isEmpty()) {
                for (int i = 0; i < myGame.flameLists.size(); i++) {
                    if (myGame.flameLists.get(i).isFlaming()) {
                        myGame.flameLists.get(i).handleFlame();
                        if (myGame.flameLists.get(i).isEndFlame()) {
                            myGame.flameLists.remove(i);
                            myGame.bombLists.remove(i);
                            i--;
                            for (int idX = 0; idX < R; idX++) {
                                for (int idY = 0; idY < C; idY++) {
                                    if (map1[idX][idY] == -2 || map1[idX][idY] == 3 || map1[idX][idY] == -3) {
                                        map1[idX][idY] = 0;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            myGame.draw();
        }
    }
}