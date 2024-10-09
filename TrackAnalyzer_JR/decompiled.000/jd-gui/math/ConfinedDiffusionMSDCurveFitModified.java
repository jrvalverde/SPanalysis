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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfinedDiffusionMSDCurveFitModified
/*    */ {
/*    */   private double a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double D;
/*    */   private double goodness;
/* 27 */   private double initA = Double.NaN;
/* 28 */   private double initB = Double.NaN;
/* 29 */   private double initC = Double.NaN;
/* 30 */   private double initD = Double.NaN;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doFit(double[] xdata, double[] ydata, boolean reduced) {
/* 41 */     CurveFitter fitter = new CurveFitter(xdata, ydata);
/* 42 */     if (!reduced) {
/* 43 */       double ia = Double.isNaN(this.initA) ? 0.0D : this.initA;
/* 44 */       double ib = Double.isNaN(this.initB) ? 0.0D : this.initB;
/* 45 */       double ic = Double.isNaN(this.initC) ? 0.0D : this.initC;
/* 46 */       double id = Double.isNaN(this.initD) ? 0.0D : this.initD;
/* 47 */       double[] initialParams = { ia, ib, ic, id };
/* 48 */       fitter.setInitialParameters(initialParams);
/*    */       
/* 50 */       fitter.doCustomFit("y=sqrt(a*a)*(1-sqrt(b*b)*exp(-4*sqrt(c*c)*sqrt(d*d)*x/sqrt(a*a)))", initialParams, false);
/* 51 */       double[] params = fitter.getParams();
/* 52 */       this.a = Math.abs(params[0]);
/* 53 */       this.b = Math.abs(params[1]);
/* 54 */       this.c = Math.abs(params[2]);
/* 55 */       this.D = Math.abs(params[3]);
/* 56 */       this.goodness = fitter.getFitGoodness();
/*    */     } else {
/* 58 */       double ia = Double.isNaN(this.initA) ? 0.0D : this.initA;
/* 59 */       double id = Double.isNaN(this.initD) ? 0.0D : this.initD;
/* 60 */       double[] initialParams = { ia, id };
/* 61 */       fitter.setInitialParameters(initialParams);
/*    */       
/* 63 */       fitter.doCustomFit("y=sqrt(a*a)*(1-exp(-4*sqrt(b*b)*x/sqrt(a*a)))", initialParams, false);
/* 64 */       double[] params = fitter.getParams();
/* 65 */       this.a = Math.abs(params[0]);
/* 66 */       this.D = Math.abs(params[1]);
/* 67 */       this.goodness = fitter.getFitGoodness();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setInitParameters(double[] p) {
/* 72 */     this.initA = p[0];
/* 73 */     this.initB = p[1];
/* 74 */     this.initC = p[2];
/* 75 */     this.initD = p[3];
/*    */   }
/*    */   
/*    */   public double getA() {
/* 79 */     return this.a;
/*    */   }
/*    */   
/*    */   public double getB() {
/* 83 */     return this.b;
/*    */   }
/*    */   
/*    */   public double getC() {
/* 87 */     return this.c;
/*    */   }
/*    */   
/*    */   public double getD() {
/* 91 */     return this.D;
/*    */   }
/*    */   
/*    */   public double getGoodness() {
/* 95 */     return this.goodness;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/math/ConfinedDiffusionMSDCurveFitModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */