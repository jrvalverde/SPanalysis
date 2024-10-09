/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import traJ.TrajectoryValidIndexTimelagIteratorModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuartricMomentFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   private int timelag;
/* 14 */   private String shortname = "QMOMENT";
/*    */   
/*    */   public QuartricMomentFeatureModified(TrajectoryModified t, int timelag) {
/* 17 */     this.t = t;
/* 18 */     this.timelag = timelag;
/*    */   }
/*    */   
/*    */   public double[] evaluate() {
/* 22 */     double sum = 0.0D;
/* 23 */     TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(this.t, this.timelag);
/* 24 */     int N = 0;
/* 25 */     while (it.hasNext()) {
/* 26 */       int i = it.next().intValue();
/* 27 */       sum = sum + 
/* 28 */         Math.pow(((Point3dModified)this.t.get(i)).x - ((Point3dModified)this.t.get(i + this.timelag)).x, 4.0D) + 
/* 29 */         Math.pow(((Point3dModified)this.t.get(i)).y - ((Point3dModified)this.t.get(i + this.timelag)).y, 4.0D) + 
/* 30 */         Math.pow(((Point3dModified)this.t.get(i)).z - ((Point3dModified)this.t.get(i + this.timelag)).z, 4.0D);
/* 31 */       N++;
/*    */     } 
/* 33 */     this.result = new double[] { sum / N };
/* 34 */     return this.result;
/*    */   }
/*    */   
/*    */   public void setTimelag(int timelag) {
/* 38 */     this.timelag = timelag;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return "Quartric Moment";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 48 */     return this.shortname;
/*    */   }
/*    */   
/*    */   public void setShortName(String name) {
/* 52 */     this.shortname = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 57 */     this.t = t;
/* 58 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/QuartricMomentFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */