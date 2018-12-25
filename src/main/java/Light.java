
import java.awt.Color;

public class Light extends Polyhedron {
    static int[][] faces = new int[9][4];
    String colorString;
    int minWV;
    int maxWV;
    float lWidth;

    public Light(float w) {
        super(8);
        this.lWidth = w;
        float w2 = w / 2.0F;
        super.points[0].v[0] = -w2;
        super.points[0].v[1] = w2;
        super.points[0].v[2] = -w2;
        super.points[1].v[0] = w2;
        super.points[1].v[1] = w2;
        super.points[1].v[2] = -w2;
        super.points[2].v[0] = w2;
        super.points[2].v[1] = w2;
        super.points[2].v[2] = w2;
        super.points[3].v[0] = -w2;
        super.points[3].v[1] = w2;
        super.points[3].v[2] = w2;
        super.points[4].v[0] = -w2;
        super.points[4].v[1] = -w2;
        super.points[4].v[2] = -w2;
        super.points[5].v[0] = w2;
        super.points[5].v[1] = -w2;
        super.points[5].v[2] = -w2;
        super.points[6].v[0] = w2;
        super.points[6].v[1] = -w2;
        super.points[6].v[2] = w2;
        super.points[7].v[0] = -w2;
        super.points[7].v[1] = -w2;
        super.points[7].v[2] = w2;
    }

    public Light() {
        this(1.0F);
        super.color = Color.white;
        this.colorString = super.color.toString();
    }

    public Light(float w, Color color, int fwl, int twl) {
        this(w);
        super.color = color;
        this.minWV = fwl;
        this.maxWV = twl;
    }

    public int getPolygonCount() {
        return 6;
    }

    public Polygon3D transformPolygonToEye(int f, ViewTransformer viewTransformer) {
        Polygon3D poly = new Polygon3D(super.color, 4);
        poly.addPoint(viewTransformer.transform(super.points[faces[f][0]]));
        poly.addPoint(viewTransformer.transform(super.points[faces[f][1]]));
        poly.addPoint(viewTransformer.transform(super.points[faces[f][2]]));
        poly.addPoint(viewTransformer.transform(super.points[faces[f][3]]));
        return poly;
    }

    public Light(Color lc) {
        super.color = lc;
    }

    public void setColor(Color newcolor) {
        super.color = newcolor;
    }

    public void focus(float x, float y, float z) {
        super.points[7].v[0] = x;
        super.points[0].v[0] = x;
        super.points[4].v[0] = x;
        super.points[3].v[0] = x;
    }

    public void focusY(float y) {
        if (this.lWidth < 0.0F) {
            this.lWidth *= -1.0F;
        }

        super.points[7].v[1] = y;
        super.points[0].v[1] = y + this.lWidth;
        super.points[4].v[1] = y;
        super.points[3].v[1] = y + this.lWidth;
    }

    public String toString() {
        return this.colorString;
    }

    public Color getColor() {
        return super.color;
    }

    public float getWidth() {
        return this.lWidth;
    }

    public float getYTraj() {
        float traj = super.points[7].v[1] - super.points[6].v[1];
        if (traj < 0.0F) {
            traj *= -1.0F;
        }

        return traj;
    }

    public float getXFocus() {
        float foc = super.points[7].v[0];
        return foc;
    }

    public int minWaveLength() {
        return this.minWV;
    }

    public int maxWaveLength() {
        return this.maxWV;
    }

    static {
        faces[0][0] = 0;
        faces[0][1] = 1;
        faces[0][2] = 2;
        faces[0][3] = 3;
        faces[1][0] = 7;
        faces[1][1] = 6;
        faces[1][2] = 5;
        faces[1][3] = 4;
        faces[2][0] = 1;
        faces[2][1] = 0;
        faces[2][2] = 4;
        faces[2][3] = 5;
        faces[3][0] = 2;
        faces[3][1] = 1;
        faces[3][2] = 5;
        faces[3][3] = 6;
        faces[4][0] = 3;
        faces[4][1] = 2;
        faces[4][2] = 6;
        faces[4][3] = 7;
        faces[5][0] = 0;
        faces[5][1] = 3;
        faces[5][2] = 7;
        faces[5][3] = 4;
    }
}
