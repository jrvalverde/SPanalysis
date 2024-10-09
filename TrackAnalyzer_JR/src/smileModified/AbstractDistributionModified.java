package smileModified;

public abstract class AbstractDistributionModified implements DistributionModified {
   protected double rejection(double pmax, double xmin, double xmax) {
      double x;
      double y;
      do {
         x = xmin + MathExModified.random() * (xmax - xmin);
         y = MathExModified.random() * pmax;
      } while(this.p(x) < y);

      return x;
   }

   protected double inverseTransformSampling() {
      double u = MathExModified.random();
      return this.quantile(u);
   }

   protected double quantile(double p, double xmin, double xmax, double eps) {
      if (eps <= 0.0D) {
         throw new IllegalArgumentException("Invalid epsilon: " + eps);
      } else {
         while(Math.abs(xmax - xmin) > eps) {
            double xmed = (xmax + xmin) / 2.0D;
            if (this.cdf(xmed) > p) {
               xmax = xmed;
            } else {
               xmin = xmed;
            }
         }

         return xmin;
      }
   }

   protected double quantile(double p, double xmin, double xmax) {
      return this.quantile(p, xmin, xmax, 1.0E-6D);
   }
}
