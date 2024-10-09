/*    */ package features;
/*    */ 
/*    */ import math.MomentsCalculatorModified;
/*    */ import traJ.TrajectoryModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KurtosisFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public KurtosisFeatureModified(TrajectoryModified t) {
/* 14 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 22 */     MomentsCalculatorModified moments = new MomentsCalculatorModified(this.t);
/* 23 */     this.result = new double[] { moments.calculateNthMoment(4) };
/* 24 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return "Kurtosis";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 34 */     return "KURT";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 39 */     this.t = t;
/* 40 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/KurtosisFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */