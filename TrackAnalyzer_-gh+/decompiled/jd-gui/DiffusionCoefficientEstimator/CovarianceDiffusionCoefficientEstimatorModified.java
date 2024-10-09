/*     */ package DiffusionCoefficientEstimator;
/*     */ 
/*     */ import features.AbstractTrajectoryFeatureModified;
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
/*     */ public class CovarianceDiffusionCoefficientEstimatorModified
/*     */   extends AbstractTrajectoryFeatureModified
/*     */   implements AbstractDiffusionCoefficientEstimatorModified
/*     */ {
/*     */   private TrajectoryModified t;
/*     */   private double fps;
/*     */   
/*     */   public CovarianceDiffusionCoefficientEstimatorModified() {}
/*     */   
/*     */   public CovarianceDiffusionCoefficientEstimatorModified(TrajectoryModified t, double fps) {
/*  24 */     this.t = t;
/*  25 */     this.fps = fps;
/*     */   }
/*     */ 
/*     */   
/*     */   private double getDistanceProductX(TrajectoryModified t, int n, int m) {
/*  30 */     double xn = ((Point3dModified)t.get(n + 1)).x - ((Point3dModified)t.get(n)).x;
/*  31 */     double xm = ((Point3dModified)t.get(m + 1)).x - ((Point3dModified)t.get(m)).x;
/*     */     
/*  33 */     return xn * xm;
/*     */   }
/*     */   
/*     */   private double getDistanceProductY(TrajectoryModified t, int n, int m) {
/*  37 */     double xn = ((Point3dModified)t.get(n + 1)).y - ((Point3dModified)t.get(n)).y;
/*  38 */     double xm = ((Point3dModified)t.get(m + 1)).y - ((Point3dModified)t.get(m)).y;
/*  39 */     return xn * xm;
/*     */   }
/*     */   
/*     */   private double getDistanceProductZ(TrajectoryModified t, int n, int m) {
/*  43 */     double xn = ((Point3dModified)t.get(n + 1)).z - ((Point3dModified)t.get(n)).z;
/*  44 */     double xm = ((Point3dModified)t.get(m + 1)).z - ((Point3dModified)t.get(m)).z;
/*  45 */     return xn * xm;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDiffusionCoefficient(TrajectoryModified t, double fps) {
/*  54 */     double[] cov = getCovData(t, fps, 0.0D);
/*  55 */     return cov;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double[] getCovData(TrajectoryModified track, double fps, double R) {
/*  61 */     double sumX = 0.0D;
/*  62 */     double sumX2 = 0.0D;
/*  63 */     double sumY = 0.0D;
/*  64 */     double sumY2 = 0.0D;
/*  65 */     double sumZ = 0.0D;
/*  66 */     double sumZ2 = 0.0D;
/*  67 */     int N = 0;
/*  68 */     int M = 0;
/*  69 */     TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(track, 1);
/*  70 */     while (it.hasNext()) {
/*  71 */       int i = it.next().intValue();
/*  72 */       sumX += getDistanceProductX(track, i, i);
/*  73 */       sumY += getDistanceProductY(track, i, i);
/*  74 */       sumZ += getDistanceProductZ(track, i, i);
/*  75 */       N++;
/*  76 */       if (i + 2 < track.size() && track.get(i + 2) != null) {
/*  77 */         sumX2 += getDistanceProductX(track, i, i + 1);
/*  78 */         sumY2 += getDistanceProductY(track, i, i + 1);
/*  79 */         sumZ2 += getDistanceProductZ(track, i, i + 1);
/*  80 */         M++;
/*     */       } 
/*     */     } 
/*     */     
/*  84 */     double msdX = sumX / N;
/*     */     
/*  86 */     double msdY = sumY / N;
/*  87 */     double msdZ = sumZ / N;
/*     */     
/*  89 */     double covX = sumX2 / M;
/*  90 */     double covY = sumY2 / M;
/*  91 */     double covZ = sumZ2 / M;
/*     */     
/*  93 */     double termXA = msdX / 2.0D * fps;
/*  94 */     double termXB = covX * fps;
/*  95 */     double termYA = msdY / 2.0D * fps;
/*  96 */     double termYB = covY * fps;
/*  97 */     double termZA = msdZ / 2.0D * fps;
/*  98 */     double termZB = covZ * fps;
/*     */     
/* 100 */     double DX = termXA + termXB;
/* 101 */     double DY = termYA + termYB;
/* 102 */     double DZ = termZA + termZB;
/*     */     
/* 104 */     double D = (DX + DY + DZ) / track.getDimension();
/*     */ 
/*     */     
/* 107 */     double[] data = new double[4];
/* 108 */     data[0] = D;
/* 109 */     data[1] = Math.sqrt(Math.abs(covX));
/* 110 */     data[2] = Math.sqrt(Math.abs(covY));
/* 111 */     data[3] = Math.sqrt(Math.abs(covZ));
/*     */     
/* 113 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] evaluate() {
/* 118 */     this.result = getDiffusionCoefficient(this.t, this.fps);
/* 119 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 125 */     return "Diffusion coefficient (Covariance)";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortName() {
/* 131 */     return "DC-COV";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrajectory(TrajectoryModified t) {
/* 136 */     this.t = t;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/DiffusionCoefficientEstimator/CovarianceDiffusionCoefficientEstimatorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */