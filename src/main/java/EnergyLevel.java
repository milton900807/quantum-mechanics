
import java.awt.Color;
import java.awt.Graphics;

class EnergyLevel {
    private double h = 6.626075D * Math.pow(10.0D, -34.0D);
    private double lightspeed = 2.99792458D * Math.pow(10.0D, 8.0D);
    Convert c;
    double energy;
    String levellabel;
    boolean selection_flag = true;
    Color selectColor;
    int number;

    public EnergyLevel(double worldValue, int number, Convert c, boolean selected) {
        this.selectColor = Color.red;
        this.energy = worldValue;
        this.selection_flag = selected;
        this.c = c;
        this.number = number;
    }

    public void drawEnergy(Graphics g) {
        int totalX = (int)(this.c.xmax - this.c.xmin);
        this.c.getXMinSc();
        this.c.getXMaxSc();
        this.c.getXMaxSc();
        int screen_y = this.c.Y(this.energy);
        String vstring = "" + (float)this.energy + "cm";
        int typesetx = this.c.getXMinSc() - (this.c.inset + 35);
        g.setColor(Color.lightGray);
        g.fill3DRect(this.c.getXMinSc(), screen_y, totalX, 3, true);
        g.setColor(Color.white);
        g.drawString(vstring, typesetx, screen_y - 5);
        int stringPixelWidth = vstring.length() * 6;
        g.drawString("-1", typesetx + stringPixelWidth + 6, screen_y - 15);
    }

    public boolean selected() {
        return this.selection_flag;
    }

    public void select() {
        this.selection_flag = true;
    }

    public void select(Color color) {
        this.selection_flag = true;
        this.selectColor = color;
    }

    public void deselect() {
        this.selection_flag = false;
    }
}
