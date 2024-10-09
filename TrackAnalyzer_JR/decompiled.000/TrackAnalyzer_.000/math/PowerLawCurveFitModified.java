package math;

import ij.measure.CurveFitter;

public class PowerLawCurveFitModified {
   private double alpha;
   private double dc;
   private double goodness;

   public void doFit(double[] xdata, double[] ydata) {
      this.doFit(xdata, ydata, false, 0.0D, 0.0D);
   }

   public void doFit(double[] xdata, double[] ydata, double initalAlpha, double initalDiffCoeff) {
      this.doFit(xdata, ydata, true, initalAlpha, initalDiffCoeff);
   }

   private void doFit(double[] xdata, double[] ydata, boolean useInitialGuess, double initalAlpha, double initalDiffCoeff) {
      CurveFitter fitter = new CurveFitter(xdata, ydata);
      if (useInitialGuess) {
         fitter.setInitialParameters(new double[]{initalDiffCoeff, this.alpha});
      }

      double[] init = null;
      if (useInitialGuess) {
         init = new double[]{initalAlpha, initalDiffCoeff};
      }

      fitter.doFit(16);
      double[] params = fitter.getParams();
      boolean failed = fitter.getStatus() != 0;
      if (failed) {
         this.alpha = -1.0D;
         this.dc = -1.0D;
         this.goodness = 0.0D;
      } else {
         this.alpha = params[1];
         this.dc = params[0] / 4.0D;
         this.goodness = fitter.getFitGoodness();
      }

      if (failed || this.alpha < 0.0D || this.dc < 0.0D) {
         fitter = new CurveFitter(xdata, ydata);

         for(int i = 0; i < ydata.length; ++i) {
            ydata[i] = Math.log(ydata[i]);
         }

         fitter.doCustomFit("y=sqrt(a*a)*log(x)+log(4*sqrt(b*b))", init, false);
         params = fitter.getParams();
         this.alpha = Math.abs(params[0]);
         this.dc = Math.abs(params[1]);
         this.goodness = fitter.getFitGoodness();
      }

   }

   public double getAlpha() {
      return this.alpha;
   }

   public double getDiffusionCoefficient() {
      return this.dc;
   }

   public double getGoodness() {
      return this.goodness;
   }
}
