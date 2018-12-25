
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.Enumeration;

public abstract class Polyhedron extends VisualObject {
    Color color;
    int n;
    Vector4[] points;
    VectorPolygon3D eyePolygons;

    public Polyhedron() {
        this.color = Color.yellow;
    }

    public Polyhedron(int n) {
        this.initialize(n);
        this.color = Color.red;
    }

    protected void initialize(int n) {
        this.n = n;
        this.points = new Vector4[n];

        for(int i = 0; i < n; ++i) {
            this.points[i] = new Vector4();
        }

    }

    public void setColor(Color c) {
        this.color = c;
    }

    public VisualObject mul(Matrix4x4 m) {
        for(int i = 0; i < this.n; ++i) {
            this.points[i].mul(m);
        }

        return this;
    }

    protected abstract int getPolygonCount();

    protected abstract Polygon3D transformPolygonToEye(int var1, ViewTransformer var2);

    public float getEyeZ() {
        if (this.eyePolygons.size() == 0) {
            return 0.0F;
        } else {
            Polygon3D p = (Polygon3D)this.eyePolygons.elementAt(0);
            return p.verticies[0].v[2];
        }
    }

    public void transformToEye(ViewTransformer viewTransformer) {
        this.eyePolygons = new VectorPolygon3D();

        for(int i = 0; i < this.getPolygonCount(); ++i) {
            Polygon3D p = this.transformPolygonToEye(i, viewTransformer);
            Vector4 v1 = p.verticies[0].copy().sub(p.verticies[1]);
            Vector4 v2 = p.verticies[2].copy().sub(p.verticies[1]);
            Vector4 n = v1.copy().cross(v2);
            if (n.v[2] > 0.0F) {
                float l = n.v[2];
                float m = n.v[0] * n.v[0] + n.v[1] * n.v[1] + n.v[2] * n.v[2];
                float r = (float)((double)l / Math.sqrt((double)m));
                int red = (int)((float)p.color.getRed() * r);
                int grn = (int)((float)p.color.getGreen() * r);
                int blu = (int)((float)p.color.getBlue() * r);
                p.color = new Color(red, grn, blu);
                this.eyePolygons.addElement(p);
            }
        }

    }

    public void project(Graphics g, ViewTransformer viewTransformer) {
        Enumeration e = this.eyePolygons.elements();

        while(e.hasMoreElements()) {
            Polygon3D p = (Polygon3D)e.nextElement();
            g.setColor(Color.yellow);
            Polygon poly = new Polygon();

            for(int j = 0; j < p.n; ++j) {
                IntVector2 v = viewTransformer.project(p.verticies[j]);
                poly.addPoint(v.v[0], v.v[1]);
            }

            g.setColor(p.color);
            g.fillPolygon(poly);
        }

    }

    public void focus(float x, float y, float z) {
        System.out.println(" overload this method ");
    }

    public void print() {
        String s = "Polyhedron[";

        for(int i = 0; i < this.n; ++i) {
            s = s + this.points[i] + " ";
        }

        s = s + "]";
        System.out.println(s);
    }
}
