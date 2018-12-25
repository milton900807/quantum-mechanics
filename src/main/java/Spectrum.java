
import javax.swing.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.ColorModel;
import java.awt.image.MemoryImageSource;

public class Spectrum extends StaticImage {
    static final int MaxWavelength = 700;
    static final int MinWavelength = 400;
    private static final int correction = 10;
    private double gamma = 50.0D;
    private int absorption;
    private boolean absorbed = false;
    private int distribution;
    private Vector4 coordinates;
    private Vector4 focusCoordinates;
    public static Image spectrum;

    public Spectrum(Vector4 focus, JPanel canvas, int wavelength, double gamma) {
        super(canvas);
        super.width = 90;
        super.height = super.width * 2;
        super.sizeFac = 1;
        this.coordinates = focus;
        if (wavelength >= 400 && wavelength <= 700) {
            this.absorbed = true;
        } else {
            this.absorbed = false;
        }

        this.absorption = wavelength;
        this.gamma = gamma;
        this.Setup();
    }

    public Spectrum(float x, float y, float z, JPanel canvas) {
        super(canvas);
        Dimension d = canvas.size();
        super.width = d.height / 4;
        super.height = super.width * 2;
        super.sizeFac = 1;
        this.coordinates = new Vector4(x, y, z, 1.0F);
        this.absorbed = false;
        this.Setup();
    }

    public Spectrum(int x, int y, int z, JPanel canvas, int wavelength, double gamma) {
        super(canvas);
        this.absorption = wavelength;
        this.gamma = gamma;
        this.absorbed = true;
        Dimension d = canvas.size();
        super.width = d.height / 4;
        super.height = (int)((double)d.width / 2.5D);
        super.sizeFac = 1;
        this.coordinates = new Vector4((float)x, (float)y, (float)z, 1.0F);
        this.Setup();
    }

    public void Setup() {
        this.distribution = super.width;
        this.build();
    }

    public void build() {
        int[] pix = new int[this.distribution * this.distribution];
        float prevLambda = 0.0F;

        for(int j = 0; j < this.distribution; ++j) {
            float value = 1.0F;
            int t = j * this.distribution;
            int currentLambda = (int)this.getLambda(j);
            if ((float)currentLambda != prevLambda && this.absorbed) {
                value = this.absorptionValue(currentLambda);
            } else {
                value = 1.0F;
            }

            prevLambda = (float)currentLambda;
            pix[t] = getRGB(j, this.distribution, value);

            for(int i = 0; i < this.distribution; ++i) {
                pix[t + i] = pix[t];
            }
        }

        super.image = super.canvas.createImage(new MemoryImageSource(this.distribution, this.distribution, ColorModel.getRGBdefault(), pix, 0, this.distribution));
    }

    public int getWidth() {
        return super.width;
    }

    public Vector4 getFocus() {
        return this.focusCoordinates;
    }

    public void setSource(float x, float y, float z) {
        this.coordinates = new Vector4(x, y, z, 1.0F);
    }

    public void setFocus(float x, float y, float z) {
        this.focusCoordinates = new Vector4(x, y, z, 1.0F);
    }

    public void setGamma(double g) {
        this.gamma = g;
    }

    public void setAbsorption(int wv) {
        this.absorption = wv;
    }

    private float absorptionValue(int wavelength) {
        double halflw = this.gamma / 2.0D;
        double expvalue = (double)(-(wavelength - this.absorption) * (wavelength - this.absorption)) / Math.pow(halflw, 2.0D);
        float v = (float)(1.0D - Math.exp(expvalue));
        return v;
    }

    public static int calcRGB(int px, int py, int width, float brightness) {
        int dx = px - width;
        int dy = py - width;
        double ang = 0.0D;
        float sat = (float)((int)Math.sqrt((double)(dx * dx + dy * dy)));
        if ((int)sat > width) {
            return Color.HSBtoRGB(0.0F, 0.0F, 1.0F);
        } else {
            float hue;
            if (dx == 0) {
                hue = dy > 0 ? 0.25F : 0.75F;
            } else {
                ang = Math.atan((double)Math.abs((float)dy / (float)dx));
                if (dx < 0) {
                    if (dy > 0) {
                        ang = 3.141592653589793D - ang;
                    } else {
                        ang += 3.141592653589793D;
                    }
                } else if (dy < 0) {
                    ang = 6.283185307179586D - ang;
                }

                hue = (float)(ang / 6.283185307179586D);
            }

            return Color.HSBtoRGB(hue, sat / (float)width, brightness);
        }
    }

    public static int getRGB(int py, int width, float brightness) {
        float sat = 1.0F;
        float hue = Math.abs((float)py / (float)width);
        return Color.HSBtoRGB(hue, sat, brightness);
    }

    public int getRGB(int py) {
        float brightness = 1.0F;
        float sat = 1.0F;
        float hue = Math.abs((float)py / 360.0F);
        return Color.HSBtoRGB(hue, sat, brightness);
    }

    private float getLambda(int y) {
        float l = 700.0F - 300.0F * ((float)y / (float)super.width);
        return l;
    }

    private float lambdaToPy(int lambda) {
        float py = (float)(((700 - lambda) * this.distribution - 10) / 300);
        return py;
    }

    public Color getColor(int lambda) {
        int py = (int)this.lambdaToPy(lambda);
        if (lambda <= 700 && lambda >= 400) {
            int rgb = getRGB(py, this.distribution + 10, 1.0F);
            Color ncol = new Color(rgb);
            return ncol;
        } else {
            return Color.black;
        }
    }

    public Color getCompColor(int lambda) {
        if (lambda <= 700 && lambda >= 400) {
            int py = 700 - lambda;
            if (py <= 360 && py >= 0) {
                if (py > 180) {
                    py -= 180;
                } else {
                    py += 180;
                }

                int rgb = this.getRGB(py);
                return new Color(rgb);
            } else {
                return Color.white;
            }
        } else {
            return Color.white;
        }
    }

    public Color getCompColor() {
        return this.getCompColor(this.absorption);
    }

    public Color getColor() {
        return this.getColor(this.absorption);
    }

    public static float getLambda(int y, int width) {
        float l = (float)y / (float)width * 300.0F + 400.0F;
        return l;
    }

    public Color absorb(int wavelength) {
        this.absorption = wavelength;
        this.absorbed = true;
        if (wavelength < 400) {
            wavelength = 400;
        } else if (wavelength > 700) {
            wavelength = 700;
        }

        int c = getRGB(wavelength - 400, super.width, 1.0F);
        this.Setup();
        this.absorbed = false;
        return new Color(c);
    }

    protected Vector4 transformImageToEye(int i, ViewTransformer viewTransformer) {
        Vector4 vec;
        if (i == 0) {
            vec = viewTransformer.transform(this.coordinates);
        } else {
            vec = viewTransformer.transform(this.focusCoordinates);
        }

        return vec;
    }
}
