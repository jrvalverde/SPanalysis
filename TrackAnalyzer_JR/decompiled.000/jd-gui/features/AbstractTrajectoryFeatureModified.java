/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ 
/*    */ public abstract class AbstractTrajectoryFeatureModified {
/*  6 */   protected double[] result = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract double[] evaluate();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getValue() {
/* 17 */     if (this.result == null) {
/* 18 */       this.result = evaluate();
/*    */     }
/* 20 */     return this.result;
/*    */   }
/*    */   
/*    */   public abstract String getName();
/*    */   
/*    */   public abstract String getShortName();
/*    */   
/*    */   public abstract void setTrajectory(TrajectoryModified paramTrajectoryModified);
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/AbstractTrajectoryFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */