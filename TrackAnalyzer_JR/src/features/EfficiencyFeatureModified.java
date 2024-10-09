package features;

import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class EfficiencyFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public EfficiencyFeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      this.result = new double[]{this.getEfficiency()};
      return this.result;
   }

   public double getEfficiency() {
      double sum = 0.0D;

      for(int i = 1; i < this.t.size(); ++i) {
         double d = ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(i - 1));
         sum += d * d;
      }

      if (sum < Math.pow(10.0D, -10.0D)) {
         return 0.0D;
      } else {
         double d = ((Point3dModified)this.t.get(0)).distance((Point3dModified)this.t.get(this.t.size() - 1));
         double eff = d * d / ((double)this.t.size() * sum);
         return eff;
      }
   }

   public String getName() {
      return "Efficiency";
   }

   public String getShortName() {
      return "EFFICENCY";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
