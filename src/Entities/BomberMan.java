package Entities;

import Graphics.Sprites;

import Graphics.Sprites.*;

import java.awt.*;
import java.util.ArrayList;

import static Graphics.Sprites.*;

public class BomberMan extends Entities {

    public BomberMan(int x, int y, Sprites sprite) {
        super(x,y,sprite);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(),x ,y,Sprites.DEFAULT_SIZE * 3, Sprites.DEFAULT_SIZE * 3,null);
    }
}
