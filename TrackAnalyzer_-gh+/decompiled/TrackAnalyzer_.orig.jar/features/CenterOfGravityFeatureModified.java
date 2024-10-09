package features;

import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class CenterOfGravityFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public CenterOfGravityFeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      double x = 0.0D;
      double y = 0.0D;
      double z = 0.0D;

      for(int i = 0; i < this.t.size(); ++i) {
         x += ((Point3dModified)this.t.get(i)).x;
         y += ((Point3dModified)this.t.get(i)).y;
         z += ((Point3dModified)this.t.get(i)).z;
      }

      x /= (double)this.t.size();
      y /= (double)this.t.size();
      z /= (double)this.t.size();
      this.result = new double[]{x, y, z};
      return this.result;
   }

   public String getName() {
      return "Center of gravity";
   }

   public String getShortName() {
      return "COG";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
