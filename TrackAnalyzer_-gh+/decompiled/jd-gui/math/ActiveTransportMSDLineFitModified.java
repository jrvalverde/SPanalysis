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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActiveTransportMSDLineFitModified
/*    */ {
/*    */   double a;
/*    */   double b;
/*    */   double goodness;
/*    */   
/*    */   public void doFit(double[] xdata, double[] ydata) {
/* 22 */     CurveFitter fitter = new CurveFitter(xdata, ydata);
/*    */     
/* 24 */     fitter = new CurveFitter(xdata, ydata);
/*    */     
/* 26 */     fitter.doCustomFit("y=a*a*x*x + 4*sqrt(b*b)*x", new double[] { 0.0D, 0.0D }, false);
/* 27 */     this.a = Math.abs(fitter.getParams()[0]);
/* 28 */     this.b = Math.abs(fitter.getParams()[1]);
/* 29 */     this.goodness = fitter.getFitGoodness();
/*    */   }
/*    */ 
/*    */   
/*    */   public double getVelocity() {
/* 34 */     return this.a;
/*    */   }
/*    */   
/*    */   public double getDiffusionCoefficient() {
/* 38 */     return this.b;
/*    */   }
/*    */   
/*    */   public double getFitGoodness() {
/* 42 */     return this.goodness;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/math/ActiveTransportMSDLineFitModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */