package features;

import traJ.TrajectoryModified;

public abstract class AbstractTrajectoryFeatureModified {
   protected double[] result = null;

   public abstract double[] evaluate();

   public double[] getValue() {
      if (this.result == null) {
         this.result = this.evaluate();
      }

      return this.result;
   }

   public abstract String getName();

   public abstract String getShortName();

   public abstract void setTrajectory(TrajectoryModified var1);
}
