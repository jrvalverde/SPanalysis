// 
// Decompiled by Procyon v0.5.36
// 

package features;

import vecmath.Point3dModified;
import traJ.TrajectoryModified;

public class FractalDimensionFeatureModified extends AbstractTrajectoryFeatureModified
{
    TrajectoryModified t;
    
    public FractalDimensionFeatureModified(final TrajectoryModified t) {
        this.t = t;
        if (t.getDimension() != 2) {
            throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys");
        }
    }
    
    @Override
    public double[] evaluate() {
        double largestDistance = Double.MIN_VALUE;
        double totalLength = 0.0;
        for (int i = 0; i < this.t.size(); ++i) {
            for (int j = i + 1; j < this.t.size(); ++j) {
                final double d = this.t.get(i).distance(this.t.get(j));
                if (d > largestDistance) {
                    largestDistance = d;
                }
            }
            if (i > 0) {
                totalLength += this.t.get(i).distance(this.t.get(i - 1));
            }
        }
        final double n = this.t.size() - 1;
        final double fractalDImension = Math.log(n) / (Math.log(n) + Math.log(largestDistance / totalLength));
        return this.result = new double[] { fractalDImension };
    }
    
    @Override
    public String getName() {
        return "Fractal Dimension";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
        if (t.getDimension() != 2) {
            throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys");
        }
    }
    
    @Override
    public String getShortName() {
        return "FD";
    }
}
