package features;

import traJ.TrajectoryModified;

public class MSDRatioFeatureModified extends AbstractTrajectoryFeatureModified {
   private int timelag1;
   private int timelag2;
   private TrajectoryModified t;

   public MSDRatioFeatureModified(TrajectoryModified t, int timelag1, int timelag2) {
      this.t = t;
      this.timelag1 = timelag1;
      this.timelag2 = timelag2;
   }

   public double[] evaluate() {
      MeanSquaredDisplacmentFeatureModified msdf1 = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag1);
      MeanSquaredDisplacmentFeatureModified msdf2 = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag2);
      double msd1 = msdf1.evaluate()[0];
      double msd2 = msdf2.evaluate()[0];
      double res = msd1 / msd2 - 1.0D * (double)this.timelag1 / (double)this.timelag2;
      this.result = new double[]{res};
      return this.result;
   }

   public String getName() {
      return "Mean squared displacment ratio";
   }

   public String getShortName() {
      return "MSDR";
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
