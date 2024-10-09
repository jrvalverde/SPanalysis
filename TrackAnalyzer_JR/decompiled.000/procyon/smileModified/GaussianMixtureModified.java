// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class GaussianMixtureModified extends ExponentialFamilyMixtureModified
{
    private static final long serialVersionUID = 2L;
    private static final Logger logger;
    
    static {
        logger = LoggerFactory.getLogger((Class)GaussianMixtureModified.class);
    }
    
    public GaussianMixtureModified(final Component... components) {
        this(0.0, 1, components);
    }
    
    private GaussianMixtureModified(final double L, final int n, final Component... components) {
        super(L, n, components);
        for (final Component component : components) {
            if (!(component.distribution instanceof GaussianDistributionModified)) {
                throw new IllegalArgumentException("Component " + component + " is not of Gaussian distribution.");
            }
        }
    }
    
    public static GaussianMixtureModified fit(final int k, final double[] x) {
        if (k < 2) {
            throw new IllegalArgumentException("Invalid number of components in the mixture.");
        }
        double min = MathExModified.min(x);
        final double max = MathExModified.max(x);
        final double step = (max - min) / (k + 1);
        final Component[] components = new Component[k];
        for (int i = 0; i < k; ++i) {
            components[i] = new Component(1.0 / k, new GaussianDistributionModified(min += step, step));
        }
        final ExponentialFamilyMixtureModified model = ExponentialFamilyMixtureModified.fit(x, components);
        return new GaussianMixtureModified(model.L, x.length, model.components);
    }
    
    public static GaussianMixtureModified fit(final double[] x) {
        if (x.length < 20) {
            throw new IllegalArgumentException("Too few samples.");
        }
        GaussianMixtureModified mixture = new GaussianMixtureModified(new Component[] { new Component(1.0, GaussianDistributionModified.fit(x)) });
        double bic = mixture.bic(x);
        GaussianMixtureModified.logger.info(String.format("The BIC of %s = %.4f", mixture, bic));
        for (int k = 2; k < x.length / 10; ++k) {
            final ExponentialFamilyMixtureModified model = fit(k, x);
            GaussianMixtureModified.logger.info(String.format("The BIC of %s = %.4f", model, model.bic));
            if (model.bic <= bic) {
                break;
            }
            mixture = new GaussianMixtureModified(model.L, x.length, model.components);
            bic = model.bic;
        }
        return mixture;
    }
    
    private static Component[] split(final Component... components) {
        final int k = components.length;
        int index = -1;
        double maxSigma = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < k; ++i) {
            final Component c = components[i];
            if (c.distribution.sd() > maxSigma) {
                maxSigma = c.distribution.sd();
                index = i;
            }
        }
        final Component component = components[index];
        final double priori = component.priori / 2.0;
        final double delta = component.distribution.sd();
        final double mu = component.distribution.mean();
        final Component[] mixture = new Component[k + 1];
        System.arraycopy(components, 0, mixture, 0, k);
        mixture[index] = new Component(priori, new GaussianDistributionModified(mu + delta / 2.0, delta));
        mixture[k] = new Component(priori, new GaussianDistributionModified(mu - delta / 2.0, delta));
        return mixture;
    }
}
