
import java.awt.Color;

public class DownPrism extends Prism {
    public float x;
    public float y;
    public float z;
    public float diffraction = -10.0F;

    public DownPrism() {
        super(40.5F);
        this.mul((new Matrix4x4()).move(0.0F, 0.0F, 0.0F).rotatez(180));
    }

    public DownPrism(float x, float y, float z) {
        super(40.5F);
        this.x = (float) ((double) x + 76.25D);
        this.y = y;
        this.z = z;
        this.mul((new Matrix4x4()).move(x, y, z).rotatez(180).rotatey(-20));
    }

    public Light compressSpectrum(Spectrum spec) {
        Light light = new Light(2.0F);
        Color scolor = spec.getCompColor();
        light.setColor(scolor);
        light.mul((new Matrix4x4()).move(150.0F, this.diffraction, this.z - 4.0F));
        light.focus((float) ((int) (this.x + 17.0F)), 4.0F, this.z - 4.0F);
        light.focusY(7.0F);
        return light;
    }
}
