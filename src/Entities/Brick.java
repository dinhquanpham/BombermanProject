package Entities;

import Graphics.Sprites;

import java.awt.*;

public class Brick extends Entities {

    public Brick (int x, int y, Sprites sprite) {
        super(x,y,sprite);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(),x ,y,Sprites.DEFAULT_SIZE * 3, Sprites.DEFAULT_SIZE * 3,null);
    }
}
