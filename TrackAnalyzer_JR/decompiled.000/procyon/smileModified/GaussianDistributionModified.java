// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public class GaussianDistributionModified extends AbstractDistributionModified implements ExponentialFamilyModified
{
    private static final long serialVersionUID = 2L;
    private static final double LOG2PIE_2;
    private static final double LOG2PI_2;
    private static final GaussianDistributionModified singleton;
    public final double mu;
    public final double sigma;
    private final double variance;
    private final double entropy;
    private final double pdfConstant;
    private double z1;
    
    static {
        LOG2PIE_2 = Math.log(17.079468445347132) / 2.0;
        LOG2PI_2 = Math.log(6.283185307179586) / 2.0;
        singleton = new GaussianDistributionModified(0.0, 1.0);
    }
    
    public GaussianDistributionModified(final double mu, final double sigma) {
        this.z1 = Double.NaN;
        this.mu = mu;
        this.sigma = sigma;
        this.variance = sigma * sigma;
        this.entropy = Math.log(sigma) + GaussianDistributionModified.LOG2PIE_2;
        this.pdfConstant = Math.log(sigma) + GaussianDistributionModified.LOG2PI_2;
    }
    
    public static GaussianDistributionModified fit(final double[] data) {
        final double mu = MathExModified.mean(data);
        final double sigma = MathExModified.sd(data);
        return new GaussianDistributionModified(mu, sigma);
    }
    
    public static GaussianDistributionModified getInstance() {
        return GaussianDistributionModified.singleton;
    }
    
    @Override
    public int length() {
        return 2;
    }
    
    @Override
    public double mean() {
        return this.mu;
    }
    
    @Override
    public double variance() {
        return this.variance;
    }
    
    @Override
    public double sd() {
        return this.sigma;
    }
    
    @Override
    public double entropy() {
        return this.entropy;
    }
    
    @Override
    public String toString() {
        return String.format("Gaussian Distribution(%.4f, %.4f)", this.mu, this.sigma);
    }
    
    @Override
    public double rand() {
        double z2;
        if (Double.isNaN(this.z1)) {
            double r;
            double x;
            double y;
            do {
                x = MathExModified.random(-1.0, 1.0);
                y = MathExModified.random(-1.0, 1.0);
                r = x * x + y * y;
            } while (r >= 1.0);
            final double z = Math.sqrt(-2.0 * Math.log(r) / r);
            this.z1 = x * z;
            z2 = y * z;
        }
        else {
            z2 = this.z1;
            this.z1 = Double.NaN;
        }
        return this.mu + this.sigma * z2;
    }
    
    public double inverseCDF() {
        final double a0 = 2.50662823884;
        final double a2 = -18.61500062529;
        final double a3 = 41.39119773534;
        final double a4 = -25.44106049637;
        final double b0 = -8.4735109309;
        final double b2 = 23.08336743743;
        final double b3 = -21.06224101826;
        final double b4 = 3.13082909833;
        final double c0 = 0.3374754822726147;
        final double c2 = 0.9761690190917186;
        final double c3 = 0.1607979714918209;
        final double c4 = 0.0276438810333863;
        final double c5 = 0.0038405729373609;
        final double c6 = 3.951896511919E-4;
        final double c7 = 3.21767881768E-5;
        final double c8 = 2.888167364E-7;
        final double c9 = 3.960315187E-7;
        double u;
        for (u = MathExModified.random(); u == 0.0; u = MathExModified.random()) {}
        final double y = u - 0.5;
        double x;
        if (Math.abs(y) < 0.42) {
            final double r = y * y;
            x = y * (((-25.44106049637 * r + 41.39119773534) * r - 18.61500062529) * r + 2.50662823884) / ((((3.13082909833 * r - 21.06224101826) * r + 23.08336743743) * r - 8.4735109309) * r + 1.0);
        }
        else {
            double r = u;
            if (y > 0.0) {
                r = 1.0 - u;
            }
            r = Math.log(-Math.log(r));
            x = 0.3374754822726147 + r * (0.9761690190917186 + r * (0.1607979714918209 + r * (0.0276438810333863 + r * (0.0038405729373609 + r * (3.951896511919E-4 + r * (3.21767881768E-5 + r * (2.888167364E-7 + r * 3.960315187E-7)))))));
            if (y < 0.0) {
                x = -x;
            }
        }
        return this.mu + this.sigma * x;
    }
    
    @Override
    public double p(final double x) {
        if (this.sigma != 0.0) {
            return Math.exp(this.logp(x));
        }
        if (x == this.mu) {
            return 1.0;
        }
        return 0.0;
    }
    
    @Override
    public double logp(final double x) {
        if (this.sigma != 0.0) {
            final double d = x - this.mu;
            return -0.5 * d * d / this.variance - this.pdfConstant;
        }
        if (x == this.mu) {
            return 0.0;
        }
        return Double.NEGATIVE_INFINITY;
    }
    
    @Override
    public double cdf(final double x) {
        if (this.sigma != 0.0) {
            return 0.5 * ErfModified.erfc(-0.7071067811865476 * (x - this.mu) / this.sigma);
        }
        if (x < this.mu) {
            return 0.0;
        }
        return 1.0;
    }
    
    @Override
    public double quantile(final double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("Invalid p: " + p);
        }
        if (this.sigma != 0.0) {
            return -1.4142135623730951 * this.sigma * ErfModified.inverfc(2.0 * p) + this.mu;
        }
        if (p < 1.0) {
            return this.mu - 1.0E-10;
        }
        return this.mu;
    }
    
    @Override
    public MixtureModified.Component M(final double[] x, final double[] posteriori) {
        double alpha = 0.0;
        double mean = 0.0;
        double sd = 0.0;
        for (int i = 0; i < x.length; ++i) {
            alpha += posteriori[i];
            mean += x[i] * posteriori[i];
        }
        mean /= alpha;
        for (int i = 0; i < x.length; ++i) {
            final double d = x[i] - mean;
            sd += d * d * posteriori[i];
        }
        sd = Math.sqrt(sd / alpha);
        return new MixtureModified.Component(alpha, new GaussianDistributionModified(mean, sd));
    }
}
