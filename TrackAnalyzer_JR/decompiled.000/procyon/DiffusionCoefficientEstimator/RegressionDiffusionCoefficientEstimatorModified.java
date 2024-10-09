// 
// Decompiled by Procyon v0.5.36
// 

package DiffusionCoefficientEstimator;

import math.StraightLineFitModified;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import features.MeanSquaredDisplacmentFeatureModified;
import traJ.TrajectoryModified;
import features.AbstractMeanSquaredDisplacmentEvaluatorModified;
import features.AbstractTrajectoryFeatureModified;

public class RegressionDiffusionCoefficientEstimatorModified extends AbstractTrajectoryFeatureModified implements AbstractDiffusionCoefficientEstimatorModified
{
    private int lagMin;
    private int lagMax;
    private AbstractMeanSquaredDisplacmentEvaluatorModified msdevaluator;
    private TrajectoryModified t;
    private double fps;
    
    public RegressionDiffusionCoefficientEstimatorModified(final int lagMin, final int lagMax) {
        this.lagMin = lagMin;
        this.lagMax = lagMax;
        this.msdevaluator = new MeanSquaredDisplacmentFeatureModified(null, lagMin);
    }
    
    public RegressionDiffusionCoefficientEstimatorModified(final TrajectoryModified t, final double fps, final int lagMin, final int lagMax) {
        this.lagMin = lagMin;
        this.lagMax = lagMax;
        this.msdevaluator = new MeanSquaredDisplacmentFeatureModified(null, lagMin);
        this.t = t;
        this.fps = fps;
    }
    
    @Override
    public double[] getDiffusionCoefficient(final TrajectoryModified t, final double fps) {
        if (t.size() == 1) {
            return null;
        }
        final ArrayList<Double> xDataList = new ArrayList<Double>();
        final ArrayList<Double> yDataList = new ArrayList<Double>();
        double msdhelp = 0.0;
        if (this.lagMin == this.lagMax) {
            xDataList.add(0.0);
            yDataList.add(0.0);
        }
        this.msdevaluator.setTrajectory(t);
        this.msdevaluator.setTimelag(this.lagMin);
        for (int i = this.lagMin; i < this.lagMax + 1; ++i) {
            this.msdevaluator.setTimelag(i);
            final double[] res = this.msdevaluator.evaluate();
            msdhelp = res[0];
            for (int N = (int)res[2], j = 0; j < N; ++j) {
                xDataList.add(i * 1.0 / fps);
                yDataList.add(msdhelp);
            }
        }
        final double[] xdata = ArrayUtils.toPrimitive((Double[])xDataList.<Double>toArray(new Double[0]));
        final double[] ydata = ArrayUtils.toPrimitive((Double[])yDataList.<Double>toArray(new Double[0]));
        final StraightLineFitModified fdf = new StraightLineFitModified();
        fdf.doFit(xdata, ydata);
        return this.result = new double[] { fdf.getB() / (2.0 * t.getDimension()), fdf.getB() * 2.0 * t.getDimension(), fdf.getA(), fdf.getGoodness() };
    }
    
    public void setTimelags(final int lagMin, final int lagMax) {
        this.lagMin = lagMin;
        this.lagMax = lagMax;
    }
    
    public void setMeanSquaredDisplacementEvaluator(final AbstractMeanSquaredDisplacmentEvaluatorModified msdeval) {
        this.msdevaluator = msdeval;
    }
    
    @Override
    public double[] evaluate() {
        return this.result = this.getDiffusionCoefficient(this.t, this.fps);
    }
    
    @Override
    public String getName() {
        return "Diffusion coefficient (Regression)";
    }
    
    @Override
    public String getShortName() {
        return "DC-REG";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
    }
}
