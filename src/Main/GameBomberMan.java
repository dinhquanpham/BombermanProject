package Main;

import Entities.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static Graphics.Sprites.*;
import static Graphics.TextMap.*;
import static Sound.Sound.*;


public class GameBomberMan extends JPanel implements KeyListener {
    public static final int R = 15;
    public static final int C = 31;

    public static final int windowWidth = 1500;
    public static final int windowHeight = 800;


    // window
    public static BufferedImage scene = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);

    //map
    public static int [][] map = new int[R][C];
    public boolean switchMap = true;
    public int currentMap = 1;

    // player
    public int playerX = DEFAULT_SIZE, playerY = DEFAULT_SIZE;
    public BomberMan player = new BomberMan(playerX, playerY, player_down);

    //bomb and flame
    private final ArrayList<Bomb> bombLists = new ArrayList<Bomb>();
    private final ArrayList<Flame> flameLists = new ArrayList<Flame>();
    private final ArrayList<Balloom> enemyLists = new ArrayList<Balloom>();

    //enemies
    Entities [][] object = new Entities[R][C];
    int usedBomb = 0;

    public void drawMap() {
        int balloom1X = 10 * DEFAULT_SIZE, balloom1Y = DEFAULT_SIZE;
        int balloom2X = 11 * DEFAULT_SIZE, balloom2Y = 13 * DEFAULT_SIZE;
        int balloom3X = 3 * DEFAULT_SIZE, balloom3Y = 7 * DEFAULT_SIZE;
        Balloom balloom1 = new Balloom(balloom1X, balloom1Y, balloom_right1);
        Balloom balloom2 = new Balloom(balloom2X, balloom2Y, balloom_right1);
        Balloom balloom3 = new Balloom(balloom3X, balloom3Y, balloom_right1);
        enemyLists.add(balloom1);
        enemyLists.add(balloom2);
        enemyLists.add(balloom3);
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                switch (map[i][j]) {
                    case 1: {
                        object[i][j] = new Wall(j * DEFAULT_SIZE, i * DEFAULT_SIZE, wall);
                        break;
                    }
                    case 2: {
                        object[i][j] = new Brick(j * DEFAULT_SIZE, i * DEFAULT_SIZE, brick);
                        break;
                    }
                    case 10: {
                        Items item = new Items(j * DEFAULT_SIZE, i * DEFAULT_SIZE, brick);
                        item.setIndex(10);
                        object[i][j] = item;
                        break;
                    }
                    case 11: {
                        Items item = new Items(j * DEFAULT_SIZE, i * DEFAULT_SIZE, brick);
                        item.setIndex(11);
                        object[i][j] = item;
                        break;
                    }
                    case 12: {
                        Items item = new Items(j * DEFAULT_SIZE, i * DEFAULT_SIZE, brick);
                        item.setIndex(12);
                        object[i][j] = item;
                        break;
                    }
                    case 69: {
                        object[i][j] = new Portal(j * DEFAULT_SIZE, i * DEFAULT_SIZE, portal);
                        break;
                    }
                    default: {
                        object[i][j] = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, grass);
                        break;
                    }
                }
            }
        }
    }

    public void handleMap() {
        if (switchMap || player.isEndDeadAnimation()) {
            switchMap = false;

            player.setX(playerX);
            player.setY(playerY);
            if(player.isEndDeadAnimation())player.resetBomberMan();

            bombLists.clear();
            flameLists.clear();
            enemyLists.clear();
            usedBomb = 0;

            if (currentMap == 1) {
                for (int i = 0; i < R; i++) {
                    System.arraycopy(map1[i], 0, map[i], 0, C);
                }
            } else {
                for (int i = 0; i < R; i++) {
                    System.arraycopy(map2[i], 0, map[i], 0, C);
                }
            }
            drawMap();
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
                if (object[i][j] instanceof Portal) {
                    if (enemyLists.isEmpty()) {
                        object[i][j].collide(player);
                        if (((Portal) object[i][j]).isSwitchMap()) {
                            switchMap = true;
                            currentMap++;
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
            for (Flame flame: flameLists) {
                if (flame.isFlaming()) {
                    player.collide(flame);
                    if (!enemyLists.isEmpty()) {
                        for (Balloom enemy : enemyLists) {
                            enemy.collide(flame);
                        }
                    }
                    flame.draw(scene.getGraphics());
                }
            }
        }
        enemyLists.removeIf(e -> e.isEndAnimation());
        if (!enemyLists.isEmpty()) {
            for (Balloom enemy : enemyLists) {
                player.collide(enemy);
                enemy.move();
                enemy.draw(scene.getGraphics());
            }
        }
        player.draw(scene.getGraphics());
    }
    public void handleGame() throws IOException, LineUnavailableException{
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
                } else {
                    for (int j = 0; j < bombLists.size(); j++) {
                        if (j == i)continue;
                        if (bombLists.get(j).isExploded()) {
                            bombLists.get(i).collide(flameLists.get(j));
                            if (bombLists.get(i).isExploded() && !bombLists.get(i).isChecked()) {
                                bombLists.get(i).setChecked(true);
                                flameLists.get(i).setExplodedTime((int) System.currentTimeMillis());
                                flameLists.get(i).setFlaming(true);
                                flameLists.get(i).check();
                                break;
                            }
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
                        usedBomb--;
                        for (int idX = 0; idX < R; idX++) {
                            for (int idY = 0; idY < C; idY++) {
                                if (map[idX][idY] == -2 || map[idX][idY] == 3 || map[idX][idY] == -3) {
                                    map[idX][idY] = 0;
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
            if (player.getPlayerBomb() - usedBomb == 0)return;
            int bombX = (player.getX() + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
            int bombY = (player.getY() + DEFAULT_SIZE / 2) / DEFAULT_SIZE;
            if (map[bombY][bombX] == -3)return;
            try {
                newBombSound.playSound(false);
            } catch (IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            usedBomb++;
            map[bombY][bombX] = -3;
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
            try {
                footSound.playSound(false);
            } catch (IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            player.setRight(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            try {
                footSound.playSound(false);
            } catch (IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            player.setLeft(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            try {
                footSound.playSound(false);
            } catch (IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            player.setUp(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            try {
                footSound.playSound(false);
            } catch (IOException | LineUnavailableException ex) {
                ex.printStackTrace();
            }
            player.setDown(false);
        }

    }

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
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
        try {
            mapSound.playSound(true);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        long timeSilent = 5;
        while (true) {
            long startTime = System.currentTimeMillis();
            myGame.handleMap();
            myGame.update();
            myGame.handleGame();
            myGame.draw();
            long endTime = System.currentTimeMillis();
            try {
                Thread.sleep(Math.max(timeSilent + startTime - endTime, 0));
            } catch(InterruptedException e) {
                System.out.println("Error");
            }
        }
    }
}