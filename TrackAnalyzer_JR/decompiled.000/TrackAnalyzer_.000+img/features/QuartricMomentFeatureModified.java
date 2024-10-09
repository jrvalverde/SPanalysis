package features;

import traJ.TrajectoryModified;
import traJ.TrajectoryValidIndexTimelagIteratorModified;
import vecmath.Point3dModified;

public class QuartricMomentFeatureModified extends AbstractTrajectoryFeatureModified {
   private TrajectoryModified t;
   private int timelag;
   private String shortname = "QMOMENT";

   public QuartricMomentFeatureModified(TrajectoryModified t, int timelag) {
      this.t = t;
      this.timelag = timelag;
   }

   public double[] evaluate() {
      double sum = 0.0D;
      TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(this.t, this.timelag);

      int N;
      for(N = 0; it.hasNext(); ++N) {
         int i = it.next();
         sum = sum + Math.pow(((Point3dModified)this.t.get(i)).x - ((Point3dModified)this.t.get(i + this.timelag)).x, 4.0D) + Math.pow(((Point3dModified)this.t.get(i)).y - ((Point3dModified)this.t.get(i + this.timelag)).y, 4.0D) + Math.pow(((Point3dModified)this.t.get(i)).z - ((Point3dModified)this.t.get(i + this.timelag)).z, 4.0D);
      }

      this.result = new double[]{sum / (double)N};
      return this.result;
   }

   public void setTimelag(int timelag) {
      this.timelag = timelag;
   }

   public String getName() {
      return "Quartric Moment";
   }

   public String getShortName() {
      return this.shortname;
   }

   public void setShortName(String name) {
      this.shortname = name;
   }

   public void setTrajectory(TrajectoryModified t) {
      this.t = t;
      this.result = null;
   }
}
