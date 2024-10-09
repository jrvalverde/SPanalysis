package smileModified;

public class UniversalGeneratorModified implements RandomNumberGeneratorModified {
   private static final int DEFAULT_SEED = 54217137;
   private static final int BIG_PRIME = 899999963;
   private double c;
   private double cd;
   private double cm;
   private double[] u;
   private int i97;
   private int j97;

   public UniversalGeneratorModified() {
      this.setSeed(54217137L);
   }

   public UniversalGeneratorModified(int seed) {
      this.setSeed((long)seed);
   }

   public UniversalGeneratorModified(long seed) {
      this.setSeed(seed);
   }

   public void setSeed(long seed) {
      this.u = new double[97];
      int ijkl = Math.abs((int)(seed % 899999963L));
      int ij = ijkl / 30082;
      int kl = ijkl % 30082;
      if (ij < 0 || ij > 31328 || kl < 0 || kl > 30081) {
         ij %= 31329;
         kl %= 30082;
      }

      int i = ij / 177 % 177 + 2;
      int j = ij % 177 + 2;
      int k = kl / 169 % 178 + 1;
      int l = kl % 169;

      for(int ii = 0; ii < 97; ++ii) {
         double s = 0.0D;
         double t = 0.5D;

         for(int jj = 0; jj < 24; ++jj) {
            int m = i * j % 179 * k % 179;
            i = j;
            j = k;
            k = m;
            l = (53 * l + 1) % 169;
            if (l * m % 64 >= 32) {
               s += t;
            }

            t *= 0.5D;
         }

         this.u[ii] = s;
      }

      this.c = 0.021602869033813477D;
      this.cd = 0.45623308420181274D;
      this.cm = 0.9999998211860657D;
      this.i97 = 96;
      this.j97 = 32;
   }

   public double nextDouble() {
      double uni = this.u[this.i97] - this.u[this.j97];
      if (uni < 0.0D) {
         ++uni;
      }

      this.u[this.i97] = uni;
      if (--this.i97 < 0) {
         this.i97 = 96;
      }

      if (--this.j97 < 0) {
         this.j97 = 96;
      }

      this.c -= this.cd;
      if (this.c < 0.0D) {
         this.c += this.cm;
      }

      uni -= this.c;
      if (uni < 0.0D) {
         ++uni;
      }

      return uni;
   }

   public void nextDoubles(double[] d) {
      int n = d.length;

      for(int i = 0; i < n; ++i) {
         double uni = this.u[this.i97] - this.u[this.j97];
         if (uni < 0.0D) {
            ++uni;
         }

         this.u[this.i97] = uni;
         if (--this.i97 < 0) {
            this.i97 = 96;
         }

         if (--this.j97 < 0) {
            this.j97 = 96;
         }

         this.c -= this.cd;
         if (this.c < 0.0D) {
            this.c += this.cm;
         }

         uni -= this.c;
         if (uni < 0.0D) {
            ++uni;
         }

         d[i] = uni;
      }

   }

   public int next(int numbits) {
      return this.nextInt() >>> 32 - numbits;
   }

   public int nextInt() {
      return (int)Math.floor(2.147483647E9D * (2.0D * this.nextDouble() - 1.0D));
   }

   public int nextInt(int n) {
      if (n <= 0) {
         throw new IllegalArgumentException("n must be positive");
      } else {
         return (int)(this.nextDouble() * (double)n);
      }
   }

   public long nextLong() {
      return (long)Math.floor(9.223372036854776E18D * (2.0D * this.nextDouble() - 1.0D));
   }
}
