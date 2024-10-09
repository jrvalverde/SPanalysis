// 
// Decompiled by Procyon v0.5.36
// 

package math;

import vecmath.Point3dModified;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import traJ.TrajectoryModified;

public class RadiusGyrationTensor2DModified
{
    private TrajectoryModified t;
    
    public RadiusGyrationTensor2DModified(final TrajectoryModified t) {
        this.t = t;
    }
    
    public Array2DRowRealMatrix getRadiusOfGyrationTensor() {
        return getRadiusOfGyrationTensor(this.t);
    }
    
    public static Array2DRowRealMatrix getRadiusOfGyrationTensor(final TrajectoryModified t) {
        double meanx = 0.0;
        double meany = 0.0;
        for (int i = 0; i < t.size(); ++i) {
            meanx += t.get(i).x;
            meany += t.get(i).y;
        }
        meanx /= t.size();
        meany /= t.size();
        double e11 = 0.0;
        double e12 = 0.0;
        double e13 = 0.0;
        double e14 = 0.0;
        for (int j = 0; j < t.size(); ++j) {
            e11 += Math.pow(t.get(j).x - meanx, 2.0);
            e12 += (t.get(j).x - meanx) * (t.get(j).y - meany);
            e14 += Math.pow(t.get(j).y - meany, 2.0);
        }
        e11 /= t.size();
        e12 = (e13 = e12 / t.size());
        e14 /= t.size();
        final int rows = 2;
        final int columns = 2;
        final Array2DRowRealMatrix gyr = new Array2DRowRealMatrix(rows, columns);
        gyr.addToEntry(0, 0, e11);
        gyr.addToEntry(0, 1, e12);
        gyr.addToEntry(1, 0, e13);
        gyr.addToEntry(1, 1, e14);
        return gyr;
    }
}
