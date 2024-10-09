// 
// Decompiled by Procyon v0.5.36
// 

package features;

import math.PowerLawCurveFitModified;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import traJ.TrajectoryModified;

public class PowerLawFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    private int minlag;
    private int maxlag;
    private AbstractMeanSquaredDisplacmentEvaluatorModified msdeval;
    private int evaluateIndex;
    private boolean useInitialGuess;
    private double initalDiffusionCoefficient;
    private double initalAlpha;
    private double fps;
    private double timelag;
    
    public PowerLawFeatureModified(final TrajectoryModified t, final double fps, final int minlag, final int maxlag) {
        this.evaluateIndex = 0;
        this.t = t;
        this.minlag = minlag;
        this.maxlag = maxlag;
        this.fps = fps;
        this.timelag = 1.0 / fps;
        this.msdeval = new MeanSquaredDisplacmentFeatureModified(null, 0);
        ((MeanSquaredDisplacmentFeatureModified)this.msdeval).setOverlap(false);
        this.evaluateIndex = 0;
        this.useInitialGuess = false;
    }
    
    public PowerLawFeatureModified(final TrajectoryModified t, final double fps, final int minlag, final int maxlag, final double initalAlpha, final double initialDiffusionCoefficient) {
        this.evaluateIndex = 0;
        this.t = t;
        this.minlag = minlag;
        this.maxlag = maxlag;
        this.fps = fps;
        this.timelag = 1.0 / fps;
        this.msdeval = new MeanSquaredDisplacmentFeatureModified(null, 0);
        ((MeanSquaredDisplacmentFeatureModified)this.msdeval).setOverlap(false);
        this.evaluateIndex = 0;
        this.useInitialGuess = true;
        this.initalAlpha = initalAlpha;
        this.initalDiffusionCoefficient = initialDiffusionCoefficient;
    }
    
    @Override
    public double[] evaluate() {
        final ArrayList<Double> xDataList = new ArrayList<Double>();
        final ArrayList<Double> yDataList = new ArrayList<Double>();
        this.msdeval.setTrajectory(this.t);
        final double[][] data = new double[this.maxlag - this.minlag + 1][3];
        for (int i = this.minlag; i <= this.maxlag; ++i) {
            this.msdeval.setTimelag(i);
            data[i - this.minlag][0] = i * this.timelag;
            final double[] res = this.msdeval.evaluate();
            data[i - this.minlag][1] = res[this.evaluateIndex];
            data[i - this.minlag][2] = (int)res[2];
        }
        for (int i = 0; i < this.maxlag - this.minlag + 1; ++i) {
            final double x = data[i][0];
            final double y = data[i][1];
            for (int np = (int)data[i][2], j = 0; j < np; ++j) {
                xDataList.add(x);
                yDataList.add(y);
            }
        }
        final double[] xData = ArrayUtils.toPrimitive((Double[])xDataList.<Double>toArray(new Double[0]));
        final double[] yData = ArrayUtils.toPrimitive((Double[])yDataList.<Double>toArray(new Double[0]));
        final PowerLawCurveFitModified pwFit = new PowerLawCurveFitModified();
        if (this.useInitialGuess) {
            pwFit.doFit(xData, yData, this.initalAlpha, this.initalDiffusionCoefficient);
        }
        else {
            pwFit.doFit(xData, yData);
        }
        return this.result = new double[] { pwFit.getAlpha(), pwFit.getDiffusionCoefficient(), pwFit.getGoodness() };
    }
    
    public void setEvaluateIndex(final int evaluateIndex) {
        this.evaluateIndex = evaluateIndex;
    }
    
    public void setMeanSquaredDisplacmentEvaluator(final AbstractMeanSquaredDisplacmentEvaluatorModified msdeval) {
        this.msdeval = msdeval;
    }
    
    @Override
    public String getName() {
        return "Power-Law-Feature";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
    
    @Override
    public String getShortName() {
        return "POWER";
    }
}
