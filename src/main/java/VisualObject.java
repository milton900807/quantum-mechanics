
import java.awt.Color;
import java.awt.Graphics;

public abstract class VisualObject {
    public abstract void setColor(Color var1);

    public abstract VisualObject mul(Matrix4x4 var1);

    abstract void transformToEye(ViewTransformer var1);

    abstract float getEyeZ();

    abstract void project(Graphics var1, ViewTransformer var2);

    public VisualObject() {
    }
}
