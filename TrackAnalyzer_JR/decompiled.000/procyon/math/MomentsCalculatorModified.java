// 
// Decompiled by Procyon v0.5.36
// 

package math;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import vecmath.Point3dModified;
import vecmath.Vector2d;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import traJ.TrajectoryModified;

public class MomentsCalculatorModified
{
    private TrajectoryModified t;
    
    public MomentsCalculatorModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    public double calculateNthMoment(final int n) {
        final Array2DRowRealMatrix gyr = RadiusGyrationTensor2DModified.getRadiusOfGyrationTensor(this.t);
        final EigenDecomposition eigdec = new EigenDecomposition((RealMatrix)gyr);
        final Vector2d eigv = new Vector2d(eigdec.getEigenvector(0).getEntry(0), eigdec.getEigenvector(0).getEntry(1));
        final double[] projected = new double[this.t.size()];
        for (int i = 0; i < this.t.size(); ++i) {
            final Vector2d pos = new Vector2d(this.t.get(i).x, this.t.get(i).y);
            final double v = eigv.dot(pos);
            projected[i] = v;
        }
        final Mean m = new Mean();
        final StandardDeviation s = new StandardDeviation();
        final double mean = m.evaluate(projected);
        final double sd = s.evaluate(projected);
        double sumPowN = 0.0;
        for (int j = 0; j < projected.length; ++j) {
            sumPowN += Math.pow((projected[j] - mean) / sd, n);
        }
        final double nThMoment = sumPowN / projected.length;
        return nThMoment;
    }
}
