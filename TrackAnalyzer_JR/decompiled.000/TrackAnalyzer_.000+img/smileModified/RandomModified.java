package smileModified;

import java.util.stream.IntStream;

public class RandomModified {
   private final UniversalGeneratorModified real;
   private final MersenneTwisterModified twister;

   public RandomModified() {
      this.real = new UniversalGeneratorModified();
      this.twister = new MersenneTwisterModified();
   }

   public RandomModified(long seed) {
      this.real = new UniversalGeneratorModified(seed);
      this.twister = new MersenneTwisterModified(seed);
   }

   public void setSeed(long seed) {
      this.real.setSeed(seed);
      this.twister.setSeed(seed);
   }

   public double nextDouble() {
      return this.real.nextDouble();
   }

   public void nextDoubles(double[] d) {
      this.real.nextDoubles(d);
   }

   public double nextDouble(double lo, double hi) {
      return lo + (hi - lo) * this.nextDouble();
   }

   public void nextDoubles(double[] d, double lo, double hi) {
      this.real.nextDoubles(d);
      double l = hi - lo;
      int n = d.length;

      for(int i = 0; i < n; ++i) {
         d[i] = lo + l * d[i];
      }

   }

   public int nextInt() {
      return this.twister.nextInt();
   }

   public int nextInt(int n) {
      return this.twister.nextInt(n);
   }

   public long nextLong() {
      return this.twister.nextLong();
   }

   public int[] permutate(int n) {
      int[] x = IntStream.range(0, n).toArray();
      this.permutate(x);
      return x;
   }

   public void permutate(int[] x) {
      for(int i = 0; i < x.length; ++i) {
         int j = i + this.nextInt(x.length - i);
         MathExModified.swap(x, i, j);
      }

   }

   public void permutate(float[] x) {
      for(int i = 0; i < x.length; ++i) {
         int j = i + this.nextInt(x.length - i);
         MathExModified.swap(x, i, j);
      }

   }

   public void permutate(double[] x) {
      for(int i = 0; i < x.length; ++i) {
         int j = i + this.nextInt(x.length - i);
         MathExModified.swap(x, i, j);
      }

   }

   public void permutate(Object[] x) {
      for(int i = 0; i < x.length; ++i) {
         int j = i + this.nextInt(x.length - i);
         MathExModified.swap(x, i, j);
      }

   }
}
