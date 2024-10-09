// 
// Decompiled by Procyon v0.5.36
// 

package features;

import math.MomentsCalculatorModified;
import traJ.TrajectoryModified;

public class KurtosisFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public KurtosisFeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        final MomentsCalculatorModified moments = new MomentsCalculatorModified(this.t);
        return this.result = new double[] { moments.calculateNthMoment(4) };
    }
    
    @Override
    public String getName() {
        return "Kurtosis";
    }
    
    @Override
    public String getShortName() {
        return "KURT";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
