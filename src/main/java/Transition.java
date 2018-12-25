
import java.awt.Color;
import java.awt.Graphics;

class Transition {
    Convert c;
    Color absorbedColor;
    double absorptionEnergy;

    public Transition(Convert _parent, Color abcolor, double aEnergy) {
        this.absorptionEnergy = aEnergy;
        this.c = _parent;
        this.absorbedColor = abcolor;
    }

    public void drawSplitting(Graphics g, double low, double high) {
        int dx = (this.c.getXMaxSc() - this.c.getXMinSc()) / 2;
        int x = this.c.getXMinSc() + dx;
        int ylow = this.c.Y(low);
        int yhigh = this.c.Y(high);
        int yhighhalf = ylow - (ylow - yhigh) / 2;
        g.setColor(this.absorbedColor);
        g.drawLine(x - 2, ylow, x, yhigh);
        g.drawLine(x, ylow, x, yhigh);
        g.drawLine(x + 2, ylow, x, yhigh);
        g.drawLine(x, yhigh, x + 5, yhigh + 10);
        g.drawLine(x, yhigh, x - 5, yhigh + 10);
        g.setColor(Color.yellow);
        g.drawLine(this.c.getXMaxSc(), ylow, this.c.getXMaxSc() + 18, yhighhalf);
        g.drawLine(this.c.getXMaxSc(), yhigh, this.c.getXMaxSc() + 18, yhighhalf);
        g.drawString("Wavelength", this.c.getXMaxSc() + 25, yhighhalf);
        g.drawString((float)this.absorptionEnergy + "nm", this.c.getXMaxSc() + 25, yhighhalf + 15);
    }
}
