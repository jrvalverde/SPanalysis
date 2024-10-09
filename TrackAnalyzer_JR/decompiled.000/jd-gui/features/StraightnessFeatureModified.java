/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ public class StraightnessFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public StraightnessFeatureModified(TrajectoryModified t) {
/* 13 */     this.t = t;
/*    */   }
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 18 */     this.result = new double[] { getStraightness() };
/* 19 */     return this.result;
/*    */   }
/*    */   
/*    */   public double getStraightness() {
/* 23 */     double sum = 0.0D;
/* 24 */     for (int i = 1; i < this.t.size(); i++) {
/* 25 */       sum += ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(i - 1));
/*    */     }
/* 27 */     if (sum < Math.pow(10.0D, -10.0D)) {
/* 28 */       return 0.0D;
/*    */     }
/* 30 */     double straightness = ((Point3dModified)this.t.get(0)).distance((Point3dModified)this.t.get(this.t.size() - 1)) / sum;
/* 31 */     return straightness;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 36 */     return "Straightness";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 42 */     return "STRAIGHTNESS";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 47 */     this.t = t;
/* 48 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/StraightnessFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */