/*     */ package smileModified;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDistributionModified
/*     */   implements DistributionModified
/*     */ {
/*     */   protected double rejection(double pmax, double xmin, double xmax) {
/*     */     double x;
/*     */     double y;
/*     */     do {
/*  64 */       x = xmin + MathExModified.random() * (xmax - xmin);
/*  65 */       y = MathExModified.random() * pmax;
/*  66 */     } while (p(x) < y);
/*     */     
/*  68 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double inverseTransformSampling() {
/*  90 */     double u = MathExModified.random();
/*  91 */     return quantile(u);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double quantile(double p, double xmin, double xmax, double eps) {
/* 105 */     if (eps <= 0.0D) {
/* 106 */       throw new IllegalArgumentException("Invalid epsilon: " + eps);
/*     */     }
/*     */     
/* 109 */     while (Math.abs(xmax - xmin) > eps) {
/* 110 */       double xmed = (xmax + xmin) / 2.0D;
/* 111 */       if (cdf(xmed) > p) {
/* 112 */         xmax = xmed; continue;
/*     */       } 
/* 114 */       xmin = xmed;
/*     */     } 
/*     */ 
/*     */     
/* 118 */     return xmin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected double quantile(double p, double xmin, double xmax) {
/* 130 */     return quantile(p, xmin, xmax, 1.0E-6D);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/AbstractDistributionModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */