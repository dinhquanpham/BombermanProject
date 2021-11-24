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

    private final ArrayList<Bomb> bombLists = new ArrayList<Bomb>();
    private final ArrayList<Flame> flameLists = new ArrayList<Flame>();
    private final ArrayList<Balloom> enemyLists = new ArrayList<Balloom>();

    //enemy
    Entities [][] object = new Entities[R][C];
    int bombUsed;

    public void drawMap() {
        int balloom1X = 10 * 48, balloom1Y = 48;
        int balloom2X = 11 * 48, balloom2Y = 13 * 48;
        int balloom3X = 3 * 48, balloom3Y = 7 * 48;
        Balloom balloom1 = new Balloom(balloom1X, balloom1Y, balloom_right1);
        Balloom balloom2 = new Balloom(balloom2X, balloom2Y, balloom_right1);
        Balloom balloom3 = new Balloom(balloom3X, balloom3Y, balloom_right1);
        enemyLists.add(balloom1);
        enemyLists.add(balloom2);
        enemyLists.add(balloom3);
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                switch (map1[i][j]) {
                    case 1: {
                        object[i][j] = new Wall(j * 48, i * 48, wall);
                        break;
                    }
                    case 2: {
                        object[i][j] = new Brick(j * 48, i * 48, brick);
                        break;
                    }
                    case 10: {
                        Items item = new Items(j * 48, i * 48, brick);
                        item.setIndex(10);
                        object[i][j] = item;
                        break;
                    }
                    case 11: {
                        Items item = new Items(j * 48, i * 48, brick);
                        item.setIndex(11);
                        object[i][j] = item;
                        break;
                    }
                    case 12: {
                        Items item = new Items(j * 48, i * 48, brick);
                        item.setIndex(12);
                        object[i][j] = item;
                        break;
                    }
                    default: {
                        object[i][j] = new Grass(j * 48, i * 48, grass);
                        break;
                    }
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
                    for (Flame flame : flameLists) {
                        if (flame.isFlaming()) {
                            object[i][j].collide(flame);
                        }
                    }
                }
                if (object[i][j] instanceof Items) {
                    player.collide(object[i][j]);
                    object[i][j].collide(player);
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
            for (Flame flame: flameLists) {
                if (flame.isFlaming()) {
                    flame.draw(scene.getGraphics());
                    if (!enemyLists.isEmpty()) {
                        for (Balloom enemy : enemyLists) {
                            enemy.collide(flame);
                        }
                    }
                }
            }
        }
        enemyLists.removeIf(e -> e.isEndAnimation());
        if (!enemyLists.isEmpty()) {
            for (Balloom enemy : enemyLists) {
                enemy.move();
                enemy.draw(scene.getGraphics());
            }
        }
        player.draw(scene.getGraphics());
    }
    public void handle() {
        player.move();
        if (!bombLists.isEmpty()) {
            for (int i = 0; i < bombLists.size(); i++) {
                bombLists.get(i).explode(player);
                if (bombLists.get(i).isExploded()) {
                    if (!bombLists.get(i).isChecked()) {
                        bombLists.get(i).setChecked(true);
                        flameLists.get(i).setExplodedTime((int) System.currentTimeMillis());
                        flameLists.get(i).setFlaming(true);
                        flameLists.get(i).check();
                    }
                    for (int j = i + 1; j < bombLists.size(); j++) {
                        if (bombLists.get(j).isChecked()) {
                            break;
                        }
                        bombLists.get(j).explode(player);
                        bombLists.get(j).collide(flameLists.get(i));
                        if (bombLists.get(j).isExploded()) {
                            bombLists.get(j).setChecked(true);
                            flameLists.get(j).setExplodedTime((int) System.currentTimeMillis());
                            flameLists.get(j).setFlaming(true);
                            flameLists.get(j).check();
                        }
                    }
                }
            }
        }
        if (!flameLists.isEmpty()) {
            for (int i = 0; i < flameLists.size(); i++) {
                if (flameLists.get(i).isFlaming()) {
                    flameLists.get(i).handleFlame();
                    if (flameLists.get(i).isEndFlame()) {
                        flameLists.remove(i);
                        bombLists.remove(i);
                        i--;
                        bombUsed--;
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
            if (player.getPlayerBomb() - bombUsed == 0)return;
            bombUsed++;
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
            newFlame.setSizeFlames(player.getPlayerFlame());
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
            myGame.handle();
            myGame.draw();
        }
    }
}