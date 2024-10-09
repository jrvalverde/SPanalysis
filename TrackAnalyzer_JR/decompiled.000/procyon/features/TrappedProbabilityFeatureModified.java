// 
// Decompiled by Procyon v0.5.36
// 

package features;

import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import traJ.TrajectoryModified;

public class TrappedProbabilityFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public TrappedProbabilityFeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        final MaxDistanceBetweenTwoPositionsFeatureModified dtwop = new MaxDistanceBetweenTwoPositionsFeatureModified(this.t);
        final double r = dtwop.evaluate()[0] / 2.0;
        final RegressionDiffusionCoefficientEstimatorModified dcEst = new RegressionDiffusionCoefficientEstimatorModified(this.t, 1.0, 1, 2);
        final double D = dcEst.evaluate()[0];
        final double time = this.t.size();
        final double p = 1.0 - Math.exp(0.2048 - 2.5117 * (D * time / (r * r)));
        return this.result = new double[] { p };
    }
    
    @Override
    public String getName() {
        return "Trapped trajectory probability";
    }
    
    @Override
    public String getShortName() {
        return "TRAPPED";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
