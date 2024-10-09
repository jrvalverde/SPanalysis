// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class ExponentialFamilyMixtureModified extends MixtureModified
{
    private static final long serialVersionUID = 2L;
    private static final Logger logger;
    public final double L;
    public final double bic;
    
    static {
        logger = LoggerFactory.getLogger((Class)ExponentialFamilyMixtureModified.class);
    }
    
    public ExponentialFamilyMixtureModified(final Component... components) {
        this(0.0, 1, components);
    }
    
    ExponentialFamilyMixtureModified(final double L, final int n, final Component... components) {
        super(components);
        for (final Component component : components) {
            if (!(component.distribution instanceof ExponentialFamilyModified)) {
                throw new IllegalArgumentException("Component " + component + " is not of exponential family.");
            }
        }
        this.L = L;
        this.bic = L - 0.5 * this.length() * Math.log(n);
    }
    
    public static ExponentialFamilyMixtureModified fit(final double[] x, final Component... components) {
        return fit(x, components, 0.0, 500, 1.0E-4);
    }
    
    public static ExponentialFamilyMixtureModified fit(final double[] x, final Component[] components, final double gamma, final int maxIter, final double tol) {
        if (x.length < components.length / 2) {
            throw new IllegalArgumentException("Too many components");
        }
        if (gamma < 0.0 || gamma > 0.2) {
            throw new IllegalArgumentException("Invalid regularization factor gamma.");
        }
        final int n = x.length;
        final int k = components.length;
        final double[][] posteriori = new double[k][n];
        double L = 0.0;
        double diff = Double.MAX_VALUE;
        for (int iter = 1; iter <= maxIter && diff > tol; ++iter) {
            for (int i = 0; i < k; ++i) {
                final Component c = components[i];
                for (int j = 0; j < n; ++j) {
                    posteriori[i][j] = c.priori * c.distribution.p(x[j]);
                }
            }
            for (int l = 0; l < n; ++l) {
                double p = 0.0;
                for (int m = 0; m < k; ++m) {
                    p += posteriori[m][l];
                }
                for (final double[] array : posteriori) {
                    final int n2 = l;
                    array[n2] /= p;
                }
                if (gamma > 0.0) {
                    for (int m = 0; m < k; ++m) {
                        final double[] array2 = posteriori[m];
                        final int n3 = l;
                        array2[n3] *= 1.0 + gamma * MathExModified.log2(posteriori[m][l]);
                        if (Double.isNaN(posteriori[m][l]) || posteriori[m][l] < 0.0) {
                            posteriori[m][l] = 0.0;
                        }
                    }
                }
            }
            double Z = 0.0;
            for (int i2 = 0; i2 < k; ++i2) {
                components[i2] = ((ExponentialFamilyModified)components[i2].distribution).M(x, posteriori[i2]);
                Z += components[i2].priori;
            }
            for (int i2 = 0; i2 < k; ++i2) {
                components[i2] = new Component(components[i2].priori / Z, components[i2].distribution);
            }
            double loglikelihood = 0.0;
            for (final double xi : x) {
                double p2 = 0.0;
                for (final Component c2 : components) {
                    p2 += c2.priori * c2.distribution.p(xi);
                }
                if (p2 > 0.0) {
                    loglikelihood += Math.log(p2);
                }
            }
            diff = loglikelihood - L;
            L = loglikelihood;
            if (iter % 10 == 0) {
                ExponentialFamilyMixtureModified.logger.info(String.format("The log-likelihood after %d iterations: %.4f", iter, L));
            }
        }
        return new ExponentialFamilyMixtureModified(L, x.length, components);
    }
}
