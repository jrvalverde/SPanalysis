package math;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class RadiusGyrationTensor2DModified {
   private TrajectoryModified t;

   public RadiusGyrationTensor2DModified(TrajectoryModified t) {
      this.t = t;
   }

   public Array2DRowRealMatrix getRadiusOfGyrationTensor() {
      return getRadiusOfGyrationTensor(this.t);
   }

   public static Array2DRowRealMatrix getRadiusOfGyrationTensor(TrajectoryModified t) {
      double meanx = 0.0D;
      double meany = 0.0D;

      for(int i = 0; i < t.size(); ++i) {
         meanx += ((Point3dModified)t.get(i)).x;
         meany += ((Point3dModified)t.get(i)).y;
      }

      meanx /= (double)t.size();
      meany /= (double)t.size();
      double e11 = 0.0D;
      double e12 = 0.0D;
      double e21 = 0.0D;
      double e22 = 0.0D;

      for(int i = 0; i < t.size(); ++i) {
         e11 += Math.pow(((Point3dModified)t.get(i)).x - meanx, 2.0D);
         e12 += (((Point3dModified)t.get(i)).x - meanx) * (((Point3dModified)t.get(i)).y - meany);
         e22 += Math.pow(((Point3dModified)t.get(i)).y - meany, 2.0D);
      }

      e11 /= (double)t.size();
      e12 /= (double)t.size();
      e22 /= (double)t.size();
      int rows = 2;
      int columns = 2;
      Array2DRowRealMatrix gyr = new Array2DRowRealMatrix(rows, columns);
      gyr.addToEntry(0, 0, e11);
      gyr.addToEntry(0, 1, e12);
      gyr.addToEntry(1, 0, e12);
      gyr.addToEntry(1, 1, e22);
      return gyr;
   }
}
