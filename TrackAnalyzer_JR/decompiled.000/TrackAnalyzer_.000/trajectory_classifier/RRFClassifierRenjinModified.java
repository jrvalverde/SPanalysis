package trajectory_classifier;

import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import features.Asymmetry3FeatureModified;
import features.EfficiencyFeatureModified;
import features.FractalDimensionFeatureModified;
import features.GaussianityFeautureModified;
import features.KurtosisFeatureModified;
import features.MSDRatioFeatureModified;
import features.PowerLawFeatureModified;
import features.StraightnessFeatureModified;
import features.TrappedProbabilityFeatureModified;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.renjin.eval.EvalException;
import org.renjin.parser.ParseException;
import org.renjin.sexp.DoubleVector;
import org.renjin.sexp.StringVector;
import traJ.TrajectoryModified;

public class RRFClassifierRenjinModified extends AbstractClassifierModified {
   ScriptEngine engine = null;
   private String pathToModel;
   private double[] confindence;
   private double timelag;

   public RRFClassifierRenjinModified(String pathToModel, double timelag) {
      this.pathToModel = pathToModel;
      this.timelag = timelag;
   }

   public void setTimelag(double timelag) {
      this.timelag = timelag;
   }

   public String classify(TrajectoryModified t) {
      ArrayList<TrajectoryModified> tracks = new ArrayList();
      tracks.add(t);
      return this.classify(tracks)[0];
   }

   public void start() {
      ScriptEngineManager manager = new ScriptEngineManager();
      this.engine = manager.getEngineByName("Renjin");

      try {
         this.engine.eval("library(randomForest)");
         this.engine.eval("library(plyr)");
         this.engine.eval("load(\"" + this.pathToModel + "\")");
      } catch (ScriptException var3) {
         var3.printStackTrace();
      }

      if (this.engine == null) {
         throw new RuntimeException("Renjin Script Engine not found on the classpath.");
      }
   }

   public void stop() {
      this.engine = null;
   }

   public String[] classify(ArrayList<TrajectoryModified> tracks) {
      int N = tracks.size();
      String[] result = new String[N];
      double[] fd = new double[N];
      double[] power = new double[N];
      Arrays.fill(power, -1.0D);
      double[] asym3 = new double[N];
      double[] efficiency = new double[N];
      double[] kurtosis = new double[N];
      double[] msdratio = new double[N];
      double[] straightness = new double[N];
      double[] trappedness = new double[N];
      double[] gaussianity = new double[N];
      double[] pwrDCs = new double[N];
      Arrays.fill(power, -1.0D);
      int cores = Runtime.getRuntime().availableProcessors();
      ExecutorService pool = Executors.newFixedThreadPool(cores);

      for(int i = 0; i < tracks.size(); ++i) {
         TrajectoryModified t = (TrajectoryModified)tracks.get(i);
         FractalDimensionFeatureModified fdF = new FractalDimensionFeatureModified(t);
         pool.submit(new FeatureWorkerModified(fd, i, fdF, FeatureWorkerModified.EVALTYPE.FIRST));
         double initDC = 0.0D;
         double initAlpha = 0.0D;
         if (i - 1 > 0 && power[i - 1] > 0.0D && pwrDCs[i - 1] > 0.0D) {
            initDC = pwrDCs[i - 1];
            initAlpha = power[i - 1];
         } else {
            RegressionDiffusionCoefficientEstimatorModified regest = new RegressionDiffusionCoefficientEstimatorModified(t, 1.0D / this.timelag, 1, 3);
            initDC = regest.evaluate()[0];
            initAlpha = 0.5D;
         }

         PowerLawFeatureModified pwf = new PowerLawFeatureModified(t, 1.0D / this.timelag, 1, t.size() / 3, initAlpha, initDC);
         pool.submit(new FeatureWorkerModified(power, i, pwf, FeatureWorkerModified.EVALTYPE.FIRST));
         pool.submit(new FeatureWorkerModified(pwrDCs, i, pwf, FeatureWorkerModified.EVALTYPE.SECOND));
         Asymmetry3FeatureModified asymf3 = new Asymmetry3FeatureModified(t);
         pool.submit(new FeatureWorkerModified(asym3, i, asymf3, FeatureWorkerModified.EVALTYPE.FIRST));
         EfficiencyFeatureModified eff = new EfficiencyFeatureModified(t);
         pool.submit(new FeatureWorkerModified(efficiency, i, eff, FeatureWorkerModified.EVALTYPE.FIRST));
         KurtosisFeatureModified kurtf = new KurtosisFeatureModified(t);
         pool.submit(new FeatureWorkerModified(kurtosis, i, kurtf, FeatureWorkerModified.EVALTYPE.FIRST));
         MSDRatioFeatureModified msdr = new MSDRatioFeatureModified(t, 1, 5);
         pool.submit(new FeatureWorkerModified(msdratio, i, msdr, FeatureWorkerModified.EVALTYPE.FIRST));
         StraightnessFeatureModified straight = new StraightnessFeatureModified(t);
         pool.submit(new FeatureWorkerModified(straightness, i, straight, FeatureWorkerModified.EVALTYPE.FIRST));
         TrappedProbabilityFeatureModified trappf = new TrappedProbabilityFeatureModified(t);
         pool.submit(new FeatureWorkerModified(trappedness, i, trappf, FeatureWorkerModified.EVALTYPE.FIRST));
         GaussianityFeautureModified gaussf = new GaussianityFeautureModified(t, 1);
         pool.submit(new FeatureWorkerModified(gaussianity, i, gaussf, FeatureWorkerModified.EVALTYPE.FIRST));
      }

      pool.shutdown();

      try {
         pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
      } catch (InterruptedException var34) {
         var34.printStackTrace();
      }

      try {
         this.engine.put("fd", fd);
         this.engine.put("power", power);
         this.engine.put("asymmetry3", asym3);
         this.engine.put("efficiency", efficiency);
         this.engine.put("kurtosis", kurtosis);
         this.engine.put("msdratio", msdratio);
         this.engine.put("straightness", straightness);
         this.engine.put("trappedness", trappedness);
         this.engine.put("gaussianity", gaussianity);
         this.engine.eval("data<-data.frame(FD=fd,POWER=power,MSD.R=msdratio,ASYM3=asymmetry3,EFFICENCY=efficiency, KURT=kurtosis,STRAIGHTNESS=straightness,TRAPPED=trappedness,GAUSS=gaussianity)");
         this.engine.eval("features.predict <- predict(model,data,type=\"prob\")");
         this.engine.eval("fprob<-features.predict");
         if (tracks.size() > 1) {
            this.engine.eval("probs <- as.vector(apply(fprob[1:nrow(fprob),],1,max))");
            this.engine.eval("indexmax <- as.vector(apply(fprob[1:nrow(fprob),],1,which.max))");
         } else {
            this.engine.eval("probs <- max(fprob)");
            this.engine.eval("indexmax <- which.max(fprob)");
         }

         this.engine.eval("mynames <- colnames(fprob)");
         this.engine.eval("maxclass <- mynames[indexmax]");
         StringVector res = (StringVector)this.engine.eval("maxclass");
         result = res.toArray();
         DoubleVector confi = (DoubleVector)this.engine.eval("probs");
         this.confindence = confi.toDoubleArray();
      } catch (ParseException var31) {
         System.out.println("R script parse error: " + var31.getMessage());
      } catch (ScriptException var32) {
         var32.printStackTrace();
      } catch (EvalException var33) {
         var33.printStackTrace();
      }

      return result;
   }

   public double[] getConfindence() {
      return this.confindence;
   }
}
