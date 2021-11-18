package Entities;

import java.awt.*;
import Graphics.Sprites;

public class Grass extends Entities{
    public Grass (int x, int y, Sprites sprite) {
        super(x,y,sprite);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(currentSprite.getImage(), x, y, Sprites.DEFAULT_SIZE,
                Sprites.DEFAULT_SIZE, null);
    }

    @Override
    public void collide(Flame flame) {
        return;
    }
}