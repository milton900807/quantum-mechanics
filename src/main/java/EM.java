
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class EM extends ConvertLogX {
    public static final double MaxWavelength = Math.pow(10.0D, 8.0D);
    public static final double MinWavelength = Math.pow(10.0D, -6.0D);
    Color bgColor;
    double arrow = -1.0D;

    public EM() {
        this.bgColor = Color.black;

    }

    public void paintComponent(Graphics g) {
        Dimension d = this.getSize();

        rescaleX(MinWavelength, MaxWavelength);
        int xsclength = this.X(MaxWavelength) - this.X(MinWavelength);

        if (xsclength < 0) {
            xsclength *= -1;
        }

        g.setColor(this.bgColor);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.lightGray);
        g.fill3DRect(this.X(MinWavelength), this.getYMinSc(), xsclength, 3, true);
        g.drawString("visible", this.X(1.0D) - 15, this.getYMaxSc() - 20);
        g.drawString("gamma rays", this.X(MinWavelength) - 20, this.getYMinSc() - 5);
        g.drawString("x - rays", this.X(Math.pow(10.0D, -4.0D)), this.getYMinSc() - 20);
        g.drawString(" UV  ", this.X(Math.pow(10.0D, -2.0D)), this.getYMinSc() - 5);
        g.drawString("  infrared", this.X(10.0D) - 10, this.getYMinSc() - 5);
        g.drawString("  microwave", this.X(Math.pow(10.0D, 4.0D)), this.getYMinSc() - 20);
        g.drawString(" TV/radio ", this.X(Math.pow(10.0D, 7.0D)), this.getYMinSc() - 5);
        g.fillOval(this.X(MinWavelength) - 8, this.getYMinSc() - 4, 8, 8);
        g.fillOval(this.X(MaxWavelength), this.getYMinSc() - 4, 8, 8);
        g.setColor(Color.red);
        g.fillRect(this.X(0.4D), this.getYMinSc() - 5, 3, 15);
        g.setColor(Color.orange);
        g.fillRect(this.X(0.45D), this.getYMinSc() - 5, 3, 15);
        g.setColor(Color.yellow);
        g.fillRect(this.X(0.5D), this.getYMinSc() - 5, 3, 15);
        g.setColor(Color.green);
        g.fillRect(this.X(0.55D), this.getYMinSc() - 5, 3, 15);
        g.setColor(Color.blue);
        g.fillRect(this.X(0.6D), this.getYMinSc() - 5, 3, 15);
        if (this.arrow >= 0.0D) {
            g.setColor(Color.yellow);
            if (this.arrow >= MinWavelength && this.arrow <= MaxWavelength) {
                g.drawLine(this.X(this.arrow), this.getYMinSc() + 20, this.X(this.arrow), this.getYMinSc());
                g.drawLine(this.X(this.arrow), this.getYMinSc(), this.X(this.arrow) - 5, this.getYMinSc() + 5);
                g.drawLine(this.X(this.arrow), this.getYMinSc(), this.X(this.arrow) + 5, this.getYMinSc() + 5);
                g.drawString("" + (float)this.arrow * 1000.0F + " nm", this.X(this.arrow), this.getYMinSc() + 35);
            } else if (this.arrow < MinWavelength) {
                g.drawLine(this.X(MinWavelength), this.getYMinSc() + 20, this.X(MinWavelength), this.getYMinSc());
                g.drawLine(this.X(MinWavelength), this.getYMinSc(), this.X(MinWavelength) - 5, this.getYMinSc() + 5);
                g.drawLine(this.X(MinWavelength), this.getYMinSc(), this.X(MinWavelength) + 5, this.getYMinSc() + 5);
                g.drawString("" + (float)this.arrow * 1000.0F + " nm", this.X(MinWavelength), this.getYMinSc() + 35);
            } else {
                if (this.arrow > MaxWavelength) {
                    g.drawLine(this.X(MaxWavelength), this.getYMinSc() + 20, this.X(MaxWavelength), this.getYMinSc());
                    g.drawLine(this.X(MaxWavelength), this.getYMinSc(), this.X(MaxWavelength) - 5, this.getYMinSc() + 5);
                    g.drawLine(this.X(MaxWavelength), this.getYMinSc(), this.X(MaxWavelength) + 5, this.getYMinSc() + 5);
                    g.drawString("" + (float)this.arrow * 1000.0F + " nm", this.X(MaxWavelength) - 25, this.getYMinSc() + 35);
                }

            }
        }
    }

    public void setArrow(double micrometers) {
        this.arrow = micrometers;
        this.repaint();
    }

}
