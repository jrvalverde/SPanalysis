// 
// Decompiled by Procyon v0.5.36
// 

package features;

import traJ.TrajectoryModified;

public class MSDRatioFeatureModified extends AbstractTrajectoryFeatureModified
{
    private int timelag1;
    private int timelag2;
    private TrajectoryModified t;
    
    public MSDRatioFeatureModified(final TrajectoryModified t, final int timelag1, final int timelag2) {
        this.t = t;
        this.timelag1 = timelag1;
        this.timelag2 = timelag2;
    }
    
    @Override
    public double[] evaluate() {
        final MeanSquaredDisplacmentFeatureModified msdf1 = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag1);
        final MeanSquaredDisplacmentFeatureModified msdf2 = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag2);
        final double msd1 = msdf1.evaluate()[0];
        final double msd2 = msdf2.evaluate()[0];
        final double res = msd1 / msd2 - 1.0 * this.timelag1 / this.timelag2;
        return this.result = new double[] { res };
    }
    
    @Override
    public String getName() {
        return "Mean squared displacment ratio";
    }
    
    @Override
    public String getShortName() {
        return "MSDR";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
