
import javax.swing.*;
import java.awt.Dimension;

public class Convert extends JPanel {
    int specMax;
    protected int inset = 20;
    double yLength;
    double xscale = 1.0D;
    double yscale = 1.0D;
    double xshift;
    double yshift;
    public double xmin;
    public double ymin;
    public double xmax = 1.0D;
    public double ymax = 1.0D;

    public Convert() {
        this.yLength = this.ymax - this.ymin;
    }

    protected double Xscreen_world(int x) {
        int xAxisValue = (int)((double)(x - this.inset) / this.xscale - this.xshift);
        return (double)xAxisValue;
    }

    protected double Yscreen_world(int screenval) {
        int yAxisValue = (int)(this.yLength - (double)(screenval - this.inset) / this.yscale);
        return (double)yAxisValue;
    }

    protected int X(double xAxisValue) {
        double x = (xAxisValue + this.xshift) * this.xscale + (double)this.inset;
        return (int)Math.round(x);
    }

    protected int Y(double yAxisValue) {
        double y = (this.yLength - (yAxisValue + this.yshift)) * this.yscale + (double)this.inset;
        return (int)Math.round(y);
    }

    public void rescale(double xAxisLength, double yAxisLength) {
        if (yAxisLength < 0.0D) {
            yAxisLength *= -1.0D;
        }
        int width = getWidth();
        int height = getHeight();
        this.yLength = yAxisLength;
        this.xscale = (double)(width - 2 * this.inset) / xAxisLength;
        this.yscale = (double)(height - 2 * this.inset) / yAxisLength;
        this.xshift = -this.xmin;
        this.yshift = -this.ymin;
    }

    public double rescaleY(double ymin, double ymax) {
        int height = getHeight();
        this.ymin = ymin;
        this.ymax = ymax;
        this.yLength = ymax - ymin;
        this.yscale = (double)(height - 2 * this.inset) / this.yLength;
        this.yshift = -ymin;
        return this.yscale;
    }

    public void rescaleX(double xAxisLength) {
        int width = getWidth();
        this.xscale = (double)(width - 2 * this.inset) / xAxisLength;
        this.xshift = -this.xmin;
    }

    public void rescaleX() {
        this.rescaleX(Math.abs(this.xmax - this.xmin));
    }

    public double getXMin() {
        return this.xmin;
    }

    public double getXMax() {
        return this.xmax;
    }

    public double getYMin() {
        return this.ymin;
    }

    public double getYMax() {
        return this.ymax;
    }

    public int getInset() {
        return this.inset;
    }

    public void setInset(int newset) {
        this.inset = newset;
    }

    public void setYMax(double newymax) {
        this.ymax = newymax;
    }

    public void setYMin(double newymin) {
        this.ymin = newymin;
    }


    public int getYMaxSc() {
        int y = this.Y(this.ymax);
        return y;
    }

    public int getYMinSc() {
        int y = this.Y(this.ymin);
        return y;
    }

    public int getXMaxSc() {
        int x = this.X(this.xmax);
        return x;
    }

    public int getXMinSc() {
        int x = this.X(this.xmin);
        return x;
    }
}
