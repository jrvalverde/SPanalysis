/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public interface DistributionModified
/*     */   extends Serializable
/*     */ {
/*     */   int length();
/*     */   
/*     */   double mean();
/*     */   
/*     */   double variance();
/*     */   
/*     */   default double sd() {
/*  48 */     return Math.sqrt(variance());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double entropy();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double rand();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double[] rand(int n) {
/*  69 */     double[] data = new double[n];
/*  70 */     for (int i = 0; i < n; i++) {
/*  71 */       data[i] = rand();
/*     */     }
/*  73 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double p(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double logp(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double cdf(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   double quantile(double paramDouble);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double likelihood(double[] x) {
/* 112 */     return Math.exp(logLikelihood(x));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default double logLikelihood(double[] x) {
/* 121 */     double L = 0.0D; byte b; int i;
/*     */     double[] arrayOfDouble;
/* 123 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double xi = arrayOfDouble[b];
/* 124 */       L += logp(xi); b++; }
/*     */     
/* 126 */     return L;
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/DistributionModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */