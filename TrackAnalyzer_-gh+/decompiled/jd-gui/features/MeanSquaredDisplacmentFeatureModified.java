/*     */ package features;
/*     */ 
/*     */ import traJ.TrajectoryModified;
/*     */ import traJ.TrajectoryValidIndexTimelagIteratorModified;
/*     */ import vecmath.Point3dModified;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeanSquaredDisplacmentFeatureModified
/*     */   extends AbstractTrajectoryFeatureModified
/*     */   implements AbstractMeanSquaredDisplacmentEvaluatorModified
/*     */ {
/*     */   private TrajectoryModified t;
/*     */   private int timelag;
/*     */   private boolean overlap = false;
/*     */   
/*     */   public MeanSquaredDisplacmentFeatureModified(TrajectoryModified t, int timelag) {
/*  22 */     this.t = t;
/*  23 */     this.timelag = timelag;
/*     */   }
/*     */   
/*     */   public void setTimelag(int timelag) {
/*  27 */     this.timelag = timelag;
/*     */   }
/*     */   
/*     */   public void setTrajectory(TrajectoryModified t) {
/*  31 */     this.t = t;
/*  32 */     this.result = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] getMeanSquaredDisplacment(TrajectoryModified t, int timelag) {
/*  48 */     double msd = 0.0D;
/*  49 */     double[] result = new double[3];
/*  50 */     if (t.size() == 1) {
/*  51 */       result[0] = 0.0D;
/*  52 */       result[1] = 0.0D;
/*  53 */       result[2] = 1.0D;
/*  54 */       return result;
/*     */     } 
/*     */     
/*  57 */     if (timelag < 1) {
/*  58 */       throw new IllegalArgumentException("Timelag can not be smaller than 1");
/*     */     }
/*  60 */     TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(t, timelag, this.overlap);
/*  61 */     int N = 0;
/*  62 */     while (it.hasNext()) {
/*  63 */       int i = it.next().intValue();
/*  64 */       msd = msd + 
/*  65 */         Math.pow(((Point3dModified)t.get(i)).x - ((Point3dModified)t.get(i + timelag)).x, 2.0D) + 
/*  66 */         Math.pow(((Point3dModified)t.get(i)).y - ((Point3dModified)t.get(i + timelag)).y, 2.0D) + 
/*  67 */         Math.pow(((Point3dModified)t.get(i)).z - ((Point3dModified)t.get(i + timelag)).z, 2.0D);
/*  68 */       N++;
/*     */     } 
/*     */     
/*  71 */     msd /= N;
/*     */     
/*  73 */     result[0] = msd;
/*  74 */     result[1] = timelag * ((2 * timelag * timelag) + 1.0D) / ((N - timelag) + 1.0D);
/*  75 */     result[2] = N;
/*  76 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] evaluate() {
/*  85 */     return getMeanSquaredDisplacment(this.t, this.timelag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRelativeVariance() {
/*  96 */     return getMeanSquaredDisplacment(this.t, this.timelag)[1];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 102 */     return "Mean squared displacement-dt-" + this.timelag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortName() {
/* 108 */     return "MSD";
/*     */   }
/*     */   
/*     */   public void setOverlap(boolean overlap) {
/* 112 */     this.overlap = overlap;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/MeanSquaredDisplacmentFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */