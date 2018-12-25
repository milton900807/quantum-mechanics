
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Floyd extends JPanel implements Runnable {
    static final double RED = 0.191D;
    static final double GREEN = 1.0D;
    static final double BLUE = 2.0D;
    static final float XFocus = -60.0F;
    static final float YFocus = 10.0F;
    static final float ZFocus = 0.0F;
    static final int lightSourceX = -150;
    static final int lightSourceY = -20;
    static final int lightSourceZ = 0;
    boolean lightOn = false;
    public EnergyBar box;
    VwCanvas vwcanvas;
    VisualWorld vw;
    String model_name;
    String promptString;
    Spectrum spec;
    Spectrum outspec;
    UpPrism prism;
    DownPrism prismend;
    Light light;
    Light finalLight;
    EM emspectra;
    int pyr1x;
    int pyr1y;
    int pyr1z;
    Vector4 origin;
    Vector4 cameraLoc;
    Vector4 cameraDir;
    Vector4 cameraUp;
    Vector4 cameraLeft;
    Thread switchOn;

    public Floyd(EM espectrum) {
        this.emspectra = espectrum;
        this.initialize();
    }

    private void initialize() {
        this.setLayout(new BorderLayout());
        Color bgColor = new Color(100, 100, 255);
        Color msgColor = Color.black;
        if (msgColor.equals(bgColor)) {
            msgColor = Color.white;
        }

        setBackground(Color.yellow );
        this.vw = new VisualWorld();
        this.vw.setBgColor(bgColor);
        this.prism = new UpPrism(-60.0F, 10.0F, 0.0F);
        this.prism.setColor(Color.lightGray);
        this.prism.mul((new Matrix4x4()).rotatey(15));
        this.vw.add(this.prism);
        this.prismend = new DownPrism(-50.0F, -5.0F, 0.0F);
        this.prismend.setColor(Color.lightGray);
        this.prismend.mul((new Matrix4x4()).rotatey(20));
        this.vw.add(this.prismend);
        this.vwcanvas = new VwCanvas(this.vw);
        this.box = new EnergyBar(this.vwcanvas, this.emspectra);
        this.vwcanvas.setBox(this.box);
        this.add("Center", this.vwcanvas);
        this.vwcanvas.setPreferredSize(getPreferredSize());
        this.cameraLoc = new Vector4();
        this.cameraDir = new Vector4();
        this.cameraUp = new Vector4();
        this.cameraLeft = new Vector4();
        this.origin = new Vector4(0.0F, 0.0F, 0.0F, 1.0F);
        this.initCamera();
        this.updateCamera();

        addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

            }

            public void mousePressed(MouseEvent e) {
                turnOn();
            }

            public void mouseReleased(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }
        });

    }

    public void prompt(String pstring) {
        this.vwcanvas.setPromptString(pstring);
//        this.vwcanvas.repaint();
    }

    private void updateCamera() {
        if (this.vw != null) {
            this.vw.setWindowSize ( getSize () );
            this.vw.setCameraDirection(this.cameraDir);
            this.vw.setCameraUp(this.cameraUp);
            this.vw.setCameraLocation(this.cameraLoc);
        }
    }

    private void initCamera() {
        this.cameraLoc.v[0] = this.cameraLoc.v[1] = 0.0F;
        this.cameraLoc.v[2] = 200.0F;
        this.cameraDir.v[0] = this.cameraDir.v[1] = 0.0F;
        this.cameraDir.v[2] = -1.0F;
        this.cameraUp.v[0] = this.cameraUp.v[2] = 0.0F;
        this.cameraUp.v[1] = 1.0F;
        this.cameraLeft.v[0] = -1.0F;
        this.cameraLeft.v[1] = this.cameraLeft.v[2] = 0.0F;
        this.cameraLoc.add(this.cameraDir.copy().scale(30.0F));
    }

    public boolean keyDown(Event evt, int key) {
        switch(key) {
            case 44:
            case 46:
            case 48:
                this.initCamera();
                this.updateCamera();
                break;
            case 98:
                this.cameraLoc.add(this.cameraDir.copy().scale(-10.0F));
                this.updateCamera();
                break;
            case 102:
                if (this.cameraLoc.magnitude() > 150.0F) {
                    this.cameraLoc.add(this.cameraDir.copy().scale(10.0F));
                    this.updateCamera();
                }
        }

        this.updateCamera();
        repaint ();
        this.vwcanvas.repaint();
        this.emspectra.repaint();
        return true;
    }

    public boolean turnOn() {
        if (this.lightOn) {
            this.turnOff(true);
            this.lightOn = false;
            return true;
        } else if (this.switchOn != null && this.switchOn.isAlive()) {
            return false;
        } else {
            this.switchOn = new Thread(this);
            this.switchOn.start();
            return true;
        }
    }

    public void reRun() {
        if (this.switchOn == null || !this.switchOn.isAlive()) {
            if (this.lightOn) {
                this.turnOff(false);
            }

            this.switchOn = new Thread(this);
            this.switchOn.start();
        }
    }

    public void run() {

        this.lightOn = true;
           this.light = new Light(2.0F);
           this.light.setColor(Color.white);
           this.light.mul((new Matrix4x4()).move(-150.0F, -20.0F, 0.0F));
           this.light.focus(-60.0F, 10.0F, 0.0F);
           this.light.focusY(10.0F);
           this.vw.add(this.light);
           this.spec = this.prism.dispers(this.light, this.vwcanvas);
           this.spec.setFocus(this.prismend.x / 2.0F - 9.0F, this.prismend.y, this.prismend.z - 3.0F);
           this.vw.add(this.spec);
           this.outspec = this.box.calculate(this.spec, this.vwcanvas);
           this.outspec.setSource(0.0F, this.prism.y, this.prism.z - 3.0F);
           this.outspec.setFocus(this.prismend.x * 2.0F, this.prismend.y, this.prismend.z - 5.0F);
           this.vw.add(this.outspec);
           this.finalLight = this.prismend.compressSpectrum(this.outspec);
           this.vw.add(this.finalLight);
           repaint();
//           try {
//               Thread.sleep(1000l);
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }

    }
//
//    public void reshape(int x, int y, int w, int h) {
//        super.reshape(x, y, w, h);
//        this.updateCamera();
//    }

    public void setBounds( int x, int y, int w, int h ){
         super.setBounds(x, y, w, h);
        this.updateCamera();
        vwcanvas.updateBackdrop(new Dimension( w, h ));
    }
    public void rotateObject(int x, int y, int z) {
        this.light.mul((new Matrix4x4()).move(0.0F, 0.0F, 0.0F).rotatey(y).rotatex(x).rotatez(z));
    }

    public void animate() {
        this.light.mul((new Matrix4x4()).move(0.0F, 0.0F, 0.0F).rotatey(1).rotatex(1).rotatez(1).move((float)this.pyr1x, (float)this.pyr1y, (float)this.pyr1z));
    }

    public boolean hasLevels() {
        return this.box.hasLevels();
    }

    public void clearLevels() {
        this.box.clearLevels();
    }

//    public void paint(Graphics g) {
//        this.vwcanvas.paint(g);
//    }

//    public void update(Graphics g) {
//        this.vwcanvas.update(g);
//    }

    public void turnOff(boolean refresh) {
        this.vw.remove(this.light);
        this.vw.remove(this.spec);
        this.vw.remove(this.outspec);
        this.vw.remove(this.finalLight);
        if (this.box != null) {
            this.box.removeTransition();
        }

        this.light = null;
        this.outspec = null;
        this.finalLight = null;
        if (this.switchOn == null || !this.switchOn.isAlive()) {
            if (refresh) {
                Graphics g = this.vwcanvas.getGraphics();
                this.vwcanvas.paint(g);
            }

        }
    }
}
