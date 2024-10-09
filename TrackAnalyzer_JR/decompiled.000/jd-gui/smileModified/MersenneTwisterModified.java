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
/*     */ public class MersenneTwisterModified
/*     */   implements RandomNumberGeneratorModified
/*     */ {
/*     */   private static final int UPPER_MASK = -2147483648;
/*     */   private static final int LOWER_MASK = 2147483647;
/*     */   private static final int N = 624;
/*     */   private static final int M = 397;
/*  53 */   private static final int[] MAGIC = new int[] { 0, -1727483681 };
/*     */   
/*     */   private static final int MAGIC_FACTOR1 = 1812433253;
/*     */   
/*     */   private static final int MAGIC_FACTOR2 = 1664525;
/*     */   
/*     */   private static final int MAGIC_FACTOR3 = 1566083941;
/*     */   private static final int MAGIC_MASK1 = -1658038656;
/*     */   private static final int MAGIC_MASK2 = -272236544;
/*     */   private static final int MAGIC_SEED = 19650218;
/*  63 */   private final int[] mt = new int[624];
/*     */ 
/*     */   
/*     */   private int mti;
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwisterModified() {
/*  71 */     this(19650218);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwisterModified(int seed) {
/*  79 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MersenneTwisterModified(long seed) {
/*  87 */     setSeed(seed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(long seed) {
/*  94 */     setSeed((int)(seed % 2147483647L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int seed) {
/* 102 */     this.mt[0] = seed;
/* 103 */     for (this.mti = 1; this.mti < 624; this.mti++) {
/* 104 */       this.mt[this.mti] = 1812433253 * (this.mt[this.mti - 1] ^ this.mt[this.mti - 1] >>> 30) + this.mti;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeed(int[] seed) {
/* 113 */     setSeed(19650218);
/*     */     
/* 115 */     if (seed == null || seed.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 119 */     int i = 1, j = 0; int k;
/* 120 */     for (k = Math.max(624, seed.length); k > 0; k--) {
/* 121 */       this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1664525) + seed[j] + j;
/* 122 */       i++;
/* 123 */       j++;
/* 124 */       if (i >= 624) {
/* 125 */         this.mt[0] = this.mt[623];
/* 126 */         i = 1;
/*     */       } 
/* 128 */       if (j >= seed.length) {
/* 129 */         j = 0;
/*     */       }
/*     */     } 
/*     */     
/* 133 */     for (k = 623; k > 0; k--) {
/* 134 */       this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1566083941) - i;
/* 135 */       i++;
/* 136 */       if (i >= 624) {
/* 137 */         this.mt[0] = this.mt[623];
/* 138 */         i = 1;
/*     */       } 
/*     */     } 
/* 141 */     this.mt[0] = this.mt[0] | Integer.MIN_VALUE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int next(int numbits) {
/* 146 */     return nextInt() >>> 32 - numbits;
/*     */   }
/*     */ 
/*     */   
/*     */   public double nextDouble() {
/* 151 */     return (nextInt() >>> 1) / 2.147483647E9D;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextDoubles(double[] d) {
/* 156 */     int n = d.length;
/* 157 */     for (int i = 0; i < n; i++) {
/* 158 */       d[i] = nextDouble();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int nextInt() {
/* 166 */     if (this.mti >= 624) {
/*     */       int i;
/* 168 */       for (i = 0; i < 227; i++) {
/* 169 */         int k = this.mt[i] & Integer.MIN_VALUE | this.mt[i + 1] & Integer.MAX_VALUE;
/* 170 */         this.mt[i] = this.mt[i + 397] ^ k >>> 1 ^ MAGIC[k & 0x1];
/*     */       } 
/* 172 */       for (; i < 623; i++) {
/* 173 */         int k = this.mt[i] & Integer.MIN_VALUE | this.mt[i + 1] & Integer.MAX_VALUE;
/* 174 */         this.mt[i] = this.mt[i + -227] ^ k >>> 1 ^ MAGIC[k & 0x1];
/*     */       } 
/* 176 */       int j = this.mt[623] & Integer.MIN_VALUE | this.mt[0] & Integer.MAX_VALUE;
/* 177 */       this.mt[623] = this.mt[396] ^ j >>> 1 ^ MAGIC[j & 0x1];
/*     */       
/* 179 */       this.mti = 0;
/*     */     } 
/*     */     
/* 182 */     int x = this.mt[this.mti++];
/*     */ 
/*     */     
/* 185 */     x ^= x >>> 11;
/* 186 */     x ^= x << 7 & 0x9D2C5680;
/* 187 */     x ^= x << 15 & 0xEFC60000;
/* 188 */     x ^= x >>> 18;
/*     */     
/* 190 */     return x;
/*     */   }
/*     */   public int nextInt(int n) {
/*     */     int bits;
/*     */     int val;
/* 195 */     if (n <= 0) {
/* 196 */       throw new IllegalArgumentException("n must be positive");
/*     */     }
/*     */ 
/*     */     
/* 200 */     if ((n & -n) == n) {
/* 201 */       return (int)(n * next(31) >> 31L);
/*     */     }
/*     */ 
/*     */     
/*     */     do {
/* 206 */       bits = next(31);
/* 207 */       val = bits % n;
/* 208 */     } while (bits - val + n - 1 < 0);
/*     */     
/* 210 */     return val;
/*     */   }
/*     */ 
/*     */   
/*     */   public long nextLong() {
/* 215 */     long x = nextInt();
/* 216 */     return x << 32L | nextInt();
/*     */   }
/*     */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/MersenneTwisterModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */