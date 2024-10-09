package features;

import DiffusionCoefficientEstimator.RegressionDiffusionCoefficientEstimatorModified;
import traJ.TrajectoryModified;

public class TrappedProbabilityFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;

   public TrappedProbabilityFeatureModified(TrajectoryModified t) {
      this.t = t;
   }

   public double[] evaluate() {
      MaxDistanceBetweenTwoPositionsFeatureModified dtwop = new MaxDistanceBetweenTwoPositionsFeatureModified(this.t);
      double r = dtwop.evaluate()[0] / 2.0D;
      RegressionDiffusionCoefficientEstimatorModified dcEst = new RegressionDiffusionCoefficientEstimatorModified(this.t, 1.0D, 1, 2);
      double D = dcEst.evaluate()[0];
      double time = (double)this.t.size();
      double p = 1.0D - Math.exp(0.2048D - 2.5117D * (D * time / (r * r)));
      this.result = new double[]{p};
      return this.result;
   }

   public String getName() {
      return "Trapped trajectory probability";
   }

   public String getShortName() {
      return "TRAPPED";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
