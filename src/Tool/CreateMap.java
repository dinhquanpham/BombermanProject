package Tool;

import Entities.Brick;
import Entities.Entities;
import Entities.Grass;
import Entities.Wall;
import static Graphics.Sprites.*;
import static Main.GameBomberMan.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CreateMap extends JPanel implements KeyListener, MouseListener {
    public static char[][] exampleMap = new char[][]{
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#', ' ', '#'},
            {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
            {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
    };

    char materia = ' ';

    public void createMap() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                Entities object;
                object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, grass);
                object.draw(scene.getGraphics());
                if (exampleMap[i][j] == '#') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, wall);
                } else if (exampleMap[i][j] == '*') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, brick);
                } else if (exampleMap[i][j] == 'x') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, portal);
                } else if (exampleMap[i][j] == 'p') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, player_right_1);
                } else if (exampleMap[i][j] == '1') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, balloom_right1);
                } else if (exampleMap[i][j] == '2') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, oneal_right1);
                } else if (exampleMap[i][j] == 'b') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, powerup_bombs);
                } else if (exampleMap[i][j] == 'f') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, powerup_flames);
                } else if (exampleMap[i][j] == 's') {
                    object = new Grass(j * DEFAULT_SIZE, i * DEFAULT_SIZE, powerup_speed);
                }
                object.draw(scene.getGraphics());
            }
        }
        Entities object;
        object = new Grass(0 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, grass); object.draw(scene.getGraphics());
        object = new Grass(1 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, wall); object.draw(scene.getGraphics());
        object = new Grass(2 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, brick); object.draw(scene.getGraphics());
        object = new Grass(3 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, portal); object.draw(scene.getGraphics());
        object = new Grass(4 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, player_right_1); object.draw(scene.getGraphics());
        object = new Grass(5 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, balloom_right1); object.draw(scene.getGraphics());
        object = new Grass(6 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, oneal_right1); object.draw(scene.getGraphics());
        object = new Grass(7 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, powerup_bombs); object.draw(scene.getGraphics());
        object = new Grass(8 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, powerup_flames); object.draw(scene.getGraphics());
        object = new Grass(9 * DEFAULT_SIZE, 15 * DEFAULT_SIZE, powerup_speed); object.draw(scene.getGraphics());
    }

    public void draw() {
        this.getGraphics().drawImage(scene, 0, 0, null);
    }

    public void printMap() {
        System.out.println("-------------------------------");
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                System.out.print(exampleMap[i][j]);
            }
            System.out.println();
        }
        System.out.println("-------------------------------");
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) materia = ' ';
        else if (e.getKeyCode() == KeyEvent.VK_3) materia = '#';
        else if (e.getKeyCode() == KeyEvent.VK_8) materia = '*';
        else if (e.getKeyCode() == KeyEvent.VK_X) materia = 'x';
        else if (e.getKeyCode() == KeyEvent.VK_P) materia = 'p';
        else if (e.getKeyCode() == KeyEvent.VK_1) materia = '1';
        else if (e.getKeyCode() == KeyEvent.VK_2) materia = '2';
        else if (e.getKeyCode() == KeyEvent.VK_B) materia = 'b';
        else if (e.getKeyCode() == KeyEvent.VK_F) materia = 'f';
        else if (e.getKeyCode() == KeyEvent.VK_S) materia = 's';
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int trueY = e.getY() / DEFAULT_SIZE;
        int trueX = e.getX() / DEFAULT_SIZE;
        if (trueY == 15 && trueX == 0) materia = ' ';
        else if (trueY == 15 && trueX == 1) materia = '#';
        else if (trueY == 15 && trueX == 2) materia = '*';
        else if (trueY == 15 && trueX == 3) materia = 'x';
        else if (trueY == 15 && trueX == 4) materia = 'p';
        else if (trueY == 15 && trueX == 5) materia = '1';
        else if (trueY == 15 && trueX == 6) materia = '2';
        else if (trueY == 15 && trueX == 7) materia = 'b';
        else if (trueY == 15 && trueX == 8) materia = 'f';
        else if (trueY == 15 && trueX == 9) materia = 's';
        else if (trueY == 15 && trueX == 30) printMap();
        else if (e.getButton() == MouseEvent.BUTTON3) exampleMap[trueY][trueX] = ' ';
        else if (e.getButton() == MouseEvent.BUTTON2) exampleMap[trueY][trueX] = '#';
        else exampleMap[trueY][trueX] = materia;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("CreateMap");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocation(25, 50);
        CreateMap creator = new CreateMap();
        creator.requestFocusInWindow();
        creator.addMouseListener(creator);
        creator.setPreferredSize(new Dimension(windowWidth, windowHeight));
        frame.addKeyListener(creator);
        frame.add(creator);
        frame.pack();
        frame.setVisible(true);
        while (true) {
            creator.createMap();
            creator.draw();
        }
    }
}
