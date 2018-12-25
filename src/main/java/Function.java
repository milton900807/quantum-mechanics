
public abstract class Function {
    public int NUM_ARGUMENTS = 1;
    public double xmin;
    public double xmax;
    public double ymin;
    public double ymax;
    public double step;
    public String xLabel;
    public String yLabel;
    double a;
    double b;

    public void setValue(double a) {
        this.a = a;
    }

    public void setValue(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public abstract double apply(double var1);

    public void setFunctionRange(Convert c) {
        this.xmax = c.xmax;
        this.xmin = c.xmin;
        this.ymax = c.ymax;
        this.ymin = c.ymin;
    }

    public abstract double apply(double var1, double var3);

    public abstract String toString();

    public Function() {
    }
}
