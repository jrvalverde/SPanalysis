/*      */ package smileModified;
/*      */ 
/*      */ import java.lang.invoke.SerializedLambda;
/*      */ import java.lang.reflect.Array;
/*      */ import java.security.SecureRandom;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ public class MathExModified {
/*   14 */   private static final Logger logger = LoggerFactory.getLogger(MathExModified.class);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   19 */   private static final FPU fpu = new FPU();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   24 */   public static final double EPSILON = fpu.EPSILON;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   29 */   public static final float FLOAT_EPSILON = fpu.FLOAT_EPSILON;
/*      */ 
/*      */ 
/*      */   
/*   33 */   public static final int RADIX = fpu.RADIX;
/*      */ 
/*      */ 
/*      */   
/*   37 */   public static final int DIGITS = fpu.DIGITS;
/*      */ 
/*      */ 
/*      */   
/*   41 */   public static final int FLOAT_DIGITS = fpu.FLOAT_DIGITS;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   54 */   public static final int ROUND_STYLE = fpu.ROUND_STYLE;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   59 */   public static final int MACHEP = fpu.MACHEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public static final int FLOAT_MACHEP = fpu.FLOAT_MACHEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final int NEGEP = fpu.NEGEP;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public static final int FLOAT_NEGEP = fpu.FLOAT_NEGEP;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   private static final SecureRandom seedRNG = new SecureRandom();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   86 */   private static final long[] DEFAULT_SEEDS = new long[] { 
/*   87 */       -4106602711295138952L, 7872020634117869514L, -1722503517109829138L, -3386820675908254116L, 
/*   88 */       -1736715870046201019L, 3854590623768163340L, 4984519038350406438L, 831971085876758331L, 
/*   89 */       7131773007627236777L, -3609561992173376238L, -8759399602515137276L, 6192158663294695439L, 
/*   90 */       -5656470009161653116L, -7984826214821970800L, -9113192788977418232L, -8979910231410580019L, 
/*   91 */       -4619021025191354324L, -5082417586190057466L, -6554946940783144090L, -3610462176018822900L, 
/*   92 */       8959796931768911980L, -4251632352234989839L, 4922191169088134258L, -7282805902317830669L, 
/*   93 */       3869302430595840919L, 2517690626940415460L, 4056663221614950174L, 6429856319379397738L, 
/*   94 */       7298845553914383313L, 8179510284261677971L, 4282994537597585253L, 7300184601511783348L, 
/*   95 */       2596703774884172704L, 1089838915342514714L, 4323657609714862439L, 777826126579190548L, 
/*   96 */       -1902743089794461140L, -2460431043688989882L, -3261708534465890932L, 4007861469505443778L, 
/*   97 */       8067600139237526646L, 5717273542173905853L, 2938568334013652889L, -2972203304739218305L, 
/*   98 */       6544901794394958069L, 7013723936758841449L, -4215598453287525312L, -1454689091401951913L, 
/*   99 */       -5699280845313829011L, -9147984414924288540L, 5211986845656222459L, -1287642354429721659L, 
/*  100 */       -1509334943513011620L, -9000043616528857326L, -2902817511399216571L, -742823064588229527L, 
/*  101 */       -4937222449957498789L, -455679889440396397L, -6109470266907575296L, 5515435653880394376L, 
/*  102 */       5557224587324997029L, 8904139390487005840L, 6560726276686488510L, 6959949429287621625L, 
/*  103 */       -6055733513105375650L, 5762016937143172332L, -9186652929482643329L, -1105816448554330895L, 
/*  104 */       -8200377873547841359L, 9107473159863354619L, 3239950546973836199L, -8104429975176305012L, 
/*  105 */       3822949195131885242L, -5261390396129824777L, 9176101422921943895L, -5102541493993205418L, 
/*  106 */       -1254710019595692814L, -6668066200971989826L, -2118519708589929546L, 5428466612765068681L, 
/*  107 */       -6528627776941116598L, -5945449163896244174L, -3293290115918281076L, 6370347300411991230L, 
/*  108 */       -7043881693953271167L, 8078993941165238212L, 6894961504641498099L, -8798276497942360228L, 
/*  109 */       2276271091333773917L, -7184141741385833013L, -4787502691178107481L, 1255068205351917608L, 
/*  110 */       -8644146770023935609L, 5124094110137147339L, 4917075344795488880L, 3423242822219783102L, 
/*  111 */       1588924456880980404L, 8515495360312448868L, -5563691320675461929L, -2352238951654504517L, 
/*  112 */       -7416919543420127888L, 631412478604690114L, 689144891258712875L, -9001615284848119152L, 
/*  113 */       -6275065758899203088L, 8164387857252400515L, -4122060123604826739L, -2016541034210046261L, 
/*  114 */       -7178335877193796678L, 3354303106860129181L, 5731595363486898779L, -2874315602397298018L, 
/*  115 */       5386746429707619069L, 9036622191596156315L, -7950190733284789459L, -5741691593792426169L, 
/*  116 */       -8600462258998065159L, 5460142111961227035L, 276738899508534641L, 2358776514903881139L, 
/*  117 */       -837649704945720257L, -3608906204977108245L, 2960825464614526243L, 7339056324843827739L, 
/*  118 */       -5709958573878745135L, -5885403829221945248L, 6611935345917126768L, 2588814037559904539L };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  124 */   private static int nextSeed = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  129 */   private static final ThreadLocal<RandomModified> random = new ThreadLocal<RandomModified>() {
/*      */       protected RandomModified initialValue() {
/*  131 */         synchronized (MathExModified.DEFAULT_SEEDS) {
/*      */           
/*  133 */           if (MathExModified.nextSeed < 0) {
/*  134 */             MathExModified.nextSeed = 0;
/*  135 */             return new RandomModified();
/*      */           } 
/*  137 */           if (MathExModified.nextSeed < MathExModified.DEFAULT_SEEDS.length) {
/*  138 */             MathExModified.nextSeed = MathExModified.nextSeed + 1; return new RandomModified(MathExModified.DEFAULT_SEEDS[MathExModified.nextSeed]);
/*      */           } 
/*  140 */           return new RandomModified(MathExModified.generateSeed());
/*      */         } 
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   private static class FPU
/*      */   {
/*      */     int RADIX;
/*      */     
/*      */     int DIGITS;
/*      */     
/*  152 */     int FLOAT_DIGITS = 24;
/*      */     int ROUND_STYLE;
/*      */     int MACHEP;
/*  155 */     int FLOAT_MACHEP = -23;
/*      */     int NEGEP;
/*  157 */     int FLOAT_NEGEP = -24;
/*  158 */     float FLOAT_EPSILON = (float)Math.pow(2.0D, this.FLOAT_MACHEP);
/*      */ 
/*      */     
/*      */     double EPSILON;
/*      */ 
/*      */     
/*      */     FPU() {
/*  165 */       double ONE = 1.0D;
/*  166 */       double TWO = ONE + ONE;
/*  167 */       double ZERO = ONE - ONE;
/*      */       
/*  169 */       double a = ONE;
/*  170 */       double temp1 = ONE;
/*  171 */       while (temp1 - ONE == ZERO) {
/*  172 */         a += a;
/*  173 */         double d = a + ONE;
/*  174 */         temp1 = d - a;
/*      */       } 
/*  176 */       double b = ONE;
/*  177 */       int itemp = 0;
/*  178 */       while (itemp == 0) {
/*  179 */         b += b;
/*  180 */         double d = a + b;
/*  181 */         itemp = (int)(d - a);
/*      */       } 
/*  183 */       this.RADIX = itemp;
/*  184 */       double beta = this.RADIX;
/*      */       
/*  186 */       this.DIGITS = 0;
/*  187 */       b = ONE;
/*  188 */       temp1 = ONE;
/*  189 */       while (temp1 - ONE == ZERO) {
/*  190 */         this.DIGITS++;
/*  191 */         b *= beta;
/*  192 */         double d = b + ONE;
/*  193 */         temp1 = d - b;
/*      */       } 
/*  195 */       this.ROUND_STYLE = 0;
/*  196 */       double betah = beta / TWO;
/*  197 */       double temp = a + betah;
/*  198 */       if (temp - a != ZERO) {
/*  199 */         this.ROUND_STYLE = 1;
/*      */       }
/*  201 */       double tempa = a + beta;
/*  202 */       temp = tempa + betah;
/*  203 */       if (this.ROUND_STYLE == 0 && temp - tempa != ZERO) {
/*  204 */         this.ROUND_STYLE = 2;
/*      */       }
/*      */       
/*  207 */       this.NEGEP = this.DIGITS + 3;
/*  208 */       double betain = ONE / beta;
/*  209 */       a = ONE;
/*  210 */       for (int i = 0; i < this.NEGEP; i++) {
/*  211 */         a *= betain;
/*      */       }
/*  213 */       b = a;
/*  214 */       temp = ONE - a;
/*  215 */       while (temp - ONE == ZERO) {
/*  216 */         a *= beta;
/*  217 */         this.NEGEP--;
/*  218 */         temp = ONE - a;
/*      */       } 
/*  220 */       this.NEGEP = -this.NEGEP;
/*      */       
/*  222 */       this.MACHEP = -this.DIGITS - 3;
/*  223 */       a = b;
/*  224 */       temp = ONE + a;
/*  225 */       while (temp - ONE == ZERO) {
/*  226 */         a *= beta;
/*  227 */         this.MACHEP++;
/*  228 */         temp = ONE + a;
/*      */       } 
/*  230 */       this.EPSILON = a;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  244 */   private static final double LOG2 = Math.log(2.0D);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log2(double x) {
/*  252 */     return Math.log(x) / LOG2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log(double x) {
/*  261 */     double y = -690.7755D;
/*  262 */     if (x > 1.0E-300D) {
/*  263 */       y = Math.log(x);
/*      */     }
/*  265 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double log1pe(double x) {
/*  274 */     double y = x;
/*  275 */     if (x <= 15.0D) {
/*  276 */       y = Math.log1p(Math.exp(x));
/*      */     }
/*      */     
/*  279 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInt(float x) {
/*  288 */     return (x == (float)Math.floor(x) && !Float.isInfinite(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInt(double x) {
/*  297 */     return (x == Math.floor(x) && !Double.isInfinite(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double a, double b) {
/*  307 */     if (a == b) {
/*  308 */       return true;
/*      */     }
/*      */     
/*  311 */     double absa = Math.abs(a);
/*  312 */     double absb = Math.abs(b);
/*  313 */     return (Math.abs(a - b) <= Math.min(absa, absb) * 2.220446049250313E-16D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sigmoid(double x) {
/*  323 */     x = Math.max(-36.0D, Math.min(x, 36.0D));
/*  324 */     return 1.0D / (1.0D + Math.exp(-x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double pow2(double x) {
/*  333 */     return x * x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPower2(int x) {
/*  342 */     return (x > 0 && (x & x - 1) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isProbablePrime(long n, int k) {
/*  355 */     return isProbablePrime(n, k, random.get());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isProbablePrime(long n, int k, RandomModified rng) {
/*  369 */     if (n <= 1L || n == 4L)
/*  370 */       return false; 
/*  371 */     if (n <= 3L) {
/*  372 */       return true;
/*      */     }
/*      */     
/*  375 */     int s = 0;
/*  376 */     long d = n - 1L;
/*  377 */     while (d % 2L == 0L) {
/*  378 */       s++;
/*  379 */       d /= 2L;
/*      */     } 
/*      */     
/*  382 */     for (int i = 0; i < k; i++) {
/*  383 */       long a = 2L + rng.nextLong() % (n - 4L);
/*  384 */       long x = power(a, d, n);
/*  385 */       if (x != 1L && x != n - 1L) {
/*      */ 
/*      */         
/*  388 */         int r = 0;
/*  389 */         for (; r < s; r++) {
/*  390 */           x = x * x % n;
/*  391 */           if (x == 1L) return false; 
/*  392 */           if (x == n - 1L) {
/*      */             break;
/*      */           }
/*      */         } 
/*  396 */         if (r == s) return false; 
/*      */       } 
/*  398 */     }  return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long power(long x, long y, long p) {
/*  410 */     long res = 1L;
/*  411 */     x %= p;
/*      */     
/*  413 */     while (y > 0L) {
/*      */       
/*  415 */       if ((y & 0x1L) == 1L) res = res * x % p;
/*      */ 
/*      */       
/*  418 */       y >>= 1L;
/*  419 */       x = x * x % p;
/*      */     } 
/*  421 */     return res;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double round(double x, int decimal) {
/*  432 */     if (decimal < 0) {
/*  433 */       return Math.round(x / Math.pow(10.0D, -decimal)) * Math.pow(10.0D, -decimal);
/*      */     }
/*  435 */     return Math.round(x * Math.pow(10.0D, decimal)) / Math.pow(10.0D, decimal);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double factorial(int n) {
/*  447 */     if (n < 0) {
/*  448 */       throw new IllegalArgumentException("n has to be non-negative.");
/*      */     }
/*      */     
/*  451 */     double f = 1.0D;
/*  452 */     for (int i = 2; i <= n; i++) {
/*  453 */       f *= i;
/*      */     }
/*      */     
/*  456 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double lfactorial(int n) {
/*  466 */     if (n < 0) {
/*  467 */       throw new IllegalArgumentException(String.format("n has to be non-negative: %d", new Object[] { Integer.valueOf(n) }));
/*      */     }
/*      */     
/*  470 */     double f = 0.0D;
/*  471 */     for (int i = 2; i <= n; i++) {
/*  472 */       f += Math.log(i);
/*      */     }
/*      */     
/*  475 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double choose(int n, int k) {
/*  485 */     if (n < 0 || k < 0) {
/*  486 */       throw new IllegalArgumentException(String.format("Invalid n = %d, k = %d", new Object[] { Integer.valueOf(n), Integer.valueOf(k) }));
/*      */     }
/*      */     
/*  489 */     if (n < k) {
/*  490 */       return 0.0D;
/*      */     }
/*      */     
/*  493 */     return Math.floor(0.5D + Math.exp(lchoose(n, k)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double lchoose(int n, int k) {
/*  503 */     if (k < 0 || k > n) {
/*  504 */       throw new IllegalArgumentException(String.format("Invalid n = %d, k = %d", new Object[] { Integer.valueOf(n), Integer.valueOf(k) }));
/*      */     }
/*      */     
/*  507 */     return lfactorial(n) - lfactorial(k) - lfactorial(n - k);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long generateSeed() {
/*  515 */     byte[] bytes = generateSeed(8);
/*  516 */     long seed = 0L;
/*  517 */     for (int i = 0; i < 8; i++) {
/*  518 */       seed <<= 8L;
/*  519 */       seed |= (bytes[i] & 0xFF);
/*      */     } 
/*  521 */     return seed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] generateSeed(int numBytes) {
/*  531 */     synchronized (seedRNG) {
/*  532 */       return seedRNG.generateSeed(numBytes);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static LongStream seeds() {
/*  541 */     return LongStream.generate(MathExModified::generateSeed).sequential();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setSeed(long seed) {
/*  549 */     ((RandomModified)random.get()).setSeed(seed);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long probablePrime(long n, int k) {
/*  561 */     return probablePrime(n, k, random.get());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long probablePrime(long n, int k, RandomModified rng) {
/*  574 */     long seed = n + rng.nextInt(899999963);
/*  575 */     for (int i = 0; i < 4096 && 
/*  576 */       !isProbablePrime(seed, k, rng); i++) {
/*  577 */       seed = n + rng.nextInt(899999963);
/*      */     }
/*      */     
/*  580 */     return seed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int random(double[] prob) {
/*  593 */     int[] ans = random(prob, 1);
/*  594 */     return ans[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] random(double[] prob, int n) {
/*  610 */     double[] q = new double[prob.length];
/*  611 */     for (int i = 0; i < prob.length; i++) {
/*  612 */       q[i] = prob[i] * prob.length;
/*      */     }
/*      */ 
/*      */     
/*  616 */     int[] a = new int[prob.length];
/*  617 */     for (int j = 0; j < prob.length; j++) {
/*  618 */       a[j] = j;
/*      */     }
/*      */ 
/*      */     
/*  622 */     int[] HL = new int[prob.length];
/*  623 */     int head = 0;
/*  624 */     int tail = prob.length - 1;
/*  625 */     for (int k = 0; k < prob.length; k++) {
/*  626 */       if (q[k] >= 1.0D) {
/*  627 */         HL[head++] = k;
/*      */       } else {
/*  629 */         HL[tail--] = k;
/*      */       } 
/*      */     } 
/*      */     
/*  633 */     while (head != 0 && tail != prob.length - 1) {
/*  634 */       int i1 = HL[tail + 1];
/*  635 */       int i2 = HL[head - 1];
/*  636 */       a[i1] = i2;
/*  637 */       q[i2] = q[i2] + q[i1] - 1.0D;
/*  638 */       tail++;
/*  639 */       if (q[i2] < 1.0D) {
/*  640 */         HL[tail--] = i2;
/*  641 */         head--;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  646 */     int[] ans = new int[n];
/*  647 */     for (int m = 0; m < n; m++) {
/*  648 */       double rU = random() * prob.length;
/*      */       
/*  650 */       int i1 = (int)rU;
/*  651 */       rU -= i1;
/*      */       
/*  653 */       if (rU < q[i1]) {
/*  654 */         ans[m] = i1;
/*      */       } else {
/*  656 */         ans[m] = a[i1];
/*      */       } 
/*      */     } 
/*      */     
/*  660 */     return ans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double random() {
/*  668 */     return ((RandomModified)random.get()).nextDouble();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] random(int n) {
/*  677 */     double[] x = new double[n];
/*  678 */     ((RandomModified)random.get()).nextDoubles(x);
/*  679 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double random(double lo, double hi) {
/*  690 */     return ((RandomModified)random.get()).nextDouble(lo, hi);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] random(double lo, double hi, int n) {
/*  702 */     double[] x = new double[n];
/*  703 */     ((RandomModified)random.get()).nextDoubles(x, lo, hi);
/*  704 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long randomLong() {
/*  712 */     return ((RandomModified)random.get()).nextLong();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int randomInt(int n) {
/*  721 */     return ((RandomModified)random.get()).nextInt(n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int randomInt(int lo, int hi) {
/*  732 */     int w = hi - lo;
/*  733 */     return lo + ((RandomModified)random.get()).nextInt(w);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] permutate(int n) {
/*  743 */     return ((RandomModified)random.get()).permutate(n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void permutate(int[] x) {
/*  751 */     ((RandomModified)random.get()).permutate(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void permutate(float[] x) {
/*  759 */     ((RandomModified)random.get()).permutate(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void permutate(double[] x) {
/*  767 */     ((RandomModified)random.get()).permutate(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void permutate(Object[] x) {
/*  775 */     ((RandomModified)random.get()).permutate(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int softmax(double[] posteriori) {
/*  794 */     return softmax(posteriori, posteriori.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int softmax(double[] x, int k) {
/*  814 */     int y = -1;
/*  815 */     double max = Double.NEGATIVE_INFINITY;
/*  816 */     for (int i = 0; i < k; i++) {
/*  817 */       if (x[i] > max) {
/*  818 */         max = x[i];
/*  819 */         y = i;
/*      */       } 
/*      */     } 
/*      */     
/*  823 */     double Z = 0.0D; int j;
/*  824 */     for (j = 0; j < k; j++) {
/*  825 */       double out = Math.exp(x[j] - max);
/*  826 */       x[j] = out;
/*  827 */       Z += out;
/*      */     } 
/*      */     
/*  830 */     for (j = 0; j < k; j++) {
/*  831 */       x[j] = x[j] / Z;
/*      */     }
/*      */     
/*  834 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] c(int... x) {
/*  843 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] c(float... x) {
/*  852 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] c(double... x) {
/*  861 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] c(String... x) {
/*  870 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] c(int[]... list) {
/*  879 */     int n = 0; byte b1; int i, arrayOfInt1[][];
/*  880 */     for (i = (arrayOfInt1 = list).length, b1 = 0; b1 < i; ) { int[] x = arrayOfInt1[b1]; n += x.length; b1++; }
/*  881 */      int[] y = new int[n];
/*  882 */     int pos = 0; byte b2; int j, arrayOfInt2[][];
/*  883 */     for (j = (arrayOfInt2 = list).length, b2 = 0; b2 < j; ) { int[] x = arrayOfInt2[b2];
/*  884 */       System.arraycopy(x, 0, y, pos, x.length);
/*  885 */       pos += x.length; b2++; }
/*      */     
/*  887 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] c(float[]... list) {
/*  896 */     int n = 0; byte b1; int i; float[][] arrayOfFloat1;
/*  897 */     for (i = (arrayOfFloat1 = list).length, b1 = 0; b1 < i; ) { float[] x = arrayOfFloat1[b1]; n += x.length; b1++; }
/*  898 */      float[] y = new float[n];
/*  899 */     int pos = 0; byte b2; int j; float[][] arrayOfFloat2;
/*  900 */     for (j = (arrayOfFloat2 = list).length, b2 = 0; b2 < j; ) { float[] x = arrayOfFloat2[b2];
/*  901 */       System.arraycopy(x, 0, y, pos, x.length);
/*  902 */       pos += x.length; b2++; }
/*      */     
/*  904 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] c(double[]... list) {
/*  913 */     int n = 0; byte b1; int i; double[][] arrayOfDouble1;
/*  914 */     for (i = (arrayOfDouble1 = list).length, b1 = 0; b1 < i; ) { double[] x = arrayOfDouble1[b1]; n += x.length; b1++; }
/*  915 */      double[] y = new double[n];
/*  916 */     int pos = 0; byte b2; int j; double[][] arrayOfDouble2;
/*  917 */     for (j = (arrayOfDouble2 = list).length, b2 = 0; b2 < j; ) { double[] x = arrayOfDouble2[b2];
/*  918 */       System.arraycopy(x, 0, y, pos, x.length);
/*  919 */       pos += x.length; b2++; }
/*      */     
/*  921 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] c(String[]... list) {
/*  930 */     int n = 0; byte b1; int i; String[][] arrayOfString1;
/*  931 */     for (i = (arrayOfString1 = list).length, b1 = 0; b1 < i; ) { String[] x = arrayOfString1[b1]; n += x.length; b1++; }
/*  932 */      String[] y = new String[n];
/*  933 */     int pos = 0; byte b2; int j; String[][] arrayOfString2;
/*  934 */     for (j = (arrayOfString2 = list).length, b2 = 0; b2 < j; ) { String[] x = arrayOfString2[b2];
/*  935 */       System.arraycopy(x, 0, y, pos, x.length);
/*  936 */       pos += x.length; b2++; }
/*      */     
/*  938 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] cbind(int[]... x) {
/*  947 */     return c(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] cbind(float[]... x) {
/*  956 */     return c(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] cbind(double[]... x) {
/*  965 */     return c(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] cbind(String[]... x) {
/*  974 */     return c(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] rbind(int[]... x) {
/*  983 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] rbind(float[]... x) {
/*  992 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] rbind(double[]... x) {
/* 1001 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[][] rbind(String[]... x) {
/* 1010 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> E[] slice(Object[] data, int[] index) {
/* 1021 */     int n = index.length;
/*      */ 
/*      */     
/* 1024 */     Object[] x = (Object[])Array.newInstance(data.getClass().getComponentType(), n);
/*      */     
/* 1026 */     for (int i = 0; i < n; i++) {
/* 1027 */       x[i] = data[index[i]];
/*      */     }
/*      */     
/* 1030 */     return (E[])x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] slice(int[] data, int[] index) {
/* 1040 */     int n = index.length;
/* 1041 */     int[] x = new int[n];
/* 1042 */     for (int i = 0; i < n; i++) {
/* 1043 */       x[i] = data[index[i]];
/*      */     }
/*      */     
/* 1046 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] slice(float[] data, int[] index) {
/* 1056 */     int n = index.length;
/* 1057 */     float[] x = new float[n];
/* 1058 */     for (int i = 0; i < n; i++) {
/* 1059 */       x[i] = data[index[i]];
/*      */     }
/*      */     
/* 1062 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] slice(double[] data, int[] index) {
/* 1072 */     int n = index.length;
/* 1073 */     double[] x = new double[n];
/* 1074 */     for (int i = 0; i < n; i++) {
/* 1075 */       x[i] = data[index[i]];
/*      */     }
/*      */     
/* 1078 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(double[][] polygon, double[] point) {
/* 1089 */     return contains(polygon, point[0], point[1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean contains(double[][] polygon, double x, double y) {
/* 1101 */     if (polygon.length <= 2) {
/* 1102 */       return false;
/*      */     }
/*      */     
/* 1105 */     int hits = 0;
/*      */     
/* 1107 */     int n = polygon.length;
/* 1108 */     double lastx = polygon[n - 1][0];
/* 1109 */     double lasty = polygon[n - 1][1];
/*      */     
/*      */     Object object1, object2;
/*      */     
/* 1113 */     for (int i = 0; i < n; object1 = SYNTHETIC_LOCAL_VARIABLE_11, object2 = SYNTHETIC_LOCAL_VARIABLE_13, i++) {
/* 1114 */       Object object; double curx = polygon[i][0];
/* 1115 */       double cury = polygon[i][1];
/*      */       
/* 1117 */       if (cury == object2) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */       
/* 1122 */       if (curx < object1) {
/* 1123 */         if (x >= object1) {
/*      */           continue;
/*      */         }
/* 1126 */         double leftx = curx;
/*      */       } else {
/* 1128 */         if (x >= curx) {
/*      */           continue;
/*      */         }
/* 1131 */         object = object1;
/*      */       } 
/*      */ 
/*      */       
/* 1135 */       if (cury < object2)
/* 1136 */       { if (y >= cury && y < object2)
/*      */         {
/*      */           
/* 1139 */           if (x < object)
/* 1140 */           { hits++; }
/*      */           else
/*      */           
/* 1143 */           { double test1 = x - curx;
/* 1144 */             double test2 = y - cury;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */             
/* 1157 */             if (test1 < test2 / (object2 - cury) * (object1 - curx))
/* 1158 */               hits++;  }  }  } else if (y >= object2 && y < cury) { if (x < object) { hits++; } else { double test1 = x - object1; double test2 = y - object2; if (test1 < test2 / (object2 - cury) * (object1 - curx)) hits++;  }
/*      */          }
/*      */        continue;
/*      */     } 
/* 1162 */     return ((hits & 0x1) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] omit(int[] a, int value) {
/* 1172 */     int n = 0; byte b1; int j, arrayOfInt1[];
/* 1173 */     for (j = (arrayOfInt1 = a).length, b1 = 0; b1 < j; ) { int x = arrayOfInt1[b1];
/* 1174 */       if (x != value) n++; 
/*      */       b1++; }
/*      */     
/* 1177 */     int i = 0;
/* 1178 */     int[] b = new int[n]; byte b2; int k, arrayOfInt2[];
/* 1179 */     for (k = (arrayOfInt2 = a).length, b2 = 0; b2 < k; ) { int x = arrayOfInt2[b2];
/* 1180 */       if (x != value) b[i++] = x; 
/*      */       b2++; }
/*      */     
/* 1183 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] omit(float[] a, float value) {
/* 1194 */     int n = 0; byte b1; int j; float[] arrayOfFloat1;
/* 1195 */     for (j = (arrayOfFloat1 = a).length, b1 = 0; b1 < j; ) { float x = arrayOfFloat1[b1];
/* 1196 */       if (x != value) n++; 
/*      */       b1++; }
/*      */     
/* 1199 */     int i = 0;
/* 1200 */     float[] b = new float[n]; byte b2; int k; float[] arrayOfFloat2;
/* 1201 */     for (k = (arrayOfFloat2 = a).length, b2 = 0; b2 < k; ) { float x = arrayOfFloat2[b2];
/* 1202 */       if (x != value) b[i++] = x; 
/*      */       b2++; }
/*      */     
/* 1205 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] omit(double[] a, double value) {
/* 1215 */     int n = 0; byte b1; int j; double[] arrayOfDouble1;
/* 1216 */     for (j = (arrayOfDouble1 = a).length, b1 = 0; b1 < j; ) { double x = arrayOfDouble1[b1];
/* 1217 */       if (x != value) n++; 
/*      */       b1++; }
/*      */     
/* 1220 */     int i = 0;
/* 1221 */     double[] b = new double[n]; byte b2; int k; double[] arrayOfDouble2;
/* 1222 */     for (k = (arrayOfDouble2 = a).length, b2 = 0; b2 < k; ) { double x = arrayOfDouble2[b2];
/* 1223 */       if (x != value) b[i++] = x; 
/*      */       b2++; }
/*      */     
/* 1226 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[] omitNaN(float[] a) {
/* 1235 */     int n = 0; byte b1; int j; float[] arrayOfFloat1;
/* 1236 */     for (j = (arrayOfFloat1 = a).length, b1 = 0; b1 < j; ) { float x = arrayOfFloat1[b1];
/* 1237 */       if (!Float.isNaN(x)) n++; 
/*      */       b1++; }
/*      */     
/* 1240 */     int i = 0;
/* 1241 */     float[] b = new float[n]; byte b2; int k; float[] arrayOfFloat2;
/* 1242 */     for (k = (arrayOfFloat2 = a).length, b2 = 0; b2 < k; ) { float x = arrayOfFloat2[b2];
/* 1243 */       if (!Float.isNaN(x)) b[i++] = x; 
/*      */       b2++; }
/*      */     
/* 1246 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] omitNaN(double[] a) {
/* 1255 */     int n = 0; byte b1; int j; double[] arrayOfDouble1;
/* 1256 */     for (j = (arrayOfDouble1 = a).length, b1 = 0; b1 < j; ) { double x = arrayOfDouble1[b1];
/* 1257 */       if (!Double.isNaN(x)) n++; 
/*      */       b1++; }
/*      */     
/* 1260 */     int i = 0;
/* 1261 */     double[] b = new double[n]; byte b2; int k; double[] arrayOfDouble2;
/* 1262 */     for (k = (arrayOfDouble2 = a).length, b2 = 0; b2 < k; ) { double x = arrayOfDouble2[b2];
/* 1263 */       if (!Double.isNaN(x)) b[i++] = x; 
/*      */       b2++; }
/*      */     
/* 1266 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(int[] a) {
/* 1274 */     int i = 0, j = a.length - 1;
/* 1275 */     while (i < j) {
/* 1276 */       SortModified.swap(a, i++, j--);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(float[] a) {
/* 1285 */     int i = 0, j = a.length - 1;
/* 1286 */     while (i < j) {
/* 1287 */       SortModified.swap(a, i++, j--);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(double[] a) {
/* 1296 */     int i = 0, j = a.length - 1;
/* 1297 */     while (i < j) {
/* 1298 */       SortModified.swap(a, i++, j--);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void reverse(Object[] a) {
/* 1308 */     int i = 0, j = a.length - 1;
/* 1309 */     while (i < j) {
/* 1310 */       SortModified.swap(a, i++, j--);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int mode(int[] a) {
/* 1322 */     Arrays.sort(a);
/*      */     
/* 1324 */     int mode = -1;
/* 1325 */     int count = 0;
/*      */     
/* 1327 */     int currentValue = a[0];
/* 1328 */     int currentCount = 1;
/*      */     
/* 1330 */     for (int i = 1; i < a.length; i++) {
/* 1331 */       if (a[i] != currentValue) {
/* 1332 */         if (currentCount > count) {
/* 1333 */           mode = currentValue;
/* 1334 */           count = currentCount;
/*      */         } 
/*      */         
/* 1337 */         currentValue = a[i];
/* 1338 */         currentCount = 1;
/*      */       } else {
/* 1340 */         currentCount++;
/*      */       } 
/*      */     } 
/*      */     
/* 1344 */     if (currentCount > count) {
/* 1345 */       mode = currentValue;
/*      */     }
/*      */     
/* 1348 */     return mode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int a, int b, int c) {
/* 1359 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float a, float b, float c) {
/* 1370 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double a, double b, double c) {
/* 1381 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int a, int b, int c, int d) {
/* 1393 */     return Math.min(Math.min(Math.min(a, b), c), d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float a, float b, float c, float d) {
/* 1405 */     return Math.min(Math.min(Math.min(a, b), c), d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double a, double b, double c, double d) {
/* 1417 */     return Math.min(Math.min(Math.min(a, b), c), d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int a, int b, int c) {
/* 1428 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float a, float b, float c) {
/* 1439 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double a, double b, double c) {
/* 1450 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int a, int b, int c, int d) {
/* 1462 */     return Math.max(Math.max(Math.max(a, b), c), d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float a, float b, float c, float d) {
/* 1474 */     return Math.max(Math.max(Math.max(a, b), c), d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double a, double b, double c, double d) {
/* 1486 */     return Math.max(Math.max(Math.max(a, b), c), d);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int[] x) {
/* 1495 */     int min = x[0]; byte b;
/*      */     int i, arrayOfInt[];
/* 1497 */     for (i = (arrayOfInt = x).length, b = 0; b < i; ) { int n = arrayOfInt[b];
/* 1498 */       if (n < min) {
/* 1499 */         min = n;
/*      */       }
/*      */       b++; }
/*      */     
/* 1503 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float[] x) {
/* 1512 */     float min = Float.POSITIVE_INFINITY; byte b; int i;
/*      */     float[] arrayOfFloat;
/* 1514 */     for (i = (arrayOfFloat = x).length, b = 0; b < i; ) { float n = arrayOfFloat[b];
/* 1515 */       if (n < min) {
/* 1516 */         min = n;
/*      */       }
/*      */       b++; }
/*      */     
/* 1520 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double[] x) {
/* 1529 */     double min = Double.POSITIVE_INFINITY; byte b; int i;
/*      */     double[] arrayOfDouble;
/* 1531 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double n = arrayOfDouble[b];
/* 1532 */       if (n < min) {
/* 1533 */         min = n;
/*      */       }
/*      */       b++; }
/*      */     
/* 1537 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int whichMin(int[] x) {
/* 1546 */     int min = x[0];
/* 1547 */     int which = 0;
/*      */     
/* 1549 */     for (int i = 1; i < x.length; i++) {
/* 1550 */       if (x[i] < min) {
/* 1551 */         min = x[i];
/* 1552 */         which = i;
/*      */       } 
/*      */     } 
/*      */     
/* 1556 */     return which;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int whichMin(float[] x) {
/* 1565 */     float min = Float.POSITIVE_INFINITY;
/* 1566 */     int which = 0;
/*      */     
/* 1568 */     for (int i = 0; i < x.length; i++) {
/* 1569 */       if (x[i] < min) {
/* 1570 */         min = x[i];
/* 1571 */         which = i;
/*      */       } 
/*      */     } 
/*      */     
/* 1575 */     return which;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int whichMin(double[] x) {
/* 1584 */     double min = Double.POSITIVE_INFINITY;
/* 1585 */     int which = 0;
/*      */     
/* 1587 */     for (int i = 0; i < x.length; i++) {
/* 1588 */       if (x[i] < min) {
/* 1589 */         min = x[i];
/* 1590 */         which = i;
/*      */       } 
/*      */     } 
/*      */     
/* 1594 */     return which;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int[] x) {
/* 1603 */     int max = x[0]; byte b;
/*      */     int i, arrayOfInt[];
/* 1605 */     for (i = (arrayOfInt = x).length, b = 0; b < i; ) { int n = arrayOfInt[b];
/* 1606 */       if (n > max) {
/* 1607 */         max = n;
/*      */       }
/*      */       b++; }
/*      */     
/* 1611 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float[] x) {
/* 1620 */     float max = Float.NEGATIVE_INFINITY; byte b; int i;
/*      */     float[] arrayOfFloat;
/* 1622 */     for (i = (arrayOfFloat = x).length, b = 0; b < i; ) { float n = arrayOfFloat[b];
/* 1623 */       if (n > max) {
/* 1624 */         max = n;
/*      */       }
/*      */       b++; }
/*      */     
/* 1628 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double[] x) {
/* 1637 */     double max = Double.NEGATIVE_INFINITY; byte b; int i;
/*      */     double[] arrayOfDouble;
/* 1639 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double n = arrayOfDouble[b];
/* 1640 */       if (n > max) {
/* 1641 */         max = n;
/*      */       }
/*      */       b++; }
/*      */     
/* 1645 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int whichMax(int[] x) {
/* 1654 */     int max = x[0];
/* 1655 */     int which = 0;
/*      */     
/* 1657 */     for (int i = 1; i < x.length; i++) {
/* 1658 */       if (x[i] > max) {
/* 1659 */         max = x[i];
/* 1660 */         which = i;
/*      */       } 
/*      */     } 
/*      */     
/* 1664 */     return which;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int whichMax(float[] x) {
/* 1673 */     float max = Float.NEGATIVE_INFINITY;
/* 1674 */     int which = 0;
/*      */     
/* 1676 */     for (int i = 0; i < x.length; i++) {
/* 1677 */       if (x[i] > max) {
/* 1678 */         max = x[i];
/* 1679 */         which = i;
/*      */       } 
/*      */     } 
/*      */     
/* 1683 */     return which;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int whichMax(double[] x) {
/* 1692 */     double max = Double.NEGATIVE_INFINITY;
/* 1693 */     int which = 0;
/*      */     
/* 1695 */     for (int i = 0; i < x.length; i++) {
/* 1696 */       if (x[i] > max) {
/* 1697 */         max = x[i];
/* 1698 */         which = i;
/*      */       } 
/*      */     } 
/*      */     
/* 1702 */     return which;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int[][] matrix) {
/* 1711 */     int min = matrix[0][0]; byte b;
/*      */     int i, arrayOfInt[][];
/* 1713 */     for (i = (arrayOfInt = matrix).length, b = 0; b < i; ) { int[] x = arrayOfInt[b]; byte b1; int j, arrayOfInt1[];
/* 1714 */       for (j = (arrayOfInt1 = x).length, b1 = 0; b1 < j; ) { int y = arrayOfInt1[b1];
/* 1715 */         if (min > y)
/* 1716 */           min = y; 
/*      */         b1++; }
/*      */       
/*      */       b++; }
/*      */     
/* 1721 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double[][] matrix) {
/* 1730 */     double min = Double.POSITIVE_INFINITY; byte b; int i;
/*      */     double[][] arrayOfDouble;
/* 1732 */     for (i = (arrayOfDouble = matrix).length, b = 0; b < i; ) { double[] x = arrayOfDouble[b]; byte b1; int j; double[] arrayOfDouble1;
/* 1733 */       for (j = (arrayOfDouble1 = x).length, b1 = 0; b1 < j; ) { double y = arrayOfDouble1[b1];
/* 1734 */         if (min > y)
/* 1735 */           min = y; 
/*      */         b1++; }
/*      */       
/*      */       b++; }
/*      */     
/* 1740 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int[][] matrix) {
/* 1749 */     int max = matrix[0][0]; byte b;
/*      */     int i, arrayOfInt[][];
/* 1751 */     for (i = (arrayOfInt = matrix).length, b = 0; b < i; ) { int[] x = arrayOfInt[b]; byte b1; int j, arrayOfInt1[];
/* 1752 */       for (j = (arrayOfInt1 = x).length, b1 = 0; b1 < j; ) { int y = arrayOfInt1[b1];
/* 1753 */         if (max < y)
/* 1754 */           max = y; 
/*      */         b1++; }
/*      */       
/*      */       b++; }
/*      */     
/* 1759 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double[][] matrix) {
/* 1768 */     double max = Double.NEGATIVE_INFINITY; byte b; int i;
/*      */     double[][] arrayOfDouble;
/* 1770 */     for (i = (arrayOfDouble = matrix).length, b = 0; b < i; ) { double[] x = arrayOfDouble[b]; byte b1; int j; double[] arrayOfDouble1;
/* 1771 */       for (j = (arrayOfDouble1 = x).length, b1 = 0; b1 < j; ) { double y = arrayOfDouble1[b1];
/* 1772 */         if (max < y)
/* 1773 */           max = y; 
/*      */         b1++; }
/*      */       
/*      */       b++; }
/*      */     
/* 1778 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPair whichMin(double[][] matrix) {
/* 1787 */     double min = Double.POSITIVE_INFINITY;
/* 1788 */     int whichRow = 0;
/* 1789 */     int whichCol = 0;
/*      */     
/* 1791 */     for (int i = 0; i < matrix.length; i++) {
/* 1792 */       for (int j = 0; j < (matrix[i]).length; j++) {
/* 1793 */         if (matrix[i][j] < min) {
/* 1794 */           min = matrix[i][j];
/* 1795 */           whichRow = i;
/* 1796 */           whichCol = j;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1801 */     return new IntPair(whichRow, whichCol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IntPair whichMax(double[][] matrix) {
/* 1810 */     double max = Double.NEGATIVE_INFINITY;
/* 1811 */     int whichRow = 0;
/* 1812 */     int whichCol = 0;
/*      */     
/* 1814 */     for (int i = 0; i < matrix.length; i++) {
/* 1815 */       for (int j = 0; j < (matrix[i]).length; j++) {
/* 1816 */         if (matrix[i][j] > max) {
/* 1817 */           max = matrix[i][j];
/* 1818 */           whichRow = i;
/* 1819 */           whichCol = j;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1824 */     return new IntPair(whichRow, whichCol);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] transpose(double[][] matrix) {
/* 1833 */     int m = matrix.length;
/* 1834 */     int n = (matrix[0]).length;
/*      */     
/* 1836 */     double[][] t = new double[n][m];
/* 1837 */     for (int i = 0; i < m; i++) {
/* 1838 */       for (int j = 0; j < n; j++) {
/* 1839 */         t[j][i] = matrix[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 1843 */     return t;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] rowMin(int[][] matrix) {
/* 1852 */     int[] x = new int[matrix.length];
/*      */     
/* 1854 */     for (int i = 0; i < x.length; i++) {
/* 1855 */       x[i] = min(matrix[i]);
/*      */     }
/*      */     
/* 1858 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] rowMax(int[][] matrix) {
/* 1867 */     int[] x = new int[matrix.length];
/*      */     
/* 1869 */     for (int i = 0; i < x.length; i++) {
/* 1870 */       x[i] = max(matrix[i]);
/*      */     }
/*      */     
/* 1873 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] rowSums(int[][] matrix) {
/* 1882 */     long[] x = new long[matrix.length];
/*      */     
/* 1884 */     for (int i = 0; i < x.length; i++) {
/* 1885 */       x[i] = sum(matrix[i]);
/*      */     }
/*      */     
/* 1888 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] rowMin(double[][] matrix) {
/* 1897 */     double[] x = new double[matrix.length];
/*      */     
/* 1899 */     for (int i = 0; i < x.length; i++) {
/* 1900 */       x[i] = min(matrix[i]);
/*      */     }
/*      */     
/* 1903 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] rowMax(double[][] matrix) {
/* 1912 */     double[] x = new double[matrix.length];
/*      */     
/* 1914 */     for (int i = 0; i < x.length; i++) {
/* 1915 */       x[i] = max(matrix[i]);
/*      */     }
/*      */     
/* 1918 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] rowSums(double[][] matrix) {
/* 1927 */     double[] x = new double[matrix.length];
/*      */     
/* 1929 */     for (int i = 0; i < x.length; i++) {
/* 1930 */       x[i] = sum(matrix[i]);
/*      */     }
/*      */     
/* 1933 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] rowMeans(double[][] matrix) {
/* 1942 */     double[] x = new double[matrix.length];
/*      */     
/* 1944 */     for (int i = 0; i < x.length; i++) {
/* 1945 */       x[i] = mean(matrix[i]);
/*      */     }
/*      */     
/* 1948 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] rowSds(double[][] matrix) {
/* 1957 */     double[] x = new double[matrix.length];
/*      */     
/* 1959 */     for (int i = 0; i < x.length; i++) {
/* 1960 */       x[i] = sd(matrix[i]);
/*      */     }
/*      */     
/* 1963 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] colMin(int[][] matrix) {
/* 1972 */     int[] x = new int[(matrix[0]).length];
/* 1973 */     Arrays.fill(x, 2147483647); byte b;
/*      */     int i, arrayOfInt[][];
/* 1975 */     for (i = (arrayOfInt = matrix).length, b = 0; b < i; ) { int[] row = arrayOfInt[b];
/* 1976 */       for (int j = 0; j < x.length; j++) {
/* 1977 */         if (x[j] > row[j]) {
/* 1978 */           x[j] = row[j];
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 1983 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] colMax(int[][] matrix) {
/* 1992 */     int[] x = new int[(matrix[0]).length];
/* 1993 */     Arrays.fill(x, -2147483648); byte b;
/*      */     int i, arrayOfInt[][];
/* 1995 */     for (i = (arrayOfInt = matrix).length, b = 0; b < i; ) { int[] row = arrayOfInt[b];
/* 1996 */       for (int j = 0; j < x.length; j++) {
/* 1997 */         if (x[j] < row[j]) {
/* 1998 */           x[j] = row[j];
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 2003 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long[] colSums(int[][] matrix) {
/* 2012 */     long[] x = new long[(matrix[0]).length]; byte b;
/*      */     int i, arrayOfInt[][];
/* 2014 */     for (i = (arrayOfInt = matrix).length, b = 0; b < i; ) { int[] row = arrayOfInt[b];
/* 2015 */       for (int j = 0; j < x.length; j++) {
/* 2016 */         x[j] = x[j] + row[j];
/*      */       }
/*      */       b++; }
/*      */     
/* 2020 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] colMin(double[][] matrix) {
/* 2029 */     double[] x = new double[(matrix[0]).length];
/* 2030 */     Arrays.fill(x, Double.POSITIVE_INFINITY); byte b; int i;
/*      */     double[][] arrayOfDouble;
/* 2032 */     for (i = (arrayOfDouble = matrix).length, b = 0; b < i; ) { double[] row = arrayOfDouble[b];
/* 2033 */       for (int j = 0; j < x.length; j++) {
/* 2034 */         if (x[j] > row[j]) {
/* 2035 */           x[j] = row[j];
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 2040 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] colMax(double[][] matrix) {
/* 2049 */     double[] x = new double[(matrix[0]).length];
/* 2050 */     Arrays.fill(x, Double.NEGATIVE_INFINITY); byte b; int i;
/*      */     double[][] arrayOfDouble;
/* 2052 */     for (i = (arrayOfDouble = matrix).length, b = 0; b < i; ) { double[] row = arrayOfDouble[b];
/* 2053 */       for (int j = 0; j < x.length; j++) {
/* 2054 */         if (x[j] < row[j]) {
/* 2055 */           x[j] = row[j];
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 2060 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] colSums(double[][] matrix) {
/* 2069 */     double[] x = (double[])matrix[0].clone();
/*      */     
/* 2071 */     for (int i = 1; i < matrix.length; i++) {
/* 2072 */       for (int j = 0; j < x.length; j++) {
/* 2073 */         x[j] = x[j] + matrix[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 2077 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] colMeans(double[][] matrix) {
/* 2086 */     double[] x = (double[])matrix[0].clone();
/*      */     
/* 2088 */     for (int i = 1; i < matrix.length; i++) {
/* 2089 */       for (int j = 0; j < x.length; j++) {
/* 2090 */         x[j] = x[j] + matrix[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 2094 */     scale(1.0D / matrix.length, x);
/*      */     
/* 2096 */     return x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] colSds(double[][] matrix) {
/* 2105 */     if (matrix.length < 2) {
/* 2106 */       throw new IllegalArgumentException("matrix length is less than 2.");
/*      */     }
/*      */     
/* 2109 */     int p = (matrix[0]).length;
/* 2110 */     double[] sum = new double[p];
/* 2111 */     double[] sumsq = new double[p]; byte b; int j; double[][] arrayOfDouble;
/* 2112 */     for (j = (arrayOfDouble = matrix).length, b = 0; b < j; ) { double[] row = arrayOfDouble[b];
/* 2113 */       for (int k = 0; k < p; k++) {
/* 2114 */         sum[k] = sum[k] + row[k];
/* 2115 */         sumsq[k] = sumsq[k] + row[k] * row[k];
/*      */       } 
/*      */       b++; }
/*      */     
/* 2119 */     int n = matrix.length - 1;
/* 2120 */     for (int i = 0; i < p; i++) {
/* 2121 */       sumsq[i] = Math.sqrt(sumsq[i] / n - sum[i] / matrix.length * sum[i] / n);
/*      */     }
/*      */     
/* 2124 */     return sumsq;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int sum(byte[] x) {
/* 2133 */     int sum = 0; byte b; int i;
/*      */     byte[] arrayOfByte;
/* 2135 */     for (i = (arrayOfByte = x).length, b = 0; b < i; ) { int n = arrayOfByte[b];
/* 2136 */       sum += n;
/*      */       b++; }
/*      */     
/* 2139 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long sum(int[] x) {
/* 2148 */     long sum = 0L; byte b;
/*      */     int i, arrayOfInt[];
/* 2150 */     for (i = (arrayOfInt = x).length, b = 0; b < i; ) { int n = arrayOfInt[b];
/* 2151 */       sum += n;
/*      */       b++; }
/*      */     
/* 2154 */     return (int)sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sum(float[] x) {
/* 2163 */     double sum = 0.0D; byte b; int i;
/*      */     float[] arrayOfFloat;
/* 2165 */     for (i = (arrayOfFloat = x).length, b = 0; b < i; ) { float n = arrayOfFloat[b];
/* 2166 */       sum += n;
/*      */       b++; }
/*      */     
/* 2169 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sum(double[] x) {
/* 2178 */     double sum = 0.0D; byte b; int i;
/*      */     double[] arrayOfDouble;
/* 2180 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double n = arrayOfDouble[b];
/* 2181 */       sum += n;
/*      */       b++; }
/*      */     
/* 2184 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int median(int[] x) {
/* 2194 */     return QuickSelectModified.median(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float median(float[] x) {
/* 2204 */     return QuickSelectModified.median(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double median(double[] x) {
/* 2214 */     return QuickSelectModified.median(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> T median(Comparable[] x) {
/* 2225 */     return QuickSelectModified.median((T[])x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int q1(int[] x) {
/* 2235 */     return QuickSelectModified.q1(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float q1(float[] x) {
/* 2245 */     return QuickSelectModified.q1(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double q1(double[] x) {
/* 2255 */     return QuickSelectModified.q1(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> T q1(Comparable[] x) {
/* 2266 */     return QuickSelectModified.q1((T[])x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int q3(int[] x) {
/* 2276 */     return QuickSelectModified.q3(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float q3(float[] x) {
/* 2286 */     return QuickSelectModified.q3(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double q3(double[] x) {
/* 2296 */     return QuickSelectModified.q3(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<? super T>> T q3(Comparable[] x) {
/* 2307 */     return QuickSelectModified.q3((T[])x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double mean(int[] x) {
/* 2316 */     return sum(x) / x.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double mean(float[] x) {
/* 2325 */     return sum(x) / x.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double mean(double[] x) {
/* 2334 */     return sum(x) / x.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double var(int[] x) {
/* 2343 */     if (x.length < 2) {
/* 2344 */       throw new IllegalArgumentException("Array length is less than 2.");
/*      */     }
/*      */     
/* 2347 */     double sum = 0.0D;
/* 2348 */     double sumsq = 0.0D; byte b; int i, arrayOfInt[];
/* 2349 */     for (i = (arrayOfInt = x).length, b = 0; b < i; ) { int xi = arrayOfInt[b];
/* 2350 */       sum += xi;
/* 2351 */       sumsq += (xi * xi);
/*      */       b++; }
/*      */     
/* 2354 */     int n = x.length - 1;
/* 2355 */     return sumsq / n - sum / x.length * sum / n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double var(float[] x) {
/* 2364 */     if (x.length < 2) {
/* 2365 */       throw new IllegalArgumentException("Array length is less than 2.");
/*      */     }
/*      */     
/* 2368 */     double sum = 0.0D;
/* 2369 */     double sumsq = 0.0D; byte b; int i; float[] arrayOfFloat;
/* 2370 */     for (i = (arrayOfFloat = x).length, b = 0; b < i; ) { float xi = arrayOfFloat[b];
/* 2371 */       sum += xi;
/* 2372 */       sumsq += (xi * xi);
/*      */       b++; }
/*      */     
/* 2375 */     int n = x.length - 1;
/* 2376 */     return sumsq / n - sum / x.length * sum / n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double var(double[] x) {
/* 2385 */     if (x.length < 2) {
/* 2386 */       throw new IllegalArgumentException("Array length is less than 2.");
/*      */     }
/*      */     
/* 2389 */     double sum = 0.0D;
/* 2390 */     double sumsq = 0.0D; byte b; int i; double[] arrayOfDouble;
/* 2391 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double xi = arrayOfDouble[b];
/* 2392 */       sum += xi;
/* 2393 */       sumsq += xi * xi;
/*      */       b++; }
/*      */     
/* 2396 */     int n = x.length - 1;
/* 2397 */     return sumsq / n - sum / x.length * sum / n;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sd(int[] x) {
/* 2406 */     return Math.sqrt(var(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sd(float[] x) {
/* 2415 */     return Math.sqrt(var(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double sd(double[] x) {
/* 2424 */     return Math.sqrt(var(x));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double mad(int[] x) {
/* 2455 */     int m = median(x);
/* 2456 */     for (int i = 0; i < x.length; i++) {
/* 2457 */       x[i] = Math.abs(x[i] - m);
/*      */     }
/*      */     
/* 2460 */     return median(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double mad(float[] x) {
/* 2491 */     float m = median(x);
/* 2492 */     for (int i = 0; i < x.length; i++) {
/* 2493 */       x[i] = Math.abs(x[i] - m);
/*      */     }
/*      */     
/* 2496 */     return median(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double mad(double[] x) {
/* 2527 */     double m = median(x);
/* 2528 */     for (int i = 0; i < x.length; i++) {
/* 2529 */       x[i] = Math.abs(x[i] - m);
/*      */     }
/*      */     
/* 2532 */     return median(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double distance(int[] x, int[] y) {
/* 2544 */     return Math.sqrt(squaredDistance(x, y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double distance(float[] x, float[] y) {
/* 2555 */     return Math.sqrt(squaredDistance(x, y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double distance(double[] x, double[] y) {
/* 2566 */     return Math.sqrt(squaredDistance(x, y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double distance(SparseArrayModified x, SparseArrayModified y) {
/* 2577 */     return Math.sqrt(squaredDistance(x, y));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double squaredDistance(int[] x, int[] y) {
/* 2589 */     double d = 0.0D;
/*      */     
/* 2591 */     int p1 = 0, p2 = 0;
/* 2592 */     while (p1 < x.length && p2 < y.length) {
/* 2593 */       int i1 = x[p1];
/* 2594 */       int i2 = y[p2];
/* 2595 */       if (i1 == i2) {
/* 2596 */         p1++;
/* 2597 */         p2++; continue;
/* 2598 */       }  if (i1 > i2) {
/* 2599 */         d++;
/* 2600 */         p2++; continue;
/*      */       } 
/* 2602 */       d++;
/* 2603 */       p1++;
/*      */     } 
/*      */ 
/*      */     
/* 2607 */     d += (x.length - p1);
/* 2608 */     d += (y.length - p2);
/*      */     
/* 2610 */     return d;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double squaredDistance(float[] x, float[] y) {
/*      */     double d0, d1, d2, d3;
/* 2621 */     if (x.length != y.length) {
/* 2622 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 2625 */     switch (x.length) {
/*      */       case 2:
/* 2627 */         d0 = x[0] - y[0];
/* 2628 */         d1 = x[1] - y[1];
/* 2629 */         return d0 * d0 + d1 * d1;
/*      */ 
/*      */       
/*      */       case 3:
/* 2633 */         d0 = x[0] - y[0];
/* 2634 */         d1 = x[1] - y[1];
/* 2635 */         d2 = x[2] - y[2];
/* 2636 */         return d0 * d0 + d1 * d1 + d2 * d2;
/*      */ 
/*      */       
/*      */       case 4:
/* 2640 */         d0 = x[0] - y[0];
/* 2641 */         d1 = x[1] - y[1];
/* 2642 */         d2 = x[2] - y[2];
/* 2643 */         d3 = x[3] - y[3];
/* 2644 */         return d0 * d0 + d1 * d1 + d2 * d2 + d3 * d3;
/*      */     } 
/*      */ 
/*      */     
/* 2648 */     double sum = 0.0D;
/* 2649 */     for (int i = 0; i < x.length; i++) {
/*      */       
/* 2651 */       double d = x[i] - y[i];
/* 2652 */       sum += d * d;
/*      */     } 
/*      */     
/* 2655 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double squaredDistance(double[] x, double[] y) {
/*      */     double d0, d1, d2, d3;
/* 2666 */     if (x.length != y.length) {
/* 2667 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 2670 */     switch (x.length) {
/*      */       case 2:
/* 2672 */         d0 = x[0] - y[0];
/* 2673 */         d1 = x[1] - y[1];
/* 2674 */         return d0 * d0 + d1 * d1;
/*      */ 
/*      */       
/*      */       case 3:
/* 2678 */         d0 = x[0] - y[0];
/* 2679 */         d1 = x[1] - y[1];
/* 2680 */         d2 = x[2] - y[2];
/* 2681 */         return d0 * d0 + d1 * d1 + d2 * d2;
/*      */ 
/*      */       
/*      */       case 4:
/* 2685 */         d0 = x[0] - y[0];
/* 2686 */         d1 = x[1] - y[1];
/* 2687 */         d2 = x[2] - y[2];
/* 2688 */         d3 = x[3] - y[3];
/* 2689 */         return d0 * d0 + d1 * d1 + d2 * d2 + d3 * d3;
/*      */     } 
/*      */ 
/*      */     
/* 2693 */     double sum = 0.0D;
/* 2694 */     for (int i = 0; i < x.length; i++) {
/* 2695 */       double d = x[i] - y[i];
/* 2696 */       sum += d * d;
/*      */     } 
/*      */     
/* 2699 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double squaredDistance(SparseArrayModified x, SparseArrayModified y) {
/* 2710 */     Iterator<SparseArrayModified.Entry> it1 = x.iterator();
/* 2711 */     Iterator<SparseArrayModified.Entry> it2 = y.iterator();
/* 2712 */     SparseArrayModified.Entry e1 = it1.hasNext() ? it1.next() : null;
/* 2713 */     SparseArrayModified.Entry e2 = it2.hasNext() ? it2.next() : null;
/*      */     
/* 2715 */     double sum = 0.0D;
/* 2716 */     while (e1 != null && e2 != null) {
/* 2717 */       if (e1.i == e2.i) {
/* 2718 */         sum += pow2(e1.x - e2.x);
/* 2719 */         e1 = it1.hasNext() ? it1.next() : null;
/* 2720 */         e2 = it2.hasNext() ? it2.next() : null; continue;
/* 2721 */       }  if (e1.i > e2.i) {
/* 2722 */         sum += pow2(e2.x);
/* 2723 */         e2 = it2.hasNext() ? it2.next() : null; continue;
/*      */       } 
/* 2725 */       sum += pow2(e1.x);
/* 2726 */       e1 = it1.hasNext() ? it1.next() : null;
/*      */     } 
/*      */ 
/*      */     
/* 2730 */     while (it1.hasNext()) {
/* 2731 */       double d = ((SparseArrayModified.Entry)it1.next()).x;
/* 2732 */       sum += d * d;
/*      */     } 
/*      */     
/* 2735 */     while (it2.hasNext()) {
/* 2736 */       double d = ((SparseArrayModified.Entry)it2.next()).x;
/* 2737 */       sum += d * d;
/*      */     } 
/*      */     
/* 2740 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double squaredDistanceWithMissingValues(double[] x, double[] y) {
/* 2755 */     int n = x.length;
/* 2756 */     int m = 0;
/* 2757 */     double dist = 0.0D;
/*      */     
/* 2759 */     for (int i = 0; i < n; i++) {
/* 2760 */       if (!Double.isNaN(x[i]) && !Double.isNaN(y[i])) {
/* 2761 */         m++;
/* 2762 */         double d = x[i] - y[i];
/* 2763 */         dist += d * d;
/*      */       } 
/*      */     } 
/*      */     
/* 2767 */     if (m == 0) {
/* 2768 */       dist = Double.MAX_VALUE;
/*      */     } else {
/* 2770 */       dist = n * dist / m;
/*      */     } 
/*      */     
/* 2773 */     return dist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(int[][] x) {
/* 2783 */     return pdist(x, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(int[][] x, boolean squared) {
/* 2794 */     int n = x.length;
/* 2795 */     double[][] dist = new double[n][n];
/*      */     
/* 2797 */     pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
/* 2798 */     return MatrixModified.of(dist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(float[][] x) {
/* 2807 */     return pdist(x, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(float[][] x, boolean squared) {
/* 2817 */     int n = x.length;
/* 2818 */     double[][] dist = new double[n][n];
/*      */     
/* 2820 */     pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
/* 2821 */     return MatrixModified.of(dist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(double[][] x) {
/* 2830 */     return pdist(x, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(double[][] x, boolean squared) {
/* 2840 */     int n = x.length;
/* 2841 */     double[][] dist = new double[n][n];
/*      */     
/* 2843 */     pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
/* 2844 */     return MatrixModified.of(dist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(SparseArrayModified[] x) {
/* 2853 */     return pdist(x, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdist(SparseArrayModified[] x, boolean squared) {
/* 2863 */     int n = x.length;
/* 2864 */     double[][] dist = new double[n][n];
/*      */     
/* 2866 */     pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
/* 2867 */     return MatrixModified.of(dist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void pdist(Object[] x, double[][] d, DistanceModified<T> distance) {
/* 2878 */     int n = x.length;
/*      */     
/* 2880 */     if ((d[0]).length < n) {
/* 2881 */       IntStream.range(0, n).parallel().forEach(i -> {
/*      */             T xi = (T)paramArrayOfObject[i];
/*      */             double[] di = paramArrayOfdouble[i];
/*      */             for (int j = 0; j < i; j++) {
/*      */               di[j] = paramDistanceModified.d(xi, (T)paramArrayOfObject[j]);
/*      */             }
/*      */           });
/*      */     } else {
/* 2889 */       IntStream.range(0, n).parallel().forEach(i -> {
/*      */             T xi = (T)paramArrayOfObject[i];
/*      */             double[] di = paramArrayOfdouble[i];
/*      */             for (int j = 0; j < paramInt1; j++) {
/*      */               di[j] = paramDistanceModified.d(xi, (T)paramArrayOfObject[j]);
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double entropy(double[] p) {
/* 2905 */     double h = 0.0D; byte b; int i; double[] arrayOfDouble;
/* 2906 */     for (i = (arrayOfDouble = p).length, b = 0; b < i; ) { double pi = arrayOfDouble[b];
/* 2907 */       if (pi > 0.0D)
/* 2908 */         h -= pi * Math.log(pi); 
/*      */       b++; }
/*      */     
/* 2911 */     return h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double KullbackLeiblerDivergence(double[] p, double[] q) {
/* 2934 */     boolean intersection = false;
/* 2935 */     double kl = 0.0D;
/*      */     
/* 2937 */     for (int i = 0; i < p.length; i++) {
/* 2938 */       if (p[i] != 0.0D && q[i] != 0.0D) {
/* 2939 */         intersection = true;
/* 2940 */         kl += p[i] * Math.log(p[i] / q[i]);
/*      */       } 
/*      */     } 
/*      */     
/* 2944 */     if (intersection) {
/* 2945 */       return kl;
/*      */     }
/* 2947 */     return Double.POSITIVE_INFINITY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double KullbackLeiblerDivergence(SparseArrayModified p, SparseArrayModified q) {
/* 2971 */     if (p.isEmpty()) {
/* 2972 */       throw new IllegalArgumentException("p is empty.");
/*      */     }
/*      */     
/* 2975 */     if (q.isEmpty()) {
/* 2976 */       throw new IllegalArgumentException("q is empty.");
/*      */     }
/*      */     
/* 2979 */     Iterator<SparseArrayModified.Entry> pIter = p.iterator();
/* 2980 */     Iterator<SparseArrayModified.Entry> qIter = q.iterator();
/*      */     
/* 2982 */     SparseArrayModified.Entry a = pIter.hasNext() ? pIter.next() : null;
/* 2983 */     SparseArrayModified.Entry b = qIter.hasNext() ? qIter.next() : null;
/*      */     
/* 2985 */     boolean intersection = false;
/* 2986 */     double kl = 0.0D;
/*      */     
/* 2988 */     while (a != null && b != null) {
/* 2989 */       if (a.i < b.i) {
/* 2990 */         a = pIter.hasNext() ? pIter.next() : null; continue;
/* 2991 */       }  if (a.i > b.i) {
/* 2992 */         b = qIter.hasNext() ? qIter.next() : null; continue;
/*      */       } 
/* 2994 */       intersection = true;
/* 2995 */       kl += a.x * Math.log(a.x / b.x);
/*      */       
/* 2997 */       a = pIter.hasNext() ? pIter.next() : null;
/* 2998 */       b = qIter.hasNext() ? qIter.next() : null;
/*      */     } 
/*      */ 
/*      */     
/* 3002 */     if (intersection) {
/* 3003 */       return kl;
/*      */     }
/* 3005 */     return Double.POSITIVE_INFINITY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double KullbackLeiblerDivergence(double[] p, SparseArrayModified q) {
/* 3029 */     return KullbackLeiblerDivergence(q, p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double KullbackLeiblerDivergence(SparseArrayModified p, double[] q) {
/* 3052 */     if (p.isEmpty()) {
/* 3053 */       throw new IllegalArgumentException("p is empty.");
/*      */     }
/*      */     
/* 3056 */     Iterator<SparseArrayModified.Entry> iter = p.iterator();
/*      */     
/* 3058 */     boolean intersection = false;
/* 3059 */     double kl = 0.0D;
/* 3060 */     while (iter.hasNext()) {
/* 3061 */       SparseArrayModified.Entry b = iter.next();
/* 3062 */       int i = b.i;
/* 3063 */       if (q[i] > 0.0D) {
/* 3064 */         intersection = true;
/* 3065 */         kl += b.x * Math.log(b.x / q[i]);
/*      */       } 
/*      */     } 
/*      */     
/* 3069 */     if (intersection) {
/* 3070 */       return kl;
/*      */     }
/* 3072 */     return Double.POSITIVE_INFINITY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double JensenShannonDivergence(double[] p, double[] q) {
/* 3090 */     double[] m = new double[p.length];
/* 3091 */     for (int i = 0; i < m.length; i++) {
/* 3092 */       m[i] = (p[i] + q[i]) / 2.0D;
/*      */     }
/*      */     
/* 3095 */     return (KullbackLeiblerDivergence(p, m) + KullbackLeiblerDivergence(q, m)) / 2.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double JensenShannonDivergence(SparseArrayModified p, SparseArrayModified q) {
/* 3112 */     if (p.isEmpty()) {
/* 3113 */       throw new IllegalArgumentException("p is empty.");
/*      */     }
/*      */     
/* 3116 */     if (q.isEmpty()) {
/* 3117 */       throw new IllegalArgumentException("q is empty.");
/*      */     }
/*      */     
/* 3120 */     Iterator<SparseArrayModified.Entry> pIter = p.iterator();
/* 3121 */     Iterator<SparseArrayModified.Entry> qIter = q.iterator();
/*      */     
/* 3123 */     SparseArrayModified.Entry a = pIter.hasNext() ? pIter.next() : null;
/* 3124 */     SparseArrayModified.Entry b = qIter.hasNext() ? qIter.next() : null;
/*      */     
/* 3126 */     double js = 0.0D;
/*      */     
/* 3128 */     while (a != null && b != null) {
/* 3129 */       if (a.i < b.i) {
/* 3130 */         double d = a.x / 2.0D;
/* 3131 */         js += a.x * Math.log(a.x / d);
/* 3132 */         a = pIter.hasNext() ? pIter.next() : null; continue;
/* 3133 */       }  if (a.i > b.i) {
/* 3134 */         double d = b.x / 2.0D;
/* 3135 */         js += b.x * Math.log(b.x / d);
/* 3136 */         b = qIter.hasNext() ? qIter.next() : null; continue;
/*      */       } 
/* 3138 */       double mi = (a.x + b.x) / 2.0D;
/* 3139 */       js += a.x * Math.log(a.x / mi) + b.x * Math.log(b.x / mi);
/*      */       
/* 3141 */       a = pIter.hasNext() ? pIter.next() : null;
/* 3142 */       b = qIter.hasNext() ? qIter.next() : null;
/*      */     } 
/*      */ 
/*      */     
/* 3146 */     return js / 2.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double JensenShannonDivergence(double[] p, SparseArrayModified q) {
/* 3163 */     return JensenShannonDivergence(q, p);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double JensenShannonDivergence(SparseArrayModified p, double[] q) {
/* 3180 */     if (p.isEmpty()) {
/* 3181 */       throw new IllegalArgumentException("p is empty.");
/*      */     }
/*      */     
/* 3184 */     Iterator<SparseArrayModified.Entry> iter = p.iterator();
/*      */     
/* 3186 */     double js = 0.0D;
/* 3187 */     while (iter.hasNext()) {
/* 3188 */       SparseArrayModified.Entry b = iter.next();
/* 3189 */       int i = b.i;
/* 3190 */       double mi = (b.x + q[i]) / 2.0D;
/* 3191 */       js += b.x * Math.log(b.x / mi);
/* 3192 */       if (q[i] > 0.0D) {
/* 3193 */         js += q[i] * Math.log(q[i] / mi);
/*      */       }
/*      */     } 
/*      */     
/* 3197 */     return js / 2.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int dot(int[] x, int[] y) {
/* 3208 */     int sum = 0;
/*      */     
/* 3210 */     for (int p1 = 0, p2 = 0; p1 < x.length && p2 < y.length; ) {
/* 3211 */       int i1 = x[p1];
/* 3212 */       int i2 = y[p2];
/* 3213 */       if (i1 == i2) {
/* 3214 */         sum++;
/* 3215 */         p1++;
/* 3216 */         p2++; continue;
/* 3217 */       }  if (i1 > i2) {
/* 3218 */         p2++; continue;
/*      */       } 
/* 3220 */       p1++;
/*      */     } 
/*      */ 
/*      */     
/* 3224 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float dot(float[] x, float[] y) {
/* 3234 */     if (x.length != y.length) {
/* 3235 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3238 */     float sum = 0.0F;
/* 3239 */     for (int i = 0; i < x.length; i++) {
/* 3240 */       sum += x[i] * y[i];
/*      */     }
/*      */     
/* 3243 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double dot(double[] x, double[] y) {
/* 3253 */     if (x.length != y.length) {
/* 3254 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3257 */     double sum = 0.0D;
/* 3258 */     for (int i = 0; i < x.length; i++) {
/* 3259 */       sum += x[i] * y[i];
/*      */     }
/*      */     
/* 3262 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double dot(SparseArrayModified x, SparseArrayModified y) {
/* 3272 */     Iterator<SparseArrayModified.Entry> it1 = x.iterator();
/* 3273 */     Iterator<SparseArrayModified.Entry> it2 = y.iterator();
/* 3274 */     SparseArrayModified.Entry e1 = it1.hasNext() ? it1.next() : null;
/* 3275 */     SparseArrayModified.Entry e2 = it2.hasNext() ? it2.next() : null;
/*      */     
/* 3277 */     double sum = 0.0D;
/* 3278 */     while (e1 != null && e2 != null) {
/* 3279 */       if (e1.i == e2.i) {
/* 3280 */         sum += e1.x * e2.x;
/* 3281 */         e1 = it1.hasNext() ? it1.next() : null;
/* 3282 */         e2 = it2.hasNext() ? it2.next() : null; continue;
/* 3283 */       }  if (e1.i > e2.i) {
/* 3284 */         e2 = it2.hasNext() ? it2.next() : null; continue;
/*      */       } 
/* 3286 */       e1 = it1.hasNext() ? it1.next() : null;
/*      */     } 
/*      */ 
/*      */     
/* 3290 */     return sum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdot(int[][] x) {
/* 3300 */     int n = x.length;
/*      */     
/* 3302 */     MatrixModified matrix = new MatrixModified(n, n);
/* 3303 */     matrix.uplo(UPLOModified.LOWER);
/* 3304 */     IntStream.range(0, n).parallel().forEach(j -> {
/*      */           int[] xj = paramArrayOfint[j];
/*      */           
/*      */           for (int i = 0; i < paramInt1; i++) {
/*      */             paramMatrixModified.set(i, j, dot(paramArrayOfint[i], xj));
/*      */           }
/*      */         });
/* 3311 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdot(float[][] x) {
/* 3320 */     int n = x.length;
/*      */     
/* 3322 */     MatrixModified matrix = new MatrixModified(n, n);
/* 3323 */     matrix.uplo(UPLOModified.LOWER);
/* 3324 */     IntStream.range(0, n).parallel().forEach(j -> {
/*      */           float[] xj = paramArrayOffloat[j];
/*      */           
/*      */           for (int i = 0; i < paramInt1; i++) {
/*      */             paramMatrixModified.set(i, j, dot(paramArrayOffloat[i], xj));
/*      */           }
/*      */         });
/* 3331 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdot(double[][] x) {
/* 3340 */     int n = x.length;
/*      */     
/* 3342 */     MatrixModified matrix = new MatrixModified(n, n);
/* 3343 */     matrix.uplo(UPLOModified.LOWER);
/* 3344 */     IntStream.range(0, n).parallel().forEach(j -> {
/*      */           double[] xj = paramArrayOfdouble[j];
/*      */           
/*      */           for (int i = 0; i < paramInt1; i++) {
/*      */             paramMatrixModified.set(i, j, dot(paramArrayOfdouble[i], xj));
/*      */           }
/*      */         });
/* 3351 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static MatrixModified pdot(SparseArrayModified[] x) {
/* 3360 */     int n = x.length;
/*      */     
/* 3362 */     MatrixModified matrix = new MatrixModified(n, n);
/* 3363 */     matrix.uplo(UPLOModified.LOWER);
/* 3364 */     IntStream.range(0, n).parallel().forEach(j -> {
/*      */           SparseArrayModified xj = paramArrayOfSparseArrayModified[j];
/*      */           
/*      */           for (int i = 0; i < paramInt1; i++) {
/*      */             paramMatrixModified.set(i, j, dot(paramArrayOfSparseArrayModified[i], xj));
/*      */           }
/*      */         });
/* 3371 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cov(int[] x, int[] y) {
/* 3381 */     if (x.length != y.length) {
/* 3382 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3385 */     if (x.length < 3) {
/* 3386 */       throw new IllegalArgumentException("array length has to be at least 3.");
/*      */     }
/*      */     
/* 3389 */     double mx = mean(x);
/* 3390 */     double my = mean(y);
/*      */     
/* 3392 */     double Sxy = 0.0D;
/* 3393 */     for (int i = 0; i < x.length; i++) {
/* 3394 */       double dx = x[i] - mx;
/* 3395 */       double dy = y[i] - my;
/* 3396 */       Sxy += dx * dy;
/*      */     } 
/*      */     
/* 3399 */     return Sxy / (x.length - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cov(float[] x, float[] y) {
/* 3409 */     if (x.length != y.length) {
/* 3410 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3413 */     if (x.length < 3) {
/* 3414 */       throw new IllegalArgumentException("array length has to be at least 3.");
/*      */     }
/*      */     
/* 3417 */     double mx = mean(x);
/* 3418 */     double my = mean(y);
/*      */     
/* 3420 */     double Sxy = 0.0D;
/* 3421 */     for (int i = 0; i < x.length; i++) {
/* 3422 */       double dx = x[i] - mx;
/* 3423 */       double dy = y[i] - my;
/* 3424 */       Sxy += dx * dy;
/*      */     } 
/*      */     
/* 3427 */     return Sxy / (x.length - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cov(double[] x, double[] y) {
/* 3437 */     if (x.length != y.length) {
/* 3438 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3441 */     if (x.length < 3) {
/* 3442 */       throw new IllegalArgumentException("array length has to be at least 3.");
/*      */     }
/*      */     
/* 3445 */     double mx = mean(x);
/* 3446 */     double my = mean(y);
/*      */     
/* 3448 */     double Sxy = 0.0D;
/* 3449 */     for (int i = 0; i < x.length; i++) {
/* 3450 */       double dx = x[i] - mx;
/* 3451 */       double dy = y[i] - my;
/* 3452 */       Sxy += dx * dy;
/*      */     } 
/*      */     
/* 3455 */     return Sxy / (x.length - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] cov(double[][] data) {
/* 3464 */     return cov(data, colMeans(data));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] cov(double[][] data, double[] mu) {
/* 3474 */     double[][] sigma = new double[(data[0]).length][(data[0]).length]; byte b; int i; double[][] arrayOfDouble1;
/* 3475 */     for (i = (arrayOfDouble1 = data).length, b = 0; b < i; ) { double[] datum = arrayOfDouble1[b];
/* 3476 */       for (int k = 0; k < mu.length; k++) {
/* 3477 */         for (int m = 0; m <= k; m++) {
/* 3478 */           sigma[k][m] = sigma[k][m] + (datum[k] - mu[k]) * (datum[m] - mu[m]);
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 3483 */     int n = data.length - 1;
/* 3484 */     for (int j = 0; j < mu.length; j++) {
/* 3485 */       for (int k = 0; k <= j; k++) {
/* 3486 */         sigma[j][k] = sigma[j][k] / n;
/* 3487 */         sigma[k][j] = sigma[j][k];
/*      */       } 
/*      */     } 
/*      */     
/* 3491 */     return sigma;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cor(int[] x, int[] y) {
/* 3501 */     if (x.length != y.length) {
/* 3502 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3505 */     if (x.length < 3) {
/* 3506 */       throw new IllegalArgumentException("array length has to be at least 3.");
/*      */     }
/*      */     
/* 3509 */     double Sxy = cov(x, y);
/* 3510 */     double Sxx = var(x);
/* 3511 */     double Syy = var(y);
/*      */     
/* 3513 */     if (Sxx == 0.0D || Syy == 0.0D) {
/* 3514 */       return Double.NaN;
/*      */     }
/*      */     
/* 3517 */     return Sxy / Math.sqrt(Sxx * Syy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cor(float[] x, float[] y) {
/* 3527 */     if (x.length != y.length) {
/* 3528 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3531 */     if (x.length < 3) {
/* 3532 */       throw new IllegalArgumentException("array length has to be at least 3.");
/*      */     }
/*      */     
/* 3535 */     double Sxy = cov(x, y);
/* 3536 */     double Sxx = var(x);
/* 3537 */     double Syy = var(y);
/*      */     
/* 3539 */     if (Sxx == 0.0D || Syy == 0.0D) {
/* 3540 */       return Double.NaN;
/*      */     }
/*      */     
/* 3543 */     return Sxy / Math.sqrt(Sxx * Syy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cor(double[] x, double[] y) {
/* 3553 */     if (x.length != y.length) {
/* 3554 */       throw new IllegalArgumentException("Arrays have different length.");
/*      */     }
/*      */     
/* 3557 */     if (x.length < 3) {
/* 3558 */       throw new IllegalArgumentException("array length has to be at least 3.");
/*      */     }
/*      */     
/* 3561 */     double Sxy = cov(x, y);
/* 3562 */     double Sxx = var(x);
/* 3563 */     double Syy = var(y);
/*      */     
/* 3565 */     if (Sxx == 0.0D || Syy == 0.0D) {
/* 3566 */       return Double.NaN;
/*      */     }
/*      */     
/* 3569 */     return Sxy / Math.sqrt(Sxx * Syy);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] cor(double[][] data) {
/* 3578 */     return cor(data, colMeans(data));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] cor(double[][] data, double[] mu) {
/* 3588 */     double[][] sigma = cov(data, mu);
/*      */     
/* 3590 */     int n = (data[0]).length;
/* 3591 */     double[] sd = new double[n]; int i;
/* 3592 */     for (i = 0; i < n; i++) {
/* 3593 */       sd[i] = Math.sqrt(sigma[i][i]);
/*      */     }
/*      */     
/* 3596 */     for (i = 0; i < n; i++) {
/* 3597 */       for (int j = 0; j <= i; j++) {
/* 3598 */         sigma[i][j] = sigma[i][j] / sd[i] * sd[j];
/* 3599 */         sigma[j][i] = sigma[i][j];
/*      */       } 
/*      */     } 
/*      */     
/* 3603 */     return sigma;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static double crank(double[] w) {
/* 3612 */     int n = w.length;
/* 3613 */     double s = 0.0D;
/* 3614 */     int j = 1;
/* 3615 */     while (j < n) {
/* 3616 */       if (w[j] != w[j - 1]) {
/* 3617 */         w[j - 1] = j;
/* 3618 */         j++; continue;
/*      */       } 
/* 3620 */       int jt = j + 1;
/* 3621 */       while (jt <= n && w[jt - 1] == w[j - 1]) {
/* 3622 */         jt++;
/*      */       }
/*      */       
/* 3625 */       double rank = 0.5D * (j + jt - 1);
/* 3626 */       for (int ji = j; ji <= jt - 1; ji++) {
/* 3627 */         w[ji - 1] = rank;
/*      */       }
/*      */       
/* 3630 */       double t = (jt - j);
/* 3631 */       s += t * t * t - t;
/* 3632 */       j = jt;
/*      */     } 
/*      */ 
/*      */     
/* 3636 */     if (j == n) {
/* 3637 */       w[n - 1] = n;
/*      */     }
/*      */     
/* 3640 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double spearman(int[] x, int[] y) {
/* 3653 */     if (x.length != y.length) {
/* 3654 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 3657 */     int n = x.length;
/* 3658 */     double[] wksp1 = new double[n];
/* 3659 */     double[] wksp2 = new double[n];
/* 3660 */     for (int j = 0; j < n; j++) {
/* 3661 */       wksp1[j] = x[j];
/* 3662 */       wksp2[j] = y[j];
/*      */     } 
/*      */     
/* 3665 */     QuickSortModified.sort(wksp1, wksp2);
/* 3666 */     crank(wksp1);
/* 3667 */     QuickSortModified.sort(wksp2, wksp1);
/* 3668 */     crank(wksp2);
/*      */     
/* 3670 */     return cor(wksp1, wksp2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double spearman(float[] x, float[] y) {
/* 3683 */     if (x.length != y.length) {
/* 3684 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 3687 */     int n = x.length;
/* 3688 */     double[] wksp1 = new double[n];
/* 3689 */     double[] wksp2 = new double[n];
/* 3690 */     for (int j = 0; j < n; j++) {
/* 3691 */       wksp1[j] = x[j];
/* 3692 */       wksp2[j] = y[j];
/*      */     } 
/*      */     
/* 3695 */     QuickSortModified.sort(wksp1, wksp2);
/* 3696 */     crank(wksp1);
/* 3697 */     QuickSortModified.sort(wksp2, wksp1);
/* 3698 */     crank(wksp2);
/*      */     
/* 3700 */     return cor(wksp1, wksp2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double spearman(double[] x, double[] y) {
/* 3713 */     if (x.length != y.length) {
/* 3714 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 3717 */     double[] wksp1 = (double[])x.clone();
/* 3718 */     double[] wksp2 = (double[])y.clone();
/*      */     
/* 3720 */     QuickSortModified.sort(wksp1, wksp2);
/* 3721 */     crank(wksp1);
/* 3722 */     QuickSortModified.sort(wksp2, wksp1);
/* 3723 */     crank(wksp2);
/*      */     
/* 3725 */     return cor(wksp1, wksp2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double kendall(int[] x, int[] y) {
/* 3737 */     if (x.length != y.length) {
/* 3738 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 3741 */     int is = 0, n2 = 0, n1 = 0, n = x.length;
/*      */     
/* 3743 */     for (int j = 0; j < n - 1; j++) {
/* 3744 */       for (int k = j + 1; k < n; k++) {
/* 3745 */         double a1 = (x[j] - x[k]);
/* 3746 */         double a2 = (y[j] - y[k]);
/* 3747 */         double aa = a1 * a2;
/* 3748 */         if (aa != 0.0D) {
/* 3749 */           n1++;
/* 3750 */           n2++;
/* 3751 */           if (aa > 0.0D) {
/* 3752 */             is++;
/*      */           } else {
/* 3754 */             is--;
/*      */           } 
/*      */         } else {
/*      */           
/* 3758 */           if (a1 != 0.0D) {
/* 3759 */             n1++;
/*      */           }
/* 3761 */           if (a2 != 0.0D) {
/* 3762 */             n2++;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3768 */     return is / Math.sqrt(n1) * Math.sqrt(n2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double kendall(float[] x, float[] y) {
/* 3780 */     if (x.length != y.length) {
/* 3781 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 3784 */     int is = 0, n2 = 0, n1 = 0, n = x.length;
/*      */     
/* 3786 */     for (int j = 0; j < n - 1; j++) {
/* 3787 */       for (int k = j + 1; k < n; k++) {
/* 3788 */         double a1 = (x[j] - x[k]);
/* 3789 */         double a2 = (y[j] - y[k]);
/* 3790 */         double aa = a1 * a2;
/* 3791 */         if (aa != 0.0D) {
/* 3792 */           n1++;
/* 3793 */           n2++;
/* 3794 */           if (aa > 0.0D) {
/* 3795 */             is++;
/*      */           } else {
/* 3797 */             is--;
/*      */           } 
/*      */         } else {
/*      */           
/* 3801 */           if (a1 != 0.0D) {
/* 3802 */             n1++;
/*      */           }
/* 3804 */           if (a2 != 0.0D) {
/* 3805 */             n2++;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3811 */     return is / Math.sqrt(n1) * Math.sqrt(n2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double kendall(double[] x, double[] y) {
/* 3823 */     if (x.length != y.length) {
/* 3824 */       throw new IllegalArgumentException("Input vector sizes are different.");
/*      */     }
/*      */     
/* 3827 */     int is = 0, n2 = 0, n1 = 0, n = x.length;
/*      */     
/* 3829 */     for (int j = 0; j < n - 1; j++) {
/* 3830 */       for (int k = j + 1; k < n; k++) {
/* 3831 */         double a1 = x[j] - x[k];
/* 3832 */         double a2 = y[j] - y[k];
/* 3833 */         double aa = a1 * a2;
/* 3834 */         if (aa != 0.0D) {
/* 3835 */           n1++;
/* 3836 */           n2++;
/* 3837 */           if (aa > 0.0D) {
/* 3838 */             is++;
/*      */           } else {
/* 3840 */             is--;
/*      */           } 
/*      */         } else {
/*      */           
/* 3844 */           if (a1 != 0.0D) {
/* 3845 */             n1++;
/*      */           }
/* 3847 */           if (a2 != 0.0D) {
/* 3848 */             n2++;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3854 */     return is / Math.sqrt(n1) * Math.sqrt(n2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float norm1(float[] x) {
/* 3863 */     float norm = 0.0F; byte b; int i;
/*      */     float[] arrayOfFloat;
/* 3865 */     for (i = (arrayOfFloat = x).length, b = 0; b < i; ) { float n = arrayOfFloat[b];
/* 3866 */       norm += Math.abs(n);
/*      */       b++; }
/*      */     
/* 3869 */     return norm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double norm1(double[] x) {
/* 3878 */     double norm = 0.0D; byte b; int i;
/*      */     double[] arrayOfDouble;
/* 3880 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double n = arrayOfDouble[b];
/* 3881 */       norm += Math.abs(n);
/*      */       b++; }
/*      */     
/* 3884 */     return norm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float norm2(float[] x) {
/* 3893 */     float norm = 0.0F; byte b; int i;
/*      */     float[] arrayOfFloat;
/* 3895 */     for (i = (arrayOfFloat = x).length, b = 0; b < i; ) { float n = arrayOfFloat[b];
/* 3896 */       norm += n * n;
/*      */       b++; }
/*      */     
/* 3899 */     norm = (float)Math.sqrt(norm);
/*      */     
/* 3901 */     return norm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double norm2(double[] x) {
/* 3910 */     double norm = 0.0D; byte b; int i;
/*      */     double[] arrayOfDouble;
/* 3912 */     for (i = (arrayOfDouble = x).length, b = 0; b < i; ) { double n = arrayOfDouble[b];
/* 3913 */       norm += n * n;
/*      */       b++; }
/*      */     
/* 3916 */     norm = Math.sqrt(norm);
/*      */     
/* 3918 */     return norm;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float normInf(float[] x) {
/* 3927 */     int n = x.length;
/*      */     
/* 3929 */     float f = Math.abs(x[0]);
/* 3930 */     for (int i = 1; i < n; i++) {
/* 3931 */       f = Math.max(f, Math.abs(x[i]));
/*      */     }
/*      */     
/* 3934 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double normInf(double[] x) {
/* 3943 */     int n = x.length;
/*      */     
/* 3945 */     double f = Math.abs(x[0]);
/* 3946 */     for (int i = 1; i < n; i++) {
/* 3947 */       f = Math.max(f, Math.abs(x[i]));
/*      */     }
/*      */     
/* 3950 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float norm(float[] x) {
/* 3959 */     return norm2(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double norm(double[] x) {
/* 3968 */     return norm2(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float cos(float[] x, float[] y) {
/* 3978 */     return dot(x, y) / norm2(x) * norm2(y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double cos(double[] x, double[] y) {
/* 3988 */     return dot(x, y) / norm2(x) * norm2(y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void standardize(double[] x) {
/* 3996 */     double mu = mean(x);
/* 3997 */     double sigma = sd(x);
/*      */     
/* 3999 */     if (isZero(sigma)) {
/* 4000 */       logger.warn("array has variance of 0.");
/*      */       
/*      */       return;
/*      */     } 
/* 4004 */     for (int i = 0; i < x.length; i++) {
/* 4005 */       x[i] = (x[i] - mu) / sigma;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void scale(double[][] x) {
/* 4014 */     int n = x.length;
/* 4015 */     int p = (x[0]).length;
/*      */     
/* 4017 */     double[] min = colMin(x);
/* 4018 */     double[] max = colMax(x);
/*      */     
/* 4020 */     for (int j = 0; j < p; j++) {
/* 4021 */       double scale = max[j] - min[j];
/* 4022 */       if (!isZero(scale)) {
/* 4023 */         for (int i = 0; i < n; i++) {
/* 4024 */           x[i][j] = (x[i][j] - min[j]) / scale;
/*      */         }
/*      */       } else {
/* 4027 */         for (int i = 0; i < n; i++) {
/* 4028 */           x[i][j] = 0.5D;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void standardize(double[][] x) {
/* 4039 */     int n = x.length;
/* 4040 */     int p = (x[0]).length;
/*      */     
/* 4042 */     double[] center = colMeans(x);
/* 4043 */     for (int i = 0; i < n; i++) {
/* 4044 */       for (int k = 0; k < p; k++) {
/* 4045 */         x[i][k] = x[i][k] - center[k];
/*      */       }
/*      */     } 
/*      */     
/* 4049 */     double[] scale = new double[p];
/* 4050 */     for (int j = 0; j < p; j++) {
/* 4051 */       byte b; int k; double[][] arrayOfDouble; for (k = (arrayOfDouble = x).length, b = 0; b < k; ) { double[] xi = arrayOfDouble[b];
/* 4052 */         scale[j] = scale[j] + pow2(xi[j]); b++; }
/*      */       
/* 4054 */       scale[j] = Math.sqrt(scale[j] / (n - 1));
/*      */       
/* 4056 */       if (!isZero(scale[j])) {
/* 4057 */         for (int m = 0; m < n; m++) {
/* 4058 */           x[m][j] = x[m][j] / scale[j];
/*      */         }
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void normalize(double[][] x) {
/* 4069 */     normalize(x, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void normalize(double[][] x, boolean centerizing) {
/* 4078 */     int n = x.length;
/* 4079 */     int p = (x[0]).length;
/*      */     
/* 4081 */     if (centerizing) {
/* 4082 */       double[] center = colMeans(x);
/* 4083 */       for (int k = 0; k < n; k++) {
/* 4084 */         for (int m = 0; m < p; m++) {
/* 4085 */           x[k][m] = x[k][m] - center[m];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 4090 */     double[] scale = new double[p];
/* 4091 */     for (int j = 0; j < p; j++) {
/* 4092 */       byte b; int k; double[][] arrayOfDouble; for (k = (arrayOfDouble = x).length, b = 0; b < k; ) { double[] xi = arrayOfDouble[b];
/* 4093 */         scale[j] = scale[j] + pow2(xi[j]); b++; }
/*      */       
/* 4095 */       scale[j] = Math.sqrt(scale[j]);
/*      */     } 
/*      */     
/* 4098 */     for (int i = 0; i < n; i++) {
/* 4099 */       for (int k = 0; k < p; k++) {
/* 4100 */         if (!isZero(scale[k])) {
/* 4101 */           x[i][k] = x[i][k] / scale[k];
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unitize(double[] x) {
/* 4113 */     unitize2(x);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unitize1(double[] x) {
/* 4122 */     double n = norm1(x);
/*      */     
/* 4124 */     for (int i = 0; i < x.length; i++) {
/* 4125 */       x[i] = x[i] / n;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void unitize2(double[] x) {
/* 4135 */     double n = norm(x);
/*      */     
/* 4137 */     for (int i = 0; i < x.length; i++) {
/* 4138 */       x[i] = x[i] / n;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(float[] x, float[] y) {
/* 4149 */     return equals(x, y, 1.0E-7F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(float[] x, float[] y, float epsilon) {
/* 4160 */     if (x.length != y.length) {
/* 4161 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4164 */     for (int i = 0; i < x.length; i++) {
/* 4165 */       if (Math.abs(x[i] - y[i]) > epsilon) {
/* 4166 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 4170 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double[] x, double[] y) {
/* 4180 */     return equals(x, y, 1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double[] x, double[] y, double epsilon) {
/* 4191 */     if (x.length != y.length) {
/* 4192 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4195 */     if (epsilon <= 0.0D) {
/* 4196 */       throw new IllegalArgumentException("Invalid epsilon: " + epsilon);
/*      */     }
/*      */     
/* 4199 */     for (int i = 0; i < x.length; i++) {
/* 4200 */       if (Math.abs(x[i] - y[i]) > epsilon) {
/* 4201 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 4205 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(float[][] x, float[][] y) {
/* 4215 */     return equals(x, y, 1.0E-7F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(float[][] x, float[][] y, float epsilon) {
/* 4226 */     if (x.length != y.length || (x[0]).length != (y[0]).length) {
/* 4227 */       throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length), Integer.valueOf(y.length), Integer.valueOf((y[0]).length) }));
/*      */     }
/*      */     
/* 4230 */     for (int i = 0; i < x.length; i++) {
/* 4231 */       for (int j = 0; j < (x[i]).length; j++) {
/* 4232 */         if (Math.abs(x[i][j] - y[i][j]) > epsilon) {
/* 4233 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 4237 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double[][] x, double[][] y) {
/* 4247 */     return equals(x, y, 1.0E-10D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(double[][] x, double[][] y, double epsilon) {
/* 4258 */     if (x.length != y.length || (x[0]).length != (y[0]).length) {
/* 4259 */       throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length), Integer.valueOf(y.length), Integer.valueOf((y[0]).length) }));
/*      */     }
/*      */     
/* 4262 */     if (epsilon <= 0.0D) {
/* 4263 */       throw new IllegalArgumentException("Invalid epsilon: " + epsilon);
/*      */     }
/*      */     
/* 4266 */     for (int i = 0; i < x.length; i++) {
/* 4267 */       for (int j = 0; j < (x[i]).length; j++) {
/* 4268 */         if (Math.abs(x[i][j] - y[i][j]) > epsilon) {
/* 4269 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/* 4273 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(float x) {
/* 4282 */     return isZero(x, FLOAT_EPSILON);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(float x, float epsilon) {
/* 4292 */     return (Math.abs(x) < epsilon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(double x) {
/* 4301 */     return isZero(x, EPSILON);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isZero(double x, double epsilon) {
/* 4311 */     return (Math.abs(x) < epsilon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[][] clone(int[][] x) {
/* 4320 */     int[][] matrix = new int[x.length][];
/* 4321 */     for (int i = 0; i < x.length; i++) {
/* 4322 */       matrix[i] = (int[])x[i].clone();
/*      */     }
/*      */     
/* 4325 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float[][] clone(float[][] x) {
/* 4334 */     float[][] matrix = new float[x.length][];
/* 4335 */     for (int i = 0; i < x.length; i++) {
/* 4336 */       matrix[i] = (float[])x[i].clone();
/*      */     }
/*      */     
/* 4339 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[][] clone(double[][] x) {
/* 4348 */     double[][] matrix = new double[x.length][];
/* 4349 */     for (int i = 0; i < x.length; i++) {
/* 4350 */       matrix[i] = (double[])x[i].clone();
/*      */     }
/*      */     
/* 4353 */     return matrix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[] x, int i, int j) {
/* 4363 */     int s = x[i];
/* 4364 */     x[i] = x[j];
/* 4365 */     x[j] = s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(float[] x, int i, int j) {
/* 4375 */     float s = x[i];
/* 4376 */     x[i] = x[j];
/* 4377 */     x[j] = s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[] x, int i, int j) {
/* 4387 */     double s = x[i];
/* 4388 */     x[i] = x[j];
/* 4389 */     x[j] = s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(Object[] x, int i, int j) {
/* 4399 */     Object s = x[i];
/* 4400 */     x[i] = x[j];
/* 4401 */     x[j] = s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(int[] x, int[] y) {
/* 4410 */     if (x.length != y.length) {
/* 4411 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4414 */     for (int i = 0; i < x.length; i++) {
/* 4415 */       int s = x[i];
/* 4416 */       x[i] = y[i];
/* 4417 */       y[i] = s;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(float[] x, float[] y) {
/* 4427 */     if (x.length != y.length) {
/* 4428 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4431 */     for (int i = 0; i < x.length; i++) {
/* 4432 */       float s = x[i];
/* 4433 */       x[i] = y[i];
/* 4434 */       y[i] = s;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void swap(double[] x, double[] y) {
/* 4444 */     if (x.length != y.length) {
/* 4445 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4448 */     for (int i = 0; i < x.length; i++) {
/* 4449 */       double s = x[i];
/* 4450 */       x[i] = y[i];
/* 4451 */       y[i] = s;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <E> void swap(Object[] x, Object[] y) {
/* 4462 */     if (x.length != y.length) {
/* 4463 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4466 */     for (int i = 0; i < x.length; i++) {
/* 4467 */       E s = (E)x[i];
/* 4468 */       x[i] = y[i];
/* 4469 */       y[i] = s;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(int[][] x, int[][] y) {
/* 4479 */     if (x.length != y.length || (x[0]).length != (y[0]).length) {
/* 4480 */       throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length), Integer.valueOf(y.length), Integer.valueOf((y[0]).length) }));
/*      */     }
/*      */     
/* 4483 */     for (int i = 0; i < x.length; i++) {
/* 4484 */       System.arraycopy(x[i], 0, y[i], 0, (x[i]).length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(float[][] x, float[][] y) {
/* 4494 */     if (x.length != y.length || (x[0]).length != (y[0]).length) {
/* 4495 */       throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length), Integer.valueOf(y.length), Integer.valueOf((y[0]).length) }));
/*      */     }
/*      */     
/* 4498 */     for (int i = 0; i < x.length; i++) {
/* 4499 */       System.arraycopy(x[i], 0, y[i], 0, (x[i]).length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void copy(double[][] x, double[][] y) {
/* 4509 */     if (x.length != y.length || (x[0]).length != (y[0]).length) {
/* 4510 */       throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", new Object[] { Integer.valueOf(x.length), Integer.valueOf((x[0]).length), Integer.valueOf(y.length), Integer.valueOf((y[0]).length) }));
/*      */     }
/*      */     
/* 4513 */     for (int i = 0; i < x.length; i++) {
/* 4514 */       System.arraycopy(x[i], 0, y[i], 0, (x[i]).length);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void add(double[] y, double[] x) {
/* 4524 */     if (x.length != y.length) {
/* 4525 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4528 */     for (int i = 0; i < x.length; i++) {
/* 4529 */       y[i] = y[i] + x[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sub(double[] y, double[] x) {
/* 4539 */     if (x.length != y.length) {
/* 4540 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4543 */     for (int i = 0; i < x.length; i++) {
/* 4544 */       y[i] = y[i] - x[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void scale(double a, double[] x) {
/* 4554 */     for (int i = 0; i < x.length; i++) {
/* 4555 */       x[i] = x[i] * a;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void scale(double a, double[] x, double[] y) {
/* 4566 */     for (int i = 0; i < x.length; i++) {
/* 4567 */       y[i] = a * x[i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] axpy(double a, double[] x, double[] y) {
/* 4579 */     if (x.length != y.length) {
/* 4580 */       throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", new Object[] { Integer.valueOf(x.length), Integer.valueOf(y.length) }));
/*      */     }
/*      */     
/* 4583 */     for (int i = 0; i < x.length; i++) {
/* 4584 */       y[i] = y[i] + a * x[i];
/*      */     }
/*      */     
/* 4587 */     return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] pow(double[] x, double n) {
/* 4597 */     double[] y = new double[x.length];
/* 4598 */     for (int i = 0; i < x.length; ) { y[i] = Math.pow(x[i], n); i++; }
/* 4599 */      return y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] unique(int[] x) {
/* 4608 */     return Arrays.stream(x).distinct().toArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] unique(String[] x) {
/* 4617 */     return (String[])Arrays.<String>stream(x).distinct().toArray(paramInt -> new String[paramInt]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] solve(double[] a, double[] b, double[] c, double[] r) {
/* 4651 */     if (b[0] == 0.0D) {
/* 4652 */       throw new IllegalArgumentException("Invalid value of b[0] == 0. The equations should be rewritten as a set of order n - 1.");
/*      */     }
/*      */     
/* 4655 */     int n = a.length;
/* 4656 */     double[] u = new double[n];
/* 4657 */     double[] gam = new double[n];
/*      */     
/* 4659 */     double bet = b[0];
/* 4660 */     u[0] = r[0] / bet;
/*      */     int j;
/* 4662 */     for (j = 1; j < n; j++) {
/* 4663 */       gam[j] = c[j - 1] / bet;
/* 4664 */       bet = b[j] - a[j] * gam[j];
/* 4665 */       if (bet == 0.0D) {
/* 4666 */         throw new IllegalArgumentException("The tridagonal matrix is not of diagonal dominance.");
/*      */       }
/* 4668 */       u[j] = (r[j] - a[j] * u[j - 1]) / bet;
/*      */     } 
/*      */     
/* 4671 */     for (j = n - 2; j >= 0; j--) {
/* 4672 */       u[j] = u[j] - gam[j + 1] * u[j + 1];
/*      */     }
/*      */     
/* 4675 */     return u;
/*      */   }
/*      */ }


/* Location:              /mnt/jr/work/mmellado/TrackAnalyzer/TrackAnalyzer_CNB/jar_expanded/!/smileModified/MathExModified.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */