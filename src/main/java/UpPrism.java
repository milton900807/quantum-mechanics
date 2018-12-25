
public class UpPrism extends Prism {
    float x;
    float y;
    float z;
    float pWidth = 30.5F;

    public UpPrism() {
        super(40.5F);
        this.mul((new Matrix4x4()).move(0.0F, 0.0F, 0.0F));
    }

    public UpPrism(float x, float y, float z) {
        super(40.5F);
        this.mul((new Matrix4x4()).move(x, y, z));
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Spectrum dispers(Light inlight, VwCanvas vwcanvas) {
        Spectrum spec = new Spectrum(this.x, this.y, this.z - 3.0F, vwcanvas);
        return spec;
    }
}
