package traJ;

import java.util.List;
import vecmath.Point3dModified;

public class TrajectoryUtilModified {
   public static TrajectoryModified resample(TrajectoryModified t, int n) {
      TrajectoryModified t1 = new TrajectoryModified(2);

      for(int i = 0; i < t.size(); i += n) {
         t1.add((Point3dModified)t.get(i));
      }

      return t1;
   }

   public static boolean isZero(double v) {
      return Math.abs(v) < Math.pow(10.0D, -14.0D);
   }

   public static TrajectoryModified getTrajectoryByID(List<? extends TrajectoryModified> t, int id) {
      TrajectoryModified track = null;

      for(int i = 0; i < t.size(); ++i) {
         if (((TrajectoryModified)t.get(i)).getID() == (long)id) {
            track = (TrajectoryModified)t.get(i);
            break;
         }
      }

      return track;
   }
}
