/*    */ package trajectory_classifier;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import traJ.TrajectoryModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubtrajectoryModified
/*    */   extends TrajectoryModified
/*    */ {
/*    */   private static final long serialVersionUID = 3846588503781023924L;
/*    */   private TrajectoryModified parent;
/*    */   private double confidence;
/*    */   
/*    */   public SubtrajectoryModified(TrajectoryModified parent, int dimension) {
/* 18 */     super(dimension);
/* 19 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public SubtrajectoryModified(TrajectoryModified parent, int dimension, int relativeStartPoint) {
/* 23 */     super(dimension, relativeStartPoint);
/* 24 */     this.parent = parent;
/*    */   }
/*    */   
/*    */   public TrajectoryModified getParent() {
/* 28 */     return this.parent;
/*    */   }
/*    */   
/*    */   public static ArrayList<SubtrajectoryModified> getTracksWithSameParant(ArrayList<SubtrajectoryModified> tracks, long parentid) {
/* 32 */     ArrayList<SubtrajectoryModified> res = new ArrayList<>();
/*    */     
/* 34 */     for (SubtrajectoryModified sub : tracks) {
/* 35 */       if (sub.getParent().getID() == parentid) {
/* 36 */         res.add(sub);
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return res;
/*    */   }
/*    */   
/*    */   public void setConfidence(double confidence) {
/* 44 */     this.confidence = confidence;
/*    */   }
/*    */   
/*    */   public double getConfidence() {
/* 48 */     return this.confidence;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/trajectory_classifier/SubtrajectoryModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */