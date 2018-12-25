
import java.awt.Color;

public class Polygon3D {
    public Color color;
    public int n;
    int vCount;
    public Vector4[] verticies;

    public Polygon3D(Color color, int vCount) {
        this.color = color;
        this.vCount = vCount;
        this.verticies = new Vector4[vCount];
        this.n = 0;
    }

    public void addPoint(Vector4 point) {
        this.verticies[this.n++] = point;
    }

    public String toString() {
        String s = "(Polygon3d)\n";

        for(int i = 0; i < this.n; ++i) {
            s = s + "  " + this.verticies[i].toString() + "\n";
        }

        return s;
    }
}
