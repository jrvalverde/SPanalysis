/*    */ package features;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import math.ActiveTransportMSDLineFitModified;
/*    */ import org.apache.commons.lang3.ArrayUtils;
/*    */ import traJ.TrajectoryModified;
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
/*    */ public class ActiveTransportParametersFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   private double timelag;
/*    */   
/*    */   public ActiveTransportParametersFeatureModified(TrajectoryModified t, double timelag) {
/* 30 */     this.t = t;
/* 31 */     this.timelag = timelag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 41 */     MeanSquaredDisplacmentFeatureModified msdevaluator = new MeanSquaredDisplacmentFeatureModified(this.t, 1);
/* 42 */     msdevaluator.setTrajectory(this.t);
/*    */     
/* 44 */     ArrayList<Double> xDataList = new ArrayList<>();
/* 45 */     ArrayList<Double> yDataList = new ArrayList<>();
/* 46 */     for (int i = 1; i < this.t.size() / 3; i++) {
/* 47 */       msdevaluator.setTimelag(i);
/* 48 */       double[] res = msdevaluator.evaluate();
/* 49 */       double msdhelp = res[0];
/* 50 */       int N = (int)res[2];
/* 51 */       for (int j = 0; j < N; j++) {
/* 52 */         xDataList.add(Double.valueOf(i * this.timelag));
/* 53 */         yDataList.add(Double.valueOf(msdhelp));
/*    */       } 
/*    */     } 
/* 56 */     double[] xdata = ArrayUtils.toPrimitive(xDataList.<Double>toArray(new Double[0]));
/* 57 */     double[] ydata = ArrayUtils.toPrimitive(yDataList.<Double>toArray(new Double[0]));
/*    */     
/* 59 */     ActiveTransportMSDLineFitModified afit = new ActiveTransportMSDLineFitModified();
/* 60 */     afit.doFit(xdata, ydata);
/*    */     
/* 62 */     this.result = new double[] { afit.getDiffusionCoefficient(), afit.getVelocity(), afit.getFitGoodness() };
/* 63 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 68 */     return "Active transport parameters";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 73 */     return "ACTPARAM";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 78 */     this.t = t;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/ActiveTransportParametersFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */