// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryModified;

public class MaxDistanceBetweenTwoPositionsFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public MaxDistanceBetweenTwoPositionsFeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        double maxDistance = Double.MIN_VALUE;
        for (int i = 0; i < this.t.size(); ++i) {
            for (int j = i + 1; j < this.t.size(); ++j) {
                final double d = this.t.get(i).distance(this.t.get(j));
                if (d > maxDistance) {
                    maxDistance = d;
                }
            }
        }
        return this.result = new double[] { maxDistance };
    }
    
    @Override
    public String getName() {
        return "Maximum distance between two positions";
    }
    
    @Override
    public String getShortName() {
        return "MAX-DIST-POS";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
