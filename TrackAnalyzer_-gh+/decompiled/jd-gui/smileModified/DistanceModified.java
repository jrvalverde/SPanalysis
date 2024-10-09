/*     */ package smileModified;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.function.ToDoubleBiFunction;
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
/*     */ public interface DistanceModified<T>
/*     */   extends ToDoubleBiFunction<T, T>, Serializable
/*     */ {
/*     */   default double apply(T x, T y) {
/*  54 */     return d(x, y);
/*     */   }
/*     */ 
/*     */   
/*     */   default double applyAsDouble(T x, T y) {
/*  59 */     return d(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default MatrixModified D(Object[] x) {
/*  69 */     int n = x.length;
/*  70 */     int N = n * (n - 1) / 2;
/*  71 */     MatrixModified D = new MatrixModified(n, n);
/*  72 */     IntStream.range(0, N).parallel().forEach(k -> {
/*     */           int j = paramInt1 - 2 - (int)Math.floor(Math.sqrt((-8 * k + 4 * paramInt1 * (paramInt1 - 1) - 7)) / 2.0D - 0.5D);
/*     */           
/*     */           int i = k + j + 1 - paramInt1 * (paramInt1 - 1) / 2 + (paramInt1 - j) * (paramInt1 - j - 1) / 2;
/*     */           paramMatrixModified.set(i, j, d((T)paramArrayOfObject[i], (T)paramArrayOfObject[j]));
/*     */         });
/*  78 */     for (int i = 0; i < n; i++) {
/*  79 */       for (int j = i + 1; j < n; j++) {
/*  80 */         D.set(i, j, D.get(j, i));
/*     */       }
/*     */     } 
/*     */     
/*  84 */     D.uplo(UPLOModified.LOWER);
/*  85 */     return D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default MatrixModified D(Object[] x, Object[] y) {
/*  96 */     int m = x.length;
/*  97 */     int n = y.length;
/*  98 */     MatrixModified D = new MatrixModified(m, n);
/*  99 */     IntStream.range(0, m).parallel().forEach(i -> {
/*     */           T xi = (T)paramArrayOfObject1[i];
/*     */           
/*     */           for (int j = 0; j < paramInt1; j++) {
/*     */             paramMatrixModified.set(i, j, d(xi, (T)paramArrayOfObject2[j]));
/*     */           }
/*     */         });
/* 106 */     return D;
/*     */   }
/*     */   
/*     */   double d(T paramT1, T paramT2);
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/DistanceModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */