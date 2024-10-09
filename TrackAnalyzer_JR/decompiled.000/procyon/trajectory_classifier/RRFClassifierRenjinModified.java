// 
// Decompiled by Procyon v0.5.36
// 

package trajectory_classifier;

import java.util.concurrent.ExecutorService;
import org.renjin.eval.EvalException;
import org.renjin.parser.ParseException;
import org.renjin.sexp.DoubleVector;
import org.renjin.sexp.StringVector;
import java.util.concurrent.TimeUnit;
import features.GaussianityFeautureModified;
import features.TrappedProbabilityFeatureModified;
import features.StraightnessFeatureModified;
import features.MSDRatioFeatureModified;
import features.KurtosisFeatureModified;
import features.EfficiencyFeatureModified;
import features.Asymmetry3FeatureModified;
import features.PowerLawFeatureModified;
import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import features.AbstractTrajectoryFeatureModified;
import features.FractalDimensionFeatureModified;
import java.util.concurrent.Executors;
import java.util.Arrays;
import javax.script.ScriptException;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import traJ.TrajectoryModified;
import javax.script.ScriptEngine;

public class RRFClassifierRenjinModified extends AbstractClassifierModified
{
    ScriptEngine engine;
    private String pathToModel;
    private double[] confindence;
    private double timelag;
    
    public RRFClassifierRenjinModified(final String pathToModel, final double timelag) {
        this.engine = null;
        this.pathToModel = pathToModel;
        this.timelag = timelag;
    }
    
    public void setTimelag(final double timelag) {
        this.timelag = timelag;
    }
    
    @Override
    public String classify(final TrajectoryModified t) {
        final ArrayList<TrajectoryModified> tracks = new ArrayList<TrajectoryModified>();
        tracks.add(t);
        return this.classify(tracks)[0];
    }
    
    @Override
    public void start() {
        final ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("Renjin");
        try {
            this.engine.eval("library(randomForest)");
            this.engine.eval("library(plyr)");
            this.engine.eval("load(\"" + this.pathToModel + "\")");
        }
        catch (ScriptException e) {
            e.printStackTrace();
        }
        if (this.engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }
    }
    
    @Override
    public void stop() {
        this.engine = null;
    }
    
    @Override
    public String[] classify(final ArrayList<TrajectoryModified> tracks) {
        final int N = tracks.size();
        String[] result = new String[N];
        final double[] fd = new double[N];
        final double[] power = new double[N];
        Arrays.fill(power, -1.0);
        final double[] asym3 = new double[N];
        final double[] efficiency = new double[N];
        final double[] kurtosis = new double[N];
        final double[] msdratio = new double[N];
        final double[] straightness = new double[N];
        final double[] trappedness = new double[N];
        final double[] gaussianity = new double[N];
        final double[] pwrDCs = new double[N];
        Arrays.fill(power, -1.0);
        final int cores = Runtime.getRuntime().availableProcessors();
        final ExecutorService pool = Executors.newFixedThreadPool(cores);
        for (int i = 0; i < tracks.size(); ++i) {
            final TrajectoryModified t = tracks.get(i);
            final FractalDimensionFeatureModified fdF = new FractalDimensionFeatureModified(t);
            pool.submit(new FeatureWorkerModified(fd, i, fdF, FeatureWorkerModified.EVALTYPE.FIRST));
            double initDC = 0.0;
            double initAlpha = 0.0;
            if (i - 1 > 0 && power[i - 1] > 0.0 && pwrDCs[i - 1] > 0.0) {
                initDC = pwrDCs[i - 1];
                initAlpha = power[i - 1];
            }
            else {
                final RegressionDiffusionCoefficientEstimatorModified regest = new RegressionDiffusionCoefficientEstimatorModified(t, 1.0 / this.timelag, 1, 3);
                initDC = regest.evaluate()[0];
                initAlpha = 0.5;
            }
            final PowerLawFeatureModified pwf = new PowerLawFeatureModified(t, 1.0 / this.timelag, 1, t.size() / 3, initAlpha, initDC);
            pool.submit(new FeatureWorkerModified(power, i, pwf, FeatureWorkerModified.EVALTYPE.FIRST));
            pool.submit(new FeatureWorkerModified(pwrDCs, i, pwf, FeatureWorkerModified.EVALTYPE.SECOND));
            final Asymmetry3FeatureModified asymf3 = new Asymmetry3FeatureModified(t);
            pool.submit(new FeatureWorkerModified(asym3, i, asymf3, FeatureWorkerModified.EVALTYPE.FIRST));
            final EfficiencyFeatureModified eff = new EfficiencyFeatureModified(t);
            pool.submit(new FeatureWorkerModified(efficiency, i, eff, FeatureWorkerModified.EVALTYPE.FIRST));
            final KurtosisFeatureModified kurtf = new KurtosisFeatureModified(t);
            pool.submit(new FeatureWorkerModified(kurtosis, i, kurtf, FeatureWorkerModified.EVALTYPE.FIRST));
            final MSDRatioFeatureModified msdr = new MSDRatioFeatureModified(t, 1, 5);
            pool.submit(new FeatureWorkerModified(msdratio, i, msdr, FeatureWorkerModified.EVALTYPE.FIRST));
            final StraightnessFeatureModified straight = new StraightnessFeatureModified(t);
            pool.submit(new FeatureWorkerModified(straightness, i, straight, FeatureWorkerModified.EVALTYPE.FIRST));
            final TrappedProbabilityFeatureModified trappf = new TrappedProbabilityFeatureModified(t);
            pool.submit(new FeatureWorkerModified(trappedness, i, trappf, FeatureWorkerModified.EVALTYPE.FIRST));
            final GaussianityFeautureModified gaussf = new GaussianityFeautureModified(t, 1);
            pool.submit(new FeatureWorkerModified(gaussianity, i, gaussf, FeatureWorkerModified.EVALTYPE.FIRST));
        }
        pool.shutdown();
        try {
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
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
            }
            else {
                this.engine.eval("probs <- max(fprob)");
                this.engine.eval("indexmax <- which.max(fprob)");
            }
            this.engine.eval("mynames <- colnames(fprob)");
            this.engine.eval("maxclass <- mynames[indexmax]");
            final StringVector res = (StringVector)this.engine.eval("maxclass");
            result = res.toArray();
            final DoubleVector confi = (DoubleVector)this.engine.eval("probs");
            this.confindence = confi.toDoubleArray();
        }
        catch (ParseException e2) {
            System.out.println("R script parse error: " + e2.getMessage());
        }
        catch (ScriptException e3) {
            e3.printStackTrace();
        }
        catch (EvalException e4) {
            e4.printStackTrace();
        }
        return result;
    }
    
    @Override
    public double[] getConfindence() {
        return this.confindence;
    }
}
