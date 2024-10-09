/*    */ package features;
/*    */ 
/*    */ import math.RadiusGyrationTensor2DModified;
/*    */ import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/*    */ import org.apache.commons.math3.linear.EigenDecomposition;
/*    */ import org.apache.commons.math3.linear.RealMatrix;
/*    */ import traJ.TrajectoryModified;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Asymmetry3FeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   private TrajectoryModified t;
/*    */   
/*    */   public Asymmetry3FeatureModified(TrajectoryModified t) {
/* 30 */     this.t = t;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 38 */     Array2DRowRealMatrix gyr = RadiusGyrationTensor2DModified.getRadiusOfGyrationTensor(this.t);
/* 39 */     EigenDecomposition eigdec = new EigenDecomposition((RealMatrix)gyr);
/* 40 */     double e1 = eigdec.getRealEigenvalue(0);
/* 41 */     double e2 = eigdec.getRealEigenvalue(1);
/* 42 */     double asym = -1.0D * Math.log(1.0D - Math.pow(e1 - e2, 2.0D) / 2.0D * Math.pow(e1 + e2, 2.0D));
/* 43 */     this.result = new double[] { asym };
/*    */     
/* 45 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 50 */     return "Assymetry3";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 55 */     return "ASYM3";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 60 */     this.t = t;
/* 61 */     this.result = null;
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/features/Asymmetry3FeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */