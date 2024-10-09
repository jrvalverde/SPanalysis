package smileModified;

public class MersenneTwisterModified implements RandomNumberGeneratorModified {
   private static final int UPPER_MASK = Integer.MIN_VALUE;
   private static final int LOWER_MASK = Integer.MAX_VALUE;
   private static final int N = 624;
   private static final int M = 397;
   private static final int[] MAGIC = new int[]{0, -1727483681};
   private static final int MAGIC_FACTOR1 = 1812433253;
   private static final int MAGIC_FACTOR2 = 1664525;
   private static final int MAGIC_FACTOR3 = 1566083941;
   private static final int MAGIC_MASK1 = -1658038656;
   private static final int MAGIC_MASK2 = -272236544;
   private static final int MAGIC_SEED = 19650218;
   private final int[] mt;
   private int mti;

   public MersenneTwisterModified() {
      this(19650218);
   }

   public MersenneTwisterModified(int seed) {
      this.mt = new int[624];
      this.setSeed(seed);
   }

   public MersenneTwisterModified(long seed) {
      this.mt = new int[624];
      this.setSeed(seed);
   }

   public void setSeed(long seed) {
      this.setSeed((int)(seed % 2147483647L));
   }

   public void setSeed(int seed) {
      this.mt[0] = seed;

      for(this.mti = 1; this.mti < 624; ++this.mti) {
         this.mt[this.mti] = 1812433253 * (this.mt[this.mti - 1] ^ this.mt[this.mti - 1] >>> 30) + this.mti;
      }

   }

   public void setSeed(int[] seed) {
      this.setSeed(19650218);
      if (seed != null && seed.length != 0) {
         int i = 1;
         int j = 0;

         int k;
         for(k = Math.max(624, seed.length); k > 0; --k) {
            this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1664525) + seed[j] + j;
            ++i;
            ++j;
            if (i >= 624) {
               this.mt[0] = this.mt[623];
               i = 1;
            }

            if (j >= seed.length) {
               j = 0;
            }
         }

         for(k = 623; k > 0; --k) {
            this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1566083941) - i;
            ++i;
            if (i >= 624) {
               this.mt[0] = this.mt[623];
               i = 1;
            }
         }

         int[] var10000 = this.mt;
         var10000[0] |= Integer.MIN_VALUE;
      }
   }

   public int next(int numbits) {
      return this.nextInt() >>> 32 - numbits;
   }

   public double nextDouble() {
      return (double)(this.nextInt() >>> 1) / 2.147483647E9D;
   }

   public void nextDoubles(double[] d) {
      int n = d.length;

      for(int i = 0; i < n; ++i) {
         d[i] = this.nextDouble();
      }

   }

   public int nextInt() {
      int x;
      if (this.mti >= 624) {
         int i;
         for(i = 0; i < 227; ++i) {
            x = this.mt[i] & Integer.MIN_VALUE | this.mt[i + 1] & Integer.MAX_VALUE;
            this.mt[i] = this.mt[i + 397] ^ x >>> 1 ^ MAGIC[x & 1];
         }

         while(i < 623) {
            x = this.mt[i] & Integer.MIN_VALUE | this.mt[i + 1] & Integer.MAX_VALUE;
            this.mt[i] = this.mt[i + -227] ^ x >>> 1 ^ MAGIC[x & 1];
            ++i;
         }

         x = this.mt[623] & Integer.MIN_VALUE | this.mt[0] & Integer.MAX_VALUE;
         this.mt[623] = this.mt[396] ^ x >>> 1 ^ MAGIC[x & 1];
         this.mti = 0;
      }

      x = this.mt[this.mti++];
      x ^= x >>> 11;
      x ^= x << 7 & -1658038656;
      x ^= x << 15 & -272236544;
      x ^= x >>> 18;
      return x;
   }

   public int nextInt(int n) {
      if (n <= 0) {
         throw new IllegalArgumentException("n must be positive");
      } else if ((n & -n) == n) {
         return (int)((long)n * (long)this.next(31) >> 31);
      } else {
         int bits;
         int val;
         do {
            bits = this.next(31);
            val = bits % n;
         } while(bits - val + (n - 1) < 0);

         return val;
      }
   }

   public long nextLong() {
      long x = (long)this.nextInt();
      return x << 32 | (long)this.nextInt();
   }
}
