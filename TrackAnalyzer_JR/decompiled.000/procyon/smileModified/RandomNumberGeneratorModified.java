// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public interface RandomNumberGeneratorModified
{
    void setSeed(final long p0);
    
    int next(final int p0);
    
    int nextInt();
    
    int nextInt(final int p0);
    
    long nextLong();
    
    double nextDouble();
    
    void nextDoubles(final double[] p0);
}
