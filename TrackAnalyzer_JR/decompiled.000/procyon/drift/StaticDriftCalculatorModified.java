// 
// Decompiled by Procyon v0.5.36
// 

package drift;

import vecmath.Point3dModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import java.util.ArrayList;
import traJ.TrajectoryModified;

public class StaticDriftCalculatorModified<T extends TrajectoryModified>
{
    public double[] calculateDrift(final ArrayList<T> tracks) {
        final double[] result = new double[3];
        double sumX = 0.0;
        double sumY = 0.0;
        double sumZ = 0.0;
        int N = 0;
        for (int i = 0; i < tracks.size(); ++i) {
            final T t = tracks.get(i);
            final TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(t, 1);
            while (it.hasNext()) {
                final int j = it.next();
                sumX += t.get(j + 1).x - t.get(j).x;
                sumY += t.get(j + 1).y - t.get(j).y;
                sumZ += t.get(j + 1).z - t.get(j).z;
                ++N;
            }
        }
        result[0] = sumX / N;
        result[1] = sumY / N;
        result[2] = sumZ / N;
        return result;
    }
}
