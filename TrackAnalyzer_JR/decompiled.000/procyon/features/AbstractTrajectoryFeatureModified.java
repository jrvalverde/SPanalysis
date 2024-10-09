// 
// Decompiled by Procyon v0.5.36
// 

package features;

import traJ.TrajectoryModified;

public abstract class AbstractTrajectoryFeatureModified
{
    protected double[] result;
    
    public AbstractTrajectoryFeatureModified() {
        this.result = null;
    }
    
    public abstract double[] evaluate();
    
    public double[] getValue() {
        if (this.result == null) {
            this.result = this.evaluate();
        }
        return this.result;
    }
    
    public abstract String getName();
    
    public abstract String getShortName();
    
    public abstract void setTrajectory(final TrajectoryModified p0);
}
