/*    */ package features;
/*    */ 
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
/*    */ public class MSDRatioFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private int timelag1;
/*    */   private int timelag2;
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public MSDRatioFeatureModified(TrajectoryModified t, int timelag1, int timelag2) {
/* 21 */     this.t = t;
/* 22 */     this.timelag1 = timelag1;
/* 23 */     this.timelag2 = timelag2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 30 */     MeanSquaredDisplacmentFeatureModified msdf1 = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag1);
/* 31 */     MeanSquaredDisplacmentFeatureModified msdf2 = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag2);
/*    */     
/* 33 */     double msd1 = msdf1.evaluate()[0];
/* 34 */     double msd2 = msdf2.evaluate()[0];
/* 35 */     double res = msd1 / msd2 - 1.0D * this.timelag1 / this.timelag2;
/* 36 */     this.result = new double[] { res };
/* 37 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 42 */     return "Mean squared displacment ratio";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 47 */     return "MSDR";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 52 */     this.t = t;
/* 53 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/MSDRatioFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */