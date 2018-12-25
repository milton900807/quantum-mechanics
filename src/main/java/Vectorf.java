

public class Vectorf {
    public int n;
    public float[] v;

    Vectorf(int n) {
        this.n = n;
        this.v = new float[n];
    }

    Vectorf(Vectorf vec) {
        this(vec.n);

        for(int i = 0; i < this.n; ++i) {
            this.v[i] = vec.v[i];
        }

    }

    Vectorf addf(Vectorf vec) {
        for(int i = 0; i < this.n - 1; ++i) {
            this.v[i] += vec.v[i];
        }

        return this;
    }

    Vectorf subf(Vectorf vec) {
        for(int i = 0; i < this.n - 1; ++i) {
            this.v[i] -= vec.v[i];
        }

        return this;
    }

    Vectorf negf() {
        for(int i = 0; i < this.n - 1; ++i) {
            this.v[i] = -this.v[i];
        }

        return this;
    }

    Vectorf scalef(float s) {
        for(int i = 0; i < this.n - 1; ++i) {
            this.v[i] *= s;
        }

        return this;
    }

    Vectorf copyf() {
        return new Vectorf(this);
    }

    float dot(Vectorf vec) {
        float d = 0.0F;

        for(int i = 0; i < this.n; ++i) {
            d += this.v[i] * vec.v[i];
        }

        return d;
    }

    public String toString() {
        String s = "[";

        for(int i = 0; i < this.n; ++i) {
            s = s + this.v[i] + " ";
        }

        s = s + "]";
        return s;
    }

    public void print() {
        System.out.println(this.toString());
    }
}
