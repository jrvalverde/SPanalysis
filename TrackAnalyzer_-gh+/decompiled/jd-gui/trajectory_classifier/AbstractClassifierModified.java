package trajectory_classifier;

import java.util.ArrayList;
import traJ.TrajectoryModified;

public abstract class AbstractClassifierModified {
  public abstract String classify(TrajectoryModified paramTrajectoryModified);
  
  public abstract void start();
  
  public abstract void stop();
  
  public abstract String[] classify(ArrayList<TrajectoryModified> paramArrayList);
  
  public abstract double[] getConfindence();
}


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/trajectory_classifier/AbstractClassifierModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */