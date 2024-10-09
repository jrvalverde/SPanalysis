package drift;

import java.util.ArrayList;
import traJ.TrajectoryModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import vecmath.Point3dModified;

public class StaticDriftCalculatorModified<T extends TrajectoryModified> {
   public double[] calculateDrift(ArrayList<T> tracks) {
      double[] result = new double[3];
      double sumX = 0.0D;
      double sumY = 0.0D;
      double sumZ = 0.0D;
      int N = 0;

      for(int i = 0; i < tracks.size(); ++i) {
         T t = (TrajectoryModified)tracks.get(i);

         for(TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(t, 1); it.hasNext(); ++N) {
            int j = it.next();
            sumX += ((Point3dModified)t.get(j + 1)).x - ((Point3dModified)t.get(j)).x;
            sumY += ((Point3dModified)t.get(j + 1)).y - ((Point3dModified)t.get(j)).y;
            sumZ += ((Point3dModified)t.get(j + 1)).z - ((Point3dModified)t.get(j)).z;
         }
      }

      result[0] = sumX / (double)N;
      result[1] = sumY / (double)N;
      result[2] = sumZ / (double)N;
      return result;
   }
}
