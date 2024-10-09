package smileModified;

import java.io.Serializable;

public interface DistributionModified extends Serializable {
   int length();

   double mean();

   double variance();

   default double sd() {
      return Math.sqrt(this.variance());
   }

   double entropy();

   double rand();

   default double[] rand(int n) {
      double[] data = new double[n];

      for(int i = 0; i < n; ++i) {
         data[i] = this.rand();
      }

      return data;
   }

   double p(double var1);

   double logp(double var1);

   double cdf(double var1);

   double quantile(double var1);

   default double likelihood(double[] x) {
      return Math.exp(this.logLikelihood(x));
   }

   default double logLikelihood(double[] x) {
      double L = 0.0D;
      double[] var8 = x;
      int var7 = x.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         double xi = var8[var6];
         L += this.logp(xi);
      }

      return L;
   }
}
