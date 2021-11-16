package Entities;

import java.awt.*;
import Graphics.Sprites;


public class Brick extends Entities {

    public Brick (int x, int y, Sprites sprite) {
        super(x,y,sprite);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(),x ,y,Sprites.DEFAULT_SIZE * 3, Sprites.DEFAULT_SIZE * 3,null);
    }
}
