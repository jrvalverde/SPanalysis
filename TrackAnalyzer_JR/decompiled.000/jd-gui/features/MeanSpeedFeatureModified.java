/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MeanSpeedFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   private double timelag;
/*    */   
/*    */   public MeanSpeedFeatureModified(TrajectoryModified t, double timelag) {
/* 15 */     this.t = t;
/* 16 */     this.timelag = timelag;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 27 */     double sum = 0.0D;
/* 28 */     for (int i = 1; i < this.t.size(); i++) {
/* 29 */       sum += ((Point3dModified)this.t.get(i - 1)).distance((Point3dModified)this.t.get(i)) / this.timelag;
/*    */     }
/*    */     
/* 32 */     double meanspeed = sum / (this.t.size() - 1);
/*    */     
/* 34 */     double netDistance = ((Point3dModified)this.t.get(0)).distance((Point3dModified)this.t.get(this.t.size() - 1));
/* 35 */     double straightLineSpeed = netDistance / (this.t.size() - 1) * this.timelag;
/* 36 */     this.result = new double[] { meanspeed, straightLineSpeed };
/* 37 */     return this.result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return "Mean Speed Feature";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 49 */     return "MEANSPEED";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 54 */     this.t = t;
/* 55 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/MeanSpeedFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */