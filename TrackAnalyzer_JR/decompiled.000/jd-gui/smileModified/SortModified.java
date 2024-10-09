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
/*     */ public interface SortModified
/*     */ {
/*     */   static void swap(int[] x, int i, int j) {
/*  35 */     int a = x[i];
/*  36 */     x[i] = x[j];
/*  37 */     x[j] = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void swap(float[] x, int i, int j) {
/*  47 */     float a = x[i];
/*  48 */     x[i] = x[j];
/*  49 */     x[j] = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void swap(double[] x, int i, int j) {
/*  60 */     double a = x[i];
/*  61 */     x[i] = x[j];
/*  62 */     x[j] = a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void swap(Object[] x, int i, int j) {
/*  73 */     Object a = x[i];
/*  74 */     x[i] = x[j];
/*  75 */     x[j] = a;
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
/*     */   static void siftUp(int[] x, int k) {
/*  87 */     while (k > 1 && x[k / 2] < x[k]) {
/*  88 */       swap(x, k, k / 2);
/*  89 */       k /= 2;
/*     */     } 
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
/*     */   static void siftUp(float[] x, int k) {
/* 102 */     while (k > 1 && x[k / 2] < x[k]) {
/* 103 */       swap(x, k, k / 2);
/* 104 */       k /= 2;
/*     */     } 
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
/*     */   static void siftUp(double[] x, int k) {
/* 117 */     while (k > 1 && x[k / 2] < x[k]) {
/* 118 */       swap(x, k, k / 2);
/* 119 */       k /= 2;
/*     */     } 
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
/*     */   static <T extends Comparable<? super T>> void siftUp(Comparable[] x, int k) {
/* 133 */     while (k > 1 && x[k / 2].compareTo(x[k]) < 0) {
/* 134 */       swap((Object[])x, k, k / 2);
/* 135 */       k /= 2;
/*     */     } 
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
/*     */   static void siftDown(int[] x, int k, int n) {
/* 151 */     while (2 * k <= n) {
/* 152 */       int j = 2 * k;
/* 153 */       if (j < n && x[j] < x[j + 1]) {
/* 154 */         j++;
/*     */       }
/* 156 */       if (x[k] >= x[j]) {
/*     */         break;
/*     */       }
/* 159 */       swap(x, k, j);
/* 160 */       k = j;
/*     */     } 
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
/*     */   static void siftDown(float[] x, int k, int n) {
/* 176 */     while (2 * k <= n) {
/* 177 */       int j = 2 * k;
/* 178 */       if (j < n && x[j] < x[j + 1]) {
/* 179 */         j++;
/*     */       }
/* 181 */       if (x[k] >= x[j]) {
/*     */         break;
/*     */       }
/* 184 */       swap(x, k, j);
/* 185 */       k = j;
/*     */     } 
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
/*     */   static void siftDown(double[] x, int k, int n) {
/* 201 */     while (2 * k <= n) {
/* 202 */       int j = 2 * k;
/* 203 */       if (j < n && x[j] < x[j + 1]) {
/* 204 */         j++;
/*     */       }
/* 206 */       if (x[k] >= x[j]) {
/*     */         break;
/*     */       }
/* 209 */       swap(x, k, j);
/* 210 */       k = j;
/*     */     } 
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
/*     */   static <T extends Comparable<? super T>> void siftDown(Comparable[] x, int k, int n) {
/* 227 */     while (2 * k <= n) {
/* 228 */       int j = 2 * k;
/* 229 */       if (j < n && x[j].compareTo(x[j + 1]) < 0) {
/* 230 */         j++;
/*     */       }
/* 232 */       if (x[k].compareTo(x[j]) >= 0) {
/*     */         break;
/*     */       }
/* 235 */       swap((Object[])x, k, j);
/* 236 */       k = j;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/SortModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */