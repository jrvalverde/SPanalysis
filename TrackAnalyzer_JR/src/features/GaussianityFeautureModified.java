package features;

import traJ.TrajectoryModified;

public class GaussianityFeautureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;
   private int timelag;
   private String name = "Gaussianity";
   private String sname = "GAUSS";

   public GaussianityFeautureModified(TrajectoryModified t, int timelag) {
      this.t = t;
      this.timelag = timelag;
   }

   public double[] evaluate() {
      MeanSquaredDisplacmentFeatureModified msdf = new MeanSquaredDisplacmentFeatureModified(this.t, this.timelag);
      QuartricMomentFeatureModified qart = new QuartricMomentFeatureModified(this.t, this.timelag);
      double msd = msdf.evaluate()[0];
      double q = qart.evaluate()[0];
      double res = 2.0D * q / (3.0D * msd * msd) - 1.0D;
      this.result = new double[]{res};
      return this.result;
   }

   public String getName() {
      return this.name;
   }

   public String getShortName() {
      return this.sname;
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
