// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum TransposeModified
{
    NO_TRANSPOSE("NO_TRANSPOSE", 0, 111, (byte)78), 
    TRANSPOSE("TRANSPOSE", 1, 112, (byte)84), 
    CONJUGATE_TRANSPOSE("CONJUGATE_TRANSPOSE", 2, 113, (byte)67);
    
    private final int blas;
    private final byte lapack;
    
    private TransposeModified(final String name, final int ordinal, final int blas, final byte lapack) {
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
