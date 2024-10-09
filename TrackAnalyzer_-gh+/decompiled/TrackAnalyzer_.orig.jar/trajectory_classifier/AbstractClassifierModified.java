package trajectory_classifier;

import java.util.ArrayList;
import traJ.TrajectoryModified;

public abstract class AbstractClassifierModified {
   public abstract String classify(TrajectoryModified var1);

   public abstract void start();

   public abstract void stop();

   public abstract String[] classify(ArrayList<TrajectoryModified> var1);

   public abstract double[] getConfindence();
}
