package Graphics;

import com.sun.istack.internal.localization.NullLocalizable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Sprites {
    public static final int DEFAULT_SIZE = 16;
    protected int width;
    protected int height;
    private BufferedImage image;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Sprites(String path, int width, int height) {
        this.width = width;
        this.height = height;
        try {
            image = ImageIO.read(new File(path));
            image.getScaledInstance(DEFAULT_SIZE,DEFAULT_SIZE,image.SCALE_SMOOTH);
        } catch (IOException e) {
            System.out.println("Error read image");
        }
    }

    /*
    |--------------------------------------------------------------------------
    | Board Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprites grass = new Sprites("Data\\Sprites\\grass.png",16,16);
    public static Sprites brick = new Sprites("Data\\Sprites\\brick.png",16,16);
    public static Sprites wall = new Sprites("Data\\Sprites\\wall.png",16,16);
    public static Sprites portal = new Sprites("Data\\Sprites\\portal.png",14,14);

    /*
    |--------------------------------------------------------------------------
    | Bomber Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprites player_up = new Sprites("Data\\Sprites\\player_up.png",12,16);
    public static Sprites player_down = new Sprites("Data\\Sprites\\player_down.png",12,15);
    public static Sprites player_left = new Sprites("Data\\Sprites\\player_left.png",10,15);
    public static Sprites player_right = new Sprites("Data\\Sprites\\player_right.png",10,16);



    public static Sprites player_up_1 = new Sprites("Data\\Sprites\\player_up_1.png",12,16);
    public static Sprites player_up_2 = new Sprites("Data\\Sprites\\player_up_2.png",12,15);

    public static Sprites player_down_1 = new Sprites("Data\\Sprites\\player_down_1.png",12,15);
    public static Sprites player_down_2 = new Sprites("Data\\Sprites\\player_down_2.png",12,16);

    public static Sprites player_left_1 = new Sprites("Data\\Sprites\\player_left_1.png",11,16);
    public static Sprites player_left_2 = new Sprites("Data\\Sprites\\player_left_2.png",12,16);

    public static Sprites player_right_1 = new Sprites("Data\\Sprites\\player_right_1.png",11,16);
    public static Sprites player_right_2 = new Sprites("Data\\Sprites\\player_right_2.png",12,16);

    public static Sprites player_dead1 = new Sprites("Data\\Sprites\\player_dead1.png",14,16);
    public static Sprites player_dead2 = new Sprites("Data\\Sprites\\player_dead2.png",14,16);
    public static Sprites player_dead3 = new Sprites("Data\\Sprites\\player_dead3.png",14,16);

    /*
    |--------------------------------------------------------------------------
    | Character
    |--------------------------------------------------------------------------
     */
    //BALLOOM
    public static Sprites balloom_left1 = new Sprites("Data\\Sprites\\portal.png",16,16);
    public static Sprites balloom_left2 = new Sprites("Data\\Sprites\\portal.png",16,16);
    public static Sprites balloom_left3 = new Sprites("Data\\Sprites\\portal.png",16,16);

    public static Sprites balloom_right1 = new Sprites("Data\\Sprites\\portal.png",16,16);
    public static Sprites balloom_right2 = new Sprites("Data\\Sprites\\portal.png",16,16);
    public static Sprites balloom_right3 = new Sprites("Data\\Sprites\\portal.png",16,16);

    public static Sprites balloom_dead = new Sprites("Data\\Sprites\\portal.png",16,16);


    /*
	|--------------------------------------------------------------------------
	| Bomb Sprites
	|--------------------------------------------------------------------------
	 */
    public static Sprites bomb = new Sprites("Data\\Sprites\\bomb.png", 16, 16);
    public static Sprites bomb_1 = new Sprites("Data\\Sprites\\bomb_1.png", 16, 16);
    public static Sprites bomb_2 = new Sprites("Data\\Sprites\\bomb_2.png", 16, 16);

    /*
    |--------------------------------------------------------------------------
    | FlameSegment Sprites
    |--------------------------------------------------------------------------
     */
    public static Sprites bomb_exploded = new Sprites("Data\\Sprites\\bomb_exploded.png", 16, 16);
    public static Sprites bomb_exploded1 = new Sprites("Data\\Sprites\\bomb_exploded1.png", 16, 16);
    public static Sprites bomb_exploded2 = new Sprites("Data\\Sprites\\bomb_exploded2.png", 16, 16);

    public static Sprites explosion_vertical = new Sprites("Data\\Sprites\\explosion_vertical.png", 16, 16);
    public static Sprites explosion_vertical1 = new Sprites("Data\\Sprites\\explosion_vertical1.png", 16, 16);
    public static Sprites explosion_vertical2 = new Sprites("Data\\Sprites\\explosion_vertical2.png", 16, 16);

    public static Sprites explosion_horizontal = new Sprites("Data\\Sprites\\explosion_horizontal.png", 16, 16);
    public static Sprites explosion_horizontal1 = new Sprites("Data\\Sprites\\explosion_horizontal1.png", 16, 16);
    public static Sprites explosion_horizontal2 = new Sprites("Data\\Sprites\\explosion_horizontal2.png", 16, 16);

    public static Sprites explosion_horizontal_left_last = new Sprites("Data\\Sprites\\explosion_horizontal_left_last.png", 16, 16);
    public static Sprites explosion_horizontal_left_last1 = new Sprites("Data\\Sprites\\explosion_horizontal_left_last1.png", 16, 16);
    public static Sprites explosion_horizontal_left_last2 = new Sprites("Data\\Sprites\\explosion_horizontal_left_last2.png", 16, 16);

    public static Sprites explosion_horizontal_right_last = new Sprites("Data\\Sprites\\explosion_horizontal_right_last.png", 16, 16);
    public static Sprites explosion_horizontal_right_last1 = new Sprites("Data\\Sprites\\explosion_horizontal_right_last1.png", 16, 16);
    public static Sprites explosion_horizontal_right_last2 = new Sprites("Data\\Sprites\\explosion_horizontal_right_last2.png", 16, 16);

    public static Sprites explosion_vertical_top_last = new Sprites("Data\\Sprites\\explosion_vertical_top_last.png", 16, 16);
    public static Sprites explosion_vertical_top_last1 = new Sprites("Data\\Sprites\\explosion_vertical_top_last1.png", 16, 16);
    public static Sprites explosion_vertical_top_last2 = new Sprites("Data\\Sprites\\explosion_vertical_top_last2.png", 16, 16);

    public static Sprites explosion_vertical_down_last = new Sprites("Data\\Sprites\\explosion_vertical_down_last.png", 16, 16);
    public static Sprites explosion_vertical_down_last1 = new Sprites("Data\\Sprites\\explosion_vertical_down_last1.png", 16, 16);
    public static Sprites explosion_vertical_down_last2 = new Sprites("Data\\Sprites\\explosion_vertical_down_last2.png", 16, 16);

	/*

    /*
    |--------------------------------------------------------------------------
    | playerAnimation
    |--------------------------------------------------------------------------
     */

    public static Sprites[] playerAnimationLeft = {player_left, player_left_1,player_left_2};
    public static Sprites[] playerAnimationRight = {player_right, player_right_1,player_right_2};
    public static Sprites[] playerAnimationUp = {player_up, player_up_1, player_up_2};
    public static Sprites[] playerAnimationDown = {player_down, player_down_1,player_down_2};

    /*
    |--------------------------------------------------------------------------
    | bombAnimation
    |--------------------------------------------------------------------------
     */

    public static Sprites[] bombAnimation = {bomb, bomb_1, bomb_2};
}