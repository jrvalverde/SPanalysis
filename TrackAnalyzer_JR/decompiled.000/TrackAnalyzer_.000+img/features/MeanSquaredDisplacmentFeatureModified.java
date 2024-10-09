package features;

import traJ.TrajectoryModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import vecmath.Point3dModified;

public class MeanSquaredDisplacmentFeatureModified extends AbstractTrajectoryFeatureModified implements AbstractMeanSquaredDisplacmentEvaluatorModified {
   private TrajectoryModified t;
   private int timelag;
   private boolean overlap = false;

   public MeanSquaredDisplacmentFeatureModified(TrajectoryModified t, int timelag) {
      this.t = t;
      this.timelag = timelag;
   }

   public void setTimelag(int timelag) {
      this.timelag = timelag;
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }

   private double[] getMeanSquaredDisplacment(TrajectoryModified t, int timelag) {
      double msd = 0.0D;
      double[] result = new double[3];
      if (t.size() == 1) {
         result[0] = 0.0D;
         result[1] = 0.0D;
         result[2] = 1.0D;
         return result;
      } else if (timelag < 1) {
         throw new IllegalArgumentException("Timelag can not be smaller than 1");
      } else {
         TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(t, timelag, this.overlap);

         int N;
         for(N = 0; it.hasNext(); ++N) {
            int i = it.next();
            msd = msd + Math.pow(((Point3dModified)t.get(i)).x - ((Point3dModified)t.get(i + timelag)).x, 2.0D) + Math.pow(((Point3dModified)t.get(i)).y - ((Point3dModified)t.get(i + timelag)).y, 2.0D) + Math.pow(((Point3dModified)t.get(i)).z - ((Point3dModified)t.get(i + timelag)).z, 2.0D);
         }

         msd /= (double)N;
         result[0] = msd;
         result[1] = (double)timelag * ((double)(2 * timelag * timelag) + 1.0D) / ((double)(N - timelag) + 1.0D);
         result[2] = (double)N;
         return result;
      }
   }

   public double[] evaluate() {
      return this.getMeanSquaredDisplacment(this.t, this.timelag);
   }

   public double getRelativeVariance() {
      return this.getMeanSquaredDisplacment(this.t, this.timelag)[1];
   }

   public String getName() {
      return "Mean squared displacement-dt-" + this.timelag;
   }

   public String getShortName() {
      return "MSD";
   }

   public void setOverlap(boolean overlap) {
      this.overlap = overlap;
   }
}
