
public class ConvertLogX extends Convert {
    public void rescaleX(double newxmin, double newxmax) {
        super.xmin = 0.43429448D * Math.log(newxmin);
        super.xmax = 0.43429448D * Math.log(newxmax);
        double xAxisLength = Math.abs(super.xmax - super.xmin);
        super.xscale = (double) (getWidth()- 2 * super.inset) / xAxisLength;
        super.xshift = -super.xmin;
    }

    protected double Xscreen_world(int x) {
        double xAxisValue = (double) (x - super.inset) / super.xscale - super.xshift;
        return Math.exp(xAxisValue * Math.log(10.0D));
    }

    protected int X(double xAxisValue) {
        double x = (0.43429448D * Math.log(xAxisValue) + super.xshift) * super.xscale + (double) super.inset;
        return (int) Math.round(x);
    }

    public int getXMaxSc() {
        double x = (0.43429448D * Math.log(super.xmax) + super.xshift) * super.xscale + (double) super.inset;
        return (int) Math.round(x);
    }

    public int getXMinSc() {
        double x = (0.43429448D * Math.log(super.xmin) + super.xshift) * super.xscale + (double) super.inset;
        return (int) Math.round(x);
    }

    public ConvertLogX() {
    }
}
