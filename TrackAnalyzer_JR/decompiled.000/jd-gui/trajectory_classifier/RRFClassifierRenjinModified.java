/*     */ package trajectory_classifier;
/*     */ 
/*     */ import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
/*     */ import features.AbstractTrajectoryFeatureModified;
/*     */ import features.Asymmetry3FeatureModified;
/*     */ import features.EfficiencyFeatureModified;
/*     */ import features.FractalDimensionFeatureModified;
/*     */ import features.GaussianityFeautureModified;
/*     */ import features.KurtosisFeatureModified;
/*     */ import features.MSDRatioFeatureModified;
/*     */ import features.PowerLawFeatureModified;
/*     */ import features.StraightnessFeatureModified;
/*     */ import features.TrappedProbabilityFeatureModified;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.script.ScriptEngine;
/*     */ import javax.script.ScriptEngineManager;
/*     */ import javax.script.ScriptException;
/*     */ import org.renjin.eval.EvalException;
/*     */ import org.renjin.parser.ParseException;
/*     */ import org.renjin.sexp.DoubleVector;
/*     */ import org.renjin.sexp.StringVector;
/*     */ import traJ.TrajectoryModified;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RRFClassifierRenjinModified
/*     */   extends AbstractClassifierModified
/*     */ {
/*  36 */   ScriptEngine engine = null;
/*     */   private String pathToModel;
/*     */   private double[] confindence;
/*     */   private double timelag;
/*     */   
/*     */   public RRFClassifierRenjinModified(String pathToModel, double timelag) {
/*  42 */     this.pathToModel = pathToModel;
/*  43 */     this.timelag = timelag;
/*     */   }
/*     */   
/*     */   public void setTimelag(double timelag) {
/*  47 */     this.timelag = timelag;
/*     */   }
/*     */ 
/*     */   
/*     */   public String classify(TrajectoryModified t) {
/*  52 */     ArrayList<TrajectoryModified> tracks = new ArrayList<>();
/*  53 */     tracks.add(t);
/*     */     
/*  55 */     return classify(tracks)[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  63 */     ScriptEngineManager manager = new ScriptEngineManager();
/*     */     
/*  65 */     this.engine = manager.getEngineByName("Renjin");
/*     */     try {
/*  67 */       this.engine.eval("library(randomForest)");
/*  68 */       this.engine.eval("library(plyr)");
/*  69 */       this.engine.eval("load(\"" + this.pathToModel + "\")");
/*  70 */     } catch (ScriptException e) {
/*  71 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*  75 */     if (this.engine == null) {
/*  76 */       throw new RuntimeException("Renjin Script Engine not found on the classpath.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  83 */     this.engine = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] classify(ArrayList<TrajectoryModified> tracks) {
/*  90 */     int N = tracks.size();
/*     */     
/*  92 */     String[] result = new String[N];
/*  93 */     double[] fd = new double[N];
/*  94 */     double[] power = new double[N];
/*  95 */     Arrays.fill(power, -1.0D);
/*  96 */     double[] asym3 = new double[N];
/*  97 */     double[] efficiency = new double[N];
/*  98 */     double[] kurtosis = new double[N];
/*  99 */     double[] msdratio = new double[N];
/* 100 */     double[] straightness = new double[N];
/* 101 */     double[] trappedness = new double[N];
/* 102 */     double[] gaussianity = new double[N];
/* 103 */     double[] pwrDCs = new double[N];
/* 104 */     Arrays.fill(power, -1.0D);
/* 105 */     int cores = Runtime.getRuntime().availableProcessors();
/* 106 */     ExecutorService pool = Executors.newFixedThreadPool(cores);
/*     */     
/* 108 */     for (int i = 0; i < tracks.size(); i++) {
/* 109 */       TrajectoryModified t = tracks.get(i);
/*     */       
/* 111 */       FractalDimensionFeatureModified fdF = new FractalDimensionFeatureModified(t);
/* 112 */       pool.submit(new FeatureWorkerModified(fd, i, (AbstractTrajectoryFeatureModified)fdF, FeatureWorkerModified.EVALTYPE.FIRST));
/* 113 */       double initDC = 0.0D;
/* 114 */       double initAlpha = 0.0D;
/* 115 */       if (i - 1 > 0 && power[i - 1] > 0.0D && pwrDCs[i - 1] > 0.0D) {
/* 116 */         initDC = pwrDCs[i - 1];
/* 117 */         initAlpha = power[i - 1];
/*     */       } else {
/*     */         
/* 120 */         RegressionDiffusionCoefficientEstimatorModified regest = new RegressionDiffusionCoefficientEstimatorModified(
/* 121 */             t, 1.0D / this.timelag, 1, 3);
/* 122 */         initDC = regest.evaluate()[0];
/* 123 */         initAlpha = 0.5D;
/*     */       } 
/*     */       
/* 126 */       PowerLawFeatureModified pwf = new PowerLawFeatureModified(t, 1.0D / this.timelag, 1, t.size() / 3, initAlpha, 
/* 127 */           initDC);
/* 128 */       pool.submit(new FeatureWorkerModified(power, i, (AbstractTrajectoryFeatureModified)pwf, FeatureWorkerModified.EVALTYPE.FIRST));
/* 129 */       pool.submit(new FeatureWorkerModified(pwrDCs, i, (AbstractTrajectoryFeatureModified)pwf, FeatureWorkerModified.EVALTYPE.SECOND));
/*     */       
/* 131 */       Asymmetry3FeatureModified asymf3 = new Asymmetry3FeatureModified(t);
/* 132 */       pool.submit(new FeatureWorkerModified(asym3, i, (AbstractTrajectoryFeatureModified)asymf3, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 134 */       EfficiencyFeatureModified eff = new EfficiencyFeatureModified(t);
/* 135 */       pool.submit(new FeatureWorkerModified(efficiency, i, (AbstractTrajectoryFeatureModified)eff, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 137 */       KurtosisFeatureModified kurtf = new KurtosisFeatureModified(t);
/* 138 */       pool.submit(new FeatureWorkerModified(kurtosis, i, (AbstractTrajectoryFeatureModified)kurtf, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 140 */       MSDRatioFeatureModified msdr = new MSDRatioFeatureModified(t, 1, 5);
/* 141 */       pool.submit(new FeatureWorkerModified(msdratio, i, (AbstractTrajectoryFeatureModified)msdr, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 143 */       StraightnessFeatureModified straight = new StraightnessFeatureModified(t);
/* 144 */       pool.submit(new FeatureWorkerModified(straightness, i, (AbstractTrajectoryFeatureModified)straight, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 146 */       TrappedProbabilityFeatureModified trappf = new TrappedProbabilityFeatureModified(t);
/* 147 */       pool.submit(new FeatureWorkerModified(trappedness, i, (AbstractTrajectoryFeatureModified)trappf, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 149 */       GaussianityFeautureModified gaussf = new GaussianityFeautureModified(t, 1);
/* 150 */       pool.submit(new FeatureWorkerModified(gaussianity, i, (AbstractTrajectoryFeatureModified)gaussf, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */     } 
/*     */     
/* 153 */     pool.shutdown();
/*     */     
/*     */     try {
/* 156 */       pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
/* 157 */     } catch (InterruptedException e) {
/* 158 */       e.printStackTrace();
/*     */     } 
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
/*     */     try {
/* 173 */       this.engine.put("fd", fd);
/* 174 */       this.engine.put("power", power);
/* 175 */       this.engine.put("asymmetry3", asym3);
/* 176 */       this.engine.put("efficiency", efficiency);
/* 177 */       this.engine.put("kurtosis", kurtosis);
/* 178 */       this.engine.put("msdratio", msdratio);
/* 179 */       this.engine.put("straightness", straightness);
/* 180 */       this.engine.put("trappedness", trappedness);
/* 181 */       this.engine.put("gaussianity", gaussianity);
/*     */       
/* 183 */       this.engine.eval("data<-data.frame(FD=fd,POWER=power,MSD.R=msdratio,ASYM3=asymmetry3,EFFICENCY=efficiency, KURT=kurtosis,STRAIGHTNESS=straightness,TRAPPED=trappedness,GAUSS=gaussianity)");
/*     */ 
/*     */ 
/*     */       
/* 187 */       this.engine.eval("features.predict <- predict(model,data,type=\"prob\")");
/* 188 */       this.engine.eval("fprob<-features.predict");
/*     */       
/* 190 */       if (tracks.size() > 1) {
/* 191 */         this.engine.eval("probs <- as.vector(apply(fprob[1:nrow(fprob),],1,max))");
/* 192 */         this.engine.eval("indexmax <- as.vector(apply(fprob[1:nrow(fprob),],1,which.max))");
/*     */       } else {
/* 194 */         this.engine.eval("probs <- max(fprob)");
/* 195 */         this.engine.eval("indexmax <- which.max(fprob)");
/*     */       } 
/* 197 */       this.engine.eval("mynames <- colnames(fprob)");
/* 198 */       this.engine.eval("maxclass <- mynames[indexmax]");
/* 199 */       StringVector res = (StringVector)this.engine.eval("maxclass");
/* 200 */       result = res.toArray();
/* 201 */       DoubleVector confi = (DoubleVector)this.engine.eval("probs");
/* 202 */       this.confindence = confi.toDoubleArray();
/* 203 */     } catch (ParseException e) {
/* 204 */       System.out.println("R script parse error: " + e.getMessage());
/* 205 */     } catch (ScriptException e) {
/*     */       
/* 207 */       e.printStackTrace();
/* 208 */     } catch (EvalException e) {
/* 209 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 212 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getConfindence() {
/* 218 */     return this.confindence;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/trajectory_classifier/RRFClassifierRenjinModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */