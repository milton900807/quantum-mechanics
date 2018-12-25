
class ViewTransformer {
    Matrix4x4 viewMatrix;
    float screenDistance;
    float screenHeight;
    float k;
    int w2;
    int h2;

    public ViewTransformer(Matrix4x4 m, int w2, int h2) {
        this.viewMatrix = m.copy();
        this.w2 = w2;
        this.h2 = h2;
    }

    public void setScreenDistanceAndHeight(float d, float h) {
        this.screenDistance = d;
        this.screenHeight = h;
        this.k = -this.screenDistance / this.screenHeight * (float)this.h2;
    }

    public Vector4 transform(Vector4 vin) {
        Vector4 vout = vin.copy().mul(this.viewMatrix);
        float z = vout.v[2];
        if (z == 0.0F) {
            z = 1.0F;
        }

        vout.v[0] = vout.v[0] / z * this.k;
        vout.v[1] = vout.v[1] / z * this.k;
        return vout;
    }

    public IntVector2 project(Vector4 p) {
        return new IntVector2((int)p.v[0] + this.w2, this.h2 - (int)p.v[1]);
    }
}
