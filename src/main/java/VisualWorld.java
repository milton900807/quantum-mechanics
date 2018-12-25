
import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

public class VisualWorld {
    Vector4 origin = new Vector4(0.0F, 0.0F, 0.0F, 1.0F);
    boolean debugFlag = false;
    Color bgColor;
    Vector objects;
    ViewTransformer viewTransformer;
    boolean newCameraSettings;
    Vector4 cameraLocation;
    Vector4 cameraDirection;
    Vector4 cameraUp;
    Vector4 cameraSubject;

    public void debug(boolean flag) {
        this.debugFlag = flag;
    }

    public void setBgColor(Color c) {
        this.bgColor = c;
    }

    private VisualObject objectAt(int i) {
        return (VisualObject)this.objects.elementAt(i);
    }

    private void setObjectAt(VisualObject o, int i) {
        this.objects.setElementAt(o, i);
    }

    public void setCameraSubject(Vector4 subject) {
        this.newCameraSettings = true;
        this.cameraSubject = subject.copy();
    }

    public void setCameraDirection(Vector4 direction) {
        this.newCameraSettings = true;
        this.cameraSubject = null;
        this.cameraDirection = direction.copy();
    }

    public void setCameraLocation(Vector4 location) {
        this.newCameraSettings = true;
        this.cameraLocation = location.copy();
    }

    public void setCameraUp(Vector4 up) {
        this.newCameraSettings = true;
        this.cameraUp = up.copy();
    }

    public void add(VisualObject object) {
        this.objects.addElement(object);
    }

    public void remove(VisualObject ob) {
        this.objects.removeElement(ob);
    }

    public Vector4 getOrigin() {
        return this.origin;
    }

    public void paint(Graphics g, int w, int h) {
        this.computeView(w, h);
        Enumeration e = this.objects.elements();
        e = this.objects.elements();

        VisualObject vo;
        while(e.hasMoreElements()) {
            vo = (VisualObject)e.nextElement();
            vo.transformToEye(this.viewTransformer);
        }

        this.sortObjects();
        e = this.objects.elements();

        while(e.hasMoreElements()) {
            vo = (VisualObject)e.nextElement();
            vo.project(g, this.viewTransformer);
        }

    }

    void sortObjects() {
        this.sortRange(0, this.objects.size() - 1, 0);
    }

    void sortRange(int start, int end, int depth) {
        if (start < end) {
            int middle = (start + end) / 2;
            float middleZ = ((VisualObject)this.objects.elementAt(middle)).getEyeZ();
            int low = start;
            int high = end;

            VisualObject o;
            while(low <= high) {
                while(low <= high && this.objectAt(low).getEyeZ() <= middleZ) {
                    ++low;
                }

                while(low <= high && this.objectAt(high).getEyeZ() > middleZ) {
                    --high;
                }

                if (low <= high) {
                    o = this.objectAt(high);
                    this.setObjectAt(this.objectAt(low), high);
                    this.setObjectAt(o, low);
                }
            }

            if (low > end) {
                o = this.objectAt(high);
                this.setObjectAt(this.objectAt(middle), high);
                this.setObjectAt(o, middle);
                this.sortRange(start, end - 1, depth + 1);
            } else {
                this.sortRange(start, high, depth + 1);
                this.sortRange(low, end, depth + 1);
            }
        }
    }

    void computeView(int w, int h) {
        if (this.newCameraSettings) {
            this.newCameraSettings = false;
            if (this.cameraSubject != null) {
                this.cameraDirection = this.cameraSubject.copy().sub(this.cameraLocation);
            }

            Matrix4x4 trn = (new Matrix4x4()).move(this.cameraLocation.copy().neg());
            float m = (float)Math.sqrt((double)(this.cameraDirection.v[0] * this.cameraDirection.v[0] + this.cameraDirection.v[2] * this.cameraDirection.v[2]));
            float sin;
            float cos;
            Matrix4x4 roty;
            if (m != 0.0F) {
                sin = this.cameraDirection.v[0] / m;
                cos = -this.cameraDirection.v[2] / m;
                roty = (new Matrix4x4()).rotatey(sin, cos);
            } else {
                roty = new Matrix4x4();
            }

            Vector4 dir = this.cameraDirection.copy().mul(roty);
            m = (float)Math.sqrt((double)(dir.v[1] * dir.v[1] + dir.v[2] * dir.v[2]));
            Matrix4x4 rotx;
            if (m != 0.0F) {
                sin = -dir.v[1] / m;
                cos = -dir.v[2] / m;
                rotx = (new Matrix4x4()).rotatex(sin, cos);
            } else {
                rotx = new Matrix4x4();
            }

            Matrix4x4 rot = roty.copy().mul(rotx);
            Vector4 up = this.cameraUp.copy().mul(rot);
            m = (float)Math.sqrt((double)(up.v[0] * up.v[0] + up.v[1] * up.v[1]));
            Matrix4x4 rotz;
            if (m != 0.0F) {
                sin = up.v[0] / m;
                cos = up.v[1] / m;
                rotz = (new Matrix4x4()).rotatez(sin, cos);
            } else {
                rotz = new Matrix4x4();
            }

            rot.mul(rotz);
            Matrix4x4 viewTransform = trn.copy().mul(rot);
            this.viewTransformer = new ViewTransformer(viewTransform, w / 2, h / 2);
            this.viewTransformer.setScreenDistanceAndHeight(200.0F, 100.0F);
        }
    }

    public VisualWorld() {
        this.bgColor = Color.lightGray;
        this.objects = new Vector();
        this.newCameraSettings = true;
        this.cameraLocation = new Vector4(100.0F, 100.0F, -100.0F, 1.0F);
        this.cameraDirection = new Vector4(-1.0F, -1.0F, -1.0F, 1.0F);
        this.cameraUp = new Vector4(0.0F, 1.0F, 0.0F, 1.0F);
    }

    public void setWindowSize(Dimension windowSize) {
    }
}
