// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.io.Serializable;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Arrays;

public class MixtureModified extends AbstractDistributionModified
{
    private static final long serialVersionUID = 2L;
    public final Component[] components;
    
    public MixtureModified(final Component... components) {
        if (components.length == 0) {
            throw new IllegalStateException("Empty mixture!");
        }
        double sum = 0.0;
        for (final Component component : components) {
            sum += component.priori;
        }
        if (Math.abs(sum - 1.0) > 0.001) {
            throw new IllegalArgumentException("The sum of priori is not equal to 1.");
        }
        this.components = components;
    }
    
    public double[] posteriori(final double x) {
        final int k = this.components.length;
        final double[] prob = new double[k];
        for (int i = 0; i < k; ++i) {
            final Component c = this.components[i];
            prob[i] = c.priori * c.distribution.p(x);
        }
        final double p = MathExModified.sum(prob);
        for (int j = 0; j < k; ++j) {
            final double[] array = prob;
            final int n = j;
            array[n] /= p;
        }
        return prob;
    }
    
    public int map(final double x) {
        final int k = this.components.length;
        final double[] prob = new double[k];
        for (int i = 0; i < k; ++i) {
            final Component c = this.components[i];
            prob[i] = c.priori * c.distribution.p(x);
        }
        return MathExModified.whichMax(prob);
    }
    
    @Override
    public double mean() {
        double mu = 0.0;
        Component[] components;
        for (int length = (components = this.components).length, i = 0; i < length; ++i) {
            final Component c = components[i];
            mu += c.priori * c.distribution.mean();
        }
        return mu;
    }
    
    @Override
    public double variance() {
        double variance = 0.0;
        Component[] components;
        for (int length = (components = this.components).length, i = 0; i < length; ++i) {
            final Component c = components[i];
            variance += c.priori * c.priori * c.distribution.variance();
        }
        return variance;
    }
    
    @Override
    public double entropy() {
        throw new UnsupportedOperationException("Mixture does not support entropy()");
    }
    
    @Override
    public double p(final double x) {
        double p = 0.0;
        Component[] components;
        for (int length = (components = this.components).length, i = 0; i < length; ++i) {
            final Component c = components[i];
            p += c.priori * c.distribution.p(x);
        }
        return p;
    }
    
    @Override
    public double logp(final double x) {
        return Math.log(this.p(x));
    }
    
    @Override
    public double cdf(final double x) {
        double p = 0.0;
        Component[] components;
        for (int length = (components = this.components).length, i = 0; i < length; ++i) {
            final Component c = components[i];
            p += c.priori * c.distribution.cdf(x);
        }
        return p;
    }
    
    @Override
    public double rand() {
        final double r = MathExModified.random();
        double p = 0.0;
        Component[] components;
        for (int length = (components = this.components).length, i = 0; i < length; ++i) {
            final Component g = components[i];
            p += g.priori;
            if (r <= p) {
                return g.distribution.rand();
            }
        }
        throw new IllegalStateException();
    }
    
    @Override
    public double quantile(final double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("Invalid p: " + p);
        }
        double inc = 1.0;
        double x = (int)this.mean();
        double xl;
        double xu;
        if (p < this.cdf(x)) {
            do {
                x -= inc;
                inc *= 2.0;
            } while (p < this.cdf(x));
            xl = x;
            xu = x + inc / 2.0;
        }
        else {
            do {
                x += inc;
                inc *= 2.0;
            } while (p > this.cdf(x));
            xu = x;
            xl = x - inc / 2.0;
        }
        return this.quantile(p, xl, xu);
    }
    
    @Override
    public int length() {
        int length = this.components.length - 1;
        Component[] components;
        for (int length2 = (components = this.components).length, i = 0; i < length2; ++i) {
            final Component component = components[i];
            length += component.distribution.length();
        }
        return length;
    }
    
    public int size() {
        return this.components.length;
    }
    
    public double bic(final double[] data) {
        final int n = data.length;
        double logLikelihood = 0.0;
        for (final double x : data) {
            final double p = this.p(x);
            if (p > 0.0) {
                logLikelihood += Math.log(p);
            }
        }
        return logLikelihood - 0.5 * this.length() * Math.log(n);
    }
    
    @Override
    public String toString() {
        return Arrays.<Component>stream(this.components).<Object>map(component -> String.format("%.2f x %s", component.priori, component.distribution)).<String, ?>collect((Collector<? super Object, ?, String>)Collectors.joining(" + ", String.format("Mixture(%d)[", this.components.length), "]"));
    }
    
    public static class Component implements Serializable
    {
        private static final long serialVersionUID = 2L;
        public final double priori;
        public final DistributionModified distribution;
        
        public Component(final double priori, final DistributionModified distribution) {
            this.priori = priori;
            this.distribution = distribution;
        }
    }
}
