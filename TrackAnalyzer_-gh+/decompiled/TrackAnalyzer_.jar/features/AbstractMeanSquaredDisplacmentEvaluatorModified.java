package features;

import traJ.TrajectoryModified;

public interface AbstractMeanSquaredDisplacmentEvaluatorModified {
   void setTimelag(int var1);

   void setTrajectory(TrajectoryModified var1);

   double[] evaluate();
}
