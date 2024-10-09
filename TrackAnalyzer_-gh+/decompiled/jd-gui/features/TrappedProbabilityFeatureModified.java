/*    */ package features;
/*    */ 
/*    */ import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
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
/*    */ public class TrappedProbabilityFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public TrappedProbabilityFeatureModified(TrajectoryModified t) {
/* 27 */     this.t = t;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 32 */     MaxDistanceBetweenTwoPositionsFeatureModified dtwop = new MaxDistanceBetweenTwoPositionsFeatureModified(this.t);
/* 33 */     double r = dtwop.evaluate()[0] / 2.0D;
/*    */ 
/*    */     
/* 36 */     RegressionDiffusionCoefficientEstimatorModified dcEst = new RegressionDiffusionCoefficientEstimatorModified(this.t, 1.0D, 1, 2);
/* 37 */     double D = dcEst.evaluate()[0];
/* 38 */     double time = this.t.size();
/*    */     
/* 40 */     double p = 1.0D - Math.exp(0.2048D - 2.5117D * D * time / r * r);
/* 41 */     this.result = new double[] { p };
/* 42 */     return this.result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 48 */     return "Trapped trajectory probability";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 53 */     return "TRAPPED";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 58 */     this.t = t;
/* 59 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/TrappedProbabilityFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */