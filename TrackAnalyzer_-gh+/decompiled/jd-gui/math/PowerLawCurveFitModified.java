/*    */ package math;
/*    */ 
/*    */ import ij.measure.CurveFitter;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PowerLawCurveFitModified
/*    */ {
/*    */   private double alpha;
/*    */   private double dc;
/*    */   private double goodness;
/*    */   
/*    */   public void doFit(double[] xdata, double[] ydata) {
/* 19 */     doFit(xdata, ydata, false, 0.0D, 0.0D);
/*    */   }
/*    */   
/*    */   public void doFit(double[] xdata, double[] ydata, double initalAlpha, double initalDiffCoeff) {
/* 23 */     doFit(xdata, ydata, true, initalAlpha, initalDiffCoeff);
/*    */   }
/*    */   
/*    */   private void doFit(double[] xdata, double[] ydata, boolean useInitialGuess, double initalAlpha, double initalDiffCoeff) {
/* 27 */     CurveFitter fitter = new CurveFitter(xdata, ydata);
/* 28 */     if (useInitialGuess) {
/* 29 */       fitter.setInitialParameters(new double[] { initalDiffCoeff, this.alpha });
/*    */     }
/* 31 */     double[] init = null;
/* 32 */     if (useInitialGuess) {
/* 33 */       init = new double[] { initalAlpha, initalDiffCoeff };
/*    */     }
/* 35 */     fitter.doFit(16);
/* 36 */     double[] params = fitter.getParams();
/* 37 */     boolean failed = (fitter.getStatus() != 0);
/* 38 */     if (failed) {
/* 39 */       this.alpha = -1.0D;
/* 40 */       this.dc = -1.0D;
/* 41 */       this.goodness = 0.0D;
/*    */     } else {
/* 43 */       this.alpha = params[1];
/* 44 */       this.dc = params[0] / 4.0D;
/* 45 */       this.goodness = fitter.getFitGoodness();
/*    */     } 
/* 47 */     if (failed || this.alpha < 0.0D || this.dc < 0.0D) {
/*    */       
/* 49 */       fitter = new CurveFitter(xdata, ydata);
/* 50 */       for (int i = 0; i < ydata.length; i++) {
/* 51 */         ydata[i] = Math.log(ydata[i]);
/*    */       }
/*    */       
/* 54 */       fitter.doCustomFit("y=sqrt(a*a)*log(x)+log(4*sqrt(b*b))", init, false);
/* 55 */       params = fitter.getParams();
/* 56 */       this.alpha = Math.abs(params[0]);
/* 57 */       this.dc = Math.abs(params[1]);
/* 58 */       this.goodness = fitter.getFitGoodness();
/*    */     } 
/*    */   }
/*    */   
/*    */   public double getAlpha() {
/* 63 */     return this.alpha;
/*    */   }
/*    */   
/*    */   public double getDiffusionCoefficient() {
/* 67 */     return this.dc;
/*    */   }
/*    */   
/*    */   public double getGoodness() {
/* 71 */     return this.goodness;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/math/PowerLawCurveFitModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */