/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ 
/*    */ public class GaussianityFeautureModified extends AbstractTrajectoryFeatureModified {
/*    */   private TrajectoryModified t;
/*    */   private int timelag;
/*  8 */   private String name = "Gaussianity";
/*  9 */   private String sname = "GAUSS";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GaussianityFeautureModified(TrajectoryModified t, int timelag) {
/* 16 */     this.t = t;
/* 17 */     this.timelag = timelag;
/*    */   }
/*    */   
/*    */   public double[] evaluate() {
/* 21 */     MeanSquaredDisplacmentFeatureModified msdf = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag);
/* 22 */     QuartricMomentFeatureModified qart = new QuartricMomentFeatureModified(this.t, this.timelag);
/*    */     
/* 24 */     double msd = msdf.evaluate()[0];
/* 25 */     double q = qart.evaluate()[0];
/*    */     
/* 27 */     double res = 2.0D * q / 3.0D * msd * msd - 1.0D;
/* 28 */     this.result = new double[] { res };
/* 29 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 34 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 39 */     return this.sname;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 44 */     this.t = t;
/* 45 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/GaussianityFeautureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */