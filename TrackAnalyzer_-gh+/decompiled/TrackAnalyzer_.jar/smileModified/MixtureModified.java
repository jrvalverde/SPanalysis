package smileModified;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MixtureModified extends AbstractDistributionModified {
   private static final long serialVersionUID = 2L;
   public final MixtureModified.Component[] components;

   public MixtureModified(MixtureModified.Component... components) {
      if (components.length == 0) {
         throw new IllegalStateException("Empty mixture!");
      } else {
         double sum = 0.0D;
         MixtureModified.Component[] var7 = components;
         int var6 = components.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            MixtureModified.Component component = var7[var5];
            sum += component.priori;
         }

         if (Math.abs(sum - 1.0D) > 0.001D) {
            throw new IllegalArgumentException("The sum of priori is not equal to 1.");
         } else {
            this.components = components;
         }
      }
   }

   public double[] posteriori(double x) {
      int k = this.components.length;
      double[] prob = new double[k];

      for(int i = 0; i < k; ++i) {
         MixtureModified.Component c = this.components[i];
         prob[i] = c.priori * c.distribution.p(x);
      }

      double p = MathExModified.sum(prob);

      for(int i = 0; i < k; ++i) {
         prob[i] /= p;
      }

      return prob;
   }

   public int map(double x) {
      int k = this.components.length;
      double[] prob = new double[k];

      for(int i = 0; i < k; ++i) {
         MixtureModified.Component c = this.components[i];
         prob[i] = c.priori * c.distribution.p(x);
      }

      return MathExModified.whichMax(prob);
   }

   public double mean() {
      double mu = 0.0D;
      MixtureModified.Component[] var6;
      int var5 = (var6 = this.components).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         MixtureModified.Component c = var6[var4];
         mu += c.priori * c.distribution.mean();
      }

      return mu;
   }

   public double variance() {
      double variance = 0.0D;
      MixtureModified.Component[] var6;
      int var5 = (var6 = this.components).length;

      for(int var4 = 0; var4 < var5; ++var4) {
         MixtureModified.Component c = var6[var4];
         variance += c.priori * c.priori * c.distribution.variance();
      }

      return variance;
   }

   public double entropy() {
      throw new UnsupportedOperationException("Mixture does not support entropy()");
   }

   public double p(double x) {
      double p = 0.0D;
      MixtureModified.Component[] var8;
      int var7 = (var8 = this.components).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         MixtureModified.Component c = var8[var6];
         p += c.priori * c.distribution.p(x);
      }

      return p;
   }

   public double logp(double x) {
      return Math.log(this.p(x));
   }

   public double cdf(double x) {
      double p = 0.0D;
      MixtureModified.Component[] var8;
      int var7 = (var8 = this.components).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         MixtureModified.Component c = var8[var6];
         p += c.priori * c.distribution.cdf(x);
      }

      return p;
   }

   public double rand() {
      double r = MathExModified.random();
      double p = 0.0D;
      MixtureModified.Component[] var8;
      int var7 = (var8 = this.components).length;

      for(int var6 = 0; var6 < var7; ++var6) {
         MixtureModified.Component g = var8[var6];
         p += g.priori;
         if (r <= p) {
            return g.distribution.rand();
         }
      }

      throw new IllegalStateException();
   }

   public double quantile(double p) {
      if (!(p < 0.0D) && !(p > 1.0D)) {
         double inc = 1.0D;
         double x = (double)((int)this.mean());
         double xl;
         double xu;
         if (p < this.cdf(x)) {
            do {
               x -= inc;
               inc *= 2.0D;
            } while(p < this.cdf(x));

            xl = x;
            xu = x + inc / 2.0D;
         } else {
            do {
               x += inc;
               inc *= 2.0D;
            } while(p > this.cdf(x));

            xu = x;
            xl = x - inc / 2.0D;
         }

         return this.quantile(p, xl, xu);
      } else {
         throw new IllegalArgumentException("Invalid p: " + p);
      }
   }

   public int length() {
      int length = this.components.length - 1;
      MixtureModified.Component[] var5;
      int var4 = (var5 = this.components).length;

      for(int var3 = 0; var3 < var4; ++var3) {
         MixtureModified.Component component = var5[var3];
         length += component.distribution.length();
      }

      return length;
   }

   public int size() {
      return this.components.length;
   }

   public double bic(double[] data) {
      int n = data.length;
      double logLikelihood = 0.0D;
      double[] var9 = data;
      int var8 = data.length;

      for(int var7 = 0; var7 < var8; ++var7) {
         double x = var9[var7];
         double p = this.p(x);
         if (p > 0.0D) {
            logLikelihood += Math.log(p);
         }
      }

      return logLikelihood - 0.5D * (double)this.length() * Math.log((double)n);
   }

   public String toString() {
      return (String)Arrays.stream(this.components).map((component) -> {
         return String.format("%.2f x %s", component.priori, component.distribution);
      }).collect(Collectors.joining(" + ", String.format("Mixture(%d)[", this.components.length), "]"));
   }

   public static class Component implements Serializable {
      private static final long serialVersionUID = 2L;
      public final double priori;
      public final DistributionModified distribution;

      public Component(double priori, DistributionModified distribution) {
         this.priori = priori;
         this.distribution = distribution;
      }
   }
}
