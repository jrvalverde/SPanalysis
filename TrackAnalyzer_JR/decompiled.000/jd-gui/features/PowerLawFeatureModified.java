/*     */ package features;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import math.PowerLawCurveFitModified;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import traJ.TrajectoryModified;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PowerLawFeatureModified
/*     */   extends AbstractTrajectoryFeatureModified
/*     */ {
/*     */   private TrajectoryModified t;
/*     */   private int minlag;
/*     */   private int maxlag;
/*     */   private AbstractMeanSquaredDisplacmentEvaluatorModified msdeval;
/*  23 */   private int evaluateIndex = 0;
/*     */   
/*     */   private boolean useInitialGuess;
/*     */   
/*     */   private double initalDiffusionCoefficient;
/*     */   
/*     */   private double initalAlpha;
/*     */   
/*     */   private double fps;
/*     */   
/*     */   private double timelag;
/*     */   
/*     */   public PowerLawFeatureModified(TrajectoryModified t, double fps, int minlag, int maxlag) {
/*  36 */     this.t = t;
/*  37 */     this.minlag = minlag;
/*  38 */     this.maxlag = maxlag;
/*  39 */     this.fps = fps;
/*  40 */     this.timelag = 1.0D / fps;
/*  41 */     this.msdeval = new MeanSquaredDisplacmentFeatureModified(null, 0);
/*  42 */     ((MeanSquaredDisplacmentFeatureModified)this.msdeval).setOverlap(false);
/*  43 */     this.evaluateIndex = 0;
/*  44 */     this.useInitialGuess = false;
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
/*     */   public PowerLawFeatureModified(TrajectoryModified t, double fps, int minlag, int maxlag, double initalAlpha, double initialDiffusionCoefficient) {
/*  57 */     this.t = t;
/*  58 */     this.minlag = minlag;
/*  59 */     this.maxlag = maxlag;
/*  60 */     this.fps = fps;
/*  61 */     this.timelag = 1.0D / fps;
/*  62 */     this.msdeval = new MeanSquaredDisplacmentFeatureModified(null, 0);
/*  63 */     ((MeanSquaredDisplacmentFeatureModified)this.msdeval).setOverlap(false);
/*  64 */     this.evaluateIndex = 0;
/*  65 */     this.useInitialGuess = true;
/*  66 */     this.initalAlpha = initalAlpha;
/*  67 */     this.initalDiffusionCoefficient = initialDiffusionCoefficient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] evaluate() {
/*  76 */     ArrayList<Double> xDataList = new ArrayList<>();
/*  77 */     ArrayList<Double> yDataList = new ArrayList<>();
/*  78 */     this.msdeval.setTrajectory(this.t);
/*  79 */     double[][] data = new double[this.maxlag - this.minlag + 1][3];
/*     */     int i;
/*  81 */     for (i = this.minlag; i <= this.maxlag; i++) {
/*  82 */       this.msdeval.setTimelag(i);
/*  83 */       data[i - this.minlag][0] = i * this.timelag;
/*  84 */       double[] res = this.msdeval.evaluate();
/*  85 */       data[i - this.minlag][1] = res[this.evaluateIndex];
/*  86 */       data[i - this.minlag][2] = (int)res[2];
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     for (i = 0; i < this.maxlag - this.minlag + 1; i++) {
/*  93 */       double x = data[i][0];
/*  94 */       double y = data[i][1];
/*  95 */       int np = (int)data[i][2];
/*     */       
/*  97 */       for (int j = 0; j < np; j++) {
/*  98 */         xDataList.add(Double.valueOf(x));
/*  99 */         yDataList.add(Double.valueOf(y));
/*     */       } 
/*     */     } 
/*     */     
/* 103 */     double[] xData = ArrayUtils.toPrimitive(xDataList.<Double>toArray(new Double[0]));
/* 104 */     double[] yData = ArrayUtils.toPrimitive(yDataList.<Double>toArray(new Double[0]));
/*     */     
/* 106 */     PowerLawCurveFitModified pwFit = new PowerLawCurveFitModified();
/*     */     
/* 108 */     if (this.useInitialGuess) {
/* 109 */       pwFit.doFit(xData, yData, this.initalAlpha, this.initalDiffusionCoefficient);
/*     */     } else {
/* 111 */       pwFit.doFit(xData, yData);
/*     */     } 
/* 113 */     this.result = new double[] { pwFit.getAlpha(), pwFit.getDiffusionCoefficient(), pwFit.getGoodness() };
/*     */     
/* 115 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEvaluateIndex(int evaluateIndex) {
/* 123 */     this.evaluateIndex = evaluateIndex;
/*     */   }
/*     */   
/*     */   public void setMeanSquaredDisplacmentEvaluator(AbstractMeanSquaredDisplacmentEvaluatorModified msdeval) {
/* 127 */     this.msdeval = msdeval;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 133 */     return "Power-Law-Feature";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrajectory(TrajectoryModified t) {
/* 138 */     this.t = t;
/* 139 */     this.result = null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortName() {
/* 145 */     return "POWER";
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/PowerLawFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */