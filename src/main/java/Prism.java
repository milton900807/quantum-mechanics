
public class Prism extends Polyhedron {
    static int[][] faces = new int[5][4];
    private float width;

    public Prism(float w) {
        super(5);
        this.width = w;
        float ww = w / 2.0F;
        super.points[0].v[0] = -ww;
        super.points[0].v[1] = -ww;
        super.points[0].v[2] = ww;
        super.points[1].v[0] = -ww;
        super.points[1].v[1] = -ww;
        super.points[1].v[2] = -ww;
        super.points[2].v[0] = ww;
        super.points[2].v[1] = -ww;
        super.points[2].v[2] = -ww;
        super.points[3].v[0] = ww;
        super.points[3].v[1] = -ww;
        super.points[3].v[2] = ww;
        super.points[4].v[0] = 0.0F;
        super.points[4].v[1] = ww;
        super.points[4].v[2] = 0.0F;
    }

    public Prism() {
        this(1.0F);
    }

    public int getPolygonCount() {
        return 5;
    }

    public Polygon3D transformPolygonToEye(int f, ViewTransformer view) {
        Polygon3D poly = new Polygon3D(super.color, 4);
        poly.addPoint(view.transform(super.points[faces[f][0]]));
        poly.addPoint(view.transform(super.points[faces[f][1]]));
        poly.addPoint(view.transform(super.points[faces[f][2]]));
        if (f == 0) {
            poly.addPoint(view.transform(super.points[faces[f][3]]));
        }

        return poly;
    }

    public float getWidth() {
        return this.width;
    }

    static {
        faces[0][0] = 0;
        faces[0][1] = 3;
        faces[0][2] = 2;
        faces[0][3] = 1;
        faces[1][0] = 0;
        faces[1][1] = 4;
        faces[1][2] = 3;
        faces[2][0] = 3;
        faces[2][1] = 4;
        faces[2][2] = 2;
        faces[3][0] = 2;
        faces[3][1] = 4;
        faces[3][2] = 1;
        faces[4][0] = 1;
        faces[4][1] = 4;
        faces[4][2] = 0;
    }
}
