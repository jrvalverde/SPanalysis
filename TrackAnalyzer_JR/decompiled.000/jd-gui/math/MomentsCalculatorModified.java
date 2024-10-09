/*    */ package math;
/*    */ 
/*    */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*    */ import org.apache.commons.math3.linear.EigenDecomposition;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
/*    */ import org.apache.commons.math3.stat.descriptive.moment.Mean;
/*    */ import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ import vecmath.Vector2d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MomentsCalculatorModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public MomentsCalculatorModified(TrajectoryModified t) {
/* 21 */     this.t = t;
/*    */   }
/*    */   
/*    */   public double calculateNthMoment(int n) {
/* 25 */     Array2DRowRealMatrix gyr = RadiusGyrationTensor2DModified.getRadiusOfGyrationTensor(this.t);
/* 26 */     EigenDecomposition eigdec = new EigenDecomposition((RealMatrix)gyr);
/*    */     
/* 28 */     Vector2d eigv = new Vector2d(eigdec.getEigenvector(0).getEntry(0), eigdec.getEigenvector(0).getEntry(1));
/*    */     
/* 30 */     double[] projected = new double[this.t.size()];
/* 31 */     for (int i = 0; i < this.t.size(); i++) {
/* 32 */       Vector2d pos = new Vector2d(((Point3dModified)this.t.get(i)).x, ((Point3dModified)this.t.get(i)).y);
/* 33 */       double v = eigv.dot(pos);
/* 34 */       projected[i] = v;
/*    */     } 
/*    */     
/* 37 */     Mean m = new Mean();
/* 38 */     StandardDeviation s = new StandardDeviation();
/* 39 */     double mean = m.evaluate(projected);
/* 40 */     double sd = s.evaluate(projected);
/* 41 */     double sumPowN = 0.0D;
/*    */     
/* 43 */     for (int j = 0; j < projected.length; j++) {
/* 44 */       sumPowN += Math.pow((projected[j] - mean) / sd, n);
/*    */     }
/*    */     
/* 47 */     double nThMoment = sumPowN / projected.length;
/*    */     
/* 49 */     return nThMoment;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/math/MomentsCalculatorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */