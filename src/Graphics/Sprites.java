package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Sprites {
    public static final int DEFAULT_SIZE = 48;
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
    public static Sprites portal = new Sprites("Data\\Sprites\\portal.png",16,16);

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

    public static Sprites explosion_vertical_up_last = new Sprites("Data\\Sprites\\explosion_vertical_top_last.png", 16, 16);
    public static Sprites explosion_vertical_up_last1 = new Sprites("Data\\Sprites\\explosion_vertical_top_last1.png", 16, 16);
    public static Sprites explosion_vertical_up_last2 = new Sprites("Data\\Sprites\\explosion_vertical_top_last2.png", 16, 16);

    public static Sprites explosion_vertical_down_last = new Sprites("Data\\Sprites\\explosion_vertical_down_last.png", 16, 16);
    public static Sprites explosion_vertical_down_last1 = new Sprites("Data\\Sprites\\explosion_vertical_down_last1.png", 16, 16);
    public static Sprites explosion_vertical_down_last2 = new Sprites("Data\\Sprites\\explosion_vertical_down_last2.png", 16, 16);

    public static Sprites brick_exploded = new Sprites("Data\\Sprites\\brick_exploded.png", 16, 16);
    public static Sprites brick_exploded1 = new Sprites("Data\\Sprites\\brick_exploded1.png", 16, 16);
    public static Sprites brick_exploded2 = new Sprites("Data\\Sprites\\brick_exploded2.png", 16, 16);


    /*
    |--------------------------------------------------------------------------
    | Powerups
    |--------------------------------------------------------------------------
     */
    public static Sprites powerup_bombs = new Sprites("Data\\Sprites\\powerup_bombs.png", 16, 16);
    public static Sprites powerup_flames = new Sprites("Data\\Sprites\\powerup_flames.png", 16, 16);
    public static Sprites powerup_speed = new Sprites("Data\\Sprites\\powerup_speed.png", 16, 16);
    public static Sprites powerup_wallpass = new Sprites("Data\\Sprites\\powerup_wallpass.png", 16, 16);
    public static Sprites powerup_detonator = new Sprites("Data\\Sprites\\powerup_detonator.png", 16, 16);
    public static Sprites powerup_bombpass = new Sprites("Data\\Sprites\\powerup_bombpass.png", 16, 16);
    public static Sprites powerup_flamepass = new Sprites("Data\\Sprites\\powerup_flamepass.png ", 16, 16);

    /*
    |--------------------------------------------------------------------------
    | Character
    |--------------------------------------------------------------------------
     */
    //BALLOOM
    public static Sprites balloom_left1 = new Sprites("Data\\Sprites\\balloom_left1.png",16,16);
    public static Sprites balloom_left2 = new Sprites("Data\\Sprites\\balloom_left2.png",16,16);
    public static Sprites balloom_left3 = new Sprites("Data\\Sprites\\balloom_left3.png",16,16);

    public static Sprites balloom_right1 = new Sprites("Data\\Sprites\\balloom_right1.png",16,16);
    public static Sprites balloom_right2 = new Sprites("Data\\Sprites\\balloom_right2.png",16,16);
    public static Sprites balloom_right3 = new Sprites("Data\\Sprites\\balloom_right3.png",16,16);

    public static Sprites balloom_dead = new Sprites("Data\\Sprites\\balloom_dead.png",16,16);

    //ONEAL
    public static Sprites oneal_left1 = new Sprites("Data\\Sprites\\oneal_left1.png", 16, 16);
    public static Sprites oneal_left2 = new Sprites("Data\\Sprites\\oneal_left2.png", 16, 16);
    public static Sprites oneal_left3 = new Sprites("Data\\Sprites\\oneal_left3.png", 16, 16);

    public static Sprites oneal_right1 = new Sprites("Data\\Sprites\\oneal_right1.png", 16, 16);
    public static Sprites oneal_right2 = new Sprites("Data\\Sprites\\oneal_right2.png", 16, 16);
    public static Sprites oneal_right3 = new Sprites("Data\\Sprites\\oneal_right3.png", 16, 16);

    public static Sprites oneal_dead = new Sprites("Data\\Sprites\\oneal_dead.png", 16, 16);

    //Doll
    public static Sprites doll_left1 = new Sprites("Data\\Sprites\\doll_left1.png", 16, 16);
    public static Sprites doll_left2 = new Sprites("Data\\Sprites\\doll_left2.png", 16, 16);
    public static Sprites doll_left3 = new Sprites("Data\\Sprites\\doll_left3.png", 16, 16);

    public static Sprites doll_right1 = new Sprites("Data\\Sprites\\doll_right1.png", 16, 16);
    public static Sprites doll_right2 = new Sprites("Data\\Sprites\\doll_right2.png", 16, 16);
    public static Sprites doll_right3 = new Sprites("Data\\Sprites\\doll_right3.png", 16, 16);

    public static Sprites doll_dead = new Sprites("Data\\Sprites\\doll_dead.png", 16, 16);

    //Minvo
    public static Sprites minvo_left1 = new Sprites("Data\\Sprites\\minvo_left1.png", 16, 16);
    public static Sprites minvo_left2 = new Sprites("Data\\Sprites\\minvo_left2.png", 16, 16);
    public static Sprites minvo_left3 = new Sprites("Data\\Sprites\\minvo_left3.png", 16, 16);

    public static Sprites minvo_right1 = new Sprites("Data\\Sprites\\minvo_right1.png", 16, 16);
    public static Sprites minvo_right2 = new Sprites("Data\\Sprites\\minvo_right2.png", 16, 16);
    public static Sprites minvo_right3 = new Sprites("Data\\Sprites\\minvo_right3.png", 16, 16);

    public static Sprites minvo_dead = new Sprites("Data\\Sprites\\minvo_dead.png", 16, 16);

    //Kondoria
    public static Sprites kondoria_left1 = new Sprites("Data\\Sprites\\kondoria_left1.png", 16, 16);
    public static Sprites kondoria_left2 = new Sprites("Data\\Sprites\\kondoria_left2.png", 16, 16);
    public static Sprites kondoria_left3 = new Sprites("Data\\Sprites\\kondoria_left3.png", 16, 16);

    public static Sprites kondoria_right1 = new Sprites("Data\\Sprites\\kondoria_right1.png", 16, 16);
    public static Sprites kondoria_right2 = new Sprites("Data\\Sprites\\kondoria_right2.png", 16, 16);
    public static Sprites kondoria_right3 = new Sprites("Data\\Sprites\\kondoria_right3.png", 16, 16);

    public static Sprites kondoria_dead = new Sprites("Data\\Sprites\\kondoria_dead.png", 16, 16);

    //ALL
    public static Sprites mob_dead1 = new Sprites("Data\\Sprites\\mob_dead1.png", 16, 16);
    public static Sprites mob_dead2 = new Sprites("Data\\Sprites\\mob_dead2.png", 16, 16);
    public static Sprites mob_dead3 = new Sprites("Data\\Sprites\\mob_dead3.png", 16, 16);

    //Other
    public static Sprites menu_background = new Sprites("Data\\Sprites\\menu_background.png", 1600, 900);
    public static Sprites highscore_background = new Sprites("Data\\Sprites\\highscore_background.png", 1600, 900);
    public static Sprites play_button1 = new Sprites("Data\\Sprites\\play_button1.png", 160, 72);
    public static Sprites play_button2 = new Sprites("Data\\Sprites\\play_button2.png", 160, 72);
    public static Sprites score_button1 = new Sprites("Data\\Sprites\\score_button1.png", 192, 72);
    public static Sprites score_button2 = new Sprites("Data\\Sprites\\score_button2.png", 192, 72);
    public static Sprites back_button1 = new Sprites("Data\\Sprites\\back_button1.png", 192, 128);
    public static Sprites back_button2 = new Sprites("Data\\Sprites\\back_button2.png", 192, 128);
}