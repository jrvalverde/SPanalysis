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
/*     */ public class UniversalGeneratorModified
/*     */   implements RandomNumberGeneratorModified
/*     */ {
/*     */   private static final int DEFAULT_SEED = 54217137;
/*     */   private static final int BIG_PRIME = 899999963;
/*     */   private double c;
/*     */   private double cd;
/*     */   private double cm;
/*     */   private double[] u;
/*     */   private int i97;
/*     */   private int j97;
/*     */   
/*     */   public UniversalGeneratorModified() {
/*  53 */     setSeed(54217137L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniversalGeneratorModified(int seed) {
/*  61 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UniversalGeneratorModified(long seed) {
/*  69 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/*  74 */     this.u = new double[97];
/*     */     
/*  76 */     int ijkl = Math.abs((int)(seed % 899999963L));
/*  77 */     int ij = ijkl / 30082;
/*  78 */     int kl = ijkl % 30082;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  83 */     if (ij < 0 || ij > 31328 || kl < 0 || kl > 30081) {
/*  84 */       ij %= 31329;
/*  85 */       kl %= 30082;
/*     */     } 
/*     */     
/*  88 */     int i = ij / 177 % 177 + 2;
/*  89 */     int j = ij % 177 + 2;
/*  90 */     int k = kl / 169 % 178 + 1;
/*  91 */     int l = kl % 169;
/*     */ 
/*     */ 
/*     */     
/*  95 */     for (int ii = 0; ii < 97; ii++) {
/*  96 */       double s = 0.0D;
/*  97 */       double t = 0.5D;
/*  98 */       for (int jj = 0; jj < 24; jj++) {
/*  99 */         int m = i * j % 179 * k % 179;
/* 100 */         i = j;
/* 101 */         j = k;
/* 102 */         k = m;
/* 103 */         l = (53 * l + 1) % 169;
/* 104 */         if (l * m % 64 >= 32) {
/* 105 */           s += t;
/*     */         }
/* 107 */         t *= 0.5D;
/*     */       } 
/* 109 */       this.u[ii] = s;
/*     */     } 
/*     */     
/* 112 */     this.c = 0.021602869033813477D;
/* 113 */     this.cd = 0.45623308420181274D;
/* 114 */     this.cm = 0.9999998211860657D;
/* 115 */     this.i97 = 96;
/* 116 */     this.j97 = 32;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/* 123 */     double uni = this.u[this.i97] - this.u[this.j97];
/* 124 */     if (uni < 0.0D) {
/* 125 */       uni++;
/*     */     }
/*     */     
/* 128 */     this.u[this.i97] = uni;
/* 129 */     if (--this.i97 < 0) {
/* 130 */       this.i97 = 96;
/*     */     }
/*     */     
/* 133 */     if (--this.j97 < 0) {
/* 134 */       this.j97 = 96;
/*     */     }
/*     */     
/* 137 */     this.c -= this.cd;
/* 138 */     if (this.c < 0.0D) {
/* 139 */       this.c += this.cm;
/*     */     }
/*     */     
/* 142 */     uni -= this.c;
/* 143 */     if (uni < 0.0D) {
/* 144 */       uni++;
/*     */     }
/*     */     
/* 147 */     return uni;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/* 152 */     int n = d.length;
/*     */ 
/*     */ 
/*     */     
/* 156 */     for (int i = 0; i < n; i++) {
/* 157 */       double uni = this.u[this.i97] - this.u[this.j97];
/* 158 */       if (uni < 0.0D) {
/* 159 */         uni++;
/*     */       }
/* 161 */       this.u[this.i97] = uni;
/* 162 */       if (--this.i97 < 0) {
/* 163 */         this.i97 = 96;
/*     */       }
/* 165 */       if (--this.j97 < 0) {
/* 166 */         this.j97 = 96;
/*     */       }
/* 168 */       this.c -= this.cd;
/* 169 */       if (this.c < 0.0D) {
/* 170 */         this.c += this.cm;
/*     */       }
/* 172 */       uni -= this.c;
/* 173 */       if (uni < 0.0D) {
/* 174 */         uni++;
/*     */       }
/* 176 */       d[i] = uni;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int next(int numbits) {
/* 182 */     return nextInt() >>> 32 - numbits;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 187 */     return (int)Math.floor(2.147483647E9D * (2.0D * nextDouble() - 1.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextInt(int n) {
/* 192 */     if (n <= 0) {
/* 193 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */     
/* 196 */     return (int)(nextDouble() * n);
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 201 */     return (long)Math.floor(9.223372036854776E18D * (2.0D * nextDouble() - 1.0D));
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_-master/jar_expanded/!/smileModified/UniversalGeneratorModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */