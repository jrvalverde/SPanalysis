package math;

import ij.measure.CurveFitter;

public class ConfinedDiffusionMSDCurveFitModified {
   private double a;
   private double b;
   private double c;
   private double D;
   private double goodness;
   private double initA = Double.NaN;
   private double initB = Double.NaN;
   private double initC = Double.NaN;
   private double initD = Double.NaN;

   public void doFit(double[] xdata, double[] ydata, boolean reduced) {
      CurveFitter fitter = new CurveFitter(xdata, ydata);
      double ia;
      double ib;
      if (!reduced) {
         ia = Double.isNaN(this.initA) ? 0.0D : this.initA;
         ib = Double.isNaN(this.initB) ? 0.0D : this.initB;
         double ic = Double.isNaN(this.initC) ? 0.0D : this.initC;
         double id = Double.isNaN(this.initD) ? 0.0D : this.initD;
         double[] initialParams = new double[]{ia, ib, ic, id};
         fitter.setInitialParameters(initialParams);
         fitter.doCustomFit("y=sqrt(a*a)*(1-sqrt(b*b)*exp(-4*sqrt(c*c)*sqrt(d*d)*x/sqrt(a*a)))", initialParams, false);
         double[] params = fitter.getParams();
         this.a = Math.abs(params[0]);
         this.b = Math.abs(params[1]);
         this.c = Math.abs(params[2]);
         this.D = Math.abs(params[3]);
         this.goodness = fitter.getFitGoodness();
      } else {
         ia = Double.isNaN(this.initA) ? 0.0D : this.initA;
         ib = Double.isNaN(this.initD) ? 0.0D : this.initD;
         double[] initialParams = new double[]{ia, ib};
         fitter.setInitialParameters(initialParams);
         fitter.doCustomFit("y=sqrt(a*a)*(1-exp(-4*sqrt(b*b)*x/sqrt(a*a)))", initialParams, false);
         double[] params = fitter.getParams();
         this.a = Math.abs(params[0]);
         this.D = Math.abs(params[1]);
         this.goodness = fitter.getFitGoodness();
      }

   }

   public void setInitParameters(double[] p) {
      this.initA = p[0];
      this.initB = p[1];
      this.initC = p[2];
      this.initD = p[3];
   }

   public double getA() {
      return this.a;
   }

   public double getB() {
      return this.b;
   }

   public double getC() {
      return this.c;
   }

   public double getD() {
      return this.D;
   }

   public double getGoodness() {
      return this.goodness;
   }
}
