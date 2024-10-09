// 
// Decompiled by Procyon v0.5.36
// 

package features;

import math.ConfinedDiffusionMSDCurveFitModified;
import org.apache.commons.lang3.ArrayUtils;
import java.util.ArrayList;
import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import DiffusionCoefficientEstimator.AbstractDiffusionCoefficientEstimatorModified;
import traJ.TrajectoryModified;

public class ConfinedDiffusionParametersFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    private double timelag;
    private AbstractDiffusionCoefficientEstimatorModified dcEst;
    private boolean useReducedModel;
    
    public ConfinedDiffusionParametersFeatureModified(final TrajectoryModified t, final double timelag, final boolean useReducedModel) {
        this.t = t;
        this.timelag = timelag;
        this.dcEst = new RegressionDiffusionCoefficientEstimatorModified(null, 1.0 / timelag, 1, 2);
        this.useReducedModel = useReducedModel;
    }
    
    public ConfinedDiffusionParametersFeatureModified(final TrajectoryModified t, final double timelag, final boolean useReducedModel, final AbstractDiffusionCoefficientEstimatorModified dcEst) {
        this.t = t;
        this.timelag = timelag;
        this.dcEst = dcEst;
        this.useReducedModel = useReducedModel;
    }
    
    @Override
    public double[] evaluate() {
        final MeanSquaredDisplacmentFeatureModified msd = new MeanSquaredDisplacmentFeatureModified(this.t, 1);
        msd.setOverlap(false);
        final ArrayList<Double> xDataList = new ArrayList<Double>();
        final ArrayList<Double> yDataList = new ArrayList<Double>();
        for (int i = 1; i < this.t.size() / 3; ++i) {
            msd.setTimelag(i);
            final double[] res = msd.evaluate();
            final double msdvalue = res[0];
            for (int N = (int)res[2], j = 0; j < N; ++j) {
                xDataList.add(i * this.timelag);
                yDataList.add(msdvalue);
            }
        }
        final double[] xData = ArrayUtils.toPrimitive((Double[])xDataList.<Double>toArray(new Double[0]));
        final double[] yData = ArrayUtils.toPrimitive((Double[])yDataList.<Double>toArray(new Double[0]));
        final MaxDistanceBetweenTwoPositionsFeatureModified maxdist = new MaxDistanceBetweenTwoPositionsFeatureModified(this.t);
        final double estdia = maxdist.evaluate()[0];
        final double estDC = this.dcEst.getDiffusionCoefficient(this.t, 1.0 / this.timelag)[0];
        final double[] initialParams = { estdia * estdia, 0.0, 0.0, estDC };
        final ConfinedDiffusionMSDCurveFitModified cmsdfit = new ConfinedDiffusionMSDCurveFitModified();
        cmsdfit.setInitParameters(initialParams);
        cmsdfit.doFit(xData, yData, this.useReducedModel);
        if (this.useReducedModel) {
            this.result = new double[] { cmsdfit.getA(), cmsdfit.getD(), cmsdfit.getGoodness() };
        }
        else {
            this.result = new double[] { cmsdfit.getA(), cmsdfit.getD(), cmsdfit.getB(), cmsdfit.getC(), cmsdfit.getGoodness() };
        }
        return this.result;
    }
    
    @Override
    public String getName() {
        return "Confinement Parameters";
    }
    
    @Override
    public String getShortName() {
        return "CONFPARAM";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
