package features;

import traJ.TrajectoryModified;
import vecmath.Point3dModified;

public class MeanSpeedFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;
   private double timelag;

   public MeanSpeedFeatureModified(TrajectoryModified t, double timelag) {
      this.t = t;
      this.timelag = timelag;
   }

   public double[] evaluate() {
      double sum = 0.0D;

      for(int i = 1; i < this.t.size(); ++i) {
         sum += ((Point3dModified)this.t.get(i - 1)).distance((Point3dModified)this.t.get(i)) / this.timelag;
      }

      double meanspeed = sum / (double)(this.t.size() - 1);
      double netDistance = ((Point3dModified)this.t.get(0)).distance((Point3dModified)this.t.get(this.t.size() - 1));
      double straightLineSpeed = netDistance / ((double)(this.t.size() - 1) * this.timelag);
      this.result = new double[]{meanspeed, straightLineSpeed};
      return this.result;
   }

   public String getName() {
      return "Mean Speed Feature";
   }

   public String getShortName() {
      return "MEANSPEED";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
