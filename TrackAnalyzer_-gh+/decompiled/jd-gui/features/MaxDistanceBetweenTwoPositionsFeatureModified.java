/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ public class MaxDistanceBetweenTwoPositionsFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public MaxDistanceBetweenTwoPositionsFeatureModified(TrajectoryModified t) {
/* 13 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 22 */     double maxDistance = Double.MIN_VALUE;
/* 23 */     for (int i = 0; i < this.t.size(); i++) {
/* 24 */       for (int j = i + 1; j < this.t.size(); j++) {
/* 25 */         double d = ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(j));
/* 26 */         if (d > maxDistance) {
/* 27 */           maxDistance = d;
/*    */         }
/*    */       } 
/*    */     } 
/* 31 */     this.result = new double[] { maxDistance };
/* 32 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 37 */     return "Maximum distance between two positions";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 42 */     return "MAX-DIST-POS";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 47 */     this.t = t;
/* 48 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/MaxDistanceBetweenTwoPositionsFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */