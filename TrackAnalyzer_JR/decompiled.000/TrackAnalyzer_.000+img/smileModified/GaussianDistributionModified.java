package smileModified;

public class GaussianDistributionModified extends AbstractDistributionModified implements ExponentialFamilyModified {
   private static final long serialVersionUID = 2L;
   private static final double LOG2PIE_2 = Math.log(17.079468445347132D) / 2.0D;
   private static final double LOG2PI_2 = Math.log(6.283185307179586D) / 2.0D;
   private static final GaussianDistributionModified singleton = new GaussianDistributionModified(0.0D, 1.0D);
   public final double mu;
   public final double sigma;
   private final double variance;
   private final double entropy;
   private final double pdfConstant;
   private double z1 = Double.NaN;

   public GaussianDistributionModified(double mu, double sigma) {
      this.mu = mu;
      this.sigma = sigma;
      this.variance = sigma * sigma;
      this.entropy = Math.log(sigma) + LOG2PIE_2;
      this.pdfConstant = Math.log(sigma) + LOG2PI_2;
   }

   public static GaussianDistributionModified fit(double[] data) {
      double mu = MathExModified.mean(data);
      double sigma = MathExModified.sd(data);
      return new GaussianDistributionModified(mu, sigma);
   }

   public static GaussianDistributionModified getInstance() {
      return singleton;
   }

   public int length() {
      return 2;
   }

   public double mean() {
      return this.mu;
   }

   public double variance() {
      return this.variance;
   }

   public double sd() {
      return this.sigma;
   }

   public double entropy() {
      return this.entropy;
   }

   public String toString() {
      return String.format("Gaussian Distribution(%.4f, %.4f)", this.mu, this.sigma);
   }

   public double rand() {
      double z0;
      if (Double.isNaN(this.z1)) {
         double x;
         double y;
         double r;
         do {
            x = MathExModified.random(-1.0D, 1.0D);
            y = MathExModified.random(-1.0D, 1.0D);
            r = x * x + y * y;
         } while(r >= 1.0D);

         double z = Math.sqrt(-2.0D * Math.log(r) / r);
         this.z1 = x * z;
         z0 = y * z;
      } else {
         z0 = this.z1;
         this.z1 = Double.NaN;
      }

      return this.mu + this.sigma * z0;
   }

   public double inverseCDF() {
      double a0 = 2.50662823884D;
      double a1 = -18.61500062529D;
      double a2 = 41.39119773534D;
      double a3 = -25.44106049637D;
      double b0 = -8.4735109309D;
      double b1 = 23.08336743743D;
      double b2 = -21.06224101826D;
      double b3 = 3.13082909833D;
      double c0 = 0.3374754822726147D;
      double c1 = 0.9761690190917186D;
      double c2 = 0.1607979714918209D;
      double c3 = 0.0276438810333863D;
      double c4 = 0.0038405729373609D;
      double c5 = 3.951896511919E-4D;
      double c6 = 3.21767881768E-5D;
      double c7 = 2.888167364E-7D;
      double c8 = 3.960315187E-7D;

      double u;
      for(u = MathExModified.random(); u == 0.0D; u = MathExModified.random()) {
      }

      double y = u - 0.5D;
      double r;
      double x;
      if (Math.abs(y) < 0.42D) {
         r = y * y;
         x = y * (((-25.44106049637D * r + 41.39119773534D) * r + -18.61500062529D) * r + 2.50662823884D) / ((((3.13082909833D * r + -21.06224101826D) * r + 23.08336743743D) * r + -8.4735109309D) * r + 1.0D);
      } else {
         r = u;
         if (y > 0.0D) {
            r = 1.0D - u;
         }

         r = Math.log(-Math.log(r));
         x = 0.3374754822726147D + r * (0.9761690190917186D + r * (0.1607979714918209D + r * (0.0276438810333863D + r * (0.0038405729373609D + r * (3.951896511919E-4D + r * (3.21767881768E-5D + r * (2.888167364E-7D + r * 3.960315187E-7D)))))));
         if (y < 0.0D) {
            x = -x;
         }
      }

      return this.mu + this.sigma * x;
   }

   public double p(double x) {
      if (this.sigma == 0.0D) {
         return x == this.mu ? 1.0D : 0.0D;
      } else {
         return Math.exp(this.logp(x));
      }
   }

   public double logp(double x) {
      if (this.sigma == 0.0D) {
         return x == this.mu ? 0.0D : Double.NEGATIVE_INFINITY;
      } else {
         double d = x - this.mu;
         return -0.5D * d * d / this.variance - this.pdfConstant;
      }
   }

   public double cdf(double x) {
      if (this.sigma == 0.0D) {
         return x < this.mu ? 0.0D : 1.0D;
      } else {
         return 0.5D * ErfModified.erfc(-0.7071067811865476D * (x - this.mu) / this.sigma);
      }
   }

   public double quantile(double p) {
      if (!(p < 0.0D) && !(p > 1.0D)) {
         if (this.sigma == 0.0D) {
            return p < 1.0D ? this.mu - 1.0E-10D : this.mu;
         } else {
            return -1.4142135623730951D * this.sigma * ErfModified.inverfc(2.0D * p) + this.mu;
         }
      } else {
         throw new IllegalArgumentException("Invalid p: " + p);
      }
   }

   public MixtureModified.Component M(double[] x, double[] posteriori) {
      double alpha = 0.0D;
      double mean = 0.0D;
      double sd = 0.0D;

      int i;
      for(i = 0; i < x.length; ++i) {
         alpha += posteriori[i];
         mean += x[i] * posteriori[i];
      }

      mean /= alpha;

      for(i = 0; i < x.length; ++i) {
         double d = x[i] - mean;
         sd += d * d * posteriori[i];
      }

      sd = Math.sqrt(sd / alpha);
      return new MixtureModified.Component(alpha, new GaussianDistributionModified(mean, sd));
   }
}
