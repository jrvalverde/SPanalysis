// 
// Decompiled by Procyon v0.5.36
// 

package features;

import traJ.TrajectoryModified;

public class GaussianityFeautureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    private int timelag;
    private String name;
    private String sname;
    
    public GaussianityFeautureModified(final TrajectoryModified t, final int timelag) {
        this.name = "Gaussianity";
        this.sname = "GAUSS";
        this.t = t;
        this.timelag = timelag;
    }
    
    @Override
    public double[] evaluate() {
        final MeanSquaredDisplacmentFeatureModified msdf = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag);
        final QuartricMomentFeatureModified qart = new QuartricMomentFeatureModified(this.t, this.timelag);
        final double msd = msdf.evaluate()[0];
        final double q = qart.evaluate()[0];
        final double res = 2.0 * q / (3.0 * msd * msd) - 1.0;
        return this.result = new double[] { res };
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String getShortName() {
        return this.sname;
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
