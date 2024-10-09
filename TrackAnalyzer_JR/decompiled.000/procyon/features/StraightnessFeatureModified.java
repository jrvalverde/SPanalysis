// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryModified;

public class StraightnessFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public StraightnessFeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        return this.result = new double[] { this.getStraightness() };
    }
    
    public double getStraightness() {
        double sum = 0.0;
        for (int i = 1; i < this.t.size(); ++i) {
            sum += this.t.get(i).distance(this.t.get(i - 1));
        }
        if (sum < Math.pow(10.0, -10.0)) {
            return 0.0;
        }
        final double straightness = this.t.get(0).distance(this.t.get(this.t.size() - 1)) / sum;
        return straightness;
    }
    
    @Override
    public String getName() {
        return "Straightness";
    }
    
    @Override
    public String getShortName() {
        return "STRAIGHTNESS";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
