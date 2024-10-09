// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public class MersenneTwisterModified implements RandomNumberGeneratorModified
{
    private static final int UPPER_MASK = Integer.MIN_VALUE;
    private static final int LOWER_MASK = Integer.MAX_VALUE;
    private static final int N = 624;
    private static final int M = 397;
    private static final int[] MAGIC;
    private static final int MAGIC_FACTOR1 = 1812433253;
    private static final int MAGIC_FACTOR2 = 1664525;
    private static final int MAGIC_FACTOR3 = 1566083941;
    private static final int MAGIC_MASK1 = -1658038656;
    private static final int MAGIC_MASK2 = -272236544;
    private static final int MAGIC_SEED = 19650218;
    private final int[] mt;
    private int mti;
    
    static {
        MAGIC = new int[] { 0, -1727483681 };
    }
    
    public MersenneTwisterModified() {
        this(19650218);
    }
    
    public MersenneTwisterModified(final int seed) {
        this.mt = new int[624];
        this.setSeed(seed);
    }
    
    public MersenneTwisterModified(final long seed) {
        this.mt = new int[624];
        this.setSeed(seed);
    }
    
    @Override
    public void setSeed(final long seed) {
        this.setSeed((int)(seed % 2147483647L));
    }
    
    public void setSeed(final int seed) {
        this.mt[0] = seed;
        this.mti = 1;
        while (this.mti < 624) {
            this.mt[this.mti] = 1812433253 * (this.mt[this.mti - 1] ^ this.mt[this.mti - 1] >>> 30) + this.mti;
            ++this.mti;
        }
    }
    
    public void setSeed(final int[] seed) {
        this.setSeed(19650218);
        if (seed == null || seed.length == 0) {
            return;
        }
        int i = 1;
        int j = 0;
        for (int k = Math.max(624, seed.length); k > 0; --k) {
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
        for (int k = 623; k > 0; --k) {
            this.mt[i] = (this.mt[i] ^ (this.mt[i - 1] ^ this.mt[i - 1] >>> 30) * 1566083941) - i;
            if (++i >= 624) {
                this.mt[0] = this.mt[623];
                i = 1;
            }
        }
        final int[] mt = this.mt;
        final int n = 0;
        mt[n] |= Integer.MIN_VALUE;
    }
    
    @Override
    public int next(final int numbits) {
        return this.nextInt() >>> 32 - numbits;
    }
    
    @Override
    public double nextDouble() {
        return (this.nextInt() >>> 1) / 2.147483647E9;
    }
    
    @Override
    public void nextDoubles(final double[] d) {
        for (int n = d.length, i = 0; i < n; ++i) {
            d[i] = this.nextDouble();
        }
    }
    
    @Override
    public int nextInt() {
        if (this.mti >= 624) {
            int i;
            for (i = 0; i < 227; ++i) {
                final int x = (this.mt[i] & Integer.MIN_VALUE) | (this.mt[i + 1] & Integer.MAX_VALUE);
                this.mt[i] = (this.mt[i + 397] ^ x >>> 1 ^ MersenneTwisterModified.MAGIC[x & 0x1]);
            }
            while (i < 623) {
                final int x = (this.mt[i] & Integer.MIN_VALUE) | (this.mt[i + 1] & Integer.MAX_VALUE);
                this.mt[i] = (this.mt[i - 227] ^ x >>> 1 ^ MersenneTwisterModified.MAGIC[x & 0x1]);
                ++i;
            }
            final int x = (this.mt[623] & Integer.MIN_VALUE) | (this.mt[0] & Integer.MAX_VALUE);
            this.mt[623] = (this.mt[396] ^ x >>> 1 ^ MersenneTwisterModified.MAGIC[x & 0x1]);
            this.mti = 0;
        }
        int x = this.mt[this.mti++];
        x ^= x >>> 11;
        x ^= (x << 7 & 0x9D2C5680);
        x ^= (x << 15 & 0xEFC60000);
        x ^= x >>> 18;
        return x;
    }
    
    @Override
    public int nextInt(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        if ((n & -n) == n) {
            return (int)(n * (long)this.next(31) >> 31);
        }
        int bits;
        int val;
        do {
            bits = this.next(31);
            val = bits % n;
        } while (bits - val + (n - 1) < 0);
        return val;
    }
    
    @Override
    public long nextLong() {
        final long x = this.nextInt();
        return x << 32 | (long)this.nextInt();
    }
}
