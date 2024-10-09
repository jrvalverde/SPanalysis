/*    */ package math;
/*    */ 
/*    */ import ij.measure.CurveFitter;
/*    */ import traJ.TrajectoryUtilModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StraightLineFitModified
/*    */ {
/*    */   double a;
/*    */   double b;
/*    */   double goodness;
/*    */   
/*    */   public void doFit(double[] xdata, double[] ydata) {
/* 17 */     CurveFitter fitter = new CurveFitter(xdata, ydata);
/* 18 */     fitter.doFit(0);
/* 19 */     this.goodness = fitter.getFitGoodness();
/* 20 */     this.a = fitter.getParams()[0];
/* 21 */     this.a = TrajectoryUtilModified.isZero(this.a) ? 0.0D : this.a;
/* 22 */     this.b = fitter.getParams()[1];
/* 23 */     this.b = TrajectoryUtilModified.isZero(this.b) ? 0.0D : this.b;
/* 24 */     if (this.b < 0.0D) {
/* 25 */       fitter = new CurveFitter(xdata, ydata);
/*    */       
/* 27 */       fitter.doCustomFit("y=sqrt(a*a)+sqrt(b*b)*x", new double[] { 0.0D, 0.0D }, false);
/* 28 */       this.a = Math.abs(fitter.getParams()[0]);
/* 29 */       this.b = Math.abs(fitter.getParams()[1]);
/* 30 */       this.goodness = fitter.getFitGoodness();
/*    */     } 
/*    */   }
/*    */   
/*    */   public double getA() {
/* 35 */     return this.a;
/*    */   }
/*    */   
/*    */   public double getB() {
/* 39 */     return this.b;
/*    */   }
/*    */   
/*    */   public double getGoodness() {
/* 43 */     return this.goodness;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/math/StraightLineFitModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */