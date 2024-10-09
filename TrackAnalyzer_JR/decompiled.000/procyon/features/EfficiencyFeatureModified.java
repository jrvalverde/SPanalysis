// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryModified;

public class EfficiencyFeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public EfficiencyFeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        return this.result = new double[] { this.getEfficiency() };
    }
    
    public double getEfficiency() {
        double sum = 0.0;
        for (int i = 1; i < this.t.size(); ++i) {
            final double d = this.t.get(i).distance(this.t.get(i - 1));
            sum += d * d;
        }
        if (sum < Math.pow(10.0, -10.0)) {
            return 0.0;
        }
        final double d2 = this.t.get(0).distance(this.t.get(this.t.size() - 1));
        final double eff = d2 * d2 / (this.t.size() * sum);
        return eff;
    }
    
    @Override
    public String getName() {
        return "Efficiency";
    }
    
    @Override
    public String getShortName() {
        return "EFFICENCY";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
