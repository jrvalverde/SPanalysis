/*    */ package drift;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import traJ.TrajectoryModified;
/*    */ import traJ.TrajectoryValidIndexTimelagIteratorModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StaticDriftCalculatorModified<T extends TrajectoryModified>
/*    */ {
/*    */   public double[] calculateDrift(ArrayList<T> tracks) {
/* 17 */     double[] result = new double[3];
/*    */     
/* 19 */     double sumX = 0.0D;
/* 20 */     double sumY = 0.0D;
/* 21 */     double sumZ = 0.0D;
/* 22 */     int N = 0;
/* 23 */     for (int i = 0; i < tracks.size(); i++) {
/* 24 */       TrajectoryModified trajectoryModified = (TrajectoryModified)tracks.get(i);
/* 25 */       TrajectoryValidIndexTimelagIteratorModified it = new TrajectoryValidIndexTimelagIteratorModified(trajectoryModified, 1);
/*    */ 
/*    */       
/* 28 */       while (it.hasNext()) {
/* 29 */         int j = it.next().intValue();
/* 30 */         sumX += ((Point3dModified)trajectoryModified.get(j + 1)).x - ((Point3dModified)trajectoryModified.get(j)).x;
/* 31 */         sumY += ((Point3dModified)trajectoryModified.get(j + 1)).y - ((Point3dModified)trajectoryModified.get(j)).y;
/* 32 */         sumZ += ((Point3dModified)trajectoryModified.get(j + 1)).z - ((Point3dModified)trajectoryModified.get(j)).z;
/* 33 */         N++;
/*    */       } 
/*    */     } 
/* 36 */     result[0] = sumX / N;
/* 37 */     result[1] = sumY / N;
/* 38 */     result[2] = sumZ / N;
/* 39 */     return result;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/drift/StaticDriftCalculatorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */