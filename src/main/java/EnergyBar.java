
import javax.swing.*;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Vector;

public class EnergyBar {
    Convert c;
    private EM emspectra;
    private Transition transition;
    private Spectrum spectrum;
    private static final int SCALE_FACTOR = 1;
    private static final int MAX_LEVELS = 5;
    private int selectIndex;
    private boolean up_date = true;
    private boolean selected = false;
    private boolean paintBlack = false;
    double gamma = 45.0D;
    double absorptionEnergy;
    double maxEnergy;
    double minEnergy;
    static final Color[] color = new Color[10];
    Color boxColor;
    double step = 1.0D;
    Vector energy = new Vector();
    EnergyLevel onEl;
    allowedEnergyFunc energyFun;

    public EnergyBar(Convert conv, EM emspectra) {
        this.emspectra = emspectra;
        this.energyFun = new allowedEnergyFunc();
        if (this.energyFun == null) {
            System.exit(1);
        }

        this.c = conv;
        this.boxColor = Color.gray;
        this.up_date = false;
        this.initialize();
    }

    public EnergyBar() {
        this.initialize();
    }

    void initialize() {
        this.maxEnergy = 1.0D;
        this.minEnergy = 0.0D;
        Dimension d = this.c.getSize ();
        this.c.ymax = this.maxEnergy;
        this.c.ymin = this.minEnergy;
        this.c.xmin = (double)(d.width / 2 - 10);
        this.c.xmax = (double)(d.width / 2 + 10);
        this.c.setInset(45);
    }

    public void calculateEnergy(double m, double l, int unitmass, int unitlength, int unitenergy) {
        this.maxEnergy = 0.0D;
        this.minEnergy = 0.0D;
        this.c.ymin = 0.0D;
        this.c.ymax = 0.0D;
        this.energyFun.setUnits(unitmass, unitlength, unitenergy);
        this.energyFun.setValue(l, m);

        for(int n = 1; n < 5; ++n) {
            double worldValue = this.energyFun.apply((double)n);
            if (n == 1) {
                this.maxEnergy = worldValue;
            }

            if (worldValue > this.maxEnergy) {
                this.maxEnergy = worldValue;
            }

            this.energy.addElement(new EnergyLevel(worldValue, this.energy.size(), this.c, false));
        }

        this.absorbTransition(1, 2);
    }

    public void absorbTransition(int low, int high) {
        double el1 = this.energyFun.apply((double)low);
        double el2 = this.energyFun.apply((double)high);
        this.absorptionEnergy = Energy.cmI_nm(el2 - el1);
        this.emspectra.setArrow(this.absorptionEnergy / 1000.0D);
    }

    public void removeTransition() {
        this.transition = null;
    }

    public Spectrum calculate(Spectrum inspec, JPanel vwcanvas) {
        this.spectrum = inspec;
        Spectrum outspec = new Spectrum(inspec.getFocus(), vwcanvas, (int)this.absorptionEnergy, this.gamma);
        if (this.absorptionEnergy > 700.0D && this.absorptionEnergy < 400.0D) {
            return outspec;
        } else {
            this.transition = new Transition(this.c, outspec.getColor(), this.absorptionEnergy);
            return outspec;
        }
    }

    public boolean hasLevels() {
        return this.energy.size() > 0;
    }

    public void clearLevels() {
        this.energy.removeAllElements();
        this.transition = null;
    }

    void selectLevel(Color color) {
        this.onEl.select(color);
        this.selected = true;
    }

    void selectLevel() {
        this.onEl.select();
        this.selected = true;
        ++this.selectIndex;
    }

    void deselectLevel(int index) {
        EnergyLevel enl = (EnergyLevel)this.energy.elementAt(index);
        enl.deselect();
        --this.selectIndex;
    }

    void deselectAll() {
        for(int i = 0; i < this.energy.size(); ++i) {
            EnergyLevel enl = (EnergyLevel)this.energy.elementAt(i);
            enl.deselect();
        }

        this.selectIndex = 0;
    }

    void deselectLevel() {
        this.onEl.deselect();
        this.selectIndex = 0;
        this.selected = false;
    }

    int query(int sy) {
        int num = this.energy.size();

        for(int i = num - 1; i > -1; --i) {
            this.energy.elementAt(i);
        }

        return -1;
    }

    public void scaleAxis() {
//        this.c.setSize(d.width, d.height - d.height / 10);
        this.c.rescaleY(this.minEnergy, this.maxEnergy);
    }

    public void rescaleAxis(double newMin, double newMax) {
        this.minEnergy = newMin;
        this.maxEnergy = newMax;
        this.c.rescaleY(newMin, newMax);
    }

    public void setAbsorption(double absor) {
        this.absorptionEnergy = absor;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public void update(Graphics g) {
        this.paint(g);
    }

    public void paint(Graphics g) {
        Dimension d = this.c.size();
        this.c.xmax = (double)(d.width / 2 - 30);
        this.c.xmin = (double)(d.width / 2 - 90);
        this.c.getInset();
        this.scaleAxis();
        int x_max = this.c.getXMaxSc();
        int y_max = this.c.getYMaxSc();
        int x_min = this.c.getXMinSc();
        int y_min = this.c.getYMinSc();
        int totalEnergy = y_min - y_max;
        int totalSize = x_max - x_min;
        g.setColor(Color.darkGray);
        g.fillRect(x_min + 9, y_max + 9, totalSize, totalEnergy);
        g.setColor(this.boxColor);
        g.fill3DRect(x_min, y_max, totalSize, totalEnergy + 5, true);
        int lsize = this.energy.size();

        EnergyLevel el;
        for(int i = 0; i < lsize; ++i) {
            el = (EnergyLevel)this.energy.elementAt(i);
            el.drawEnergy(g);
        }

        if (this.transition != null && this.energy.size() > 1) {
            el = (EnergyLevel)this.energy.elementAt(0);
            EnergyLevel el2 = (EnergyLevel)this.energy.elementAt(1);
            this.transition.drawSplitting(g, el.energy, el2.energy);
        }

        this.up_date = true;
    }
}
