/*    */ package features;
/*    */ 
/*    */ import traJ.TrajectoryModified;
/*    */ import vecmath.Point3dModified;
/*    */ 
/*    */ public class FractalDimensionFeatureModified
/*    */   extends AbstractTrajectoryFeatureModified
/*    */ {
/*    */   TrajectoryModified t;
/*    */   
/*    */   public FractalDimensionFeatureModified(TrajectoryModified t) {
/* 12 */     this.t = t;
/* 13 */     if (t.getDimension() != 2) {
/* 14 */       throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] evaluate() {
/* 23 */     double largestDistance = Double.MIN_VALUE;
/* 24 */     double totalLength = 0.0D;
/* 25 */     for (int i = 0; i < this.t.size(); i++) {
/* 26 */       for (int j = i + 1; j < this.t.size(); j++) {
/* 27 */         double d = ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(j));
/* 28 */         if (d > largestDistance) {
/* 29 */           largestDistance = d;
/*    */         }
/*    */       } 
/* 32 */       if (i > 0) {
/* 33 */         totalLength += ((Point3dModified)this.t.get(i)).distance((Point3dModified)this.t.get(i - 1));
/*    */       }
/*    */     } 
/*    */     
/* 37 */     double n = (this.t.size() - 1);
/* 38 */     double fractalDImension = Math.log(n) / (Math.log(n) + Math.log(largestDistance / totalLength));
/* 39 */     this.result = new double[] { fractalDImension };
/*    */ 
/*    */     
/* 42 */     return this.result;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 47 */     return "Fractal Dimension";
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTrajectory(TrajectoryModified t) {
/* 52 */     this.t = t;
/* 53 */     this.result = null;
/* 54 */     if (t.getDimension() != 2) {
/* 55 */       throw new IllegalArgumentException("The fractal dimension feature only supoorts planer (2D) trajetorys");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getShortName() {
/* 62 */     return "FD";
/*    */   }
/*    */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/features/FractalDimensionFeatureModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */