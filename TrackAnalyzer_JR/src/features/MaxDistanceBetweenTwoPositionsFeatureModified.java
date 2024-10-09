package features;

import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class MaxDistanceBetweenTwoPositionsFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public MaxDistanceBetweenTwoPositionsFeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      double maxDistance = Double.MIN_VALUE;

      for(int i = 0; i < this.t.size(); ++i) {
         for(int j = i + 1; j < this.t.size(); ++j) {
            double d = ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(j));
            if (d > maxDistance) {
               maxDistance = d;
            }
         }
      }

      this.result = new double[]{maxDistance};
      return this.result;
   }

   public String getName() {
      return "Maximum distance between two positions";
   }

   public String getShortName() {
      return "MAX-DIST-POS";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
