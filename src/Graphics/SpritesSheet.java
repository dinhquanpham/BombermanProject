package Graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;

public class SpritesSheet {
    private String _path;
    private final int _size;
    public int[] _pixels;
    public BufferedImage image;

    public static SpritesSheet tiles =new SpritesSheet("/Textures/classic.png",256);

    public SpritesSheet(String path, int size) {
        _path = path;
        _size = size;
        _pixels = new int[_size * _size];
        load();
    }

    private void load() {
        try {
            URL a = SpritesSheet.class.getResource(_path);
            image = ImageIO.read(a);
            int w = image.getWidth();
            int h = image.getHeight();
            image.getRGB(0, 0, w, h, _pixels, 0, w);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

}
