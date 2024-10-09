// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

import java.util.stream.IntStream;

public class RandomModified
{
    private final UniversalGeneratorModified real;
    private final MersenneTwisterModified twister;
    
    public RandomModified() {
        this.real = new UniversalGeneratorModified();
        this.twister = new MersenneTwisterModified();
    }
    
    public RandomModified(final long seed) {
        this.real = new UniversalGeneratorModified(seed);
        this.twister = new MersenneTwisterModified(seed);
    }
    
    public void setSeed(final long seed) {
        this.real.setSeed(seed);
        this.twister.setSeed(seed);
    }
    
    public double nextDouble() {
        return this.real.nextDouble();
    }
    
    public void nextDoubles(final double[] d) {
        this.real.nextDoubles(d);
    }
    
    public double nextDouble(final double lo, final double hi) {
        return lo + (hi - lo) * this.nextDouble();
    }
    
    public void nextDoubles(final double[] d, final double lo, final double hi) {
        this.real.nextDoubles(d);
        final double l = hi - lo;
        for (int n = d.length, i = 0; i < n; ++i) {
            d[i] = lo + l * d[i];
        }
    }
    
    public int nextInt() {
        return this.twister.nextInt();
    }
    
    public int nextInt(final int n) {
        return this.twister.nextInt(n);
    }
    
    public long nextLong() {
        return this.twister.nextLong();
    }
    
    public int[] permutate(final int n) {
        final int[] x = IntStream.range(0, n).toArray();
        this.permutate(x);
        return x;
    }
    
    public void permutate(final int[] x) {
        for (int i = 0; i < x.length; ++i) {
            final int j = i + this.nextInt(x.length - i);
            MathExModified.swap(x, i, j);
        }
    }
    
    public void permutate(final float[] x) {
        for (int i = 0; i < x.length; ++i) {
            final int j = i + this.nextInt(x.length - i);
            MathExModified.swap(x, i, j);
        }
    }
    
    public void permutate(final double[] x) {
        for (int i = 0; i < x.length; ++i) {
            final int j = i + this.nextInt(x.length - i);
            MathExModified.swap(x, i, j);
        }
    }
    
    public void permutate(final Object[] x) {
        for (int i = 0; i < x.length; ++i) {
            final int j = i + this.nextInt(x.length - i);
            MathExModified.swap(x, i, j);
        }
    }
}
