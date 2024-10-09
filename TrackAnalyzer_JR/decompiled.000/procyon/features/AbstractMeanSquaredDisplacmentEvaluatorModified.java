// 
// Decompiled by Procyon v0.5.36
// 

package features;

import traJ.TrajectoryModified;

public interface AbstractMeanSquaredDisplacmentEvaluatorModified
{
    void setTimelag(final int p0);
    
    void setTrajectory(final TrajectoryModified p0);
    
    double[] evaluate();
}
