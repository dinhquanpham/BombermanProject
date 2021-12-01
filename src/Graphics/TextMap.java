package Graphics;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Logger;

public class TextMap {
    public static int[][] map1 = new int[15][31];

    public static int[][] map2 = new int[15][31];
    public static void loadLevel1() throws FileNotFoundException {
        int rand = new Random().nextInt(4);
        String path = System.getProperty("user.dir") + "\\Data\\Maps\\Level1_" + Integer.toString(rand) + ".txt";
        FileInputStream fileInputStream = new FileInputStream(path);
        Scanner scanner = new Scanner(fileInputStream);
        int curRow = 0;
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for(int curColumn = 0; curColumn < line.length(); curColumn++) {
                    switch (line.charAt(curColumn)) {
                        case '#': {
                            map1[curRow][curColumn] = 1;
                            break;
                        }
                        case '*': {
                            map1[curRow][curColumn] = 2;
                            break;
                        }
                        case 'b': {
                            map1[curRow][curColumn] = 10;
                            break;
                        }
                        case 'f': {
                            map1[curRow][curColumn] = 11;
                            break;
                        }
                        case 's': {
                            map1[curRow][curColumn] = 12;
                            break;
                        }
                        case '1': {
                            map1[curRow][curColumn] = -20;
                            break;
                        }
                        case '2': {
                            map1[curRow][curColumn] = -21;
                            break;
                        }
                        case '3': {
                            map1[curRow][curColumn] = -22;
                            break;
                        }
                        case 'x': {
                            map1[curRow][curColumn] = 69;
                            break;
                        }
                        default: {
                            map1[curRow][curColumn] = 0;
                            break;
                        }
                    }
                }
                curRow++;
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
    public static void loadLevel2() throws FileNotFoundException {
        int rand = new Random().nextInt(4);
        String path = System.getProperty("user.dir") + "\\Data\\Maps\\Level2_" + Integer.toString(rand) + ".txt";
        FileInputStream fileInputStream = new FileInputStream(path);
        Scanner scanner = new Scanner(fileInputStream);
        int curRow = 0;
        try {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for(int curColumn = 0; curColumn < line.length(); curColumn++) {
                    switch (line.charAt(curColumn)) {
                        case '#': {
                            map2[curRow][curColumn] = 1;
                            break;
                        }
                        case '*': {
                            map2[curRow][curColumn] = 2;
                            break;
                        }
                        case 'b': {
                            map2[curRow][curColumn] = 10;
                            break;
                        }
                        case 'f': {
                            map2[curRow][curColumn] = 11;
                            break;
                        }
                        case 's': {
                            map2[curRow][curColumn] = 12;
                            break;
                        }
                        case '1': {
                            map2[curRow][curColumn] = -20;
                            break;
                        }
                        case '2': {
                            map2[curRow][curColumn] = -21;
                            break;
                        }
                        case '3': {
                            map2[curRow][curColumn] = -22;
                            break;
                        }
                        case 'x': {
                            map2[curRow][curColumn] = 69;
                            break;
                        }
                        default: {
                            map2[curRow][curColumn] = 0;
                            break;
                        }
                    }
                }
                curRow++;
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
    public static void loadMapFromFile() throws FileNotFoundException {
        loadLevel1();
        loadLevel2();
    }
}