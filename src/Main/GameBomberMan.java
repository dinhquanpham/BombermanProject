package Main;

import Entities.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Graphics.Sprites;
import static Graphics.Sprites.*;
import static Graphics.TextMap.*;
import static Sound.Sound.*;


public class GameBomberMan extends JPanel implements KeyListener, MouseListener {
    public static final int R = 15;
    public static final int C = 31;

    public static final int windowWidth = 1488;
    public static final int windowHeight = 760;

    public static int gameStatus = 0;

    // window
    public static BufferedImage scene = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
    public static Font textFont;
    public static Graphics textFiled = scene.getGraphics();
    public static int startTime, timeLeft = 0, score = 0;

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
    private final ArrayList<Balloom> balloomList = new ArrayList<Balloom>();
    private final ArrayList<Oneal> onealList = new ArrayList<Oneal>();
    private final ArrayList<Kondoria> kondoriaList = new ArrayList<Kondoria>();
    private static final ArrayList<Integer> scoreList = new ArrayList<Integer>();
    //enemies
    Entities [][] object = new Entities[R][C];
    int usedBomb = 0;

    public void drawMap() {
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
                    case -20: {
                        Balloom newBalloom = new Balloom(j * DEFAULT_SIZE, i * DEFAULT_SIZE, balloom_right1);
                        object[i][j] = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, grass);
                        balloomList.add(newBalloom);
                        break;
                    }
                    case -21: {
                        Oneal newOneal = new Oneal(j * DEFAULT_SIZE, i * DEFAULT_SIZE, oneal_right1);
                        object[i][j] = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, grass);
                        onealList.add(newOneal);
                        break;
                    }
                    case -22: {
                        Kondoria newKondoria = new Kondoria(j * DEFAULT_SIZE, i * DEFAULT_SIZE, kondoria_right1);
                        object[i][j] = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, grass);
                        kondoriaList.add(newKondoria);
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
        BufferedImage black_background = new BufferedImage(windowWidth, windowHeight, BufferedImage.TYPE_INT_RGB);
        scene.getGraphics().drawImage(black_background, 0, 0, windowWidth, windowHeight, null);
        if (switchMap || player.isEndDeadAnimation() || timeLeft == 0) {
            if (currentMap == 3 || player.isEndDeadAnimation()) {
                gameStatus = 3;
            }
            if (!switchMap) {
                score = 0;
            }
            switchMap = false;
            startTime = (int) System.currentTimeMillis();
            timeLeft = 180;

            player.setX(playerX);
            player.setY(playerY);
            if(player.isEndDeadAnimation())player.resetBomberMan();

            bombLists.clear();
            flameLists.clear();
            balloomList.clear();
            onealList.clear();
            kondoriaList.clear();
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

    public void printScore() {
        textFiled.setColor(Color.white);
        textFiled.clearRect(0, 725, windowWidth, windowHeight);
        textFiled.drawString("Time: ", 20, 755);
        textFiled.drawString(Integer.toString(timeLeft), 110, 755);
        int curentTime = (int) System.currentTimeMillis() - startTime;
        timeLeft = 180 - curentTime / 1000;
        textFiled.drawString("Score: ", 400, 755);
        textFiled.drawString(Integer.toString(score), 525, 755);
        textFiled.drawString("Map: ", 800, 755);
        textFiled.drawString(Integer.toString(currentMap), 880, 755);
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
                    if (balloomList.isEmpty() && onealList.isEmpty() && kondoriaList.isEmpty()) {
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
                    if (!balloomList.isEmpty()) {
                        for (Balloom enemy : balloomList) {
                            enemy.collide(flame);
                        }
                    }
                    if (!kondoriaList.isEmpty()) {
                        for (Kondoria enemy: kondoriaList) {
                            enemy.collide(flame);
                        }
                    }
                    if (!onealList.isEmpty()) {
                        for (Oneal enemy: onealList) {
                            enemy.collide(flame);
                        }
                    }
                    flame.draw(scene.getGraphics());
                }
            }
        }
        balloomList.removeIf(e -> e.isEndAnimation());
        if (!balloomList.isEmpty()) {
            for (Balloom enemy : balloomList) {
                player.collide(enemy);
                enemy.move();
                if (enemy.isDead()) {
                    score += enemy.getGetScore();
                    enemy.setGetScore(0);
                }
                enemy.draw(scene.getGraphics());
            }
        }
        onealList.removeIf(e -> e.isEndAnimation());
        if (!onealList.isEmpty()) {
            for (Oneal enemy: onealList) {
                player.collide(enemy);
                enemy.move();
                if (enemy.isDead()) {
                    score += enemy.getGetScore();
                    enemy.setGetScore(0);
                }
                enemy.draw(scene.getGraphics());
            }
        }
        kondoriaList.removeIf(e -> e.isEndAnimation());
        if (!kondoriaList.isEmpty()) {
            for (Kondoria enemy: kondoriaList) {
                player.collide(enemy);
                enemy.move();
                if (enemy.isDead()) {
                    score += enemy.getGetScore();
                    enemy.setGetScore(0);
                }
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
        bombLists.removeIf(e -> e.isExploded());
        if (!flameLists.isEmpty()) {
            for (int i = 0; i < flameLists.size(); i++) {
                if (flameLists.get(i).isFlaming()) {
                    flameLists.get(i).handleFlame();
                    if (flameLists.get(i).isEndFlame()) {
                        //flameLists.remove(i);
                        //bombLists.remove(i);
                        //i--;
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
        flameLists.removeIf(e -> e.isEndFlame());
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

    public static Sprites[] play_button = {
            play_button1,
            play_button2
    };
    public static Sprites[] score_button = {
            play_button1,
            play_button2
    };
    public static Sprites[] back_button = {
            play_button1,
            play_button2
    };
    public static int menu_pressed = 0;
    public static int score_pressed = 0;
    public static int end_pressed = 0;
    public static int play_index = 0;
    public static int score_index = 0;
    public static int back_index = 0;


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int y = e.getY();
        int x = e.getX();
        switch (gameStatus) {
            case 0: {
                if (x >= (windowWidth - 118) / 2 && x <= (windowWidth + 118) / 2 &&
                        y >= (windowHeight - 58) / 2 && y <= (windowHeight + 58) / 2) {
                    menu_pressed = 1;
                    play_index = 1;
                }
                if (x >= (windowWidth - 118) / 2 && x <= (windowWidth + 118) / 2 &&
                        y >= (windowHeight - 58) * 3 / 4 && y <= (windowHeight + 58) * 3 / 4) {
                    menu_pressed = 2;
                    score_index = 1;
                }
                break;
            }
            case 1:
                break;
            case 2: {
                if (x >= 0 && x <= 118 &&
                        y >= 0 && y <= 58) {
                    score_pressed = 1;
                    back_index = 1;
                }
                break;
            }
            case 3: {
                if (x >= 0 && x <= 118 &&
                        y >= 0 && y <= 58) {
                    end_pressed = 1;
                    back_index = 1;
                }
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (gameStatus) {
            case 0: {
                switch (menu_pressed) {
                    case 0:
                        break;
                    case 1: {
                        menu_pressed = 0;
                        play_index = 0;
                        gameStatus = 1;
                        break;
                    }
                    case 2: {
                        menu_pressed = 0;
                        score_index = 0;
                        gameStatus = 2;
                        break;
                    }
                }
                break;
            }
            case 1:
                break;
            case 2: {
                switch (score_pressed) {
                    case 0:
                        break;
                    case 1: {
                        score_pressed = 0;
                        back_index = 0;
                        gameStatus = 0;
                        break;
                    }
                }
            }
            case 3: {
                switch (end_pressed) {
                    case 0:
                        break;
                    case 1: {
                        end_pressed = 0;
                        back_index = 0;
                        gameStatus = 0;
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void menu() {
        scene.getGraphics().drawImage(menu_background.getImage(), 0, 0, windowWidth, windowHeight, null);
        scene.getGraphics().drawImage(play_button[play_index].getImage(), (windowWidth - 118) / 2, (windowHeight - 58) / 2, null);
        scene.getGraphics().drawImage(score_button[score_index].getImage(), (windowWidth - 118) / 2, (windowHeight - 58) * 3 / 4, null);
    }

    public void showHighScore() {
        Collections.sort(scoreList);
        Collections.reverse(scoreList);
        scene.getGraphics().drawImage(menu_background.getImage(), 0, 0, windowWidth, windowHeight, null);
        scene.getGraphics().drawImage(back_button[back_index].getImage(), 0, 0, null);
        textFiled.setColor(Color.black);
        textFiled.drawString("High score: ", 400, 355);
        for (int i = 0; i < Math.min(scoreList.size(), 5); i++) {
            textFiled.drawString(Integer.toString(scoreList.get(i)), 400, 400 + 35 * i);
        }
        // show high score
    }

    public void endGame() {
        scene.getGraphics().drawImage(menu_background.getImage(), 0, 0, windowWidth, windowHeight, null);
        scene.getGraphics().drawImage(back_button[back_index].getImage(), 0, 0, null);
        textFiled.setColor(Color.black);
        textFiled.drawString("Your score: ", 300, 455);
        textFiled.drawString(Integer.toString(score), 525, 455);
        // show score
    }

    public static void loadScoreFromFile() throws FileNotFoundException {
        String path = System.getProperty("user.dir") + "\\Data\\Score\\score.txt";
        FileInputStream fileInputStream = new FileInputStream(path);
        Scanner scanner = new Scanner(fileInputStream);
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                scoreList.add(Integer.parseInt(line));
            }
        } finally {
            try {
                scanner.close();
                fileInputStream.close();
            } catch (IOException ex) {
                System.out.println("LOI DOC FILE");
            }
        }
    }
    public static void main(String[] args) throws IOException, LineUnavailableException{
        JFrame frame = new JFrame();
        frame.setTitle("Bomberman");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocation(50, 50);
        frame.setSize(windowWidth, windowHeight);

        loadMapFromFile();
        loadScoreFromFile();
        String path = System.getProperty("user.dir") + "\\Data\\Font\\EightBitDragon-anqx.ttf";
        try {
            textFont = Font.createFont(Font.TRUETYPE_FONT,
                    new FileInputStream(new File(path))).deriveFont(Font.PLAIN, 30);
        } catch (FontFormatException e) {
            e.printStackTrace();
        }
        textFiled.setFont(textFont);

        GameBomberMan myGame = new GameBomberMan();
        frame.setFocusable(true);
        myGame.requestFocusInWindow();
        myGame.addMouseListener(myGame);
        myGame.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame.addKeyListener(myGame);
        frame.add(myGame);
        frame.pack();
        frame.setVisible(true);
        try {
            mapSound.playSound(true);
        } catch (IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        long timeSilent = 5;
        while (true) {
            long startTime = System.currentTimeMillis();
            switch (gameStatus) {
                case 0: {
                    myGame.menu();
                    break;
                }
                case 1: {
                    myGame.handleMap();
                    myGame.update();
                    myGame.handleGame();
                    myGame.printScore();
                    break;
                }
                case 2: {
                    myGame.showHighScore();
                    break;
                }
                case 3: {
                    myGame.endGame();
                    break;
                }
            }
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