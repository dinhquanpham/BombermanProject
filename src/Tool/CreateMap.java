package Tool;

import Entities.Brick;
import Entities.Entities;
import Entities.Grass;
import Entities.Wall;
import static Graphics.Sprites.*;
import static Main.GameBomberMan.*;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CreateMap extends JPanel implements MouseListener {
    public static int[][] exampleMap = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    int indexMateria = 1;

    public void createMap() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                Entities object;
                if (exampleMap[i][j] == 1) {
                    object = new Wall(j * 48, i * 48, wall);
                } else if (exampleMap[i][j] == 0) {
                    object = new Grass(j * 48, i * 48, grass);
                } else {
                    object = new Brick(j * 48, i * 48, brick);
                }
                object.draw(scene.getGraphics());
            }
        }
    }

    public void draw() {
        this.getGraphics().drawImage(scene, 0, 0, null);
    }

    public void printMap() {
        System.out.println("----------------------------------------------------------------------------------------------");
        for (int i = 0; i < R; i++) {
            System.out.print("{");
            for (int j = 0; j < C; j++) {
                System.out.print(exampleMap[i][j]);
                if (j != C - 1) System.out.print(", ");
            }
            System.out.println("}");
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int y = e.getY() / 48;
        int x = e.getX() / 48;
        if (y == 0 && x == 0) indexMateria = 0;
        else if (y == 0 && x == 1) indexMateria = 1;
        else if (y == 0 && x == 2) indexMateria = 2;
        else if (y == 14 && x == 30) printMap();
        else if (e.getButton() == MouseEvent.BUTTON3) exampleMap[y][x] = 0;
        else if (e.getButton() == MouseEvent.BUTTON2) exampleMap[y][x] = 2;
        else exampleMap[y][x] = indexMateria;
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
        frame.setLocation(25, 50);
        frame.setSize(windowWidth, windowHeight);
        CreateMap creator = new CreateMap();
        frame.setFocusable(true);
        creator.requestFocusInWindow();
        creator.addMouseListener(creator);
        frame.add(creator);
        frame.setVisible(true);
        while (true) {
            creator.createMap();
            creator.draw();
        }
    }
}
