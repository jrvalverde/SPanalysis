package features;

import math.MomentsCalculatorModified;
import traJ.TrajectoryModified;

public class KurtosisFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public KurtosisFeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      MomentsCalculatorModified moments = new MomentsCalculatorModified(this.t);
      this.result = new double[]{moments.calculateNthMoment(4)};
      return this.result;
   }

   public String getName() {
      return "Kurtosis";
   }

   public String getShortName() {
      return "KURT";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
