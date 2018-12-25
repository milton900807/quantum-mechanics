
import javax.swing.*;
import java.awt.Image;
import java.awt.image.MemoryImageSource;

public class BackDrop {
    public static Image blueWater(int w, int h, JComponent component) {
        int[] pix = new int[w * h];
        int index = 0;

        for(int y = 0; y < h; ++y) {
            int blue = y * 200 / (h - 1);

            for(int x = 0; x < w; ++x) {
                pix[index++] = -16777216 | blue;
            }
        }

        Image backDrop = component.createImage(new MemoryImageSource(w, h, pix, 0, h));
        return backDrop;
    }

    public BackDrop() {
    }
}
