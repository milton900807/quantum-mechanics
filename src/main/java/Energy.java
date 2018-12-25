

public class Energy {
    static final double lightSpeed = 3.0D * Math.pow(10.0D, 10.0D);

    public static double J_eV(double var0) {
        double var2 = 1.6D * Math.pow(10.0D, -9.0D) * var0;
        return var2;
    }

    public static double J_cmI(double var0) {
        double var2 = var0 * 11.96266D;
        return var2;
    }

    public static double cmI_nm(double var0) {
        double var2 = 1.0D / var0 * Math.pow(10.0D, 7.0D);
        return var2;
    }

    public static double cmi_sI(double var0) {
        double var2 = var0 * lightSpeed;
        return var2;
    }

    public Energy() {
    }
}
