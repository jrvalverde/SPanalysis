// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum LayoutModified
{
    ROW_MAJOR("ROW_MAJOR", 0, 101), 
    COL_MAJOR("COL_MAJOR", 1, 102);
    
    private final int blas;
    private final int lapack;
    
    private LayoutModified(final String name, final int ordinal, final int value) {
        this.blas = value;
        this.lapack = value;
    }
    
    public int blas() {
        return this.blas;
    }
    
    public int lapack() {
        return this.lapack;
    }
}
