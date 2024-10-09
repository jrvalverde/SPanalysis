/*     */ package DiffusionCoefficientEstimator;
/*     */ 
/*     */ import features.AbstractMeanSquaredDisplacmentEvaluatorModified;
/*     */ import features.AbstractTrajectoryFeatureModified;
/*     */ import features.MeanSquaredDisplacmentFeatureModified;
/*     */ import java.util.ArrayList;
/*     */ import math.StraightLineFitModified;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import traJ.TrajectoryModified;
/*     */ 
/*     */ 
/*     */ public class RegressionDiffusionCoefficientEstimatorModified
/*     */   extends AbstractTrajectoryFeatureModified
/*     */   implements AbstractDiffusionCoefficientEstimatorModified
/*     */ {
/*     */   private int lagMin;
/*     */   private int lagMax;
/*     */   private AbstractMeanSquaredDisplacmentEvaluatorModified msdevaluator;
/*     */   private TrajectoryModified t;
/*     */   private double fps;
/*     */   
/*     */   public RegressionDiffusionCoefficientEstimatorModified(int lagMin, int lagMax) {
/*  23 */     this.lagMin = lagMin;
/*  24 */     this.lagMax = lagMax;
/*  25 */     this.msdevaluator = (AbstractMeanSquaredDisplacmentEvaluatorModified)new MeanSquaredDisplacmentFeatureModified(null, lagMin);
/*     */   }
/*     */   
/*     */   public RegressionDiffusionCoefficientEstimatorModified(TrajectoryModified t, double fps, int lagMin, int lagMax) {
/*  29 */     this.lagMin = lagMin;
/*  30 */     this.lagMax = lagMax;
/*  31 */     this.msdevaluator = (AbstractMeanSquaredDisplacmentEvaluatorModified)new MeanSquaredDisplacmentFeatureModified(null, lagMin);
/*  32 */     this.t = t;
/*  33 */     this.fps = fps;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getDiffusionCoefficient(TrajectoryModified t, double fps) {
/*  41 */     if (t.size() == 1) {
/*  42 */       return null;
/*     */     }
/*  44 */     ArrayList<Double> xDataList = new ArrayList<>();
/*  45 */     ArrayList<Double> yDataList = new ArrayList<>();
/*  46 */     double msdhelp = 0.0D;
/*  47 */     if (this.lagMin == this.lagMax) {
/*  48 */       xDataList.add(Double.valueOf(0.0D));
/*  49 */       yDataList.add(Double.valueOf(0.0D));
/*     */     } 
/*  51 */     this.msdevaluator.setTrajectory(t);
/*  52 */     this.msdevaluator.setTimelag(this.lagMin);
/*     */     
/*  54 */     for (int i = this.lagMin; i < this.lagMax + 1; i++) {
/*  55 */       this.msdevaluator.setTimelag(i);
/*  56 */       double[] res = this.msdevaluator.evaluate();
/*  57 */       msdhelp = res[0];
/*  58 */       int N = (int)res[2];
/*  59 */       for (int j = 0; j < N; j++) {
/*  60 */         xDataList.add(Double.valueOf(i * 1.0D / fps));
/*  61 */         yDataList.add(Double.valueOf(msdhelp));
/*     */       } 
/*     */     } 
/*  64 */     double[] xdata = ArrayUtils.toPrimitive(xDataList.<Double>toArray(new Double[0]));
/*  65 */     double[] ydata = ArrayUtils.toPrimitive(yDataList.<Double>toArray(new Double[0]));
/*  66 */     StraightLineFitModified fdf = new StraightLineFitModified();
/*  67 */     fdf.doFit(xdata, ydata);
/*     */     
/*  69 */     this.result = new double[] { fdf.getB() / 2.0D * t.getDimension(), fdf.getB() * 2.0D * t.getDimension(), fdf.getA(), fdf.getGoodness() };
/*  70 */     return this.result;
/*     */   }
/*     */   
/*     */   public void setTimelags(int lagMin, int lagMax) {
/*  74 */     this.lagMin = lagMin;
/*  75 */     this.lagMax = lagMax;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMeanSquaredDisplacementEvaluator(AbstractMeanSquaredDisplacmentEvaluatorModified msdeval) {
/*  80 */     this.msdevaluator = msdeval;
/*     */   }
/*     */ 
/*     */   
/*     */   public double[] evaluate() {
/*  85 */     this.result = getDiffusionCoefficient(this.t, this.fps);
/*  86 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  92 */     return "Diffusion coefficient (Regression)";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getShortName() {
/*  98 */     return "DC-REG";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrajectory(TrajectoryModified t) {
/* 103 */     this.t = t;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/DiffusionCoefficientEstimator/RegressionDiffusionCoefficientEstimatorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */