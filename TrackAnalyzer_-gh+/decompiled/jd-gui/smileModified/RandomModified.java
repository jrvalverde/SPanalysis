/*     */ package smileModified;
/*     */ 
/*     */ import java.util.stream.IntStream;
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
/*     */ public class RandomModified
/*     */ {
/*     */   private final UniversalGeneratorModified real;
/*     */   private final MersenneTwisterModified twister;
/*     */   
/*     */   public RandomModified() {
/*  38 */     this.real = new UniversalGeneratorModified();
/*  39 */     this.twister = new MersenneTwisterModified();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RandomModified(long seed) {
/*  47 */     this.real = new UniversalGeneratorModified(seed);
/*  48 */     this.twister = new MersenneTwisterModified(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/*  56 */     this.real.setSeed(seed);
/*  57 */     this.twister.setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/*  65 */     return this.real.nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/*  73 */     this.real.nextDoubles(d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble(double lo, double hi) {
/*  83 */     return lo + (hi - lo) * nextDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d, double lo, double hi) {
/*  93 */     this.real.nextDoubles(d);
/*     */     
/*  95 */     double l = hi - lo;
/*  96 */     int n = d.length;
/*  97 */     for (int i = 0; i < n; i++) {
/*  98 */       d[i] = lo + l * d[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 107 */     return this.twister.nextInt();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt(int n) {
/* 116 */     return this.twister.nextInt(n);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 124 */     return this.twister.nextLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] permutate(int n) {
/* 134 */     int[] x = IntStream.range(0, n).toArray();
/* 135 */     permutate(x);
/* 136 */     return x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(int[] x) {
/* 144 */     for (int i = 0; i < x.length; i++) {
/* 145 */       int j = i + nextInt(x.length - i);
/* 146 */       MathExModified.swap(x, i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(float[] x) {
/* 155 */     for (int i = 0; i < x.length; i++) {
/* 156 */       int j = i + nextInt(x.length - i);
/* 157 */       MathExModified.swap(x, i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(double[] x) {
/* 166 */     for (int i = 0; i < x.length; i++) {
/* 167 */       int j = i + nextInt(x.length - i);
/* 168 */       MathExModified.swap(x, i, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void permutate(Object[] x) {
/* 177 */     for (int i = 0; i < x.length; i++) {
/* 178 */       int j = i + nextInt(x.length - i);
/* 179 */       MathExModified.swap(x, i, j);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/RandomModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */