package smileModified;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GaussianMixtureModified extends ExponentialFamilyMixtureModified {
   private static final long serialVersionUID = 2L;
   private static final Logger logger = LoggerFactory.getLogger(GaussianMixtureModified.class);

   public GaussianMixtureModified(MixtureModified.Component... components) {
      this(0.0D, 1, components);
   }

   private GaussianMixtureModified(double L, int n, MixtureModified.Component... components) {
      super(L, n, components);
      MixtureModified.Component[] var8 = components;
      int var7 = components.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         MixtureModified.Component component = var8[var6];
         if (!(component.distribution instanceof GaussianDistributionModified)) {
            throw new IllegalArgumentException("Component " + component + " is not of Gaussian distribution.");
         }
      }

   }

   public static GaussianMixtureModified fit(int k, double[] x) {
      if (k < 2) {
         throw new IllegalArgumentException("Invalid number of components in the mixture.");
      } else {
         double min = MathExModified.min(x);
         double max = MathExModified.max(x);
         double step = (max - min) / (double)(k + 1);
         MixtureModified.Component[] components = new MixtureModified.Component[k];

         for(int i = 0; i < k; ++i) {
            components[i] = new MixtureModified.Component(1.0D / (double)k, new GaussianDistributionModified(min += step, step));
         }

         ExponentialFamilyMixtureModified model = fit(x, components);
         return new GaussianMixtureModified(model.L, x.length, model.components);
      }
   }

   public static GaussianMixtureModified fit(double[] x) {
      if (x.length < 20) {
         throw new IllegalArgumentException("Too few samples.");
      } else {
         GaussianMixtureModified mixture = new GaussianMixtureModified(new MixtureModified.Component[]{new MixtureModified.Component(1.0D, GaussianDistributionModified.fit(x))});
         double bic = mixture.bic(x);
         logger.info(String.format("The BIC of %s = %.4f", mixture, bic));

         for(int k = 2; k < x.length / 10; ++k) {
            ExponentialFamilyMixtureModified model = fit(k, x);
            logger.info(String.format("The BIC of %s = %.4f", model, model.bic));
            if (model.bic <= bic) {
               break;
            }

            mixture = new GaussianMixtureModified(model.L, x.length, model.components);
            bic = model.bic;
         }

         return mixture;
      }
   }

   private static MixtureModified.Component[] split(MixtureModified.Component... components) {
      int k = components.length;
      int index = -1;
      double maxSigma = Double.NEGATIVE_INFINITY;

      for(int i = 0; i < k; ++i) {
         MixtureModified.Component c = components[i];
         if (c.distribution.sd() > maxSigma) {
            maxSigma = c.distribution.sd();
            index = i;
         }
      }

      MixtureModified.Component component = components[index];
      double priori = component.priori / 2.0D;
      double delta = component.distribution.sd();
      double mu = component.distribution.mean();
      MixtureModified.Component[] mixture = new MixtureModified.Component[k + 1];
      System.arraycopy(components, 0, mixture, 0, k);
      mixture[index] = new MixtureModified.Component(priori, new GaussianDistributionModified(mu + delta / 2.0D, delta));
      mixture[k] = new MixtureModified.Component(priori, new GaussianDistributionModified(mu - delta / 2.0D, delta));
      return mixture;
   }
}
