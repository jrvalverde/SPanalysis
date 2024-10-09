package math;

import ij.measure.CurveFitter;
import traJ.TrajectoryUtilModified;

public class StraightLineFitModified {
   double a;
   double b;
   double goodness;

   public void doFit(double[] xdata, double[] ydata) {
      CurveFitter fitter = new CurveFitter(xdata, ydata);
      fitter.doFit(0);
      this.goodness = fitter.getFitGoodness();
      this.a = fitter.getParams()[0];
      this.a = TrajectoryUtilModified.isZero(this.a) ? 0.0D : this.a;
      this.b = fitter.getParams()[1];
      this.b = TrajectoryUtilModified.isZero(this.b) ? 0.0D : this.b;
      if (this.b < 0.0D) {
         fitter = new CurveFitter(xdata, ydata);
         fitter.doCustomFit("y=sqrt(a*a)+sqrt(b*b)*x", new double[]{0.0D, 0.0D}, false);
         this.a = Math.abs(fitter.getParams()[0]);
         this.b = Math.abs(fitter.getParams()[1]);
         this.goodness = fitter.getFitGoodness();
      }

   }

   public double getA() {
      return this.a;
   }

   public double getB() {
      return this.b;
   }

   public double getGoodness() {
      return this.goodness;
   }
}
