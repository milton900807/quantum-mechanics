
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;

public class ProgressBar extends JFrame {
    private int count = 0;
    private int max;
    private static final int FrameBottom = 75;
    private String vstring = "Welcome to the Machine";
    Graphics bg;
    Image im;

    public ProgressBar(String title, int totalClasses) {
        super(title);
        this.max = totalClasses;
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setLayout((LayoutManager) null);
        this.addNotify();
        this.resize(this.insets().left + this.insets().right + 500, this.insets().top + this.insets().bottom + 75);
    }

    public synchronized void show() {
        this.move(200, 300);
        super.show();
    }

    public void updateDownLoad() {
        ++this.count;
        if (this.count != this.max) {
            this.repaint();
        }
    }

    public void process(String processtring) {
        this.vstring = processtring;
        this.repaint();
    }

    public void update(Graphics g) {
        this.paint(g);
    }

    public void paint(Graphics g) {
        if (this.bg != null) {
            Dimension frameD = this.size();
            double percentComplete = (double) this.count * 100.0D / (double) this.max;
            int barpixelWidth = frameD.width * this.count / this.max;
            this.bg.setColor(Color.black);
            this.bg.fillRect(0, 0, frameD.width, frameD.height);
            this.bg.setColor(Color.red);
            this.bg.fill3DRect(0, 0, barpixelWidth, frameD.height, true);
            this.bg.setFont(new Font("Helvetica", 2, 18));
            int c = 255 - (int) (percentComplete / 100.0D * 255.0D);
            if (percentComplete > 50.0D) {
                this.bg.setColor(new Color(c, c, c));
                this.vstring = (int) percentComplete + " %";
            } else {
                this.bg.setColor(new Color(255, c, c));
            }

            FontMetrics fm = this.bg.getFontMetrics(this.bg.getFont());
            int stringPixelWidth = fm.stringWidth(this.vstring);
            int bottom = frameD.height / 2 + fm.getHeight() / 2;
            this.bg.drawString(this.vstring, frameD.width / 2 - stringPixelWidth / 2, bottom);
            g.drawImage(this.im, 0, 0, this);
        }
    }

    public void reshape(int x, int y, int w, int h) {
        super.reshape(x, y, w, h);

        try {
            this.im = this.createImage(w, h);
            this.bg = this.im.getGraphics();
        } catch (Exception var5) {
            System.out.println(" Failed reshapping ");
            this.bg = null;
        }
    }

    public boolean handleEvent(Event e) {
        if (e.id == 201) {
            this.dispose();
            return true;
        } else {
            return super.handleEvent(e);
        }
    }
}
