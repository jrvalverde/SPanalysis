// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public class ErfModified
{
    private static final double[] cof;
    
    static {
        cof = new double[] { -1.3026537197817094, 0.6419697923564902, 0.019476473204185836, -0.00956151478680863, -9.46595344482036E-4, 3.66839497852761E-4, 4.2523324806907E-5, -2.0278578112534E-5, -1.624290004647E-6, 1.30365583558E-6, 1.5626441722E-8, -8.5238095915E-8, 6.529054439E-9, 5.059343495E-9, -9.91364156E-10, -2.27365122E-10, 9.6467911E-11, 2.394038E-12, -6.886027E-12, 8.94487E-13, 3.13092E-13, -1.12708E-13, 3.81E-16, 7.106E-15, -1.523E-15, -9.4E-17, 1.21E-16, -2.8E-17 };
    }
    
    private ErfModified() {
    }
    
    public static double erf(final double x) {
        if (x >= 0.0) {
            return 1.0 - erfccheb(x);
        }
        return erfccheb(-x) - 1.0;
    }
    
    public static double erfc(final double x) {
        if (x >= 0.0) {
            return erfccheb(x);
        }
        return 2.0 - erfccheb(-x);
    }
    
    public static double erfcc(final double x) {
        final double z = Math.abs(x);
        final double t = 2.0 / (2.0 + z);
        final double ans = t * Math.exp(-z * z - 1.26551223 + t * (1.00002368 + t * (0.37409196 + t * (0.09678418 + t * (-0.18628806 + t * (0.27886807 + t * (-1.13520398 + t * (1.48851587 + t * (-0.82215223 + t * 0.17087277)))))))));
        return (x >= 0.0) ? ans : (2.0 - ans);
    }
    
    private static double erfccheb(final double z) {
        double d = 0.0;
        double dd = 0.0;
        if (z < 0.0) {
            throw new IllegalArgumentException("erfccheb requires non-negative argument");
        }
        final double t = 2.0 / (2.0 + z);
        final double ty = 4.0 * t - 2.0;
        for (int j = ErfModified.cof.length - 1; j > 0; --j) {
            final double tmp = d;
            d = ty * d - dd + ErfModified.cof[j];
            dd = tmp;
        }
        return t * Math.exp(-z * z + 0.5 * (ErfModified.cof[0] + ty * d) - dd);
    }
    
    public static double inverfc(final double p) {
        if (p >= 2.0) {
            return -100.0;
        }
        if (p <= 0.0) {
            return 100.0;
        }
        final double pp = (p < 1.0) ? p : (2.0 - p);
        final double t = Math.sqrt(-2.0 * Math.log(pp / 2.0));
        double x = -0.70711 * ((2.30753 + t * 0.27061) / (1.0 + t * (0.99229 + t * 0.04481)) - t);
        for (int j = 0; j < 2; ++j) {
            final double err = erfc(x) - pp;
            x += err / (1.1283791670955126 * Math.exp(-x * x) - x * err);
        }
        return (p < 1.0) ? x : (-x);
    }
    
    public static double inverf(final double p) {
        return inverfc(1.0 - p);
    }
}
