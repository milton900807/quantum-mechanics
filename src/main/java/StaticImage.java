
import javax.swing.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public abstract class StaticImage extends VisualObject {
    Color color;
    Vector4 vertex;
    Vector4 focusVertex;
    JPanel canvas;
    int zImageIndex;
    int width;
    int height;
    int size;
    int sizeFac;
    int rep;
    Image image;

    public StaticImage() {
        this.width = 30;
        this.height = 30;
        this.size = 30;
        this.color = Color.red;
        this.vertex = new Vector4();
    }

    public StaticImage(JPanel canvas) {
        this();
        this.canvas = canvas;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    public void setImage(Image gmage) {
        this.image = gmage;
    }

    public VisualObject mul(Matrix4x4 m) {
        this.vertex.mul(m);
        return this;
    }

    protected abstract void Setup();

    protected abstract Vector4 transformImageToEye(int var1, ViewTransformer var2);

    public float getEyeZ() {
        if (this.vertex == null) {
            System.out.println(" vertex is null ");
            return 0.0F;
        } else {
            return this.vertex.v[2];
        }
    }

    public void transformToEye(ViewTransformer viewTransformer) {
        Vector4 vec = this.transformImageToEye(0, viewTransformer);
        this.vertex = vec;
        Vector4 vecc = this.transformImageToEye(1, viewTransformer);
        this.focusVertex = vecc;
    }

    public void project(Graphics g, ViewTransformer viewTransformer) {
        IntVector2 v = viewTransformer.project(this.vertex);
        IntVector2 focusv = viewTransformer.project(this.focusVertex);
        if (this.canvas == null) {
            System.out.println(" returning from visualobject because the canvas is null ");
        } else {
            if (this.image == null) {
                System.out.println(" image is null setting up again ");
                this.Setup();
            }

            int x = v.v[0];
            int y = v.v[1];
            int fx = focusv.v[0];
            int fy = focusv.v[1];
            int dx = Math.abs(fx - x);
            int dy = Math.abs(fy - y);
            g.drawImage(this.image, x, y, dx, dy, this.canvas);
        }
    }
}
