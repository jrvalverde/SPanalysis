package features;

import traJ.TrajectoryModified;

public interface AbstractMeanSquaredDisplacmentEvaluatorModified {
  void setTimelag(int paramInt);
  
  void setTrajectory(TrajectoryModified paramTrajectoryModified);
  
  double[] evaluate();
}


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/AbstractMeanSquaredDisplacmentEvaluatorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */