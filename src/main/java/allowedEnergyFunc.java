
class allowedEnergyFunc extends Function {
    public static final int KILOGRAMS = 0;
    public static final int GRAMS = 1;
    public static final int AMU = 2;
    public static final int ELECTRON = 3;
    int massMode = 3;
    public static final int M = 0;
    public static final int CM = 1;
    public static final int ANGSTROMS = 2;
    int lengthMode = 2;
    public static final int JOULES = 0;
    public static final int ELECTRON_VOLTS = 1;
    public static final int CMI = 2;
    int energyMode = 2;
    static final double hInJS = 6.626075500000001D * Math.pow(10.0D, -34.0D);
    double h;
    double length;
    double mass;

    public allowedEnergyFunc() {
        this.h = hInJS;
        super.yLabel = "energy";
        super.xLabel = "distance";
        this.setUnits(this.massMode, this.lengthMode, this.energyMode);
    }

    public int[] setUnits(int mmode, int lmode, int emode) {
        int[] mode = new int[]{mmode, lmode, emode};
        this.resetPlank();
        switch(mmode) {
            case 0:
                break;
            case 1:
                this.h *= Math.sqrt(1000.0D);
                break;
            case 2:
                this.h *= Math.sqrt(6.0223D * Math.pow(10.0D, 26.0D));
                break;
            case 3:
                this.h *= Math.sqrt(1.0D / (9.1093897D * Math.pow(10.0D, -31.0D)));
                break;
            default:
                this.h *= Math.sqrt(1.0D);
        }

        switch(lmode) {
            case 0:
                break;
            case 1:
                this.h *= 100.0D;
                break;
            case 2:
                this.h *= Math.pow(10.0D, 10.0D);
                break;
            default:
                this.h = this.h;
        }

        switch(emode) {
            case 0:
            default:
                break;
            case 1:
                this.h *= Math.sqrt(1.0D / (1.6D * Math.pow(10.0D, -19.0D)));
                break;
            case 2:
                this.h *= Math.sqrt(5.034800000000001D * Math.pow(10.0D, 22.0D));
        }

        return mode;
    }

    public void resetPlank() {
        this.h = hInJS;
    }

    public void setValue(double a, double b) {
        this.length = a;
        this.mass = b;
    }

    public double apply(double xin, double yin) {
        return yin;
    }

    public double apply(double n) {
        double E = 0.0D;
        return n * n * this.h * this.h / (8.0D * this.mass * this.length * this.length);
    }

    public String toString() {
        return "allowedEnergy ****** function";
    }
}
