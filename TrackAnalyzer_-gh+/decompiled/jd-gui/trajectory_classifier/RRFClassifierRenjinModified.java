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
/*     */ public class RRFClassifierRenjinModified
/*     */   extends AbstractClassifierModified
/*     */ {
/*  35 */   ScriptEngine engine = null;
/*     */   private String pathToModel;
/*     */   private double[] confindence;
/*     */   private double timelag;
/*     */   
/*     */   public RRFClassifierRenjinModified(String pathToModel, double timelag) {
/*  41 */     this.pathToModel = pathToModel;
/*  42 */     this.timelag = timelag;
/*     */   }
/*     */   
/*     */   public void setTimelag(double timelag) {
/*  46 */     this.timelag = timelag;
/*     */   }
/*     */ 
/*     */   
/*     */   public String classify(TrajectoryModified t) {
/*  51 */     ArrayList<TrajectoryModified> tracks = new ArrayList<>();
/*  52 */     tracks.add(t);
/*     */     
/*  54 */     return classify(tracks)[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  62 */     ScriptEngineManager manager = new ScriptEngineManager();
/*     */     
/*  64 */     this.engine = manager.getEngineByName("Renjin");
/*     */     try {
/*  66 */       this.engine.eval("library(randomForest)");
/*  67 */       this.engine.eval("library(plyr)");
/*  68 */       this.engine.eval("load(\"" + this.pathToModel + "\")");
/*  69 */     } catch (ScriptException e) {
/*  70 */       e.printStackTrace();
/*     */     } 
/*     */ 
/*     */     
/*  74 */     if (this.engine == null) {
/*  75 */       throw new RuntimeException("Renjin Script Engine not found on the classpath.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/*  82 */     this.engine = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] classify(ArrayList<TrajectoryModified> tracks) {
/*  89 */     int N = tracks.size();
/*     */     
/*  91 */     String[] result = new String[N];
/*  92 */     double[] fd = new double[N];
/*  93 */     double[] power = new double[N];
/*  94 */     Arrays.fill(power, -1.0D);
/*  95 */     double[] asym3 = new double[N];
/*  96 */     double[] efficiency = new double[N];
/*  97 */     double[] kurtosis = new double[N];
/*  98 */     double[] msdratio = new double[N];
/*  99 */     double[] straightness = new double[N];
/* 100 */     double[] trappedness = new double[N];
/* 101 */     double[] gaussianity = new double[N];
/* 102 */     double[] pwrDCs = new double[N];
/* 103 */     Arrays.fill(power, -1.0D);
/* 104 */     int cores = Runtime.getRuntime().availableProcessors();
/* 105 */     ExecutorService pool = Executors.newFixedThreadPool(cores);
/*     */     
/* 107 */     for (int i = 0; i < tracks.size(); i++) {
/* 108 */       TrajectoryModified t = tracks.get(i);
/*     */       
/* 110 */       FractalDimensionFeatureModified fdF = new FractalDimensionFeatureModified(t);
/* 111 */       pool.submit(new FeatureWorkerModified(fd, i, (AbstractTrajectoryFeatureModified)fdF, FeatureWorkerModified.EVALTYPE.FIRST));
/* 112 */       double initDC = 0.0D;
/* 113 */       double initAlpha = 0.0D;
/* 114 */       if (i - 1 > 0 && power[i - 1] > 0.0D && pwrDCs[i - 1] > 0.0D) {
/* 115 */         initDC = pwrDCs[i - 1];
/* 116 */         initAlpha = power[i - 1];
/*     */       } else {
/*     */         
/* 119 */         RegressionDiffusionCoefficientEstimatorModified regest = new RegressionDiffusionCoefficientEstimatorModified(
/* 120 */             t, 1.0D / this.timelag, 1, 3);
/* 121 */         initDC = regest.evaluate()[0];
/* 122 */         initAlpha = 0.5D;
/*     */       } 
/*     */       
/* 125 */       PowerLawFeatureModified pwf = new PowerLawFeatureModified(t, 1.0D / this.timelag, 1, t.size() / 3, initAlpha, 
/* 126 */           initDC);
/* 127 */       pool.submit(new FeatureWorkerModified(power, i, (AbstractTrajectoryFeatureModified)pwf, FeatureWorkerModified.EVALTYPE.FIRST));
/* 128 */       pool.submit(new FeatureWorkerModified(pwrDCs, i, (AbstractTrajectoryFeatureModified)pwf, FeatureWorkerModified.EVALTYPE.SECOND));
/*     */       
/* 130 */       Asymmetry3FeatureModified asymf3 = new Asymmetry3FeatureModified(t);
/* 131 */       pool.submit(new FeatureWorkerModified(asym3, i, (AbstractTrajectoryFeatureModified)asymf3, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 133 */       EfficiencyFeatureModified eff = new EfficiencyFeatureModified(t);
/* 134 */       pool.submit(new FeatureWorkerModified(efficiency, i, (AbstractTrajectoryFeatureModified)eff, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 136 */       KurtosisFeatureModified kurtf = new KurtosisFeatureModified(t);
/* 137 */       pool.submit(new FeatureWorkerModified(kurtosis, i, (AbstractTrajectoryFeatureModified)kurtf, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 139 */       MSDRatioFeatureModified msdr = new MSDRatioFeatureModified(t, 1, 5);
/* 140 */       pool.submit(new FeatureWorkerModified(msdratio, i, (AbstractTrajectoryFeatureModified)msdr, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 142 */       StraightnessFeatureModified straight = new StraightnessFeatureModified(t);
/* 143 */       pool.submit(new FeatureWorkerModified(straightness, i, (AbstractTrajectoryFeatureModified)straight, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 145 */       TrappedProbabilityFeatureModified trappf = new TrappedProbabilityFeatureModified(t);
/* 146 */       pool.submit(new FeatureWorkerModified(trappedness, i, (AbstractTrajectoryFeatureModified)trappf, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */       
/* 148 */       GaussianityFeautureModified gaussf = new GaussianityFeautureModified(t, 1);
/* 149 */       pool.submit(new FeatureWorkerModified(gaussianity, i, (AbstractTrajectoryFeatureModified)gaussf, FeatureWorkerModified.EVALTYPE.FIRST));
/*     */     } 
/*     */     
/* 152 */     pool.shutdown();
/*     */     
/*     */     try {
/* 155 */       pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
/* 156 */     } catch (InterruptedException e) {
/* 157 */       e.printStackTrace();
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
/* 172 */       this.engine.put("fd", fd);
/* 173 */       this.engine.put("power", power);
/* 174 */       this.engine.put("asymmetry3", asym3);
/* 175 */       this.engine.put("efficiency", efficiency);
/* 176 */       this.engine.put("kurtosis", kurtosis);
/* 177 */       this.engine.put("msdratio", msdratio);
/* 178 */       this.engine.put("straightness", straightness);
/* 179 */       this.engine.put("trappedness", trappedness);
/* 180 */       this.engine.put("gaussianity", gaussianity);
/*     */       
/* 182 */       this.engine.eval("data<-data.frame(FD=fd,POWER=power,MSD.R=msdratio,ASYM3=asymmetry3,EFFICENCY=efficiency, KURT=kurtosis,STRAIGHTNESS=straightness,TRAPPED=trappedness,GAUSS=gaussianity)");
/*     */ 
/*     */ 
/*     */       
/* 186 */       this.engine.eval("features.predict <- predict(model,data,type=\"prob\")");
/* 187 */       this.engine.eval("fprob<-features.predict");
/*     */       
/* 189 */       if (tracks.size() > 1) {
/* 190 */         this.engine.eval("probs <- as.vector(apply(fprob[1:nrow(fprob),],1,max))");
/* 191 */         this.engine.eval("indexmax <- as.vector(apply(fprob[1:nrow(fprob),],1,which.max))");
/*     */       } else {
/* 193 */         this.engine.eval("probs <- max(fprob)");
/* 194 */         this.engine.eval("indexmax <- which.max(fprob)");
/*     */       } 
/* 196 */       this.engine.eval("mynames <- colnames(fprob)");
/* 197 */       this.engine.eval("maxclass <- mynames[indexmax]");
/* 198 */       StringVector res = (StringVector)this.engine.eval("maxclass");
/* 199 */       result = res.toArray();
/* 200 */       DoubleVector confi = (DoubleVector)this.engine.eval("probs");
/* 201 */       this.confindence = confi.toDoubleArray();
/* 202 */     } catch (ParseException e) {
/* 203 */       System.out.println("R script parse error: " + e.getMessage());
/* 204 */     } catch (ScriptException e) {
/*     */       
/* 206 */       e.printStackTrace();
/* 207 */     } catch (EvalException e) {
/* 208 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 211 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double[] getConfindence() {
/* 217 */     return this.confindence;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/trajectory_classifier/RRFClassifierRenjinModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */