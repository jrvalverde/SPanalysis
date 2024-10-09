// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum DiagModified
{
    NON_UNIT("NON_UNIT", 0, 131, (byte)78), 
    UNIT("UNIT", 1, 132, (byte)85);
    
    private final int blas;
    private final byte lapack;
    
    private DiagModified(final String name, final int ordinal, final int blas, final byte lapack) {
        this.blas = blas;
        this.lapack = lapack;
    }
    
    public int blas() {
        return this.blas;
    }
    
    public byte lapack() {
        return this.lapack;
    }
}
