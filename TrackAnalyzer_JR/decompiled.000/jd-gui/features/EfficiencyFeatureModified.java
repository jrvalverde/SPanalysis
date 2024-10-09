/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ public class EfficiencyFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public EfficiencyFeatureModified(TrajectoryModified t) {
/* 12 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 20 */     this.result = new double[] { getEfficiency() };
/* 21 */     return this.result;
/*    */   }
/*    */   
/*    */   public double getEfficiency() {
/* 25 */     double sum = 0.0D;
/* 26 */     for (int i = 1; i < this.t.size(); i++) {
/* 27 */       double d1 = ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(i - 1));
/* 28 */       sum += d1 * d1;
/*    */     } 
/* 30 */     if (sum < Math.pow(10.0D, -10.0D)) {
/* 31 */       return 0.0D;
/*    */     }
/* 33 */     double d = ((Point3dModified)this.t.get(0)).distance((Point3dModified)this.t.get(this.t.size() - 1));
/* 34 */     double eff = d * d / this.t.size() * sum;
/* 35 */     return eff;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 40 */     return "Efficiency";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 46 */     return "EFFICENCY";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 51 */     this.t = t;
/* 52 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/EfficiencyFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */