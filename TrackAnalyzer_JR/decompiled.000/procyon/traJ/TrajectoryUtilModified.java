// 
// Decompiled by Procyon v0.5.36
// 

package traJ;

import java.util.List;
import vecmath.Point3dModified;

public class TrajectoryUtilModified
{
    public static TrajectoryModified resample(final TrajectoryModified t, final int n) {
        final TrajectoryModified t2 = new TrajectoryModified(2);
        for (int i = 0; i < t.size(); i += n) {
            t2.add(t.get(i));
        }
        return t2;
    }
    
    public static boolean isZero(final double v) {
        return Math.abs(v) < Math.pow(10.0, -14.0);
    }
    
    public static TrajectoryModified getTrajectoryByID(final List<? extends TrajectoryModified> t, final int id) {
        TrajectoryModified track = null;
        for (int i = 0; i < t.size(); ++i) {
            if (((TrajectoryModified)t.get(i)).getID() == id) {
                track = (TrajectoryModified)t.get(i);
                break;
            }
        }
        return track;
    }
}
