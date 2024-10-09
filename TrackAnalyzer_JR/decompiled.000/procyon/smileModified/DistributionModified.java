// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.io.Serializable;

public interface DistributionModified extends Serializable
{
    int length();
    
    double mean();
    
    double variance();
    
    default double sd() {
        return Math.sqrt(this.variance());
    }
    
    double entropy();
    
    double rand();
    
    default double[] rand(final int n) {
        final double[] data = new double[n];
        for (int i = 0; i < n; ++i) {
            data[i] = this.rand();
        }
        return data;
    }
    
    double p(final double p0);
    
    double logp(final double p0);
    
    double cdf(final double p0);
    
    double quantile(final double p0);
    
    default double likelihood(final double[] x) {
        return Math.exp(this.logLikelihood(x));
    }
    
    default double logLikelihood(final double[] x) {
        double L = 0.0;
        for (final double xi : x) {
            L += this.logp(xi);
        }
        return L;
    }
}
