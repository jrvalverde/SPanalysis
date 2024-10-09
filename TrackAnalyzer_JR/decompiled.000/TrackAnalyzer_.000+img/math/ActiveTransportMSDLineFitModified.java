package math;

import ij.measure.CurveFitter;

public class ActiveTransportMSDLineFitModified {
   double a;
   double b;
   double goodness;

   public void doFit(double[] xdata, double[] ydata) {
      new CurveFitter(xdata, ydata);
      CurveFitter fitter = new CurveFitter(xdata, ydata);
      fitter.doCustomFit("y=a*a*x*x + 4*sqrt(b*b)*x", new double[]{0.0D, 0.0D}, false);
      this.a = Math.abs(fitter.getParams()[0]);
      this.b = Math.abs(fitter.getParams()[1]);
      this.goodness = fitter.getFitGoodness();
   }

   public double getVelocity() {
      return this.a;
   }

   public double getDiffusionCoefficient() {
      return this.b;
   }

   public double getFitGoodness() {
      return this.goodness;
   }
}
