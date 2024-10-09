// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public abstract class AbstractDistributionModified implements DistributionModified
{
    protected double rejection(final double pmax, final double xmin, final double xmax) {
        double x;
        double y;
        do {
            x = xmin + MathExModified.random() * (xmax - xmin);
            y = MathExModified.random() * pmax;
        } while (this.p(x) < y);
        return x;
    }
    
    protected double inverseTransformSampling() {
        final double u = MathExModified.random();
        return this.quantile(u);
    }
    
    protected double quantile(final double p, double xmin, double xmax, final double eps) {
        if (eps <= 0.0) {
            throw new IllegalArgumentException("Invalid epsilon: " + eps);
        }
        while (Math.abs(xmax - xmin) > eps) {
            final double xmed = (xmax + xmin) / 2.0;
            if (this.cdf(xmed) > p) {
                xmax = xmed;
            }
            else {
                xmin = xmed;
            }
        }
        return xmin;
    }
    
    protected double quantile(final double p, final double xmin, final double xmax) {
        return this.quantile(p, xmin, xmax, 1.0E-6);
    }
}
