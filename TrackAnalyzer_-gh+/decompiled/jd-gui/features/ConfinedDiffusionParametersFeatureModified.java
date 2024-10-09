/*     */ package features;
/*     */ 
/*     */ import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
/*     */ import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
/*     */ import java.util.ArrayList;
/*     */ import math.ConfinedDiffusionMSDCurveFitModified;
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
/*     */ 
/*     */ public class ConfinedDiffusionParametersFeatureModified
/*     */   extends AbstractTrajectoryFeatureModified
/*     */ {
/*     */   private TrajectoryModified t;
/*     */   private double timelag;
/*     */   private AbstractDiffusionCoefficientEstimatorModified dcEst;
/*     */   private boolean useReducedModel;
/*     */   
/*     */   public ConfinedDiffusionParametersFeatureModified(TrajectoryModified t, double timelag, boolean useReducedModel) {
/*  41 */     this.t = t;
/*  42 */     this.timelag = timelag;
/*  43 */     this.dcEst = (AbstractDiffusionCoefficientEstimatorModified)new RegressionDiffusionCoefficientEstimatorModified(null, 1.0D / timelag, 1, 2);
/*  44 */     this.useReducedModel = useReducedModel;
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
/*     */   public ConfinedDiffusionParametersFeatureModified(TrajectoryModified t, double timelag, boolean useReducedModel, AbstractDiffusionCoefficientEstimatorModified dcEst) {
/*  56 */     this.t = t;
/*  57 */     this.timelag = timelag;
/*  58 */     this.dcEst = dcEst;
/*  59 */     this.useReducedModel = useReducedModel;
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
/*     */   public double[] evaluate() {
/*  71 */     MeanSquaredDisplacmentFeatureModified msd = new MeanSquaredDisplacmentFeatureModified(this.t, 1);
/*  72 */     msd.setOverlap(false);
/*     */     
/*  74 */     ArrayList<Double> xDataList = new ArrayList<>();
/*  75 */     ArrayList<Double> yDataList = new ArrayList<>();
/*     */     
/*  77 */     for (int i = 1; i < this.t.size() / 3; i++) {
/*  78 */       msd.setTimelag(i);
/*  79 */       double[] res = msd.evaluate();
/*  80 */       double msdvalue = res[0];
/*  81 */       int N = (int)res[2];
/*  82 */       for (int j = 0; j < N; j++) {
/*  83 */         xDataList.add(Double.valueOf(i * this.timelag));
/*  84 */         yDataList.add(Double.valueOf(msdvalue));
/*     */       } 
/*     */     } 
/*  87 */     double[] xData = ArrayUtils.toPrimitive(xDataList.<Double>toArray(new Double[0]));
/*  88 */     double[] yData = ArrayUtils.toPrimitive(yDataList.<Double>toArray(new Double[0]));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     MaxDistanceBetweenTwoPositionsFeatureModified maxdist = new MaxDistanceBetweenTwoPositionsFeatureModified(this.t);
/*  94 */     double estdia = maxdist.evaluate()[0];
/*  95 */     double estDC = this.dcEst.getDiffusionCoefficient(this.t, 1.0D / this.timelag)[0];
/*     */     
/*  97 */     double[] initialParams = { estdia * estdia, 0.0D, 0.0D, estDC };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 103 */     ConfinedDiffusionMSDCurveFitModified cmsdfit = new ConfinedDiffusionMSDCurveFitModified();
/* 104 */     cmsdfit.setInitParameters(initialParams);
/* 105 */     cmsdfit.doFit(xData, yData, this.useReducedModel);
/* 106 */     if (this.useReducedModel) {
/* 107 */       this.result = new double[] { cmsdfit.getA(), cmsdfit.getD(), cmsdfit.getGoodness() };
/*     */     } else {
/* 109 */       this.result = new double[] { cmsdfit.getA(), cmsdfit.getD(), cmsdfit.getB(), cmsdfit.getC(), 
/* 110 */           cmsdfit.getGoodness() };
/*     */     } 
/*     */     
/* 113 */     return this.result;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 118 */     return "Confinement Parameters";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getShortName() {
/* 123 */     return "CONFPARAM";
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTrajectory(TrajectoryModified t) {
/* 128 */     this.t = t;
/* 129 */     this.result = null;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/ConfinedDiffusionParametersFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */