package features;

import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class StraightnessFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public StraightnessFeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      this.result = new double[]{this.getStraightness()};
      return this.result;
   }

   public double getStraightness() {
      double sum = 0.0D;

      for(int i = 1; i < this.t.size(); ++i) {
         sum += ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(i - 1));
      }

      if (sum < Math.pow(10.0D, -10.0D)) {
         return 0.0D;
      } else {
         double straightness = ((Point3dModified)this.t.get(0)).distance((Point3dModified)this.t.get(this.t.size() - 1)) / sum;
         return straightness;
      }
   }

   public String getName() {
      return "Straightness";
   }

   public String getShortName() {
      return "STRAIGHTNESS";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
