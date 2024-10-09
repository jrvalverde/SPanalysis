// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryModified;

public class MeanSpeedFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    private double timelag;
    
    public MeanSpeedFeatureModified(final TrajectoryModified t, final double timelag) {
        this.t = t;
        this.timelag = timelag;
    }
    
    @Override
    public double[] evaluate() {
        double sum = 0.0;
        for (int i = 1; i < this.t.size(); ++i) {
            sum += this.t.get(i - 1).distance(this.t.get(i)) / this.timelag;
        }
        final double meanspeed = sum / (this.t.size() - 1);
        final double netDistance = this.t.get(0).distance(this.t.get(this.t.size() - 1));
        final double straightLineSpeed = netDistance / ((this.t.size() - 1) * this.timelag);
        return this.result = new double[] { meanspeed, straightLineSpeed };
    }
    
    @Override
    public String getName() {
        return "Mean Speed Feature";
    }
    
    @Override
    public String getShortName() {
        return "MEANSPEED";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
