/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CenterOfGravityFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public CenterOfGravityFeatureModified(TrajectoryModified t) {
/* 17 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 24 */     double x = 0.0D;
/* 25 */     double y = 0.0D;
/* 26 */     double z = 0.0D;
/*    */     
/* 28 */     for (int i = 0; i < this.t.size(); i++) {
/* 29 */       x += ((Point3dModified)this.t.get(i)).x;
/* 30 */       y += ((Point3dModified)this.t.get(i)).y;
/* 31 */       z += ((Point3dModified)this.t.get(i)).z;
/*    */     } 
/*    */     
/* 34 */     x /= this.t.size();
/* 35 */     y /= this.t.size();
/* 36 */     z /= this.t.size();
/* 37 */     this.result = new double[] { x, y, z };
/* 38 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return "Center of gravity";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 48 */     return "COG";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 53 */     this.t = t;
/* 54 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/CenterOfGravityFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */