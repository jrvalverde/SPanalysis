// 
// Decompiled by Procyon v0.5.36
// 

package features;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import math.RadiusGyrationTensor2DModified;
import traJ.TrajectoryModified;

public class Asymmetry3FeatureModified extends AbstractTrajectoryFeatureModified
{
    private TrajectoryModified t;
    
    public Asymmetry3FeatureModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    @Override
    public double[] evaluate() {
        final Array2DRowRealMatrix gyr = RadiusGyrationTensor2DModified.getRadiusOfGyrationTensor(this.t);
        final EigenDecomposition eigdec = new EigenDecomposition((RealMatrix)gyr);
        final double e1 = eigdec.getRealEigenvalue(0);
        final double e2 = eigdec.getRealEigenvalue(1);
        final double asym = -1.0 * Math.log(1.0 - Math.pow(e1 - e2, 2.0) / (2.0 * Math.pow(e1 + e2, 2.0)));
        return this.result = new double[] { asym };
    }
    
    @Override
    public String getName() {
        return "Assymetry3";
    }
    
    @Override
    public String getShortName() {
        return "ASYM3";
    }
    
    @Override
    public void setTrajectory(final TrajectoryModified t) {
        this.t = t;
        this.result = null;
    }
}
