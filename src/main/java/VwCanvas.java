
import java.awt.*;

class VwCanvas extends Convert {
    VisualWorld vw;
    EnergyBar box;
    Image im;
    Image backDrop;
    Graphics offscreen;
    int image_index;
    String promptString;
    int i;
    int bSizew = 800;
    int bSizeh = 500;

    public VwCanvas(VisualWorld vw) {
        this.vw = vw;
        setPreferredSize(new Dimension(bSizew, 500));
        this.backDrop = BackDrop.blueWater(this.bSizew, this.bSizeh, this);
    }

    public void updateBackdrop ( Dimension d )
    {
        this.bSizew = d.width;
        this.bSizeh = d.height;

        this.backDrop = BackDrop.blueWater(this.bSizew, this.bSizeh, this);
    }


//
//    public void reshape(int x, int y, int w, int h) {
//        super.reshape(x, y, w, h);
//        if (w > this.bSizew || h > this.bSizeh) {
//            this.bSizew = w;
//            this.bSizeh = h;
//            this.backDrop = BackDrop.blueWater(w, h, this);
//        }
//
//        try {
//            this.im = this.createImage(w, h);
//            this.offscreen = this.im.getGraphics();
//        } catch (Exception var5) {
//            this.offscreen = null;
//        }
//    }

    public void setPromptString(String promptString) {
        this.promptString = promptString;
    }

    public void setBox(EnergyBar eb) {
        this.box = eb;
    }

    public void update_dep(Graphics g) {
        int w = this.size().width;
        int h = this.size().height;
        if (this.offscreen == null) {
            this.paint(g);
        } else {
            this.offscreen.drawImage(this.backDrop, 0, 0, this);
            this.vw.paint(this.offscreen, w, h);
            this.box.paint(this.offscreen);
            if (this.promptString != null) {
                this.offscreen.drawString(this.promptString, 20, h - 50);
                this.promptString = null;
            }

            g.drawImage(this.im, 0, 0, this);
        }
    }

    public void paintComponent(Graphics g) {
//        int w = this.size().width;
//        int h = this.size().height;
//        if (this.offscreen != null) {
//            this.offscreen.drawImage(this.backDrop, 0, 0, this);
//            this.vw.paint(this.offscreen, w, h);
//            this.offscreen.setColor(Color.yellow);
//            this.box.paint(this.offscreen);
//            g.drawImage(this.im, 0, 0, this);
//        } else {

        g.drawImage(this.backDrop, 0, 0, this);
        this.vw.paint(g, getSize().width, getSize().height);
        this.box.paint ( g );
//        }
    }
}
