// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public class IntPair
{
    public final int i;
    public final int j;
    
    public IntPair(final int i, final int j) {
        this.i = i;
        this.j = j;
    }
    
    @Override
    public int hashCode() {
        return this.i * 31 + this.j;
    }
    
    @Override
    public String toString() {
        return String.format("(%d, %d)", this.i, this.j);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof IntPair) {
            final IntPair p = (IntPair)o;
            return this.i == p.i && this.j == p.j;
        }
        return false;
    }
}
