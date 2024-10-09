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
/*     */ public interface QuickSelectModified
/*     */ {
/*     */   static int select(int[] x, int k) {
/*  16 */     int n = x.length;
/*  17 */     int l = 0;
/*  18 */     int ir = n - 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  23 */       if (ir <= l + 1) {
/*  24 */         if (ir == l + 1 && x[ir] < x[l]) {
/*  25 */           SortModified.swap(x, l, ir);
/*     */         }
/*  27 */         return x[k];
/*     */       } 
/*  29 */       int mid = l + ir >> 1;
/*  30 */       SortModified.swap(x, mid, l + 1);
/*  31 */       if (x[l] > x[ir]) {
/*  32 */         SortModified.swap(x, l, ir);
/*     */       }
/*  34 */       if (x[l + 1] > x[ir]) {
/*  35 */         SortModified.swap(x, l + 1, ir);
/*     */       }
/*  37 */       if (x[l] > x[l + 1]) {
/*  38 */         SortModified.swap(x, l, l + 1);
/*     */       }
/*  40 */       int i = l + 1;
/*  41 */       int j = ir;
/*  42 */       int a = x[l + 1];
/*     */       
/*     */       while (true) {
/*  45 */         i++;
/*  46 */         if (x[i] >= a) {
/*     */           do {
/*  48 */             j--;
/*  49 */           } while (x[j] > a);
/*  50 */           if (j < i) {
/*     */             break;
/*     */           }
/*  53 */           SortModified.swap(x, i, j);
/*     */         } 
/*  55 */       }  x[l + 1] = x[j];
/*  56 */       x[j] = a;
/*  57 */       if (j >= k) {
/*  58 */         ir = j - 1;
/*     */       }
/*  60 */       if (j <= k) {
/*  61 */         l = i;
/*     */       }
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
/*     */   static float select(float[] x, int k) {
/*  78 */     int n = x.length;
/*  79 */     int l = 0;
/*  80 */     int ir = n - 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/*  85 */       if (ir <= l + 1) {
/*  86 */         if (ir == l + 1 && x[ir] < x[l]) {
/*  87 */           SortModified.swap(x, l, ir);
/*     */         }
/*  89 */         return x[k];
/*     */       } 
/*  91 */       int mid = l + ir >> 1;
/*  92 */       SortModified.swap(x, mid, l + 1);
/*  93 */       if (x[l] > x[ir]) {
/*  94 */         SortModified.swap(x, l, ir);
/*     */       }
/*  96 */       if (x[l + 1] > x[ir]) {
/*  97 */         SortModified.swap(x, l + 1, ir);
/*     */       }
/*  99 */       if (x[l] > x[l + 1]) {
/* 100 */         SortModified.swap(x, l, l + 1);
/*     */       }
/* 102 */       int i = l + 1;
/* 103 */       int j = ir;
/* 104 */       float a = x[l + 1];
/*     */       
/*     */       while (true) {
/* 107 */         i++;
/* 108 */         if (x[i] >= a) {
/*     */           do {
/* 110 */             j--;
/* 111 */           } while (x[j] > a);
/* 112 */           if (j < i) {
/*     */             break;
/*     */           }
/* 115 */           SortModified.swap(x, i, j);
/*     */         } 
/* 117 */       }  x[l + 1] = x[j];
/* 118 */       x[j] = a;
/* 119 */       if (j >= k) {
/* 120 */         ir = j - 1;
/*     */       }
/* 122 */       if (j <= k) {
/* 123 */         l = i;
/*     */       }
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
/*     */   static double select(double[] x, int k) {
/* 140 */     int n = x.length;
/* 141 */     int l = 0;
/* 142 */     int ir = n - 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 147 */       if (ir <= l + 1) {
/* 148 */         if (ir == l + 1 && x[ir] < x[l]) {
/* 149 */           SortModified.swap(x, l, ir);
/*     */         }
/* 151 */         return x[k];
/*     */       } 
/* 153 */       int mid = l + ir >> 1;
/* 154 */       SortModified.swap(x, mid, l + 1);
/* 155 */       if (x[l] > x[ir]) {
/* 156 */         SortModified.swap(x, l, ir);
/*     */       }
/* 158 */       if (x[l + 1] > x[ir]) {
/* 159 */         SortModified.swap(x, l + 1, ir);
/*     */       }
/* 161 */       if (x[l] > x[l + 1]) {
/* 162 */         SortModified.swap(x, l, l + 1);
/*     */       }
/* 164 */       int i = l + 1;
/* 165 */       int j = ir;
/* 166 */       double a = x[l + 1];
/*     */       
/*     */       while (true) {
/* 169 */         i++;
/* 170 */         if (x[i] >= a) {
/*     */           do {
/* 172 */             j--;
/* 173 */           } while (x[j] > a);
/* 174 */           if (j < i) {
/*     */             break;
/*     */           }
/* 177 */           SortModified.swap(x, i, j);
/*     */         } 
/* 179 */       }  x[l + 1] = x[j];
/* 180 */       x[j] = a;
/* 181 */       if (j >= k) {
/* 182 */         ir = j - 1;
/*     */       }
/* 184 */       if (j <= k) {
/* 185 */         l = i;
/*     */       }
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
/*     */   static <T extends Comparable<? super T>> T select(Comparable[] x, int k) {
/* 203 */     int n = x.length;
/* 204 */     int l = 0;
/* 205 */     int ir = n - 1;
/*     */ 
/*     */ 
/*     */     
/*     */     while (true) {
/* 210 */       if (ir <= l + 1) {
/* 211 */         if (ir == l + 1 && x[ir].compareTo(x[l]) < 0) {
/* 212 */           SortModified.swap((Object[])x, l, ir);
/*     */         }
/* 214 */         return (T)x[k];
/*     */       } 
/* 216 */       int mid = l + ir >> 1;
/* 217 */       SortModified.swap((Object[])x, mid, l + 1);
/* 218 */       if (x[l].compareTo(x[ir]) > 0) {
/* 219 */         SortModified.swap((Object[])x, l, ir);
/*     */       }
/* 221 */       if (x[l + 1].compareTo(x[ir]) > 0) {
/* 222 */         SortModified.swap((Object[])x, l + 1, ir);
/*     */       }
/* 224 */       if (x[l].compareTo(x[l + 1]) > 0) {
/* 225 */         SortModified.swap((Object[])x, l, l + 1);
/*     */       }
/* 227 */       int i = l + 1;
/* 228 */       int j = ir;
/* 229 */       Comparable comparable = x[l + 1];
/*     */       
/*     */       while (true) {
/* 232 */         i++;
/* 233 */         if (x[i].compareTo(comparable) >= 0) {
/*     */           do {
/* 235 */             j--;
/* 236 */           } while (x[j].compareTo(comparable) > 0);
/* 237 */           if (j < i) {
/*     */             break;
/*     */           }
/* 240 */           SortModified.swap((Object[])x, i, j);
/*     */         } 
/* 242 */       }  x[l + 1] = x[j];
/* 243 */       x[j] = comparable;
/* 244 */       if (j >= k) {
/* 245 */         ir = j - 1;
/*     */       }
/* 247 */       if (j <= k) {
/* 248 */         l = i;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int median(int[] x) {
/* 260 */     int k = x.length / 2;
/* 261 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float median(float[] x) {
/* 270 */     int k = x.length / 2;
/* 271 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double median(double[] x) {
/* 280 */     int k = x.length / 2;
/* 281 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Comparable<? super T>> T median(Comparable[] x) {
/* 291 */     int k = x.length / 2;
/* 292 */     return select((T[])x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int q1(int[] x) {
/* 301 */     int k = x.length / 4;
/* 302 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float q1(float[] x) {
/* 311 */     int k = x.length / 4;
/* 312 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double q1(double[] x) {
/* 321 */     int k = x.length / 4;
/* 322 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Comparable<? super T>> T q1(Comparable[] x) {
/* 332 */     int k = x.length / 4;
/* 333 */     return select((T[])x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static int q3(int[] x) {
/* 342 */     int k = 3 * x.length / 4;
/* 343 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static float q3(float[] x) {
/* 352 */     int k = 3 * x.length / 4;
/* 353 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static double q3(double[] x) {
/* 362 */     int k = 3 * x.length / 4;
/* 363 */     return select(x, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static <T extends Comparable<? super T>> T q3(Comparable[] x) {
/* 373 */     int k = 3 * x.length / 4;
/* 374 */     return select((T[])x, k);
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/QuickSelectModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */