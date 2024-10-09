package smileModified;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MathExModified {
   private static final Logger logger = LoggerFactory.getLogger(MathExModified.class);
   private static final MathExModified.FPU fpu = new MathExModified.FPU();
   public static final double EPSILON;
   public static final float FLOAT_EPSILON;
   public static final int RADIX;
   public static final int DIGITS;
   public static final int FLOAT_DIGITS;
   public static final int ROUND_STYLE;
   public static final int MACHEP;
   public static final int FLOAT_MACHEP;
   public static final int NEGEP;
   public static final int FLOAT_NEGEP;
   private static final SecureRandom seedRNG;
   private static final long[] DEFAULT_SEEDS;
   private static int nextSeed;
   private static final ThreadLocal<RandomModified> random;
   private static final double LOG2;

   static {
      EPSILON = fpu.EPSILON;
      FLOAT_EPSILON = fpu.FLOAT_EPSILON;
      RADIX = fpu.RADIX;
      DIGITS = fpu.DIGITS;
      FLOAT_DIGITS = fpu.FLOAT_DIGITS;
      ROUND_STYLE = fpu.ROUND_STYLE;
      MACHEP = fpu.MACHEP;
      FLOAT_MACHEP = fpu.FLOAT_MACHEP;
      NEGEP = fpu.NEGEP;
      FLOAT_NEGEP = fpu.FLOAT_NEGEP;
      seedRNG = new SecureRandom();
      DEFAULT_SEEDS = new long[]{-4106602711295138952L, 7872020634117869514L, -1722503517109829138L, -3386820675908254116L, -1736715870046201019L, 3854590623768163340L, 4984519038350406438L, 831971085876758331L, 7131773007627236777L, -3609561992173376238L, -8759399602515137276L, 6192158663294695439L, -5656470009161653116L, -7984826214821970800L, -9113192788977418232L, -8979910231410580019L, -4619021025191354324L, -5082417586190057466L, -6554946940783144090L, -3610462176018822900L, 8959796931768911980L, -4251632352234989839L, 4922191169088134258L, -7282805902317830669L, 3869302430595840919L, 2517690626940415460L, 4056663221614950174L, 6429856319379397738L, 7298845553914383313L, 8179510284261677971L, 4282994537597585253L, 7300184601511783348L, 2596703774884172704L, 1089838915342514714L, 4323657609714862439L, 777826126579190548L, -1902743089794461140L, -2460431043688989882L, -3261708534465890932L, 4007861469505443778L, 8067600139237526646L, 5717273542173905853L, 2938568334013652889L, -2972203304739218305L, 6544901794394958069L, 7013723936758841449L, -4215598453287525312L, -1454689091401951913L, -5699280845313829011L, -9147984414924288540L, 5211986845656222459L, -1287642354429721659L, -1509334943513011620L, -9000043616528857326L, -2902817511399216571L, -742823064588229527L, -4937222449957498789L, -455679889440396397L, -6109470266907575296L, 5515435653880394376L, 5557224587324997029L, 8904139390487005840L, 6560726276686488510L, 6959949429287621625L, -6055733513105375650L, 5762016937143172332L, -9186652929482643329L, -1105816448554330895L, -8200377873547841359L, 9107473159863354619L, 3239950546973836199L, -8104429975176305012L, 3822949195131885242L, -5261390396129824777L, 9176101422921943895L, -5102541493993205418L, -1254710019595692814L, -6668066200971989826L, -2118519708589929546L, 5428466612765068681L, -6528627776941116598L, -5945449163896244174L, -3293290115918281076L, 6370347300411991230L, -7043881693953271167L, 8078993941165238212L, 6894961504641498099L, -8798276497942360228L, 2276271091333773917L, -7184141741385833013L, -4787502691178107481L, 1255068205351917608L, -8644146770023935609L, 5124094110137147339L, 4917075344795488880L, 3423242822219783102L, 1588924456880980404L, 8515495360312448868L, -5563691320675461929L, -2352238951654504517L, -7416919543420127888L, 631412478604690114L, 689144891258712875L, -9001615284848119152L, -6275065758899203088L, 8164387857252400515L, -4122060123604826739L, -2016541034210046261L, -7178335877193796678L, 3354303106860129181L, 5731595363486898779L, -2874315602397298018L, 5386746429707619069L, 9036622191596156315L, -7950190733284789459L, -5741691593792426169L, -8600462258998065159L, 5460142111961227035L, 276738899508534641L, 2358776514903881139L, -837649704945720257L, -3608906204977108245L, 2960825464614526243L, 7339056324843827739L, -5709958573878745135L, -5885403829221945248L, 6611935345917126768L, 2588814037559904539L};
      nextSeed = -1;
      random = new ThreadLocal<RandomModified>() {
         protected RandomModified initialValue() {
            synchronized(MathExModified.DEFAULT_SEEDS) {
               if (MathExModified.nextSeed < 0) {
                  MathExModified.nextSeed = 0;
                  return new RandomModified();
               } else if (MathExModified.nextSeed < MathExModified.DEFAULT_SEEDS.length) {
                  long[] var10002 = MathExModified.DEFAULT_SEEDS;
                  int var10003 = MathExModified.nextSeed;
                  MathExModified.nextSeed = var10003 + 1;
                  return new RandomModified(var10002[var10003]);
               } else {
                  return new RandomModified(MathExModified.generateSeed());
               }
            }
         }
      };
      LOG2 = Math.log(2.0D);
   }

   private MathExModified() {
   }

   public static double log2(double x) {
      return Math.log(x) / LOG2;
   }

   public static double log(double x) {
      double y = -690.7755D;
      if (x > 1.0E-300D) {
         y = Math.log(x);
      }

      return y;
   }

   public static double log1pe(double x) {
      double y = x;
      if (x <= 15.0D) {
         y = Math.log1p(Math.exp(x));
      }

      return y;
   }

   public static boolean isInt(float x) {
      return x == (float)Math.floor((double)x) && !Float.isInfinite(x);
   }

   public static boolean isInt(double x) {
      return x == Math.floor(x) && !Double.isInfinite(x);
   }

   public static boolean equals(double a, double b) {
      if (a == b) {
         return true;
      } else {
         double absa = Math.abs(a);
         double absb = Math.abs(b);
         return Math.abs(a - b) <= Math.min(absa, absb) * 2.220446049250313E-16D;
      }
   }

   public static double sigmoid(double x) {
      x = Math.max(-36.0D, Math.min(x, 36.0D));
      return 1.0D / (1.0D + Math.exp(-x));
   }

   public static double pow2(double x) {
      return x * x;
   }

   public static boolean isPower2(int x) {
      return x > 0 && (x & x - 1) == 0;
   }

   public static boolean isProbablePrime(long n, int k) {
      return isProbablePrime(n, k, (RandomModified)random.get());
   }

   private static boolean isProbablePrime(long n, int k, RandomModified rng) {
      if (n > 1L && n != 4L) {
         if (n <= 3L) {
            return true;
         } else {
            int s = 0;

            long d;
            for(d = n - 1L; d % 2L == 0L; d /= 2L) {
               ++s;
            }

            for(int i = 0; i < k; ++i) {
               long a = 2L + rng.nextLong() % (n - 4L);
               long x = power(a, d, n);
               if (x != 1L && x != n - 1L) {
                  int r;
                  for(r = 0; r < s; ++r) {
                     x = x * x % n;
                     if (x == 1L) {
                        return false;
                     }

                     if (x == n - 1L) {
                        break;
                     }
                  }

                  if (r == s) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static long power(long x, long y, long p) {
      long res = 1L;

      for(x %= p; y > 0L; x = x * x % p) {
         if ((y & 1L) == 1L) {
            res = res * x % p;
         }

         y >>= 1;
      }

      return res;
   }

   public static double round(double x, int decimal) {
      return decimal < 0 ? (double)Math.round(x / Math.pow(10.0D, (double)(-decimal))) * Math.pow(10.0D, (double)(-decimal)) : (double)Math.round(x * Math.pow(10.0D, (double)decimal)) / Math.pow(10.0D, (double)decimal);
   }

   public static double factorial(int n) {
      if (n < 0) {
         throw new IllegalArgumentException("n has to be non-negative.");
      } else {
         double f = 1.0D;

         for(int i = 2; i <= n; ++i) {
            f *= (double)i;
         }

         return f;
      }
   }

   public static double lfactorial(int n) {
      if (n < 0) {
         throw new IllegalArgumentException(String.format("n has to be non-negative: %d", n));
      } else {
         double f = 0.0D;

         for(int i = 2; i <= n; ++i) {
            f += Math.log((double)i);
         }

         return f;
      }
   }

   public static double choose(int n, int k) {
      if (n >= 0 && k >= 0) {
         return n < k ? 0.0D : Math.floor(0.5D + Math.exp(lchoose(n, k)));
      } else {
         throw new IllegalArgumentException(String.format("Invalid n = %d, k = %d", n, k));
      }
   }

   public static double lchoose(int n, int k) {
      if (k >= 0 && k <= n) {
         return lfactorial(n) - lfactorial(k) - lfactorial(n - k);
      } else {
         throw new IllegalArgumentException(String.format("Invalid n = %d, k = %d", n, k));
      }
   }

   public static long generateSeed() {
      byte[] bytes = generateSeed(8);
      long seed = 0L;

      for(int i = 0; i < 8; ++i) {
         seed <<= 8;
         seed |= (long)(bytes[i] & 255);
      }

      return seed;
   }

   public static byte[] generateSeed(int numBytes) {
      synchronized(seedRNG) {
         return seedRNG.generateSeed(numBytes);
      }
   }

   public static LongStream seeds() {
      return LongStream.generate(MathExModified::generateSeed).sequential();
   }

   public static void setSeed(long seed) {
      ((RandomModified)random.get()).setSeed(seed);
   }

   public static long probablePrime(long n, int k) {
      return probablePrime(n, k, (RandomModified)random.get());
   }

   private static long probablePrime(long n, int k, RandomModified rng) {
      long seed = n + (long)rng.nextInt(899999963);

      for(int i = 0; i < 4096 && !isProbablePrime(seed, k, rng); ++i) {
         seed = n + (long)rng.nextInt(899999963);
      }

      return seed;
   }

   public static int random(double[] prob) {
      int[] ans = random(prob, 1);
      return ans[0];
   }

   public static int[] random(double[] prob, int n) {
      double[] q = new double[prob.length];

      for(int i = 0; i < prob.length; ++i) {
         q[i] = prob[i] * (double)prob.length;
      }

      int[] a = new int[prob.length];

      for(int i = 0; i < prob.length; a[i] = i++) {
      }

      int[] HL = new int[prob.length];
      int head = 0;
      int tail = prob.length - 1;

      int i;
      for(i = 0; i < prob.length; ++i) {
         if (q[i] >= 1.0D) {
            HL[head++] = i;
         } else {
            HL[tail--] = i;
         }
      }

      int i;
      while(head != 0 && tail != prob.length - 1) {
         i = HL[tail + 1];
         i = HL[head - 1];
         a[i] = i;
         q[i] += q[i] - 1.0D;
         ++tail;
         if (q[i] < 1.0D) {
            HL[tail--] = i;
            --head;
         }
      }

      int[] ans = new int[n];

      for(i = 0; i < n; ++i) {
         double rU = random() * (double)prob.length;
         int k = (int)rU;
         rU -= (double)k;
         if (rU < q[k]) {
            ans[i] = k;
         } else {
            ans[i] = a[k];
         }
      }

      return ans;
   }

   public static double random() {
      return ((RandomModified)random.get()).nextDouble();
   }

   public static double[] random(int n) {
      double[] x = new double[n];
      ((RandomModified)random.get()).nextDoubles(x);
      return x;
   }

   public static double random(double lo, double hi) {
      return ((RandomModified)random.get()).nextDouble(lo, hi);
   }

   public static double[] random(double lo, double hi, int n) {
      double[] x = new double[n];
      ((RandomModified)random.get()).nextDoubles(x, lo, hi);
      return x;
   }

   public static long randomLong() {
      return ((RandomModified)random.get()).nextLong();
   }

   public static int randomInt(int n) {
      return ((RandomModified)random.get()).nextInt(n);
   }

   public static int randomInt(int lo, int hi) {
      int w = hi - lo;
      return lo + ((RandomModified)random.get()).nextInt(w);
   }

   public static int[] permutate(int n) {
      return ((RandomModified)random.get()).permutate(n);
   }

   public static void permutate(int[] x) {
      ((RandomModified)random.get()).permutate(x);
   }

   public static void permutate(float[] x) {
      ((RandomModified)random.get()).permutate(x);
   }

   public static void permutate(double[] x) {
      ((RandomModified)random.get()).permutate(x);
   }

   public static void permutate(Object[] x) {
      ((RandomModified)random.get()).permutate(x);
   }

   public static int softmax(double[] posteriori) {
      return softmax(posteriori, posteriori.length);
   }

   public static int softmax(double[] x, int k) {
      int y = -1;
      double max = Double.NEGATIVE_INFINITY;

      for(int i = 0; i < k; ++i) {
         if (x[i] > max) {
            max = x[i];
            y = i;
         }
      }

      double Z = 0.0D;

      int i;
      for(i = 0; i < k; ++i) {
         double out = Math.exp(x[i] - max);
         x[i] = out;
         Z += out;
      }

      for(i = 0; i < k; ++i) {
         x[i] /= Z;
      }

      return y;
   }

   public static int[] c(int... x) {
      return x;
   }

   public static float[] c(float... x) {
      return x;
   }

   public static double[] c(double... x) {
      return x;
   }

   public static String[] c(String... x) {
      return x;
   }

   public static int[] c(int[]... list) {
      int n = 0;
      int[][] var5 = list;
      int var4 = list.length;

      int[] y;
      int pos;
      for(pos = 0; pos < var4; ++pos) {
         y = var5[pos];
         n += y.length;
      }

      y = new int[n];
      pos = 0;
      int[][] var7 = list;
      int var6 = list.length;

      for(int var9 = 0; var9 < var6; ++var9) {
         int[] x = var7[var9];
         System.arraycopy(x, 0, y, pos, x.length);
         pos += x.length;
      }

      return y;
   }

   public static float[] c(float[]... list) {
      int n = 0;
      float[][] var5 = list;
      int var4 = list.length;

      float[] y;
      int pos;
      for(pos = 0; pos < var4; ++pos) {
         y = var5[pos];
         n += y.length;
      }

      y = new float[n];
      pos = 0;
      float[][] var7 = list;
      int var6 = list.length;

      for(int var9 = 0; var9 < var6; ++var9) {
         float[] x = var7[var9];
         System.arraycopy(x, 0, y, pos, x.length);
         pos += x.length;
      }

      return y;
   }

   public static double[] c(double[]... list) {
      int n = 0;
      double[][] var5 = list;
      int var4 = list.length;

      double[] y;
      int pos;
      for(pos = 0; pos < var4; ++pos) {
         y = var5[pos];
         n += y.length;
      }

      y = new double[n];
      pos = 0;
      double[][] var7 = list;
      int var6 = list.length;

      for(int var9 = 0; var9 < var6; ++var9) {
         double[] x = var7[var9];
         System.arraycopy(x, 0, y, pos, x.length);
         pos += x.length;
      }

      return y;
   }

   public static String[] c(String[]... list) {
      int n = 0;
      String[][] var5 = list;
      int var4 = list.length;

      String[] y;
      int pos;
      for(pos = 0; pos < var4; ++pos) {
         y = var5[pos];
         n += y.length;
      }

      y = new String[n];
      pos = 0;
      String[][] var7 = list;
      int var6 = list.length;

      for(int var9 = 0; var9 < var6; ++var9) {
         String[] x = var7[var9];
         System.arraycopy(x, 0, y, pos, x.length);
         pos += x.length;
      }

      return y;
   }

   public static int[] cbind(int[]... x) {
      return c(x);
   }

   public static float[] cbind(float[]... x) {
      return c(x);
   }

   public static double[] cbind(double[]... x) {
      return c(x);
   }

   public static String[] cbind(String[]... x) {
      return c(x);
   }

   public static int[][] rbind(int[]... x) {
      return x;
   }

   public static float[][] rbind(float[]... x) {
      return x;
   }

   public static double[][] rbind(double[]... x) {
      return x;
   }

   public static String[][] rbind(String[]... x) {
      return x;
   }

   public static <E> E[] slice(E[] data, int[] index) {
      int n = index.length;
      Object[] x = (Object[])Array.newInstance(data.getClass().getComponentType(), n);

      for(int i = 0; i < n; ++i) {
         x[i] = data[index[i]];
      }

      return x;
   }

   public static int[] slice(int[] data, int[] index) {
      int n = index.length;
      int[] x = new int[n];

      for(int i = 0; i < n; ++i) {
         x[i] = data[index[i]];
      }

      return x;
   }

   public static float[] slice(float[] data, int[] index) {
      int n = index.length;
      float[] x = new float[n];

      for(int i = 0; i < n; ++i) {
         x[i] = data[index[i]];
      }

      return x;
   }

   public static double[] slice(double[] data, int[] index) {
      int n = index.length;
      double[] x = new double[n];

      for(int i = 0; i < n; ++i) {
         x[i] = data[index[i]];
      }

      return x;
   }

   public static boolean contains(double[][] polygon, double[] point) {
      return contains(polygon, point[0], point[1]);
   }

   public static boolean contains(double[][] polygon, double x, double y) {
      if (polygon.length <= 2) {
         return false;
      } else {
         int hits = 0;
         int n = polygon.length;
         double lastx = polygon[n - 1][0];
         double lasty = polygon[n - 1][1];

         for(int i = 0; i < n; ++i) {
            double curx = polygon[i][0];
            double cury = polygon[i][1];
            if (cury != lasty) {
               label75: {
                  double leftx;
                  if (curx < lastx) {
                     if (x >= lastx) {
                        break label75;
                     }

                     leftx = curx;
                  } else {
                     if (x >= curx) {
                        break label75;
                     }

                     leftx = lastx;
                  }

                  double test1;
                  double test2;
                  if (cury < lasty) {
                     if (y < cury || y >= lasty) {
                        break label75;
                     }

                     if (x < leftx) {
                        ++hits;
                        break label75;
                     }

                     test1 = x - curx;
                     test2 = y - cury;
                  } else {
                     if (y < lasty || y >= cury) {
                        break label75;
                     }

                     if (x < leftx) {
                        ++hits;
                        break label75;
                     }

                     test1 = x - lastx;
                     test2 = y - lasty;
                  }

                  if (test1 < test2 / (lasty - cury) * (lastx - curx)) {
                     ++hits;
                  }
               }
            }

            lastx = curx;
            lasty = cury;
         }

         if ((hits & 1) != 0) {
            return true;
         } else {
            return false;
         }
      }
   }

   public static int[] omit(int[] a, int value) {
      int n = 0;
      int[] var6 = a;
      int x = a.length;

      int x;
      for(int var4 = 0; var4 < x; ++var4) {
         x = var6[var4];
         if (x != value) {
            ++n;
         }
      }

      x = 0;
      int[] b = new int[n];
      int[] var8 = a;
      int var7 = a.length;

      for(int var10 = 0; var10 < var7; ++var10) {
         x = var8[var10];
         if (x != value) {
            b[x++] = x;
         }
      }

      return b;
   }

   public static float[] omit(float[] a, float value) {
      int n = 0;
      float[] var6 = a;
      int var5 = a.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         float x = var6[var4];
         if (x != value) {
            ++n;
         }
      }

      int i = 0;
      float[] b = new float[n];
      float[] var8 = a;
      int var7 = a.length;

      for(int var12 = 0; var12 < var7; ++var12) {
         float x = var8[var12];
         if (x != value) {
            b[i++] = x;
         }
      }

      return b;
   }

   public static double[] omit(double[] a, double value) {
      int n = 0;
      double[] var8 = a;
      int var7 = a.length;

      for(int var6 = 0; var6 < var7; ++var6) {
         double x = var8[var6];
         if (x != value) {
            ++n;
         }
      }

      int i = 0;
      double[] b = new double[n];
      double[] var10 = a;
      int var9 = a.length;

      for(int var13 = 0; var13 < var9; ++var13) {
         double x = var10[var13];
         if (x != value) {
            b[i++] = x;
         }
      }

      return b;
   }

   public static float[] omitNaN(float[] a) {
      int n = 0;
      float[] var5 = a;
      int var4 = a.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         float x = var5[var3];
         if (!Float.isNaN(x)) {
            ++n;
         }
      }

      int i = 0;
      float[] b = new float[n];
      float[] var7 = a;
      int var6 = a.length;

      for(int var11 = 0; var11 < var6; ++var11) {
         float x = var7[var11];
         if (!Float.isNaN(x)) {
            b[i++] = x;
         }
      }

      return b;
   }

   public static double[] omitNaN(double[] a) {
      int n = 0;
      double[] var6 = a;
      int var5 = a.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         double x = var6[var4];
         if (!Double.isNaN(x)) {
            ++n;
         }
      }

      int i = 0;
      double[] b = new double[n];
      double[] var8 = a;
      int var7 = a.length;

      for(int var11 = 0; var11 < var7; ++var11) {
         double x = var8[var11];
         if (!Double.isNaN(x)) {
            b[i++] = x;
         }
      }

      return b;
   }

   public static void reverse(int[] a) {
      int i = 0;
      int j = a.length - 1;

      while(i < j) {
         SortModified.swap(a, i++, j--);
      }

   }

   public static void reverse(float[] a) {
      int i = 0;
      int j = a.length - 1;

      while(i < j) {
         SortModified.swap(a, i++, j--);
      }

   }

   public static void reverse(double[] a) {
      int i = 0;
      int j = a.length - 1;

      while(i < j) {
         SortModified.swap(a, i++, j--);
      }

   }

   public static <T> void reverse(T[] a) {
      int i = 0;
      int j = a.length - 1;

      while(i < j) {
         SortModified.swap(a, i++, j--);
      }

   }

   public static int mode(int[] a) {
      Arrays.sort(a);
      int mode = -1;
      int count = 0;
      int currentValue = a[0];
      int currentCount = 1;

      for(int i = 1; i < a.length; ++i) {
         if (a[i] != currentValue) {
            if (currentCount > count) {
               mode = currentValue;
               count = currentCount;
            }

            currentValue = a[i];
            currentCount = 1;
         } else {
            ++currentCount;
         }
      }

      if (currentCount > count) {
         mode = currentValue;
      }

      return mode;
   }

   public static int min(int a, int b, int c) {
      return Math.min(Math.min(a, b), c);
   }

   public static float min(float a, float b, float c) {
      return Math.min(Math.min(a, b), c);
   }

   public static double min(double a, double b, double c) {
      return Math.min(Math.min(a, b), c);
   }

   public static int min(int a, int b, int c, int d) {
      return Math.min(Math.min(Math.min(a, b), c), d);
   }

   public static float min(float a, float b, float c, float d) {
      return Math.min(Math.min(Math.min(a, b), c), d);
   }

   public static double min(double a, double b, double c, double d) {
      return Math.min(Math.min(Math.min(a, b), c), d);
   }

   public static int max(int a, int b, int c) {
      return Math.max(Math.max(a, b), c);
   }

   public static float max(float a, float b, float c) {
      return Math.max(Math.max(a, b), c);
   }

   public static double max(double a, double b, double c) {
      return Math.max(Math.max(a, b), c);
   }

   public static int max(int a, int b, int c, int d) {
      return Math.max(Math.max(Math.max(a, b), c), d);
   }

   public static float max(float a, float b, float c, float d) {
      return Math.max(Math.max(Math.max(a, b), c), d);
   }

   public static double max(double a, double b, double c, double d) {
      return Math.max(Math.max(Math.max(a, b), c), d);
   }

   public static int min(int[] x) {
      int min = x[0];
      int[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int n = var5[var3];
         if (n < min) {
            min = n;
         }
      }

      return min;
   }

   public static float min(float[] x) {
      float min = Float.POSITIVE_INFINITY;
      float[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         float n = var5[var3];
         if (n < min) {
            min = n;
         }
      }

      return min;
   }

   public static double min(double[] x) {
      double min = Double.POSITIVE_INFINITY;
      double[] var7 = x;
      int var6 = x.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         double n = var7[var5];
         if (n < min) {
            min = n;
         }
      }

      return min;
   }

   public static int whichMin(int[] x) {
      int min = x[0];
      int which = 0;

      for(int i = 1; i < x.length; ++i) {
         if (x[i] < min) {
            min = x[i];
            which = i;
         }
      }

      return which;
   }

   public static int whichMin(float[] x) {
      float min = Float.POSITIVE_INFINITY;
      int which = 0;

      for(int i = 0; i < x.length; ++i) {
         if (x[i] < min) {
            min = x[i];
            which = i;
         }
      }

      return which;
   }

   public static int whichMin(double[] x) {
      double min = Double.POSITIVE_INFINITY;
      int which = 0;

      for(int i = 0; i < x.length; ++i) {
         if (x[i] < min) {
            min = x[i];
            which = i;
         }
      }

      return which;
   }

   public static int max(int[] x) {
      int max = x[0];
      int[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int n = var5[var3];
         if (n > max) {
            max = n;
         }
      }

      return max;
   }

   public static float max(float[] x) {
      float max = Float.NEGATIVE_INFINITY;
      float[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         float n = var5[var3];
         if (n > max) {
            max = n;
         }
      }

      return max;
   }

   public static double max(double[] x) {
      double max = Double.NEGATIVE_INFINITY;
      double[] var7 = x;
      int var6 = x.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         double n = var7[var5];
         if (n > max) {
            max = n;
         }
      }

      return max;
   }

   public static int whichMax(int[] x) {
      int max = x[0];
      int which = 0;

      for(int i = 1; i < x.length; ++i) {
         if (x[i] > max) {
            max = x[i];
            which = i;
         }
      }

      return which;
   }

   public static int whichMax(float[] x) {
      float max = Float.NEGATIVE_INFINITY;
      int which = 0;

      for(int i = 0; i < x.length; ++i) {
         if (x[i] > max) {
            max = x[i];
            which = i;
         }
      }

      return which;
   }

   public static int whichMax(double[] x) {
      double max = Double.NEGATIVE_INFINITY;
      int which = 0;

      for(int i = 0; i < x.length; ++i) {
         if (x[i] > max) {
            max = x[i];
            which = i;
         }
      }

      return which;
   }

   public static int min(int[][] matrix) {
      int min = matrix[0][0];
      int[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int[] x = var5[var3];
         int[] var9 = x;
         int var8 = x.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            int y = var9[var7];
            if (min > y) {
               min = y;
            }
         }
      }

      return min;
   }

   public static double min(double[][] matrix) {
      double min = Double.POSITIVE_INFINITY;
      double[][] var6 = matrix;
      int var5 = matrix.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         double[] x = var6[var4];
         double[] var11 = x;
         int var10 = x.length;

         for(int var9 = 0; var9 < var10; ++var9) {
            double y = var11[var9];
            if (min > y) {
               min = y;
            }
         }
      }

      return min;
   }

   public static int max(int[][] matrix) {
      int max = matrix[0][0];
      int[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int[] x = var5[var3];
         int[] var9 = x;
         int var8 = x.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            int y = var9[var7];
            if (max < y) {
               max = y;
            }
         }
      }

      return max;
   }

   public static double max(double[][] matrix) {
      double max = Double.NEGATIVE_INFINITY;
      double[][] var6 = matrix;
      int var5 = matrix.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         double[] x = var6[var4];
         double[] var11 = x;
         int var10 = x.length;

         for(int var9 = 0; var9 < var10; ++var9) {
            double y = var11[var9];
            if (max < y) {
               max = y;
            }
         }
      }

      return max;
   }

   public static IntPair whichMin(double[][] matrix) {
      double min = Double.POSITIVE_INFINITY;
      int whichRow = 0;
      int whichCol = 0;

      for(int i = 0; i < matrix.length; ++i) {
         for(int j = 0; j < matrix[i].length; ++j) {
            if (matrix[i][j] < min) {
               min = matrix[i][j];
               whichRow = i;
               whichCol = j;
            }
         }
      }

      return new IntPair(whichRow, whichCol);
   }

   public static IntPair whichMax(double[][] matrix) {
      double max = Double.NEGATIVE_INFINITY;
      int whichRow = 0;
      int whichCol = 0;

      for(int i = 0; i < matrix.length; ++i) {
         for(int j = 0; j < matrix[i].length; ++j) {
            if (matrix[i][j] > max) {
               max = matrix[i][j];
               whichRow = i;
               whichCol = j;
            }
         }
      }

      return new IntPair(whichRow, whichCol);
   }

   public static double[][] transpose(double[][] matrix) {
      int m = matrix.length;
      int n = matrix[0].length;
      double[][] t = new double[n][m];

      for(int i = 0; i < m; ++i) {
         for(int j = 0; j < n; ++j) {
            t[j][i] = matrix[i][j];
         }
      }

      return t;
   }

   public static int[] rowMin(int[][] matrix) {
      int[] x = new int[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = min(matrix[i]);
      }

      return x;
   }

   public static int[] rowMax(int[][] matrix) {
      int[] x = new int[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = max(matrix[i]);
      }

      return x;
   }

   public static long[] rowSums(int[][] matrix) {
      long[] x = new long[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = sum(matrix[i]);
      }

      return x;
   }

   public static double[] rowMin(double[][] matrix) {
      double[] x = new double[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = min(matrix[i]);
      }

      return x;
   }

   public static double[] rowMax(double[][] matrix) {
      double[] x = new double[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = max(matrix[i]);
      }

      return x;
   }

   public static double[] rowSums(double[][] matrix) {
      double[] x = new double[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = sum(matrix[i]);
      }

      return x;
   }

   public static double[] rowMeans(double[][] matrix) {
      double[] x = new double[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = mean(matrix[i]);
      }

      return x;
   }

   public static double[] rowSds(double[][] matrix) {
      double[] x = new double[matrix.length];

      for(int i = 0; i < x.length; ++i) {
         x[i] = sd(matrix[i]);
      }

      return x;
   }

   public static int[] colMin(int[][] matrix) {
      int[] x = new int[matrix[0].length];
      Arrays.fill(x, Integer.MAX_VALUE);
      int[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int[] row = var5[var3];

         for(int j = 0; j < x.length; ++j) {
            if (x[j] > row[j]) {
               x[j] = row[j];
            }
         }
      }

      return x;
   }

   public static int[] colMax(int[][] matrix) {
      int[] x = new int[matrix[0].length];
      Arrays.fill(x, Integer.MIN_VALUE);
      int[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int[] row = var5[var3];

         for(int j = 0; j < x.length; ++j) {
            if (x[j] < row[j]) {
               x[j] = row[j];
            }
         }
      }

      return x;
   }

   public static long[] colSums(int[][] matrix) {
      long[] x = new long[matrix[0].length];
      int[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int[] row = var5[var3];

         for(int j = 0; j < x.length; ++j) {
            x[j] += (long)row[j];
         }
      }

      return x;
   }

   public static double[] colMin(double[][] matrix) {
      double[] x = new double[matrix[0].length];
      Arrays.fill(x, Double.POSITIVE_INFINITY);
      double[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         double[] row = var5[var3];

         for(int j = 0; j < x.length; ++j) {
            if (x[j] > row[j]) {
               x[j] = row[j];
            }
         }
      }

      return x;
   }

   public static double[] colMax(double[][] matrix) {
      double[] x = new double[matrix[0].length];
      Arrays.fill(x, Double.NEGATIVE_INFINITY);
      double[][] var5 = matrix;
      int var4 = matrix.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         double[] row = var5[var3];

         for(int j = 0; j < x.length; ++j) {
            if (x[j] < row[j]) {
               x[j] = row[j];
            }
         }
      }

      return x;
   }

   public static double[] colSums(double[][] matrix) {
      double[] x = (double[])matrix[0].clone();

      for(int i = 1; i < matrix.length; ++i) {
         for(int j = 0; j < x.length; ++j) {
            x[j] += matrix[i][j];
         }
      }

      return x;
   }

   public static double[] colMeans(double[][] matrix) {
      double[] x = (double[])matrix[0].clone();

      for(int i = 1; i < matrix.length; ++i) {
         for(int j = 0; j < x.length; ++j) {
            x[j] += matrix[i][j];
         }
      }

      scale(1.0D / (double)matrix.length, x);
      return x;
   }

   public static double[] colSds(double[][] matrix) {
      if (matrix.length < 2) {
         throw new IllegalArgumentException("matrix length is less than 2.");
      } else {
         int p = matrix[0].length;
         double[] sum = new double[p];
         double[] sumsq = new double[p];
         double[][] var7 = matrix;
         int var6 = matrix.length;

         int i;
         for(i = 0; i < var6; ++i) {
            double[] row = var7[i];

            for(int i = 0; i < p; ++i) {
               sum[i] += row[i];
               sumsq[i] += row[i] * row[i];
            }
         }

         int n = matrix.length - 1;

         for(i = 0; i < p; ++i) {
            sumsq[i] = Math.sqrt(sumsq[i] / (double)n - sum[i] / (double)matrix.length * (sum[i] / (double)n));
         }

         return sumsq;
      }
   }

   public static int sum(byte[] x) {
      int sum = 0;
      byte[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         int n = var5[var3];
         sum += n;
      }

      return sum;
   }

   public static long sum(int[] x) {
      long sum = 0L;
      int[] var6 = x;
      int var5 = x.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         int n = var6[var4];
         sum += (long)n;
      }

      return (long)((int)sum);
   }

   public static double sum(float[] x) {
      double sum = 0.0D;
      float[] var6 = x;
      int var5 = x.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         float n = var6[var4];
         sum += (double)n;
      }

      return sum;
   }

   public static double sum(double[] x) {
      double sum = 0.0D;
      double[] var7 = x;
      int var6 = x.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         double n = var7[var5];
         sum += n;
      }

      return sum;
   }

   public static int median(int[] x) {
      return QuickSelectModified.median(x);
   }

   public static float median(float[] x) {
      return QuickSelectModified.median(x);
   }

   public static double median(double[] x) {
      return QuickSelectModified.median(x);
   }

   public static <T extends Comparable<? super T>> T median(T[] x) {
      return QuickSelectModified.median(x);
   }

   public static int q1(int[] x) {
      return QuickSelectModified.q1(x);
   }

   public static float q1(float[] x) {
      return QuickSelectModified.q1(x);
   }

   public static double q1(double[] x) {
      return QuickSelectModified.q1(x);
   }

   public static <T extends Comparable<? super T>> T q1(T[] x) {
      return QuickSelectModified.q1(x);
   }

   public static int q3(int[] x) {
      return QuickSelectModified.q3(x);
   }

   public static float q3(float[] x) {
      return QuickSelectModified.q3(x);
   }

   public static double q3(double[] x) {
      return QuickSelectModified.q3(x);
   }

   public static <T extends Comparable<? super T>> T q3(T[] x) {
      return QuickSelectModified.q3(x);
   }

   public static double mean(int[] x) {
      return (double)sum(x) / (double)x.length;
   }

   public static double mean(float[] x) {
      return sum(x) / (double)x.length;
   }

   public static double mean(double[] x) {
      return sum(x) / (double)x.length;
   }

   public static double var(int[] x) {
      if (x.length < 2) {
         throw new IllegalArgumentException("Array length is less than 2.");
      } else {
         double sum = 0.0D;
         double sumsq = 0.0D;
         int[] var8 = x;
         int var7 = x.length;

         int n;
         for(int var6 = 0; var6 < var7; ++var6) {
            n = var8[var6];
            sum += (double)n;
            sumsq += (double)(n * n);
         }

         n = x.length - 1;
         return sumsq / (double)n - sum / (double)x.length * (sum / (double)n);
      }
   }

   public static double var(float[] x) {
      if (x.length < 2) {
         throw new IllegalArgumentException("Array length is less than 2.");
      } else {
         double sum = 0.0D;
         double sumsq = 0.0D;
         float[] var8 = x;
         int var7 = x.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            float xi = var8[var6];
            sum += (double)xi;
            sumsq += (double)(xi * xi);
         }

         int n = x.length - 1;
         return sumsq / (double)n - sum / (double)x.length * (sum / (double)n);
      }
   }

   public static double var(double[] x) {
      if (x.length < 2) {
         throw new IllegalArgumentException("Array length is less than 2.");
      } else {
         double sum = 0.0D;
         double sumsq = 0.0D;
         double[] var9 = x;
         int var8 = x.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            double xi = var9[var7];
            sum += xi;
            sumsq += xi * xi;
         }

         int n = x.length - 1;
         return sumsq / (double)n - sum / (double)x.length * (sum / (double)n);
      }
   }

   public static double sd(int[] x) {
      return Math.sqrt(var(x));
   }

   public static double sd(float[] x) {
      return Math.sqrt(var(x));
   }

   public static double sd(double[] x) {
      return Math.sqrt(var(x));
   }

   public static double mad(int[] x) {
      int m = median(x);

      for(int i = 0; i < x.length; ++i) {
         x[i] = Math.abs(x[i] - m);
      }

      return (double)median(x);
   }

   public static double mad(float[] x) {
      float m = median(x);

      for(int i = 0; i < x.length; ++i) {
         x[i] = Math.abs(x[i] - m);
      }

      return (double)median(x);
   }

   public static double mad(double[] x) {
      double m = median(x);

      for(int i = 0; i < x.length; ++i) {
         x[i] = Math.abs(x[i] - m);
      }

      return median(x);
   }

   public static double distance(int[] x, int[] y) {
      return Math.sqrt(squaredDistance(x, y));
   }

   public static double distance(float[] x, float[] y) {
      return Math.sqrt(squaredDistance(x, y));
   }

   public static double distance(double[] x, double[] y) {
      return Math.sqrt(squaredDistance(x, y));
   }

   public static double distance(SparseArrayModified x, SparseArrayModified y) {
      return Math.sqrt(squaredDistance(x, y));
   }

   public static double squaredDistance(int[] x, int[] y) {
      double d = 0.0D;
      int p1 = 0;
      int p2 = 0;

      while(p1 < x.length && p2 < y.length) {
         int i1 = x[p1];
         int i2 = y[p2];
         if (i1 == i2) {
            ++p1;
            ++p2;
         } else if (i1 > i2) {
            ++d;
            ++p2;
         } else {
            ++d;
            ++p1;
         }
      }

      d += (double)(x.length - p1);
      d += (double)(y.length - p2);
      return d;
   }

   public static double squaredDistance(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         double sum;
         double d1;
         double d2;
         switch(x.length) {
         case 2:
            sum = (double)x[0] - (double)y[0];
            d1 = (double)x[1] - (double)y[1];
            return sum * sum + d1 * d1;
         case 3:
            sum = (double)x[0] - (double)y[0];
            d1 = (double)x[1] - (double)y[1];
            d2 = (double)x[2] - (double)y[2];
            return sum * sum + d1 * d1 + d2 * d2;
         case 4:
            sum = (double)x[0] - (double)y[0];
            d1 = (double)x[1] - (double)y[1];
            d2 = (double)x[2] - (double)y[2];
            double d3 = (double)x[3] - (double)y[3];
            return sum * sum + d1 * d1 + d2 * d2 + d3 * d3;
         default:
            sum = 0.0D;

            for(int i = 0; i < x.length; ++i) {
               double d = (double)x[i] - (double)y[i];
               sum += d * d;
            }

            return sum;
         }
      }
   }

   public static double squaredDistance(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         double sum;
         double d1;
         double d2;
         switch(x.length) {
         case 2:
            sum = x[0] - y[0];
            d1 = x[1] - y[1];
            return sum * sum + d1 * d1;
         case 3:
            sum = x[0] - y[0];
            d1 = x[1] - y[1];
            d2 = x[2] - y[2];
            return sum * sum + d1 * d1 + d2 * d2;
         case 4:
            sum = x[0] - y[0];
            d1 = x[1] - y[1];
            d2 = x[2] - y[2];
            double d3 = x[3] - y[3];
            return sum * sum + d1 * d1 + d2 * d2 + d3 * d3;
         default:
            sum = 0.0D;

            for(int i = 0; i < x.length; ++i) {
               double d = x[i] - y[i];
               sum += d * d;
            }

            return sum;
         }
      }
   }

   public static double squaredDistance(SparseArrayModified x, SparseArrayModified y) {
      Iterator<SparseArrayModified.Entry> it1 = x.iterator();
      Iterator<SparseArrayModified.Entry> it2 = y.iterator();
      SparseArrayModified.Entry e1 = it1.hasNext() ? (SparseArrayModified.Entry)it1.next() : null;
      SparseArrayModified.Entry e2 = it2.hasNext() ? (SparseArrayModified.Entry)it2.next() : null;
      double sum = 0.0D;

      while(e1 != null && e2 != null) {
         if (e1.i == e2.i) {
            sum += pow2(e1.x - e2.x);
            e1 = it1.hasNext() ? (SparseArrayModified.Entry)it1.next() : null;
            e2 = it2.hasNext() ? (SparseArrayModified.Entry)it2.next() : null;
         } else if (e1.i > e2.i) {
            sum += pow2(e2.x);
            e2 = it2.hasNext() ? (SparseArrayModified.Entry)it2.next() : null;
         } else {
            sum += pow2(e1.x);
            e1 = it1.hasNext() ? (SparseArrayModified.Entry)it1.next() : null;
         }
      }

      double d;
      while(it1.hasNext()) {
         d = ((SparseArrayModified.Entry)it1.next()).x;
         sum += d * d;
      }

      while(it2.hasNext()) {
         d = ((SparseArrayModified.Entry)it2.next()).x;
         sum += d * d;
      }

      return sum;
   }

   public static double squaredDistanceWithMissingValues(double[] x, double[] y) {
      int n = x.length;
      int m = 0;
      double dist = 0.0D;

      for(int i = 0; i < n; ++i) {
         if (!Double.isNaN(x[i]) && !Double.isNaN(y[i])) {
            ++m;
            double d = x[i] - y[i];
            dist += d * d;
         }
      }

      if (m == 0) {
         dist = Double.MAX_VALUE;
      } else {
         dist = (double)n * dist / (double)m;
      }

      return dist;
   }

   public static MatrixModified pdist(int[][] x) {
      return pdist(x, false);
   }

   public static MatrixModified pdist(int[][] x, boolean squared) {
      int n = x.length;
      double[][] dist = new double[n][n];
      pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
      return MatrixModified.of(dist);
   }

   public static MatrixModified pdist(float[][] x) {
      return pdist(x, false);
   }

   public static MatrixModified pdist(float[][] x, boolean squared) {
      int n = x.length;
      double[][] dist = new double[n][n];
      pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
      return MatrixModified.of(dist);
   }

   public static MatrixModified pdist(double[][] x) {
      return pdist(x, false);
   }

   public static MatrixModified pdist(double[][] x, boolean squared) {
      int n = x.length;
      double[][] dist = new double[n][n];
      pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
      return MatrixModified.of(dist);
   }

   public static MatrixModified pdist(SparseArrayModified[] x) {
      return pdist(x, false);
   }

   public static MatrixModified pdist(SparseArrayModified[] x, boolean squared) {
      int n = x.length;
      double[][] dist = new double[n][n];
      pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
      return MatrixModified.of(dist);
   }

   public static <T> void pdist(T[] x, double[][] d, DistanceModified<T> distance) {
      int n = x.length;
      if (d[0].length < n) {
         IntStream.range(0, n).parallel().forEach((i) -> {
            T xi = x[i];
            double[] di = d[i];

            for(int j = 0; j < i; ++j) {
               di[j] = distance.d(xi, x[j]);
            }

         });
      } else {
         IntStream.range(0, n).parallel().forEach((i) -> {
            T xi = x[i];
            double[] di = d[i];

            for(int j = 0; j < n; ++j) {
               di[j] = distance.d(xi, x[j]);
            }

         });
      }

   }

   public static double entropy(double[] p) {
      double h = 0.0D;
      double[] var7 = p;
      int var6 = p.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         double pi = var7[var5];
         if (pi > 0.0D) {
            h -= pi * Math.log(pi);
         }
      }

      return h;
   }

   public static double KullbackLeiblerDivergence(double[] p, double[] q) {
      boolean intersection = false;
      double kl = 0.0D;

      for(int i = 0; i < p.length; ++i) {
         if (p[i] != 0.0D && q[i] != 0.0D) {
            intersection = true;
            kl += p[i] * Math.log(p[i] / q[i]);
         }
      }

      if (intersection) {
         return kl;
      } else {
         return Double.POSITIVE_INFINITY;
      }
   }

   public static double KullbackLeiblerDivergence(SparseArrayModified p, SparseArrayModified q) {
      if (p.isEmpty()) {
         throw new IllegalArgumentException("p is empty.");
      } else if (q.isEmpty()) {
         throw new IllegalArgumentException("q is empty.");
      } else {
         Iterator<SparseArrayModified.Entry> pIter = p.iterator();
         Iterator<SparseArrayModified.Entry> qIter = q.iterator();
         SparseArrayModified.Entry a = pIter.hasNext() ? (SparseArrayModified.Entry)pIter.next() : null;
         SparseArrayModified.Entry b = qIter.hasNext() ? (SparseArrayModified.Entry)qIter.next() : null;
         boolean intersection = false;
         double kl = 0.0D;

         while(a != null && b != null) {
            if (a.i < b.i) {
               a = pIter.hasNext() ? (SparseArrayModified.Entry)pIter.next() : null;
            } else if (a.i > b.i) {
               b = qIter.hasNext() ? (SparseArrayModified.Entry)qIter.next() : null;
            } else {
               intersection = true;
               kl += a.x * Math.log(a.x / b.x);
               a = pIter.hasNext() ? (SparseArrayModified.Entry)pIter.next() : null;
               b = qIter.hasNext() ? (SparseArrayModified.Entry)qIter.next() : null;
            }
         }

         return intersection ? kl : Double.POSITIVE_INFINITY;
      }
   }

   public static double KullbackLeiblerDivergence(double[] p, SparseArrayModified q) {
      return KullbackLeiblerDivergence(q, p);
   }

   public static double KullbackLeiblerDivergence(SparseArrayModified p, double[] q) {
      if (p.isEmpty()) {
         throw new IllegalArgumentException("p is empty.");
      } else {
         Iterator<SparseArrayModified.Entry> iter = p.iterator();
         boolean intersection = false;
         double kl = 0.0D;

         while(iter.hasNext()) {
            SparseArrayModified.Entry b = (SparseArrayModified.Entry)iter.next();
            int i = b.i;
            if (q[i] > 0.0D) {
               intersection = true;
               kl += b.x * Math.log(b.x / q[i]);
            }
         }

         if (intersection) {
            return kl;
         } else {
            return Double.POSITIVE_INFINITY;
         }
      }
   }

   public static double JensenShannonDivergence(double[] p, double[] q) {
      double[] m = new double[p.length];

      for(int i = 0; i < m.length; ++i) {
         m[i] = (p[i] + q[i]) / 2.0D;
      }

      return (KullbackLeiblerDivergence(p, m) + KullbackLeiblerDivergence(q, m)) / 2.0D;
   }

   public static double JensenShannonDivergence(SparseArrayModified p, SparseArrayModified q) {
      if (p.isEmpty()) {
         throw new IllegalArgumentException("p is empty.");
      } else if (q.isEmpty()) {
         throw new IllegalArgumentException("q is empty.");
      } else {
         Iterator<SparseArrayModified.Entry> pIter = p.iterator();
         Iterator<SparseArrayModified.Entry> qIter = q.iterator();
         SparseArrayModified.Entry a = pIter.hasNext() ? (SparseArrayModified.Entry)pIter.next() : null;
         SparseArrayModified.Entry b = qIter.hasNext() ? (SparseArrayModified.Entry)qIter.next() : null;
         double js = 0.0D;

         while(a != null && b != null) {
            double mi;
            if (a.i < b.i) {
               mi = a.x / 2.0D;
               js += a.x * Math.log(a.x / mi);
               a = pIter.hasNext() ? (SparseArrayModified.Entry)pIter.next() : null;
            } else if (a.i > b.i) {
               mi = b.x / 2.0D;
               js += b.x * Math.log(b.x / mi);
               b = qIter.hasNext() ? (SparseArrayModified.Entry)qIter.next() : null;
            } else {
               mi = (a.x + b.x) / 2.0D;
               js += a.x * Math.log(a.x / mi) + b.x * Math.log(b.x / mi);
               a = pIter.hasNext() ? (SparseArrayModified.Entry)pIter.next() : null;
               b = qIter.hasNext() ? (SparseArrayModified.Entry)qIter.next() : null;
            }
         }

         return js / 2.0D;
      }
   }

   public static double JensenShannonDivergence(double[] p, SparseArrayModified q) {
      return JensenShannonDivergence(q, p);
   }

   public static double JensenShannonDivergence(SparseArrayModified p, double[] q) {
      if (p.isEmpty()) {
         throw new IllegalArgumentException("p is empty.");
      } else {
         Iterator<SparseArrayModified.Entry> iter = p.iterator();
         double js = 0.0D;

         while(iter.hasNext()) {
            SparseArrayModified.Entry b = (SparseArrayModified.Entry)iter.next();
            int i = b.i;
            double mi = (b.x + q[i]) / 2.0D;
            js += b.x * Math.log(b.x / mi);
            if (q[i] > 0.0D) {
               js += q[i] * Math.log(q[i] / mi);
            }
         }

         return js / 2.0D;
      }
   }

   public static int dot(int[] x, int[] y) {
      int sum = 0;
      int p1 = 0;
      int p2 = 0;

      while(p1 < x.length && p2 < y.length) {
         int i1 = x[p1];
         int i2 = y[p2];
         if (i1 == i2) {
            ++sum;
            ++p1;
            ++p2;
         } else if (i1 > i2) {
            ++p2;
         } else {
            ++p1;
         }
      }

      return sum;
   }

   public static float dot(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else {
         float sum = 0.0F;

         for(int i = 0; i < x.length; ++i) {
            sum += x[i] * y[i];
         }

         return sum;
      }
   }

   public static double dot(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else {
         double sum = 0.0D;

         for(int i = 0; i < x.length; ++i) {
            sum += x[i] * y[i];
         }

         return sum;
      }
   }

   public static double dot(SparseArrayModified x, SparseArrayModified y) {
      Iterator<SparseArrayModified.Entry> it1 = x.iterator();
      Iterator<SparseArrayModified.Entry> it2 = y.iterator();
      SparseArrayModified.Entry e1 = it1.hasNext() ? (SparseArrayModified.Entry)it1.next() : null;
      SparseArrayModified.Entry e2 = it2.hasNext() ? (SparseArrayModified.Entry)it2.next() : null;
      double sum = 0.0D;

      while(e1 != null && e2 != null) {
         if (e1.i == e2.i) {
            sum += e1.x * e2.x;
            e1 = it1.hasNext() ? (SparseArrayModified.Entry)it1.next() : null;
            e2 = it2.hasNext() ? (SparseArrayModified.Entry)it2.next() : null;
         } else if (e1.i > e2.i) {
            e2 = it2.hasNext() ? (SparseArrayModified.Entry)it2.next() : null;
         } else {
            e1 = it1.hasNext() ? (SparseArrayModified.Entry)it1.next() : null;
         }
      }

      return sum;
   }

   public static MatrixModified pdot(int[][] x) {
      int n = x.length;
      MatrixModified matrix = new MatrixModified(n, n);
      matrix.uplo(UPLOModified.LOWER);
      IntStream.range(0, n).parallel().forEach((j) -> {
         int[] xj = x[j];

         for(int i = 0; i < n; ++i) {
            matrix.set(i, j, (double)dot(x[i], xj));
         }

      });
      return matrix;
   }

   public static MatrixModified pdot(float[][] x) {
      int n = x.length;
      MatrixModified matrix = new MatrixModified(n, n);
      matrix.uplo(UPLOModified.LOWER);
      IntStream.range(0, n).parallel().forEach((j) -> {
         float[] xj = x[j];

         for(int i = 0; i < n; ++i) {
            matrix.set(i, j, (double)dot(x[i], xj));
         }

      });
      return matrix;
   }

   public static MatrixModified pdot(double[][] x) {
      int n = x.length;
      MatrixModified matrix = new MatrixModified(n, n);
      matrix.uplo(UPLOModified.LOWER);
      IntStream.range(0, n).parallel().forEach((j) -> {
         double[] xj = x[j];

         for(int i = 0; i < n; ++i) {
            matrix.set(i, j, dot(x[i], xj));
         }

      });
      return matrix;
   }

   public static MatrixModified pdot(SparseArrayModified[] x) {
      int n = x.length;
      MatrixModified matrix = new MatrixModified(n, n);
      matrix.uplo(UPLOModified.LOWER);
      IntStream.range(0, n).parallel().forEach((j) -> {
         SparseArrayModified xj = x[j];

         for(int i = 0; i < n; ++i) {
            matrix.set(i, j, dot(x[i], xj));
         }

      });
      return matrix;
   }

   public static double cov(int[] x, int[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else if (x.length < 3) {
         throw new IllegalArgumentException("array length has to be at least 3.");
      } else {
         double mx = mean(x);
         double my = mean(y);
         double Sxy = 0.0D;

         for(int i = 0; i < x.length; ++i) {
            double dx = (double)x[i] - mx;
            double dy = (double)y[i] - my;
            Sxy += dx * dy;
         }

         return Sxy / (double)(x.length - 1);
      }
   }

   public static double cov(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else if (x.length < 3) {
         throw new IllegalArgumentException("array length has to be at least 3.");
      } else {
         double mx = mean(x);
         double my = mean(y);
         double Sxy = 0.0D;

         for(int i = 0; i < x.length; ++i) {
            double dx = (double)x[i] - mx;
            double dy = (double)y[i] - my;
            Sxy += dx * dy;
         }

         return Sxy / (double)(x.length - 1);
      }
   }

   public static double cov(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else if (x.length < 3) {
         throw new IllegalArgumentException("array length has to be at least 3.");
      } else {
         double mx = mean(x);
         double my = mean(y);
         double Sxy = 0.0D;

         for(int i = 0; i < x.length; ++i) {
            double dx = x[i] - mx;
            double dy = y[i] - my;
            Sxy += dx * dy;
         }

         return Sxy / (double)(x.length - 1);
      }
   }

   public static double[][] cov(double[][] data) {
      return cov(data, colMeans(data));
   }

   public static double[][] cov(double[][] data, double[] mu) {
      double[][] sigma = new double[data[0].length][data[0].length];
      double[][] var6 = data;
      int k = data.length;

      int j;
      for(j = 0; j < k; ++j) {
         double[] datum = var6[j];

         for(int j = 0; j < mu.length; ++j) {
            for(int k = 0; k <= j; ++k) {
               sigma[j][k] += (datum[j] - mu[j]) * (datum[k] - mu[k]);
            }
         }
      }

      int n = data.length - 1;

      for(j = 0; j < mu.length; ++j) {
         for(k = 0; k <= j; ++k) {
            sigma[j][k] /= (double)n;
            sigma[k][j] = sigma[j][k];
         }
      }

      return sigma;
   }

   public static double cor(int[] x, int[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else if (x.length < 3) {
         throw new IllegalArgumentException("array length has to be at least 3.");
      } else {
         double Sxy = cov(x, y);
         double Sxx = var(x);
         double Syy = var(y);
         return Sxx != 0.0D && Syy != 0.0D ? Sxy / Math.sqrt(Sxx * Syy) : Double.NaN;
      }
   }

   public static double cor(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else if (x.length < 3) {
         throw new IllegalArgumentException("array length has to be at least 3.");
      } else {
         double Sxy = cov(x, y);
         double Sxx = var(x);
         double Syy = var(y);
         return Sxx != 0.0D && Syy != 0.0D ? Sxy / Math.sqrt(Sxx * Syy) : Double.NaN;
      }
   }

   public static double cor(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Arrays have different length.");
      } else if (x.length < 3) {
         throw new IllegalArgumentException("array length has to be at least 3.");
      } else {
         double Sxy = cov(x, y);
         double Sxx = var(x);
         double Syy = var(y);
         return Sxx != 0.0D && Syy != 0.0D ? Sxy / Math.sqrt(Sxx * Syy) : Double.NaN;
      }
   }

   public static double[][] cor(double[][] data) {
      return cor(data, colMeans(data));
   }

   public static double[][] cor(double[][] data, double[] mu) {
      double[][] sigma = cov(data, mu);
      int n = data[0].length;
      double[] sd = new double[n];

      int i;
      for(i = 0; i < n; ++i) {
         sd[i] = Math.sqrt(sigma[i][i]);
      }

      for(i = 0; i < n; ++i) {
         for(int j = 0; j <= i; ++j) {
            sigma[i][j] /= sd[i] * sd[j];
            sigma[j][i] = sigma[i][j];
         }
      }

      return sigma;
   }

   private static double crank(double[] w) {
      int n = w.length;
      double s = 0.0D;
      int j = 1;

      while(true) {
         while(j < n) {
            if (w[j] != w[j - 1]) {
               w[j - 1] = (double)j;
               ++j;
            } else {
               int jt;
               for(jt = j + 1; jt <= n && w[jt - 1] == w[j - 1]; ++jt) {
               }

               double rank = 0.5D * (double)(j + jt - 1);

               for(int ji = j; ji <= jt - 1; ++ji) {
                  w[ji - 1] = rank;
               }

               double t = (double)(jt - j);
               s += t * t * t - t;
               j = jt;
            }
         }

         if (j == n) {
            w[n - 1] = (double)n;
         }

         return s;
      }
   }

   public static double spearman(int[] x, int[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         int n = x.length;
         double[] wksp1 = new double[n];
         double[] wksp2 = new double[n];

         for(int j = 0; j < n; ++j) {
            wksp1[j] = (double)x[j];
            wksp2[j] = (double)y[j];
         }

         QuickSortModified.sort(wksp1, wksp2);
         crank(wksp1);
         QuickSortModified.sort(wksp2, wksp1);
         crank(wksp2);
         return cor(wksp1, wksp2);
      }
   }

   public static double spearman(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         int n = x.length;
         double[] wksp1 = new double[n];
         double[] wksp2 = new double[n];

         for(int j = 0; j < n; ++j) {
            wksp1[j] = (double)x[j];
            wksp2[j] = (double)y[j];
         }

         QuickSortModified.sort(wksp1, wksp2);
         crank(wksp1);
         QuickSortModified.sort(wksp2, wksp1);
         crank(wksp2);
         return cor(wksp1, wksp2);
      }
   }

   public static double spearman(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         double[] wksp1 = (double[])x.clone();
         double[] wksp2 = (double[])y.clone();
         QuickSortModified.sort(wksp1, wksp2);
         crank(wksp1);
         QuickSortModified.sort(wksp2, wksp1);
         crank(wksp2);
         return cor(wksp1, wksp2);
      }
   }

   public static double kendall(int[] x, int[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         int is = 0;
         int n2 = 0;
         int n1 = 0;
         int n = x.length;

         for(int j = 0; j < n - 1; ++j) {
            for(int k = j + 1; k < n; ++k) {
               double a1 = (double)(x[j] - x[k]);
               double a2 = (double)(y[j] - y[k]);
               double aa = a1 * a2;
               if (aa != 0.0D) {
                  ++n1;
                  ++n2;
                  if (aa > 0.0D) {
                     ++is;
                  } else {
                     --is;
                  }
               } else {
                  if (a1 != 0.0D) {
                     ++n1;
                  }

                  if (a2 != 0.0D) {
                     ++n2;
                  }
               }
            }
         }

         return (double)is / (Math.sqrt((double)n1) * Math.sqrt((double)n2));
      }
   }

   public static double kendall(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         int is = 0;
         int n2 = 0;
         int n1 = 0;
         int n = x.length;

         for(int j = 0; j < n - 1; ++j) {
            for(int k = j + 1; k < n; ++k) {
               double a1 = (double)(x[j] - x[k]);
               double a2 = (double)(y[j] - y[k]);
               double aa = a1 * a2;
               if (aa != 0.0D) {
                  ++n1;
                  ++n2;
                  if (aa > 0.0D) {
                     ++is;
                  } else {
                     --is;
                  }
               } else {
                  if (a1 != 0.0D) {
                     ++n1;
                  }

                  if (a2 != 0.0D) {
                     ++n2;
                  }
               }
            }
         }

         return (double)is / (Math.sqrt((double)n1) * Math.sqrt((double)n2));
      }
   }

   public static double kendall(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException("Input vector sizes are different.");
      } else {
         int is = 0;
         int n2 = 0;
         int n1 = 0;
         int n = x.length;

         for(int j = 0; j < n - 1; ++j) {
            for(int k = j + 1; k < n; ++k) {
               double a1 = x[j] - x[k];
               double a2 = y[j] - y[k];
               double aa = a1 * a2;
               if (aa != 0.0D) {
                  ++n1;
                  ++n2;
                  if (aa > 0.0D) {
                     ++is;
                  } else {
                     --is;
                  }
               } else {
                  if (a1 != 0.0D) {
                     ++n1;
                  }

                  if (a2 != 0.0D) {
                     ++n2;
                  }
               }
            }
         }

         return (double)is / (Math.sqrt((double)n1) * Math.sqrt((double)n2));
      }
   }

   public static float norm1(float[] x) {
      float norm = 0.0F;
      float[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         float n = var5[var3];
         norm += Math.abs(n);
      }

      return norm;
   }

   public static double norm1(double[] x) {
      double norm = 0.0D;
      double[] var7 = x;
      int var6 = x.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         double n = var7[var5];
         norm += Math.abs(n);
      }

      return norm;
   }

   public static float norm2(float[] x) {
      float norm = 0.0F;
      float[] var5 = x;
      int var4 = x.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         float n = var5[var3];
         norm += n * n;
      }

      norm = (float)Math.sqrt((double)norm);
      return norm;
   }

   public static double norm2(double[] x) {
      double norm = 0.0D;
      double[] var7 = x;
      int var6 = x.length;

      for(int var5 = 0; var5 < var6; ++var5) {
         double n = var7[var5];
         norm += n * n;
      }

      norm = Math.sqrt(norm);
      return norm;
   }

   public static float normInf(float[] x) {
      int n = x.length;
      float f = Math.abs(x[0]);

      for(int i = 1; i < n; ++i) {
         f = Math.max(f, Math.abs(x[i]));
      }

      return f;
   }

   public static double normInf(double[] x) {
      int n = x.length;
      double f = Math.abs(x[0]);

      for(int i = 1; i < n; ++i) {
         f = Math.max(f, Math.abs(x[i]));
      }

      return f;
   }

   public static float norm(float[] x) {
      return norm2(x);
   }

   public static double norm(double[] x) {
      return norm2(x);
   }

   public static float cos(float[] x, float[] y) {
      return dot(x, y) / (norm2(x) * norm2(y));
   }

   public static double cos(double[] x, double[] y) {
      return dot(x, y) / (norm2(x) * norm2(y));
   }

   public static void standardize(double[] x) {
      double mu = mean(x);
      double sigma = sd(x);
      if (isZero(sigma)) {
         logger.warn("array has variance of 0.");
      } else {
         for(int i = 0; i < x.length; ++i) {
            x[i] = (x[i] - mu) / sigma;
         }

      }
   }

   public static void scale(double[][] x) {
      int n = x.length;
      int p = x[0].length;
      double[] min = colMin(x);
      double[] max = colMax(x);

      for(int j = 0; j < p; ++j) {
         double scale = max[j] - min[j];
         int i;
         if (!isZero(scale)) {
            for(i = 0; i < n; ++i) {
               x[i][j] = (x[i][j] - min[j]) / scale;
            }
         } else {
            for(i = 0; i < n; ++i) {
               x[i][j] = 0.5D;
            }
         }
      }

   }

   public static void standardize(double[][] x) {
      int n = x.length;
      int p = x[0].length;
      double[] center = colMeans(x);

      int j;
      for(int i = 0; i < n; ++i) {
         for(j = 0; j < p; ++j) {
            x[i][j] -= center[j];
         }
      }

      double[] scale = new double[p];

      for(j = 0; j < p; ++j) {
         double[][] var9 = x;
         int var8 = x.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            double[] xi = var9[var7];
            scale[j] += pow2(xi[j]);
         }

         scale[j] = Math.sqrt(scale[j] / (double)(n - 1));
         if (!isZero(scale[j])) {
            for(int i = 0; i < n; ++i) {
               x[i][j] /= scale[j];
            }
         }
      }

   }

   public static void normalize(double[][] x) {
      normalize(x, false);
   }

   public static void normalize(double[][] x, boolean centerizing) {
      int n = x.length;
      int p = x[0].length;
      double[] scale;
      int i;
      int j;
      if (centerizing) {
         scale = colMeans(x);

         for(i = 0; i < n; ++i) {
            for(j = 0; j < p; ++j) {
               x[i][j] -= scale[j];
            }
         }
      }

      scale = new double[p];

      for(i = 0; i < p; ++i) {
         double[][] var9 = x;
         int var8 = x.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            double[] xi = var9[var7];
            scale[i] += pow2(xi[i]);
         }

         scale[i] = Math.sqrt(scale[i]);
      }

      for(i = 0; i < n; ++i) {
         for(j = 0; j < p; ++j) {
            if (!isZero(scale[j])) {
               x[i][j] /= scale[j];
            }
         }
      }

   }

   public static void unitize(double[] x) {
      unitize2(x);
   }

   public static void unitize1(double[] x) {
      double n = norm1(x);

      for(int i = 0; i < x.length; ++i) {
         x[i] /= n;
      }

   }

   public static void unitize2(double[] x) {
      double n = norm(x);

      for(int i = 0; i < x.length; ++i) {
         x[i] /= n;
      }

   }

   public static boolean equals(float[] x, float[] y) {
      return equals(x, y, 1.0E-7F);
   }

   public static boolean equals(float[] x, float[] y, float epsilon) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            if (Math.abs(x[i] - y[i]) > epsilon) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equals(double[] x, double[] y) {
      return equals(x, y, 1.0E-10D);
   }

   public static boolean equals(double[] x, double[] y, double epsilon) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else if (epsilon <= 0.0D) {
         throw new IllegalArgumentException("Invalid epsilon: " + epsilon);
      } else {
         for(int i = 0; i < x.length; ++i) {
            if (Math.abs(x[i] - y[i]) > epsilon) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equals(float[][] x, float[][] y) {
      return equals(x, y, 1.0E-7F);
   }

   public static boolean equals(float[][] x, float[][] y, float epsilon) {
      if (x.length == y.length && x[0].length == y[0].length) {
         for(int i = 0; i < x.length; ++i) {
            for(int j = 0; j < x[i].length; ++j) {
               if (Math.abs(x[i][j] - y[i][j]) > epsilon) {
                  return false;
               }
            }
         }

         return true;
      } else {
         throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
      }
   }

   public static boolean equals(double[][] x, double[][] y) {
      return equals(x, y, 1.0E-10D);
   }

   public static boolean equals(double[][] x, double[][] y, double epsilon) {
      if (x.length == y.length && x[0].length == y[0].length) {
         if (epsilon <= 0.0D) {
            throw new IllegalArgumentException("Invalid epsilon: " + epsilon);
         } else {
            for(int i = 0; i < x.length; ++i) {
               for(int j = 0; j < x[i].length; ++j) {
                  if (Math.abs(x[i][j] - y[i][j]) > epsilon) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else {
         throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
      }
   }

   public static boolean isZero(float x) {
      return isZero(x, FLOAT_EPSILON);
   }

   public static boolean isZero(float x, float epsilon) {
      return Math.abs(x) < epsilon;
   }

   public static boolean isZero(double x) {
      return isZero(x, EPSILON);
   }

   public static boolean isZero(double x, double epsilon) {
      return Math.abs(x) < epsilon;
   }

   public static int[][] clone(int[][] x) {
      int[][] matrix = new int[x.length][];

      for(int i = 0; i < x.length; ++i) {
         matrix[i] = (int[])x[i].clone();
      }

      return matrix;
   }

   public static float[][] clone(float[][] x) {
      float[][] matrix = new float[x.length][];

      for(int i = 0; i < x.length; ++i) {
         matrix[i] = (float[])x[i].clone();
      }

      return matrix;
   }

   public static double[][] clone(double[][] x) {
      double[][] matrix = new double[x.length][];

      for(int i = 0; i < x.length; ++i) {
         matrix[i] = (double[])x[i].clone();
      }

      return matrix;
   }

   public static void swap(int[] x, int i, int j) {
      int s = x[i];
      x[i] = x[j];
      x[j] = s;
   }

   public static void swap(float[] x, int i, int j) {
      float s = x[i];
      x[i] = x[j];
      x[j] = s;
   }

   public static void swap(double[] x, int i, int j) {
      double s = x[i];
      x[i] = x[j];
      x[j] = s;
   }

   public static void swap(Object[] x, int i, int j) {
      Object s = x[i];
      x[i] = x[j];
      x[j] = s;
   }

   public static void swap(int[] x, int[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            int s = x[i];
            x[i] = y[i];
            y[i] = s;
         }

      }
   }

   public static void swap(float[] x, float[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            float s = x[i];
            x[i] = y[i];
            y[i] = s;
         }

      }
   }

   public static void swap(double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            double s = x[i];
            x[i] = y[i];
            y[i] = s;
         }

      }
   }

   public static <E> void swap(E[] x, E[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            E s = x[i];
            x[i] = y[i];
            y[i] = s;
         }

      }
   }

   public static void copy(int[][] x, int[][] y) {
      if (x.length == y.length && x[0].length == y[0].length) {
         for(int i = 0; i < x.length; ++i) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
         }

      } else {
         throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
      }
   }

   public static void copy(float[][] x, float[][] y) {
      if (x.length == y.length && x[0].length == y[0].length) {
         for(int i = 0; i < x.length; ++i) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
         }

      } else {
         throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
      }
   }

   public static void copy(double[][] x, double[][] y) {
      if (x.length == y.length && x[0].length == y[0].length) {
         for(int i = 0; i < x.length; ++i) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
         }

      } else {
         throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
      }
   }

   public static void add(double[] y, double[] x) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            y[i] += x[i];
         }

      }
   }

   public static void sub(double[] y, double[] x) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            y[i] -= x[i];
         }

      }
   }

   public static void scale(double a, double[] x) {
      for(int i = 0; i < x.length; ++i) {
         x[i] *= a;
      }

   }

   public static void scale(double a, double[] x, double[] y) {
      for(int i = 0; i < x.length; ++i) {
         y[i] = a * x[i];
      }

   }

   public static double[] axpy(double a, double[] x, double[] y) {
      if (x.length != y.length) {
         throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
      } else {
         for(int i = 0; i < x.length; ++i) {
            y[i] += a * x[i];
         }

         return y;
      }
   }

   public static double[] pow(double[] x, double n) {
      double[] y = new double[x.length];

      for(int i = 0; i < x.length; ++i) {
         y[i] = Math.pow(x[i], n);
      }

      return y;
   }

   public static int[] unique(int[] x) {
      return Arrays.stream(x).distinct().toArray();
   }

   public static String[] unique(String[] x) {
      return (String[])Arrays.stream(x).distinct().toArray((var0) -> {
         return new String[var0];
      });
   }

   public static double[] solve(double[] a, double[] b, double[] c, double[] r) {
      if (b[0] == 0.0D) {
         throw new IllegalArgumentException("Invalid value of b[0] == 0. The equations should be rewritten as a set of order n - 1.");
      } else {
         int n = a.length;
         double[] u = new double[n];
         double[] gam = new double[n];
         double bet = b[0];
         u[0] = r[0] / bet;

         int j;
         for(j = 1; j < n; ++j) {
            gam[j] = c[j - 1] / bet;
            bet = b[j] - a[j] * gam[j];
            if (bet == 0.0D) {
               throw new IllegalArgumentException("The tridagonal matrix is not of diagonal dominance.");
            }

            u[j] = (r[j] - a[j] * u[j - 1]) / bet;
         }

         for(j = n - 2; j >= 0; --j) {
            u[j] -= gam[j + 1] * u[j + 1];
         }

         return u;
      }
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda var0) {
      String var1 = var0.getImplMethodName();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case -1421810692:
         if (var1.equals("squaredDistance")) {
            var2 = 0;
         } else if (var1.equals("squaredDistance")) {
            var2 = 0;
         } else if (var1.equals("squaredDistance")) {
            var2 = 0;
         } else if (var1.equals("squaredDistance")) {
            var2 = 0;
         }
         break;
      case 288459765:
         if (var1.equals("distance")) {
            var2 = 1;
         } else if (var1.equals("distance")) {
            var2 = 1;
         } else if (var1.equals("distance")) {
            var2 = 1;
         } else if (var1.equals("distance")) {
            var2 = 1;
         }
      }

      switch(var2) {
      case 0:
         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("([I[I)D")) {
            return MathExModified::squaredDistance;
         }

         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("([F[F)D")) {
            return MathExModified::squaredDistance;
         }

         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("([D[D)D")) {
            return MathExModified::squaredDistance;
         }

         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("(LsmileModified/SparseArrayModified;LsmileModified/SparseArrayModified;)D")) {
            return MathExModified::squaredDistance;
         }
         break;
      case 1:
         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("([I[I)D")) {
            return MathExModified::distance;
         }

         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("([F[F)D")) {
            return MathExModified::distance;
         }

         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("([D[D)D")) {
            return MathExModified::distance;
         }

         if (var0.getImplMethodKind() == 6 && var0.getFunctionalInterfaceClass().equals("smileModified/DistanceModified") && var0.getFunctionalInterfaceMethodName().equals("d") && var0.getFunctionalInterfaceMethodSignature().equals("(Ljava/lang/Object;Ljava/lang/Object;)D") && var0.getImplClass().equals("smileModified/MathExModified") && var0.getImplMethodSignature().equals("(LsmileModified/SparseArrayModified;LsmileModified/SparseArrayModified;)D")) {
            return MathExModified::distance;
         }
      }

      throw new IllegalArgumentException("Invalid lambda deserialization");
   }

   private static class FPU {
      int RADIX;
      int DIGITS;
      int FLOAT_DIGITS = 24;
      int ROUND_STYLE;
      int MACHEP;
      int FLOAT_MACHEP = -23;
      int NEGEP;
      int FLOAT_NEGEP = -24;
      float FLOAT_EPSILON;
      double EPSILON;

      FPU() {
         this.FLOAT_EPSILON = (float)Math.pow(2.0D, (double)this.FLOAT_MACHEP);
         double ONE = 1.0D;
         double TWO = ONE + ONE;
         double ZERO = ONE - ONE;
         double a = ONE;

         double temp;
         double temp1;
         for(temp1 = ONE; temp1 - ONE == ZERO; temp1 = temp - a) {
            a += a;
            temp = a + ONE;
         }

         double b = ONE;

         int itemp;
         for(itemp = 0; itemp == 0; itemp = (int)(temp - a)) {
            b += b;
            temp = a + b;
         }

         this.RADIX = itemp;
         double beta = (double)this.RADIX;
         this.DIGITS = 0;
         b = ONE;

         for(temp1 = ONE; temp1 - ONE == ZERO; temp1 = temp - b) {
            ++this.DIGITS;
            b *= beta;
            temp = b + ONE;
         }

         this.ROUND_STYLE = 0;
         double betah = beta / TWO;
         temp = a + betah;
         if (temp - a != ZERO) {
            this.ROUND_STYLE = 1;
         }

         double tempa = a + beta;
         temp = tempa + betah;
         if (this.ROUND_STYLE == 0 && temp - tempa != ZERO) {
            this.ROUND_STYLE = 2;
         }

         this.NEGEP = this.DIGITS + 3;
         double betain = ONE / beta;
         a = ONE;

         for(int i = 0; i < this.NEGEP; ++i) {
            a *= betain;
         }

         for(temp = ONE - a; temp - ONE == ZERO; temp = ONE - a) {
            a *= beta;
            --this.NEGEP;
         }

         this.NEGEP = -this.NEGEP;
         this.MACHEP = -this.DIGITS - 3;
         a = a;

         for(temp = ONE + a; temp - ONE == ZERO; temp = ONE + a) {
            a *= beta;
            ++this.MACHEP;
         }

         this.EPSILON = a;
      }
   }
}
