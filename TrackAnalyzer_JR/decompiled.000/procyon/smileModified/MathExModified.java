// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.lang.invoke.SerializedLambda;
import java.util.stream.IntStream;
import java.util.Iterator;
import java.util.Arrays;
import java.lang.reflect.Array;
import java.util.stream.LongStream;
import org.slf4j.LoggerFactory;
import java.security.SecureRandom;
import org.slf4j.Logger;

public class MathExModified
{
    private static final Logger logger;
    private static final FPU fpu;
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
        logger = LoggerFactory.getLogger((Class)MathExModified.class);
        fpu = new FPU();
        EPSILON = MathExModified.fpu.EPSILON;
        FLOAT_EPSILON = MathExModified.fpu.FLOAT_EPSILON;
        RADIX = MathExModified.fpu.RADIX;
        DIGITS = MathExModified.fpu.DIGITS;
        FLOAT_DIGITS = MathExModified.fpu.FLOAT_DIGITS;
        ROUND_STYLE = MathExModified.fpu.ROUND_STYLE;
        MACHEP = MathExModified.fpu.MACHEP;
        FLOAT_MACHEP = MathExModified.fpu.FLOAT_MACHEP;
        NEGEP = MathExModified.fpu.NEGEP;
        FLOAT_NEGEP = MathExModified.fpu.FLOAT_NEGEP;
        seedRNG = new SecureRandom();
        DEFAULT_SEEDS = new long[] { -4106602711295138952L, 7872020634117869514L, -1722503517109829138L, -3386820675908254116L, -1736715870046201019L, 3854590623768163340L, 4984519038350406438L, 831971085876758331L, 7131773007627236777L, -3609561992173376238L, -8759399602515137276L, 6192158663294695439L, -5656470009161653116L, -7984826214821970800L, -9113192788977418232L, -8979910231410580019L, -4619021025191354324L, -5082417586190057466L, -6554946940783144090L, -3610462176018822900L, 8959796931768911980L, -4251632352234989839L, 4922191169088134258L, -7282805902317830669L, 3869302430595840919L, 2517690626940415460L, 4056663221614950174L, 6429856319379397738L, 7298845553914383313L, 8179510284261677971L, 4282994537597585253L, 7300184601511783348L, 2596703774884172704L, 1089838915342514714L, 4323657609714862439L, 777826126579190548L, -1902743089794461140L, -2460431043688989882L, -3261708534465890932L, 4007861469505443778L, 8067600139237526646L, 5717273542173905853L, 2938568334013652889L, -2972203304739218305L, 6544901794394958069L, 7013723936758841449L, -4215598453287525312L, -1454689091401951913L, -5699280845313829011L, -9147984414924288540L, 5211986845656222459L, -1287642354429721659L, -1509334943513011620L, -9000043616528857326L, -2902817511399216571L, -742823064588229527L, -4937222449957498789L, -455679889440396397L, -6109470266907575296L, 5515435653880394376L, 5557224587324997029L, 8904139390487005840L, 6560726276686488510L, 6959949429287621625L, -6055733513105375650L, 5762016937143172332L, -9186652929482643329L, -1105816448554330895L, -8200377873547841359L, 9107473159863354619L, 3239950546973836199L, -8104429975176305012L, 3822949195131885242L, -5261390396129824777L, 9176101422921943895L, -5102541493993205418L, -1254710019595692814L, -6668066200971989826L, -2118519708589929546L, 5428466612765068681L, -6528627776941116598L, -5945449163896244174L, -3293290115918281076L, 6370347300411991230L, -7043881693953271167L, 8078993941165238212L, 6894961504641498099L, -8798276497942360228L, 2276271091333773917L, -7184141741385833013L, -4787502691178107481L, 1255068205351917608L, -8644146770023935609L, 5124094110137147339L, 4917075344795488880L, 3423242822219783102L, 1588924456880980404L, 8515495360312448868L, -5563691320675461929L, -2352238951654504517L, -7416919543420127888L, 631412478604690114L, 689144891258712875L, -9001615284848119152L, -6275065758899203088L, 8164387857252400515L, -4122060123604826739L, -2016541034210046261L, -7178335877193796678L, 3354303106860129181L, 5731595363486898779L, -2874315602397298018L, 5386746429707619069L, 9036622191596156315L, -7950190733284789459L, -5741691593792426169L, -8600462258998065159L, 5460142111961227035L, 276738899508534641L, 2358776514903881139L, -837649704945720257L, -3608906204977108245L, 2960825464614526243L, 7339056324843827739L, -5709958573878745135L, -5885403829221945248L, 6611935345917126768L, 2588814037559904539L };
        MathExModified.nextSeed = -1;
        random = new ThreadLocal<RandomModified>() {
            @Override
            protected RandomModified initialValue() {
                synchronized (MathExModified.DEFAULT_SEEDS) {
                    if (MathExModified.nextSeed < 0) {
                        MathExModified.access$2(0);
                        // monitorexit(MathExModified.access$0())
                        return new RandomModified();
                    }
                    if (MathExModified.nextSeed < MathExModified.DEFAULT_SEEDS.length) {
                        final long[] access$0 = MathExModified.DEFAULT_SEEDS;
                        final int access$2 = MathExModified.nextSeed;
                        MathExModified.access$2(access$2 + 1);
                        // monitorexit(MathExModified.access$0())
                        return new RandomModified(access$0[access$2]);
                    }
                    // monitorexit(MathExModified.access$0())
                    return new RandomModified(MathExModified.generateSeed());
                }
            }
        };
        LOG2 = Math.log(2.0);
    }
    
    private MathExModified() {
    }
    
    public static double log2(final double x) {
        return Math.log(x) / MathExModified.LOG2;
    }
    
    public static double log(final double x) {
        double y = -690.7755;
        if (x > 1.0E-300) {
            y = Math.log(x);
        }
        return y;
    }
    
    public static double log1pe(final double x) {
        double y = x;
        if (x <= 15.0) {
            y = Math.log1p(Math.exp(x));
        }
        return y;
    }
    
    public static boolean isInt(final float x) {
        return x == (float)Math.floor(x) && !Float.isInfinite(x);
    }
    
    public static boolean isInt(final double x) {
        return x == Math.floor(x) && !Double.isInfinite(x);
    }
    
    public static boolean equals(final double a, final double b) {
        if (a == b) {
            return true;
        }
        final double absa = Math.abs(a);
        final double absb = Math.abs(b);
        return Math.abs(a - b) <= Math.min(absa, absb) * 2.220446049250313E-16;
    }
    
    public static double sigmoid(double x) {
        x = Math.max(-36.0, Math.min(x, 36.0));
        return 1.0 / (1.0 + Math.exp(-x));
    }
    
    public static double pow2(final double x) {
        return x * x;
    }
    
    public static boolean isPower2(final int x) {
        return x > 0 && (x & x - 1) == 0x0;
    }
    
    public static boolean isProbablePrime(final long n, final int k) {
        return isProbablePrime(n, k, MathExModified.random.get());
    }
    
    private static boolean isProbablePrime(final long n, final int k, final RandomModified rng) {
        if (n <= 1L || n == 4L) {
            return false;
        }
        if (n <= 3L) {
            return true;
        }
        int s = 0;
        long d;
        for (d = n - 1L; d % 2L == 0L; d /= 2L) {
            ++s;
        }
        for (int i = 0; i < k; ++i) {
            final long a = 2L + rng.nextLong() % (n - 4L);
            long x = power(a, d, n);
            if (x != 1L) {
                if (x != n - 1L) {
                    int r;
                    for (r = 0; r < s; ++r) {
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
        }
        return true;
    }
    
    private static long power(long x, long y, final long p) {
        long res = 1L;
        for (x %= p; y > 0L; y >>= 1, x = x * x % p) {
            if ((y & 0x1L) == 0x1L) {
                res = res * x % p;
            }
        }
        return res;
    }
    
    public static double round(final double x, final int decimal) {
        if (decimal < 0) {
            return Math.round(x / Math.pow(10.0, -decimal)) * Math.pow(10.0, -decimal);
        }
        return Math.round(x * Math.pow(10.0, decimal)) / Math.pow(10.0, decimal);
    }
    
    public static double factorial(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n has to be non-negative.");
        }
        double f = 1.0;
        for (int i = 2; i <= n; ++i) {
            f *= i;
        }
        return f;
    }
    
    public static double lfactorial(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException(String.format("n has to be non-negative: %d", n));
        }
        double f = 0.0;
        for (int i = 2; i <= n; ++i) {
            f += Math.log(i);
        }
        return f;
    }
    
    public static double choose(final int n, final int k) {
        if (n < 0 || k < 0) {
            throw new IllegalArgumentException(String.format("Invalid n = %d, k = %d", n, k));
        }
        if (n < k) {
            return 0.0;
        }
        return Math.floor(0.5 + Math.exp(lchoose(n, k)));
    }
    
    public static double lchoose(final int n, final int k) {
        if (k < 0 || k > n) {
            throw new IllegalArgumentException(String.format("Invalid n = %d, k = %d", n, k));
        }
        return lfactorial(n) - lfactorial(k) - lfactorial(n - k);
    }
    
    public static long generateSeed() {
        final byte[] bytes = generateSeed(8);
        long seed = 0L;
        for (int i = 0; i < 8; ++i) {
            seed <<= 8;
            seed |= (bytes[i] & 0xFF);
        }
        return seed;
    }
    
    public static byte[] generateSeed(final int numBytes) {
        synchronized (MathExModified.seedRNG) {
            // monitorexit(MathExModified.seedRNG)
            return MathExModified.seedRNG.generateSeed(numBytes);
        }
    }
    
    public static LongStream seeds() {
        return LongStream.generate(MathExModified::generateSeed).sequential();
    }
    
    public static void setSeed(final long seed) {
        MathExModified.random.get().setSeed(seed);
    }
    
    public static long probablePrime(final long n, final int k) {
        return probablePrime(n, k, MathExModified.random.get());
    }
    
    private static long probablePrime(final long n, final int k, final RandomModified rng) {
        long seed = n + rng.nextInt(899999963);
        for (int i = 0; i < 4096 && !isProbablePrime(seed, k, rng); seed = n + rng.nextInt(899999963), ++i) {}
        return seed;
    }
    
    public static int random(final double[] prob) {
        final int[] ans = random(prob, 1);
        return ans[0];
    }
    
    public static int[] random(final double[] prob, final int n) {
        final double[] q = new double[prob.length];
        for (int i = 0; i < prob.length; ++i) {
            q[i] = prob[i] * prob.length;
        }
        final int[] a = new int[prob.length];
        for (int j = 0; j < prob.length; ++j) {
            a[j] = j;
        }
        final int[] HL = new int[prob.length];
        int head = 0;
        int tail = prob.length - 1;
        for (int k = 0; k < prob.length; ++k) {
            if (q[k] >= 1.0) {
                HL[head++] = k;
            }
            else {
                HL[tail--] = k;
            }
        }
        while (head != 0 && tail != prob.length - 1) {
            final int l = HL[tail + 1];
            final int m = HL[head - 1];
            a[l] = m;
            final double[] array = q;
            final int n2 = m;
            array[n2] += q[l] - 1.0;
            ++tail;
            if (q[m] < 1.0) {
                HL[tail--] = m;
                --head;
            }
        }
        final int[] ans = new int[n];
        for (int i2 = 0; i2 < n; ++i2) {
            double rU = random() * prob.length;
            final int k2 = (int)rU;
            rU -= k2;
            if (rU < q[k2]) {
                ans[i2] = k2;
            }
            else {
                ans[i2] = a[k2];
            }
        }
        return ans;
    }
    
    public static double random() {
        return MathExModified.random.get().nextDouble();
    }
    
    public static double[] random(final int n) {
        final double[] x = new double[n];
        MathExModified.random.get().nextDoubles(x);
        return x;
    }
    
    public static double random(final double lo, final double hi) {
        return MathExModified.random.get().nextDouble(lo, hi);
    }
    
    public static double[] random(final double lo, final double hi, final int n) {
        final double[] x = new double[n];
        MathExModified.random.get().nextDoubles(x, lo, hi);
        return x;
    }
    
    public static long randomLong() {
        return MathExModified.random.get().nextLong();
    }
    
    public static int randomInt(final int n) {
        return MathExModified.random.get().nextInt(n);
    }
    
    public static int randomInt(final int lo, final int hi) {
        final int w = hi - lo;
        return lo + MathExModified.random.get().nextInt(w);
    }
    
    public static int[] permutate(final int n) {
        return MathExModified.random.get().permutate(n);
    }
    
    public static void permutate(final int[] x) {
        MathExModified.random.get().permutate(x);
    }
    
    public static void permutate(final float[] x) {
        MathExModified.random.get().permutate(x);
    }
    
    public static void permutate(final double[] x) {
        MathExModified.random.get().permutate(x);
    }
    
    public static void permutate(final Object[] x) {
        MathExModified.random.get().permutate(x);
    }
    
    public static int softmax(final double[] posteriori) {
        return softmax(posteriori, posteriori.length);
    }
    
    public static int softmax(final double[] x, final int k) {
        int y = -1;
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < k; ++i) {
            if (x[i] > max) {
                max = x[i];
                y = i;
            }
        }
        double Z = 0.0;
        for (int j = 0; j < k; ++j) {
            final double out = Math.exp(x[j] - max);
            x[j] = out;
            Z += out;
        }
        for (int j = 0; j < k; ++j) {
            final int n = j;
            x[n] /= Z;
        }
        return y;
    }
    
    public static int[] c(final int... x) {
        return x;
    }
    
    public static float[] c(final float... x) {
        return x;
    }
    
    public static double[] c(final double... x) {
        return x;
    }
    
    public static String[] c(final String... x) {
        return x;
    }
    
    public static int[] c(final int[]... list) {
        int n = 0;
        for (final int[] x : list) {
            n += x.length;
        }
        final int[] y = new int[n];
        int pos = 0;
        for (final int[] x2 : list) {
            System.arraycopy(x2, 0, y, pos, x2.length);
            pos += x2.length;
        }
        return y;
    }
    
    public static float[] c(final float[]... list) {
        int n = 0;
        for (final float[] x : list) {
            n += x.length;
        }
        final float[] y = new float[n];
        int pos = 0;
        for (final float[] x2 : list) {
            System.arraycopy(x2, 0, y, pos, x2.length);
            pos += x2.length;
        }
        return y;
    }
    
    public static double[] c(final double[]... list) {
        int n = 0;
        for (final double[] x : list) {
            n += x.length;
        }
        final double[] y = new double[n];
        int pos = 0;
        for (final double[] x2 : list) {
            System.arraycopy(x2, 0, y, pos, x2.length);
            pos += x2.length;
        }
        return y;
    }
    
    public static String[] c(final String[]... list) {
        int n = 0;
        for (final String[] x : list) {
            n += x.length;
        }
        final String[] y = new String[n];
        int pos = 0;
        for (final String[] x2 : list) {
            System.arraycopy(x2, 0, y, pos, x2.length);
            pos += x2.length;
        }
        return y;
    }
    
    public static int[] cbind(final int[]... x) {
        return c(x);
    }
    
    public static float[] cbind(final float[]... x) {
        return c(x);
    }
    
    public static double[] cbind(final double[]... x) {
        return c(x);
    }
    
    public static String[] cbind(final String[]... x) {
        return c(x);
    }
    
    public static int[][] rbind(final int[]... x) {
        return x;
    }
    
    public static float[][] rbind(final float[]... x) {
        return x;
    }
    
    public static double[][] rbind(final double[]... x) {
        return x;
    }
    
    public static String[][] rbind(final String[]... x) {
        return x;
    }
    
    public static <E> E[] slice(final E[] data, final int[] index) {
        final int n = index.length;
        final Object[] x = (Object[])Array.newInstance(data.getClass().getComponentType(), n);
        for (int i = 0; i < n; ++i) {
            x[i] = data[index[i]];
        }
        return (E[])x;
    }
    
    public static int[] slice(final int[] data, final int[] index) {
        final int n = index.length;
        final int[] x = new int[n];
        for (int i = 0; i < n; ++i) {
            x[i] = data[index[i]];
        }
        return x;
    }
    
    public static float[] slice(final float[] data, final int[] index) {
        final int n = index.length;
        final float[] x = new float[n];
        for (int i = 0; i < n; ++i) {
            x[i] = data[index[i]];
        }
        return x;
    }
    
    public static double[] slice(final double[] data, final int[] index) {
        final int n = index.length;
        final double[] x = new double[n];
        for (int i = 0; i < n; ++i) {
            x[i] = data[index[i]];
        }
        return x;
    }
    
    public static boolean contains(final double[][] polygon, final double[] point) {
        return contains(polygon, point[0], point[1]);
    }
    
    public static boolean contains(final double[][] polygon, final double x, final double y) {
        if (polygon.length <= 2) {
            return false;
        }
        int hits = 0;
        final int n = polygon.length;
        double lastx = polygon[n - 1][0];
        double lasty = polygon[n - 1][1];
        for (int i = 0; i < n; ++i) {
            final double curx = polygon[i][0];
            final double cury = polygon[i][1];
            Label_0225: {
                if (cury != lasty) {
                    double leftx;
                    if (curx < lastx) {
                        if (x >= lastx) {
                            break Label_0225;
                        }
                        leftx = curx;
                    }
                    else {
                        if (x >= curx) {
                            break Label_0225;
                        }
                        leftx = lastx;
                    }
                    double test1;
                    double test2;
                    if (cury < lasty) {
                        if (y < cury) {
                            break Label_0225;
                        }
                        if (y >= lasty) {
                            break Label_0225;
                        }
                        if (x < leftx) {
                            ++hits;
                            break Label_0225;
                        }
                        test1 = x - curx;
                        test2 = y - cury;
                    }
                    else {
                        if (y < lasty) {
                            break Label_0225;
                        }
                        if (y >= cury) {
                            break Label_0225;
                        }
                        if (x < leftx) {
                            ++hits;
                            break Label_0225;
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
        return (hits & 0x1) != 0x0;
    }
    
    public static int[] omit(final int[] a, final int value) {
        int n = 0;
        for (final int x : a) {
            if (x != value) {
                ++n;
            }
        }
        int i = 0;
        final int[] b = new int[n];
        for (final int x2 : a) {
            if (x2 != value) {
                b[i++] = x2;
            }
        }
        return b;
    }
    
    public static float[] omit(final float[] a, final float value) {
        int n = 0;
        for (final float x : a) {
            if (x != value) {
                ++n;
            }
        }
        int i = 0;
        final float[] b = new float[n];
        for (final float x2 : a) {
            if (x2 != value) {
                b[i++] = x2;
            }
        }
        return b;
    }
    
    public static double[] omit(final double[] a, final double value) {
        int n = 0;
        for (final double x : a) {
            if (x != value) {
                ++n;
            }
        }
        int i = 0;
        final double[] b = new double[n];
        for (final double x2 : a) {
            if (x2 != value) {
                b[i++] = x2;
            }
        }
        return b;
    }
    
    public static float[] omitNaN(final float[] a) {
        int n = 0;
        for (final float x : a) {
            if (!Float.isNaN(x)) {
                ++n;
            }
        }
        int i = 0;
        final float[] b = new float[n];
        for (final float x2 : a) {
            if (!Float.isNaN(x2)) {
                b[i++] = x2;
            }
        }
        return b;
    }
    
    public static double[] omitNaN(final double[] a) {
        int n = 0;
        for (final double x : a) {
            if (!Double.isNaN(x)) {
                ++n;
            }
        }
        int i = 0;
        final double[] b = new double[n];
        for (final double x2 : a) {
            if (!Double.isNaN(x2)) {
                b[i++] = x2;
            }
        }
        return b;
    }
    
    public static void reverse(final int[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            SortModified.swap(a, i++, j--);
        }
    }
    
    public static void reverse(final float[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            SortModified.swap(a, i++, j--);
        }
    }
    
    public static void reverse(final double[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            SortModified.swap(a, i++, j--);
        }
    }
    
    public static <T> void reverse(final T[] a) {
        int i = 0;
        int j = a.length - 1;
        while (i < j) {
            SortModified.swap(a, i++, j--);
        }
    }
    
    public static int mode(final int[] a) {
        Arrays.sort(a);
        int mode = -1;
        int count = 0;
        int currentValue = a[0];
        int currentCount = 1;
        for (int i = 1; i < a.length; ++i) {
            if (a[i] != currentValue) {
                if (currentCount > count) {
                    mode = currentValue;
                    count = currentCount;
                }
                currentValue = a[i];
                currentCount = 1;
            }
            else {
                ++currentCount;
            }
        }
        if (currentCount > count) {
            mode = currentValue;
        }
        return mode;
    }
    
    public static int min(final int a, final int b, final int c) {
        return Math.min(Math.min(a, b), c);
    }
    
    public static float min(final float a, final float b, final float c) {
        return Math.min(Math.min(a, b), c);
    }
    
    public static double min(final double a, final double b, final double c) {
        return Math.min(Math.min(a, b), c);
    }
    
    public static int min(final int a, final int b, final int c, final int d) {
        return Math.min(Math.min(Math.min(a, b), c), d);
    }
    
    public static float min(final float a, final float b, final float c, final float d) {
        return Math.min(Math.min(Math.min(a, b), c), d);
    }
    
    public static double min(final double a, final double b, final double c, final double d) {
        return Math.min(Math.min(Math.min(a, b), c), d);
    }
    
    public static int max(final int a, final int b, final int c) {
        return Math.max(Math.max(a, b), c);
    }
    
    public static float max(final float a, final float b, final float c) {
        return Math.max(Math.max(a, b), c);
    }
    
    public static double max(final double a, final double b, final double c) {
        return Math.max(Math.max(a, b), c);
    }
    
    public static int max(final int a, final int b, final int c, final int d) {
        return Math.max(Math.max(Math.max(a, b), c), d);
    }
    
    public static float max(final float a, final float b, final float c, final float d) {
        return Math.max(Math.max(Math.max(a, b), c), d);
    }
    
    public static double max(final double a, final double b, final double c, final double d) {
        return Math.max(Math.max(Math.max(a, b), c), d);
    }
    
    public static int min(final int[] x) {
        int min = x[0];
        for (final int n : x) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }
    
    public static float min(final float[] x) {
        float min = Float.POSITIVE_INFINITY;
        for (final float n : x) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }
    
    public static double min(final double[] x) {
        double min = Double.POSITIVE_INFINITY;
        for (final double n : x) {
            if (n < min) {
                min = n;
            }
        }
        return min;
    }
    
    public static int whichMin(final int[] x) {
        int min = x[0];
        int which = 0;
        for (int i = 1; i < x.length; ++i) {
            if (x[i] < min) {
                min = x[i];
                which = i;
            }
        }
        return which;
    }
    
    public static int whichMin(final float[] x) {
        float min = Float.POSITIVE_INFINITY;
        int which = 0;
        for (int i = 0; i < x.length; ++i) {
            if (x[i] < min) {
                min = x[i];
                which = i;
            }
        }
        return which;
    }
    
    public static int whichMin(final double[] x) {
        double min = Double.POSITIVE_INFINITY;
        int which = 0;
        for (int i = 0; i < x.length; ++i) {
            if (x[i] < min) {
                min = x[i];
                which = i;
            }
        }
        return which;
    }
    
    public static int max(final int[] x) {
        int max = x[0];
        for (final int n : x) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }
    
    public static float max(final float[] x) {
        float max = Float.NEGATIVE_INFINITY;
        for (final float n : x) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }
    
    public static double max(final double[] x) {
        double max = Double.NEGATIVE_INFINITY;
        for (final double n : x) {
            if (n > max) {
                max = n;
            }
        }
        return max;
    }
    
    public static int whichMax(final int[] x) {
        int max = x[0];
        int which = 0;
        for (int i = 1; i < x.length; ++i) {
            if (x[i] > max) {
                max = x[i];
                which = i;
            }
        }
        return which;
    }
    
    public static int whichMax(final float[] x) {
        float max = Float.NEGATIVE_INFINITY;
        int which = 0;
        for (int i = 0; i < x.length; ++i) {
            if (x[i] > max) {
                max = x[i];
                which = i;
            }
        }
        return which;
    }
    
    public static int whichMax(final double[] x) {
        double max = Double.NEGATIVE_INFINITY;
        int which = 0;
        for (int i = 0; i < x.length; ++i) {
            if (x[i] > max) {
                max = x[i];
                which = i;
            }
        }
        return which;
    }
    
    public static int min(final int[][] matrix) {
        int min = matrix[0][0];
        for (final int[] x : matrix) {
            int[] array;
            for (int length2 = (array = x).length, j = 0; j < length2; ++j) {
                final int y = array[j];
                if (min > y) {
                    min = y;
                }
            }
        }
        return min;
    }
    
    public static double min(final double[][] matrix) {
        double min = Double.POSITIVE_INFINITY;
        for (final double[] x : matrix) {
            double[] array;
            for (int length2 = (array = x).length, j = 0; j < length2; ++j) {
                final double y = array[j];
                if (min > y) {
                    min = y;
                }
            }
        }
        return min;
    }
    
    public static int max(final int[][] matrix) {
        int max = matrix[0][0];
        for (final int[] x : matrix) {
            int[] array;
            for (int length2 = (array = x).length, j = 0; j < length2; ++j) {
                final int y = array[j];
                if (max < y) {
                    max = y;
                }
            }
        }
        return max;
    }
    
    public static double max(final double[][] matrix) {
        double max = Double.NEGATIVE_INFINITY;
        for (final double[] x : matrix) {
            double[] array;
            for (int length2 = (array = x).length, j = 0; j < length2; ++j) {
                final double y = array[j];
                if (max < y) {
                    max = y;
                }
            }
        }
        return max;
    }
    
    public static IntPair whichMin(final double[][] matrix) {
        double min = Double.POSITIVE_INFINITY;
        int whichRow = 0;
        int whichCol = 0;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                    whichRow = i;
                    whichCol = j;
                }
            }
        }
        return new IntPair(whichRow, whichCol);
    }
    
    public static IntPair whichMax(final double[][] matrix) {
        double max = Double.NEGATIVE_INFINITY;
        int whichRow = 0;
        int whichCol = 0;
        for (int i = 0; i < matrix.length; ++i) {
            for (int j = 0; j < matrix[i].length; ++j) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                    whichRow = i;
                    whichCol = j;
                }
            }
        }
        return new IntPair(whichRow, whichCol);
    }
    
    public static double[][] transpose(final double[][] matrix) {
        final int m = matrix.length;
        final int n = matrix[0].length;
        final double[][] t = new double[n][m];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                t[j][i] = matrix[i][j];
            }
        }
        return t;
    }
    
    public static int[] rowMin(final int[][] matrix) {
        final int[] x = new int[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = min(matrix[i]);
        }
        return x;
    }
    
    public static int[] rowMax(final int[][] matrix) {
        final int[] x = new int[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = max(matrix[i]);
        }
        return x;
    }
    
    public static long[] rowSums(final int[][] matrix) {
        final long[] x = new long[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = sum(matrix[i]);
        }
        return x;
    }
    
    public static double[] rowMin(final double[][] matrix) {
        final double[] x = new double[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = min(matrix[i]);
        }
        return x;
    }
    
    public static double[] rowMax(final double[][] matrix) {
        final double[] x = new double[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = max(matrix[i]);
        }
        return x;
    }
    
    public static double[] rowSums(final double[][] matrix) {
        final double[] x = new double[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = sum(matrix[i]);
        }
        return x;
    }
    
    public static double[] rowMeans(final double[][] matrix) {
        final double[] x = new double[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = mean(matrix[i]);
        }
        return x;
    }
    
    public static double[] rowSds(final double[][] matrix) {
        final double[] x = new double[matrix.length];
        for (int i = 0; i < x.length; ++i) {
            x[i] = sd(matrix[i]);
        }
        return x;
    }
    
    public static int[] colMin(final int[][] matrix) {
        final int[] x = new int[matrix[0].length];
        Arrays.fill(x, Integer.MAX_VALUE);
        for (final int[] row : matrix) {
            for (int j = 0; j < x.length; ++j) {
                if (x[j] > row[j]) {
                    x[j] = row[j];
                }
            }
        }
        return x;
    }
    
    public static int[] colMax(final int[][] matrix) {
        final int[] x = new int[matrix[0].length];
        Arrays.fill(x, Integer.MIN_VALUE);
        for (final int[] row : matrix) {
            for (int j = 0; j < x.length; ++j) {
                if (x[j] < row[j]) {
                    x[j] = row[j];
                }
            }
        }
        return x;
    }
    
    public static long[] colSums(final int[][] matrix) {
        final long[] x = new long[matrix[0].length];
        for (final int[] row : matrix) {
            for (int j = 0; j < x.length; ++j) {
                final long[] array = x;
                final int n = j;
                array[n] += row[j];
            }
        }
        return x;
    }
    
    public static double[] colMin(final double[][] matrix) {
        final double[] x = new double[matrix[0].length];
        Arrays.fill(x, Double.POSITIVE_INFINITY);
        for (final double[] row : matrix) {
            for (int j = 0; j < x.length; ++j) {
                if (x[j] > row[j]) {
                    x[j] = row[j];
                }
            }
        }
        return x;
    }
    
    public static double[] colMax(final double[][] matrix) {
        final double[] x = new double[matrix[0].length];
        Arrays.fill(x, Double.NEGATIVE_INFINITY);
        for (final double[] row : matrix) {
            for (int j = 0; j < x.length; ++j) {
                if (x[j] < row[j]) {
                    x[j] = row[j];
                }
            }
        }
        return x;
    }
    
    public static double[] colSums(final double[][] matrix) {
        final double[] x = matrix[0].clone();
        for (int i = 1; i < matrix.length; ++i) {
            for (int j = 0; j < x.length; ++j) {
                final double[] array = x;
                final int n = j;
                array[n] += matrix[i][j];
            }
        }
        return x;
    }
    
    public static double[] colMeans(final double[][] matrix) {
        final double[] x = matrix[0].clone();
        for (int i = 1; i < matrix.length; ++i) {
            for (int j = 0; j < x.length; ++j) {
                final double[] array = x;
                final int n = j;
                array[n] += matrix[i][j];
            }
        }
        scale(1.0 / matrix.length, x);
        return x;
    }
    
    public static double[] colSds(final double[][] matrix) {
        if (matrix.length < 2) {
            throw new IllegalArgumentException("matrix length is less than 2.");
        }
        final int p = matrix[0].length;
        final double[] sum = new double[p];
        final double[] sumsq = new double[p];
        for (final double[] row : matrix) {
            for (int i = 0; i < p; ++i) {
                final double[] array = sum;
                final int n2 = i;
                array[n2] += row[i];
                final double[] array2 = sumsq;
                final int n3 = i;
                array2[n3] += row[i] * row[i];
            }
        }
        final int n = matrix.length - 1;
        for (int j = 0; j < p; ++j) {
            sumsq[j] = Math.sqrt(sumsq[j] / n - sum[j] / matrix.length * (sum[j] / n));
        }
        return sumsq;
    }
    
    public static int sum(final byte[] x) {
        int sum = 0;
        for (final int n : x) {
            sum += n;
        }
        return sum;
    }
    
    public static long sum(final int[] x) {
        long sum = 0L;
        for (final int n : x) {
            sum += n;
        }
        return (int)sum;
    }
    
    public static double sum(final float[] x) {
        double sum = 0.0;
        for (final float n : x) {
            sum += n;
        }
        return sum;
    }
    
    public static double sum(final double[] x) {
        double sum = 0.0;
        for (final double n : x) {
            sum += n;
        }
        return sum;
    }
    
    public static int median(final int[] x) {
        return QuickSelectModified.median(x);
    }
    
    public static float median(final float[] x) {
        return QuickSelectModified.median(x);
    }
    
    public static double median(final double[] x) {
        return QuickSelectModified.median(x);
    }
    
    public static <T extends Comparable<? super T>> T median(final T[] x) {
        return QuickSelectModified.<T>median(x);
    }
    
    public static int q1(final int[] x) {
        return QuickSelectModified.q1(x);
    }
    
    public static float q1(final float[] x) {
        return QuickSelectModified.q1(x);
    }
    
    public static double q1(final double[] x) {
        return QuickSelectModified.q1(x);
    }
    
    public static <T extends Comparable<? super T>> T q1(final T[] x) {
        return QuickSelectModified.<T>q1(x);
    }
    
    public static int q3(final int[] x) {
        return QuickSelectModified.q3(x);
    }
    
    public static float q3(final float[] x) {
        return QuickSelectModified.q3(x);
    }
    
    public static double q3(final double[] x) {
        return QuickSelectModified.q3(x);
    }
    
    public static <T extends Comparable<? super T>> T q3(final T[] x) {
        return QuickSelectModified.<T>q3(x);
    }
    
    public static double mean(final int[] x) {
        return sum(x) / (double)x.length;
    }
    
    public static double mean(final float[] x) {
        return sum(x) / x.length;
    }
    
    public static double mean(final double[] x) {
        return sum(x) / x.length;
    }
    
    public static double var(final int[] x) {
        if (x.length < 2) {
            throw new IllegalArgumentException("Array length is less than 2.");
        }
        double sum = 0.0;
        double sumsq = 0.0;
        for (final int xi : x) {
            sum += xi;
            sumsq += xi * xi;
        }
        final int n = x.length - 1;
        return sumsq / n - sum / x.length * (sum / n);
    }
    
    public static double var(final float[] x) {
        if (x.length < 2) {
            throw new IllegalArgumentException("Array length is less than 2.");
        }
        double sum = 0.0;
        double sumsq = 0.0;
        for (final float xi : x) {
            sum += xi;
            sumsq += xi * xi;
        }
        final int n = x.length - 1;
        return sumsq / n - sum / x.length * (sum / n);
    }
    
    public static double var(final double[] x) {
        if (x.length < 2) {
            throw new IllegalArgumentException("Array length is less than 2.");
        }
        double sum = 0.0;
        double sumsq = 0.0;
        for (final double xi : x) {
            sum += xi;
            sumsq += xi * xi;
        }
        final int n = x.length - 1;
        return sumsq / n - sum / x.length * (sum / n);
    }
    
    public static double sd(final int[] x) {
        return Math.sqrt(var(x));
    }
    
    public static double sd(final float[] x) {
        return Math.sqrt(var(x));
    }
    
    public static double sd(final double[] x) {
        return Math.sqrt(var(x));
    }
    
    public static double mad(final int[] x) {
        final int m = median(x);
        for (int i = 0; i < x.length; ++i) {
            x[i] = Math.abs(x[i] - m);
        }
        return median(x);
    }
    
    public static double mad(final float[] x) {
        final float m = median(x);
        for (int i = 0; i < x.length; ++i) {
            x[i] = Math.abs(x[i] - m);
        }
        return median(x);
    }
    
    public static double mad(final double[] x) {
        final double m = median(x);
        for (int i = 0; i < x.length; ++i) {
            x[i] = Math.abs(x[i] - m);
        }
        return median(x);
    }
    
    public static double distance(final int[] x, final int[] y) {
        return Math.sqrt(squaredDistance(x, y));
    }
    
    public static double distance(final float[] x, final float[] y) {
        return Math.sqrt(squaredDistance(x, y));
    }
    
    public static double distance(final double[] x, final double[] y) {
        return Math.sqrt(squaredDistance(x, y));
    }
    
    public static double distance(final SparseArrayModified x, final SparseArrayModified y) {
        return Math.sqrt(squaredDistance(x, y));
    }
    
    public static double squaredDistance(final int[] x, final int[] y) {
        double d = 0.0;
        int p1 = 0;
        int p2 = 0;
        while (p1 < x.length && p2 < y.length) {
            final int i1 = x[p1];
            final int i2 = y[p2];
            if (i1 == i2) {
                ++p1;
                ++p2;
            }
            else if (i1 > i2) {
                ++d;
                ++p2;
            }
            else {
                ++d;
                ++p1;
            }
        }
        d += x.length - p1;
        d += y.length - p2;
        return d;
    }
    
    public static double squaredDistance(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        switch (x.length) {
            case 2: {
                final double d0 = x[0] - (double)y[0];
                final double d2 = x[1] - (double)y[1];
                return d0 * d0 + d2 * d2;
            }
            case 3: {
                final double d0 = x[0] - (double)y[0];
                final double d2 = x[1] - (double)y[1];
                final double d3 = x[2] - (double)y[2];
                return d0 * d0 + d2 * d2 + d3 * d3;
            }
            case 4: {
                final double d0 = x[0] - (double)y[0];
                final double d2 = x[1] - (double)y[1];
                final double d3 = x[2] - (double)y[2];
                final double d4 = x[3] - (double)y[3];
                return d0 * d0 + d2 * d2 + d3 * d3 + d4 * d4;
            }
            default: {
                double sum = 0.0;
                for (int i = 0; i < x.length; ++i) {
                    final double d5 = x[i] - (double)y[i];
                    sum += d5 * d5;
                }
                return sum;
            }
        }
    }
    
    public static double squaredDistance(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        switch (x.length) {
            case 2: {
                final double d0 = x[0] - y[0];
                final double d2 = x[1] - y[1];
                return d0 * d0 + d2 * d2;
            }
            case 3: {
                final double d0 = x[0] - y[0];
                final double d2 = x[1] - y[1];
                final double d3 = x[2] - y[2];
                return d0 * d0 + d2 * d2 + d3 * d3;
            }
            case 4: {
                final double d0 = x[0] - y[0];
                final double d2 = x[1] - y[1];
                final double d3 = x[2] - y[2];
                final double d4 = x[3] - y[3];
                return d0 * d0 + d2 * d2 + d3 * d3 + d4 * d4;
            }
            default: {
                double sum = 0.0;
                for (int i = 0; i < x.length; ++i) {
                    final double d5 = x[i] - y[i];
                    sum += d5 * d5;
                }
                return sum;
            }
        }
    }
    
    public static double squaredDistance(final SparseArrayModified x, final SparseArrayModified y) {
        final Iterator<SparseArrayModified.Entry> it1 = x.iterator();
        final Iterator<SparseArrayModified.Entry> it2 = y.iterator();
        SparseArrayModified.Entry e1 = it1.hasNext() ? it1.next() : null;
        SparseArrayModified.Entry e2 = it2.hasNext() ? it2.next() : null;
        double sum = 0.0;
        while (e1 != null) {
            if (e2 == null) {
                break;
            }
            if (e1.i == e2.i) {
                sum += pow2(e1.x - e2.x);
                e1 = (it1.hasNext() ? it1.next() : null);
                e2 = (it2.hasNext() ? it2.next() : null);
            }
            else if (e1.i > e2.i) {
                sum += pow2(e2.x);
                e2 = (it2.hasNext() ? it2.next() : null);
            }
            else {
                sum += pow2(e1.x);
                e1 = (it1.hasNext() ? it1.next() : null);
            }
        }
        while (it1.hasNext()) {
            final double d = it1.next().x;
            sum += d * d;
        }
        while (it2.hasNext()) {
            final double d = it2.next().x;
            sum += d * d;
        }
        return sum;
    }
    
    public static double squaredDistanceWithMissingValues(final double[] x, final double[] y) {
        final int n = x.length;
        int m = 0;
        double dist = 0.0;
        for (int i = 0; i < n; ++i) {
            if (!Double.isNaN(x[i]) && !Double.isNaN(y[i])) {
                ++m;
                final double d = x[i] - y[i];
                dist += d * d;
            }
        }
        if (m == 0) {
            dist = Double.MAX_VALUE;
        }
        else {
            dist = n * dist / m;
        }
        return dist;
    }
    
    public static MatrixModified pdist(final int[][] x) {
        return pdist(x, false);
    }
    
    public static MatrixModified pdist(final int[][] x, final boolean squared) {
        final int n = x.length;
        final double[][] dist = new double[n][n];
        MathExModified.<int[]>pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
        return MatrixModified.of(dist);
    }
    
    public static MatrixModified pdist(final float[][] x) {
        return pdist(x, false);
    }
    
    public static MatrixModified pdist(final float[][] x, final boolean squared) {
        final int n = x.length;
        final double[][] dist = new double[n][n];
        MathExModified.<float[]>pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
        return MatrixModified.of(dist);
    }
    
    public static MatrixModified pdist(final double[][] x) {
        return pdist(x, false);
    }
    
    public static MatrixModified pdist(final double[][] x, final boolean squared) {
        final int n = x.length;
        final double[][] dist = new double[n][n];
        MathExModified.<double[]>pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
        return MatrixModified.of(dist);
    }
    
    public static MatrixModified pdist(final SparseArrayModified[] x) {
        return pdist(x, false);
    }
    
    public static MatrixModified pdist(final SparseArrayModified[] x, final boolean squared) {
        final int n = x.length;
        final double[][] dist = new double[n][n];
        MathExModified.<SparseArrayModified>pdist(x, dist, squared ? MathExModified::squaredDistance : MathExModified::distance);
        return MatrixModified.of(dist);
    }
    
    public static <T> void pdist(final T[] x, final double[][] d, final DistanceModified<T> distance) {
        final int n = x.length;
        if (d[0].length < n) {
            final Object xi;
            final double[] di;
            int j;
            IntStream.range(0, n).parallel().forEach(i -> {
                xi = x[i];
                di = d[i];
                for (j = 0; j < i; ++j) {
                    di[j] = distance.d((T)xi, x[j]);
                }
            });
        }
        else {
            final Object xi2;
            final double[] di2;
            int k;
            final int n2;
            IntStream.range(0, n).parallel().forEach(i -> {
                xi2 = x[i];
                di2 = d[i];
                for (k = 0; k < n2; ++k) {
                    di2[k] = distance.d((T)xi2, x[k]);
                }
            });
        }
    }
    
    public static double entropy(final double[] p) {
        double h = 0.0;
        for (final double pi : p) {
            if (pi > 0.0) {
                h -= pi * Math.log(pi);
            }
        }
        return h;
    }
    
    public static double KullbackLeiblerDivergence(final double[] p, final double[] q) {
        boolean intersection = false;
        double kl = 0.0;
        for (int i = 0; i < p.length; ++i) {
            if (p[i] != 0.0 && q[i] != 0.0) {
                intersection = true;
                kl += p[i] * Math.log(p[i] / q[i]);
            }
        }
        if (intersection) {
            return kl;
        }
        return Double.POSITIVE_INFINITY;
    }
    
    public static double KullbackLeiblerDivergence(final SparseArrayModified p, final SparseArrayModified q) {
        if (p.isEmpty()) {
            throw new IllegalArgumentException("p is empty.");
        }
        if (q.isEmpty()) {
            throw new IllegalArgumentException("q is empty.");
        }
        final Iterator<SparseArrayModified.Entry> pIter = p.iterator();
        final Iterator<SparseArrayModified.Entry> qIter = q.iterator();
        SparseArrayModified.Entry a = pIter.hasNext() ? pIter.next() : null;
        SparseArrayModified.Entry b = qIter.hasNext() ? qIter.next() : null;
        boolean intersection = false;
        double kl = 0.0;
        while (a != null && b != null) {
            if (a.i < b.i) {
                a = (pIter.hasNext() ? pIter.next() : null);
            }
            else if (a.i > b.i) {
                b = (qIter.hasNext() ? qIter.next() : null);
            }
            else {
                intersection = true;
                kl += a.x * Math.log(a.x / b.x);
                a = (pIter.hasNext() ? pIter.next() : null);
                b = (qIter.hasNext() ? qIter.next() : null);
            }
        }
        if (intersection) {
            return kl;
        }
        return Double.POSITIVE_INFINITY;
    }
    
    public static double KullbackLeiblerDivergence(final double[] p, final SparseArrayModified q) {
        return KullbackLeiblerDivergence(q, p);
    }
    
    public static double KullbackLeiblerDivergence(final SparseArrayModified p, final double[] q) {
        if (p.isEmpty()) {
            throw new IllegalArgumentException("p is empty.");
        }
        final Iterator<SparseArrayModified.Entry> iter = p.iterator();
        boolean intersection = false;
        double kl = 0.0;
        while (iter.hasNext()) {
            final SparseArrayModified.Entry b = iter.next();
            final int i = b.i;
            if (q[i] > 0.0) {
                intersection = true;
                kl += b.x * Math.log(b.x / q[i]);
            }
        }
        if (intersection) {
            return kl;
        }
        return Double.POSITIVE_INFINITY;
    }
    
    public static double JensenShannonDivergence(final double[] p, final double[] q) {
        final double[] m = new double[p.length];
        for (int i = 0; i < m.length; ++i) {
            m[i] = (p[i] + q[i]) / 2.0;
        }
        return (KullbackLeiblerDivergence(p, m) + KullbackLeiblerDivergence(q, m)) / 2.0;
    }
    
    public static double JensenShannonDivergence(final SparseArrayModified p, final SparseArrayModified q) {
        if (p.isEmpty()) {
            throw new IllegalArgumentException("p is empty.");
        }
        if (q.isEmpty()) {
            throw new IllegalArgumentException("q is empty.");
        }
        final Iterator<SparseArrayModified.Entry> pIter = p.iterator();
        final Iterator<SparseArrayModified.Entry> qIter = q.iterator();
        SparseArrayModified.Entry a = pIter.hasNext() ? pIter.next() : null;
        SparseArrayModified.Entry b = qIter.hasNext() ? qIter.next() : null;
        double js = 0.0;
        while (a != null && b != null) {
            if (a.i < b.i) {
                final double mi = a.x / 2.0;
                js += a.x * Math.log(a.x / mi);
                a = (pIter.hasNext() ? pIter.next() : null);
            }
            else if (a.i > b.i) {
                final double mi = b.x / 2.0;
                js += b.x * Math.log(b.x / mi);
                b = (qIter.hasNext() ? qIter.next() : null);
            }
            else {
                final double mi = (a.x + b.x) / 2.0;
                js += a.x * Math.log(a.x / mi) + b.x * Math.log(b.x / mi);
                a = (pIter.hasNext() ? pIter.next() : null);
                b = (qIter.hasNext() ? qIter.next() : null);
            }
        }
        return js / 2.0;
    }
    
    public static double JensenShannonDivergence(final double[] p, final SparseArrayModified q) {
        return JensenShannonDivergence(q, p);
    }
    
    public static double JensenShannonDivergence(final SparseArrayModified p, final double[] q) {
        if (p.isEmpty()) {
            throw new IllegalArgumentException("p is empty.");
        }
        final Iterator<SparseArrayModified.Entry> iter = p.iterator();
        double js = 0.0;
        while (iter.hasNext()) {
            final SparseArrayModified.Entry b = iter.next();
            final int i = b.i;
            final double mi = (b.x + q[i]) / 2.0;
            js += b.x * Math.log(b.x / mi);
            if (q[i] > 0.0) {
                js += q[i] * Math.log(q[i] / mi);
            }
        }
        return js / 2.0;
    }
    
    public static int dot(final int[] x, final int[] y) {
        int sum = 0;
        int p1 = 0;
        int p2 = 0;
        while (p1 < x.length && p2 < y.length) {
            final int i1 = x[p1];
            final int i2 = y[p2];
            if (i1 == i2) {
                ++sum;
                ++p1;
                ++p2;
            }
            else if (i1 > i2) {
                ++p2;
            }
            else {
                ++p1;
            }
        }
        return sum;
    }
    
    public static float dot(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        float sum = 0.0f;
        for (int i = 0; i < x.length; ++i) {
            sum += x[i] * y[i];
        }
        return sum;
    }
    
    public static double dot(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        double sum = 0.0;
        for (int i = 0; i < x.length; ++i) {
            sum += x[i] * y[i];
        }
        return sum;
    }
    
    public static double dot(final SparseArrayModified x, final SparseArrayModified y) {
        final Iterator<SparseArrayModified.Entry> it1 = x.iterator();
        final Iterator<SparseArrayModified.Entry> it2 = y.iterator();
        SparseArrayModified.Entry e1 = it1.hasNext() ? it1.next() : null;
        SparseArrayModified.Entry e2 = it2.hasNext() ? it2.next() : null;
        double sum = 0.0;
        while (e1 != null && e2 != null) {
            if (e1.i == e2.i) {
                sum += e1.x * e2.x;
                e1 = (it1.hasNext() ? it1.next() : null);
                e2 = (it2.hasNext() ? it2.next() : null);
            }
            else if (e1.i > e2.i) {
                e2 = (it2.hasNext() ? it2.next() : null);
            }
            else {
                e1 = (it1.hasNext() ? it1.next() : null);
            }
        }
        return sum;
    }
    
    public static MatrixModified pdot(final int[][] x) {
        final int n = x.length;
        final MatrixModified matrix = new MatrixModified(n, n);
        matrix.uplo(UPLOModified.LOWER);
        final int[] xj;
        int i;
        final int n2;
        final MatrixModified matrixModified;
        IntStream.range(0, n).parallel().forEach(j -> {
            xj = x[j];
            for (i = 0; i < n2; ++i) {
                matrixModified.set(i, j, dot(x[i], xj));
            }
            return;
        });
        return matrix;
    }
    
    public static MatrixModified pdot(final float[][] x) {
        final int n = x.length;
        final MatrixModified matrix = new MatrixModified(n, n);
        matrix.uplo(UPLOModified.LOWER);
        final float[] xj;
        int i;
        final int n2;
        final MatrixModified matrixModified;
        IntStream.range(0, n).parallel().forEach(j -> {
            xj = x[j];
            for (i = 0; i < n2; ++i) {
                matrixModified.set(i, j, dot(x[i], xj));
            }
            return;
        });
        return matrix;
    }
    
    public static MatrixModified pdot(final double[][] x) {
        final int n = x.length;
        final MatrixModified matrix = new MatrixModified(n, n);
        matrix.uplo(UPLOModified.LOWER);
        final double[] xj;
        int i;
        final int n2;
        final MatrixModified matrixModified;
        IntStream.range(0, n).parallel().forEach(j -> {
            xj = x[j];
            for (i = 0; i < n2; ++i) {
                matrixModified.set(i, j, dot(x[i], xj));
            }
            return;
        });
        return matrix;
    }
    
    public static MatrixModified pdot(final SparseArrayModified[] x) {
        final int n = x.length;
        final MatrixModified matrix = new MatrixModified(n, n);
        matrix.uplo(UPLOModified.LOWER);
        final SparseArrayModified xj;
        int i;
        final int n2;
        final MatrixModified matrixModified;
        IntStream.range(0, n).parallel().forEach(j -> {
            xj = x[j];
            for (i = 0; i < n2; ++i) {
                matrixModified.set(i, j, dot(x[i], xj));
            }
            return;
        });
        return matrix;
    }
    
    public static double cov(final int[] x, final int[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("array length has to be at least 3.");
        }
        final double mx = mean(x);
        final double my = mean(y);
        double Sxy = 0.0;
        for (int i = 0; i < x.length; ++i) {
            final double dx = x[i] - mx;
            final double dy = y[i] - my;
            Sxy += dx * dy;
        }
        return Sxy / (x.length - 1);
    }
    
    public static double cov(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("array length has to be at least 3.");
        }
        final double mx = mean(x);
        final double my = mean(y);
        double Sxy = 0.0;
        for (int i = 0; i < x.length; ++i) {
            final double dx = x[i] - mx;
            final double dy = y[i] - my;
            Sxy += dx * dy;
        }
        return Sxy / (x.length - 1);
    }
    
    public static double cov(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("array length has to be at least 3.");
        }
        final double mx = mean(x);
        final double my = mean(y);
        double Sxy = 0.0;
        for (int i = 0; i < x.length; ++i) {
            final double dx = x[i] - mx;
            final double dy = y[i] - my;
            Sxy += dx * dy;
        }
        return Sxy / (x.length - 1);
    }
    
    public static double[][] cov(final double[][] data) {
        return cov(data, colMeans(data));
    }
    
    public static double[][] cov(final double[][] data, final double[] mu) {
        final double[][] sigma = new double[data[0].length][data[0].length];
        for (final double[] datum : data) {
            for (int j = 0; j < mu.length; ++j) {
                for (int k = 0; k <= j; ++k) {
                    final double[] array = sigma[j];
                    final int n3 = k;
                    array[n3] += (datum[j] - mu[j]) * (datum[k] - mu[k]);
                }
            }
        }
        final int n = data.length - 1;
        for (int i = 0; i < mu.length; ++i) {
            for (int l = 0; l <= i; ++l) {
                final double[] array2 = sigma[i];
                final int n4 = l;
                array2[n4] /= n;
                sigma[l][i] = sigma[i][l];
            }
        }
        return sigma;
    }
    
    public static double cor(final int[] x, final int[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("array length has to be at least 3.");
        }
        final double Sxy = cov(x, y);
        final double Sxx = var(x);
        final double Syy = var(y);
        if (Sxx == 0.0 || Syy == 0.0) {
            return Double.NaN;
        }
        return Sxy / Math.sqrt(Sxx * Syy);
    }
    
    public static double cor(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("array length has to be at least 3.");
        }
        final double Sxy = cov(x, y);
        final double Sxx = var(x);
        final double Syy = var(y);
        if (Sxx == 0.0 || Syy == 0.0) {
            return Double.NaN;
        }
        return Sxy / Math.sqrt(Sxx * Syy);
    }
    
    public static double cor(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Arrays have different length.");
        }
        if (x.length < 3) {
            throw new IllegalArgumentException("array length has to be at least 3.");
        }
        final double Sxy = cov(x, y);
        final double Sxx = var(x);
        final double Syy = var(y);
        if (Sxx == 0.0 || Syy == 0.0) {
            return Double.NaN;
        }
        return Sxy / Math.sqrt(Sxx * Syy);
    }
    
    public static double[][] cor(final double[][] data) {
        return cor(data, colMeans(data));
    }
    
    public static double[][] cor(final double[][] data, final double[] mu) {
        final double[][] sigma = cov(data, mu);
        final int n = data[0].length;
        final double[] sd = new double[n];
        for (int i = 0; i < n; ++i) {
            sd[i] = Math.sqrt(sigma[i][i]);
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j <= i; ++j) {
                final double[] array = sigma[i];
                final int n2 = j;
                array[n2] /= sd[i] * sd[j];
                sigma[j][i] = sigma[i][j];
            }
        }
        return sigma;
    }
    
    private static double crank(final double[] w) {
        final int n = w.length;
        double s = 0.0;
        int j = 1;
        while (j < n) {
            if (w[j] != w[j - 1]) {
                w[j - 1] = j;
                ++j;
            }
            else {
                int jt;
                for (jt = j + 1; jt <= n && w[jt - 1] == w[j - 1]; ++jt) {}
                final double rank = 0.5 * (j + jt - 1);
                for (int ji = j; ji <= jt - 1; ++ji) {
                    w[ji - 1] = rank;
                }
                final double t = jt - j;
                s += t * t * t - t;
                j = jt;
            }
        }
        if (j == n) {
            w[n - 1] = n;
        }
        return s;
    }
    
    public static double spearman(final int[] x, final int[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        final int n = x.length;
        final double[] wksp1 = new double[n];
        final double[] wksp2 = new double[n];
        for (int j = 0; j < n; ++j) {
            wksp1[j] = x[j];
            wksp2[j] = y[j];
        }
        QuickSortModified.sort(wksp1, wksp2);
        crank(wksp1);
        QuickSortModified.sort(wksp2, wksp1);
        crank(wksp2);
        return cor(wksp1, wksp2);
    }
    
    public static double spearman(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        final int n = x.length;
        final double[] wksp1 = new double[n];
        final double[] wksp2 = new double[n];
        for (int j = 0; j < n; ++j) {
            wksp1[j] = x[j];
            wksp2[j] = y[j];
        }
        QuickSortModified.sort(wksp1, wksp2);
        crank(wksp1);
        QuickSortModified.sort(wksp2, wksp1);
        crank(wksp2);
        return cor(wksp1, wksp2);
    }
    
    public static double spearman(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        final double[] wksp1 = x.clone();
        final double[] wksp2 = y.clone();
        QuickSortModified.sort(wksp1, wksp2);
        crank(wksp1);
        QuickSortModified.sort(wksp2, wksp1);
        crank(wksp2);
        return cor(wksp1, wksp2);
    }
    
    public static double kendall(final int[] x, final int[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        int is = 0;
        int n2 = 0;
        int n3 = 0;
        for (int n4 = x.length, j = 0; j < n4 - 1; ++j) {
            for (int k = j + 1; k < n4; ++k) {
                final double a1 = x[j] - x[k];
                final double a2 = y[j] - y[k];
                final double aa = a1 * a2;
                if (aa != 0.0) {
                    ++n3;
                    ++n2;
                    if (aa > 0.0) {
                        ++is;
                    }
                    else {
                        --is;
                    }
                }
                else {
                    if (a1 != 0.0) {
                        ++n3;
                    }
                    if (a2 != 0.0) {
                        ++n2;
                    }
                }
            }
        }
        return is / (Math.sqrt(n3) * Math.sqrt(n2));
    }
    
    public static double kendall(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        int is = 0;
        int n2 = 0;
        int n3 = 0;
        for (int n4 = x.length, j = 0; j < n4 - 1; ++j) {
            for (int k = j + 1; k < n4; ++k) {
                final double a1 = x[j] - x[k];
                final double a2 = y[j] - y[k];
                final double aa = a1 * a2;
                if (aa != 0.0) {
                    ++n3;
                    ++n2;
                    if (aa > 0.0) {
                        ++is;
                    }
                    else {
                        --is;
                    }
                }
                else {
                    if (a1 != 0.0) {
                        ++n3;
                    }
                    if (a2 != 0.0) {
                        ++n2;
                    }
                }
            }
        }
        return is / (Math.sqrt(n3) * Math.sqrt(n2));
    }
    
    public static double kendall(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Input vector sizes are different.");
        }
        int is = 0;
        int n2 = 0;
        int n3 = 0;
        for (int n4 = x.length, j = 0; j < n4 - 1; ++j) {
            for (int k = j + 1; k < n4; ++k) {
                final double a1 = x[j] - x[k];
                final double a2 = y[j] - y[k];
                final double aa = a1 * a2;
                if (aa != 0.0) {
                    ++n3;
                    ++n2;
                    if (aa > 0.0) {
                        ++is;
                    }
                    else {
                        --is;
                    }
                }
                else {
                    if (a1 != 0.0) {
                        ++n3;
                    }
                    if (a2 != 0.0) {
                        ++n2;
                    }
                }
            }
        }
        return is / (Math.sqrt(n3) * Math.sqrt(n2));
    }
    
    public static float norm1(final float[] x) {
        float norm = 0.0f;
        for (final float n : x) {
            norm += Math.abs(n);
        }
        return norm;
    }
    
    public static double norm1(final double[] x) {
        double norm = 0.0;
        for (final double n : x) {
            norm += Math.abs(n);
        }
        return norm;
    }
    
    public static float norm2(final float[] x) {
        float norm = 0.0f;
        for (final float n : x) {
            norm += n * n;
        }
        norm = (float)Math.sqrt(norm);
        return norm;
    }
    
    public static double norm2(final double[] x) {
        double norm = 0.0;
        for (final double n : x) {
            norm += n * n;
        }
        norm = Math.sqrt(norm);
        return norm;
    }
    
    public static float normInf(final float[] x) {
        final int n = x.length;
        float f = Math.abs(x[0]);
        for (int i = 1; i < n; ++i) {
            f = Math.max(f, Math.abs(x[i]));
        }
        return f;
    }
    
    public static double normInf(final double[] x) {
        final int n = x.length;
        double f = Math.abs(x[0]);
        for (int i = 1; i < n; ++i) {
            f = Math.max(f, Math.abs(x[i]));
        }
        return f;
    }
    
    public static float norm(final float[] x) {
        return norm2(x);
    }
    
    public static double norm(final double[] x) {
        return norm2(x);
    }
    
    public static float cos(final float[] x, final float[] y) {
        return dot(x, y) / (norm2(x) * norm2(y));
    }
    
    public static double cos(final double[] x, final double[] y) {
        return dot(x, y) / (norm2(x) * norm2(y));
    }
    
    public static void standardize(final double[] x) {
        final double mu = mean(x);
        final double sigma = sd(x);
        if (isZero(sigma)) {
            MathExModified.logger.warn("array has variance of 0.");
            return;
        }
        for (int i = 0; i < x.length; ++i) {
            x[i] = (x[i] - mu) / sigma;
        }
    }
    
    public static void scale(final double[][] x) {
        final int n = x.length;
        final int p = x[0].length;
        final double[] min = colMin(x);
        final double[] max = colMax(x);
        for (int j = 0; j < p; ++j) {
            final double scale = max[j] - min[j];
            if (!isZero(scale)) {
                for (int i = 0; i < n; ++i) {
                    x[i][j] = (x[i][j] - min[j]) / scale;
                }
            }
            else {
                for (int i = 0; i < n; ++i) {
                    x[i][j] = 0.5;
                }
            }
        }
    }
    
    public static void standardize(final double[][] x) {
        final int n = x.length;
        final int p = x[0].length;
        final double[] center = colMeans(x);
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < p; ++j) {
                x[i][j] -= center[j];
            }
        }
        final double[] scale = new double[p];
        for (int j = 0; j < p; ++j) {
            for (final double[] xi : x) {
                final double[] array = scale;
                final int n2 = j;
                array[n2] += pow2(xi[j]);
            }
            scale[j] = Math.sqrt(scale[j] / (n - 1));
            if (!isZero(scale[j])) {
                for (final double[] array2 : x) {
                    final int n3 = j;
                    array2[n3] /= scale[j];
                }
            }
        }
    }
    
    public static void normalize(final double[][] x) {
        normalize(x, false);
    }
    
    public static void normalize(final double[][] x, final boolean centerizing) {
        final int n = x.length;
        final int p = x[0].length;
        if (centerizing) {
            final double[] center = colMeans(x);
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < p; ++j) {
                    x[i][j] -= center[j];
                }
            }
        }
        final double[] scale = new double[p];
        for (int k = 0; k < p; ++k) {
            for (final double[] xi : x) {
                final double[] array = scale;
                final int n2 = k;
                array[n2] += pow2(xi[k]);
            }
            scale[k] = Math.sqrt(scale[k]);
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < p; ++j) {
                if (!isZero(scale[j])) {
                    final double[] array2 = x[i];
                    final int n3 = j;
                    array2[n3] /= scale[j];
                }
            }
        }
    }
    
    public static void unitize(final double[] x) {
        unitize2(x);
    }
    
    public static void unitize1(final double[] x) {
        final double n = norm1(x);
        for (int i = 0; i < x.length; ++i) {
            final int n2 = i;
            x[n2] /= n;
        }
    }
    
    public static void unitize2(final double[] x) {
        final double n = norm(x);
        for (int i = 0; i < x.length; ++i) {
            final int n2 = i;
            x[n2] /= n;
        }
    }
    
    public static boolean equals(final float[] x, final float[] y) {
        return equals(x, y, 1.0E-7f);
    }
    
    public static boolean equals(final float[] x, final float[] y, final float epsilon) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            if (Math.abs(x[i] - y[i]) > epsilon) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final double[] x, final double[] y) {
        return equals(x, y, 1.0E-10);
    }
    
    public static boolean equals(final double[] x, final double[] y, final double epsilon) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        if (epsilon <= 0.0) {
            throw new IllegalArgumentException("Invalid epsilon: " + epsilon);
        }
        for (int i = 0; i < x.length; ++i) {
            if (Math.abs(x[i] - y[i]) > epsilon) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean equals(final float[][] x, final float[][] y) {
        return equals(x, y, 1.0E-7f);
    }
    
    public static boolean equals(final float[][] x, final float[][] y, final float epsilon) {
        if (x.length != y.length || x[0].length != y[0].length) {
            throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
        }
        for (int i = 0; i < x.length; ++i) {
            for (int j = 0; j < x[i].length; ++j) {
                if (Math.abs(x[i][j] - y[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean equals(final double[][] x, final double[][] y) {
        return equals(x, y, 1.0E-10);
    }
    
    public static boolean equals(final double[][] x, final double[][] y, final double epsilon) {
        if (x.length != y.length || x[0].length != y[0].length) {
            throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
        }
        if (epsilon <= 0.0) {
            throw new IllegalArgumentException("Invalid epsilon: " + epsilon);
        }
        for (int i = 0; i < x.length; ++i) {
            for (int j = 0; j < x[i].length; ++j) {
                if (Math.abs(x[i][j] - y[i][j]) > epsilon) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static boolean isZero(final float x) {
        return isZero(x, MathExModified.FLOAT_EPSILON);
    }
    
    public static boolean isZero(final float x, final float epsilon) {
        return Math.abs(x) < epsilon;
    }
    
    public static boolean isZero(final double x) {
        return isZero(x, MathExModified.EPSILON);
    }
    
    public static boolean isZero(final double x, final double epsilon) {
        return Math.abs(x) < epsilon;
    }
    
    public static int[][] clone(final int[][] x) {
        final int[][] matrix = new int[x.length][];
        for (int i = 0; i < x.length; ++i) {
            matrix[i] = x[i].clone();
        }
        return matrix;
    }
    
    public static float[][] clone(final float[][] x) {
        final float[][] matrix = new float[x.length][];
        for (int i = 0; i < x.length; ++i) {
            matrix[i] = x[i].clone();
        }
        return matrix;
    }
    
    public static double[][] clone(final double[][] x) {
        final double[][] matrix = new double[x.length][];
        for (int i = 0; i < x.length; ++i) {
            matrix[i] = x[i].clone();
        }
        return matrix;
    }
    
    public static void swap(final int[] x, final int i, final int j) {
        final int s = x[i];
        x[i] = x[j];
        x[j] = s;
    }
    
    public static void swap(final float[] x, final int i, final int j) {
        final float s = x[i];
        x[i] = x[j];
        x[j] = s;
    }
    
    public static void swap(final double[] x, final int i, final int j) {
        final double s = x[i];
        x[i] = x[j];
        x[j] = s;
    }
    
    public static void swap(final Object[] x, final int i, final int j) {
        final Object s = x[i];
        x[i] = x[j];
        x[j] = s;
    }
    
    public static void swap(final int[] x, final int[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final int s = x[i];
            x[i] = y[i];
            y[i] = s;
        }
    }
    
    public static void swap(final float[] x, final float[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final float s = x[i];
            x[i] = y[i];
            y[i] = s;
        }
    }
    
    public static void swap(final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final double s = x[i];
            x[i] = y[i];
            y[i] = s;
        }
    }
    
    public static <E> void swap(final E[] x, final E[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final E s = x[i];
            x[i] = y[i];
            y[i] = s;
        }
    }
    
    public static void copy(final int[][] x, final int[][] y) {
        if (x.length != y.length || x[0].length != y[0].length) {
            throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
        }
        for (int i = 0; i < x.length; ++i) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
        }
    }
    
    public static void copy(final float[][] x, final float[][] y) {
        if (x.length != y.length || x[0].length != y[0].length) {
            throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
        }
        for (int i = 0; i < x.length; ++i) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
        }
    }
    
    public static void copy(final double[][] x, final double[][] y) {
        if (x.length != y.length || x[0].length != y[0].length) {
            throw new IllegalArgumentException(String.format("Matrices have different rows: %d x %d vs %d x %d", x.length, x[0].length, y.length, y[0].length));
        }
        for (int i = 0; i < x.length; ++i) {
            System.arraycopy(x[i], 0, y[i], 0, x[i].length);
        }
    }
    
    public static void add(final double[] y, final double[] x) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final int n = i;
            y[n] += x[i];
        }
    }
    
    public static void sub(final double[] y, final double[] x) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final int n = i;
            y[n] -= x[i];
        }
    }
    
    public static void scale(final double a, final double[] x) {
        for (int i = 0; i < x.length; ++i) {
            final int n = i;
            x[n] *= a;
        }
    }
    
    public static void scale(final double a, final double[] x, final double[] y) {
        for (int i = 0; i < x.length; ++i) {
            y[i] = a * x[i];
        }
    }
    
    public static double[] axpy(final double a, final double[] x, final double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException(String.format("Arrays have different length: x[%d], y[%d]", x.length, y.length));
        }
        for (int i = 0; i < x.length; ++i) {
            final int n = i;
            y[n] += a * x[i];
        }
        return y;
    }
    
    public static double[] pow(final double[] x, final double n) {
        final double[] y = new double[x.length];
        for (int i = 0; i < x.length; ++i) {
            y[i] = Math.pow(x[i], n);
        }
        return y;
    }
    
    public static int[] unique(final int[] x) {
        return Arrays.stream(x).distinct().toArray();
    }
    
    public static String[] unique(final String[] x) {
        return Arrays.<String>stream(x).distinct().<String>toArray(String[]::new);
    }
    
    public static double[] solve(final double[] a, final double[] b, final double[] c, final double[] r) {
        if (b[0] == 0.0) {
            throw new IllegalArgumentException("Invalid value of b[0] == 0. The equations should be rewritten as a set of order n - 1.");
        }
        final int n = a.length;
        final double[] u = new double[n];
        final double[] gam = new double[n];
        double bet = b[0];
        u[0] = r[0] / bet;
        for (int j = 1; j < n; ++j) {
            gam[j] = c[j - 1] / bet;
            bet = b[j] - a[j] * gam[j];
            if (bet == 0.0) {
                throw new IllegalArgumentException("The tridagonal matrix is not of diagonal dominance.");
            }
            u[j] = (r[j] - a[j] * u[j - 1]) / bet;
        }
        for (int j = n - 2; j >= 0; --j) {
            final double[] array = u;
            final int n2 = j;
            array[n2] -= gam[j + 1] * u[j + 1];
        }
        return u;
    }
    
    static /* synthetic */ void access$2(final int nextSeed) {
        MathExModified.nextSeed = nextSeed;
    }
    
    private static class FPU
    {
        int RADIX;
        int DIGITS;
        int FLOAT_DIGITS;
        int ROUND_STYLE;
        int MACHEP;
        int FLOAT_MACHEP;
        int NEGEP;
        int FLOAT_NEGEP;
        float FLOAT_EPSILON;
        double EPSILON;
        
        FPU() {
            this.FLOAT_DIGITS = 24;
            this.FLOAT_MACHEP = -23;
            this.FLOAT_NEGEP = -24;
            this.FLOAT_EPSILON = (float)Math.pow(2.0, this.FLOAT_MACHEP);
            final double ONE = 1.0;
            final double TWO = ONE + ONE;
            final double ZERO = ONE - ONE;
            double a = ONE;
            double temp2;
            for (double temp1 = ONE; temp1 - ONE == ZERO; temp1 = temp2 - a) {
                a += a;
                temp2 = a + ONE;
            }
            double b = ONE;
            int itemp;
            for (itemp = 0; itemp == 0; itemp = (int)(temp2 - a)) {
                b += b;
                temp2 = a + b;
            }
            this.RADIX = itemp;
            final double beta = this.RADIX;
            this.DIGITS = 0;
            b = ONE;
            for (double temp1 = ONE; temp1 - ONE == ZERO; temp1 = temp2 - b) {
                ++this.DIGITS;
                b *= beta;
                temp2 = b + ONE;
            }
            this.ROUND_STYLE = 0;
            final double betah = beta / TWO;
            temp2 = a + betah;
            if (temp2 - a != ZERO) {
                this.ROUND_STYLE = 1;
            }
            final double tempa = a + beta;
            temp2 = tempa + betah;
            if (this.ROUND_STYLE == 0 && temp2 - tempa != ZERO) {
                this.ROUND_STYLE = 2;
            }
            this.NEGEP = this.DIGITS + 3;
            final double betain = ONE / beta;
            a = ONE;
            for (int i = 0; i < this.NEGEP; ++i) {
                a *= betain;
            }
            b = a;
            for (temp2 = ONE - a; temp2 - ONE == ZERO; temp2 = ONE - a) {
                a *= beta;
                --this.NEGEP;
            }
            this.NEGEP = -this.NEGEP;
            this.MACHEP = -this.DIGITS - 3;
            for (a = b, temp2 = ONE + a; temp2 - ONE == ZERO; temp2 = ONE + a) {
                a *= beta;
                ++this.MACHEP;
            }
            this.EPSILON = a;
        }
    }
}
