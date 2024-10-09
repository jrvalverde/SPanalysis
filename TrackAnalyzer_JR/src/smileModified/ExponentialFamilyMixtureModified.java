package smileModified;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExponentialFamilyMixtureModified extends MixtureModified {
   private static final long serialVersionUID = 2L;
   private static final Logger logger = LoggerFactory.getLogger(ExponentialFamilyMixtureModified.class);
   public final double L;
   public final double bic;

   public ExponentialFamilyMixtureModified(MixtureModified.Component... components) {
      this(0.0D, 1, components);
   }

   ExponentialFamilyMixtureModified(double L, int n, MixtureModified.Component... components) {
      super(components);
      MixtureModified.Component[] var8 = components;
      int var7 = components.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         MixtureModified.Component component = var8[var6];
         if (!(component.distribution instanceof ExponentialFamilyModified)) {
            throw new IllegalArgumentException("Component " + component + " is not of exponential family.");
         }
      }

      this.L = L;
      this.bic = L - 0.5D * (double)this.length() * Math.log((double)n);
   }

   public static ExponentialFamilyMixtureModified fit(double[] x, MixtureModified.Component... components) {
      return fit(x, components, 0.0D, 500, 1.0E-4D);
   }

   public static ExponentialFamilyMixtureModified fit(double[] x, MixtureModified.Component[] components, double gamma, int maxIter, double tol) {
      if (x.length < components.length / 2) {
         throw new IllegalArgumentException("Too many components");
      } else if (!(gamma < 0.0D) && !(gamma > 0.2D)) {
         int n = x.length;
         int k = components.length;
         double[][] posteriori = new double[k][n];
         double L = 0.0D;
         double diff = Double.MAX_VALUE;

         for(int iter = 1; iter <= maxIter && diff > tol; ++iter) {
            int j;
            int i;
            for(j = 0; j < k; ++j) {
               MixtureModified.Component c = components[j];

               for(i = 0; i < n; ++i) {
                  posteriori[j][i] = c.priori * c.distribution.p(x[i]);
               }
            }

            for(j = 0; j < n; ++j) {
               double p = 0.0D;

               int i;
               for(i = 0; i < k; ++i) {
                  p += posteriori[i][j];
               }

               for(i = 0; i < k; ++i) {
                  posteriori[i][j] /= p;
               }

               if (gamma > 0.0D) {
                  for(i = 0; i < k; ++i) {
                     posteriori[i][j] *= 1.0D + gamma * MathExModified.log2(posteriori[i][j]);
                     if (Double.isNaN(posteriori[i][j]) || posteriori[i][j] < 0.0D) {
                        posteriori[i][j] = 0.0D;
                     }
                  }
               }
            }

            double Z = 0.0D;

            for(i = 0; i < k; ++i) {
               components[i] = ((ExponentialFamilyModified)components[i].distribution).M(x, posteriori[i]);
               Z += components[i].priori;
            }

            for(i = 0; i < k; ++i) {
               components[i] = new MixtureModified.Component(components[i].priori / Z, components[i].distribution);
            }

            double loglikelihood = 0.0D;
            double[] var23 = x;
            int var22 = x.length;

            for(int var21 = 0; var21 < var22; ++var21) {
               double xi = var23[var21];
               double p = 0.0D;
               MixtureModified.Component[] var29 = components;
               int var28 = components.length;

               for(int var27 = 0; var27 < var28; ++var27) {
                  MixtureModified.Component c = var29[var27];
                  p += c.priori * c.distribution.p(xi);
               }

               if (p > 0.0D) {
                  loglikelihood += Math.log(p);
               }
            }

            diff = loglikelihood - L;
            L = loglikelihood;
            if (iter % 10 == 0) {
               logger.info(String.format("The log-likelihood after %d iterations: %.4f", iter, loglikelihood));
            }
         }

         return new ExponentialFamilyMixtureModified(L, x.length, components);
      } else {
         throw new IllegalArgumentException("Invalid regularization factor gamma.");
      }
   }
}
