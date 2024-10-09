// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryModified;

public class CenterOfGravityFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public CenterOfGravityFeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;
        for (int i = 0; i < this.t.size(); ++i) {
            x += this.t.get(i).x;
            y += this.t.get(i).y;
            z += this.t.get(i).z;
        }
        x /= this.t.size();
        y /= this.t.size();
        z /= this.t.size();
        return this.result = new double[] { x, y, z };
    }
    
    @Override
    public String getName() {
        return "Center of gravity";
    }
    
    @Override
    public String getShortName() {
        return "COG";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
