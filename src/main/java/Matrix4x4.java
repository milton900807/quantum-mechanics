
public class Matrix4x4 {
    float[][] v = new float[4][4];

    public Matrix4x4() {
        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                if (i == j) {
                    this.v[i][j] = 1.0F;
                } else {
                    this.v[i][j] = 0.0F;
                }
            }
        }

    }

    public Matrix4x4 copy() {
        Matrix4x4 m = new Matrix4x4();

        for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
                m.v[i][j] = this.v[i][j];
            }
        }

        return m;
    }

    public Matrix4x4 mul(Matrix4x4 m1) {
        float[][] mr = new float[4][4];

        int j;
        int k;
        for(int i = 0; i < 4; ++i) {
            for(j = 0; j < 4; ++j) {
                mr[i][j] = 0.0F;

                for(k = 0; k < 4; ++k) {
                    mr[i][j] += this.v[k][j] * m1.v[i][k];
                }
            }
        }

        for(j = 0; j < 4; ++j) {
            for(k = 0; k < 4; ++k) {
                this.v[j][k] = mr[j][k];
            }
        }

        return this;
    }

    public Matrix4x4 scale(float s) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 4; ++j) {
                this.v[i][j] *= s;
            }
        }

        return this;
    }

    public Matrix4x4 moveold(Vector4 tv) {
        for(int i = 0; i < 3; ++i) {
            this.v[i][3] += tv.v[i];
        }

        return this;
    }

    public Matrix4x4 move(float x, float y, float z) {
        Matrix4x4 m = new Matrix4x4();
        m.v[0][3] = x;
        m.v[1][3] = y;
        m.v[2][3] = z;
        this.mul(m);
        return this;
    }

    public Matrix4x4 move(Vector4 vec) {
        return this.move(vec.v[0], vec.v[1], vec.v[2]);
    }

    public Matrix4x4 invertx() {
        Matrix4x4 m = new Matrix4x4();
        m.v[0][0] = -1.0F;
        return this.mul(m);
    }

    public Matrix4x4 inverty() {
        Matrix4x4 m = new Matrix4x4();
        m.v[1][1] = -1.0F;
        return this.mul(m);
    }

    public Matrix4x4 invertz() {
        Matrix4x4 m = new Matrix4x4();
        m.v[2][2] = -1.0F;
        return this.mul(m);
    }

    public Matrix4x4 rotatex(int a) {
        float sin = ITrig.sin(a);
        float cos = ITrig.cos(a);
        return this.rotatex(sin, cos);
    }

    public Matrix4x4 rotatex(float sin, float cos) {
        Matrix4x4 m = new Matrix4x4();
        m.v[1][1] = cos;
        m.v[2][2] = cos;
        m.v[1][2] = -sin;
        m.v[2][1] = sin;
        return this.mul(m);
    }

    public Matrix4x4 rotatey(int a) {
        float sin = ITrig.sin(a);
        float cos = ITrig.cos(a);
        return this.rotatey(sin, cos);
    }

    public Matrix4x4 rotatey(float sin, float cos) {
        Matrix4x4 m = new Matrix4x4();
        m.v[0][0] = cos;
        m.v[2][2] = cos;
        m.v[0][2] = sin;
        m.v[2][0] = -sin;
        return this.mul(m);
    }

    public Matrix4x4 rotatez(int a) {
        float sin = ITrig.sin(a);
        float cos = ITrig.cos(a);
        return this.rotatez(sin, cos);
    }

    public Matrix4x4 rotatez(float sin, float cos) {
        Matrix4x4 m = new Matrix4x4();
        m.v[0][0] = cos;
        m.v[1][1] = cos;
        m.v[0][1] = -sin;
        m.v[1][0] = sin;
        return this.mul(m);
    }

    public Matrix4x4 rotateAbout(Vector4 point, Vector4 axis, int angle) {
        Matrix4x4 mat = new Matrix4x4();
        float mx = (float)Math.sqrt((double)(axis.v[1] * axis.v[1] + axis.v[2] * axis.v[2]));
        float sinx;
        float cosx;
        if (mx == 0.0F) {
            sinx = 0.0F;
            cosx = 1.0F;
        } else {
            sinx = axis.v[1] / mx;
            cosx = axis.v[2] / mx;
        }

        float m = (float)Math.sqrt((double)(axis.v[0] * axis.v[0] + mx * mx));
        float siny;
        float cosy;
        if (m == 0.0F) {
            siny = 0.0F;
            cosy = 1.0F;
        } else {
            siny = -axis.v[0] / m;
            cosy = mx / m;
        }

        mat.move(point.copy().neg());
        mat.rotatex(sinx, cosx);
        mat.rotatey(siny, cosy);
        mat.rotatez(angle);
        mat.rotatey(-siny, cosy);
        mat.rotatex(-sinx, cosx);
        mat.move(point);
        return this.mul(mat);
    }

    public void print() {
        for(int j = 0; j < 4; ++j) {
            String s = "[[";

            for(int i = 0; i < 4; ++i) {
                s = s + this.v[i][j] + " ";
            }

            System.out.println(s + "]]");
        }

        System.out.println("");
    }
}
