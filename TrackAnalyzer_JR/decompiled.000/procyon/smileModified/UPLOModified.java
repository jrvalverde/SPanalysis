// 
// Decompiled by Procyon v0.5.36
// 

package smileModified;

public enum UPLOModified
{
    UPPER("UPPER", 0, 121, (byte)85), 
    LOWER("LOWER", 1, 122, (byte)76);
    
    private final int blas;
    private final byte lapack;
    
    private UPLOModified(final String name, final int ordinal, final int blas, final byte lapack) {
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
