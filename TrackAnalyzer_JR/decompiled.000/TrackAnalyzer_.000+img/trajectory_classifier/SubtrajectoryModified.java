package trajectory_classifier;

import java.util.ArrayList;
import java.util.Iterator;
import traJ.TrajectoryModified;

public class SubtrajectoryModified extends TrajectoryModified {
   private static final long serialVersionUID = 3846588503781023924L;
   private TrajectoryModified parent;
   private double confidence;

   public SubtrajectoryModified(TrajectoryModified parent, int dimension) {
      super(dimension);
      this.parent = parent;
   }

   public SubtrajectoryModified(TrajectoryModified parent, int dimension, int relativeStartPoint) {
      super(dimension, relativeStartPoint);
      this.parent = parent;
   }

   public TrajectoryModified getParent() {
      return this.parent;
   }

   public static ArrayList<SubtrajectoryModified> getTracksWithSameParant(ArrayList<SubtrajectoryModified> tracks, long parentid) {
      ArrayList<SubtrajectoryModified> res = new ArrayList();
      Iterator var5 = tracks.iterator();

      while(var5.hasNext()) {
         SubtrajectoryModified sub = (SubtrajectoryModified)var5.next();
         if (sub.getParent().getID() == parentid) {
            res.add(sub);
         }
      }

      return res;
   }

   public void setConfidence(double confidence) {
      this.confidence = confidence;
   }

   public double getConfidence() {
      return this.confidence;
   }
}
