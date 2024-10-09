// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public class UniversalGeneratorModified implements RandomNumberGeneratorModified
{
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
    
    public UniversalGeneratorModified(final int seed) {
        this.setSeed(seed);
    }
    
    public UniversalGeneratorModified(final long seed) {
        this.setSeed(seed);
    }
    
    @Override
    public void setSeed(final long seed) {
        this.u = new double[97];
        final int ijkl = Math.abs((int)(seed % 899999963L));
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
        for (int ii = 0; ii < 97; ++ii) {
            double s = 0.0;
            double t = 0.5;
            for (int jj = 0; jj < 24; ++jj) {
                final int m = i * j % 179 * k % 179;
                i = j;
                j = k;
                k = m;
                l = (53 * l + 1) % 169;
                if (l * m % 64 >= 32) {
                    s += t;
                }
                t *= 0.5;
            }
            this.u[ii] = s;
        }
        this.c = 0.021602869033813477;
        this.cd = 0.45623308420181274;
        this.cm = 0.9999998211860657;
        this.i97 = 96;
        this.j97 = 32;
    }
    
    @Override
    public double nextDouble() {
        double uni = this.u[this.i97] - this.u[this.j97];
        if (uni < 0.0) {
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
        if (this.c < 0.0) {
            this.c += this.cm;
        }
        uni -= this.c;
        if (uni < 0.0) {
            ++uni;
        }
        return uni;
    }
    
    @Override
    public void nextDoubles(final double[] d) {
        for (int n = d.length, i = 0; i < n; ++i) {
            double uni = this.u[this.i97] - this.u[this.j97];
            if (uni < 0.0) {
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
            if (this.c < 0.0) {
                this.c += this.cm;
            }
            uni -= this.c;
            if (uni < 0.0) {
                ++uni;
            }
            d[i] = uni;
        }
    }
    
    @Override
    public int next(final int numbits) {
        return this.nextInt() >>> 32 - numbits;
    }
    
    @Override
    public int nextInt() {
        return (int)Math.floor(2.147483647E9 * (2.0 * this.nextDouble() - 1.0));
    }
    
    @Override
    public int nextInt(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        return (int)(this.nextDouble() * n);
    }
    
    @Override
    public long nextLong() {
        return (long)Math.floor(9.223372036854776E18 * (2.0 * this.nextDouble() - 1.0));
    }
}
