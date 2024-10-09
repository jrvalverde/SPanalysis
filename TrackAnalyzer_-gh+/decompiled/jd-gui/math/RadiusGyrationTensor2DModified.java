/*    */ package math;
/*    */ 
/*    */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ 
/*    */ public class RadiusGyrationTensor2DModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public RadiusGyrationTensor2DModified(TrajectoryModified t) {
/* 13 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Array2DRowRealMatrix getRadiusOfGyrationTensor() {
/* 24 */     return getRadiusOfGyrationTensor(this.t);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Array2DRowRealMatrix getRadiusOfGyrationTensor(TrajectoryModified t) {
/* 35 */     double meanx = 0.0D;
/* 36 */     double meany = 0.0D;
/* 37 */     for (int i = 0; i < t.size(); i++) {
/* 38 */       meanx += ((Point3dModified)t.get(i)).x;
/* 39 */       meany += ((Point3dModified)t.get(i)).y;
/*    */     } 
/* 41 */     meanx /= t.size();
/* 42 */     meany /= t.size();
/*    */     
/* 44 */     double e11 = 0.0D;
/* 45 */     double e12 = 0.0D;
/* 46 */     double e21 = 0.0D;
/* 47 */     double e22 = 0.0D;
/*    */     
/* 49 */     for (int j = 0; j < t.size(); j++) {
/* 50 */       e11 += Math.pow(((Point3dModified)t.get(j)).x - meanx, 2.0D);
/* 51 */       e12 += (((Point3dModified)t.get(j)).x - meanx) * (((Point3dModified)t.get(j)).y - meany);
/* 52 */       e22 += Math.pow(((Point3dModified)t.get(j)).y - meany, 2.0D);
/*    */     } 
/* 54 */     e11 /= t.size();
/* 55 */     e12 /= t.size();
/* 56 */     e21 = e12;
/* 57 */     e22 /= t.size();
/* 58 */     int rows = 2;
/* 59 */     int columns = 2;
/* 60 */     Array2DRowRealMatrix gyr = new Array2DRowRealMatrix(rows, columns);
/*    */     
/* 62 */     gyr.addToEntry(0, 0, e11);
/* 63 */     gyr.addToEntry(0, 1, e12);
/* 64 */     gyr.addToEntry(1, 0, e21);
/* 65 */     gyr.addToEntry(1, 1, e22);
/*    */     
/* 67 */     return gyr;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/math/RadiusGyrationTensor2DModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */